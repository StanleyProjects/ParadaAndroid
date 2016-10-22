package ru.parada.app.modules.news.adapter;

import android.content.Context;
import android.view.ViewGroup;

import ru.parada.app.core.NewsCore;
import ru.parada.app.units.ListModel;
import ru.parada.app.units.ModelAdapter;

public class NewsAdapter
    extends ModelAdapter<OneOfNewsHolder, NewsCore.OneOfNewsModel, NewsAdapterListener>
{
    private ListModel<NewsCore.OneOfNewsModel> data;

    public NewsAdapter(Context c, NewsAdapterListener l)
    {
        super(c, l);
    }

    @Override
    protected NewsCore.OneOfNewsModel getItem(int position)
    {
        return data.getItem(position);
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

    @Override
    public int getItemCount()
    {
        if(data == null)
        {
            return 0;
        }
        return data.getItemsCount();
    }

    public void swapData(ListModel<NewsCore.OneOfNewsModel> d)
    {
        if(this.data != null)
        {
            this.data.clear();
        }
        this.data = d;
    }
}