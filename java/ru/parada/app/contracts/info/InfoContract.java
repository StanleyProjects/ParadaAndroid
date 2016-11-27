package ru.parada.app.contracts.info;

import ru.parada.app.core.InfoCore;
import ru.parada.app.units.ListModel;

public interface InfoContract
{
    interface View
    {
        void load();
        void update(ListModel<InfoCore.Model> data);
    }

    interface Presenter
    {
        void update();
        void load();
    }

    interface Behaviour
    {
        void openMenu();
    }
}