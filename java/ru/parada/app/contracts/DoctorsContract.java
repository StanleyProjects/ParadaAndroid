package ru.parada.app.contracts;

import ru.parada.app.units.ListModel;

public interface DoctorsContract
{
    interface ListItemModel
    {
        int getId();
        String getLastName();
    }

    interface View
    {
        void showWaiter();
        void hideWaiter();
        void updateDoctors(ListModel<ListItemModel> data);
    }

    interface Presenter
    {
        void loadDoctors();
    }
}