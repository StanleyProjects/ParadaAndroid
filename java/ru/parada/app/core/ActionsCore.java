package ru.parada.app.core;

public interface ActionsCore
{
    interface ActionModel
    {
        int getId();
        String getDescription();
        String getTitle();
        String getSubtitle();
        String getPhotoPath();
        long getFromDate();
        long getToDate();
    }
}