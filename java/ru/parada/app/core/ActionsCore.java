package ru.parada.app.core;

public interface ActionsCore
{
    interface Model
    {
        int getId();
        String getDescription();
        String getTitle();
        String getSubtitle();
        String getImagePath();
        long getFromDate();
        long getToDate();
    }
}