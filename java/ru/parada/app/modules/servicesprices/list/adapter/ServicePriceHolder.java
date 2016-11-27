package ru.parada.app.modules.servicesprices.list.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.parada.app.R;
import ru.parada.app.units.AdapterHolder;

public class ServicePriceHolder
    extends AdapterHolder
{
    private TextView title;
    private TextView subtitle;

    public ServicePriceHolder(Context context, ViewGroup parent)
    {
        super(context, parent, R.layout.service_price_holder);
        title = (TextView)itemView.findViewById(R.id.title);
        subtitle = (TextView)itemView.findViewById(R.id.subtitle);
    }

    public void setTitle(String t)
    {
        title.setText(t);
    }
    public void setSubTitle(String s)
    {
        if(s.length() > 0)
        {
            subtitle.setVisibility(View.VISIBLE);
            subtitle.setText(s);
        }
        else
        {
            subtitle.setVisibility(View.GONE);
        }
    }
}