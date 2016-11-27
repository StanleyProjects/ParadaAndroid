package ru.parada.app.modules.prices;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ru.parada.app.R;
import ru.parada.app.contracts.PricesContract;
import ru.parada.app.core.ServicesWithPricesCore;
import ru.parada.app.modules.prices.adapter.PricesAdapterListener;
import ru.parada.app.modules.prices.adapter.PricesFooterAdapter;
import ru.parada.app.units.ListModel;
import ru.parada.app.units.MVPFragment;

public class PricesFragment
        extends MVPFragment<PricesContract.Presenter, PricesContract.Behaviour>
        implements PricesContract.View, PricesContract.Mark
{
    static public MVPFragment newInstanse(PricesContract.Behaviour behaviour, int service_id)
    {
        PricesFragment fragment = new PricesFragment();
        fragment.setBehaviour(behaviour);
        Bundle bundle = new Bundle();
        bundle.putInt(SERVICE_ID, service_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private RecyclerView list;
    private TextView service_name;

    private PricesFooterAdapter adapter;

    @Override
    protected PricesContract.Presenter setPresenter()
    {
        return new PricesPresenter(this);
    }

    @Override
    protected int setContentView()
    {
        return R.layout.prices_fragment;
    }

    @Override
    protected void initViews(View v)
    {
        setClickListener(v.findViewById(R.id.back));
        list = (RecyclerView)v.findViewById(R.id.list);
        service_name = (TextView)v.findViewById(R.id.service_name);
    }

    @Override
    protected void onClickView(int id)
    {
        switch(id)
        {
            case R.id.back:
                getBehaviour().back();
                break;
        }
    }

    @Override
    protected void init()
    {
        list.setVisibility(View.GONE);
        adapter = new PricesFooterAdapter(getActivity(), new PricesAdapterListener()
        {
        });
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        getPresenter().update(getArguments().getInt(SERVICE_ID));
        getPresenter().load(getArguments().getInt(SERVICE_ID));
    }

    @Override
    public void update(final ListModel<PricesContract.Model> data, final ServicesWithPricesCore.Model service)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                adapter.swapData(data);
                adapter.notifyDataSetChanged();
                Log.e(this.getClass()
                          .getName(), "update " + data.getItemsCount());
                service_name.setText(service.getTitle());
                list.setVisibility(View.VISIBLE);
            }
        });
    }
}