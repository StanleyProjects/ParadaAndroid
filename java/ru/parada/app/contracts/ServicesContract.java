package ru.parada.app.contracts;

import ru.parada.app.units.ListModel;

public interface ServicesContract
{
    interface ListItemModel
    {
        int getId();
        String getPhotoPath();
        String getTitle();
    }

    interface View
    {
        void updateServices(ListModel<ListItemModel> data);
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