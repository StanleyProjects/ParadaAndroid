package ru.parada.app.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.HashMap;
import java.util.Map;

import ru.parada.app.di.ImagesUtil;

public class IUtil
    implements ImagesUtil
{
    private final int MAX_SIZE = 15;
    private final Map<String, Drawable> imagesCache = new HashMap<>();
    private Resources resources;

    public IUtil(Resources r)
    {
        resources = r;
    }

    private void addImageToCache(Drawable d, String path)
    {
        if(imagesCache.size() > MAX_SIZE)
        {
            imagesCache.clear();
        }
        imagesCache.put(path, d);
    }

    private Bitmap optimize(String name, int inSampleSize)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeFile(name, options);
    }
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if(height > reqHeight && width > reqWidth)
        {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth)
            {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    @Override
    public Drawable getFromCache(String path)
    {
        Drawable drawable = imagesCache.get(path);
        if(drawable == null)
        {
            drawable = Drawable.createFromPath(path);
            addImageToCache(drawable, path);
        }
        return drawable;
    }

    @Override
    public Drawable getThumbFromCache(String path, int h, int w)
    {
        Drawable drawable = imagesCache.get("thumb" +h+w+ path);
        if(drawable == null)
        {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            int inSampleSize = calculateInSampleSize(options, h, w);
            Bitmap bitmap = optimize(path, inSampleSize);
            drawable = new BitmapDrawable(resources, bitmap);
            addImageToCache(drawable, "thumb" +h+w+ path);
        }
        return drawable;
    }
}