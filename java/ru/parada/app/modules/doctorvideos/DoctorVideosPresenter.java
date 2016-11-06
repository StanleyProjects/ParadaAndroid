package ru.parada.app.modules.doctorvideos;

import ru.parada.app.contracts.DoctorVideosContract;

public class DoctorVideosPresenter
    implements DoctorVideosContract.Presenter
{
    private DoctorVideosContract.View view;

    public DoctorVideosPresenter(DoctorVideosContract.View v)
    {
        view = v;
    }

    @Override
    public void update()
    {

    }
}