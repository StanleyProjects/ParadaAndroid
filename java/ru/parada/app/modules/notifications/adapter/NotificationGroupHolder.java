package ru.parada.app.modules.notifications.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;

import ru.parada.app.R;
import ru.parada.app.units.AdapterHolder;

public class NotificationGroupHolder
        extends AdapterHolder
{
    private TextView day_month;
    private TextView day_week;

    private String[] months;
    private String[] weekdays;

    public NotificationGroupHolder(Context context, ViewGroup parent)
    {
        super(context, parent, R.layout.notification_group_list_item);
        day_month = (TextView)itemView.findViewById(R.id.day_month);
        day_week = (TextView)itemView.findViewById(R.id.day_week);
        months = context.getResources().getStringArray(R.array.months);
        weekdays = context.getResources().getStringArray(R.array.weekdays);
    }

    public void setDay(long d)
    {
        Date time = new Date(d);
        day_month.setText(time.getDate() + " " + months[time.getMonth()]);
        day_week.setText(weekdays[time.getDay()]);
    }
}