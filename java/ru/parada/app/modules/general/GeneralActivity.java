package ru.parada.app.modules.general;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import ru.parada.app.R;
import ru.parada.app.contracts.DoctorsContract;
import ru.parada.app.contracts.GeneralContract;
import ru.parada.app.contracts.MainContract;
import ru.parada.app.contracts.MenuContract;
import ru.parada.app.contracts.ServicesContract;
import ru.parada.app.modules.doctors.DoctorsFragment;
import ru.parada.app.modules.main.MainFragment;
import ru.parada.app.modules.menu.MenuFragment;
import ru.parada.app.modules.services.ServicesFragment;
import ru.parada.app.units.DrawerContainer;

public class GeneralActivity
        extends AppCompatActivity
        implements GeneralContract.View
{
    private Fragment currentFragment;
    private final Fragment menuFragment = MenuFragment.newInstanse(new MenuContract.Behaviour()
    {
        @Override
        public void openMain()
        {
            presenter.setMainScreen();
        }
        @Override
        public void openServices()
        {
            presenter.setServicesScreen();
        }
        @Override
        public void openDoctors()
        {
            presenter.setDoctorsScreen();
        }

        @Override
        public void openPrices()
        {

        }
    });
    private final Fragment mainFragment = MainFragment.newInstanse(new MainContract.MainBehaviour()
    {
        @Override
        public void openMenu()
        {
            GeneralActivity.this.openMenu();
        }
        @Override
        public void openServices()
        {
            presenter.setServicesScreen();
        }
        @Override
        public void openSubscribe()
        {

        }
        @Override
        public void openPrices()
        {

        }
        @Override
        public void openAllNews()
        {

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

    private DrawerContainer drawerContainer;

    private GeneralContract.Presenter presenter;

    @Override
    public void onBackPressed()
    {
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
    public void showMainScreen()
    {
        closeMenu(new DrawerContainer.AnimationEndListener()
        {
            @Override
            public void onAnimationEnd()
            {
                currentFragment = mainFragment;
                replaceFragment();
            }
        });
    }

    @Override
    public void showServicesScreen()
    {
        closeMenu(new DrawerContainer.AnimationEndListener()
        {
            @Override
            public void onAnimationEnd()
            {
                currentFragment = servicesFragment;
                replaceFragment();
            }
        });
    }

    @Override
    public void showDoctorsScreen()
    {
        closeMenu(new DrawerContainer.AnimationEndListener()
        {
            @Override
            public void onAnimationEnd()
            {
                currentFragment = doctorsFragment;
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