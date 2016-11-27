package ru.parada.app.modules.actions.list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ru.parada.app.R;
import ru.parada.app.contracts.actions.ActionsContract;
import ru.parada.app.core.ActionsCore;
import ru.parada.app.modules.actions.list.adapter.ActionsAdapter;
import ru.parada.app.modules.actions.list.adapter.ActionsAdapterListener;
import ru.parada.app.units.ListModel;
import ru.parada.app.units.MVPFragment;

public class ActionsFragment
        extends MVPFragment<ActionsContract.Presenter, ActionsContract.Behaviour>
        implements ActionsContract.View
{
    static public MVPFragment newInstanse(ActionsContract.Behaviour behaviour)
    {
        ActionsFragment fragment = new ActionsFragment();
        fragment.setBehaviour(behaviour);
        return fragment;
    }

    private RecyclerView list;
    private TextView list_empty;

    private boolean load;
    private ActionsAdapter adapter;

    @Override
    public void load()
    {
        load = false;
    }

    @Override
    protected ActionsContract.Presenter setPresenter()
    {
        return new ActionsPresenter(this);
    }

    @Override
    protected int setContentView()
    {
        return R.layout.actions_list_fragment;
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
        adapter = new ActionsAdapter(getActivity(), new ActionsAdapterListener()
        {
            @Override
            public void getAction(int id)
            {
                if(load)
                {
                    return;
                }
                getBehaviour().getAction(id);
            }
        });
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        getPresenter().update();
        load = true;
        getPresenter().load();
    }

    @Override
    public void update(final ListModel<ActionsCore.Model> data)
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