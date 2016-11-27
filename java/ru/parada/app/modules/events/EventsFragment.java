package ru.parada.app.modules.events;

import android.support.v4.app.Fragment;
import android.view.View;

import ru.parada.app.App;
import ru.parada.app.R;
import ru.parada.app.contracts.actions.ActionDetailContract;
import ru.parada.app.contracts.actions.ActionsContract;
import ru.parada.app.contracts.EventsContract;
import ru.parada.app.contracts.news.NewsContract;
import ru.parada.app.contracts.news.OneOfNewsDetailContract;
import ru.parada.app.contracts.requestcall.RequestCallCheckContract;
import ru.parada.app.contracts.requestcall.RequestCallContract;
import ru.parada.app.contracts.subscribe.SubscribeCheckContract;
import ru.parada.app.contracts.subscribe.SubscribeContract;
import ru.parada.app.core.EventsCore;
import ru.parada.app.core.RequestCallCore;
import ru.parada.app.core.SubscribeCore;
import ru.parada.app.modules.actions.detail.ActionDetailFragment;
import ru.parada.app.modules.actions.list.ActionsFragment;
import ru.parada.app.modules.actions.list.adapter.ActionsAdapterListener;
import ru.parada.app.modules.news.list.NewsFragment;
import ru.parada.app.modules.news.detail.OneOfNewsDetailFragment;
import ru.parada.app.modules.requestcall.check.RequestCallCheckFragment;
import ru.parada.app.modules.requestcall.screen.RequestCallFragment;
import ru.parada.app.modules.subscribe.check.SubscribeCheckFragment;
import ru.parada.app.modules.subscribe.subscribescreen.SubscribeFragment;
import ru.parada.app.units.CallbackConnector;
import ru.parada.app.units.MultiFragment;

public class EventsFragment
    extends MultiFragment<EventsContract.Presenter, EventsContract.Behaviour>
    implements EventsContract.View
{
    static public Fragment newInstanse(EventsContract.Behaviour behaviour, CallbackConnector<EventsContract.Callback> cconnector)
    {
        final EventsFragment fragment = new EventsFragment();
        fragment.setBehaviour(behaviour);
        cconnector.setCallback(new EventsContract.Callback()
        {
            @Override
            public void setNews()
            {
                fragment.runAfterResume(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        fragment.resetScreenIndex();
                        fragment.getPresenter().setNews();
                    }
                });
            }
            @Override
            public void setOneOfNews(final int id)
            {
                fragment.runAfterResume(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        fragment.resetScreenIndex();
                        fragment.getPresenter().setNews();
                        fragment.showOneOfNews(id);
                    }
                });
            }
        });
        return fragment;
    }

    private Fragment listFragment;
    private final Fragment newsFragment = NewsFragment.newInstanse(new NewsContract.Behaviour()
    {
        @Override
        public void getOneOfNews(int id)
        {
            if(App.getComponent().getAndroidUtil().blockClick())
            {
                return;
            }
            showOneOfNews(id);
        }
    });
    private final Fragment actionsFragment = ActionsFragment.newInstanse(new ActionsContract.Behaviour()
    {
        @Override
        public void getAction(final int id)
        {
            if(App.getComponent().getAndroidUtil().blockClick())
            {
                return;
            }
            runAfterResume(new Runnable()
            {
                @Override
                public void run()
                {
                    resetScreenIndex();
                    detailFragment = ActionDetailFragment.newInstanse(actionDetailContractBehaviour, id);
                    addSubscreen(detailFragment);
                }
            });
        }
    });
    private Fragment detailFragment;
    private Fragment getUserDataFragment;
    private Fragment sendUserDataFragment;

    private final OneOfNewsDetailContract.Behaviour oneOfNewsDetailBehaviour = new OneOfNewsDetailContract.Behaviour()
    {
        @Override
        public void back()
        {
            getChildFragmentManager().popBackStack();
            detailFragment = null;
        }
    };
    private final ActionDetailContract.Behaviour actionDetailContractBehaviour = new ActionDetailContract.Behaviour()
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

    private View news_active;
    private View actions_active;

    private int tabNormal;
    private int tabHighlight;

    @Override
    protected void setScreens(int screen)
    {
        if(screen >= EventsCore.Screens.NEWS_or_ACTIONS)
        {
            replaceList();
        }
        if(screen >= EventsCore.Screens.ONEOFNEWS_or_ACTION)
        {
            addSubscreen(detailFragment);
        }
        if(screen >= EventsCore.Screens.GET_USER_DATA)
        {
            addSubscreen(getUserDataFragment);
        }
        if(screen >= EventsCore.Screens.SEND_USER_DATA)
        {
            addSubscreen(sendUserDataFragment);
        }
    }
    @Override
    protected int beginScreenIndex()
    {
        return EventsCore.Screens.NEWS_or_ACTIONS;
    }

    @Override
    protected EventsContract.Presenter setPresenter()
    {
        return new EventsPresenter(this);
    }

    @Override
    protected int setContentView()
    {
        return R.layout.events_fragment;
    }

    @Override
    protected void initViews(View v)
    {
        setClickListener(v.findViewById(R.id.menu),
                v.findViewById(R.id.set_news),
                v.findViewById(R.id.set_actions));
        news_active = v.findViewById(R.id.news_active);
        actions_active = v.findViewById(R.id.actions_active);
    }

    @Override
    protected void onClickView(int id)
    {
        switch(id)
        {
            case R.id.menu:
                getBehaviour().openMenu();
                break;
            case R.id.set_news:
                getPresenter().setNews();
                break;
            case R.id.set_actions:
                getPresenter().setActions();
                break;
        }
    }

    @Override
    protected void init()
    {
        tabNormal = getActivity().getResources().getColor(R.color.colorPrimaryDark);
        tabHighlight = getActivity().getResources().getColor(R.color.purplebright);
        showNews();
    }

    @Override
    public void showNews()
    {
        news_active.setBackgroundColor(tabHighlight);
        actions_active.setBackgroundColor(tabNormal);
        listFragment = newsFragment;
        replaceList();
    }

    @Override
    public void showActions()
    {
        actions_active.setBackgroundColor(tabHighlight);
        news_active.setBackgroundColor(tabNormal);
        listFragment = actionsFragment;
        replaceList();
    }

    private void replaceList()
    {
        getChildFragmentManager().beginTransaction()
                                 .replace(R.id.events, listFragment)
                                 .commit();
    }

    public void showOneOfNews(final int id)
    {
        runAfterResume(new Runnable()
        {
            @Override
            public void run()
            {
                resetScreenIndex();
                detailFragment = OneOfNewsDetailFragment.newInstanse(oneOfNewsDetailBehaviour, id);
                addSubscreen(detailFragment);
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