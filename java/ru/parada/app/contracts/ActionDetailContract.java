package ru.parada.app.contracts;

import ru.parada.app.core.ActionsCore;

public interface ActionDetailContract
{
    interface Mark
    {
        String ACTION_ID = Mark.class.getCanonicalName().toLowerCase().replace(".", "_") + "_" + "action_id";
    }

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