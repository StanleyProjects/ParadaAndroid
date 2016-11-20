package ru.parada.app.units.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

public abstract class ModelAdapter<MODELHOLDER extends RecyclerView.ViewHolder, MODEL, LISTENER>
        extends RecyclerView.Adapter<MODELHOLDER>
{
    private Context context;
    private LISTENER listener;

    public ModelAdapter(Context c, LISTENER l)
    {
        this.context = c;
        this.listener = l;
    }

    @Override
    public void onBindViewHolder(MODELHOLDER holder, int position)
    {
        MODEL item = getItem(position);
        if(item != null)
        {
            setData(holder, item);
        }
    }

    protected abstract MODEL getItem(int position);
    protected abstract void setData(MODELHOLDER holder, MODEL item);

    protected Context getContext()
    {
        return context;
    }

    protected LISTENER getListener()
    {
        return listener;
    }
}