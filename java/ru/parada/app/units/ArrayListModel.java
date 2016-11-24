package ru.parada.app.units;

import java.util.List;

public class ArrayListModel<MODEL>
        implements ListModel<MODEL>
{
    private List<MODEL> data;

    public ArrayListModel(List<MODEL> d)
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