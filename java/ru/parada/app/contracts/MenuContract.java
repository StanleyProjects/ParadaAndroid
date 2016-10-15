package ru.parada.app.contracts;

public interface MenuContract
{
    interface View
    {
        void setMain();
        void setServices();
        void setDoctors();
        void setPrices();
    }

    interface Presenter
    {
        void openMain();
        void openServices();
        void openDoctors();
        void openPrices();
    }

    interface Behaviour
    {
        void openMain();
        void openServices();
        void openDoctors();
        void openPrices();
    }
}