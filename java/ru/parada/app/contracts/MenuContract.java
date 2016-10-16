package ru.parada.app.contracts;

public interface MenuContract
{
    interface View
    {
        void set(ScreenType screenType);
    }

    interface Presenter
    {
        void open(ScreenType screenType);
    }

    interface Behaviour
    {
        void open(ScreenType screenType);
    }
}