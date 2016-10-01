package ru.parada.app.contracts;

public interface MenuContract
{
    interface View
    {
        void setMain();
        void setService();
    }

    interface Presenter
    {
        void openMain();
        void openService();
    }
}