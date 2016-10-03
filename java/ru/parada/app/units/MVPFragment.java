package ru.parada.app.units;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class MVPFragment<PRESENTER, LISTENER>
    extends Fragment
{
    private PRESENTER presenter;
    private LISTENER listener;
    private View.OnClickListener clickListener;
    private View mainView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if(mainView == null)
        {
            mainView = inflater.inflate(setContentView(), container, false);
            this.clickListener = setClickListener();
            this.presenter = setPresenter();
            initViews(mainView);
            init();
        }
//        mainView = inflater.inflate(setContentView(), container, false);
//        this.clickListener = setClickListener();
//        this.presenter = setPresenter();
//        initViews(mainView);
//        init();
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

    public void setListener(LISTENER listener)
    {
        this.listener = listener;
    }
    protected LISTENER getListener()
    {
        return listener;
    }

    protected PRESENTER getPresenter()
    {
        return presenter;
    }

    abstract protected int setContentView();
    abstract protected void initViews(View v);
    abstract protected PRESENTER setPresenter();
    abstract protected View.OnClickListener setClickListener();
    abstract protected void init();
}