package ru.parada.app.modules.menu.models;

import ru.parada.app.contracts.MenuContract;

public abstract class MenuModel
{
    public int icon;
    public String name;
    public boolean isNotification;

    public MenuModel(int i, String n)
    {
        icon = i;
        name = n;
        isNotification = false;
    }
    public MenuModel(int i, String n, boolean in)
    {
        this(i, n);
        isNotification = in;
    }

    abstract public void click(MenuContract.Behaviour behaviour);
}