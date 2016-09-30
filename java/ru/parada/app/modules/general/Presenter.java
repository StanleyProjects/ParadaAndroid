package ru.parada.app.modules.general;

import ru.parada.app.mvp.presenters.GeneralPresenter;
import ru.parada.app.mvp.views.GeneralView;

public class Presenter
    implements GeneralPresenter
{
    private GeneralView view;

    public Presenter(GeneralView v)
    {
        view = v;
        view.onCreate();
    }

    @Override
    public void setMainScreen()
    {
        view.showMainScreen();
    }
}