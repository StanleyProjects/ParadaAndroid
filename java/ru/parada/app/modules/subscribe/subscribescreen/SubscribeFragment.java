package ru.parada.app.modules.subscribe.subscribescreen;

import android.view.View;
import android.widget.EditText;

import ru.parada.app.R;
import ru.parada.app.contracts.subscribe.SubscribeContract;
import ru.parada.app.modules.subscribe.model.UserData;
import ru.parada.app.units.MVPFragment;

public class SubscribeFragment
        extends MVPFragment<SubscribeContract.Presenter, SubscribeContract.Behaviour>
        implements SubscribeContract.View
{
    static public MVPFragment newInstanse(SubscribeContract.Behaviour behaviour)
    {
        SubscribeFragment fragment = new SubscribeFragment();
        fragment.setBehaviour(behaviour);
        return fragment;
    }

    private EditText wish_date;
    private EditText wish_time;
    private EditText last_name;
    private EditText first_name;
    private EditText middle_name;
    private EditText email;
    private EditText phone;
    private EditText comment;

    @Override
    protected SubscribeContract.Presenter setPresenter()
    {
        return new SubscribePresenter(this);
    }

    @Override
    protected int setContentView()
    {
        return R.layout.subscribe_fragment;
    }

    @Override
    protected void initViews(View v)
    {
        wish_date = (EditText)v.findViewById(R.id.wish_date);
        wish_time = (EditText)v.findViewById(R.id.wish_time);
        last_name = (EditText)v.findViewById(R.id.last_name);
        first_name = (EditText)v.findViewById(R.id.first_name);
        middle_name = (EditText)v.findViewById(R.id.middle_name);
        email = (EditText)v.findViewById(R.id.email);
        phone = (EditText)v.findViewById(R.id.phone);
        comment = (EditText)v.findViewById(R.id.comment);
        setClickListener(v.findViewById(R.id.back), v.findViewById(R.id.send));
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
                    case R.id.send:
                        sendData();
                        break;
                }
            }
        };
    }

    @Override
    protected void init()
    {

    }

    private void sendData()
    {
        String wish_date = this.wish_date.getText().toString();
        String wish_time = this.wish_time.getText().toString();
        String last_name = this.last_name.getText().toString();
        if(last_name.length() == 0)
        {
            showToast(R.string.last_name_empty);
            return;
        }
        String first_name = this.first_name.getText().toString();
        if(first_name.length() == 0)
        {
            showToast(R.string.first_name_empty);
            return;
        }
        String middle_name = this.middle_name.getText().toString();
        String email = this.email.getText().toString();
        String phone = this.phone.getText().toString();
        if(phone.length() == 0)
        {
            showToast(R.string.phone_empty);
            return;
        }
        String comment = this.comment.getText().toString();
        getBehaviour().send(new UserData(wish_date, wish_time, last_name, first_name, middle_name, email, phone, comment));
    }
}