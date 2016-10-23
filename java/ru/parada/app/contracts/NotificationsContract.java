package ru.parada.app.contracts;

import ru.parada.app.units.ListModel;

public interface NotificationsContract
{
    interface Model
    {
        int getId();
        String getMessage();
        long getDate();
    }

    interface View
    {
        void update(ListModel<Model> data);
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