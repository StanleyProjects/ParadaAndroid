package ru.parada.app.modules.notifications;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import ru.parada.app.R;
import ru.parada.app.contracts.NotificationsContract;
import ru.parada.app.modules.notifications.adapter.NotificationsAdapterListener;
import ru.parada.app.modules.notifications.adapter.NotificationsGroupData;
import ru.parada.app.units.ArrayListModel;
import ru.parada.app.units.GroupAdapter;
import ru.parada.app.units.GroupModel;
import ru.parada.app.units.ListModel;
import ru.parada.app.units.MVPFragment;

public class NotificationsFragment
    extends MVPFragment<NotificationsContract.Presenter, NotificationsContract.Behaviour>
    implements NotificationsContract.View
{
    static public NotificationsFragment newInstanse(NotificationsContract.Behaviour behaviour)
    {
        NotificationsFragment fragment = new NotificationsFragment();
        fragment.setBehaviour(behaviour);
        return fragment;
    }

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
        notificationsGroupData = new NotificationsGroupData(new NotificationsAdapterListener()
        {
        });
        adapter = new GroupAdapter<>(getActivity(), notificationsGroupData);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        getPresenter().update();
        getPresenter().load();
    }

    @Override
    public void update(ListModel<NotificationsContract.Model> data)
    {
        ArrayList<GroupModel> groupData = new ArrayList<>();
        for(int i=0; i<data.getItemsCount(); i++)
        {
            groupData.add(new GroupModel<>(data.getItem(i), 0));
        }
        final ArrayListModel<GroupModel> notifications = new ArrayListModel<>(groupData);
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                notificationsGroupData.swapData(notifications);
                adapter.notifyDataSetChanged();
            }
        });
    }
}