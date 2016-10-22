package ru.parada.app.contracts;

import ru.parada.app.core.ActionsCore;
import ru.parada.app.units.ListModel;

public interface ActionsContract
{
    interface View
    {
        void update(ListModel<ActionsCore.ActionModel> data);
    }

    interface Presenter
    {
        void update();
        void load();
    }

    interface Behaviour
    {
    }
}