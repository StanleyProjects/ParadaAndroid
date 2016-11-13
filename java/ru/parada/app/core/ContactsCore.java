package ru.parada.app.core;

public interface ContactsCore
{
    interface Mark
    {
        String NAME = Mark.class.getCanonicalName().toLowerCase().replace(".", "_") + "_" + "name";
        String IMAGE = Mark.class.getCanonicalName().toLowerCase().replace(".", "_") + "_" + "image";
        String LATITUDE = Mark.class.getCanonicalName().toLowerCase().replace(".", "_") + "_" + "latitude";
        String LONGITUDE = Mark.class.getCanonicalName().toLowerCase().replace(".", "_") + "_" + "longitude";
    }

    interface Screens
    {
        int DETAIL = 1;
        int MAP = 1;
    }

    interface Model
    {
        String getName();
        int getImage();
        double getLatitude();
        double getLongitude();
    }
}