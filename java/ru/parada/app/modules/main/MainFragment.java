package ru.parada.app.modules.main;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import ru.parada.app.R;
import ru.parada.app.contracts.MainContract;
import ru.parada.app.core.NewsCore;
import ru.parada.app.modules.call.CallDialog;
import ru.parada.app.modules.call.CallDialogListener;
import ru.parada.app.units.ListModel;
import ru.parada.app.units.MVPFragment;

public class MainFragment
        extends MVPFragment<MainContract.Presenter, MainContract.MainBehaviour>
        implements MainContract.View
{
    static public MainFragment newInstanse(MainContract.MainBehaviour behaviour)
    {
        MainFragment fragment = new MainFragment();
        fragment.setBehaviour(behaviour);
        return fragment;
    }

    private ImageView phone;
    private RecyclerView list;

    private MainNewsAdapter adapter;

    @Override
    protected MainContract.Presenter setPresenter()
    {
        return new MainPresenter(this);
    }

    @Override
    protected int setContentView()
    {
        return R.layout.main_screen;
    }

    @Override
    protected void initViews(View v)
    {
        setClickListener(v.findViewById(R.id.menu));
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
                        getPresenter().callDialogOpen();
                        break;
                }
            }
        };
    }

    @Override
    protected void init()
    {
        setClickListener(phone);
        adapter = new MainNewsAdapter(getActivity(), new MainNewsAdapterListener()
        {
            @Override
            public void openServices()
            {
                getBehaviour().openServices();
            }
            @Override
            public void openSubscribe()
            {
                getBehaviour().openSubscribe();
            }
            @Override
            public void openPrices()
            {
                getBehaviour().openPrices();
            }
            @Override
            public void openAllNews()
            {
                getBehaviour().openAllNews();
            }
        });
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        getPresenter().updateNews();
        getPresenter().loadNews();
    }

    @Override
    public void callDialogOpen()
    {
        CallDialog.newInstance(new CallDialogListener()
        {
            @Override
            public void phone()
            {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + getActivity().getResources().getString(R.string.phone_number)));
                startActivity(intent);
            }
            @Override
            public void sms()
            {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:" + getActivity().getResources().getString(R.string.message_number)));
                startActivity(intent);
            }
            @Override
            public void whatsapp()
            {

            }
            @Override
            public void viber()
            {

            }
            @Override
            public void close()
            {
                getPresenter().callDialogClose();
            }
        }).show(getActivity().getSupportFragmentManager(), CallDialog.class.getCanonicalName());
    }

    @Override
    public void callDialogClose()
    {
    }

    @Override
    public void updateNews(final ListModel<NewsCore.OneOfNewsModel> data)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Log.e(this.getClass().getName(), "updateNews " + data.getItemsCount() + " " + Thread.currentThread());
                adapter.swapData(data);
                adapter.notifyDataSetChanged();
            }
        }, 0);
    }
}