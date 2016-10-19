package ru.parada.app.modules.prices.models;

import ru.parada.app.contracts.PricesContract;

public class Price
    implements PricesContract.Model
{
    private int id;
    private String title;
    private String value;
    private int serviceId;

    public Price(int i, String t, String v, int s)
    {
        this.id = i;
        this.title = t;
        this.value = v;
        this.serviceId = s;
    }

    @Override
    public int getId()
    {
        return id;
    }

    @Override
    public int getServiceId()
    {
        return serviceId;
    }

    @Override
    public String getTitle()
    {
        return title;
    }

    @Override
    public String getValue()
    {
        return value;
    }
}