package ru.parada.app.units;

import java.util.ArrayList;

public class ArrayListModel<MODEL>
        implements ListModel<MODEL>
{
    private ArrayList<MODEL> data;

    public ArrayListModel(ArrayList<MODEL> d)
    {
        data = d;
    }

    @Override
    public MODEL getItem(int i)
    {
        return data.get(i);
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
            data.clear();
            data = null;
        }
    }
}