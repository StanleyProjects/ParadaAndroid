package ru.parada.app.core;

public interface NotificationsCore
{
    interface GroupModel
    {
        long getDate();
    }

    interface Model
    {
        int getId();
        String getMessage();
        long getDate();
    }
}