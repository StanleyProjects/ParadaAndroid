package ru.parada.app.managers;

import android.util.Log;

import java.io.File;

public class FoldersManager
{
    private static String filesDirectory;

    static public void setFilesDirectory(String fd)
    {
        filesDirectory = fd;
        checkRoot();
        Log.e(FoldersManager.class.getCanonicalName(),"filesDir - " + filesDirectory);
    }
    static private boolean checkRoot()
    {
        String pathPhotos = FoldersManager.getImagesDirectory();
        File images = new File(pathPhotos);
        images.mkdirs();
        if(images.exists())
        {
            Log.e(FoldersManager.class.getCanonicalName(), "ImagesDirectory - " + FoldersManager.getImagesDirectory());
            return true;
        }
        return false;
    }

    static public String getFilesDirectory()
    {
        return filesDirectory;
    }
    static public String getImagesDirectory()
    {
        return getFilesDirectory() + "/images";
    }
}