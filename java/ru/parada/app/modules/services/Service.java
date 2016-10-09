package ru.parada.app.modules.services;

import ru.parada.app.contracts.ServicesContract;

public class Service
    implements ServicesContract.ListItemModel
{
    private int id;
    private String title;
    private String image_path;

    public Service(int i, String t)
    {
        this.id = i;
        this.title = t;
    }
    public Service(int i, String t, String ip)
    {
        this(i, t);
        this.image_path = ip;
    }

    @Override
    public int getId()
    {
        return id;
    }

    @Override
    public String getPhotoPath()
    {
        return image_path;
    }

    @Override
    public String getTitle()
    {
        return title;
    }
}