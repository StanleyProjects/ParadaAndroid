package ru.parada.app.modules.menu;

import java.util.ArrayList;

import ru.parada.app.units.ListModel;

public class MenuListModel
    implements ListModel<MenuModel>
{
    private int h;

    private ArrayList<MenuModel> data;

    public MenuListModel(ArrayList<MenuModel> d)
    {
        this.data = d;
    }

    @Override
    public MenuModel getItem(int i)
    {
        return data.get(i);
    }

    public void setHighlight(int i)
    {
        h = i;
    }
    public boolean isHighlight(MenuModel model)
    {
        return data.get(h).equals(model);
    }

    @Override
    public int getItemsCount()
    {
        if(data == null)
        {
            return 0;
        }
        return data.size();
    }

    @Override
    public void clear()
    {
        if(data != null)
        {
            data = null;
        }
    }
}