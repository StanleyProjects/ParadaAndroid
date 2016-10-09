package ru.parada.app.modules.main;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import ru.parada.app.R;
import ru.parada.app.contracts.MainContract;
import ru.parada.app.units.ListModel;
import ru.parada.app.units.MVPFragment;

public class MainFragment
        extends MVPFragment<MainContract.Presenter, MainContract.Behaviour>
        implements MainContract.View
{
    static public MainFragment newInstanse(MainContract.Behaviour behaviour)
    {
        MainFragment fragment = new MainFragment();
        fragment.setBehaviour(behaviour);
        return fragment;
    }

    private ImageView phone;
    private RecyclerView list;

    private NewsAdapter adapter;

    private Drawable btn_phone;
    private Drawable btn_phone_close;

    @Override
    public void onResume()
    {
        super.onResume();
        getPresenter().updateNews();
    }
    @Override
    public void onPause()
    {
        super.onPause();
        adapter.swapData(null);
    }

    @Override
    protected int setContentView()
    {
        return R.layout.main_fragment;
    }

    @Override
    protected MainContract.Presenter setPresenter()
    {
        return new MainPresenter(this);
    }

    @Override
    protected void initViews(View v)
    {
        setClickListener(v.findViewById(R.id.menu), v.findViewById(R.id.services));
        phone = (ImageView)v.findViewById(R.id.phone);
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
                switch(v.getId())
                {
                    case R.id.menu:
                        getBehaviour().openMenu();
                        break;
                    case R.id.phone:
                        getPresenter().phoneSwitch();
                        break;
                    case R.id.services:
                        getBehaviour().openService();
                        break;
                }
            }
        };
    }

    @Override
    protected void init()
    {
        setClickListener(phone);
        btn_phone = getActivity().getResources()
                                 .getDrawable(R.drawable.btn_phone);
        btn_phone_close = getActivity().getResources()
                                       .getDrawable(R.drawable.btn_phone_close);
        phoneOpen();
        adapter = new NewsAdapter(getActivity(), new NewsAdapterListener()
        {
        });
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        getPresenter().loadNews();
    }

    @Override
    public void phoneOpen()
    {
        phone.setImageDrawable(btn_phone);
    }

    @Override
    public void phoneClose()
    {
        phone.setImageDrawable(btn_phone_close);
    }

    @Override
    public void updateNews(final ListModel<MainContract.ListItemModel> data)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                adapter.swapData(data);
                adapter.notifyDataSetChanged();
            }
        }, 0);
    }
}