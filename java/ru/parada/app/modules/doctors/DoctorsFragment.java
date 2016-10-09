package ru.parada.app.modules.doctors;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ru.parada.app.R;
import ru.parada.app.contracts.DoctorsContract;
import ru.parada.app.units.ListModel;
import ru.parada.app.units.MVPFragment;

public class DoctorsFragment
        extends MVPFragment<DoctorsContract.Presenter, DoctorsContract.Behaviour>
        implements DoctorsContract.View
{
    static public DoctorsFragment newInstanse(DoctorsContract.Behaviour behaviour)
    {
        DoctorsFragment fragment = new DoctorsFragment();
        fragment.setBehaviour(behaviour);
        return fragment;
    }

    private RecyclerView list;

    private DoctorsAdapter adapter;

    @Override
    public void onResume()
    {
        super.onResume();
        getPresenter().updateDoctors();
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
        return R.layout.doctors_fragment;
    }

    @Override
    protected DoctorsContract.Presenter setPresenter()
    {
        return new DoctorsPresenter(this);
    }

    @Override
    protected void initViews(View v)
    {
        setClickListener(v.findViewById(R.id.menu));
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
                }
            }
        };
    }

    @Override
    protected void init()
    {
        adapter = new DoctorsAdapter(getActivity(), new DoctorsAdapterListener()
        {
        });
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        getPresenter().loadDoctors();
    }

    @Override
    public void updateDoctors(final ListModel<DoctorsContract.ListItemModel> data)
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
//        adapter.swapData(data);
//        adapter.notifyDataSetChanged();
    }
}