package ru.parada.app.units;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import ru.parada.app.App;
import ru.parada.app.R;

public abstract class MVPFragment<PRESENTER, BEHAVIOUR>
        extends Fragment
{
    private PRESENTER presenter;
    private BEHAVIOUR behaviour;
    private View.OnClickListener clickListener;
    private View mainView;
    private Animation enterAnimation;

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
            presenter = setPresenter();
            clickListener = new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(!App.getComponent().getAndroidUtil().blockClick())
                    {
                        onClickView(view.getId());
                    }
                }
            };
            enterAnimation = getEnterAnimation();
            if(enterAnimation != null)
            {
                mainView.setVisibility(View.GONE);
                runAfterResume(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mainView.setVisibility(View.VISIBLE);
                        mainView.startAnimation(enterAnimation);
                    }
                });
            }
            initViews(mainView);
            init();
        }
        return mainView;
    }

    protected Animation getEnterAnimation()
    {
        return null;
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