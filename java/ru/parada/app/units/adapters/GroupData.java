package ru.parada.app.units.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import ru.parada.app.units.ListModel;

public abstract class GroupData<LISTENER>
{
    private LISTENER listener;
    private ListModel<GroupModel> listModel;

    public GroupData(LISTENER l)
    {
        listener = l;
    }

    protected GroupModel getItem(int position)
    {
        return listModel.getItem(position);
    }

    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

    public int getItemViewType(int position)
    {
        return listModel.getItem(position).getType();
    }

    public int getItemCount()
    {
        if(listModel == null)
        {
            return 0;
        }
        return listModel.getItemsCount();
    }

    public void swapData(ListModel<GroupModel> l)
    {
        if(listModel != null)
        {
            listModel.clear();
        }
        listModel = l;
    }

    public LISTENER getListener()
    {
        return listener;
    }

    public abstract RecyclerView.ViewHolder onCreateViewHolder(Context context, ViewGroup parent, int viewType);
}