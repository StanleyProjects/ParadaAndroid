package ru.parada.app.contracts;

public interface EventsContract
{
    interface View
    {
        void showNews();
        void showActions();
    }

    interface Presenter
    {
        void setNews();
        void setActions();
    }

    interface Behaviour
    {
        void openMenu();
    }
}