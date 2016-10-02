package ru.parada.app.contracts;

public interface GeneralContract
{
    interface View
    {
        void showMainScreen();
        void showServicesScreen();
        void showDoctorsScreen();
    }

    interface Presenter
    {
        void setMainScreen();
        void setServicesScreen();
        void setDoctorsScreen();
    }
}