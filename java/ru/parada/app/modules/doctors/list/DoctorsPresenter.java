package ru.parada.app.modules.doctors.list;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import ru.parada.app.connection.DownloadFile;
import ru.parada.app.connection.ParadaService;
import ru.parada.app.connection.Request;
import ru.parada.app.contracts.doctors.DoctorsContract;
import ru.parada.app.contracts.ImagesContract;
import ru.parada.app.core.DoctorsCore;
import ru.parada.app.db.SQliteApi;
import ru.parada.app.json.JSONParser;
import ru.parada.app.managers.FoldersManager;
import ru.parada.app.modules.doctors.models.Doctor;
import ru.parada.app.modules.doctors.videos.model.Video;
import ru.parada.app.modules.images.ImageModel;
import ru.parada.app.units.ListModel;

public class DoctorsPresenter
    implements DoctorsContract.Presenter
{
    private DoctorsContract.View view;

    public DoctorsPresenter(DoctorsContract.View v)
    {
        view = v;
    }

    @Override
    public void load()
    {
        new Request(ParadaService.BASE_URL, ParadaService.Get.DOCTORS).execute(new Request.RequestListener()
        {
            @Override
            public void answer(String answer)
            {
                ArrayList doctors;
                try
                {
                    doctors = (ArrayList)JSONParser.newParser().parse(answer);
                }
                catch(Exception e)
                {
                    Log.e(getClass().getName(), "parse doctors " + e.getMessage());
                    return;
                }
                SQliteApi.getInstanse().getDoctors().clear();
                SQliteApi.getInstanse().startTransaction();
                for(Object doctor : doctors)
                {
                    if(!correcting((HashMap)doctor))
                    {
                        continue;
                    }
                    int id = Integer.parseInt((String)((HashMap)doctor).get("id"));
                    checkImage(id, (String)((HashMap)doctor).get("photo_url"));
                    SQliteApi.getInstanse().getDoctors().insertOne(new Doctor(id,
                            getString((HashMap)doctor, "last_name"),
                            getString((HashMap)doctor, "first_name"),
                            getString((HashMap)doctor, "middle_name"),
                            getString((HashMap)doctor, "first_position"),
                            getString((HashMap)doctor, "second_position"),
                            getString((HashMap)doctor, "third_position"),
                            null,
                            getString((HashMap)doctor, "descr"),
                            Integer.parseInt((String)((HashMap)doctor).get("order")),
                            getString((HashMap)doctor, "phone")));
                }
                SQliteApi.getInstanse().endTransaction();
                //Log.e(getClass().getName(), "load doctors");
                update();
            }
            @Override
            public void error(Exception e)
            {
                Log.e(getClass()
                          .getName(), "request " + ParadaService.BASE_URL + "\n" + ParadaService.Get.DOCTORS + "\n" + e.getMessage());
            }
        });
        new Request(ParadaService.BASE_URL, ParadaService.Get.VIDEOS).execute(new Request.RequestListener()
        {
            @Override
            public void answer(String answer)
            {
                ArrayList videos;
                try
                {
                    videos = (ArrayList)JSONParser.newParser().parse(answer);
                }
                catch(Exception e)
                {
                    Log.e(getClass().getName(), "parse videos " + e.getMessage());
                    return;
                }
                SQliteApi.getInstanse().getVideos().clear();
                SQliteApi.getInstanse().startTransaction();
                for(Object video : videos)
                {
                    int id = Integer.parseInt((String)((HashMap)video).get("id"));
                    String link = getString((HashMap)video, "link");
                    checkVideoImage(id, "https://img.youtube.com/vi/"+link+"/maxresdefault.jpg");
                    SQliteApi.getInstanse().getVideos().insertOne(new Video(id,
                            Integer.parseInt((String)((HashMap)video).get("doctor_id")),
                            getString((HashMap)video, "title"),
                            getString((HashMap)video, "descr"),
                            link,
                            null));
                }
                SQliteApi.getInstanse().endTransaction();
                //Log.e(getClass().getName(), "load videos");
            }
            @Override
            public void error(Exception e)
            {
                Log.e(getClass()
                        .getName(), "request " + ParadaService.BASE_URL + "\n" + ParadaService.Get.VIDEOS + "\n" + e.getMessage());
            }
        });
    }
    private void checkImage(final int id, final String photo_url)
    {
        final ImagesContract.Model oldModel = SQliteApi.getInstanse().getImages().getOneFromTypeAndEntityId(ImagesContract.Types.DOCTORS_TYPE, id);
        if(oldModel == null || oldModel.getImageUrl() == null || !oldModel.getImageUrl().equals(photo_url))
        {
            final String relativePath = "doctor-" + UUID.randomUUID().toString() + ".jpg";
            String fullPath = FoldersManager.getImagesDirectory() + "/" + relativePath;
            new DownloadFile(fullPath, photo_url).download(new DownloadFile.DownloadFileListener()
            {
                @Override
                public void answer(File file)
                {
                    SQliteApi.getInstanse().getImages().insertOne(new ImageModel(ImagesContract.Types.DOCTORS_TYPE, id, relativePath, photo_url));
                    if(oldModel != null && oldModel.getImagePath() != null)
                    {
                        new File(oldModel.getImagePath()).delete();
                    }
                    update();
                }
                @Override
                public void error(Exception error)
                {
                    Log.e(this.getClass().getName(), "download photo " + error.getMessage());
                }
            });
        }
    }
    private void checkVideoImage(final int id, final String photo_url)
    {
        final ImagesContract.Model oldModel = SQliteApi.getInstanse().getImages().getOneFromTypeAndEntityId(ImagesContract.Types.DOCTOR_VIDEOS_TYPE, id);
        if(oldModel == null || oldModel.getImageUrl() == null || !oldModel.getImageUrl().equals(photo_url))
        {
            final String relativePath = "doctor-video-" + UUID.randomUUID().toString() + ".jpg";
            String fullPath = FoldersManager.getImagesDirectory() + "/" + relativePath;
            new DownloadFile(fullPath, photo_url).download(new DownloadFile.DownloadFileListener()
            {
                @Override
                public void answer(File file)
                {
                    SQliteApi.getInstanse().getImages().insertOne(new ImageModel(ImagesContract.Types.DOCTOR_VIDEOS_TYPE, id, relativePath, photo_url));
                    if(oldModel != null && oldModel.getImagePath() != null)
                    {
                        new File(oldModel.getImagePath()).delete();
                    }
                }
                @Override
                public void error(Exception error)
                {
                    Log.e(this.getClass().getName(), "download photo " + error.getMessage());
                }
            });
        }
    }
    private String getString(HashMap map, String key)
    {
        if(map.get(key) == null)
        {
            return "";
        }
        return (String)map.get(key);
    }
    private boolean correcting(HashMap doctor)
    {
        if(doctor.get("id") == null)
        {
            return false;
        }
        try
        {
            Integer.parseInt((String)doctor.get("id"));
        }
        catch(Exception e)
        {
            return false;
        }
        if(doctor.get("order") == null)
        {
            doctor.put("order", 0);
        }
        try
        {
            Integer.parseInt((String)doctor.get("order"));
        }
        catch(Exception e)
        {
            doctor.put("order", 0);
        }
        return true;
    }

    @Override
    public void update()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                //Log.e(getClass().getName(), "update Thread " + Thread.currentThread());
                updateDoctors(SQliteApi.getInstanse().getDoctors().getAll());
            }
        }).start();
    }

    @Override
    public void search(final String keys)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                updateDoctors(SQliteApi.getInstanse().getDoctors().getFromKeys(keys));
            }
        }).start();
    }

    private void updateDoctors(ListModel<DoctorsCore.Model> data)
    {
        //Log.e(getClass().getName(), "update " + data.getItemsCount());
        view.update(data);
    }
}