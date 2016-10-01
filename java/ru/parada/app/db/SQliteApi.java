package ru.parada.app.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

public class SQliteApi
{
    static private final String DB_NAME = "parada";
    static private final int DB_VERSION = 1610011556;
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

    private SQliteApi()
    {

    }

    public void createDB(Context context)
    {
        new SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION)
        {
            @Override
            public void onCreate(SQLiteDatabase db)
            {
                sdb = db;
                createTables();
            }
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
            {
                sdb = db;
                clearTables();
                createTables();
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

    private void clearTables()
    {
        sdb.execSQL("drop table if exists " + Tables.News.TABLE_NAME);
        sdb.execSQL("drop table if exists " + Tables.Services.TABLE_NAME);
    }
    private void createTables()
    {
        sdb.execSQL(Tables.News.CREATE_TABLE);
        sdb.execSQL(Tables.Services.CREATE_TABLE);
    }
}