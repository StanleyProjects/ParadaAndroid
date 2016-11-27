package ru.parada.app.modules.info.list;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ru.parada.app.App;
import ru.parada.app.R;
import ru.parada.app.contracts.info.InfoContract;
import ru.parada.app.contracts.info.InfoDetailContract;
import ru.parada.app.core.InfoCore;
import ru.parada.app.modules.info.detail.InfoDetailFragment;
import ru.parada.app.modules.info.list.adapter.InfoAdapter;
import ru.parada.app.modules.info.list.adapter.InfoAdapterListener;
import ru.parada.app.units.ListModel;
import ru.parada.app.units.MultiFragment;

public class InfoListFragment
        extends MultiFragment<InfoContract.Presenter, InfoContract.Behaviour>
        implements InfoContract.View
{
    static public Fragment newInstanse(InfoContract.Behaviour behaviour)
    {
        InfoListFragment fragment = new InfoListFragment();
        fragment.setBehaviour(behaviour);
        return fragment;
    }

    private Fragment detailFragment;

    private RecyclerView list;
    private TextView list_empty;

    private boolean load;
    private InfoAdapter adapter;
    private InfoDetailContract.Behaviour infoDetailBehaviour = new InfoDetailContract.Behaviour()
    {
        @Override
        public void back()
        {
            getChildFragmentManager().popBackStack();
            detailFragment = null;
        }
    };

    @Override
    protected InfoContract.Presenter setPresenter()
    {
        return new InfoListPresenter(this);
    }

    @Override
    protected int setContentView()
    {
        return R.layout.info_list_fragment;
    }

    @Override
    protected void initViews(View v)
    {
        setClickListener(v.findViewById(R.id.menu));
        list = (RecyclerView)v.findViewById(R.id.list);
        list_empty = (TextView)v.findViewById(R.id.list_empty);
    }

    @Override
    protected void onClickView(int id)
    {
        switch(id)
        {
            case R.id.menu:
                getBehaviour().openMenu();
                break;
        }
    }

    @Override
    protected void init()
    {
        list.setVisibility(View.GONE);
        list_empty.setVisibility(View.VISIBLE);
        adapter = new InfoAdapter(getActivity(), new InfoAdapterListener()
        {
            @Override
            public void getInfo(final int id)
            {
                if(load || App.getComponent().getAndroidUtil().blockClick())
                {
                    return;
                }
                runAfterResume(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        resetScreenIndex();
                        detailFragment = InfoDetailFragment.newInstanse(infoDetailBehaviour, id);
                        addSubscreen(detailFragment);
                    }
                });
            }
        });
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        getPresenter().update();
        load = true;
        getPresenter().load();
    }

    @Override
    protected void setScreens(int screen)
    {
        if(screen >= InfoCore.Screens.DETAIL)
        {
            addSubscreen(detailFragment);
        }
    }

    @Override
    public void load()
    {
        load = false;
    }

    @Override
    public void update(final ListModel<InfoCore.Model> data)
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

    private void addSubscreen(Fragment fragment)
    {
        getChildFragmentManager().beginTransaction()
                                 .add(R.id.subscreen, fragment)
                                 .addToBackStack(fragment.getClass().getName())
                                 .commit();
    }
}