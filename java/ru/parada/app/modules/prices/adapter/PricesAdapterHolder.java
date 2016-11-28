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

    private int ttl;
    private int ttlOdd;
    private int val;
    private int valOdd;

    private String pricePostfix;

    public PricesAdapterHolder(Context context, ViewGroup parent)
    {
        super(context, parent, R.layout.price_list_item);
        title = (TextView)itemView.findViewById(R.id.title);
        value = (TextView)itemView.findViewById(R.id.value);
        ttl = context.getResources().getColor(R.color.white);
        ttlOdd = context.getResources().getColor(R.color.graysuperlight);
        val = context.getResources().getColor(R.color.purplesuperalpha);
        valOdd = context.getResources().getColor(R.color.purplealpha);
        pricePostfix = context.getResources().getString(R.string.price_postfix);
    }

    public void setTitle(String t)
    {
        title.setText(t);
    }
    public void setValue(String v)
    {
        value.setText(v + " " + pricePostfix);
    }
    public void drawOdd(boolean odd)
    {
        if(odd)
        {
            title.setBackgroundColor(ttl);
            value.setBackgroundColor(val);
        }
        else
        {
            title.setBackgroundColor(ttlOdd);
            value.setBackgroundColor(valOdd);
        }
    }
}