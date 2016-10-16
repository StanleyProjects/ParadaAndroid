package ru.parada.app.modules.menu;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import ru.parada.app.R;
import ru.parada.app.contracts.MenuContract;
import ru.parada.app.contracts.ScreenType;
import ru.parada.app.units.MVPFragment;

public class MenuFragment
        extends MVPFragment<MenuContract.Presenter, MenuContract.MenuBehaviour>
        implements MenuContract.View
{
    static public MenuFragment newInstanse(MenuContract.MenuBehaviour behaviour)
    {
        MenuFragment fragment = new MenuFragment();
        fragment.setBehaviour(behaviour);
        return fragment;
    }

    private RecyclerView list;

    private MenuAdapter adapter;
    private MenuListModel menuListModel;

    @Override
    protected int setContentView()
    {
        return R.layout.menu_screen;
    }

    @Override
    protected MenuContract.Presenter setPresenter()
    {
        return new MenuPresenter(this);
    }

    @Override
    protected void initViews(View v)
    {
        //setClickListener(v.findViewById(R.id.main), v.findViewById(R.id.service), v.findViewById(R.id.doctors), v.findViewById(R.id.prices));
        list = (RecyclerView)v.findViewById(R.id.list);
        getBehaviour().setCallback(new MenuContract.Callback()
        {
            @Override
            public void open(ScreenType screenType)
            {
                getPresenter().open(screenType);
            }
        });
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
                }
            }
        };
    }

    @Override
    protected void init()
    {
        initMenuListModel();
        adapter = new MenuAdapter(getActivity(), menuListModel, new MenuContract.Behaviour()
        {
            @Override
            public void open(ScreenType screenType)
            {
                getPresenter().open(screenType);
            }
        });
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
    }

    private void initMenuListModel()
    {
        ArrayList<MenuModel> menuModels = new ArrayList<>();
        for(final ScreenType screenType : ScreenType.values())
        {
            int ico = 0;
            String name = null;
            switch(screenType)
            {
                case MAIN_SCREEN:
                    ico = R.mipmap.menu_main_active;
                    name = getActivity().getResources()
                                        .getString(R.string.main);
                    break;
                case SERVICES_SCREEN:
                    ico = R.mipmap.menu_service;
                    name = getActivity().getResources()
                                        .getString(R.string.services);
                    break;
                case DOCTORS_SCREEN:
                    ico = R.mipmap.menu_doctors;
                    name = getActivity().getResources()
                                        .getString(R.string.doctors);
                    break;
                case PRICES_SCREEN:
                    ico = R.mipmap.menu_prices;
                    name = getActivity().getResources()
                                        .getString(R.string.prices);
                    break;
                default:
                    continue;
            }
            menuModels.add(new MenuModel(ico, name)
            {
                @Override
                public void click(MenuContract.Behaviour behaviour)
                {
                    behaviour.open(screenType);
                }
            });
        }
        menuListModel = new MenuListModel(menuModels);
    }

    @Override
    public void set(ScreenType screenType)
    {
        getBehaviour().open(screenType);
        menuListModel.setHighlight(screenType.ordinal());
        adapter.notifyDataSetChanged();
    }
}