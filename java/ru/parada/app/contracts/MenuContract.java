package ru.parada.app.contracts;

import ru.parada.app.core.GeneralCore;

public interface MenuContract
{
    interface View
    {
        void set(GeneralCore.ScreenType screenType);
    }

    interface Presenter
    {
        void open(GeneralCore.ScreenType screenType);
    }

    interface Behaviour
    {
        void open(GeneralCore.ScreenType screenType);
    }

    interface Callback
    {
        void open(GeneralCore.ScreenType screenType);
    }
}