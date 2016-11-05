package ru.parada.app.modules.prices.adapter;

import android.content.Context;
import android.view.ViewGroup;

import ru.parada.app.contracts.PricesContract;
import ru.parada.app.units.ModelDataAdapter;

public class PricesAdapter
        extends ModelDataAdapter<PricesAdapterHolder, PricesContract.Model, PricesAdapterListener>
{
    public PricesAdapter(Context c, PricesAdapterListener l)
    {
        super(c, l);
    }

    @Override
    protected void setData(PricesAdapterHolder holder, PricesContract.Model item)
    {
        holder.setTitle(item.getTitle());
        holder.setValue(item.getValue());
        holder.drawOdd(holder.getAdapterPosition() % 2 == 0);
    }

    @Override
    public PricesAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new PricesAdapterHolder(getContext(), parent);
    }
}