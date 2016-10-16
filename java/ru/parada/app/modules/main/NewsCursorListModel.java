package ru.parada.app.modules.main;

import android.database.Cursor;
import android.provider.BaseColumns;

import ru.parada.app.contracts.MainContract;
import ru.parada.app.db.Tables;
import ru.parada.app.units.CursorListModel;

public class NewsCursorListModel
        extends CursorListModel<MainContract.ListItemModel>
{
    public NewsCursorListModel(Cursor d)
    {
        super(d);
    }

    @Override
    public MainContract.ListItemModel getModel(int i)
    {
        return new MainContract.ListItemModel()
        {
            @Override
            public int getId()
            {
                return getInt(BaseColumns._ID);
            }

            @Override
            public String getTitle()
            {
                return getString(Tables.News.Columns.title);
            }

            @Override
            public String getDescription()
            {
                return getString(Tables.News.Columns.descr);
            }

            @Override
            public long getDate()
            {
                return getLong(Tables.News.Columns.date);
            }
        };
    }
}