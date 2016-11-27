package ru.parada.app.modules.doctors.detail;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import ru.parada.app.App;
import ru.parada.app.R;
import ru.parada.app.contracts.doctors.DoctorDetailContract;
import ru.parada.app.core.DoctorsCore;
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

    private View content;
    private TextView doctor_fullname;
    private DoctorHolder holder;
    private TextView descr;
    private View phone_button;

    private Animation enter;
    private PackageManager packageManager;
    private ClipboardManager clipboard;
    private int doctor_id;
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
        content = v.findViewById(R.id.content);
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
    protected void onClickView(int id)
    {
        switch(id)
        {
            case R.id.back:
                getBehaviour().back();
                break;
            case R.id.phone_button:
                callDialogOpen();
                break;
            case R.id.watch_video:
                getBehaviour().showVideos(doctor_id);
                break;
        }
    }

    private void callDialogOpen()
    {
        CallDialog.newInstance(new CallDialogListener()
        {
            @Override
            public void phone()
            {
                App.getComponent().getAndroidUtil().openPhone(phone);
            }
            @Override
            public void sms()
            {
                App.getComponent().getAndroidUtil().openMessages(phone);
            }
            @Override
            public void whatsapp()
            {
                try
                {
                    packageManager.getPackageInfo(getActivity().getResources()
                                                               .getString(R.string.whatsapp_pack), PackageManager.GET_META_DATA);
                }
                catch(Exception e)
                {
                    Log.e(getClass().getName(), "whatsapp " + e.getMessage());
                    showDialog(getActivity().getResources()
                                            .getString(R.string.whatsapp), getActivity().getResources()
                                                                                        .getString(R.string.whatsapp_open_error), new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {

                        }
                    });
                    return;
                }
                copyClipBoard(phone);
                showDialog(getActivity().getResources()
                                        .getString(R.string.whatsapp), getActivity().getResources()
                                                                                    .getString(R.string.phone_copy_clipboard), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        startActivity(packageManager.getLaunchIntentForPackage(getActivity().getResources()
                                                                                            .getString(R.string.whatsapp_pack)));
                    }
                });
            }
            @Override
            public void viber()
            {
                try
                {
                    packageManager.getPackageInfo(getActivity().getResources()
                                                               .getString(R.string.viber_pack), PackageManager.GET_META_DATA);
                }
                catch(Exception e)
                {
                    Log.e(getClass().getName(), "viber " + e.getMessage());
                    showDialog(getActivity().getResources()
                                            .getString(R.string.viber), getActivity().getResources()
                                                                                     .getString(R.string.viber_open_error), new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {

                        }
                    });
                    return;
                }
                copyClipBoard(phone);
                showDialog(getActivity().getResources()
                                        .getString(R.string.viber), getActivity().getResources()
                                                                                 .getString(R.string.phone_copy_clipboard), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        startActivity(packageManager.getLaunchIntentForPackage(getActivity().getResources()
                                                                                            .getString(R.string.viber_pack)));
                    }
                });
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
        enter = AnimationUtils.loadAnimation(getActivity(), R.anim.enter);
        content.setVisibility(View.GONE);
        packageManager = getActivity().getPackageManager();
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        doctor_id = getArguments().getInt(DOCTOR_ID);
        getPresenter().update(doctor_id);
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
                    holder.setPhoto(App.getComponent().getFoldersManager().getImagesDirectory() + "/" + item.getPhotoPath());
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
                content.setVisibility(View.VISIBLE);
                content.startAnimation(enter);
            }
        });
    }

    private void copyClipBoard(String text)
    {
        ClipData clip = ClipData.newPlainText(getClass().getName(), text);
        clipboard.setPrimaryClip(clip);
    }

    private void showDialog(String title, String message, DialogInterface.OnClickListener listener)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "ОК", listener);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
}