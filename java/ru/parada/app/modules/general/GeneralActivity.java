package ru.parada.app.modules.general;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import ru.parada.app.R;
import ru.parada.app.contracts.GeneralContract;
import ru.parada.app.modules.doctors.DoctorsFragment;
import ru.parada.app.modules.doctors.DoctorsFragmentListener;
import ru.parada.app.modules.main.MainFragment;
import ru.parada.app.modules.main.MainFragmentListener;
import ru.parada.app.modules.menu.MenuFragment;
import ru.parada.app.modules.menu.MenuFragmentListener;
import ru.parada.app.modules.service.ServicesFragment;
import ru.parada.app.modules.service.ServicesFragmentListener;

public class GeneralActivity
        extends AppCompatActivity
    implements GeneralContract.View
{
    private final MenuFragment menuFragment = MenuFragment.newInstanse(new MenuFragmentListener()
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
    private final MainFragment mainFragment = MainFragment.newInstanse(new MainFragmentListener()
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
    private final ServicesFragment servicesFragment = ServicesFragment.newInstanse(new ServicesFragmentListener()
    {
        @Override
        public void openMenu()
        {
            GeneralActivity.this.openMenu();
        }
    });
    private final DoctorsFragment doctorsFragment = DoctorsFragment.newInstanse(new DoctorsFragmentListener()
    {
        @Override
        public void openMenu()
        {
            GeneralActivity.this.openMenu();
        }
    });

    private DrawerLayout main_drawer;

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
        main_drawer = (DrawerLayout)findViewById(R.id.main_drawer);
    }
    private void init()
    {
        presenter = new GeneralPresenter(this);
        getSupportFragmentManager().beginTransaction().add(R.id.main_frame, mainFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.menu_frame, menuFragment).commit();
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
        main_drawer.openDrawer(GravityCompat.START);
    }
    private void closeMenu()
    {
        main_drawer.closeDrawer(GravityCompat.START);
    }

    private void replaceFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment).commit();
    }
}