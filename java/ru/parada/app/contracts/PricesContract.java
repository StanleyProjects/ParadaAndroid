package ru.parada.app.contracts;

import ru.parada.app.units.ListModel;

public interface PricesContract
{
    interface Mark
    {
        String SERVICE_ID = Mark.class.getCanonicalName().toLowerCase().replace(".", "_") + "_" + "service_id";
    }

    interface Model
    {
        int getId();
        int getServiceId();
        String getTitle();
        String getValue();
    }

    interface View
    {
        void update(ListModel<Model> data, ServicesWithPricesContract.Model service);
    }

    interface Presenter
    {
        void load(int id);
        void update(int id);
    }

    interface Behaviour
    {
        void back();
    }
}
