package ru.parada.app.modules.contacts;

import android.view.View;

import ru.parada.app.R;
import ru.parada.app.contracts.ContactsContract;
import ru.parada.app.units.MVPFragment;

public class ContactsFragment
    extends MVPFragment<ContactsContract.Presenter, ContactsContract.Behaviour>
    implements ContactsContract.View
{
    static public ContactsFragment newInstanse(ContactsContract.Behaviour behaviour)
    {
        ContactsFragment fragment = new ContactsFragment();
        fragment.setBehaviour(behaviour);
        return fragment;
    }

    @Override
    protected ContactsContract.Presenter setPresenter()
    {
        return new ContactsPresenter(this);
    }

    @Override
    protected int setContentView()
    {
        return R.layout.contacts_fragment;
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