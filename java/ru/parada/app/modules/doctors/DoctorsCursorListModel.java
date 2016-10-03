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
            public String getLastName()
            {
                return data.getString(data.getColumnIndex(Tables.Doctors.Columns.last_name));
            }

            @Override
            public String getFirstName()
            {
                return data.getString(data.getColumnIndex(Tables.Doctors.Columns.first_name));
            }

            @Override
            public String getMiddleName()
            {
                return data.getString(data.getColumnIndex(Tables.Doctors.Columns.middle_name));
            }

            @Override
            public String getFirstPosition()
            {
                return data.getString(data.getColumnIndex(Tables.Doctors.Columns.first_position));
            }

            @Override
            public String getSecondPosition()
            {
                return data.getString(data.getColumnIndex(Tables.Doctors.Columns.second_position));
            }

            @Override
            public String getThirdPosition()
            {
                return data.getString(data.getColumnIndex(Tables.Doctors.Columns.third_position));
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