package ru.parada.app.contracts.doctors;

import ru.parada.app.core.DoctorsCore;
import ru.parada.app.units.ListModel;

public interface DoctorsContract
{
    interface View
    {
        void load();
        void update(ListModel<DoctorsCore.Model> data);
    }

    interface Presenter
    {
        void load();
        void update();
        void search(String keys);
    }

    interface Behaviour
    {
        void openMenu();
    }
}