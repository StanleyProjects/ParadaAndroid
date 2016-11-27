package ru.parada.app.modules.subscribe.check;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import ru.parada.app.R;
import ru.parada.app.contracts.subscribe.SubscribeCheckContract;
import ru.parada.app.core.SubscribeCore;
import ru.parada.app.modules.subscribe.model.UserData;
import ru.parada.app.units.MVPFragment;

public class SubscribeCheckFragment
        extends MVPFragment<SubscribeCheckContract.Presenter, SubscribeCheckContract.Behaviour>
        implements SubscribeCheckContract.View, SubscribeCore.Mark
{
    static public MVPFragment newInstanse(SubscribeCheckContract.Behaviour behaviour, SubscribeCore.Model data)
    {
        SubscribeCheckFragment fragment = new SubscribeCheckFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DATE, data.getDate());
        bundle.putString(TIME, data.getTime());
        bundle.putString(LASTNAME, data.getLastname());
        bundle.putString(FIRSTNAME, data.getFirstname());
        bundle.putString(MIDDLENAME, data.getMiddlename());
        bundle.putString(EMAIL, data.getEmail());
        bundle.putString(PHONE, data.getPhone());
        bundle.putString(COMMENT, data.getComment());
        fragment.setArguments(bundle);
        fragment.setBehaviour(behaviour);
        return fragment;
    }

    private TextView date_reception;
    private TextView time_reception;
    private TextView email;
    private TextView phone;
    private TextView lfmname;
    private View comment_container;
    private TextView comment;
    private View waiter;

    private SubscribeCore.Model data;
    private boolean sendProcess;

    @Override
    protected SubscribeCheckContract.Presenter setPresenter()
    {
        return new SubscribeCheckPresenter(this);
    }

    @Override
    protected int setContentView()
    {
        return R.layout.subscribe_check_fragment;
    }

    @Override
    protected void initViews(View v)
    {
        date_reception = (TextView)v.findViewById(R.id.date_reception);
        time_reception = (TextView)v.findViewById(R.id.time_reception);
        email = (TextView)v.findViewById(R.id.email);
        phone = (TextView)v.findViewById(R.id.phone);
        lfmname = (TextView)v.findViewById(R.id.lfmname);
        comment_container = v.findViewById(R.id.comment_container);
        comment = (TextView)v.findViewById(R.id.comment);
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
        data = new UserData(getArguments().getString(DATE),
                getArguments().getString(TIME),
                getArguments().getString(LASTNAME),
                getArguments().getString(FIRSTNAME),
                getArguments().getString(MIDDLENAME),
                getArguments().getString(EMAIL),
                getArguments().getString(PHONE),
                getArguments().getString(COMMENT));
        date_reception.setText(data.getDate());
        time_reception.setText(data.getTime());
        email.setText(data.getEmail());
        phone.setText(data.getPhone());
        lfmname.setText(data.getLastname() + "\n" + data.getFirstname() + " " + data.getMiddlename());
        if(data.getComment().length() > 0)
        {
            comment_container.setVisibility(View.VISIBLE);
            comment.setText(data.getComment());
        }
        else
        {
            comment_container.setVisibility(View.GONE);
        }
    }

    @Override
    protected Animation getEnterAnimation()
    {
        return AnimationUtils.loadAnimation(getActivity(), R.anim.rtl);
    }

    @Override
    protected boolean onBackPressed()
    {
        return sendProcess;
    }

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
}