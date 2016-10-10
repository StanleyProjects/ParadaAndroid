package ru.parada.app.modules.main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import ru.parada.app.R;
import ru.parada.app.contracts.MainContract;
import ru.parada.app.units.AdapterHolder;

public class MainFooterHolder
        extends AdapterHolder
{
    private MainContract.FooterBehaviour behaviour;

    public MainFooterHolder(Context context, ViewGroup parent, MainContract.FooterBehaviour b)
    {
        super(context, parent, R.layout.main_footer);
        this.behaviour = b;
        itemView.findViewById(R.id.all_news).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                behaviour.openAllNews();
            }
        });
    }
}