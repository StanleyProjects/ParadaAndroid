package ru.parada.app.contracts;

import ru.parada.app.units.ListModel;

public interface DoctorsContract
{
    interface View
    {
        void updateDoctors(ListModel<DoctorDetailContract.Model> data);
    }

    interface Presenter
    {
        void loadDoctors();
        void updateDoctors();
        void searchDoctors(String keys);
    }

    interface Behaviour
    {
        void openMenu();
    }
}