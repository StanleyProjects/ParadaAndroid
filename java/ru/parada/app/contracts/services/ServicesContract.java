package ru.parada.app.contracts.services;

import ru.parada.app.core.ServicesCore;
import ru.parada.app.units.ListModel;

public interface ServicesContract
{
    interface View
    {
        void load();
        void update(ListModel<ServicesCore.Model> data);
    }

    interface Presenter
    {
        void load();
        void update();
    }

    interface Behaviour
    {
        void openMenu();
    }
}