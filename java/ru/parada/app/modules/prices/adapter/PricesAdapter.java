package ru.parada.app.modules.prices.adapter;

import android.content.Context;
import android.view.ViewGroup;

import ru.parada.app.contracts.PricesContract;
import ru.parada.app.units.ListModel;
import ru.parada.app.units.ModelAdapter;

public class PricesAdapter
        extends ModelAdapter<PricesAdapterHolder, PricesContract.Model, PricesAdapterListener>
{
    private ListModel<PricesContract.Model> data;

    public PricesAdapter(Context c, PricesAdapterListener l)
    {
        super(c, l);
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
    }

    @Override
    public PricesAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new PricesAdapterHolder(getContext(), parent);
    }

    @Override
    public int getItemCount()
    {
        if(data == null)
        {
            return 0;
        }
        return data.getItemsCount();
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