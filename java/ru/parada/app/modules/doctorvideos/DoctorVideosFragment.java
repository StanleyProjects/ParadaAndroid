package ru.parada.app.modules.doctorvideos;

import android.os.Bundle;
import android.view.View;

import ru.parada.app.R;
import ru.parada.app.contracts.DoctorVideosContract;
import ru.parada.app.core.DoctorVideosCore;
import ru.parada.app.core.DoctorsCore;
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
        getPresenter().update();
    }

    @Override
    public void update(ListModel<DoctorVideosCore.Model> data)
    {

    }
}