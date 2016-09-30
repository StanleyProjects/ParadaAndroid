package ru.parada.app.modules.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.parada.app.R;

public class MainFragment
        extends Fragment
{
    static public MainFragment newInstanse(MainFragmentListener l)
    {
        MainFragment fragment = new MainFragment();
        fragment.listener = l;
        return fragment;
    }

    MainFragmentListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.main_fragment, container, false);
        initViews(v);
        init();
        return v;
    }
    private void initViews(View v)
    {
        v.findViewById(R.id.menu).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                listener.openMenu();
            }
        });
    }
    private void init()
    {

    }

    public interface MainFragmentListener
    {
        void openMenu();
    }
}