package ru.parada.app.units;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public abstract class GroupData
{
    private ListModel<GroupModel> listModel;

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

    public abstract RecyclerView.ViewHolder onCreateViewHolder(Context context, ViewGroup parent, int viewType);
}