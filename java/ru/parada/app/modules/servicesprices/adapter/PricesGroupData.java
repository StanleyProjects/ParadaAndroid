package ru.parada.app.modules.servicesprices.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import ru.parada.app.contracts.ServicesWithPricesContract;
import ru.parada.app.core.ServicesWithPricesCore;
import ru.parada.app.units.GroupData;

public class PricesGroupData
    extends GroupData<ServicesPricesAdapterListener>
{
    public PricesGroupData(ServicesPricesAdapterListener l)
    {
        super(l);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(Context context, ViewGroup parent, int viewType)
    {
        switch(viewType)
        {
            case ViewTypes.GROUP:
                return new ServicePriceGroupHolder(context, parent);
            case ViewTypes.NORMAL:
                return new ServicePriceHolder(context, parent);
        }
        return null;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        switch(getItemViewType(position))
        {
            case ViewTypes.GROUP:
                setGroup((ServicePriceGroupHolder)holder, (ServicesWithPricesContract.GroupModel)getItem(position).getData());
                break;
            case ViewTypes.NORMAL:
                setNormal((ServicePriceHolder)holder, (ServicesWithPricesCore.Model)getItem(position).getData());
                break;
        }
    }

    private void setNormal(ServicePriceHolder holder, final ServicesWithPricesCore.Model data)
    {
        holder.setTitle(data.getTitle());
        holder.setSubTitle(data.getSubTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getListener().getService(data.getId());
            }
        });
    }

    private void setGroup(ServicePriceGroupHolder holder, ServicesWithPricesContract.GroupModel data)
    {
        holder.setName(data.getGroupName());
    }

    public interface ViewTypes
    {
        int GROUP = 1;
        int NORMAL = 2;
    }
}