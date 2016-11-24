package ru.parada.app.modules.menu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import ru.parada.app.contracts.MenuContract;
import ru.parada.app.modules.menu.models.MenuListModel;
import ru.parada.app.modules.menu.models.MenuModel;
import ru.parada.app.units.adapters.ModelAdapter;

public class MenuAdapter
    extends ModelAdapter<MenuAdapterHolder, MenuModel, MenuContract.Behaviour>
{
    private MenuListModel data;

    public MenuAdapter(Context c, MenuListModel d, MenuContract.Behaviour l)
    {
        super(c, l);
        data = d;
    }

    @Override
    protected MenuModel getItem(int position)
    {
        return data.getItem(position);
    }

    @Override
    protected void setData(MenuAdapterHolder holder, final MenuModel item)
    {
        holder.getHolder().getIcon().setImageResource(item.icon);
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
                item.click(getListener());
            }
        });
    }

    @Override
    public MenuAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new MenuAdapterHolder(getContext(), parent);
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
}