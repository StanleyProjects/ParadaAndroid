package ru.parada.app.contracts;

import ru.parada.app.core.DoctorsCore;

public interface DoctorDetailContract
{
    interface View
    {
        void update(DoctorsCore.DetailModel data);
    }

    interface Presenter
    {
        void update(int id);
    }

    interface Behaviour
    {
        void back();
        void showVideos(int id);
    }
}