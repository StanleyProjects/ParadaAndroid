package ru.parada.app.modules.contacts.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.parada.app.R;
import ru.parada.app.contracts.contacts.ContactDetailContract;
import ru.parada.app.core.ContactsCore;
import ru.parada.app.units.MultiFragment;

public class ContactDetailFragment
        extends MultiFragment<ContactDetailContract.Presenter, ContactDetailContract.Behaviour>
        implements ContactDetailContract.View, ContactsCore.Mark
{
    static public Fragment newInstanse(ContactDetailContract.Behaviour behaviour, int contact)
    {
        ContactDetailFragment fragment = new ContactDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CONTACT, contact);
        fragment.setArguments(bundle);
        fragment.setBehaviour(behaviour);
        return fragment;
    }

    private Fragment mapFragment;

    private TextView name;
    private ImageView image;
    private TextView phone_label;
    private TextView work_time_label;
    private TextView mail_label;
    private TextView address_label;

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
        phone_label = (TextView)v.findViewById(R.id.phone_label);
        work_time_label = (TextView)v.findViewById(R.id.work_time_label);
        mail_label = (TextView)v.findViewById(R.id.mail_label);
        address_label = (TextView)v.findViewById(R.id.address_label);
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
        double latitude = 0;
        double longitude = 0;
        String nm;
        String pl;
        String wl;
        String ml;
        String al;
        int img;
        if(getArguments().getInt(CONTACT) == ContactsCore.Contacts.SURGERY)
        {
            nm = getActivity().getResources().getString(R.string.surgery_name);
            img = R.drawable.menu_logo;
            pl = getActivity().getResources().getString(R.string.surgery_phone);
            wl = getActivity().getResources().getString(R.string.surgery_work_time);
            ml = getActivity().getResources().getString(R.string.surgery_mail);
            al = getActivity().getResources().getString(R.string.surgery_address);
            latitude = 59.938884;
            longitude = 30.223898;
        }
        else if(getArguments().getInt(CONTACT) == ContactsCore.Contacts.BALTMED)
        {
            nm = getActivity().getResources().getString(R.string.baltmed_name);
            img = R.drawable.bm_logo;
            pl = getActivity().getResources().getString(R.string.baltmed_phone);
            wl = getActivity().getResources().getString(R.string.baltmed_work_time);
            ml = getActivity().getResources().getString(R.string.baltmed_mail);
            al = getActivity().getResources().getString(R.string.baltmed_address);
            latitude = 59.941371;
            longitude = 30.223222;
        }
        else
        {
            runAfterResume(new Runnable()
            {
                @Override
                public void run()
                {
                    getBehaviour().back();
                }
            });
            return;
        }
        name.setText(nm);
        image.setImageDrawable(getActivity().getResources().getDrawable(img));
        phone_label.setText(pl);
        work_time_label.setText(wl);
        mail_label.setText(ml);
        address_label.setText(al);
        mapFragment = MapFragment.newInstanse(latitude, longitude);
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