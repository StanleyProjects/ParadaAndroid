package ru.parada.app.contracts.actions;

import ru.parada.app.core.ActionsCore;

public interface ActionDetailContract
{
    interface View
    {
        void update(ActionsCore.Model data);
    }

    interface Presenter
    {
        void update(int id);
    }

    interface Behaviour
    {
        void back();
    }
}