package ru.parada.app.contracts;

import ru.parada.app.core.DoctorsCore;

public interface DoctorDetailContract
{
    interface Mark
    {
        String DOCTOR_ID = Mark.class.getCanonicalName().toLowerCase().replace(".", "_") + "_" + "doctor_id";
    }

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
    }
}