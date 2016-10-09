package ru.parada.app.contracts;

public interface ImagesContract
{
    interface Types
    {
        int DOCTORS_TYPE = 1;
        int SERVICES_TYPE = 2;
    }

    interface Model
    {
        int getId();
        int getType();
        int getEntityId();
        String getImagePath();
        String getImageUrl();
    }
}