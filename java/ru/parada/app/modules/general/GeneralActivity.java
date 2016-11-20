package ru.parada.app.modules.general;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import ru.parada.app.R;
import ru.parada.app.contracts.contacts.ContactsContract;
import ru.parada.app.contracts.doctors.DoctorsContract;
import ru.parada.app.contracts.EventsContract;
import ru.parada.app.contracts.GeneralContract;
import ru.parada.app.contracts.MainContract;
import ru.parada.app.contracts.MenuContract;
import ru.parada.app.contracts.NotificationsContract;
import ru.parada.app.contracts.ServicesContract;
import ru.parada.app.contracts.ServicesWithPricesContract;
import ru.parada.app.contracts.SocialsContract;
import ru.parada.app.core.GeneralCore;
import ru.parada.app.modules.contacts.list.ContactsFragment;
import ru.parada.app.modules.doctors.list.DoctorsFragment;
import ru.parada.app.modules.events.EventsFragment;
import ru.parada.app.modules.main.MainFragment;
import ru.parada.app.modules.menu.MenuFragment;
import ru.parada.app.modules.notifications.NotificationsFragment;
import ru.parada.app.modules.services.list.ServicesFragment;
import ru.parada.app.modules.servicesprices.ServicesWithPricesFragment;
import ru.parada.app.modules.socials.SocialsFragment;
import ru.parada.app.units.CallbackConnector;
import ru.parada.app.units.DrawerContainer;

public class GeneralActivity
        extends FragmentActivity
        implements GeneralContract.View
{
    private Fragment currentFragment;
    private final Fragment menuFragment = MenuFragment.newInstanse(new MenuContract.Behaviour()
    {
        @Override
        public void open(GeneralCore.ScreenType screenType)
        {
            presenter.setScreen(screenType);
        }
    }, new CallbackConnector<MenuContract.Callback>()
    {
        @Override
        public void setCallback(MenuContract.Callback callback)
        {
            menuCallback = callback;
        }
    });
    private final Fragment mainFragment = MainFragment.newInstanse(new MainContract.MainBehaviour()
    {
        @Override
        public void oneOfNews(int id)
        {
            presenter.setScreen(GeneralCore.ScreenType.EVENTS_SCREEN);
            menuCallback.open(GeneralCore.ScreenType.EVENTS_SCREEN);
            eventsCallback.setOneOfNews(id);
        }

        @Override
        public void openMenu()
        {
            GeneralActivity.this.openMenu();
        }

        @Override
        public void openServices()
        {
            presenter.setScreen(GeneralCore.ScreenType.SERVICES_SCREEN);
            menuCallback.open(GeneralCore.ScreenType.SERVICES_SCREEN);
        }

        @Override
        public void openSubscribe()
        {

        }

        @Override
        public void openPrices()
        {
            presenter.setScreen(GeneralCore.ScreenType.PRICES_SCREEN);
            menuCallback.open(GeneralCore.ScreenType.PRICES_SCREEN);
        }

        @Override
        public void openAllNews()
        {
            presenter.setScreen(GeneralCore.ScreenType.EVENTS_SCREEN);
            menuCallback.open(GeneralCore.ScreenType.EVENTS_SCREEN);
            eventsCallback.setNews();
        }
    });
    private final Fragment servicesFragment = ServicesFragment.newInstanse(new ServicesContract.Behaviour()
    {
        @Override
        public void openMenu()
        {
            GeneralActivity.this.openMenu();
        }
    });
    private final Fragment doctorsFragment = DoctorsFragment.newInstanse(new DoctorsContract.Behaviour()
    {
        @Override
        public void openMenu()
        {
            GeneralActivity.this.openMenu();
        }
    });
    private final Fragment pricesFragment = ServicesWithPricesFragment.newInstanse(new ServicesWithPricesContract.Behaviour()
    {
        @Override
        public void openMenu()
        {
            GeneralActivity.this.openMenu();
        }
    });
    private final Fragment eventsFragment = EventsFragment.newInstanse(new EventsContract.Behaviour()
    {
        @Override
        public void openMenu()
        {
            GeneralActivity.this.openMenu();
        }
    }, new CallbackConnector<EventsContract.Callback>()
    {
        @Override
        public void setCallback(EventsContract.Callback callback)
        {
            eventsCallback = callback;
        }
    });
    private final Fragment socialsFragment = SocialsFragment.newInstanse(new SocialsContract.Behaviour()
    {
        @Override
        public void openMenu()
        {
            GeneralActivity.this.openMenu();
        }
    });
    private final Fragment notificationsFragment = NotificationsFragment.newInstanse(new NotificationsContract.Behaviour()
    {
        @Override
        public void openMenu()
        {
            GeneralActivity.this.openMenu();
        }
    });
    private final Fragment contactsFragment = ContactsFragment.newInstanse(new ContactsContract.Behaviour()
    {
        @Override
        public void openMenu()
        {
            GeneralActivity.this.openMenu();
        }
    });

    private DrawerContainer drawerContainer;

    private GeneralContract.Presenter presenter;
    private MenuContract.Callback menuCallback;
    private EventsContract.Callback eventsCallback;

    @Override
    public void onBackPressed()
    {
        if(drawerContainer.isOpen())
        {
            drawerContainer.closeDrawer(null);
            return;
        }
        if(!currentFragment.getChildFragmentManager().popBackStackImmediate())
        {
            super.onBackPressed();
        }
    }
    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.general_drawer_activity);
        initViews();
        init();
    }

    private void initViews()
    {
        drawerContainer = (DrawerContainer)findViewById(R.id.main_drawer);
    }

    private void init()
    {
        presenter = new GeneralPresenter(this);
        getSupportFragmentManager().beginTransaction()
                                   .add(R.id.menu_frame, menuFragment)
                                   .commit();
        currentFragment = mainFragment;
        replaceFragment();
    }

    @Override
    public void showScreen(final GeneralCore.ScreenType screenType)
    {
        closeMenu(new DrawerContainer.AnimationEndListener()
        {
            @Override
            public void onAnimationEnd()
            {
                switch(screenType)
                {
                    case MAIN_SCREEN:
                        currentFragment = mainFragment;
                        break;
                    case SERVICES_SCREEN:
                        currentFragment = servicesFragment;
                        break;
                    case DOCTORS_SCREEN:
                        currentFragment = doctorsFragment;
                        break;
                    case PRICES_SCREEN:
                        currentFragment = pricesFragment;
                        break;
                    case EVENTS_SCREEN:
                        currentFragment = eventsFragment;
                        break;
                    case SOCIALS_SCREEN:
                        currentFragment = socialsFragment;
                        break;
                    case NOTIFICATIONS_SCREEN:
                        currentFragment = notificationsFragment;
                        break;
                    case CONTACTS_SCREEN:
                        currentFragment = contactsFragment;
                        break;
                    default:
                        return;
                }
                replaceFragment();
            }
        });
    }

    private void openMenu()
    {
        drawerContainer.openDrawer();
    }

    private void closeMenu(DrawerContainer.AnimationEndListener listener)
    {
        drawerContainer.closeDrawer(listener);
    }

    private void replaceFragment()
    {
        getSupportFragmentManager().beginTransaction()
                                   .replace(R.id.main_frame, currentFragment)
                                   .commit();
    }
}