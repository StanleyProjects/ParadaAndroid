package ru.parada.app.units;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public abstract class MVPFragment<PRESENTER, BEHAVIOUR>
        extends Fragment
{
    private PRESENTER presenter;
    private BEHAVIOUR behaviour;
    private View.OnClickListener clickListener;
    private View mainView;
    private Handler uiHandler;
    private InputMethodManager inputMethodManager;

    @Override
    public void onPause()
    {
        super.onPause();
        hideKeyBoard();
    }
    @Override
    public void onResume()
    {
        super.onResume();
        hideKeyBoard();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if(mainView == null)
        {
            mainView = inflater.inflate(setContentView(), container, false);
            mainView.setFocusableInTouchMode(true);
            mainView.requestFocus();
            mainView.setOnKeyListener(new View.OnKeyListener()
            {
                @Override
                public boolean onKey(View view, int keyCode, KeyEvent keyEvent)
                {
                    if(keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP)
                    {
                        return onBackPressed();
                    }
                    return false;
                }
            });
            this.inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            this.uiHandler = new Handler();
            this.presenter = setPresenter();
            this.clickListener = setClickListener();
            initViews(mainView);
            init();
        }
        return mainView;
    }

    protected void setClickListener(View... views)
    {
        for(View v : views)
        {
            if(v != null)
            {
                v.setOnClickListener(clickListener);
            }
        }
    }

    protected void runOnUiThread(Runnable r, long d)
    {
        if(d > 0)
        {
            uiHandler.postDelayed(r, d);
        }
        else
        {
            uiHandler.post(r);
        }
    }

    protected void runOnUiThread(Runnable r)
    {
        uiHandler.post(r);
    }

    protected void runAfterResume(final Runnable r)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(!isResumed())
                {

                }
                uiHandler.post(r);
            }
        }).start();
    }

    protected void showToast(int mid)
    {
        Toast.makeText(getActivity(), mid, Toast.LENGTH_SHORT)
             .show();
    }

    protected boolean onBackPressed()
    {
        return false;
    }

    protected void hideKeyBoard()
    {
        inputMethodManager.hideSoftInputFromWindow(mainView.getWindowToken(), 0);
    }

    protected void setBehaviour(BEHAVIOUR b)
    {
        this.behaviour = b;
    }

    protected BEHAVIOUR getBehaviour()
    {
        return behaviour;
    }

    protected PRESENTER getPresenter()
    {
        return presenter;
    }

    abstract protected PRESENTER setPresenter();

    abstract protected int setContentView();

    abstract protected void initViews(View v);

    abstract protected View.OnClickListener setClickListener();

    abstract protected void init();
}