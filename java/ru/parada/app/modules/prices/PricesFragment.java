package ru.parada.app.modules.prices;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ru.parada.app.R;
import ru.parada.app.contracts.PricesContract;
import ru.parada.app.contracts.ServicesWithPricesContract;
import ru.parada.app.modules.prices.adapter.PricesAdapter;
import ru.parada.app.modules.prices.adapter.PricesAdapterListener;
import ru.parada.app.units.ListModel;
import ru.parada.app.units.MVPFragment;

public class PricesFragment
        extends MVPFragment<PricesContract.Presenter, PricesContract.Behaviour>
        implements PricesContract.View, PricesContract.Mark
{
    static public PricesFragment newInstanse(PricesContract.Behaviour behaviour, int service_id)
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

    private PricesAdapter adapter;

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
    protected View.OnClickListener setClickListener()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switch(v.getId())
                {
                    case R.id.back:
                        getBehaviour().back();
                        break;
                }
            }
        };
    }

    @Override
    protected void init()
    {
        adapter = new PricesAdapter(getActivity(), new PricesAdapterListener()
        {
        });
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        getPresenter().update(getArguments().getInt(SERVICE_ID));
        getPresenter().load(getArguments().getInt(SERVICE_ID));
    }

    @Override
    public void update(final ListModel<PricesContract.Model> data, final ServicesWithPricesContract.Model service)
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
            }
        });
    }
}