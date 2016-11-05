package ru.parada.app.modules.services;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ru.parada.app.R;
import ru.parada.app.contracts.ServiceDetailContract;
import ru.parada.app.contracts.ServicesContract;
import ru.parada.app.core.ServicesCore;
import ru.parada.app.modules.servicedetail.ServiceDetailFragment;
import ru.parada.app.units.ListModel;
import ru.parada.app.units.MVPFragment;

public class ServicesFragment
        extends MVPFragment<ServicesContract.Presenter, ServicesContract.Behaviour>
        implements ServicesContract.View
{
    static public Fragment newInstanse(ServicesContract.Behaviour behaviour)
    {
        ServicesFragment fragment = new ServicesFragment();
        fragment.setBehaviour(behaviour);
        return fragment;
    }

    private Fragment detailFragment;

    private RecyclerView list;

    private ServicesAdapter adapter;

    @Override
    public void onResume()
    {
        super.onResume();
        if(detailFragment != null && getChildFragmentManager().getBackStackEntryCount() == 0)
        {
            showDetail();
        }
    }

    @Override
    protected ServicesContract.Presenter setPresenter()
    {
        return new ServicesPresenter(this);
    }

    @Override
    protected int setContentView()
    {
        return R.layout.service_fragment;
    }

    @Override
    protected void initViews(View v)
    {
        setClickListener(v.findViewById(R.id.menu));
        list = (RecyclerView)v.findViewById(R.id.list);
    }

    @Override
    protected View.OnClickListener setClickListener()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switch(v.getId())
                {
                    case R.id.menu:
                        getBehaviour().openMenu();
                        break;
                }
            }
        };
    }

    @Override
    protected void init()
    {
        adapter = new ServicesAdapter(getActivity(), new ServicesAdapterListener()
        {
            @Override
            public void getService(int id)
            {
                detailFragment = ServiceDetailFragment.newInstanse(new ServiceDetailContract.Behaviour()
                {
                    @Override
                    public void back()
                    {
                        getChildFragmentManager().popBackStack();
                        detailFragment = null;
                    }
                }, id);
                showDetail();
            }
        });
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        getPresenter().updateServices();
        getPresenter().loadServices();
    }

    @Override
    public void updateServices(final ListModel<ServicesCore.Model> data)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                adapter.swapData(data);
                adapter.notifyDataSetChanged();
            }
        }, 0);
    }

    public void showDetail()
    {
        getChildFragmentManager().beginTransaction()
                                 .add(R.id.detail_frame, detailFragment)
                                 .addToBackStack(ServiceDetailFragment.class.getName())
                                 .commit();
    }
}