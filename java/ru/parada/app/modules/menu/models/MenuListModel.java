package ru.parada.app.modules.menu.models;

import java.util.ArrayList;

import ru.parada.app.units.ArrayListModel;

public class MenuListModel
    extends ArrayListModel<MenuModel>
{
    private int h;

    public MenuListModel(ArrayList<MenuModel> d)
    {
        super(d);
    }

    public void setHighlight(int i)
    {
        h = i;
    }
    public boolean isHighlight(MenuModel model)
    {
        return getItem(h).equals(model);
    }
}