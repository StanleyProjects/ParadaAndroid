package ru.parada.app.contracts;

public interface ServiceContract
{
    interface View
    {
        void updateServices();
    }

    interface Presenter
    {
        void loadServices();
    }
}