package ru.parada.app.modules.main;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ru.parada.app.R;
import ru.parada.app.contracts.MainContract;

public class MainFragment
        extends Fragment
    implements MainContract.View
{
    static public MainFragment newInstanse(MainFragmentListener l)
    {
        MainFragment fragment = new MainFragment();
        fragment.listener = l;
        return fragment;
    }

    private ImageView phone;

    private MainContract.Presenter presenter;
    private MainFragmentListener listener;
    private Drawable btn_phone;
    private Drawable btn_phone_close;
    private View.OnClickListener clickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch(v.getId())
            {
                case R.id.phone:
                    presenter.phoneSwitch();
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.main_fragment, container, false);
        initViews(v);
        init();
        return v;
    }
    private void initViews(View v)
    {
        v.findViewById(R.id.menu).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                listener.openMenu();
            }
        });
        phone = (ImageView)v.findViewById(R.id.phone);
    }
    private void init()
    {
        presenter = new MainPresenter(this);
        phone.setOnClickListener(clickListener);
        btn_phone = getActivity().getResources().getDrawable(R.drawable.btn_phone);
        btn_phone_close = getActivity().getResources().getDrawable(R.drawable.btn_phone_close);
        phoneOpen();
        presenter.loadNews();
    }

    @Override
    public void phoneOpen()
    {
        phone.setImageDrawable(btn_phone);
    }

    @Override
    public void phoneClose()
    {
        phone.setImageDrawable(btn_phone_close);
    }

    public interface MainFragmentListener
    {
        void openMenu();
    }
}