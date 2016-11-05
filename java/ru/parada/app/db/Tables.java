package ru.parada.app.db;

import android.provider.BaseColumns;

import ru.parada.app.contracts.ImagesContract;
import ru.parada.app.contracts.ServicesContract;
import ru.parada.app.units.ListModel;

public interface Tables
{
    interface News
            extends DAO.News
    {
        String TABLE_NAME = News.class.getCanonicalName()
                                      .toLowerCase()
                                      .replace('.', '_') + "_table";
        String CREATE_TABLE = "create table if not exists " + TABLE_NAME + " (" +
                BaseColumns._ID + " integer primary key autoincrement, " +
                Columns.title + " text" + "," +
                Columns.descr + " text" + "," +
                Columns.date + " integer" + "," +
                Columns.full_descr + " text" + //"," +
                ");";

        interface Columns
        {
            String title = TABLE_NAME + "_" + "title";
            String descr = TABLE_NAME + "_" + "descr";
            String date = TABLE_NAME + "_" + "date";
            String full_descr = TABLE_NAME + "_" + "full_descr";
        }
    }

    interface Actions
            extends DAO.Actions
    {
        String TABLE_NAME = Actions.class.getCanonicalName()
                                         .toLowerCase()
                                         .replace('.', '_') + "_table";
        String CREATE_TABLE = "create table if not exists " + TABLE_NAME + " (" +
                BaseColumns._ID + " integer primary key autoincrement, " +
                Columns.title + " text" + "," +
                Columns.descr + " text" + "," +
                Columns.from_date + " integer" + "," +
                Columns.to_date + " integer" + "," +
                Columns.subtitle + " text" + //"," +
                ");";

        interface Columns
        {
            String title = TABLE_NAME + "_" + "title";
            String descr = TABLE_NAME + "_" + "descr";
            String from_date = TABLE_NAME + "_" + "from_date";
            String to_date = TABLE_NAME + "_" + "to_date";
            String subtitle = TABLE_NAME + "_" + "subtitle";
        }
    }

    interface Services
            extends DAO.Services
    {
        String TABLE_NAME = Services.class.getCanonicalName()
                                          .toLowerCase()
                                          .replace('.', '_') + "_table";
        String CREATE_TABLE = "create table if not exists " + TABLE_NAME + " (" +
                BaseColumns._ID + " integer primary key autoincrement, " +
                Columns.title + " text" + "," +
                Columns.descr + " text" + "," +
                Columns.order + " integer" + //"," +
                ");";

        interface Columns
        {
            String title = TABLE_NAME + "_" + "title";
            String descr = TABLE_NAME + "_" + "descr";
            String order = TABLE_NAME + "_" + "order";
        }
    }

    interface Doctors
            extends DAO.Doctors
    {
        String TABLE_NAME = Doctors.class.getCanonicalName()
                                         .toLowerCase()
                                         .replace('.', '_') + "_table";
        String CREATE_TABLE = "create table if not exists " + TABLE_NAME + " (" +
                BaseColumns._ID + " integer primary key autoincrement, " +
                Columns.first_name + " text" + "," +
                Columns.last_name + " text" + "," +
                Columns.middle_name + " text" + "," +
                Columns.first_last_middle + " text" + "," +
                Columns.descr + " text" + "," +
                Columns.first_position + " text" + "," +
                Columns.second_position + " text" + "," +
                Columns.third_position + " text" + "," +
                Columns.order + " integer" + "," +
                Columns.phone + " integer" + //"," +
                ");";

        interface Columns
        {
            String descr = TABLE_NAME + "_" + "descr";
            String order = TABLE_NAME + "_" + "order";
            String first_name = TABLE_NAME + "_" + "first_name";
            String last_name = TABLE_NAME + "_" + "last_name";
            String middle_name = TABLE_NAME + "_" + "middle_name";
            String first_last_middle = TABLE_NAME + "_" + "first_last_middle";
            String first_position = TABLE_NAME + "_" + "first_position";
            String second_position = TABLE_NAME + "_" + "second_position";
            String third_position = TABLE_NAME + "_" + "third_position";
            String phone = TABLE_NAME + "_" + "phone";
        }
    }

    interface Images
    {
        String TABLE_NAME = Images.class.getCanonicalName()
                                        .toLowerCase()
                                        .replace('.', '_') + "_table";
        String CREATE_TABLE = "create table if not exists " + TABLE_NAME + " (" +
                Columns.id + " integer primary key autoincrement, " +
                Columns.image_path + " text" + "," +
                Columns.image_url + " text" + "," +
                Columns.entity_id + " integer" + "," +
                Columns.type + " integer" + //"," +
                ");";

        interface Columns
        {
            String id = TABLE_NAME + "_" + "id";
            String type = TABLE_NAME + "_" + "type";
            String entity_id = TABLE_NAME + "_" + "entity_id";
            String image_path = TABLE_NAME + "_" + "image_path";
            String image_url = TABLE_NAME + "_" + "image_url";
        }

        ImagesContract.Model getOneFromTypeAndEntityId(int type, int id);

        String getUrl(int type, int id);

        long insertOne(ImagesContract.Model item);
//        long removeOneFromTypeAndEntityId(int type, int id);
    }

    interface ServicesWithPrices
            extends DAO.ServicesWithPrices
    {
        String TABLE_NAME = ServicesWithPrices.class.getCanonicalName()
                                                    .toLowerCase()
                                                    .replace('.', '_') + "_table";
        String CREATE_TABLE = "create table if not exists " + TABLE_NAME + " (" +
                BaseColumns._ID + " integer primary key autoincrement, " +
                Columns.title + " text" + "," +
                Columns.order + " integer" + "," +
                Columns.group_id + " integer" + "," +
                Columns.group + " text" + "," +
                Columns.subtitle + " text" + "," +
                Columns.title_search + " text" + "," +
                Columns.group_order + " integer" + //"," +
                ");";

        interface Columns
        {
            String title = TABLE_NAME + "_" + "title";
            String subtitle = TABLE_NAME + "_" + "subtitle";
            String order = TABLE_NAME + "_" + "order";
            String group_id = TABLE_NAME + "_" + "group_id";
            String group = TABLE_NAME + "_" + "group";
            String group_order = TABLE_NAME + "_" + "group_order";
            String title_search = TABLE_NAME + "_" + "title_search";
        }
    }

    interface Prices
            extends DAO.Prices
    {
        String TABLE_NAME = Prices.class.getCanonicalName()
                                        .toLowerCase()
                                        .replace('.', '_') + "_table";
        String CREATE_TABLE = "create table if not exists " + TABLE_NAME + " (" +
                BaseColumns._ID + " integer primary key autoincrement, " +
                Columns.title + " text" + "," +
                Columns.service_id + " integer" + "," +
                Columns.value + " text" + //"," +
                ");";

        interface Columns
        {
            String title = TABLE_NAME + "_" + "title";
            String service_id = TABLE_NAME + "_" + "service_id";
            String value = TABLE_NAME + "_" + "value";
        }
    }

    interface Notifications
            extends DAO.Notifications
    {
        String TABLE_NAME = Notifications.class.getCanonicalName()
                                               .toLowerCase()
                                               .replace('.', '_') + "_table";
        String CREATE_TABLE = "create table if not exists " + TABLE_NAME + " (" +
                BaseColumns._ID + " integer primary key autoincrement, " +
                Columns.date + " integer" + "," +
                Columns.message + " text" + //"," +
                ");";

        interface Columns
        {
            String message = TABLE_NAME + "_" + "message";
            String date = TABLE_NAME + "_" + "date";
        }
    }
}