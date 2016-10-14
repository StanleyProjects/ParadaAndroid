package ru.parada.app.contracts;

import ru.parada.app.units.ListModel;

public interface PricesContract
{
    interface ListItemModel
    {
        int getId();
    }

    interface View
    {
        void updatePrices(ListModel<ListItemModel> data);
    }

    interface Presenter
    {
        void loadPrices();
        void updatePrices();
        void searchServices(String keys);
    }
}
