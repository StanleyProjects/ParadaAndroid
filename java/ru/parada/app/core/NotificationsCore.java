package ru.parada.app.core;

public interface NotificationsCore
{
    interface Model
    {
        int getId();
        String getMessage();
        long getDate();
    }
}