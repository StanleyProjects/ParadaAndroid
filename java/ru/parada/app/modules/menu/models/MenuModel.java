package ru.parada.app.modules.menu.models;

import ru.parada.app.contracts.MenuContract;

public abstract class MenuModel
{
    public int id;
    public int icon;
    public String name;
    public boolean isNotification;

    public MenuModel(int i, int ic, String n, boolean in)
    {
        id = i;
        icon = ic;
        name = n;
        isNotification = in;
    }

    abstract public void click(MenuContract.Behaviour behaviour);
}