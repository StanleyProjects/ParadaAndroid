package ru.parada.app.modules.doctors.videos.model;

import android.util.Log;

import ru.parada.app.core.DoctorVideosCore;

public class Video
    implements DoctorVideosCore.Model
{
    private int id;
    private int doctor_id;
    private String title;
    private String descr;
    private String link;
    private String image_path;

    public Video(int i, int d, String t, String de, String l, String imp)
    {
        //Log.e(getClass().getName(), i + " " + d + " " + t);
        id = i;
        doctor_id = d;
        title = t;
        descr = de;
        link = l;
        image_path = imp;
    }

    @Override
    public int getId()
    {
        return id;
    }

    @Override
    public int getDoctorId()
    {
        return doctor_id;
    }

    @Override
    public String getDescription()
    {
        return descr;
    }

    @Override
    public String getTitle()
    {
        return title;
    }

    @Override
    public String getImagePath()
    {
        return image_path;
    }

    @Override
    public String getLink()
    {
        return link;
    }
}