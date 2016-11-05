package ru.parada.app.core;

public interface NewsCore
{
    interface Model
    {
        int getId();
        String getTitle();
        String getFullDescription();
        String getDescription();
        String getImagePath();
        long getDate();
    }
}