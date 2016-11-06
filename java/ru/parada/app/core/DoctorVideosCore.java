package ru.parada.app.core;

public interface DoctorVideosCore
{
    interface Model
    {
        int getId();
        int getDoctorId();
        String getDescription();
        String getTitle();
        String getImagePath();
        String getLink();
    }
}