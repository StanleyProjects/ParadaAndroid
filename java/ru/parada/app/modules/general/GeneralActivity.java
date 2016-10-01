package ru.parada.app.modules.general;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import ru.parada.app.R;
import ru.parada.app.contracts.GeneralContract;
import ru.parada.app.modules.main.MainFragment;

public class GeneralActivity
        extends AppCompatActivity
    implements GeneralContract.View
{
    private final MenuFragment menuFragment = new MenuFragment();
    private final MainFragment mainFragment = MainFragment.newInstanse(new MainFragment.MainFragmentListener()
    {
        @Override
        public void openMenu()
        {
            main_drawer.openDrawer(GravityCompat.START);
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
    }

    private void replaceFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment).commit();
    }
}