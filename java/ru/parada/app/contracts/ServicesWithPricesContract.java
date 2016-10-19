package ru.parada.app.contracts;

import ru.parada.app.core.ServicesWithPricesCore;
import ru.parada.app.units.ListModel;

public interface ServicesWithPricesContract
{
    interface GroupModel
    {
        int getId();
        String getGroupName();
        int getGroupOrder();
    }

    interface View
    {
        void update(ListModel<ServicesWithPricesCore.Model> allData, ListModel<GroupModel> groups);
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