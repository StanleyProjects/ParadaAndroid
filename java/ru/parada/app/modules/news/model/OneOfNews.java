package ru.parada.app.modules.news.model;

import ru.parada.app.core.NewsCore;

public class OneOfNews
        implements NewsCore.OneOfNewsModel
{
    private int id;
    private String title;
    private String descr;
    private long date;

    public OneOfNews(int i, String t, String de, long da)
    {
        this.id = i;
        this.title = t;
        this.descr = de;
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
    public String getDescription()
    {
        return descr;
    }

    @Override
    public long getDate()
    {
        return date;
    }
}