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
        extends MVPFragment<MenuContract.Presenter, MenuContract.Behaviour>
        implements MenuContract.View
{
    static public MenuFragment newInstanse(MenuContract.Behaviour behaviour)
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
            public void openMain()
            {
                getPresenter().openMain();
            }

            @Override
            public void openServices()
            {
                getPresenter().openServices();
            }

            @Override
            public void openDoctors()
            {
                getPresenter().openDoctors();
            }

            @Override
            public void openPrices()
            {
                getPresenter().openPrices();
            }
        });
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
    }

    private void initMenuListModel()
    {
        ArrayList<MenuModel> menuModels = new ArrayList<>();
        for(ScreenType screenType : ScreenType.values())
        {
            switch(screenType)
            {
                case MAIN_SCREEN:
                    menuModels.add(new MenuModel(R.mipmap.menu_main_active, getActivity().getResources()
                                                                                         .getString(R.string.main))
                    {
                        @Override
                        public void click(MenuContract.Behaviour behaviour)
                        {
                            behaviour.openMain();
                        }
                    });
                    break;
                case SERVICES_SCREEN:
                    menuModels.add(new MenuModel(R.mipmap.menu_service, getActivity().getResources()
                                                                                     .getString(R.string.services))
                    {
                        @Override
                        public void click(MenuContract.Behaviour behaviour)
                        {
                            behaviour.openServices();
                        }
                    });
                    break;
                case DOCTORS_SCREEN:
                    menuModels.add(new MenuModel(R.mipmap.menu_doctors, getActivity().getResources()
                                                                                     .getString(R.string.doctors))
                    {
                        @Override
                        public void click(MenuContract.Behaviour behaviour)
                        {
                            behaviour.openDoctors();
                        }
                    });
                    break;
                case PRICES_SCREEN:
                    menuModels.add(new MenuModel(R.mipmap.menu_prices, getActivity().getResources()
                                                                                    .getString(R.string.prices))
                    {
                        @Override
                        public void click(MenuContract.Behaviour behaviour)
                        {
                            behaviour.openPrices();
                        }
                    });
                    break;
            }
        }
        menuListModel = new MenuListModel(menuModels);
    }

    @Override
    public void setMain()
    {
        getBehaviour().openMain();
        menuListModel.setHighlight(ScreenType.MAIN_SCREEN.ordinal());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setServices()
    {
        getBehaviour().openServices();
        menuListModel.setHighlight(ScreenType.SERVICES_SCREEN.ordinal());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setDoctors()
    {
        getBehaviour().openDoctors();
        menuListModel.setHighlight(ScreenType.DOCTORS_SCREEN.ordinal());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setPrices()
    {
        getBehaviour().openPrices();
        menuListModel.setHighlight(ScreenType.PRICES_SCREEN.ordinal());
        adapter.notifyDataSetChanged();
    }
}