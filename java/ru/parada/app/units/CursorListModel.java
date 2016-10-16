package ru.parada.app.units;

import android.database.Cursor;

public abstract class CursorListModel<MODEL>
    implements ListModel<MODEL>
{
    private Cursor data;

    public CursorListModel(Cursor d)
    {
        this.data = d;
    }

    @Override
    public MODEL getItem(int i)
    {
        if(!data.moveToPosition(i))
        {
            return null;
        }
        return getModel(i);
    }

    protected int getColumnIndex(String ci)
    {
        return data.getColumnIndex(ci);
    }
    protected int getInt(String ci)
    {
        return data.getInt(data.getColumnIndex(ci));
    }
    protected String getString(String ci)
    {
        return data.getString(data.getColumnIndex(ci));
    }
    protected long getLong(String ci)
    {
        return data.getLong(data.getColumnIndex(ci));
    }

    protected abstract MODEL getModel(int i);

    @Override
    public int getItemsCount()
    {
        if(data == null)
        {
            return 0;
        }
        return data.getCount();
    }

    public void clear()
    {
        if(data != null)
        {
            data.close();
            data = null;
        }
    }
}