package ru.parada.app.modules.servicesprices.models;

import ru.parada.app.contracts.ServicesWithPricesContract;

public class ServiceGroupPrice
    implements ServicesWithPricesContract.GroupModel
{
    private int id;
    private String group;
    private int group_order;

    public ServiceGroupPrice(int i, String gn, int go)
    {
        id = i;
        group = gn;
        group_order = go;
    }

    @Override
    public int getId()
    {
        return id;
    }

    @Override
    public String getGroupName()
    {
        return group;
    }

    @Override
    public int getGroupOrder()
    {
        return group_order;
    }
}