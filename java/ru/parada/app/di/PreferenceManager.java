package ru.parada.app.di;

public interface PreferenceManager
{
    void setNotificationBadge(boolean need);
    boolean needNotificationBadge();
}