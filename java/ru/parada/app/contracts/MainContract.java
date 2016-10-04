package ru.parada.app.contracts;

public interface MainContract
{
    interface View
    {
        void phoneOpen();
        void phoneClose();
    }

    interface Presenter
    {
        void phoneSwitch();
        void loadNews();
    }

    interface Behaviour
    {
        void openMenu();
        void openService();
    }
}