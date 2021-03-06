package ru.parada.app.modules.menu;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import ru.parada.app.App;
import ru.parada.app.R;
import ru.parada.app.contracts.MenuContract;
import ru.parada.app.core.GeneralCore;
import ru.parada.app.modules.menu.adapter.MenuAdapter;
import ru.parada.app.modules.menu.models.MenuModel;
import ru.parada.app.units.ArrayListModel;
import ru.parada.app.units.CallbackConnector;
import ru.parada.app.units.MVPFragment;

public class MenuFragment
        extends MVPFragment<MenuContract.Presenter, MenuContract.Behaviour>
        implements MenuContract.View
{
    static public MVPFragment newInstanse(MenuContract.Behaviour behaviour, CallbackConnector<MenuContract.Callback> cconnector)
    {
        final MenuFragment fragment = new MenuFragment();
        fragment.setBehaviour(behaviour);
        cconnector.setCallback(new MenuContract.Callback()
        {
            @Override
            public void open(GeneralCore.ScreenType screenType)
            {
                fragment.getPresenter().open(screenType);
            }
        });
        return fragment;
    }

    private RecyclerView list;

    private MenuAdapter adapter;

    @Override
    protected MenuContract.Presenter setPresenter()
    {
        return new MenuPresenter(this);
    }

    @Override
    protected int setContentView()
    {
        return R.layout.menu_screen;
    }

    @Override
    protected void initViews(View v)
    {
        list = (RecyclerView)v.findViewById(R.id.list);
    }

    @Override
    protected void init()
    {
        adapter = new MenuAdapter(getActivity(), new MenuContract.Behaviour()
        {
            @Override
            public void open(GeneralCore.ScreenType screenType)
            {
                if(!App.getComponent().getAndroidUtil().blockClick())
                {
                    getPresenter().open(screenType);
                }
            }
        });
        adapter.swapData(initMenuListModel());
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        getPresenter().checkNotifications();
    }

    private ArrayListModel<MenuModel> initMenuListModel()
    {
        ArrayList<MenuModel> menuModels = new ArrayList<>();
        for(final GeneralCore.ScreenType screenType : GeneralCore.ScreenType.values())
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
                case EVENTS_SCREEN:
                    ico = R.mipmap.menu_news_and_actions;
                    name = getActivity().getResources()
                                        .getString(R.string.events);
                    break;
                case SOCIALS_SCREEN:
                    ico = R.mipmap.menu_socials;
                    name = getActivity().getResources()
                                        .getString(R.string.socials);
                    break;
                case INFO_SCREEN:
                    ico = R.mipmap.info_icon;
                    name = getActivity().getResources()
                                        .getString(R.string.info_menu);
                    break;
                case NOTIFICATIONS_SCREEN:
                    ico = R.mipmap.menu_push;
                    name = getActivity().getResources()
                                        .getString(R.string.notifications);
                    menuModels.add(new MenuModel(screenType.ordinal(), ico, name, true)
                    {
                        @Override
                        public void click(MenuContract.Behaviour behaviour)
                        {
                            behaviour.open(screenType);
                        }
                    });
                    continue;
                case CONTACTS_SCREEN:
                    ico = R.mipmap.menu_contacts;
                    name = getActivity().getResources()
                                        .getString(R.string.contacts);
                    break;
                default:
                    continue;
            }
            menuModels.add(new MenuModel(screenType.ordinal(), ico, name, false)
            {
                @Override
                public void click(MenuContract.Behaviour behaviour)
                {
                    behaviour.open(screenType);
                }
            });
        }
        return new ArrayListModel<>(menuModels);
    }

    @Override
    public void set(GeneralCore.ScreenType screenType)
    {
        if(screenType == GeneralCore.ScreenType.NOTIFICATIONS_SCREEN)
        {
            App.getComponent().getPreferenceManager().setNotificationBadge(false);
        }
        getBehaviour().open(screenType);
        adapter.setHighlight(screenType.ordinal());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void update()
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                adapter.notifyDataSetChanged();
            }
        });
    }
}