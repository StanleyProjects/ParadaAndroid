package ru.parada.app.modules.general;

import ru.parada.app.contracts.GeneralContract;
import ru.parada.app.contracts.ScreenType;

public class GeneralPresenter
    implements GeneralContract.Presenter
{
    private GeneralContract.View view;
    private ScreenType screenType;

    public GeneralPresenter(GeneralContract.View v)
    {
        view = v;
        screenType = ScreenType.MAIN_SCREEN;
    }

    @Override
    public void setScreen(ScreenType st)
    {
        if(screenType != st)
        {
            screenType = st;
            view.showScreen(screenType);
        }
    }
}