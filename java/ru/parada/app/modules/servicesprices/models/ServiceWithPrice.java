package ru.parada.app.modules.servicesprices.models;

import ru.parada.app.core.ServicesWithPricesCore;

public class ServiceWithPrice
    implements ServicesWithPricesCore.Model
{
    private int id;
    private String title;
    private String subtitle;
    private int order;
    private int group_id;
    private String group;
    private int group_order;

    public ServiceWithPrice(int i, String t, String s, int o, int gi, String gn, int go)
    {
        id = i;
        title = t;
        subtitle = s;
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
    public String getSubTitle()
    {
        return subtitle;
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