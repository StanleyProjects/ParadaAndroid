package ru.parada.app.modules.news.adapter;

import android.content.Context;
import android.view.ViewGroup;

import ru.parada.app.core.NewsCore;
import ru.parada.app.units.ModelDataAdapter;

public class NewsAdapter
    extends ModelDataAdapter<OneOfNewsHolder, NewsCore.OneOfNewsModel, NewsAdapterListener>
{
    public NewsAdapter(Context c, NewsAdapterListener l)
    {
        super(c, l);
    }

    @Override
    protected void setData(OneOfNewsHolder holder, NewsCore.OneOfNewsModel item)
    {
        holder.setTitle(item.getTitle());
        holder.setDescr(item.getDescription());
        holder.setDate(item.getDate()*1000);
    }

    @Override
    public OneOfNewsHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new OneOfNewsHolder(getContext(), parent);
    }
}