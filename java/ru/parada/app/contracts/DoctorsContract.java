package ru.parada.app.contracts;

import ru.parada.app.units.ListModel;

public interface DoctorsContract
{
    interface ListItemModel
    {
        int getId();
        String getPhotoPath();
        String getLastName();
        String getFirstName();
        String getMiddleName();
        String getFirstPosition();
        String getSecondPosition();
        String getThirdPosition();
    }

    interface View
    {
        void updateDoctors(ListModel<ListItemModel> data);
    }

    interface Presenter
    {
        void loadDoctors();
        void updateDoctors();
        void searchDoctors(String keys);
    }

    interface Behaviour
    {
        void openMenu();
    }
}