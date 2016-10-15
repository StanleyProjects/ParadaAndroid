package ru.parada.app.modules.menu;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.parada.app.R;
import ru.parada.app.units.AdapterHolder;

public class MenuAdapterHolder
        extends AdapterHolder
{
    private MenuHolder holder;

    public MenuAdapterHolder(Context context, ViewGroup parent)
    {
        super(context, parent, R.layout.menu_list_item);
        holder = new MenuHolder(
                itemView.findViewById(R.id.background),
                (ImageView)itemView.findViewById(R.id.icon),
                (TextView)itemView.findViewById(R.id.name),
                context.getResources().getColor(R.color.greylight),
                context.getResources().getColor(R.color.colorAccent),
                context.getResources().getColor(R.color.white),
                context.getResources().getColor(R.color.grey),
                context.getResources().getColor(R.color.greydark)
        );
    }

    public MenuHolder getHolder()
    {
        return holder;
    }
}