package ru.parada.app.modules.services;

import android.database.Cursor;
import android.provider.BaseColumns;

import ru.parada.app.contracts.ServicesContract;
import ru.parada.app.db.Tables;
import ru.parada.app.units.CursorListModel;

public class ServicesCursorListModel
        extends CursorListModel<ServicesContract.ListItemModel>
{
    public ServicesCursorListModel(Cursor d)
    {
        super(d);
    }

    @Override
    public ServicesContract.ListItemModel getModel(int i)
    {
        return new ServicesContract.ListItemModel()
        {
            @Override
            public int getId()
            {
                return getInt(BaseColumns._ID);
            }

            @Override
            public String getPhotoPath()
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
        };
    }
}