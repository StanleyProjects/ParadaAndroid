package ru.parada.app.units;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class MVPFragment<PRESENTER, BEHAVIOUR>
    extends Fragment
{
    static public <P, B> Fragment setMVPFragment(MVPFragment<P, B> fragment, P presenter, B behaviour)
    {
        fragment.setPresenter(presenter);
        fragment.setBehaviour(behaviour);
        return fragment;
    }

    private PRESENTER presenter;
    private BEHAVIOUR behaviour;
    private View.OnClickListener clickListener;
    private View mainView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if(mainView == null)
        {
            mainView = inflater.inflate(setContentView(), container, false);
            this.clickListener = setClickListener();
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

    private void setBehaviour(BEHAVIOUR b)
    {
        this.behaviour = b;
    }
    protected BEHAVIOUR getBehaviour()
    {
        return behaviour;
    }

    private void setPresenter(PRESENTER p)
    {
        this.presenter = p;
    }
    protected PRESENTER getPresenter()
    {
        return presenter;
    }

    abstract protected int setContentView();
    abstract protected void initViews(View v);
    abstract protected View.OnClickListener setClickListener();
    abstract protected void init();
}