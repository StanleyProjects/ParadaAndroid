package ru.parada.app.modules.requestcall.model;

import ru.parada.app.core.RequestCallCore;

public class UserData
    implements RequestCallCore.Model
{
    private String name;
    private String phone;

    public UserData(String n, String p)
    {
        name = n;
        phone = p;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public String getPhone()
    {
        return phone;
    }
}