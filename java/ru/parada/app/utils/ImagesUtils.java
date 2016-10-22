package ru.parada.app.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

public class ImagesUtils
{
    static private final Map<String, Drawable> imagesCache = new HashMap<>();
    static private void addImageToCache(Drawable d, String path)
    {
        if(imagesCache.size() > 50)
        {
            Map.Entry<String, Drawable> entry = imagesCache.entrySet().iterator().next();
            imagesCache.remove(entry.getKey());
        }
        imagesCache.put(path, d);
    }
    static private Drawable getImageFromCache(String path)
    {
        return imagesCache.get(path);
    }

    static public void setThumpImage(String path, ImageView iv, int h, int w)
    {
        Drawable drawable = getImageFromCache("thumb" +h+w+ path);
        if(drawable == null)
        {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            int inSampleSize = ImagesUtils.calculateInSampleSize(options, h, w);
            Bitmap bitmap = ImagesUtils.optimize(path, inSampleSize);
            drawable = new BitmapDrawable(iv.getContext().getResources(), bitmap);
            addImageToCache(drawable, "thumb" +h+w+ path);
        }
        iv.setImageDrawable(drawable);
    }
    static public void setImage(String path, ImageView iv)
    {
        Drawable drawable = getImageFromCache(path);
        if(drawable == null)
        {
            drawable = Drawable.createFromPath(path);
            addImageToCache(drawable, path);
        }
        iv.setImageDrawable(drawable);
    }
    static public void setImageFromResources(int id, ImageView iv)
    {
        Drawable drawable = getImageFromCache("res"+id);
        if(drawable == null)
        {
            drawable = iv.getContext().getResources().getDrawable(id);
            addImageToCache(drawable, "res"+id);
        }
        iv.setImageDrawable(drawable);
    }

    static private Bitmap optimize(String name, int inSampleSize)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeFile(name, options);
    }
    static private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if(height > reqHeight || width > reqWidth)
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
}