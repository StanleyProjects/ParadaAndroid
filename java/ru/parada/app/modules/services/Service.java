package ru.parada.app.modules.services;

import ru.parada.app.core.ServicesCore;

public class Service
    implements ServicesCore.Model
{
    private int id;
    private int order;
    private String title;
    private String descr;
    private String image_path;

    public Service(int i, int o, String t, String d, String imp)
    {
        this.id = i;
        this.order = o;
        this.title = t;
        this.descr = d;
        this.image_path = imp;
    }
    @Override
    public int getId()
    {
        return id;
    }

    @Override
    public String getImagePath()
    {
        return image_path;
    }

    @Override
    public String getTitle()
    {
        return title;
    }

    @Override
    public String getDescription()
    {
        return descr;
    }

    @Override
    public int getOrder()
    {
        return order;
    }
}