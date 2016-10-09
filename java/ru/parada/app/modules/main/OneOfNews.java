package ru.parada.app.modules.main;

import ru.parada.app.contracts.MainContract;

public class OneOfNews
        implements MainContract.ListItemModel
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