package ru.parada.app.modules.news.list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ru.parada.app.R;
import ru.parada.app.contracts.news.NewsContract;
import ru.parada.app.core.NewsCore;
import ru.parada.app.modules.news.list.adapter.NewsAdapter;
import ru.parada.app.modules.news.list.adapter.NewsAdapterListener;
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
    private TextView list_empty;

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
        list_empty = (TextView)v.findViewById(R.id.list_empty);
    }

    @Override
    protected void init()
    {
        list.setVisibility(View.GONE);
        list_empty.setVisibility(View.VISIBLE);
        adapter = new NewsAdapter(getActivity(), new NewsAdapterListener()
        {
            @Override
            public void getOneOfNews(int id)
            {
                getBehaviour().getOneOfNews(id);
            }
        });
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        getPresenter().update();
    }

    @Override
    public void update(final ListModel<NewsCore.Model> data)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                if(data.getItemsCount() > 0)
                {
                    list.setVisibility(View.VISIBLE);
                    list_empty.setVisibility(View.GONE);
                    adapter.swapData(data);
                }
                else
                {
                    list.setVisibility(View.GONE);
                    list_empty.setVisibility(View.VISIBLE);
                    adapter.swapData(null);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}