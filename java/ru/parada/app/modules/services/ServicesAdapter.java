package ru.parada.app.modules.services;

import android.content.Context;
import android.view.ViewGroup;

import ru.parada.app.contracts.ServicesContract;
import ru.parada.app.managers.FoldersManager;
import ru.parada.app.units.ListModel;
import ru.parada.app.units.ModelAdapter;

public class ServicesAdapter
    extends ModelAdapter<ServiceHolder, ServicesContract.ListItemModel, ServicesAdapterListener>
{
    private ListModel<ServicesContract.ListItemModel> data;

    public ServicesAdapter(Context c, ServicesAdapterListener l)
    {
        super(c, l);
    }

    @Override
    protected ServicesContract.ListItemModel getItem(int position)
    {
        return data.getItem(position);
    }

    @Override
    protected void setData(ServiceHolder holder, ServicesContract.ListItemModel item)
    {
        if(item.getPhotoPath() != null)
        {
            holder.setPhoto(FoldersManager.getImagesDirectory() + "/" + item.getPhotoPath());
        }
        holder.setTitle(item.getTitle());
    }

    @Override
    public ServiceHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ServiceHolder(getContext(), parent);
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

    public void swapData(ListModel<ServicesContract.ListItemModel> d)
    {
        if(this.data != null)
        {
            this.data.clear();
        }
        this.data = d;
    }
}