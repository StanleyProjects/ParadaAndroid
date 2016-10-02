package ru.parada.app.contracts;

public interface MenuContract
{
    interface View
    {
        void setMain();
        void setServices();
        void setDoctors();
    }

    interface Presenter
    {
        void openMain();
        void openServices();
        void openDoctors();
    }
}