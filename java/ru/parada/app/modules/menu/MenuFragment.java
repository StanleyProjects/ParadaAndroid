package ru.parada.app.modules.menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.parada.app.R;
import ru.parada.app.contracts.MenuContract;

public class MenuFragment
        extends Fragment
    implements MenuContract.View
{
    static public MenuFragment newInstanse(MenuFragmentListener l)
    {
        MenuFragment fragment = new MenuFragment();
        fragment.listener = l;
        return fragment;
    }

    private MenuFragmentListener listener;
    private MenuContract.Presenter presenter;
    private View.OnClickListener clickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch(v.getId())
            {
                case R.id.main:
                    presenter.openMain();
                    break;
                case R.id.service:
                    presenter.openService();
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.menu_fragment, container, false);
        initViews(v);
        init();
        return v;
    }
    private void initViews(View v)
    {
        v.findViewById(R.id.main).setOnClickListener(clickListener);
        v.findViewById(R.id.service).setOnClickListener(clickListener);
    }
    private void init()
    {
        presenter = new MenuPresenter(this);
    }

    @Override
    public void setMain()
    {
        listener.openMain();
    }

    @Override
    public void setService()
    {
        listener.openService();
    }

    public interface MenuFragmentListener
    {
        void openMain();
        void openService();
    }
}