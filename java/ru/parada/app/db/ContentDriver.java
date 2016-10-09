package ru.parada.app.db;

import android.content.ContentValues;
import android.provider.BaseColumns;

import java.util.HashMap;

import ru.parada.app.contracts.DoctorsContract;
import ru.parada.app.contracts.ImagesContract;
import ru.parada.app.contracts.ServicesContract;

public class ContentDriver
{
    static public ContentValues getServiceContentValues(ServicesContract.ListItemModel item)
    {
        ContentValues content = new ContentValues();
        content.put(BaseColumns._ID, item.getId());
        content.put(Tables.Services.Columns.title, item.getTitle());
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

    static public ContentValues getImageContentValues(ImagesContract.Model item)
    {
        ContentValues content = new ContentValues();
        content.put(Tables.Images.Columns.type, item.getType());
        content.put(Tables.Images.Columns.entity_id, item.getEntityId());
        content.put(Tables.Images.Columns.image_path, item.getImagePath());
        content.put(Tables.Images.Columns.image_url, item.getImageUrl());
        return content;
    }
}