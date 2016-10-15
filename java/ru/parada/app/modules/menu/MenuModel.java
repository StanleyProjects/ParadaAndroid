package ru.parada.app.modules.menu;

import ru.parada.app.contracts.MenuContract;

public abstract class MenuModel
{
    public int icon;
    public String name;

    public MenuModel(int i, String n)
    {
        icon = i;
        name = n;
    }

    abstract public void click(MenuContract.Behaviour behaviour);
}