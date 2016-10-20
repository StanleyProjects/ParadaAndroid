package ru.parada.app.modules.general;

import ru.parada.app.contracts.GeneralContract;
import ru.parada.app.core.GeneralCore;

public class GeneralPresenter
    implements GeneralContract.Presenter
{
    private GeneralContract.View view;
    private GeneralCore.ScreenType screenType;

    public GeneralPresenter(GeneralContract.View v)
    {
        view = v;
        screenType = GeneralCore.ScreenType.MAIN_SCREEN;
    }

    @Override
    public void setScreen(GeneralCore.ScreenType st)
    {
        if(screenType != st)
        {
            screenType = st;
            view.showScreen(screenType);
        }
    }
}