package ru.parada.app.modules.actions;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import ru.parada.app.R;
import ru.parada.app.contracts.ActionsContract;
import ru.parada.app.core.ActionsCore;
import ru.parada.app.modules.actions.adapter.ActionsAdapter;
import ru.parada.app.modules.actions.adapter.ActionsAdapterListener;
import ru.parada.app.units.ListModel;
import ru.parada.app.units.MVPFragment;

public class ActionsFragment
        extends MVPFragment<ActionsContract.Presenter, ActionsContract.Behaviour>
        implements ActionsContract.View
{
    static public ActionsFragment newInstanse(ActionsContract.Behaviour behaviour)
    {
        ActionsFragment fragment = new ActionsFragment();
        fragment.setBehaviour(behaviour);
        return fragment;
    }

    private RecyclerView list;

    private ActionsAdapter adapter;

    @Override
    public void update(final ListModel<ActionsCore.Model> data)
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
        adapter = new ActionsAdapter(getActivity(), new ActionsAdapterListener()
        {
            @Override
            public void getAction(int id)
            {
                getBehaviour().getAction(id);
            }
        });
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        getPresenter().update();
        getPresenter().load();
    }
}