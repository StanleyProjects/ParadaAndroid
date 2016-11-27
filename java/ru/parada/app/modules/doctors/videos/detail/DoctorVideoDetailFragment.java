package ru.parada.app.modules.doctors.videos.detail;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.parada.app.App;
import ru.parada.app.R;
import ru.parada.app.contracts.doctors.DoctorVideoDetailContract;
import ru.parada.app.core.DoctorVideosCore;
import ru.parada.app.modules.player.PlayerActivity;
import ru.parada.app.units.MVPFragment;

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

    private DoctorVideosCore.Model videoData;

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
        setClickListener(v.findViewById(R.id.back), v.findViewById(R.id.play));
    }

    @Override
    protected void onClickView(int id)
    {
        switch(id)
        {
            case R.id.back:
                getBehaviour().back();
                break;
            case R.id.play:
                getActivity().startActivity(PlayerActivity.createIntent(getActivity(), videoData.getLink()));
                break;
        }
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
                videoData = data;
                if(data.getImagePath() != null)
                {
                    image.setImageDrawable(App.getComponent().getImagesUtil().getFromCache(
                            App.getComponent().getFoldersManager().getImagesDirectory() + "/" + data.getImagePath()));
                }
                descr.setText(Html.fromHtml(data.getDescription()));
            }
        });
    }
}