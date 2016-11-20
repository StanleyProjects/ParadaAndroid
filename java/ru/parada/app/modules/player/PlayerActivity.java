package ru.parada.app.modules.player;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Timer;
import java.util.TimerTask;

import ru.parada.app.R;
import ru.parada.app.core.DoctorVideosCore;

public class PlayerActivity
        extends Activity
        implements DoctorVideosCore.Mark
{
    static public Intent createIntent(Context context, String id)
    {
        return new Intent(context, PlayerActivity.class).putExtra(VIDEO_ID, id);
    }

    private WebView player;
    private View waiter;

    private AlertDialog alertDialog;

    private Timer timer;
    private String video_id;
    private volatile boolean resume;
    private volatile boolean error;
    private final WebViewClient client = new WebViewClient()
    {
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError webResourceError)
        {
            Log.e(getClass().getName(), "onReceivedError request " + request + " error " + webResourceError);
            error = true;
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap f)
        {
            Log.e(getClass().getName(), "onPageStarted " + url);
        }
        @Override
        public void onPageFinished(WebView view, String url)
        {
            Log.e(getClass().getName(), "onPageFinished " + url);
            pageFinished();
        }
        @Override
        public void onLoadResource(WebView view, String url)
        {
            Log.e(getClass().getName(), "onLoadResource " + url);
        }
    };

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if(timer != null)
        {
            timer.cancel();
        }
        if(alertDialog != null)
        {
            alertDialog.dismiss();
        }
        if(player != null)
        {
            player.loadUrl("about:blank");
            player.clearFormData();
            player.clearHistory();
            player.clearMatches();
            player.stopLoading();
            player.setWebViewClient(null);
        }
    }
    @Override
    public void onResume()
    {
        super.onResume();
        resume = true;
    }
    @Override
    public void onPause()
    {
        super.onPause();
        resume = false;
    }
    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.player_activity);
        initViews();
        init();
    }

    private void initViews()
    {
        player = (WebView)findViewById(R.id.player);
        waiter = findViewById(R.id.waiter);
    }

    private void init()
    {
        createErrorDialog();
        video_id = getIntent().getExtras()
                              .getString(VIDEO_ID);
        player.setVisibility(View.GONE);
        waiter.setVisibility(View.GONE);
        player.getSettings()
              .setJavaScriptEnabled(true);
        player.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                return true;
            }
        });
        player.setWebChromeClient(new WebChromeClient());
        //
        player.getSettings().setPluginState(WebSettings.PluginState.ON);
        player.getSettings().setLoadWithOverviewMode(true);
        player.setBackgroundColor(getResources().getColor(R.color.black));
        player.clearCache(true);
        //
        loadUrl();
    }

    private void loadUrl()
    {
        runAfterResume(new Runnable()
        {
            @Override
            public void run()
            {
                if(timer != null)
                {
                    timer.cancel();
                }
                timer = new Timer();
                timer.schedule(new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        runAfterResume(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                player.stopLoading();
                                error = true;
                                pageFinished();
                            }
                        });
                    }
                }, 30_000);
                waiter.setVisibility(View.VISIBLE);
                error = false;
                player.setWebViewClient(client);
                player.setVisibility(View.VISIBLE);
                player.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        String html = "<body style='margin:0;padding:0;background-color:#000000'>" +
                                "<iframe height='100%' width='100%' background-color='#000000' " +
                                "src='embed/" + video_id + "?modestbranding=1&autohide=1&showinfo=0&rel=0' frameborder='0'/>" +
                                "</body>";
                        player.loadDataWithBaseURL("https://www.youtube.com", html, "text/html", "UTF-8", null);
                    }
                });
            }
        });
    }

    private void pageFinished()
    {
        waiter.setVisibility(View.GONE);
        if(timer != null)
        {
            timer.cancel();
        }
        if(error)
        {
            player.loadUrl("about:blank");
            player.setWebViewClient(new WebViewClient());
            player.setVisibility(View.GONE);
            alertDialog.show();
        }
    }

    private void runAfterResume(final Runnable runnable)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(!resume)
                {

                }
                runOnUiThread(runnable);
            }
        }).start();
    }

    private void createErrorDialog()
    {
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getResources().getString(R.string.error));
        alertDialog.setMessage(getResources().getString(R.string.video_error));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Отмена", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                finish();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Да", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                loadUrl();
            }
        });
        alertDialog.setCancelable(false);
    }

    @Override
    public void onBackPressed()
    {
        player.loadUrl("about:blank");
        player.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                Log.e(getClass().getName(), "onBackPressed onPageFinished " + url);
                finish();
            }
        });
    }
}