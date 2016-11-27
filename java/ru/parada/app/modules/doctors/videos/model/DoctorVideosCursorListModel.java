package ru.parada.app.modules.doctors.videos.model;

import android.database.Cursor;
import android.util.Log;

import ru.parada.app.core.DoctorVideosCore;
import ru.parada.app.db.Tables;
import ru.parada.app.units.CursorListModel;

public class DoctorVideosCursorListModel
        extends CursorListModel<DoctorVideosCore.Model>
{
    public DoctorVideosCursorListModel(Cursor d)
    {
        super(d);
        Log.e(getClass().getName(), "size " + d.getCount());
    }

    @Override
    protected DoctorVideosCore.Model getModel(int i)
    {
        return new DoctorVideosCore.Model()
        {
            @Override
            public int getId()
            {
                return getInt(Tables.Videos.Columns.id);
            }
            @Override
            public int getDoctorId()
            {
                return getInt(Tables.Videos.Columns.doctor_id);
            }
            @Override
            public String getDescription()
            {
                return getString(Tables.Videos.Columns.descr);
            }
            @Override
            public String getTitle()
            {
                return getString(Tables.Videos.Columns.title);
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
            public String getLink()
            {
                return getString(Tables.Videos.Columns.link);
            }
        };
    }
}