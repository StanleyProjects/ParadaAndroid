package ru.parada.app.units;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public abstract class HeadFootAdapter<MODELHOLDER extends RecyclerView.ViewHolder, MODEL, LISTENER>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context context;
    private LISTENER listener;

    public HeadFootAdapter(Context c, LISTENER l)
    {
        this.context = c;
        this.listener = l;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(viewType == ViewTypes.HEADER)
        {
            return createHeaderHolder(parent);
        }
        else if(viewType == ViewTypes.FOOTER)
        {
            return createFooterHolder(parent);
        }
        return createNormalHolder(parent);
    }

    protected abstract RecyclerView.ViewHolder createHeaderHolder(ViewGroup parent);
    protected abstract RecyclerView.ViewHolder createFooterHolder(ViewGroup parent);
    protected abstract MODELHOLDER createNormalHolder(ViewGroup parent);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if(getItemViewType(position) == ViewTypes.HEADER)
        {
            setHeader(holder);
        }
        else if(getItemViewType(position) == ViewTypes.FOOTER)
        {
            setFooter(holder);
        }
        else
        {
            setData((MODELHOLDER)holder, getItem(position - 1));
        }
    }

    protected void setHeader(RecyclerView.ViewHolder holder)
    {

    }
    protected void setFooter(RecyclerView.ViewHolder holder)
    {

    }

    @Override
    public int getItemViewType(int position)
    {
        if(position == 0)
        {
            return ViewTypes.HEADER;
        }
        else if(position == getRealCount() + 1)
        {
            return ViewTypes.FOOTER;
        }
        return ViewTypes.NORMAL;
    }

    @Override
    public int getItemCount()
    {
        return getRealCount() + 2;
    }

    protected abstract int getRealCount();

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

    protected interface ViewTypes
    {
        int HEADER = 1;
        int NORMAL = 2;
        int FOOTER = 3;
    }
}