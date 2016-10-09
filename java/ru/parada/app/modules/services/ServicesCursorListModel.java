package ru.parada.app.modules.services;

import android.database.Cursor;
import android.provider.BaseColumns;

import ru.parada.app.contracts.ServicesContract;
import ru.parada.app.db.Tables;
import ru.parada.app.units.ListModel;

public class ServicesCursorListModel
    implements ListModel<ServicesContract.ListItemModel>
{
    private Cursor data;

    public ServicesCursorListModel(Cursor d)
    {
        this.data = d;
    }

    @Override
    public ServicesContract.ListItemModel getItem(int i)
    {
        if(!data.moveToPosition(i))
        {
            return null;
        }
        return new ServicesContract.ListItemModel()
        {
            @Override
            public int getId()
            {
                return data.getInt(data.getColumnIndex(BaseColumns._ID));
            }

            @Override
            public String getPhotoPath()
            {
                int photoPathColumnIndex = data.getColumnIndex(Tables.Images.Columns.image_path);
                if(photoPathColumnIndex < 0)
                {
                    return null;
                }
                return data.getString(photoPathColumnIndex);
            }

            @Override
            public String getTitle()
            {
                return data.getString(data.getColumnIndex(Tables.Services.Columns.title));
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