package ru.parada.app.db;

import android.content.ContentValues;
import android.provider.BaseColumns;
import android.util.Log;

import ru.parada.app.contracts.ImagesContract;
import ru.parada.app.contracts.PricesContract;
import ru.parada.app.core.ActionsCore;
import ru.parada.app.core.DoctorVideosCore;
import ru.parada.app.core.DoctorsCore;
import ru.parada.app.core.InfoCore;
import ru.parada.app.core.NewsCore;
import ru.parada.app.core.NotificationsCore;
import ru.parada.app.core.ServicesCore;
import ru.parada.app.core.ServicesWithPricesCore;

public class ContentDriver
{
    static public ContentValues getContentValues(DoctorsCore.Model item)
    {
        //Log.e("DoctorsCore.Model", item.getId() + " " + item.getLastName());
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

    public static ContentValues getContentValues(NewsCore.Model item)
    {
        ContentValues content = new ContentValues();
        content.put(BaseColumns._ID, item.getId());
        content.put(Tables.News.Columns.title, item.getTitle());
        content.put(Tables.News.Columns.descr, item.getDescription());
        content.put(Tables.News.Columns.full_descr, item.getFullDescription());
        content.put(Tables.News.Columns.date, item.getDate());
        return content;
    }

    public static ContentValues getContentValues(ServicesWithPricesCore.Model item)
    {
        ContentValues content = new ContentValues();
        content.put(BaseColumns._ID, item.getId());
        content.put(Tables.ServicesWithPrices.Columns.title, item.getTitle());
        content.put(Tables.ServicesWithPrices.Columns.order, item.getOrder());
        content.put(Tables.ServicesWithPrices.Columns.group_id, item.getGroupId());
        content.put(Tables.ServicesWithPrices.Columns.group, item.getGroupName());
        content.put(Tables.ServicesWithPrices.Columns.group_order, item.getGroupOrder());
        content.put(Tables.ServicesWithPrices.Columns.subtitle, item.getSubTitle());
        content.put(Tables.ServicesWithPrices.Columns.title_search, item.getTitle().toLowerCase() + item.getSubTitle().toLowerCase());
        return content;
    }

    public static ContentValues getContentValues(PricesContract.Model item)
    {
        ContentValues content = new ContentValues();
        content.put(BaseColumns._ID, item.getId());
        content.put(Tables.Prices.Columns.title, item.getTitle());
        content.put(Tables.Prices.Columns.service_id, item.getServiceId());
        content.put(Tables.Prices.Columns.value, item.getValue());
        return content;
    }

    public static ContentValues getContentValues(ActionsCore.Model item)
    {
        ContentValues content = new ContentValues();
        content.put(BaseColumns._ID, item.getId());
        content.put(Tables.Actions.Columns.title, item.getTitle());
        content.put(Tables.Actions.Columns.descr, item.getDescription());
        content.put(Tables.Actions.Columns.subtitle, item.getSubtitle());
        content.put(Tables.Actions.Columns.from_date, item.getFromDate());
        content.put(Tables.Actions.Columns.to_date, item.getToDate());
        return content;
    }

    public static ContentValues getContentValues(NotificationsCore.Model item, boolean read)
    {
        ContentValues content = new ContentValues();
        content.put(BaseColumns._ID, item.getId());
        content.put(Tables.Notifications.Columns.message, item.getMessage());
        content.put(Tables.Notifications.Columns.date, item.getDate());
        content.put(Tables.Notifications.Columns.read, read ? 1 : 0);
        return content;
    }

    public static ContentValues getContentValues(ServicesCore.Model item)
    {
        ContentValues content = new ContentValues();
        content.put(BaseColumns._ID, item.getId());
        content.put(Tables.Services.Columns.title, item.getTitle());
        content.put(Tables.Services.Columns.order, item.getOrder());
        content.put(Tables.Services.Columns.descr, item.getDescription());
        return content;
    }

    public static ContentValues getContentValues(DoctorVideosCore.Model item)
    {
        ContentValues content = new ContentValues();
//        Log.e("DoctorVideosCore.Model", item.getId() + " " + item.getTitle() + " " + item.getDoctorId());
        content.put(Tables.Videos.Columns.id, item.getId());
        content.put(Tables.Videos.Columns.doctor_id, item.getDoctorId());
        content.put(Tables.Videos.Columns.descr, item.getDescription());
        content.put(Tables.Videos.Columns.title, item.getTitle());
        content.put(Tables.Videos.Columns.link, item.getLink());
        return content;
    }

    public static ContentValues getContentValues(InfoCore.Model item)
    {
        ContentValues content = new ContentValues();
        content.put(Tables.Info.Columns.id, item.getId());
        content.put(Tables.Info.Columns.descr, item.getDescription());
        content.put(Tables.Info.Columns.title, item.getTitle());
        return content;
    }
}