package ru.parada.app.contracts;

public interface GeneralContract
{
    interface View
    {
        void showMainScreen();
        void showServiceScreen();
    }

    interface Presenter
    {
        void setMainScreen();
        void setServiceScreen();
    }
}