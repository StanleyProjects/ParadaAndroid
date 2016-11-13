package ru.parada.app.core;

public interface ServicesCore
{
    interface Screens
    {
        int DETAIL = 1;
        int SUBSCRIBE = 2;
        int SUBSCRIBE_CHECK = 3;
        int CALL = 2;
        int CALL_CHECK = 3;
    }

    interface Model
    {
        int getId();
        String getImagePath();
        String getTitle();
        String getDescription();
        int getOrder();
    }
}