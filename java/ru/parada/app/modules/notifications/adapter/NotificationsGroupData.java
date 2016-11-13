package ru.parada.app.modules.notifications.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import ru.parada.app.core.NotificationsCore;
import ru.parada.app.units.GroupData;

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
        setNormal((NotificationHolder)holder, (NotificationsCore.Model)getItem(position).getData());
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(Context context, ViewGroup parent, int viewType)
    {
        return new NotificationHolder(context, parent);
    }

    private void setNormal(NotificationHolder holder, NotificationsCore.Model data)
    {
        holder.setMessage(data.getMessage());
    }
}