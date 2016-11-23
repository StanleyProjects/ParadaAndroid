package ru.parada.app.managers;

import android.util.Log;

import java.io.File;

import ru.parada.app.di.FoldersManager;

public class FManager
    implements FoldersManager
{
    private String filesDirectory;

    public FManager(String fd)
    {
        filesDirectory = fd;
        checkRoot();
        Log.e(getClass().getName(),"filesDir - " + filesDirectory);
    }
    private void checkRoot()
    {
        String pathPhotos = getImagesDirectory();
        File images = new File(pathPhotos);
        images.mkdirs();
        if(images.exists())
        {
            Log.e(getClass().getName(), "ImagesDirectory - " + getImagesDirectory());
        }
    }

    public String getFilesDirectory()
    {
        return filesDirectory;
    }
    public String getImagesDirectory()
    {
        return getFilesDirectory() + "/images";
    }
}