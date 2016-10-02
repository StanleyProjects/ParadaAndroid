package ru.parada.app.modules.general;

import ru.parada.app.contracts.GeneralContract;

public class GeneralPresenter
    implements GeneralContract.Presenter
{
    private GeneralContract.View view;
    private int currentScreen;

    public GeneralPresenter(GeneralContract.View v)
    {
        view = v;
        currentScreen = Screens.MAIN_SCREEN;
    }

    @Override
    public void setMainScreen()
    {
        if(currentScreen != Screens.MAIN_SCREEN)
        {
            currentScreen = Screens.MAIN_SCREEN;
            view.showMainScreen();
        }
    }

    @Override
    public void setServicesScreen()
    {
        if(currentScreen != Screens.SERVICES_SCREEN)
        {
            currentScreen = Screens.SERVICES_SCREEN;
            view.showServicesScreen();
        }
    }

    @Override
    public void setDoctorsScreen()
    {
        if(currentScreen != Screens.DOCTORS_SCREEN)
        {
            currentScreen = Screens.DOCTORS_SCREEN;
            view.showDoctorsScreen();
        }
    }
}