package ru.parada.app.contracts;

public interface ServicesContract
{
    interface View
    {
        void updateServices();
    }

    interface Presenter
    {
        void loadServices();
    }

    interface Behaviour
    {
        void openMenu();
    }
}