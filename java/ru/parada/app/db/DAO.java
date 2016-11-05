package ru.parada.app.db;

import ru.parada.app.contracts.NotificationsContract;
import ru.parada.app.contracts.PricesContract;
import ru.parada.app.contracts.ServicesWithPricesContract;
import ru.parada.app.core.ActionsCore;
import ru.parada.app.core.DoctorsCore;
import ru.parada.app.core.NewsCore;
import ru.parada.app.core.ServicesCore;
import ru.parada.app.core.ServicesWithPricesCore;
import ru.parada.app.units.ListModel;

public interface DAO
{
    interface News
    {
        ListModel<NewsCore.Model> getAll();
        ListModel<NewsCore.Model> getAllWithLimit(int limit);
        NewsCore.Model getOneFromId(int id);
        void insertOne(NewsCore.Model item);
        void clear();
    }
    interface Services
    {
        ListModel<ServicesCore.Model> getAll();
        ServicesCore.Model getOneFromId(int id);
        void insertOne(ServicesCore.Model item);
        void clear();
    }
    interface Actions
    {
        ListModel<ActionsCore.Model> getAll();
        ActionsCore.Model getOneFromId(int id);
        void insertOne(ActionsCore.Model item);
        void clear();
    }
    interface Prices
    {
        ListModel<PricesContract.Model> getAllFromServiceId(int id);
        void insertOne(PricesContract.Model item);
        void clear();
    }
    interface ServicesWithPrices
    {
        ListModel<ServicesWithPricesCore.Model> getAll();
        ServicesWithPricesCore.Model getOneFromId(int id);
        ListModel<ServicesWithPricesContract.GroupModel> getAllGroups();
        ListModel<ServicesWithPricesCore.Model> getAllFromKeys(String keys);
        ListModel<ServicesWithPricesContract.GroupModel> getGroupsFromKeys(String keys);
        void insertOne(ServicesWithPricesCore.Model item);
        void clear();
    }
    interface Doctors
    {
        ListModel<DoctorsCore.DetailModel> getAll();
        ListModel<DoctorsCore.DetailModel> getFromKeys(String keys);
        DoctorsCore.DetailModel getOneFromId(int id);
        void insertOne(DoctorsCore.DetailModel item);
        void clear();
    }
    interface Notifications
    {
        ListModel<NotificationsContract.Model> getAll();
        void insertOne(NotificationsContract.Model item);
        void clear();
    }
}