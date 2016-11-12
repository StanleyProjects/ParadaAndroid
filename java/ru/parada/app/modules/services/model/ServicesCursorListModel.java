package ru.parada.app.modules.services.model;

import android.database.Cursor;
import android.provider.BaseColumns;

import ru.parada.app.core.ServicesCore;
import ru.parada.app.db.Tables;
import ru.parada.app.units.CursorListModel;

public class ServicesCursorListModel
        extends CursorListModel<ServicesCore.Model>
{
    public ServicesCursorListModel(Cursor d)
    {
        super(d);
    }

    @Override
    public ServicesCore.Model getModel(int i)
    {
        return new ServicesCore.Model()
        {
            @Override
            public int getId()
            {
                return getInt(BaseColumns._ID);
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
            public String getTitle()
            {
                return getString(Tables.Services.Columns.title);
            }

            @Override
            public String getDescription()
            {
                return getString(Tables.Services.Columns.descr);
            }

            @Override
            public int getOrder()
            {
                return getInt(Tables.Services.Columns.order);
            }
        };
    }
}