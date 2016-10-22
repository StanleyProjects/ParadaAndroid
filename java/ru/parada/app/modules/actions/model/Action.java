package ru.parada.app.modules.actions.model;

import ru.parada.app.core.ActionsCore;

public class Action
    implements ActionsCore.ActionModel
{
    private int id;
    private String descr;
    private String title;
    private String subtitle;
    private String image_path;
    private long from_date;
    private long to_date;

    public Action(int i, String d, String t, String s, String ip, long fd, long td)
    {
        id = i;
        descr = d;
        title = t;
        subtitle = s;
        image_path = ip;
        from_date = fd;
        to_date = td;
    }

    @Override
    public int getId()
    {
        return id;
    }

    @Override
    public String getDescription()
    {
        return descr;
    }

    @Override
    public String getTitle()
    {
        return title;
    }

    @Override
    public String getSubtitle()
    {
        return subtitle;
    }

    @Override
    public String getPhotoPath()
    {
        return image_path;
    }

    @Override
    public long getFromDate()
    {
        return from_date;
    }

    @Override
    public long getToDate()
    {
        return to_date;
    }
}