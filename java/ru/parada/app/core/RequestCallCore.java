package ru.parada.app.core;

public interface RequestCallCore
{
    interface Mark
    {
        String NAME = Mark.class.getCanonicalName().toLowerCase().replace(".", "_") + "_" + "name";
        String PHONE = Mark.class.getCanonicalName().toLowerCase().replace(".", "_") + "_" + "phone";
    }

    interface Model
    {
        String getName();
        String getPhone();
    }
}