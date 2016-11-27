package ru.parada.app.modules.requestcall.check;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ru.parada.app.R;
import ru.parada.app.contracts.requestcall.RequestCallCheckContract;
import ru.parada.app.core.RequestCallCore;
import ru.parada.app.modules.requestcall.model.UserData;
import ru.parada.app.units.MVPFragment;

public class RequestCallCheckFragment
        extends MVPFragment<RequestCallCheckContract.Presenter, RequestCallCheckContract.Behaviour>
        implements RequestCallCheckContract.View, RequestCallCore.Mark
{
    static public MVPFragment newInstanse(RequestCallCheckContract.Behaviour behaviour, RequestCallCore.Model data)
    {
        RequestCallCheckFragment fragment = new RequestCallCheckFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NAME, data.getName());
        bundle.putString(PHONE, data.getPhone());
        fragment.setArguments(bundle);
        fragment.setBehaviour(behaviour);
        return fragment;
    }

    private TextView phone;
    private TextView name;
    private View waiter;

    private RequestCallCore.Model data;
    private boolean sendProcess;

    @Override
    public void sendSucess()
    {
        sendProcess = false;
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                getBehaviour().sendSucess();
                waiter.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void sendError()
    {
        sendProcess = false;
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                showToast(R.string.send_subscribe_data_error);
                waiter.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected RequestCallCheckContract.Presenter setPresenter()
    {
        return new RequestCallCheckPresenter(this);
    }

    @Override
    protected int setContentView()
    {
        return R.layout.requestcall_check_fragment;
    }

    @Override
    protected void initViews(View v)
    {
        phone = (TextView)v.findViewById(R.id.phone);
        name = (TextView)v.findViewById(R.id.name);
        waiter = v.findViewById(R.id.waiter);
        setClickListener(v.findViewById(R.id.back), v.findViewById(R.id.send));
    }

    @Override
    protected void onClickView(int id)
    {
        switch(id)
        {
            case R.id.back:
                if(!sendProcess)
                {
                    getBehaviour().back();
                }
                break;
            case R.id.send:
                if(sendProcess)
                {
                    return;
                }
                sendProcess = true;
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if(sendProcess)
                        {
                            waiter.setVisibility(View.VISIBLE);
                        }
                    }
                }, 500);
                getPresenter().send(data);
                break;
        }
    }

    @Override
    protected void init()
    {
        sendProcess = false;
        data = new UserData(getArguments().getString(NAME),
                getArguments().getString(PHONE));
        name.setText(data.getName());
        phone.setText(data.getPhone());
    }

    @Override
    protected boolean onBackPressed()
    {
        return sendProcess;
    }
}