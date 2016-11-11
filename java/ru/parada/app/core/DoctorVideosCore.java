package ru.parada.app.core;

public interface DoctorVideosCore
{
    interface Mark
    {
        String VIDEO_ID = Mark.class.getCanonicalName().toLowerCase().replace(".", "_") + "_" + "video_id";
    }

    interface Model
    {
        int getId();
        int getDoctorId();
        String getDescription();
        String getTitle();
        String getImagePath();
        String getLink();
    }
}