package ru.parada.app.core;

public interface SubscribeCore
{
    interface Mark
    {
        String DATE = Mark.class.getCanonicalName().toLowerCase().replace(".", "_") + "_" + "date";
        String TIME = Mark.class.getCanonicalName().toLowerCase().replace(".", "_") + "_" + "time";
        String LASTNAME = Mark.class.getCanonicalName().toLowerCase().replace(".", "_") + "_" + "lastname";
        String FIRSTNAME = Mark.class.getCanonicalName().toLowerCase().replace(".", "_") + "_" + "firstname";
        String MIDDLENAME = Mark.class.getCanonicalName().toLowerCase().replace(".", "_") + "_" + "middlename";
        String EMAIL = Mark.class.getCanonicalName().toLowerCase().replace(".", "_") + "_" + "email";
        String PHONE = Mark.class.getCanonicalName().toLowerCase().replace(".", "_") + "_" + "phone";
        String COMMENT = Mark.class.getCanonicalName().toLowerCase().replace(".", "_") + "_" + "comment";
    }

    interface Model
    {
        String getDate();
        String getTime();
        String getLastname();
        String getFirstname();
        String getMiddlename();
        String getEmail();
        String getPhone();
        String getComment();
    }
}