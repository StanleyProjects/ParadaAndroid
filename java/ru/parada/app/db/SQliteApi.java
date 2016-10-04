package ru.parada.app.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.HashMap;

import ru.parada.app.contracts.DoctorsContract;
import ru.parada.app.contracts.ImagesContract;
import ru.parada.app.modules.doctors.Doctor;
import ru.parada.app.modules.doctors.DoctorsCursorListModel;
import ru.parada.app.modules.images.ImageModel;
import ru.parada.app.units.ListModel;

public class SQliteApi
{
    static private final String DB_NAME = "parada";
    static private final int DB_VERSION = 1610040237;
    static private volatile SQliteApi instanse;

    static public SQliteApi getInstanse()
    {
        if (instanse == null)
        {
            instanse = new SQliteApi();
        }
        return instanse;
    }

    private SQLiteDatabase sdb;
    private final Tables.News news = new Tables.News()
    {
        @Override
        public Cursor getAll()
        {
            return null;
        }

        @Override
        public Cursor getOneFromId(int id)
        {
            return null;
        }

        @Override
        public long insertOne(HashMap item)
        {
            return 0;
        }
    };
    private final Tables.Services services = new Tables.Services()
    {
        @Override
        public Cursor getAll()
        {
            return sdb.query(TABLE_NAME, null, null, null, null, null, null);
        }

        @Override
        public Cursor getOneFromId(int id)
        {
            return null;
        }

        @Override
        public long insertOne(HashMap item)
        {
            return sdb.insertWithOnConflict(TABLE_NAME, null, ContentDriver.getServiceContentValues(item), SQLiteDatabase.CONFLICT_REPLACE);
        }
    };
    private final Tables.Doctors doctors = new Tables.Doctors()
    {
        @Override
        public ListModel<DoctorsContract.ListItemModel> getAll()
        {
            return new DoctorsCursorListModel(sdb.rawQuery("SELECT * "
                    + "FROM " + TABLE_NAME + " "
                    + "LEFT JOIN " + Tables.Images.TABLE_NAME + " "
                    + "ON " + Tables.Images.Columns.type + " = " + ImagesContract.Types.DOCTORS_TYPE + " "
                    + "AND " + Tables.Doctors.TABLE_NAME + "." + BaseColumns._ID + " = " + Tables.Images.Columns.entity_id, new String[]{}));
        }

        @Override
        public DoctorsContract.ListItemModel getOneFromId(final int id)
        {
            Cursor cursor = sdb.rawQuery("SELECT * "
                    + "FROM " + TABLE_NAME + " "
                    + "WHERE " + BaseColumns._ID + "=" + id + " "
                    + "LEFT JOIN " + Tables.Images.TABLE_NAME + " "
                    + "ON " + Tables.Images.Columns.type + " = " + ImagesContract.Types.DOCTORS_TYPE, new String[]{});
            if(!cursor.moveToFirst())
            {
                cursor.close();
                return null;
            }
            DoctorsContract.ListItemModel doctor = new Doctor(id,
                    cursor.getString(cursor.getColumnIndex(Columns.last_name)),
                    cursor.getString(cursor.getColumnIndex(Columns.first_name)),
                    cursor.getString(cursor.getColumnIndex(Columns.middle_name)),
                    cursor.getString(cursor.getColumnIndex(Columns.first_position)),
                    cursor.getString(cursor.getColumnIndex(Columns.second_position)),
                    cursor.getString(cursor.getColumnIndex(Columns.third_position)),
                    cursor.getString(cursor.getColumnIndex(Tables.Images.Columns.image_path)));
            cursor.close();
            return doctor;
        }

        @Override
        public long insertOne(DoctorsContract.ListItemModel item)
        {
            return sdb.insertWithOnConflict(TABLE_NAME, null, ContentDriver.getDoctorContentValues(item), SQLiteDatabase.CONFLICT_REPLACE);
        }

        @Override
        public void clearTable()
        {
            sdb.execSQL("drop table if exists " + Tables.Doctors.TABLE_NAME);
            sdb.execSQL(Tables.Doctors.CREATE_TABLE);
        }
    };
    private final Tables.Images images = new Tables.Images()
    {
        @Override
        public ImagesContract.Model getOneFromTypeAndEntityId(final int type, final int id)
        {
            Cursor cursor = sdb.rawQuery("SELECT * "
                    + "FROM " + TABLE_NAME + " "
                    + "WHERE " + Columns.type + "=" + type + " "
                    + "AND " + Columns.entity_id + "=" + id, new String[]{});
            if(!cursor.moveToFirst())
            {
                cursor.close();
                return null;
            }
            ImagesContract.Model model = new ImageModel(id, type,
                    cursor.getInt(cursor.getColumnIndex(Columns.entity_id)),
                    cursor.getString(cursor.getColumnIndex(Columns.image_path)),
                    cursor.getString(cursor.getColumnIndex(Columns.image_url)));
            cursor.close();
            return model;
        }

        @Override
        public String getUrl(int type, int id)
        {
            Cursor cursor = sdb.rawQuery("SELECT * "
                    + "FROM " + TABLE_NAME + " "
                    + "WHERE " + BaseColumns._ID + "=" + id + " "
                    + "AND " + Columns.entity_id + "=" + type, new String[]{});
            if(!cursor.moveToFirst())
            {
                cursor.close();
                return null;
            }
            String image_url = cursor.getString(cursor.getColumnIndex(Columns.image_url));
            cursor.close();
            return image_url;
        }

        @Override
        public long insertOne(ImagesContract.Model item)
        {
            sdb.delete(TABLE_NAME, Columns.entity_id + "=" + item.getEntityId() + " "
                    + "AND " + Columns.type + "=" + item.getType(), new String[]{});
            return sdb.insert(TABLE_NAME, null, ContentDriver.getImageContentValues(item));
        }
    };

    private SQliteApi()
    {

    }

    public void createDB(Context context)
    {
        sdb = new SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION)
        {
            @Override
            public void onCreate(SQLiteDatabase db)
            {
                createTables(db);
            }
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
            {
                clearTables(db);
                onCreate(db);
            }
        }.getWritableDatabase();
    }

    public void startTransaction()
    {
        sdb.beginTransaction();
    }

    public void endTransaction()
    {
        sdb.setTransactionSuccessful();
        sdb.endTransaction();
    }

    public Tables.News getNews()
    {
        return news;
    }
    public Tables.Services getServices()
    {
        return services;
    }
    public Tables.Doctors getDoctors()
    {
        return doctors;
    }
    public Tables.Images getImages()
    {
        return images;
    }

    private void clearTables(SQLiteDatabase db)
    {
        db.execSQL("drop table if exists " + Tables.News.TABLE_NAME);
        db.execSQL("drop table if exists " + Tables.Services.TABLE_NAME);
        db.execSQL("drop table if exists " + Tables.Doctors.TABLE_NAME);
        db.execSQL("drop table if exists " + Tables.Images.TABLE_NAME);
    }
    private void createTables(SQLiteDatabase db)
    {
        db.execSQL(Tables.News.CREATE_TABLE);
        db.execSQL(Tables.Services.CREATE_TABLE);
        db.execSQL(Tables.Doctors.CREATE_TABLE);
        db.execSQL(Tables.Images.CREATE_TABLE);
    }
}