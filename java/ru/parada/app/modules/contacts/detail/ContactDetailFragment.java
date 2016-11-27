package ru.parada.app.modules.contacts.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import ru.parada.app.App;
import ru.parada.app.R;
import ru.parada.app.contracts.contacts.ContactDetailContract;
import ru.parada.app.core.ContactsCore;
import ru.parada.app.modules.map.MapActivity;
import ru.parada.app.modules.map.MapFragment;
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

    private TextView name;
    private ImageView image;
    private TextView phone_label;
    private TextView work_time_label;
    private TextView mail_label;
    private TextView web_label;
    private TextView address_label;

    private double latitude = 0;
    private double longitude = 0;
    private String phoneToCall;
    private String link;
    private String ml;

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
        web_label = (TextView)v.findViewById(R.id.web_label);
        address_label = (TextView)v.findViewById(R.id.address_label);
        setClickListener(v.findViewById(R.id.back), v.findViewById(R.id.mapforeground),
                v.findViewById(R.id.phone),
                v.findViewById(R.id.mail),
                v.findViewById(R.id.web),
                v.findViewById(R.id.address));
    }

    @Override
    protected void onClickView(int id)
    {
        switch(id)
        {
            case R.id.back:
                getBehaviour().back();
                break;
            case R.id.mapforeground:
                getActivity().startActivity(MapActivity.createIntent(getActivity(), latitude, longitude));
                break;
            case R.id.phone:
                App.getComponent().getAndroidUtil().openPhone(phoneToCall);
                break;
            case R.id.mail:
                App.getComponent().getAndroidUtil().openMail(ml);
                break;
            case R.id.web:
                App.getComponent().getAndroidUtil().openBrowser(link);
                break;
        }
    }

    @Override
    protected void init()
    {
        String nm;
        String pl;
        String wl;
        String web;
        String al;
        int img;
        if(getArguments().getInt(CONTACT) == ContactsCore.Contacts.SURGERY)
        {
            nm = getActivity().getResources().getString(R.string.surgery_name);
            img = R.drawable.menu_logo;
            pl = getActivity().getResources().getString(R.string.surgery_phone);
            wl = getActivity().getResources().getString(R.string.surgery_work_time);
            ml = getActivity().getResources().getString(R.string.surgery_mail);
            web = getActivity().getResources().getString(R.string.surgery_web);
            al = getActivity().getResources().getString(R.string.surgery_address);
            latitude = 59.938884;
            longitude = 30.223898;
            phoneToCall = getActivity().getResources().getString(R.string.surgery_phone_to_call);
            link = getActivity().getResources().getString(R.string.surgery_link);
        }
        else if(getArguments().getInt(CONTACT) == ContactsCore.Contacts.BALTMED)
        {
            nm = getActivity().getResources().getString(R.string.baltmed_name);
            img = R.drawable.bm_logo;
            pl = getActivity().getResources().getString(R.string.baltmed_phone);
            wl = getActivity().getResources().getString(R.string.baltmed_work_time);
            ml = getActivity().getResources().getString(R.string.baltmed_mail);
            web = getActivity().getResources().getString(R.string.baltmed_web);
            al = getActivity().getResources().getString(R.string.baltmed_address);
            latitude = 59.941371;
            longitude = 30.223222;
            phoneToCall = getActivity().getResources().getString(R.string.baltmed_phone_to_call);
            link = getActivity().getResources().getString(R.string.baltmed_link);
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
        web_label.setText(web);
        address_label.setText(al);
    }

    @Override
    protected Animation getEnterAnimation()
    {
        return AnimationUtils.loadAnimation(getActivity(), R.anim.rtl);
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
                                 .replace(R.id.subscreen, MapFragment.newInstanse(latitude, longitude))
                                 .commit();
    }
}