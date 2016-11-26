package ru.parada.app.contracts.services;

import ru.parada.app.core.ServicesCore;

public interface ServiceDetailContract
{
    interface Mark
    {
        String SERVICE_ID = Mark.class.getCanonicalName().toLowerCase().replace(".", "_") + "_" + "service_id";
    }

    interface View
    {
        void update(ServicesCore.Model data);
    }

    interface Presenter
    {
        void update(int id);
    }

    interface Behaviour
    {
        void back();
        void subscribe();
        void requestcall();
    }
}