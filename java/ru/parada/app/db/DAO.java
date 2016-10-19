package ru.parada.app.db;

import ru.parada.app.contracts.PricesContract;
import ru.parada.app.contracts.ServicesWithPricesContract;
import ru.parada.app.core.DoctorsCore;
import ru.parada.app.units.ListModel;

public interface DAO
{
    interface Prices
    {
        ListModel<PricesContract.Model> getAllFromServiceId(int id);
        void insertOne(PricesContract.Model item);
        void clear();
    }
    interface ServicesWithPrices
    {
        ListModel<ServicesWithPricesContract.Model> getAll();
        ServicesWithPricesContract.Model getOneFromId(int id);
        ListModel<ServicesWithPricesContract.GroupModel> getAllGroups();
        ListModel<ServicesWithPricesContract.Model> getAllFromKeys(String keys);
        ListModel<ServicesWithPricesContract.GroupModel> getGroupsFromKeys(String keys);
        void insertOne(ServicesWithPricesContract.Model item);
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
}