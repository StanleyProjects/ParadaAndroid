package ru.parada.app.modules.prices.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import ru.parada.app.contracts.PricesContract;
import ru.parada.app.units.ListModel;
import ru.parada.app.units.adapters.FooterAdapter;

public class PricesFooterAdapter
    extends FooterAdapter<PricesAdapterHolder, PricesContract.Model, PricesAdapterListener>
{
    private ListModel<PricesContract.Model> data;

    public PricesFooterAdapter(Context c, PricesAdapterListener l)
    {
        super(c, l);
    }

    @Override
    protected RecyclerView.ViewHolder createFooterHolder(ViewGroup parent)
    {
        return new FooterHolder(getContext(), parent);
    }

    @Override
    protected PricesAdapterHolder createNormalHolder(ViewGroup parent)
    {
        return new PricesAdapterHolder(getContext(), parent);
    }

    @Override
    protected int getRealCount()
    {
        if(data == null)
        {
            return 0;
        }
        return data.getItemsCount();
    }

    @Override
    protected PricesContract.Model getItem(int position)
    {
        return data.getItem(position);
    }

    @Override
    protected void setData(PricesAdapterHolder holder, PricesContract.Model item)
    {
        holder.setTitle(item.getTitle());
        holder.setValue(item.getValue());
        holder.drawOdd(holder.getAdapterPosition() % 2 == 0);
    }

    public void swapData(ListModel<PricesContract.Model> d)
    {
        if(this.data != null)
        {
            this.data.clear();
        }
        this.data = d;
    }
}