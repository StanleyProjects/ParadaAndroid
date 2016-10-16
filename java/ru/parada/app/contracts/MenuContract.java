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

    interface MenuBehaviour
        extends Behaviour
    {
        void setCallback(Callback callback);
    }
    interface Behaviour
    {
        void open(ScreenType screenType);
    }

    interface Callback
    {
        void open(ScreenType screenType);
    }
}