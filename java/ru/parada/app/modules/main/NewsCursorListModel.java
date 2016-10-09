package ru.parada.app.modules.main;

import android.database.Cursor;
import android.provider.BaseColumns;

import ru.parada.app.contracts.MainContract;
import ru.parada.app.db.Tables;
import ru.parada.app.units.ListModel;

public class NewsCursorListModel
    implements ListModel<MainContract.ListItemModel>
{
    private Cursor data;

    public NewsCursorListModel(Cursor d)
    {
        this.data = d;
    }

    @Override
    public MainContract.ListItemModel getItem(int i)
    {
        if(!data.moveToPosition(i))
        {
            return null;
        }
        return new MainContract.ListItemModel()
        {
            @Override
            public int getId()
            {
                return data.getInt(data.getColumnIndex(BaseColumns._ID));
            }

            @Override
            public String getTitle()
            {
                return data.getString(data.getColumnIndex(Tables.News.Columns.title));
            }

            @Override
            public String getDescription()
            {
                return data.getString(data.getColumnIndex(Tables.News.Columns.descr));
            }

            @Override
            public long getDate()
            {
                return data.getLong(data.getColumnIndex(Tables.News.Columns.date));
            }
        };
    }

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