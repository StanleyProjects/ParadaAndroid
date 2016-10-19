package ru.parada.app.core;

public interface DoctorsCore
{
    interface DetailModel
    {
        int getId();
        String getPhotoPath();
        String getLastName();
        String getFirstName();
        String getMiddleName();
        String getFirstPosition();
        String getSecondPosition();
        String getThirdPosition();
        String getDescription();
        String getPhone();
        int getOrder();
    }
}