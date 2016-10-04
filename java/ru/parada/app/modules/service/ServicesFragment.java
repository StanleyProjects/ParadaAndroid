package ru.parada.app.modules.service;

import android.view.View;

import ru.parada.app.R;
import ru.parada.app.contracts.ServicesContract;
import ru.parada.app.units.MVPFragment;

public class ServicesFragment
        extends MVPFragment<ServicesContract.Presenter, ServicesContract.Behaviour>
        implements ServicesContract.View
{
    @Override
    protected int setContentView()
    {
        return R.layout.service_fragment;
    }

    @Override
    protected void initViews(View v)
    {
        setClickListener(v.findViewById(R.id.menu));
    }

    @Override
    protected View.OnClickListener setClickListener()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switch(v.getId())
                {
                    case R.id.menu:
                        getBehaviour().openMenu();
                        break;
                }
            }
        };
    }

    @Override
    protected void init()
    {
        getPresenter().loadServices();
    }

    @Override
    public void updateServices()
    {

    }
}