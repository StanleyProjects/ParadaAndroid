package ru.parada.app.modules.servicesprices.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.parada.app.R;
import ru.parada.app.units.AdapterHolder;

public class ServicePriceGroupHolder
    extends AdapterHolder
{
    private TextView name;

    public ServicePriceGroupHolder(Context context, ViewGroup parent)
    {
        super(context, parent, R.layout.service_price_group_holder);
        name = (TextView)itemView.findViewById(R.id.name);
    }

    public void setName(String n)
    {
        name.setText(n);
    }
}