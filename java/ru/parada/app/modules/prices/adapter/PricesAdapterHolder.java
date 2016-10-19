package ru.parada.app.modules.prices.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.parada.app.R;
import ru.parada.app.units.AdapterHolder;

public class PricesAdapterHolder
        extends AdapterHolder
{
    private TextView title;
    private TextView value;

    public PricesAdapterHolder(Context context, ViewGroup parent)
    {
        super(context, parent, R.layout.price_list_item);
        title = (TextView)itemView.findViewById(R.id.title);
        value = (TextView)itemView.findViewById(R.id.value);
    }

    public void setTitle(String t)
    {
        title.setText(t);
    }
    public void setValue(String v)
    {
        value.setText(v);
    }
}