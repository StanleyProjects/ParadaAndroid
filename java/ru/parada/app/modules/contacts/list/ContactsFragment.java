package ru.parada.app.modules.contacts.list;

import android.support.v4.app.Fragment;
import android.view.View;

import ru.parada.app.R;
import ru.parada.app.contracts.contacts.ContactDetailContract;
import ru.parada.app.contracts.contacts.ContactsContract;
import ru.parada.app.core.ContactsCore;
import ru.parada.app.modules.contacts.detail.ContactDetailFragment;
import ru.parada.app.units.MultiFragment;

public class ContactsFragment
    extends MultiFragment<ContactsContract.Presenter, ContactsContract.Behaviour>
    implements ContactsContract.View
{
    static public Fragment newInstanse(ContactsContract.Behaviour behaviour)
    {
        ContactsFragment fragment = new ContactsFragment();
        fragment.setBehaviour(behaviour);
        return fragment;
    }

    private Fragment detailFragment;

    private final ContactDetailContract.Behaviour contactDetailBehaviour = new ContactDetailContract.Behaviour()
    {
        @Override
        public void back()
        {
            getChildFragmentManager().popBackStack();
            detailFragment = null;
        }
    };

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
        setClickListener(v.findViewById(R.id.menu),
                v.findViewById(R.id.plastic_surgery_center),
                v.findViewById(R.id.baltmed));
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
                    case R.id.plastic_surgery_center:
                        detailFragment = ContactDetailFragment.newInstanse(contactDetailBehaviour, ContactsCore.Contacts.SURGERY);
                        addSubscreen(detailFragment);
                        break;
                    case R.id.baltmed:
                        detailFragment = ContactDetailFragment.newInstanse(contactDetailBehaviour, ContactsCore.Contacts.BALTMED);
                        addSubscreen(detailFragment);
                        break;
                }
            }
        };
    }

    @Override
    protected void init()
    {
    }

    @Override
    protected void setScreens(int screen)
    {
        if(screen >= ContactsCore.Screens.DETAIL)
        {
            addSubscreen(detailFragment);
        }
    }

    private void addSubscreen(Fragment fragment)
    {
        getChildFragmentManager().beginTransaction()
                                 .add(R.id.subscreen, fragment)
                                 .addToBackStack(fragment.getClass().getName())
                                 .commit();
    }
}