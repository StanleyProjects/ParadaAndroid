package ru.parada.app.modules.servicesprices;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import ru.parada.app.R;
import ru.parada.app.contracts.PricesContract;
import ru.parada.app.contracts.ServicesWithPricesContract;
import ru.parada.app.core.ServicesWithPricesCore;
import ru.parada.app.modules.prices.PricesFragment;
import ru.parada.app.modules.servicesprices.adapter.PricesGroupData;
import ru.parada.app.modules.servicesprices.adapter.ServicesPricesAdapterListener;
import ru.parada.app.units.ArrayListModel;
import ru.parada.app.units.GroupAdapter;
import ru.parada.app.units.GroupModel;
import ru.parada.app.units.ListModel;
import ru.parada.app.units.MVPFragment;

public class ServicesWithPricesFragment
        extends MVPFragment<ServicesWithPricesContract.Presenter, ServicesWithPricesContract.Behaviour>
        implements ServicesWithPricesContract.View
{
    static public ServicesWithPricesFragment newInstanse(ServicesWithPricesContract.Behaviour behaviour)
    {
        ServicesWithPricesFragment fragment = new ServicesWithPricesFragment();
        fragment.setBehaviour(behaviour);
        return fragment;
    }

    private Fragment pricesFragment;

    private RecyclerView list;
    private EditText search;

    private GroupAdapter adapter;
    private PricesGroupData pricesGroupData;

    @Override
    public void onResume()
    {
        super.onResume();
        if(pricesFragment != null && getChildFragmentManager().getBackStackEntryCount() == 0)
        {
            showPrices();
        }
    }

    @Override
    protected ServicesWithPricesContract.Presenter setPresenter()
    {
        return new ServicesWithPricesPresenter(this);
    }

    @Override
    protected int setContentView()
    {
        return R.layout.services_prices_fragment;
    }

    @Override
    protected void initViews(View v)
    {
        setClickListener(v.findViewById(R.id.menu), v.findViewById(R.id.search_clear));
        list = (RecyclerView)v.findViewById(R.id.list);
        search = (EditText)v.findViewById(R.id.search);
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
                    case R.id.search_clear:
                        searchClear();
                        break;
                }
            }
        };
    }

    @Override
    protected void init()
    {
        pricesGroupData = new PricesGroupData(new ServicesPricesAdapterListener()
        {
            @Override
            public void getService(int id)
            {
                pricesFragment = PricesFragment.newInstanse(new PricesContract.Behaviour()
                {
                    @Override
                    public void back()
                    {
                        getChildFragmentManager().popBackStack();
                        pricesFragment = null;
                    }
                }, id);
                showPrices();
            }
        });
        adapter = new GroupAdapter<>(getActivity(), pricesGroupData);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        search.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                String keys = s.toString();
                if(keys.length() > 0)
                {
                    getPresenter().search(keys);
                }
                else
                {
                    getPresenter().update();
                }
            }
            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        getPresenter().update();
        getPresenter().load();
    }
    private void searchClear()
    {
        if(search.getText()
                 .toString()
                 .length() > 0)
        {
            search.setText("");
            getPresenter().update();
        }
    }

    @Override
    public void update(ListModel<ServicesWithPricesCore.Model> allData, ListModel<ServicesWithPricesContract.GroupModel> groups)
    {
        ArrayList<GroupModel> data = new ArrayList<>();
        int n = 0;
        for(int i=0; i<groups.getItemsCount(); i++)
        {
            ServicesWithPricesContract.GroupModel gm = groups.getItem(i);
            data.add(new GroupModel<>(gm, PricesGroupData.ViewTypes.GROUP));
            while(n < allData.getItemsCount())
            {
                ServicesWithPricesCore.Model m = allData.getItem(n);
                if(m.getGroupId() == gm.getId())
                {
                    data.add(new GroupModel<>(m, PricesGroupData.ViewTypes.NORMAL));
                    n++;
                }
                else
                {
                    break;
                }
            }
        }
        final ArrayListModel<GroupModel> servicesWithPrices = new ArrayListModel<>(data);
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                pricesGroupData.swapData(servicesWithPrices);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void showPrices()
    {
        getChildFragmentManager().beginTransaction()
                                 .add(R.id.prices_frame, pricesFragment)
                                 .addToBackStack(pricesFragment.getClass().getName())
                                 .commit();
    }
}