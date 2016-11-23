package ru.parada.app.modules.main;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import ru.parada.app.App;
import ru.parada.app.R;
import ru.parada.app.contracts.MainContract;
import ru.parada.app.core.NewsCore;
import ru.parada.app.modules.call.CallDialog;
import ru.parada.app.modules.call.CallDialogListener;
import ru.parada.app.modules.main.adapter.MainNewsAdapter;
import ru.parada.app.modules.main.adapter.MainNewsAdapterListener;
import ru.parada.app.units.ListModel;
import ru.parada.app.units.MVPFragment;

public class MainFragment
        extends MVPFragment<MainContract.Presenter, MainContract.MainBehaviour>
        implements MainContract.View
{
    static public MVPFragment newInstanse(MainContract.MainBehaviour behaviour)
    {
        MainFragment fragment = new MainFragment();
        fragment.setBehaviour(behaviour);
        return fragment;
    }

    private ImageView phone;
    private RecyclerView list;

    private MainNewsAdapter adapter;
    private PackageManager packageManager;
    private ClipboardManager clipboard;

    @Override
    protected MainContract.Presenter setPresenter()
    {
        return new MainPresenter(this);
    }

    @Override
    protected int setContentView()
    {
        return R.layout.main_screen;
    }

    @Override
    protected void initViews(View v)
    {
        setClickListener(v.findViewById(R.id.menu));
        phone = (ImageView)v.findViewById(R.id.phone);
        list = (RecyclerView)v.findViewById(R.id.list);
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
                    case R.id.phone:
                        getPresenter().callDialogOpen();
                        break;
                }
            }
        };
    }

    @Override
    protected void init()
    {
        packageManager = getActivity().getPackageManager();
        clipboard = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        setClickListener(phone);
        adapter = new MainNewsAdapter(getActivity(), new MainNewsAdapterListener()
        {
            @Override
            public void oneOfNews(int id)
            {
                getBehaviour().oneOfNews(id);
            }

            @Override
            public void openServices()
            {
                getBehaviour().openServices();
            }

            @Override
            public void openSubscribe()
            {
                getBehaviour().openSubscribe();
            }

            @Override
            public void openPrices()
            {
                getBehaviour().openPrices();
            }

            @Override
            public void openAllNews()
            {
                getBehaviour().openAllNews();
            }
        });
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        getPresenter().update();
        getPresenter().load();
    }

    @Override
    public void callDialogOpen()
    {
        CallDialog.newInstance(new CallDialogListener()
        {
            @Override
            public void phone()
            {
                App.getComponent().getAndroidUtil().openPhone(getActivity().getResources()
                                                                           .getString(R.string.phone_number));
            }

            @Override
            public void sms()
            {
                App.getComponent().getAndroidUtil().openMessages(getActivity().getResources()
                                                                           .getString(R.string.message_number));
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
                copyClipBoard(getActivity().getResources()
                                           .getString(R.string.message_number));
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
                copyClipBoard(getActivity().getResources()
                                           .getString(R.string.message_number));
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
                getPresenter().callDialogClose();
            }
        })
                  .show(getActivity().getSupportFragmentManager(), CallDialog.class.getCanonicalName());
    }

    @Override
    public void callDialogClose()
    {
    }

    @Override
    public void update(final ListModel<NewsCore.Model> data)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Log.e(this.getClass()
                          .getName(), "updateNews " + data.getItemsCount() + " " + Thread.currentThread());
                adapter.swapData(data);
                adapter.notifyDataSetChanged();
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