package ru.parada.app.modules.actions.adapter;

import android.content.Context;
import android.view.ViewGroup;

import ru.parada.app.core.ActionsCore;
import ru.parada.app.managers.FoldersManager;
import ru.parada.app.units.ListModel;
import ru.parada.app.units.ModelAdapter;

public class ActionsAdapter
        extends ModelAdapter<ActionHolder, ActionsCore.ActionModel, ActionsAdapterListener>
{
    private ListModel<ActionsCore.ActionModel> data;

    public ActionsAdapter(Context c, ActionsAdapterListener l)
    {
        super(c, l);
    }

    @Override
    protected ActionsCore.ActionModel getItem(int position)
    {
        return data.getItem(position);
    }

    @Override
    protected void setData(ActionHolder holder, ActionsCore.ActionModel item)
    {
        if(item.getPhotoPath() != null)
        {
            holder.setPhoto(FoldersManager.getImagesDirectory() + "/" + item.getPhotoPath());
        }
        holder.setDate(item.getFromDate()*1000, item.getToDate()*1000);
        holder.setTitle(item.getTitle());
    }

    @Override
    public ActionHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ActionHolder(getContext(), parent);
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

    public void swapData(ListModel<ActionsCore.ActionModel> d)
    {
        if(this.data != null)
        {
            this.data.clear();
        }
        this.data = d;
    }
}