package ru.parada.app.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.HashMap;

import ru.parada.app.contracts.DoctorsContract;
import ru.parada.app.modules.doctors.DoctorsCursorListModel;
import ru.parada.app.units.ListModel;

public class SQliteApi
{
    static private final String DB_NAME = "parada";
    static private final int DB_VERSION = 1610032313;
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
            return new DoctorsCursorListModel(sdb.query(TABLE_NAME, null, null, null, null, null, null));
        }

        @Override
        public DoctorsContract.ListItemModel getOneFromId(final int id)
        {
            Cursor cursor = sdb.rawQuery("SELECT * "
                    + "FROM " + TABLE_NAME + " "
                    + "WHERE " + BaseColumns._ID + "=" + id, new String[]{});
            if(!cursor.moveToFirst())
            {
                cursor.close();
                return null;
            }
            final String last_name = cursor.getString(cursor.getColumnIndex(Columns.last_name));
            final String first_name = cursor.getString(cursor.getColumnIndex(Columns.first_name));
            final String middle_name = cursor.getString(cursor.getColumnIndex(Columns.middle_name));
            final String first_position = cursor.getString(cursor.getColumnIndex(Columns.first_position));
            final String second_position = cursor.getString(cursor.getColumnIndex(Columns.second_position));
            final String third_position = cursor.getString(cursor.getColumnIndex(Columns.third_position));
            cursor.close();
            return new DoctorsContract.ListItemModel()
            {
                @Override
                public int getId()
                {
                    return id;
                }
                @Override
                public String getLastName()
                {
                    return last_name;
                }

                @Override
                public String getFirstName()
                {
                    return first_name;
                }

                @Override
                public String getMiddleName()
                {
                    return middle_name;
                }

                @Override
                public String getFirstPosition()
                {
                    return first_position;
                }

                @Override
                public String getSecondPosition()
                {
                    return second_position;
                }

                @Override
                public String getThirdPosition()
                {
                    return third_position;
                }
            };
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

    private void clearTables(SQLiteDatabase db)
    {
        db.execSQL("drop table if exists " + Tables.News.TABLE_NAME);
        db.execSQL("drop table if exists " + Tables.Services.TABLE_NAME);
        db.execSQL("drop table if exists " + Tables.Doctors.TABLE_NAME);
    }
    private void createTables(SQLiteDatabase db)
    {
        db.execSQL(Tables.News.CREATE_TABLE);
        db.execSQL(Tables.Services.CREATE_TABLE);
        db.execSQL(Tables.Doctors.CREATE_TABLE);
    }
}