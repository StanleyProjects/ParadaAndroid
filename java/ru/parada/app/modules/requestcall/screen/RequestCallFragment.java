package ru.parada.app.modules.requestcall.screen;

import android.view.View;
import android.widget.EditText;

import ru.parada.app.R;
import ru.parada.app.contracts.requestcall.RequestCallContract;
import ru.parada.app.modules.requestcall.model.UserData;
import ru.parada.app.units.MVPFragment;

public class RequestCallFragment
        extends MVPFragment<RequestCallContract.Presenter, RequestCallContract.Behaviour>
        implements RequestCallContract.View
{
    static public MVPFragment newInstanse(RequestCallContract.Behaviour behaviour)
    {
        RequestCallFragment fragment = new RequestCallFragment();
        fragment.setBehaviour(behaviour);
        return fragment;
    }

    private EditText contactface;
    private EditText phone;

    @Override
    protected RequestCallContract.Presenter setPresenter()
    {
        return null;
    }

    @Override
    protected int setContentView()
    {
        return R.layout.requestcall_fragment;
    }

    @Override
    protected void initViews(View v)
    {
        contactface = (EditText)v.findViewById(R.id.contactface);
        phone = (EditText)v.findViewById(R.id.phone);
        setClickListener(v.findViewById(R.id.back), v.findViewById(R.id.send));
    }

    @Override
    protected void onClickView(int id)
    {
        switch(id)
        {
            case R.id.back:
                getBehaviour().back();
                break;
            case R.id.send:
                sendData();
                break;
        }
    }

    @Override
    protected void init()
    {

    }

    private void sendData()
    {
        final String contactface = this.contactface.getText().toString();
        if(contactface.length() == 0)
        {
            showToast(R.string.contactface_empty);
            return;
        }
        final String phone = this.phone.getText().toString();
        if(phone.length() == 0)
        {
            showToast(R.string.phone_empty);
            return;
        }
        hideKeyBoard();
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                getBehaviour().send(new UserData(contactface, phone));
            }
        }, 100);
    }
}