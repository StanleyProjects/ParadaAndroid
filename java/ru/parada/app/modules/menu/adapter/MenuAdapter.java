package ru.parada.app.modules.menu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import ru.parada.app.App;
import ru.parada.app.contracts.MenuContract;
import ru.parada.app.modules.menu.models.MenuModel;
import ru.parada.app.units.adapters.ModelDataAdapter;

public class MenuAdapter
    extends ModelDataAdapter<MenuAdapterHolder, MenuModel, MenuContract.Behaviour>
{
    private int highlight;

    public MenuAdapter(Context c, MenuContract.Behaviour l)
    {
        super(c, l);
    }

    @Override
    protected void setData(MenuAdapterHolder holder, final MenuModel item)
    {
        holder.getHolder().getIcon().setImageResource(item.icon);
        holder.getHolder().setName(item.name);
        if(highlight == item.id)
        {
            holder.getHolder().highlight();
        }
        else
        {
            holder.getHolder().setNormal();
        }
        holder.getHolder().showBadge(item.isNotification && App.getComponent().getPreferenceManager().needNotificationBadge());
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

    public void setHighlight(int h)
    {
        highlight = h;
    }
}