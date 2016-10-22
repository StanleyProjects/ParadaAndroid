package ru.parada.app.modules.news;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import ru.parada.app.R;
import ru.parada.app.contracts.NewsContract;
import ru.parada.app.core.NewsCore;
import ru.parada.app.modules.news.adapter.NewsAdapter;
import ru.parada.app.modules.news.adapter.NewsAdapterListener;
import ru.parada.app.units.ListModel;
import ru.parada.app.units.MVPFragment;

public class NewsFragment
        extends MVPFragment<NewsContract.Presenter, NewsContract.Behaviour>
        implements NewsContract.View
{
    static public NewsFragment newInstanse(NewsContract.Behaviour behaviour)
    {
        NewsFragment fragment = new NewsFragment();
        fragment.setBehaviour(behaviour);
        return fragment;
    }

    @Override
    protected NewsContract.Presenter setPresenter()
    {
        return new NewsPresenter(this);
    }

    private RecyclerView list;

    private NewsAdapter adapter;

    @Override
    protected int setContentView()
    {
        return R.layout.news_list_fragment;
    }

    @Override
    protected void initViews(View v)
    {
        list = (RecyclerView)v.findViewById(R.id.list);
    }

    @Override
    protected View.OnClickListener setClickListener()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        };
    }

    @Override
    protected void init()
    {
        adapter = new NewsAdapter(getActivity(), new NewsAdapterListener()
        {
        });
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        getPresenter().update();
        getPresenter().load();
    }

    @Override
    public void update(final ListModel<NewsCore.OneOfNewsModel> data)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Log.e(this.getClass().getName(), "update " + data.getItemsCount() + " " + Thread.currentThread());
                adapter.swapData(data);
                adapter.notifyDataSetChanged();
            }
        });
    }
}