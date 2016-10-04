package ru.parada.app.modules.general;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ru.parada.app.R;
import ru.parada.app.contracts.DoctorsContract;
import ru.parada.app.contracts.GeneralContract;
import ru.parada.app.contracts.MainContract;
import ru.parada.app.contracts.MenuContract;
import ru.parada.app.contracts.ServicesContract;
import ru.parada.app.modules.doctors.DoctorsFragment;
import ru.parada.app.modules.doctors.DoctorsPresenter;
import ru.parada.app.modules.main.MainFragment;
import ru.parada.app.modules.main.MainPresenter;
import ru.parada.app.modules.menu.MenuFragment;
import ru.parada.app.modules.menu.MenuPresenter;
import ru.parada.app.modules.service.ServicesFragment;
import ru.parada.app.modules.service.ServicesPresenter;
import ru.parada.app.units.MVPFragment;

public class GeneralActivity
        extends AppCompatActivity
        implements GeneralContract.View
{
    private Fragment menuFragment;
    private Fragment mainFragment;
    private Fragment servicesFragment;
    private Fragment doctorsFragment;

    private View menu;

    private GeneralContract.Presenter presenter;

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.general_activity);
        initViews();
        init();
    }

    private void initViews()
    {
        menu = findViewById(R.id.menu);
        findViewById(R.id.close_menu).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                closeMenu();
            }
        });
    }

    private void init()
    {
        initFragments();
        presenter = new GeneralPresenter(this);
        getSupportFragmentManager().beginTransaction()
                                   .add(R.id.menu_frame, menuFragment)
                                   .commit();
        showMainScreen();
    }

    private void initFragments()
    {
        initMenuFragment();
        initMainFragment();
        initServicesFragment();
        initDoctorsFragment();
    }

    private void initMenuFragment()
    {
        MenuFragment fragment = new MenuFragment();
        menuFragment = MVPFragment.setMVPFragment(fragment, new MenuPresenter(fragment), new MenuContract.Behaviour()
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
        });
    }
    private void initMainFragment()
    {
        MainFragment fragment = new MainFragment();
        mainFragment = MVPFragment.setMVPFragment(fragment, new MainPresenter(fragment), new MainContract.Behaviour()
        {
            @Override
            public void openMenu()
            {
                GeneralActivity.this.openMenu();
            }
            @Override
            public void openService()
            {
                presenter.setServicesScreen();
            }
        });
    }
    private void initServicesFragment()
    {
        ServicesFragment fragment = new ServicesFragment();
        servicesFragment = MVPFragment.setMVPFragment(fragment, new ServicesPresenter(fragment), new ServicesContract.Behaviour()
        {
            @Override
            public void openMenu()
            {
                GeneralActivity.this.openMenu();
            }
        });
    }
    private void initDoctorsFragment()
    {
        DoctorsFragment fragment = new DoctorsFragment();
        doctorsFragment = MVPFragment.setMVPFragment(fragment, new DoctorsPresenter(fragment), new DoctorsContract.Behaviour()
        {
            @Override
            public void openMenu()
            {
                GeneralActivity.this.openMenu();
            }
        });
    }

    @Override
    public void showMainScreen()
    {
        replaceFragment(mainFragment);
        closeMenu();
    }

    @Override
    public void showServicesScreen()
    {
        replaceFragment(servicesFragment);
        closeMenu();
    }

    @Override
    public void showDoctorsScreen()
    {
        replaceFragment(doctorsFragment);
        closeMenu();
    }

    private void openMenu()
    {
        menu.setVisibility(View.VISIBLE);
    }

    private void closeMenu()
    {
        menu.setVisibility(View.GONE);
    }

    private void replaceFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction()
                                   .replace(R.id.main_frame, fragment)
                                   .commit();
    }
}