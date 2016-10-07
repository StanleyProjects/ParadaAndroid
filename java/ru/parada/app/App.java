package ru.parada.app;

import android.app.Application;

import ru.parada.app.db.SQliteApi;
import ru.parada.app.managers.FoldersManager;
import ru.parada.app.utils.AndroidUtil;

public class App
        extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        SQliteApi.getInstanse().createDB(getApplicationContext());
        FoldersManager.setFilesDirectory(getApplicationContext().getFilesDir().getAbsolutePath());
        AndroidUtil.setDensity(getApplicationContext().getResources().getDisplayMetrics().density);
    }
}