package ru.parada.app.modules.notifications;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;

import ru.parada.app.R;
import ru.parada.app.contracts.NotificationsContract;
import ru.parada.app.core.NotificationsCore;
import ru.parada.app.modules.notifications.adapter.NotificationsAdapterListener;
import ru.parada.app.modules.notifications.adapter.NotificationsGroupData;
import ru.parada.app.units.ArrayListModel;
import ru.parada.app.units.adapters.GroupAdapter;
import ru.parada.app.units.adapters.GroupModel;
import ru.parada.app.units.ListModel;
import ru.parada.app.units.MVPFragment;

public class NotificationsFragment
    extends MVPFragment<NotificationsContract.Presenter, NotificationsContract.Behaviour>
    implements NotificationsContract.View
{
    static public MVPFragment newInstanse(NotificationsContract.Behaviour behaviour)
    {
        NotificationsFragment fragment = new NotificationsFragment();
        fragment.setBehaviour(behaviour);
        return fragment;
    }

    private SwipeRefreshLayout swiperefresh;
    private RecyclerView list;

    private GroupAdapter adapter;
    private NotificationsGroupData notificationsGroupData;

    @Override
    protected NotificationsContract.Presenter setPresenter()
    {
        return new NotificationsPresenter(this);
    }

    @Override
    protected int setContentView()
    {
        return R.layout.notifications_fragment;
    }

    @Override
    protected void initViews(View v)
    {
        setClickListener(v.findViewById(R.id.menu));
        swiperefresh = (SwipeRefreshLayout)v.findViewById(R.id.swiperefresh);
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
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                getPresenter().load();
            }
        });
        swiperefresh.setColorSchemeResources(R.color.colorPrimary);
        notificationsGroupData = new NotificationsGroupData(new NotificationsAdapterListener()
        {
        });
        adapter = new GroupAdapter<>(getActivity(), notificationsGroupData);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        getPresenter().load();
    }

    @Override
    public void update(ListModel<NotificationsCore.Model> data)
    {
        ArrayList<GroupModel> groupData = new ArrayList<>();
        Date date = new Date(0);
        for(int i=0; i<data.getItemsCount(); i++)
        {
            final Date cDate = new Date(data.getItem(i).getDate() * 1000);
            if(date.getYear() != cDate.getYear() || date.getMonth() != cDate.getMonth() || date.getDate() != cDate.getDate())
            {
                date = cDate;
                groupData.add(new GroupModel<>(new NotificationsCore.GroupModel(){
                    @Override
                    public long getDate()
                    {
                        return cDate.getTime();
                    }
                }, NotificationsGroupData.ViewTypes.GROUP));
            }
            groupData.add(new GroupModel<>(data.getItem(i), NotificationsGroupData.ViewTypes.NORMAL));
        }
        final ArrayListModel<GroupModel> notifications = new ArrayListModel<>(groupData);
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                notificationsGroupData.swapData(notifications);
                adapter.notifyDataSetChanged();
                swiperefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void loadFail()
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                swiperefresh.setRefreshing(false);
            }
        });
    }
}