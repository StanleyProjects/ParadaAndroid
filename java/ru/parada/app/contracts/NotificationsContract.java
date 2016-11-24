package ru.parada.app.contracts;

import ru.parada.app.core.NotificationsCore;
import ru.parada.app.units.ListModel;

public interface NotificationsContract
{
    interface View
    {
        void update(ListModel<NotificationsCore.Model> data);
        void loadFail();
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