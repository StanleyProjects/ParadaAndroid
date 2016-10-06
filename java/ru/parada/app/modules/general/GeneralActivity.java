package ru.parada.app.modules.general;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import ru.parada.app.R;
import ru.parada.app.contracts.DoctorsContract;
import ru.parada.app.contracts.GeneralContract;
import ru.parada.app.contracts.MainContract;
import ru.parada.app.contracts.MenuContract;
import ru.parada.app.contracts.ServicesContract;
import ru.parada.app.modules.doctors.DoctorsFragment;
import ru.parada.app.modules.main.MainFragment;
import ru.parada.app.modules.menu.MenuFragment;
import ru.parada.app.modules.service.ServicesFragment;
import ru.parada.app.units.DrawerContainer;

public class GeneralActivity
        extends AppCompatActivity
        implements GeneralContract.View
{
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
    });
    private final Fragment mainFragment = MainFragment.newInstanse(new MainContract.Behaviour()
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
    private View menu;

    private GeneralContract.Presenter presenter;

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
//        drawerContainer = new DrawerContainer(this);
//        drawerContainer.setParentLayout(new FrameLayout(this));
//        drawerContainer.setDrawerLayout(new FrameLayout(this));
//        setContentView(R.layout.general_activity);
        setContentView(R.layout.general_drawer_activity);
//        setContentView(drawerContainer, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initViews();
        init();
    }

    private void initViews()
    {
//        menu = findViewById(R.id.menu);
        drawerContainer = (DrawerContainer)findViewById(R.id.main_drawer);
        drawerContainer.setDrawerLayout(findViewById(R.id.menu_frame));
//        findViewById(R.id.close_menu).setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                closeMenu();
//            }
//        });
    }

    private void init()
    {
        presenter = new GeneralPresenter(this);
        getSupportFragmentManager().beginTransaction()
//                                   .add(R.id.drawer_id, menuFragment)
//                                   .add(drawerContainer.getDrawerLayout().getId(), menuFragment)
                                   .add(R.id.menu_frame, menuFragment)
                                   .commit();
        showMainScreen();
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
//        onTouchEvent(ev);
        return super.onTouchEvent(ev);
//        return !needTouch(ev);
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
//        menu.setVisibility(View.VISIBLE);
        drawerContainer.openDrawer();
    }

    private void closeMenu()
    {
//        menu.setVisibility(View.GONE);
        drawerContainer.closeDrawer();
    }

    private void replaceFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction()
//                                   .replace(drawerContainer.getParentLayout().getId(), fragment)
                                   .replace(R.id.main_frame, fragment)
                                   .commit();
    }
}