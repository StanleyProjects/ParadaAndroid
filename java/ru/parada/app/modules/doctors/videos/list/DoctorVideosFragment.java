package ru.parada.app.modules.doctors.videos.list;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import ru.parada.app.App;
import ru.parada.app.R;
import ru.parada.app.contracts.doctors.DoctorVideosContract;
import ru.parada.app.core.DoctorVideosCore;
import ru.parada.app.core.DoctorsCore;
import ru.parada.app.modules.doctors.videos.list.adapter.DoctorVideosAdapter;
import ru.parada.app.modules.doctors.videos.list.adapter.DoctorVideosAdapterListener;
import ru.parada.app.units.ListModel;
import ru.parada.app.units.MVPFragment;

public class DoctorVideosFragment
        extends MVPFragment<DoctorVideosContract.Presenter, DoctorVideosContract.Behaviour>
        implements DoctorVideosContract.View, DoctorsCore.Mark
{
    static public MVPFragment newInstanse(DoctorVideosContract.Behaviour behaviour, int doctor_id)
    {
        DoctorVideosFragment fragment = new DoctorVideosFragment();
        fragment.setBehaviour(behaviour);
        Bundle bundle = new Bundle();
        bundle.putInt(DOCTOR_ID, doctor_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private RecyclerView list;
    private TextView list_empty;

    private DoctorVideosAdapter adapter;

    @Override
    protected DoctorVideosContract.Presenter setPresenter()
    {
        return new DoctorVideosPresenter(this);
    }

    @Override
    protected int setContentView()
    {
        return R.layout.doctor_videos;
    }

    @Override
    protected void initViews(View v)
    {
        setClickListener(v.findViewById(R.id.back));
        list = (RecyclerView)v.findViewById(R.id.list);
        list_empty = (TextView)v.findViewById(R.id.list_empty);
    }

    @Override
    protected void onClickView(int id)
    {
        switch(id)
        {
            case R.id.back:
                getBehaviour().back();
                break;
        }
    }

    @Override
    protected void init()
    {
        list.setVisibility(View.GONE);
        list_empty.setVisibility(View.VISIBLE);
        adapter = new DoctorVideosAdapter(getActivity(), new DoctorVideosAdapterListener()
        {
            @Override
            public void getVideo(int id)
            {
                if(!App.getComponent().getAndroidUtil().blockClick())
                {
                    getBehaviour().getVideo(id);
                }
            }
        });
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        getPresenter().update(getArguments().getInt(DOCTOR_ID));
    }

    @Override
    protected Animation getEnterAnimation()
    {
        return AnimationUtils.loadAnimation(getActivity(), R.anim.rtl);
    }

    @Override
    public void update(final ListModel<DoctorVideosCore.Model> data)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                if(data.getItemsCount() > 0)
                {
                    list.setVisibility(View.VISIBLE);
                    list_empty.setVisibility(View.GONE);
                    adapter.swapData(data);
                }
                else
                {
                    list.setVisibility(View.GONE);
                    list_empty.setVisibility(View.VISIBLE);
                    adapter.swapData(null);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}