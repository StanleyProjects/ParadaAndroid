package ru.parada.app.modules.news.model;

import android.database.Cursor;
import android.provider.BaseColumns;

import ru.parada.app.core.NewsCore;
import ru.parada.app.db.Tables;
import ru.parada.app.units.CursorListModel;

public class NewsCursorListModel
        extends CursorListModel<NewsCore.Model>
{
    public NewsCursorListModel(Cursor d)
    {
        super(d);
    }

    @Override
    public NewsCore.Model getModel(int i)
    {
        return new NewsCore.Model()
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
            public String getFullDescription()
            {
                return getString(Tables.News.Columns.full_descr);
            }

            @Override
            public String getDescription()
            {
                return getString(Tables.News.Columns.descr);
            }

            @Override
            public String getImagePath()
            {
                int photoPathColumnIndex = getColumnIndex(Tables.Images.Columns.image_path);
                if(photoPathColumnIndex < 0)
                {
                    return null;
                }
                return getString(Tables.Images.Columns.image_path);
            }

            @Override
            public long getDate()
            {
                return getLong(Tables.News.Columns.date);
            }
        };
    }
}