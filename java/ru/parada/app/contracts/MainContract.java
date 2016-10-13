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
        void callDialogOpen();
        void callDialogClose();
        void updateNews(ListModel<ListItemModel> data);
    }

    interface Presenter
    {
        void loadNews();
        void updateNews();
        void callDialogOpen();
        void callDialogClose();
    }

    interface MainBehaviour
        extends ToolbarBehaviour, HeaderBehaviour, FooterBehaviour
    {
    }

    interface ToolbarBehaviour
    {
        void openMenu();
    }
    interface HeaderBehaviour
    {
        void openServices();
        void openSubscribe();
        void openPrices();
    }
    interface FooterBehaviour
    {
        void openAllNews();
    }
}