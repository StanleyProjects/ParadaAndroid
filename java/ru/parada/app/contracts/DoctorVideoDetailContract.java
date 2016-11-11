package ru.parada.app.contracts;

import ru.parada.app.core.DoctorVideosCore;

public interface DoctorVideoDetailContract
{
    interface View
    {
        void update(DoctorVideosCore.Model data);
    }

    interface Presenter
    {
        void update(int id);
    }

    interface Behaviour
    {
        void back();
    }
}