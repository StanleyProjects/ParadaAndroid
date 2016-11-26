package ru.parada.app.core;

public interface InfoCore
{
    interface Mark
    {
        String INFO_ID = Mark.class.getCanonicalName().toLowerCase().replace(".", "_") + "_" + "info_id";
    }

    interface Screens
    {
        int DETAIL = 1;
    }

    interface Model
    {
        int getId();
        String getDescription();
        String getTitle();
        String getImagePath();
    }
}