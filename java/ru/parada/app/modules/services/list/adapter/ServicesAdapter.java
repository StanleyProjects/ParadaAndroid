package ru.parada.app.modules.services.list.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import ru.parada.app.core.ServicesCore;
import ru.parada.app.managers.FoldersManager;
import ru.parada.app.units.adapters.ModelDataAdapter;

public class ServicesAdapter
    extends ModelDataAdapter<ServiceHolder, ServicesCore.Model, ServicesAdapterListener>
{
    public ServicesAdapter(Context c, ServicesAdapterListener l)
    {
        super(c, l);
    }

    @Override
    protected void setData(ServiceHolder holder, ServicesCore.Model item)
    {
        if(item.getImagePath() != null)
        {
            holder.setPhoto(FoldersManager.getImagesDirectory() + "/" + item.getImagePath());
        }
        holder.setTitle(item.getTitle());
        final int id = item.getId();
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getListener().getService(id);
            }
        });
    }

    @Override
    public ServiceHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ServiceHolder(getContext(), parent);
    }
}