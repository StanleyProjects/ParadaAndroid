package ru.parada.app;

import android.app.Application;

import ru.parada.app.db.SQliteApi;
import ru.parada.app.di.AndroidUtil;
import ru.parada.app.di.DI;
import ru.parada.app.di.FoldersManager;
import ru.parada.app.di.ImagesUtil;
import ru.parada.app.di.PreferenceManager;
import ru.parada.app.managers.FManager;
import ru.parada.app.managers.PManager;
import ru.parada.app.utils.AUtil;
import ru.parada.app.utils.IUtil;

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
        component = new AppComponent(new FManager(getApplicationContext().getFilesDir().getAbsolutePath()),
                new PManager(getApplicationContext()),
                new AUtil(getApplicationContext()),
                new IUtil(getApplicationContext().getResources()));
    }

    private class AppComponent
        implements DI
    {
        private AndroidUtil androidUtil;
        private FoldersManager foldersManager;
        private ImagesUtil imagesUtil;
        private PreferenceManager preferenceManager;

        public AppComponent(FoldersManager fm, PreferenceManager pm, AndroidUtil au, ImagesUtil iu)
        {
            foldersManager = fm;
            preferenceManager = pm;
            androidUtil = au;
            imagesUtil = iu;
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

        @Override
        public PreferenceManager getPreferenceManager()
        {
            return preferenceManager;
        }

        @Override
        public ImagesUtil getImagesUtil()
        {
            return imagesUtil;
        }
    }
}