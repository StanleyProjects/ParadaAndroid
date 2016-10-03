package ru.parada.app.db;

import android.content.ContentValues;
import android.provider.BaseColumns;

import java.util.HashMap;

import ru.parada.app.contracts.DoctorsContract;

public class ContentDriver
{
    static public ContentValues getServiceContentValues(HashMap item)
    {
        ContentValues content = new ContentValues();
        content.put(BaseColumns._ID, Integer.parseInt((String)item.get("id")));
        content.put(Tables.Services.Columns.title, (String)item.get("title"));
        content.put(Tables.Services.Columns.descr, (String)item.get("descr"));
        return content;
    }

    static public ContentValues getDoctorContentValues(DoctorsContract.ListItemModel item)
    {
        ContentValues content = new ContentValues();
        content.put(BaseColumns._ID, item.getId());
        content.put(Tables.Doctors.Columns.last_name, item.getLastName());
        content.put(Tables.Doctors.Columns.first_name, item.getFirstName());
        content.put(Tables.Doctors.Columns.middle_name, item.getMiddleName());
        content.put(Tables.Doctors.Columns.first_position, item.getFirstPosition());
        content.put(Tables.Doctors.Columns.second_position, item.getSecondPosition());
        content.put(Tables.Doctors.Columns.third_position, item.getThirdPosition());
        return content;
    }
}