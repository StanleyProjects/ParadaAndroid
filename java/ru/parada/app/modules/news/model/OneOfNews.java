package ru.parada.app.modules.news.model;

import ru.parada.app.core.NewsCore;

public class OneOfNews
        implements NewsCore.Model
{
    private int id;
    private String title;
    private String image_path;
    private String descr;
    private String full_descr;
    private long date;

    public OneOfNews(int i, String t, String de, String fde, String imp, long da)
    {
        this.id = i;
        this.title = t;
        this.descr = de;
        this.full_descr = fde;
        this.image_path = imp;
        this.date = da;
    }

    @Override
    public int getId()
    {
        return id;
    }

    @Override
    public String getTitle()
    {
        return title;
    }

    @Override
    public String getFullDescription()
    {
        return full_descr;
    }

    @Override
    public String getDescription()
    {
        return descr;
    }

    @Override
    public String getImagePath()
    {
        return image_path;
    }

    @Override
    public long getDate()
    {
        return date;
    }
}