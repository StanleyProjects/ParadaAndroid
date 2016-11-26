package ru.parada.app.contracts;

public interface ImagesContract
{
    interface Types
    {
        int DOCTORS_TYPE = 1;
        int SERVICES_TYPE = 2;
        int ACTIONS_TYPE = 3;
        int SERVICE_DETAIL_TYPE = 4;
        int ACTION_DETAIL_TYPE = 5;
        int ONEOFNEWS_TYPE = 6;
        int DOCTOR_VIDEOS_TYPE = 7;
        int INFO_TYPE = 8;
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