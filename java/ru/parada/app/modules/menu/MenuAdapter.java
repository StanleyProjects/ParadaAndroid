package ru.parada.app.modules.menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import ru.parada.app.contracts.MenuContract;
import ru.parada.app.utils.ImagesUtils;

public class MenuAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private MenuListModel data;

    private Context context;
    private MenuContract.Behaviour listener;

    public MenuAdapter(Context c, MenuListModel d, MenuContract.Behaviour l)
    {
        context = c;
        listener = l;
        data = d;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if(getItemViewType(position) != ViewTypes.HEADER)
        {
            setData((MenuAdapterHolder)holder, data.getItem(position-1));
        }
    }

    private void setData(MenuAdapterHolder holder, final MenuModel item)
    {
        ImagesUtils.setImageFromResources(item.icon, holder.getHolder().getIcon());
        holder.getHolder().setName(item.name);
        if(data.isHighlight(item))
        {
            holder.getHolder().highlight();
        }
        else
        {
            holder.getHolder().setNormal();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                item.click(listener);
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(viewType == ViewTypes.HEADER)
        {
            return new MenuHeaderHolder(context, parent);
        }
        return new MenuAdapterHolder(context, parent);
    }

    @Override
    public int getItemViewType(int position)
    {
        if(position == 0)
        {
            return ViewTypes.HEADER;
        }
        return ViewTypes.NORMAL;
    }
    @Override
    public int getItemCount()
    {
        if(data == null)
        {
            return 1;
        }
        return data.getItemsCount()+1;
    }

    private interface ViewTypes
    {
        int HEADER = 1;
        int NORMAL = 2;
    }
}