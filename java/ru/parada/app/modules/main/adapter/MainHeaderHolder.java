package ru.parada.app.modules.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import ru.parada.app.App;
import ru.parada.app.R;
import ru.parada.app.contracts.MainContract;
import ru.parada.app.units.AdapterHolder;

public class MainHeaderHolder
        extends AdapterHolder
{
    private volatile boolean click;
    private MainContract.HeaderBehaviour behaviour;
    private View.OnClickListener clickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if(!click)
            {
                return;
            }
            click = false;
            switch(v.getId())
            {
                case R.id.services:
                    behaviour.openServices();
                    break;
                case R.id.subscribe:
                    behaviour.openSubscribe();
                    break;
                case R.id.prices:
                    behaviour.openPrices();
                    break;
            }
            App.getComponent().getAndroidUtil().runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    click = true;
                }
            }, 500);
        }
    };

    public MainHeaderHolder(Context context, ViewGroup parent, MainContract.HeaderBehaviour b)
    {
        super(context, parent, R.layout.main_header);
        this.behaviour = b;
        click = true;
        itemView.findViewById(R.id.services).setOnClickListener(clickListener);
        itemView.findViewById(R.id.subscribe).setOnClickListener(clickListener);
        itemView.findViewById(R.id.prices).setOnClickListener(clickListener);
    }
}