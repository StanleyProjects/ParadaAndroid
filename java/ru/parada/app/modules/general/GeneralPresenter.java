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
    public void setMainScreen()
    {
        if(screenType != ScreenType.MAIN_SCREEN)
        {
            screenType = ScreenType.MAIN_SCREEN;
            view.showMainScreen();
        }
    }

    @Override
    public void setServicesScreen()
    {
        if(screenType != ScreenType.SERVICES_SCREEN)
        {
            screenType = ScreenType.SERVICES_SCREEN;
            view.showServicesScreen();
        }
    }

    @Override
    public void setDoctorsScreen()
    {
        if(screenType != ScreenType.DOCTORS_SCREEN)
        {
            screenType = ScreenType.DOCTORS_SCREEN;
            view.showDoctorsScreen();
        }
    }
}