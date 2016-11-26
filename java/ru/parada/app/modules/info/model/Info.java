package ru.parada.app.modules.info.model;

import ru.parada.app.core.InfoCore;

public class Info
    implements InfoCore.Model
{
    private int id;
    private String title;
    private String descr;
    private String imp;

    public Info(int i, String t, String d, String im)
    {
        id = i;
        title = t;
        descr = d;
        imp = im;
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
    public String getImagePath()
    {
        return imp;
    }
}