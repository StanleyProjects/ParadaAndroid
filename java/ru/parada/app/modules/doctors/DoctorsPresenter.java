package ru.parada.app.modules.doctors;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import ru.parada.app.connection.DownloadFile;
import ru.parada.app.connection.ParadaService;
import ru.parada.app.connection.Request;
import ru.parada.app.contracts.DoctorDetailContract;
import ru.parada.app.contracts.DoctorsContract;
import ru.parada.app.contracts.ImagesContract;
import ru.parada.app.db.SQliteApi;
import ru.parada.app.json.JSONParser;
import ru.parada.app.managers.FoldersManager;
import ru.parada.app.modules.images.ImageModel;
import ru.parada.app.units.ListModel;

public class DoctorsPresenter
    implements DoctorsContract.Presenter
{
    private DoctorsContract.View view;

    public DoctorsPresenter(DoctorsContract.View v)
    {
        this.view = v;
    }

    @Override
    public void loadDoctors()
    {
        new Request(ParadaService.BASE_URL, ParadaService.GET_DOCTORS).execute(new Request.RequestListener()
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
                    Log.e(this.getClass().getName(), "parse doctors " + e.getMessage());
                    return;
                }
                SQliteApi.getInstanse().getDoctors().clearTable();
                SQliteApi.getInstanse().startTransaction();
                for(Object doctor : doctors)
                {
                    int id = Integer.parseInt((String)((HashMap)doctor).get("id"));
                    checkImage(id, (String)((HashMap)doctor).get("photo_url"));
                    SQliteApi.getInstanse().getDoctors().insertOne(new Doctor(id,
                            (String)((HashMap)doctor).get("last_name"),
                            (String)((HashMap)doctor).get("first_name"),
                            (String)((HashMap)doctor).get("middle_name"),
                            (String)((HashMap)doctor).get("first_position"),
                            (String)((HashMap)doctor).get("second_position"),
                            (String)((HashMap)doctor).get("third_position"),
                            null,
                            (String)((HashMap)doctor).get("descr"),
                            Integer.parseInt((String)((HashMap)doctor).get("order")),
                            (String)((HashMap)doctor).get("phone")));
                }
                SQliteApi.getInstanse().endTransaction();
                Log.e(this.getClass().getName(), "loadDoctors");
                updateDoctors();
            }
            @Override
            public void error(Exception error)
            {
                Log.e(this.getClass().getName(), "load doctors " + error.getMessage());
            }
        });
    }

    @Override
    public void updateDoctors()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Log.e(this.getClass().getName(), "updateDoctors t " + Thread.currentThread());
                updateDoctors(SQliteApi.getInstanse().getDoctors().getAll());
            }
        }).start();
    }

    @Override
    public void searchDoctors(final String keys)
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
                    updateDoctors();
                }
                @Override
                public void error(Exception error)
                {
                    Log.e(this.getClass().getName(), "download photo " + error.getMessage());
                }
            });
        }
    }

    private void updateDoctors(ListModel<DoctorDetailContract.Model> data)
    {
        Log.e(this.getClass().getName(), "updateDoctors " + data.getItemsCount());
        view.updateDoctors(data);
    }
}