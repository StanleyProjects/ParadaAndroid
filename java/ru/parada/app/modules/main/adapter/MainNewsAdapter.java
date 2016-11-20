package ru.parada.app.modules.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import ru.parada.app.contracts.MainContract;
import ru.parada.app.core.NewsCore;
import ru.parada.app.modules.news.adapter.OneOfNewsHolder;
import ru.parada.app.units.adapters.HeadFootAdapter;
import ru.parada.app.units.ListModel;

public class MainNewsAdapter
    extends HeadFootAdapter<OneOfNewsHolder, NewsCore.Model, MainNewsAdapterListener>
{
    private ListModel<NewsCore.Model> data;

    public MainNewsAdapter(Context c, MainNewsAdapterListener l)
    {
        super(c, l);
    }

    @Override
    protected NewsCore.Model getItem(int position)
    {
        return data.getItem(position);
    }

    @Override
    protected RecyclerView.ViewHolder createHeaderHolder(ViewGroup parent)
    {
        return new MainHeaderHolder(getContext(), parent, new MainContract.HeaderBehaviour()
        {
            @Override
            public void openServices()
            {
                getListener().openServices();
            }
            @Override
            public void openSubscribe()
            {
                getListener().openSubscribe();
            }
            @Override
            public void openPrices()
            {
                getListener().openPrices();
            }
        });
    }

    @Override
    protected RecyclerView.ViewHolder createFooterHolder(ViewGroup parent)
    {
        return new MainFooterHolder(getContext(), parent, new MainContract.FooterBehaviour()
        {
            @Override
            public void openAllNews()
            {
                getListener().openAllNews();
            }
        });
    }

    @Override
    protected OneOfNewsHolder createNormalHolder(ViewGroup parent)
    {
        return new OneOfNewsHolder(getContext(), parent);
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
                getListener().oneOfNews(id);
            }
        });
    }

    @Override
    protected int getRealCount()
    {
        if(data == null)
        {
            return 0;
        }
        return data.getItemsCount();
    }

    public void swapData(ListModel<NewsCore.Model> d)
    {
        if(this.data != null)
        {
            this.data.clear();
        }
        this.data = d;
    }
}