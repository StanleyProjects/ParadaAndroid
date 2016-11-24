package ru.parada.app.di;

import android.graphics.drawable.Drawable;

public interface ImagesUtil
{
    Drawable getFromCache(String path);
    Drawable getThumbFromCache(String path, int h, int w);
}