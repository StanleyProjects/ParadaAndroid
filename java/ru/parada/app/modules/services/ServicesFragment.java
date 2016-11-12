package ru.parada.app.modules.services;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ru.parada.app.R;
import ru.parada.app.contracts.ServiceDetailContract;
import ru.parada.app.contracts.ServicesContract;
import ru.parada.app.contracts.subscribe.SubscribeCheckContract;
import ru.parada.app.contracts.subscribe.SubscribeContract;
import ru.parada.app.core.ServicesCore;
import ru.parada.app.core.SubscribeCore;
import ru.parada.app.modules.servicedetail.ServiceDetailFragment;
import ru.parada.app.modules.services.adapter.ServicesAdapter;
import ru.parada.app.modules.services.adapter.ServicesAdapterListener;
import ru.parada.app.modules.subscribe.subscribecheck.SubscribeCheckFragment;
import ru.parada.app.modules.subscribe.subscribescreen.SubscribeFragment;
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
    private Fragment subscribeFragment;
    private Fragment subscribeCheckFragment;

    private RecyclerView list;

    private ServicesAdapter adapter;
    private final ServiceDetailContract.Behaviour serviceDetailBehaviour = new ServiceDetailContract.Behaviour()
    {
        @Override
        public void back()
        {
            getChildFragmentManager().popBackStack();
            detailFragment = null;
        }
        @Override
        public void subscribe()
        {
            subscribeFragment = SubscribeFragment.newInstanse(subscribeBehaviour);
            showSubscribeScreen();
        }
        @Override
        public void call()
        {
        }
    };
    private final SubscribeContract.Behaviour subscribeBehaviour = new SubscribeContract.Behaviour()
    {
        @Override
        public void back()
        {
            getChildFragmentManager().popBackStack();
            subscribeFragment = null;
        }
        @Override
        public void send(SubscribeCore.Model data)
        {
            subscribeCheckFragment = SubscribeCheckFragment.newInstanse(subscribeCheckBehaviour, data);
            showSubscribeCheckScreen();
        }
    };
    private final SubscribeCheckContract.Behaviour subscribeCheckBehaviour = new SubscribeCheckContract.Behaviour()
    {
        @Override
        public void back()
        {
            getChildFragmentManager().popBackStack();
            subscribeCheckFragment = null;
        }
        @Override
        public void sendSucess()
        {

        }
    };

    @Override
    public void onResume()
    {
        super.onResume();
        if(getChildFragmentManager().getBackStackEntryCount() == 0)
        {
            if(detailFragment != null)
            {
                showDetailScreen();
            }
            if(subscribeFragment != null)
            {
                showSubscribeScreen();
            }
            if(subscribeCheckFragment != null)
            {
                showSubscribeCheckScreen();
            }
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
                detailFragment = ServiceDetailFragment.newInstanse(serviceDetailBehaviour, id);
                showDetailScreen();
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

    private void showDetailScreen()
    {
        showSubscreen(detailFragment);
    }
    private void showSubscribeScreen()
    {
        showSubscreen(subscribeFragment);
    }
    private void showSubscribeCheckScreen()
    {
        showSubscreen(subscribeCheckFragment);
    }
    private void showSubscreen(Fragment fragment)
    {
        getChildFragmentManager().beginTransaction()
                                 .add(R.id.subscreen, fragment)
                                 .addToBackStack(fragment.getClass().getName())
                                 .commit();
    }
}