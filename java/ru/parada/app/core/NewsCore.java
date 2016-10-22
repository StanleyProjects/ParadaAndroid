package ru.parada.app.core;

public interface NewsCore
{
    interface OneOfNewsModel
    {
        int getId();
        String getTitle();
        String getDescription();
        long getDate();
    }
}