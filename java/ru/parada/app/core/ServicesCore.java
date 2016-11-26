package ru.parada.app.core;

public interface ServicesCore
{
    interface Screens
    {
        int DETAIL = 1;
        int GET_USER_DATA = 2;
        int SEND_USER_DATA = 3;
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