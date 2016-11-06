package ru.parada.app.modules.events;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import ru.parada.app.R;
import ru.parada.app.contracts.ActionDetailContract;
import ru.parada.app.contracts.ActionsContract;
import ru.parada.app.contracts.EventsContract;
import ru.parada.app.contracts.NewsContract;
import ru.parada.app.contracts.OneOfNewsDetailContract;
import ru.parada.app.modules.actiondetail.ActionDetailFragment;
import ru.parada.app.modules.actions.ActionsFragment;
import ru.parada.app.modules.news.NewsFragment;
import ru.parada.app.modules.oneofnewsdetail.OneOfNewsDetailFragment;
import ru.parada.app.units.CallbackConnector;
import ru.parada.app.units.MVPFragment;

public class EventsFragment
    extends MVPFragment<EventsContract.Presenter, EventsContract.Behaviour>
    implements EventsContract.View
{
    static public MVPFragment newInstanse(EventsContract.Behaviour behaviour, CallbackConnector<EventsContract.Callback> cconnector)
    {
        final EventsFragment fragment = new EventsFragment();
        fragment.setBehaviour(behaviour);
        cconnector.setCallback(new EventsContract.Callback()
        {
            @Override
            public void setNews()
            {
                fragment.detailFragment = null;
                fragment.runAfterResume(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        fragment.getPresenter().setNews();
                    }
                });
            }
            @Override
            public void setOneOfNews(final int id)
            {
                fragment.detailFragment = null;
                fragment.runAfterResume(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        fragment.getPresenter().setNews();
                        fragment.showOneOfNews(id);
                    }
                });
            }
        });
        return fragment;
    }

    private Fragment currentFragment;
    private final Fragment newsFragment = NewsFragment.newInstanse(new NewsContract.Behaviour()
    {
        @Override
        public void getOneOfNews(int id)
        {
            showOneOfNews(id);
        }
    });
    private final Fragment actionsFragment = ActionsFragment.newInstanse(new ActionsContract.Behaviour()
    {
        @Override
        public void getAction(int id)
        {
            detailFragment = ActionDetailFragment.newInstanse(new ActionDetailContract.Behaviour()
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
    private Fragment detailFragment;

    private View news_active;
    private View actions_active;

    private int tabNormal;
    private int tabHighlight;

    @Override
    public void onResume()
    {
        super.onResume();
        replaceFragment();
        if(detailFragment != null && getChildFragmentManager().getBackStackEntryCount() == 0)
        {
            showDetail();
        }
        Log.e(getClass().getName(), "onResume");
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
                    case R.id.set_news:
                        getPresenter().setNews();
                        break;
                    case R.id.set_actions:
                        getPresenter().setActions();
                        break;
                }
            }
        };
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
        currentFragment = newsFragment;
        replaceFragment();
    }

    @Override
    public void showActions()
    {
        actions_active.setBackgroundColor(tabHighlight);
        news_active.setBackgroundColor(tabNormal);
        currentFragment = actionsFragment;
        replaceFragment();
    }

    private void replaceFragment()
    {
        getChildFragmentManager().beginTransaction()
                                   .replace(R.id.events, currentFragment)
                                   .commit();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        Log.e(getClass().getName(), "onAttach " + context);
    }
    @Override
    public void onDetach()
    {
        super.onDetach();
        Log.e(getClass().getName(), "onDetach");
    }

    public void showOneOfNews(int id)
    {
        detailFragment = OneOfNewsDetailFragment.newInstanse(new OneOfNewsDetailContract.Behaviour()
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
    public void showDetail()
    {
        getChildFragmentManager().beginTransaction()
                                 .add(R.id.detail_frame, detailFragment)
                                 .addToBackStack(detailFragment.getClass().getName())
                                 .commit();
    }
}