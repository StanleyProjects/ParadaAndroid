package ru.parada.app.units;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

public abstract class ModelDataAdapter<MODELHOLDER extends RecyclerView.ViewHolder, MODEL, LISTENER>
        extends ModelAdapter<MODELHOLDER, MODEL, LISTENER>
{
    private ListModel<MODEL> data;

    public ModelDataAdapter(Context c, LISTENER l)
    {
        super(c, l);
    }

    @Override
    protected MODEL getItem(int position)
    {
        return data.getItem(position);
    }

    protected ListModel<MODEL> getData()
    {
        return data;
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

    public void swapData(ListModel<MODEL> d)
    {
        if(this.data != null)
        {
            this.data.clear();
        }
        this.data = d;
    }
}