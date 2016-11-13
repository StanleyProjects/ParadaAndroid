package ru.parada.app.modules.contacts.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.parada.app.R;
import ru.parada.app.contracts.contacts.ContactDetailContract;
import ru.parada.app.core.ContactsCore;
import ru.parada.app.modules.contacts.model.Contact;
import ru.parada.app.units.MultiFragment;

public class ContactDetailFragment
        extends MultiFragment<ContactDetailContract.Presenter, ContactDetailContract.Behaviour>
        implements ContactDetailContract.View, ContactsCore.Mark
{
    static public Fragment newInstanse(ContactDetailContract.Behaviour behaviour, ContactsCore.Model data)
    {
        ContactDetailFragment fragment = new ContactDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NAME, data.getName());
        bundle.putInt(IMAGE, data.getImage());
        bundle.putDouble(LATITUDE, data.getLatitude());
        bundle.putDouble(LONGITUDE, data.getLongitude());
        fragment.setArguments(bundle);
        fragment.setBehaviour(behaviour);
        return fragment;
    }

    private Fragment mapFragment;

    private TextView name;
    private ImageView image;

    @Override
    protected ContactDetailContract.Presenter setPresenter()
    {
        return null;
    }

    @Override
    protected int setContentView()
    {
        return R.layout.contacts_detail_fragment;
    }

    @Override
    protected void initViews(View v)
    {
        name = (TextView)v.findViewById(R.id.name);
        image = (ImageView)v.findViewById(R.id.image);
        setClickListener(v.findViewById(R.id.back));
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
                }
            }
        };
    }

    @Override
    protected void init()
    {
        ContactsCore.Model data = new Contact(getArguments().getString(NAME),
                getArguments().getInt(IMAGE),
                getArguments().getDouble(LATITUDE),
                getArguments().getDouble(LONGITUDE));
        name.setText(data.getName());
        image.setImageDrawable(getActivity().getResources().getDrawable(data.getImage()));
        mapFragment = MapFragment.newInstanse(data);
        replaceMap();
    }

    @Override
    protected void setScreens(int screen)
    {
        if(screen >= ContactsCore.Screens.MAP)
        {
            replaceMap();
        }
    }
    @Override
    protected int beginScreenIndex()
    {
        return ContactsCore.Screens.MAP;
    }

    private void replaceMap()
    {
        getChildFragmentManager().beginTransaction()
                                 .replace(R.id.subscreen, mapFragment)
                                 .commit();
    }
}