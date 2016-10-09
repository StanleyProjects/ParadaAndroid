package ru.parada.app.contracts;

import ru.parada.app.units.ListModel;

public interface MainContract
{
    interface ListItemModel
    {
        int getId();
        String getTitle();
        String getDescription();
        long getDate();
    }

    interface View
    {
        void phoneOpen();
        void phoneClose();
        void updateNews(ListModel<ListItemModel> data);
    }

    interface Presenter
    {
        void phoneSwitch();
        void loadNews();
        void updateNews();
    }

    interface Behaviour
    {
        void openMenu();
        void openService();
    }
}