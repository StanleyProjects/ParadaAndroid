package ru.parada.app.modules.notifications.model;

import ru.parada.app.core.NotificationsCore;

public class Notification
        implements NotificationsCore.Model
{
    private int id;
    private String message;
    private long date;
    private boolean read;

    public Notification(int i, String m, long d, boolean r)
    {
        id = i;
        message = m;
        date = d;
        read = r;
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

    @Override
    public boolean read()
    {
        return read;
    }
}