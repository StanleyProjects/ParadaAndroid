package ru.parada.app.core;

public interface ContactsCore
{
    interface Mark
    {
        String CONTACT = Mark.class.getCanonicalName().toLowerCase().replace(".", "_") + "_" + "contact";
        String LATITUDE = Mark.class.getCanonicalName().toLowerCase().replace(".", "_") + "_" + "latitude";
        String LONGITUDE = Mark.class.getCanonicalName().toLowerCase().replace(".", "_") + "_" + "longitude";
    }

    interface Screens
    {
        int DETAIL = 1;
        int MAP = 1;
    }

    interface Contacts
    {
        int BALTMED = 1;
        int SURGERY = 2;
    }
}