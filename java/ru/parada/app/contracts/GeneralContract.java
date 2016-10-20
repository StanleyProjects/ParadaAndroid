package ru.parada.app.contracts;

import ru.parada.app.core.GeneralCore;

public interface GeneralContract
{
    interface View
    {
        void showScreen(GeneralCore.ScreenType screenType);
    }

    interface Presenter
    {
        void setScreen(GeneralCore.ScreenType screenType);
    }
}