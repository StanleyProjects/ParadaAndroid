package ru.parada.app.modules.notifications.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import ru.parada.app.core.NotificationsCore;
import ru.parada.app.units.adapters.GroupData;

public class NotificationsGroupData
        extends GroupData<NotificationsAdapterListener>
{
    public NotificationsGroupData(NotificationsAdapterListener l)
    {
        super(l);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        switch(getItemViewType(position))
        {
            case ViewTypes.GROUP:
                setGroup((NotificationGroupHolder)holder, (NotificationsCore.GroupModel)getItem(position).getData());
                break;
            case ViewTypes.NORMAL:
                setNormal((NotificationHolder)holder, (NotificationsCore.Model)getItem(position).getData());
                break;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(Context context, ViewGroup parent, int viewType)
    {
        switch(viewType)
        {
            case ViewTypes.GROUP:
                return new NotificationGroupHolder(context, parent);
            case ViewTypes.NORMAL:
                return new NotificationHolder(context, parent);
        }
        return null;
    }

    private void setNormal(NotificationHolder holder, NotificationsCore.Model data)
    {
        holder.setMessage(data.getMessage());
    }
    private void setGroup(NotificationGroupHolder holder, NotificationsCore.GroupModel data)
    {
        holder.setDay(data.getDate());
    }

    public interface ViewTypes
    {
        int GROUP = 1;
        int NORMAL = 2;
    }
}