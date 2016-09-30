package ru.parada.app.modules.general;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.parada.app.R;

public class MenuFragment
        extends Fragment
{
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

    }
    private void init()
    {

    }
}