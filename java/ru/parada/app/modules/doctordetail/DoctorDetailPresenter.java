package ru.parada.app.modules.doctordetail;

import ru.parada.app.contracts.DoctorDetailContract;
import ru.parada.app.core.DoctorsCore;
import ru.parada.app.db.SQliteApi;

public class DoctorDetailPresenter
        implements DoctorDetailContract.Presenter
{
    private DoctorDetailContract.View view;

    public DoctorDetailPresenter(DoctorDetailContract.View v)
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
                updateDoctor(SQliteApi.getInstanse().getDoctors().getOneFromId(id));
            }
        }).start();
    }

    private void updateDoctor(DoctorsCore.DetailModel model)
    {
        view.update(model);
    }
}