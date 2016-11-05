package ru.parada.app.contracts;

import ru.parada.app.core.ServicesCore;
import ru.parada.app.units.ListModel;

public interface ServicesContract
{
    interface View
    {
        void updateServices(ListModel<ServicesCore.Model> data);
    }

    interface Presenter
    {
        void loadServices();
        void updateServices();
    }

    interface Behaviour
    {
        void openMenu();
    }
}