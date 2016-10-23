package ru.parada.app.modules.notifications.model;

import ru.parada.app.contracts.NotificationsContract;

public class Notification
        implements NotificationsContract.Model
{
    private int id;
    private String message;
    private long date;

    public Notification(int i, String m, long d)
    {
        id = i;
        message = m;
        date = d;
    }

    @Override
    public int getId()
    {
        return id;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    @Override
    public long getDate()
    {
        return date;
    }
}