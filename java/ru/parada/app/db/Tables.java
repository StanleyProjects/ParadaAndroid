package ru.parada.app.db;

import android.database.Cursor;
import android.provider.BaseColumns;

import java.util.HashMap;

import ru.parada.app.contracts.DoctorsContract;
import ru.parada.app.units.ListModel;

public interface Tables
{
    interface News
    {
        String TABLE_NAME = News.class.getCanonicalName().toLowerCase().replace('.', '_') + "_table";
        String CREATE_TABLE = "create table if not exists " + TABLE_NAME + " (" +
                BaseColumns._ID + " integer primary key autoincrement, " +
                Columns.title + " text" + "," +
                Columns.descr + " text" + "," +
                Columns.date + " integer" + "," +
                Columns.full_descr + " text" + "," +
                Columns.image_url + " text" + "," +
                Columns.image + " text" + //"," +
                ");";
        interface Columns
        {
            String title = TABLE_NAME + "_" + "title";
            String descr = TABLE_NAME + "_" + "descr";
            String date = TABLE_NAME + "_" + "date";
            String full_descr = TABLE_NAME + "_" + "full_descr";
            String image_url = TABLE_NAME + "_" + "image_url";
            String image = TABLE_NAME + "_" + "image";
        }

        Cursor getAll();
        Cursor getOneFromId(int id);
        long insertOne(HashMap item);
    }
    interface Services
    {
        String TABLE_NAME = Services.class.getCanonicalName().toLowerCase().replace('.', '_') + "_table";
        String CREATE_TABLE = "create table if not exists " + TABLE_NAME + " (" +
                BaseColumns._ID + " integer primary key autoincrement, " +
                Columns.title + " text" + "," +
                Columns.descr + " text" + "," +
                Columns.order + " integer" + "," +
                Columns.group_id + " integer" + "," +
                Columns.subtitle + " text" + "," +
                Columns.full_descr + " text" + "," +
                Columns.preview_url + " text" + "," +
                Columns.preview + " text" + "," +
                Columns.image_url + " text" + "," +
                Columns.image + " text" + //"," +
                ");";
        interface Columns
        {
            String title = TABLE_NAME + "_" + "title";
            String descr = TABLE_NAME + "_" + "descr";
            String full_descr = TABLE_NAME + "_" + "full_descr";
            String order = TABLE_NAME + "_" + "order";
            String group_id = TABLE_NAME + "_" + "group_id";
            String subtitle = TABLE_NAME + "_" + "subtitle";
            String image_url = TABLE_NAME + "_" + "image_url";
            String image = TABLE_NAME + "_" + "image";
            String preview_url = TABLE_NAME + "_" + "preview_url";
            String preview = TABLE_NAME + "_" + "preview";
        }

        Cursor getAll();
        Cursor getOneFromId(int id);
        long insertOne(HashMap item);
    }
    interface Doctors
    {
        String TABLE_NAME = Doctors.class.getCanonicalName().toLowerCase().replace('.', '_') + "_table";
        String CREATE_TABLE = "create table if not exists " + TABLE_NAME + " (" +
                BaseColumns._ID + " integer primary key autoincrement, " +
                Columns.first_name + " text" + "," +
                Columns.last_name + " text" + "," +
                Columns.middle_name + " text" + "," +
                Columns.photo_url + " text" + "," +
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
            String photo_url = TABLE_NAME + "_" + "photo_url";
            String first_position = TABLE_NAME + "_" + "first_position";
            String second_position = TABLE_NAME + "_" + "second_position";
            String third_position = TABLE_NAME + "_" + "third_position";
            String phone = TABLE_NAME + "_" + "phone";
        }

        ListModel<DoctorsContract.ListItemModel> getAll();
        DoctorsContract.ListItemModel getOneFromId(int id);
        long insertOne(DoctorsContract.ListItemModel item);
        void clearTable();
    }
}