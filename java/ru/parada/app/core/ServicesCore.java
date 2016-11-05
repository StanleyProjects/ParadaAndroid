package ru.parada.app.core;

public interface ServicesCore
{
    interface Model
    {
        int getId();
        String getImagePath();
        String getTitle();
        String getDescription();
        int getOrder();
    }
}