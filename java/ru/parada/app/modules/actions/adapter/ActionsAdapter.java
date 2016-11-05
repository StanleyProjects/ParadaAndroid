package ru.parada.app.modules.actions.adapter;

import android.content.Context;
import android.view.ViewGroup;

import ru.parada.app.core.ActionsCore;
import ru.parada.app.managers.FoldersManager;
import ru.parada.app.units.ModelDataAdapter;

public class ActionsAdapter
        extends ModelDataAdapter<ActionHolder, ActionsCore.ActionModel, ActionsAdapterListener>
{
    public ActionsAdapter(Context c, ActionsAdapterListener l)
    {
        super(c, l);
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
}