package ru.parada.app.modules.doctorvideodetail;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.parada.app.R;
import ru.parada.app.contracts.DoctorVideoDetailContract;
import ru.parada.app.core.DoctorVideosCore;
import ru.parada.app.managers.FoldersManager;
import ru.parada.app.units.MVPFragment;
import ru.parada.app.utils.AndroidUtil;
import ru.parada.app.utils.ImagesUtils;

public class DoctorVideoDetailFragment
        extends MVPFragment<DoctorVideoDetailContract.Presenter, DoctorVideoDetailContract.Behaviour>
        implements DoctorVideoDetailContract.View, DoctorVideosCore.Mark
{
    static public MVPFragment newInstanse(DoctorVideoDetailContract.Behaviour behaviour, int video_id)
    {
        DoctorVideoDetailFragment fragment = new DoctorVideoDetailFragment();
        fragment.setBehaviour(behaviour);
        Bundle bundle = new Bundle();
        bundle.putInt(VIDEO_ID, video_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private ImageView image;
    private TextView descr;

    @Override
    protected DoctorVideoDetailContract.Presenter setPresenter()
    {
        return new DoctorVideoDetailPresenter(this);
    }

    @Override
    protected int setContentView()
    {
        return R.layout.doctor_video_detail_fragment;
    }

    @Override
    protected void initViews(View v)
    {
        image = (ImageView)v.findViewById(R.id.image);
        descr = (TextView)v.findViewById(R.id.descr);
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
        getPresenter().update(getArguments().getInt(VIDEO_ID));
    }

    @Override
    public void update(final DoctorVideosCore.Model data)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                if(data.getImagePath() != null)
                {
                    ImagesUtils.setThumpImage(FoldersManager.getImagesDirectory() + "/" + data.getImagePath(), image, AndroidUtil.dp(222), AndroidUtil.dp(222));
                }
                descr.setText(Html.fromHtml(data.getDescription()));
            }
        });
    }
}