package ru.parada.app.modules.subscribe.model;

import ru.parada.app.core.SubscribeCore;

public class UserData
    implements SubscribeCore.Model
{
    private String wish_date;
    private String wish_time;
    private String last_name;
    private String first_name;
    private String middle_name;
    private String email;
    private String phone;
    private String comment;

    public UserData(String wd, String wt, String ln, String fn, String mn, String e, String p, String c)
    {
        wish_date = wd;
        wish_time = wt;
        last_name = ln;
        first_name = fn;
        middle_name = mn;
        email = e;
        phone = p;
        comment = c;
    }

    @Override
    public String getDate()
    {
        return wish_date;
    }
    @Override
    public String getTime()
    {
        return wish_time;
    }
    @Override
    public String getLastname()
    {
        return last_name;
    }
    @Override
    public String getFirstname()
    {
        return first_name;
    }
    @Override
    public String getMiddlename()
    {
        return middle_name;
    }
    @Override
    public String getEmail()
    {
        return email;
    }
    @Override
    public String getPhone()
    {
        return phone;
    }
    @Override
    public String getComment()
    {
        return comment;
    }
}