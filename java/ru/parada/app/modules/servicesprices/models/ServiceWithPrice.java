package ru.parada.app.modules.servicesprices.models;

import ru.parada.app.contracts.ServicesWithPricesContract;

public class ServiceWithPrice
    implements ServicesWithPricesContract.Model
{
    private int id;
    private String title;
    private int order;
    private int group_id;
    private String group;
    private int group_order;

    public ServiceWithPrice(int i, String t, int o, int gi, String gn, int go)
    {
        id = i;
        title = t;
        order = o;
        group_id = gi;
        group = gn;
        group_order = go;
    }

    @Override
    public int getId()
    {
        return id;
    }

    @Override
    public String getTitle()
    {
        return title;
    }

    @Override
    public int getOrder()
    {
        return order;
    }

    @Override
    public int getGroupId()
    {
        return group_id;
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