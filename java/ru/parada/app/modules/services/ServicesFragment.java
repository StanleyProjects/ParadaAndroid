package ru.parada.app.modules.services;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ru.parada.app.R;
import ru.parada.app.contracts.ServicesContract;
import ru.parada.app.units.ListModel;
import ru.parada.app.units.MVPFragment;

public class ServicesFragment
        extends MVPFragment<ServicesContract.Presenter, ServicesContract.Behaviour>
        implements ServicesContract.View
{
    static public ServicesFragment newInstanse(ServicesContract.Behaviour behaviour)
    {
        ServicesFragment fragment = new ServicesFragment();
        fragment.setBehaviour(behaviour);
        return fragment;
    }

    private RecyclerView list;

    private ServicesAdapter adapter;

    @Override
    public void onResume()
    {
        super.onResume();
        getPresenter().updateServices();
    }
    @Override
    public void onPause()
    {
        super.onPause();
        adapter.swapData(null);
    }

    @Override
    protected int setContentView()
    {
        return R.layout.service_fragment;
    }

    @Override
    protected ServicesContract.Presenter setPresenter()
    {
        return new ServicesPresenter(this);
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
        });
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        getPresenter().loadServices();
    }

    @Override
    public void updateServices(final ListModel<ServicesContract.ListItemModel> data)
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
}