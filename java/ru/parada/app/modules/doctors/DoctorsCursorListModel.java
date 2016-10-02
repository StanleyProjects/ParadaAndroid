package ru.parada.app.modules.doctors;

import android.database.Cursor;
import android.provider.BaseColumns;

import ru.parada.app.contracts.DoctorsContract;
import ru.parada.app.db.Tables;
import ru.parada.app.units.ListModel;

public class DoctorsCursorListModel
    implements ListModel<DoctorsContract.ListItemModel>
{
    private Cursor data;

    public DoctorsCursorListModel()
    {

    }
    public DoctorsCursorListModel(Cursor d)
    {
        this.data = d;
    }

    @Override
    public DoctorsContract.ListItemModel getItem(int i)
    {
        if(!data.moveToPosition(i))
        {
            return null;
        }
        return new DoctorsContract.ListItemModel()
        {
            @Override
            public int getId()
            {
                return data.getInt(data.getColumnIndex(BaseColumns._ID));
            }
            @Override
            public String getLastName()
            {
                return data.getString(data.getColumnIndex(Tables.Doctors.Columns.last_name));
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