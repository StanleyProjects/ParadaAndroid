package ru.parada.app.units;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ru.parada.app.App;

public abstract class MVPFragment<PRESENTER, BEHAVIOUR>
        extends Fragment
{
    private PRESENTER presenter;
    private BEHAVIOUR behaviour;
    private View.OnClickListener clickListener;
    private View mainView;
    private volatile boolean click;

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
        click = true;
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
            this.presenter = setPresenter();
//            this.clickListener = setClickListener();
            this.clickListener = new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(!click)
                    {
                        return;
                    }
                    click = false;
                    disableViewOn(500);
                    onClickView(view.getId());
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            click = true;
                        }
                    }, 500);
                }
            };
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

    protected void disableViewOn(long ms)
    {
        mainView.setEnabled(false);
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                mainView.setEnabled(true);
            }
        }, ms);
    }
    protected void runOnUiThread(Runnable r)
    {
        App.getComponent().getAndroidUtil().runOnUiThread(r);
    }
    protected void runOnUiThread(Runnable r, long ms)
    {
        App.getComponent().getAndroidUtil().runOnUiThread(r, ms);
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
                runOnUiThread(r);
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
        App.getComponent().getAndroidUtil().hideKeyBoard(mainView.getWindowToken());
    }

    protected void onClickView(int id)
    {

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

    abstract protected void init();
}