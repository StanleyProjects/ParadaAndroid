package ru.parada.app.modules.menu;

import ru.parada.app.contracts.MenuContract;
import ru.parada.app.contracts.ScreenType;

public class MenuPresenter
    implements MenuContract.Presenter
{
    private MenuContract.View view;

    public MenuPresenter(MenuContract.View v)
    {
        this.view = v;
    }

    @Override
    public void open(ScreenType screenType)
    {
        view.set(screenType);
    }
}