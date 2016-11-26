package ru.parada.app.modules.info.list.adapter;

import android.content.Context;
import android.view.ViewGroup;

import ru.parada.app.core.InfoCore;
import ru.parada.app.units.adapters.ModelDataAdapter;

public class InfoAdapter
        extends ModelDataAdapter<InfoAdapterHolder, InfoCore.Model, InfoAdapterListener>
{
    public InfoAdapter(Context c, InfoAdapterListener l)
    {
        super(c, l);
    }

    @Override
    protected void setData(InfoAdapterHolder holder, InfoCore.Model item)
    {
        holder.setTitle(item.getTitle());
    }

    @Override
    public InfoAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new InfoAdapterHolder(getContext(), parent);
    }
}