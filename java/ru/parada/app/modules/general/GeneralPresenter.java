package ru.parada.app.modules.general;

import ru.parada.app.contracts.GeneralContract;

public class GeneralPresenter
    implements GeneralContract.Presenter
{
    private GeneralContract.View view;

    public GeneralPresenter(GeneralContract.View v)
    {
        view = v;
    }

    @Override
    public void setMainScreen()
    {
        view.showMainScreen();
    }
}