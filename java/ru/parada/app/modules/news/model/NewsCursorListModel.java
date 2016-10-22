package ru.parada.app.modules.news.model;

import android.database.Cursor;
import android.provider.BaseColumns;

import ru.parada.app.core.NewsCore;
import ru.parada.app.db.Tables;
import ru.parada.app.units.CursorListModel;

public class NewsCursorListModel
        extends CursorListModel<NewsCore.OneOfNewsModel>
{
    public NewsCursorListModel(Cursor d)
    {
        super(d);
    }

    @Override
    public NewsCore.OneOfNewsModel getModel(int i)
    {
        return new NewsCore.OneOfNewsModel()
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