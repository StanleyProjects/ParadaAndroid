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
        if(imagesCache.size() > 30)
        {
            Map.Entry<String, Drawable> entry = imagesCache.entrySet().iterator().next();
            imagesCache.remove(entry.getKey());
        }
        imagesCache.put(path, d);
    }
    static private Drawable getImageToCache(String path)
    {
        return imagesCache.get(path);
    }

    static public void setThumpImage(String path, ImageView iv, int h, int w)
    {
        Drawable drawable = getImageToCache("thumb" + path);
        if(drawable != null)
        {
            iv.setImageDrawable(drawable);
            return;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int inSampleSize = ImagesUtils.calculateInSampleSize(options, h, w);
        Bitmap bitmap = ImagesUtils.optimize(path, inSampleSize);
        BitmapDrawable bd = new BitmapDrawable(iv.getContext().getResources(), bitmap);
        addImageToCache(bd, "thumb" + path);
        iv.setImageDrawable(bd);
    }
    static public void setImage(String path, ImageView iv)
    {
        Drawable drawable = getImageToCache(path);
        if(drawable != null)
        {
            iv.setImageDrawable(drawable);
            return;
        }
        drawable = Drawable.createFromPath(path);
        addImageToCache(drawable, path);
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