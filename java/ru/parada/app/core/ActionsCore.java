package ru.parada.app.core;

public interface ActionsCore
{
    interface Mark
    {
        String ACTION_ID = Mark.class.getCanonicalName().toLowerCase().replace(".", "_") + "_" + "action_id";
    }

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