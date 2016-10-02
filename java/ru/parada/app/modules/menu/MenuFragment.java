package ru.parada.app.modules.menu;

import android.view.View;

import ru.parada.app.R;
import ru.parada.app.contracts.MenuContract;
import ru.parada.app.units.MVPFragment;

public class MenuFragment
        extends MVPFragment<MenuContract.Presenter, MenuFragmentListener>
        implements MenuContract.View
{
    static public MenuFragment newInstanse(MenuFragmentListener l)
    {
        MenuFragment fragment = new MenuFragment();
        fragment.setListener(l);
        return fragment;
    }

    @Override
    protected int setContentView()
    {
        return R.layout.menu_fragment;
    }

    @Override
    protected void initViews(View v)
    {
        setClickListener(v.findViewById(R.id.main), v.findViewById(R.id.service), v.findViewById(R.id.doctors));
    }

    @Override
    protected MenuContract.Presenter setPresenter()
    {
        return new MenuPresenter(this);
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
                    case R.id.main:
                        getPresenter().openMain();
                        break;
                    case R.id.service:
                        getPresenter().openServices();
                        break;
                    case R.id.doctors:
                        getPresenter().openDoctors();
                        break;
                }
            }
        };
    }

    @Override
    protected void init()
    {
    }

    @Override
    public void setMain()
    {
        getListener().openMain();
    }

    @Override
    public void setServices()
    {
        getListener().openServices();
    }

    @Override
    public void setDoctors()
    {
        getListener().openDoctors();
    }
}