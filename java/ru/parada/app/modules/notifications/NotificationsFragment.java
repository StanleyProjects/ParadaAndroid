package ru.parada.app.modules.notifications;

import android.view.View;

import ru.parada.app.R;
import ru.parada.app.contracts.NotificationsContract;
import ru.parada.app.units.MVPFragment;

public class NotificationsFragment
    extends MVPFragment<NotificationsContract.Presenter, NotificationsContract.Behaviour>
    implements NotificationsContract.View
{
    static public NotificationsFragment newInstanse(NotificationsContract.Behaviour behaviour)
    {
        NotificationsFragment fragment = new NotificationsFragment();
        fragment.setBehaviour(behaviour);
        return fragment;
    }

    @Override
    protected NotificationsContract.Presenter setPresenter()
    {
        return new NotificationsPresenter(this);
    }

    @Override
    protected int setContentView()
    {
        return R.layout.notifications_fragment;
    }

    @Override
    protected void initViews(View v)
    {
        setClickListener(v.findViewById(R.id.menu));
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
                }
            }
        };
    }

    @Override
    protected void init()
    {

    }
}