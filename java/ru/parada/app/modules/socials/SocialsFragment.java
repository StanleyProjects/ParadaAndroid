package ru.parada.app.modules.socials;

import android.view.View;

import ru.parada.app.R;
import ru.parada.app.contracts.SocialsContract;
import ru.parada.app.units.MVPFragment;

public class SocialsFragment
    extends MVPFragment<SocialsContract.Presenter, SocialsContract.Behaviour>
    implements SocialsContract.View
{
    static public SocialsFragment newInstanse(SocialsContract.Behaviour behaviour)
    {
        SocialsFragment fragment = new SocialsFragment();
        fragment.setBehaviour(behaviour);
        return fragment;
    }

    @Override
    protected SocialsContract.Presenter setPresenter()
    {
        return new SocialsPresenter(this);
    }

    @Override
    protected int setContentView()
    {
        return R.layout.socials_fragment;
    }

    @Override
    protected void initViews(View v)
    {
        setClickListener(v.findViewById(R.id.menu),
                v.findViewById(R.id.facebook),
                v.findViewById(R.id.vkontakte),
                v.findViewById(R.id.youtube),
                v.findViewById(R.id.instagram));
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
                    case R.id.facebook:
                        break;
                    case R.id.vkontakte:
                        break;
                    case R.id.youtube:
                        break;
                    case R.id.instagram:
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