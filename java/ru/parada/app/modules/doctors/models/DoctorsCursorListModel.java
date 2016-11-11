package ru.parada.app.modules.doctors.models;

import android.database.Cursor;
import android.provider.BaseColumns;

import ru.parada.app.contracts.DoctorDetailContract;
import ru.parada.app.core.DoctorsCore;
import ru.parada.app.db.Tables;
import ru.parada.app.units.CursorListModel;

public class DoctorsCursorListModel
        extends CursorListModel<DoctorsCore.Model>
{
    public DoctorsCursorListModel(Cursor d)
    {
        super(d);
    }

    @Override
    public DoctorsCore.Model getModel(int i)
    {
        return new DoctorsCore.Model()
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
            public String getLastName()
            {
                return getString(Tables.Doctors.Columns.last_name);
            }

            @Override
            public String getFirstName()
            {
                return getString(Tables.Doctors.Columns.first_name);
            }

            @Override
            public String getMiddleName()
            {
                return getString(Tables.Doctors.Columns.middle_name);
            }

            @Override
            public String getFirstPosition()
            {
                return getString(Tables.Doctors.Columns.first_position);
            }

            @Override
            public String getSecondPosition()
            {
                return getString(Tables.Doctors.Columns.second_position);
            }

            @Override
            public String getThirdPosition()
            {
                return getString(Tables.Doctors.Columns.third_position);
            }

            @Override
            public String getDescription()
            {
                return getString(Tables.Doctors.Columns.descr);
            }

            @Override
            public String getPhone()
            {
                return getString(Tables.Doctors.Columns.phone);
            }

            @Override
            public int getOrder()
            {
                return getInt(Tables.Doctors.Columns.order);
            }
        };
    }
}