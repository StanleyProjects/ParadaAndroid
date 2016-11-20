package ru.parada.app.modules.news.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import ru.parada.app.core.NewsCore;
import ru.parada.app.units.adapters.ModelDataAdapter;

public class NewsAdapter
    extends ModelDataAdapter<OneOfNewsHolder, NewsCore.Model, NewsAdapterListener>
{
    public NewsAdapter(Context c, NewsAdapterListener l)
    {
        super(c, l);
    }

    @Override
    protected void setData(OneOfNewsHolder holder, NewsCore.Model item)
    {
        holder.setTitle(item.getTitle());
        holder.setDescr(item.getDescription());
        holder.setDate(item.getDate()*1000);
        final int id = item.getId();
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getListener().getOneOfNews(id);
            }
        });
    }

    @Override
    public OneOfNewsHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new OneOfNewsHolder(getContext(), parent);
    }
}