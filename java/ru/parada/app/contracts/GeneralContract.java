package ru.parada.app.contracts;

public interface GeneralContract
{
    interface View
    {
        void showMainScreen();
    }

    interface Presenter
    {
        void setMainScreen();
    }
}