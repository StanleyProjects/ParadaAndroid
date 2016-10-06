package ru.parada.app.modules.main;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import ru.parada.app.R;
import ru.parada.app.contracts.MainContract;
import ru.parada.app.units.MVPFragment;

public class MainFragment
        extends MVPFragment<MainContract.Presenter, MainContract.Behaviour>
        implements MainContract.View
{
    static public MainFragment newInstanse(MainContract.Behaviour behaviour)
    {
        MainFragment fragment = new MainFragment();
        fragment.setBehaviour(behaviour);
        return fragment;
    }

    private ImageView phone;

    private Drawable btn_phone;
    private Drawable btn_phone_close;

    @Override
    protected int setContentView()
    {
        return R.layout.main_fragment;
    }

    @Override
    protected MainContract.Presenter setPresenter()
    {
        return new MainPresenter(this);
    }

    @Override
    protected void initViews(View v)
    {
        setClickListener(v.findViewById(R.id.menu), v.findViewById(R.id.services));
        phone = (ImageView)v.findViewById(R.id.phone);
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
                    case R.id.phone:
                        getPresenter().phoneSwitch();
                        break;
                    case R.id.services:
                        getBehaviour().openService();
                        break;
                }
            }
        };
    }

    @Override
    protected void init()
    {
        setClickListener(phone);
        btn_phone = getActivity().getResources()
                                 .getDrawable(R.drawable.btn_phone);
        btn_phone_close = getActivity().getResources()
                                       .getDrawable(R.drawable.btn_phone_close);
        phoneOpen();
        getPresenter().loadNews();
    }

    @Override
    public void phoneOpen()
    {
        phone.setImageDrawable(btn_phone);
    }

    @Override
    public void phoneClose()
    {
        phone.setImageDrawable(btn_phone_close);
    }
}