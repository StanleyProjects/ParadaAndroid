package ru.parada.app.modules.info.list.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.parada.app.R;
import ru.parada.app.units.AdapterHolder;

public class InfoAdapterHolder
        extends AdapterHolder
{
    private TextView title;

    public InfoAdapterHolder(Context context, ViewGroup parent)
    {
        super(context, parent, R.layout.info_list_item);
        title = (TextView)itemView.findViewById(R.id.title);
    }

    public void setTitle(String t)
    {
        title.setText(t);
    }
}