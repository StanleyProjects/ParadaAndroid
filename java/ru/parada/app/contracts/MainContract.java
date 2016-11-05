package ru.parada.app.contracts;

import ru.parada.app.core.NewsCore;
import ru.parada.app.units.ListModel;

public interface MainContract
{

    interface View
    {
        void callDialogOpen();
        void callDialogClose();
        void update(ListModel<NewsCore.Model> data);
    }

    interface Presenter
    {
        void load();
        void update();
        void callDialogOpen();
        void callDialogClose();
    }

    interface MainBehaviour
        extends ToolbarBehaviour, HeaderBehaviour, FooterBehaviour
    {
        void oneOfNews(int id);
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