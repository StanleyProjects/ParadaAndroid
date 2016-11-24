package ru.parada.app;

import android.app.Application;

import ru.parada.app.db.SQliteApi;
import ru.parada.app.di.AndroidUtil;
import ru.parada.app.di.DI;
import ru.parada.app.di.FoldersManager;
import ru.parada.app.di.ImagesUtil;
import ru.parada.app.managers.FManager;
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
        component = new AppComponent(new AUtil(getApplicationContext()),
                new FManager(getApplicationContext().getFilesDir().getAbsolutePath()),
                new IUtil(getApplicationContext().getResources()));
    }

    private class AppComponent
        implements DI
    {
        private AndroidUtil androidUtil;
        private FoldersManager foldersManager;
        private ImagesUtil imagesUtil;

        public AppComponent(AndroidUtil au, FoldersManager fm,ImagesUtil iu)
        {
            androidUtil = au;
            foldersManager = fm;
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
        public ImagesUtil getImagesUtil()
        {
            return imagesUtil;
        }
    }
}