package ru.parada.app.contracts;

import ru.parada.app.core.DoctorVideosCore;
import ru.parada.app.units.ListModel;

public interface DoctorVideosContract
{
    interface View
    {
        void update(ListModel<DoctorVideosCore.Model> data);
    }

    interface Presenter
    {
        void update();
    }

    interface Behaviour
    {
        void back();
    }
}