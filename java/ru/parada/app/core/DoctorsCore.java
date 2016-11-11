package ru.parada.app.core;

public interface DoctorsCore
{
    interface Mark
    {
        String DOCTOR_ID = Mark.class.getCanonicalName().toLowerCase().replace(".", "_") + "_" + "doctor_id";
    }

    interface Model
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