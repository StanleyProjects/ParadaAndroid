package ru.parada.app.db;

import android.content.ContentValues;
import android.provider.BaseColumns;

import java.util.HashMap;

import ru.parada.app.contracts.DoctorDetailContract;
import ru.parada.app.contracts.DoctorsContract;
import ru.parada.app.contracts.ImagesContract;
import ru.parada.app.contracts.MainContract;
import ru.parada.app.contracts.ServicesContract;
import ru.parada.app.contracts.ServicesWithPricesContract;

public class ContentDriver
{
    static public ContentValues getContentValues(ServicesContract.ListItemModel item)
    {
        ContentValues content = new ContentValues();
        content.put(BaseColumns._ID, item.getId());
        content.put(Tables.Services.Columns.title, item.getTitle());
        return content;
    }

    static public ContentValues getContentValues(DoctorDetailContract.Model item)
    {
        ContentValues content = new ContentValues();
        content.put(BaseColumns._ID, item.getId());
        content.put(Tables.Doctors.Columns.last_name, item.getLastName());
        content.put(Tables.Doctors.Columns.first_name, item.getFirstName());
        content.put(Tables.Doctors.Columns.middle_name, item.getMiddleName());
        content.put(Tables.Doctors.Columns.first_position, item.getFirstPosition());
        content.put(Tables.Doctors.Columns.second_position, item.getSecondPosition());
        content.put(Tables.Doctors.Columns.third_position, item.getThirdPosition());
        content.put(Tables.Doctors.Columns.descr, item.getDescription());
        content.put(Tables.Doctors.Columns.phone, item.getPhone());
        content.put(Tables.Doctors.Columns.order, item.getOrder());
        content.put(Tables.Doctors.Columns.first_last_middle, item.getFirstName().toLowerCase() + item.getLastName().toLowerCase() + item.getMiddleName().toLowerCase());
        return content;
    }

    static public ContentValues getContentValues(ImagesContract.Model item)
    {
        ContentValues content = new ContentValues();
        content.put(Tables.Images.Columns.type, item.getType());
        content.put(Tables.Images.Columns.entity_id, item.getEntityId());
        content.put(Tables.Images.Columns.image_path, item.getImagePath());
        content.put(Tables.Images.Columns.image_url, item.getImageUrl());
        return content;
    }

    public static ContentValues getContentValues(MainContract.ListItemModel item)
    {
        ContentValues content = new ContentValues();
        content.put(BaseColumns._ID, item.getId());
        content.put(Tables.News.Columns.title, item.getTitle());
        content.put(Tables.News.Columns.descr, item.getDescription());
        content.put(Tables.News.Columns.date, item.getDate());
        return content;
    }

    public static ContentValues getContentValues(ServicesWithPricesContract.Model item)
    {
        ContentValues content = new ContentValues();
        content.put(BaseColumns._ID, item.getId());
        content.put(Tables.ServicesWithPrices.Columns.title, item.getTitle());
        content.put(Tables.ServicesWithPrices.Columns.order, item.getOrder());
        content.put(Tables.ServicesWithPrices.Columns.group_id, item.getGroupId());
        content.put(Tables.ServicesWithPrices.Columns.group, item.getGroupName());
        content.put(Tables.ServicesWithPrices.Columns.group_order, item.getGroupOrder());
        content.put(Tables.ServicesWithPrices.Columns.title_search, item.getTitle().toLowerCase());
        return content;
    }
}