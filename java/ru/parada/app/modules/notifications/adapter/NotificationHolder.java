package ru.parada.app.modules.notifications.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import ru.parada.app.R;
import ru.parada.app.units.AdapterHolder;

public class NotificationHolder
        extends AdapterHolder
{
    private TextView message;
    private TextView date;
    private ImageView read;

    public NotificationHolder(Context context, ViewGroup parent)
    {
        super(context, parent, R.layout.notification_list_item);
        message = (TextView)itemView.findViewById(R.id.message);
        date = (TextView)itemView.findViewById(R.id.date);
        read = (ImageView)itemView.findViewById(R.id.read);
    }

    public void setMessage(String m)
    {
        message.setText(m);
    }
    public void setRead(boolean r)
    {
        if(r)
        {
            read.setImageResource(R.drawable.gray_circle);
        }
        else
        {
            read.setImageResource(R.drawable.green_circle);
        }
    }
    public void setDate(long d)
    {
        Date time = new Date(d);
        int h;
        int m;
        String dt = "";
        h = time.getHours();
        if(h<10)
        {
            dt +="0";
        }
        dt += h + ":";
        m = time.getMinutes();
        if(m<10)
        {
            dt +="0";
        }
        dt += m + "";
        date.setText(dt);
    }
}