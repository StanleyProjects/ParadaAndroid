package ru.parada.app.modules.services.list;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ru.parada.app.App;
import ru.parada.app.R;
import ru.parada.app.contracts.services.ServiceDetailContract;
import ru.parada.app.contracts.services.ServicesContract;
import ru.parada.app.contracts.requestcall.RequestCallCheckContract;
import ru.parada.app.contracts.requestcall.RequestCallContract;
import ru.parada.app.contracts.subscribe.SubscribeCheckContract;
import ru.parada.app.contracts.subscribe.SubscribeContract;
import ru.parada.app.core.RequestCallCore;
import ru.parada.app.core.ServicesCore;
import ru.parada.app.core.SubscribeCore;
import ru.parada.app.modules.requestcall.check.RequestCallCheckFragment;
import ru.parada.app.modules.requestcall.screen.RequestCallFragment;
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
    private Fragment getUserDataFragment;
    private Fragment sendUserDataFragment;

    private RecyclerView list;

    private boolean load;
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
            getUserDataFragment = SubscribeFragment.newInstanse(subscribeBehaviour);
            addSubscreen(getUserDataFragment);
        }
        @Override
        public void requestcall()
        {
            getUserDataFragment = RequestCallFragment.newInstanse(requestCallBehaviour);
            addSubscreen(getUserDataFragment);
        }
    };
    private final SubscribeContract.Behaviour subscribeBehaviour = new SubscribeContract.Behaviour()
    {
        @Override
        public void back()
        {
            getChildFragmentManager().popBackStack();
            getUserDataFragment = null;
        }
        @Override
        public void send(SubscribeCore.Model data)
        {
            sendUserDataFragment = SubscribeCheckFragment.newInstanse(subscribeCheckBehaviour, data);
            addSubscreen(sendUserDataFragment);
        }
    };
    private final SubscribeCheckContract.Behaviour subscribeCheckBehaviour = new SubscribeCheckContract.Behaviour()
    {
        @Override
        public void back()
        {
            getChildFragmentManager().popBackStack();
            sendUserDataFragment = null;
        }
        @Override
        public void sendSucess()
        {
            runAfterResume(new Runnable()
            {
                @Override
                public void run()
                {
                    resetScreenIndex();
                    getUserDataFragment = null;
                    sendUserDataFragment = null;
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            addSubscreen(detailFragment);
                        }
                    }, 200);
                }
            });
        }
    };
    private final RequestCallContract.Behaviour requestCallBehaviour = new RequestCallContract.Behaviour()
    {
        @Override
        public void back()
        {
            getChildFragmentManager().popBackStack();
            getUserDataFragment = null;
        }
        @Override
        public void send(RequestCallCore.Model data)
        {
            sendUserDataFragment = RequestCallCheckFragment.newInstanse(requestCallCheckBehaviour, data);
            addSubscreen(sendUserDataFragment);
        }
    };
    private final RequestCallCheckContract.Behaviour requestCallCheckBehaviour = new RequestCallCheckContract.Behaviour()
    {
        @Override
        public void back()
        {
            getChildFragmentManager().popBackStack();
            sendUserDataFragment = null;
        }
        @Override
        public void sendSucess()
        {
            runAfterResume(new Runnable()
            {
                @Override
                public void run()
                {
                    resetScreenIndex();
                    getUserDataFragment = null;
                    sendUserDataFragment = null;
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            addSubscreen(detailFragment);
                        }
                    }, 200);
                }
            });
        }
    };

    @Override
    protected void setScreens(int screen)
    {
        if(screen >= ServicesCore.Screens.DETAIL)
        {
            addSubscreen(detailFragment);
        }
        if(screen >= ServicesCore.Screens.GET_USER_DATA)
        {
            addSubscreen(getUserDataFragment);
        }
        if(screen >= ServicesCore.Screens.SEND_USER_DATA)
        {
            addSubscreen(sendUserDataFragment);
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
    protected void onClickView(int id)
    {
        switch(id)
        {
            case R.id.menu:
                getBehaviour().openMenu();
                break;
        }
    }

    @Override
    protected void init()
    {
        adapter = new ServicesAdapter(getActivity(), new ServicesAdapterListener()
        {
            @Override
            public void getService(final int id)
            {
                if(load || App.getComponent().getAndroidUtil().blockClick())
                {
                    return;
                }
                runAfterResume(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        resetScreenIndex();
                        detailFragment = ServiceDetailFragment.newInstanse(serviceDetailBehaviour, id);
                        addSubscreen(detailFragment);
                    }
                });
            }
        });
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        getPresenter().update();
        load = true;
        getPresenter().load();
    }

    @Override
    public void update(final ListModel<ServicesCore.Model> data)
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

    @Override
    public void load()
    {
        load = false;
    }
}