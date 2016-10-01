package ru.parada.app;

import android.app.Application;

import ru.parada.app.db.SQliteApi;

public class App
        extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        SQliteApi.getInstanse().createDB(getApplicationContext());
    }
}