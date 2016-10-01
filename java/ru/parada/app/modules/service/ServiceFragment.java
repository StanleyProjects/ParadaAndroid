package ru.parada.app.modules.service;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.parada.app.R;
import ru.parada.app.contracts.ServiceContract;

public class ServiceFragment
        extends Fragment
    implements ServiceContract.View
{
    static public ServiceFragment newInstanse(ServiceFragmentListener l)
    {
        ServiceFragment fragment = new ServiceFragment();
        fragment.listener = l;
        return fragment;
    }

    private ServiceContract.Presenter presenter;
    private ServiceFragmentListener listener;
    private View.OnClickListener clickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch(v.getId())
            {
                case R.id.menu:
                    listener.openMenu();
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.service_fragment, container, false);
        initViews(v);
        init();
        return v;
    }
    private void initViews(View v)
    {
        v.findViewById(R.id.menu).setOnClickListener(clickListener);
    }
    private void init()
    {
        presenter = new ServicePresenter(this);
        presenter.loadServices();
    }

    @Override
    public void updateServices()
    {

    }

    public interface ServiceFragmentListener
    {
        void openMenu();
    }
}