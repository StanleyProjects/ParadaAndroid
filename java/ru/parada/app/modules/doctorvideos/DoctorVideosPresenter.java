package ru.parada.app.modules.doctorvideos;

import ru.parada.app.contracts.DoctorVideosContract;
import ru.parada.app.db.SQliteApi;

public class DoctorVideosPresenter
    implements DoctorVideosContract.Presenter
{
    private DoctorVideosContract.View view;

    public DoctorVideosPresenter(DoctorVideosContract.View v)
    {
        view = v;
    }

    @Override
    public void update(final int id)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                view.update(SQliteApi.getInstanse().getVideos().getAllFromDoctorId(id));
            }
        }).start();
    }
}