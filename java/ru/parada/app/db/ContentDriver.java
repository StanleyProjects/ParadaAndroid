package ru.parada.app.db;

import android.content.ContentValues;
import android.provider.BaseColumns;

import ru.parada.app.contracts.ImagesContract;
import ru.parada.app.contracts.MainContract;
import ru.parada.app.contracts.NotificationsContract;
import ru.parada.app.contracts.PricesContract;
import ru.parada.app.contracts.ServicesContract;
import ru.parada.app.core.ActionsCore;
import ru.parada.app.core.DoctorsCore;
import ru.parada.app.core.NewsCore;
import ru.parada.app.core.ServicesCore;
import ru.parada.app.core.ServicesWithPricesCore;

public class ContentDriver
{
    static public ContentValues getContentValues(DoctorsCore.DetailModel item)
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

    public static ContentValues getContentValues(NewsCore.OneOfNewsModel item)
    {
        ContentValues content = new ContentValues();
        content.put(BaseColumns._ID, item.getId());
        content.put(Tables.News.Columns.title, item.getTitle());
        content.put(Tables.News.Columns.descr, item.getDescription());
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

    public static ContentValues getContentValues(NotificationsContract.Model item)
    {
        ContentValues content = new ContentValues();
        content.put(BaseColumns._ID, item.getId());
        content.put(Tables.Notifications.Columns.message, item.getMessage());
        content.put(Tables.Notifications.Columns.date, item.getDate());
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
}