package ru.parada.app.modules.doctors.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.parada.app.R;
import ru.parada.app.contracts.doctors.DoctorDetailContract;
import ru.parada.app.core.DoctorsCore;
import ru.parada.app.managers.FoldersManager;
import ru.parada.app.modules.call.CallDialog;
import ru.parada.app.modules.call.CallDialogListener;
import ru.parada.app.modules.doctors.DoctorHolder;
import ru.parada.app.units.MVPFragment;

public class DoctorDetailFragment
        extends MVPFragment<DoctorDetailContract.Presenter, DoctorDetailContract.Behaviour>
        implements DoctorDetailContract.View, DoctorsCore.Mark
{
    static public MVPFragment newInstanse(DoctorDetailContract.Behaviour behaviour, int doctor_id)
    {
        DoctorDetailFragment fragment = new DoctorDetailFragment();
        fragment.setBehaviour(behaviour);
        Bundle bundle = new Bundle();
        bundle.putInt(DOCTOR_ID, doctor_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private TextView doctor_fullname;
    private DoctorHolder holder;
    private TextView descr;
    private View phone_button;

    private int id;
    private String phone;

    @Override
    protected DoctorDetailContract.Presenter setPresenter()
    {
        return new DoctorDetailPresenter(this);
    }

    @Override
    protected int setContentView()
    {
        return R.layout.doctor_detail_fragment;
    }

    @Override
    protected void initViews(View v)
    {
        doctor_fullname = (TextView)v.findViewById(R.id.doctor_fullname);
        holder = new DoctorHolder(getActivity(), (ImageView)v.findViewById(R.id.photo),
                (TextView)v.findViewById(R.id.last_name),
                (TextView)v.findViewById(R.id.first_middle_name),
                (TextView)v.findViewById(R.id.first_position),
                (TextView)v.findViewById(R.id.second_position),
                (TextView)v.findViewById(R.id.third_position),
                getActivity().getResources().getDrawable(R.drawable.photo_placeholder));
        descr = (TextView)v.findViewById(R.id.descr);
        phone_button = v.findViewById(R.id.phone_button);
        setClickListener(v.findViewById(R.id.back), v.findViewById(R.id.phone_button), v.findViewById(R.id.watch_video));
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
                    case R.id.phone_button:
                        callDialogOpen();
                        break;
                    case R.id.watch_video:
                        getBehaviour().showVideos(id);
                        break;
                }
            }
        };
    }

    private void callDialogOpen()
    {
        CallDialog.newInstance(new CallDialogListener()
        {
            @Override
            public void phone()
            {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            }
            @Override
            public void sms()
            {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:" + phone));
                startActivity(intent);
            }
            @Override
            public void whatsapp()
            {

            }
            @Override
            public void viber()
            {

            }
            @Override
            public void close()
            {
            }
        }).show(getActivity().getSupportFragmentManager(), CallDialog.class.getCanonicalName());
    }

    @Override
    protected void init()
    {
        id = getArguments().getInt(DOCTOR_ID);
        getPresenter().update(id);
    }

    @Override
    public void update(final DoctorsCore.Model item)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                phone = item.getPhone();
                if(phone == null || phone.length() == 0)
                {
                    phone_button.setVisibility(View.GONE);
                }
                else
                {
                    phone_button.setVisibility(View.VISIBLE);
                }
                doctor_fullname.setText(item.getLastName() + " " + item.getFirstName() + " " + item.getMiddleName());
                if(item.getPhotoPath() != null)
                {
                    holder.setPhoto(FoldersManager.getImagesDirectory() + "/" + item.getPhotoPath());
                }
                else
                {
                    holder.setPhotoPlaceHolder();
                }
                holder.setLastName(item.getLastName());
                holder.setFirstMiddleName(item.getFirstName(), item.getMiddleName());
                holder.setFirstPosition(item.getFirstPosition());
                holder.setSecondPosition(item.getSecondPosition());
                holder.setThirdPosition(item.getThirdPosition());
                descr.setText(Html.fromHtml(item.getDescription()));
//                phone = item.getPhotoPath()
            }
        });
    }
}