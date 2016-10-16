package ru.parada.app.contracts;

public interface GeneralContract
{
    interface View
    {
        void showScreen(ScreenType screenType);
    }

    interface Presenter
    {
        void setScreen(ScreenType screenType);
    }
}