package ru.parada.app.modules.doctors.videos.detail;

import ru.parada.app.contracts.doctors.DoctorVideoDetailContract;
import ru.parada.app.db.SQliteApi;

public class DoctorVideoDetailPresenter
        implements DoctorVideoDetailContract.Presenter
{
    private DoctorVideoDetailContract.View view;

    public DoctorVideoDetailPresenter(DoctorVideoDetailContract.View v)
    {
        this.view = v;
    }

    @Override
    public void update(final int id)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                view.update(SQliteApi.getInstanse().getVideos().getOneFromId(id));
            }
        }).start();
    }
}