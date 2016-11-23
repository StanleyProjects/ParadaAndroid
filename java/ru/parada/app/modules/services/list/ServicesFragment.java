package ru.parada.app.modules.services.list;

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
import ru.parada.app.modules.services.detail.ServiceDetailFragment;
import ru.parada.app.modules.services.list.adapter.ServicesAdapter;
import ru.parada.app.modules.services.list.adapter.ServicesAdapterListener;
import ru.parada.app.modules.subscribe.check.SubscribeCheckFragment;
import ru.parada.app.modules.subscribe.subscribescreen.SubscribeFragment;
import ru.parada.app.units.ListModel;
import ru.parada.app.units.MultiFragment;

public class ServicesFragment
        extends MultiFragment<ServicesContract.Presenter, ServicesContract.Behaviour>
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
            addSubscreen(subscribeFragment);
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
            addSubscreen(subscribeCheckFragment);
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
            getChildFragmentManager().popBackStack();
            subscribeCheckFragment = null;
            getChildFragmentManager().popBackStack();
            subscribeFragment = null;
        }
    };

    @Override
    protected void setScreens(int screen)
    {
        if(screen >= ServicesCore.Screens.DETAIL)
        {
            addSubscreen(detailFragment);
        }
        if(screen >= ServicesCore.Screens.SUBSCRIBE)
        {
            addSubscreen(subscribeFragment);
        }
        if(screen >= ServicesCore.Screens.SUBSCRIBE_CHECK)
        {
            addSubscreen(subscribeCheckFragment);
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
        return R.layout.services_fragment;
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
                addSubscreen(detailFragment);
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
        });
    }

    private void addSubscreen(Fragment fragment)
    {
        getChildFragmentManager().beginTransaction()
                                 .add(R.id.subscreen, fragment)
                                 .addToBackStack(fragment.getClass().getName())
                                 .commit();
    }
}