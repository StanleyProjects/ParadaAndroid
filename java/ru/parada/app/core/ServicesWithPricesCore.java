package ru.parada.app.core;

public interface ServicesWithPricesCore
{
    interface Model
    {
        int getId();
        String getTitle();
        String getSubTitle();
        int getOrder();
        int getGroupId();
        String getGroupName();
        int getGroupOrder();
    }
}