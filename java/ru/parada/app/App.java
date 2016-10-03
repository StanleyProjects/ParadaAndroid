package ru.parada.app;

import android.app.Application;
import android.util.Log;

import ru.parada.app.db.SQliteApi;
import ru.parada.app.managers.FoldersManager;

public class App
        extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        SQliteApi.getInstanse().createDB(getApplicationContext());
        FoldersManager.setFilesDirectory(getApplicationContext().getFilesDir().getAbsolutePath());
    }
}