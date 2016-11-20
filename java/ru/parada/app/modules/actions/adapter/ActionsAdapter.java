package ru.parada.app.modules.actions.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import ru.parada.app.core.ActionsCore;
import ru.parada.app.managers.FoldersManager;
import ru.parada.app.units.adapters.ModelDataAdapter;

public class ActionsAdapter
        extends ModelDataAdapter<ActionHolder, ActionsCore.Model, ActionsAdapterListener>
{
    public ActionsAdapter(Context c, ActionsAdapterListener l)
    {
        super(c, l);
    }

    @Override
    protected void setData(ActionHolder holder, ActionsCore.Model item)
    {
        if(item.getImagePath() != null)
        {
            holder.setPhoto(FoldersManager.getImagesDirectory() + "/" + item.getImagePath());
        }
        holder.setDate(item.getFromDate()*1000, item.getToDate()*1000);
        holder.setTitle(item.getTitle());
        final int id = item.getId();
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getListener().getAction(id);
            }
        });
    }

    @Override
    public ActionHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ActionHolder(getContext(), parent);
    }
}