package ru.parada.app.modules.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import ru.parada.app.contracts.MainContract;
import ru.parada.app.units.HeadFootAdapter;
import ru.parada.app.units.ListModel;

public class NewsAdapter
    extends HeadFootAdapter<OneOfNewsHolder, MainContract.ListItemModel, NewsAdapterListener>
{
    private ListModel<MainContract.ListItemModel> data;

    public NewsAdapter(Context c, NewsAdapterListener l)
    {
        super(c, l);
    }

    @Override
    protected MainContract.ListItemModel getItem(int position)
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
    protected void setData(OneOfNewsHolder holder, MainContract.ListItemModel item)
    {
        holder.setTitle(item.getTitle());
        holder.setDescr(item.getDescription());
        holder.setDate(item.getDate()*1000);
    }

    @Override
    protected void setHeader(RecyclerView.ViewHolder holder)
    {
    }

    @Override
    protected void setFooter(RecyclerView.ViewHolder holder)
    {

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

    public void swapData(ListModel<MainContract.ListItemModel> d)
    {
        if(this.data != null)
        {
            this.data.clear();
        }
        this.data = d;
    }
}