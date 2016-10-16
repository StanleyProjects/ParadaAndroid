package ru.parada.app.units;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public class GroupAdapter<DATA extends GroupData, LISTENER>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context context;
    private DATA data;
    private LISTENER listener;

    public GroupAdapter(Context c, DATA d, LISTENER l)
    {
        context = c;
        data = d;
        listener = l;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return data.onCreateViewHolder(context, parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        data.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemViewType(int position)
    {
        return data.getItemViewType(position);
    }
    @Override
    public int getItemCount()
    {
        return data.getItemCount();
    }
}