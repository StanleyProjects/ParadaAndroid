package ru.parada.app;

import android.app.Application;

import ru.parada.app.db.SQliteApi;
import ru.parada.app.di.AndroidUtil;
import ru.parada.app.di.DI;
import ru.parada.app.di.FoldersManager;
import ru.parada.app.managers.FManager;
import ru.parada.app.utils.AUtil;

public class App
        extends Application
{
    static private DI component;

    static public DI getComponent()
    {
        return component;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        SQliteApi.getInstanse().createDB(getApplicationContext());
        component = new DIApp(new AUtil(getApplicationContext()),
                new FManager(getApplicationContext().getFilesDir().getAbsolutePath()));
    }

    private class DIApp
        implements DI
    {
        private AndroidUtil androidUtil;
        private FoldersManager foldersManager;

        public DIApp(AndroidUtil au, FoldersManager fm)
        {
            androidUtil = au;
            foldersManager = fm;
        }

        @Override
        public AndroidUtil getAndroidUtil()
        {
            return androidUtil;
        }

        @Override
        public FoldersManager getFoldersManager()
        {
            return foldersManager;
        }
    }
}