package ru.parada.app.modules.menu;

import ru.parada.app.contracts.MenuContract;

public class MenuPresenter
    implements MenuContract.Presenter
{
    private MenuContract.View view;

    public MenuPresenter(MenuContract.View v)
    {
        this.view = v;
    }

    @Override
    public void openMain()
    {
        view.setMain();
    }

    @Override
    public void openServices()
    {
        view.setServices();
    }

    @Override
    public void openDoctors()
    {
        view.setDoctors();
    }

    @Override
    public void openPrices()
    {

    }
}