package ru.parada.app.modules.servicesprices.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.parada.app.R;
import ru.parada.app.units.AdapterHolder;

public class ServicePriceHolder
    extends AdapterHolder
{
    private TextView title;

    public ServicePriceHolder(Context context, ViewGroup parent)
    {
        super(context, parent, R.layout.service_price_holder);
        title = (TextView)itemView.findViewById(R.id.title);
    }

    public void setTitle(String t)
    {
        title.setText(t);
    }
}