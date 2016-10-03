package ru.parada.app.modules.doctors;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import ru.parada.app.connection.Connection;
import ru.parada.app.connection.DownloadFile;
import ru.parada.app.connection.ParadaService;
import ru.parada.app.connection.Request;
import ru.parada.app.contracts.DoctorsContract;
import ru.parada.app.contracts.ImagesContract;
import ru.parada.app.db.SQliteApi;
import ru.parada.app.json.JSONParser;
import ru.parada.app.managers.FoldersManager;

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
//        updateDoctors();
        view.updateDoctors(SQliteApi.getInstanse().getDoctors().getAll());
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
                    return;
                }
                SQliteApi.getInstanse().getDoctors().clearTable();
                SQliteApi.getInstanse().startTransaction();
                for(Object doctor : doctors)
                {
                    final int id = Integer.parseInt((String)((HashMap)doctor).get("id"));
                    final String last_name = (String)((HashMap)doctor).get("last_name");
                    final String first_name = (String)((HashMap)doctor).get("first_name");
                    final String middle_name = (String)((HashMap)doctor).get("middle_name");
                    final String first_position = (String)((HashMap)doctor).get("first_position");
                    final String second_position = (String)((HashMap)doctor).get("second_position");
                    final String third_position = (String)((HashMap)doctor).get("third_position");
                    checkImage(id, (String)((HashMap)doctor).get("photo_url"));
                    SQliteApi.getInstanse().getDoctors().insertOne(new DoctorsContract.ListItemModel()
                    {
                        @Override
                        public int getId()
                        {
                            return id;
                        }

                        @Override
                        public String getPhotoPath()
                        {
                            return null;
                        }

                        @Override
                        public String getLastName()
                        {
                            return last_name;
                        }

                        @Override
                        public String getFirstName()
                        {
                            return first_name;
                        }

                        @Override
                        public String getMiddleName()
                        {
                            return middle_name;
                        }

                        @Override
                        public String getFirstPosition()
                        {
                            return first_position;
                        }

                        @Override
                        public String getSecondPosition()
                        {
                            return second_position;
                        }

                        @Override
                        public String getThirdPosition()
                        {
                            return third_position;
                        }
                    });
                }
                SQliteApi.getInstanse().endTransaction();
                updateDoctors();
            }
            @Override
            public void error(Exception error)
            {
                Log.e(this.getClass().getName(), "load doctors " + error.getMessage());
            }
        });
    }

    private void checkImage(final int id, final String photo_url)
    {
        final ImagesContract.Model oldModel = SQliteApi.getInstanse().getImages().getOneFromTypeAndEntityId(ImagesContract.Types.DOCTORS_TYPE, id);
//        if(model == null)
//        {
//            Log.e(this.getClass().getCanonicalName(), "model null");
//            return;
//        }
//        if(model.getImageUrl() == null)
//        {
//            Log.e(this.getClass().getCanonicalName(), "model ImageUrl null");
//            return;
//        }
//        String imageUrl = model.getImageUrl();
//        if(!imageUrl.equals(photo_url))
//        {
//            Log.e(this.getClass().getCanonicalName(), !model.getImageUrl().equals(photo_url) + "");
//            return;
//        }
        if(oldModel == null || oldModel.getImageUrl() == null || !oldModel.getImageUrl().equals(photo_url))
        {
            final String relativePath = "doctor-" + UUID.randomUUID().toString() + ".jpg";
            String fullPath = FoldersManager.getImagesDirectory() + "/" + relativePath;
            new DownloadFile(fullPath, photo_url).download(new DownloadFile.DownloadFileListener()
            {
                @Override
                public void answer(File file)
                {
                    SQliteApi.getInstanse().getImages().insertOne(new ImagesContract.Model()
                    {
                        @Override
                        public int getId()
                        {
                            return 0;
                        }
                        @Override
                        public int getType()
                        {
                            return ImagesContract.Types.DOCTORS_TYPE;
                        }
                        @Override
                        public int getEntityId()
                        {
                            return id;
                        }
                        @Override
                        public String getImagePath()
                        {
                            return relativePath;
                        }
                        @Override
                        public String getImageUrl()
                        {
                            return photo_url;
                        }
                    });
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

    private void updateDoctors()
    {
        new Handler(Looper.getMainLooper()).post(new Runnable()
        {
            @Override
            public void run()
            {
                view.updateDoctors(SQliteApi.getInstanse().getDoctors().getAll());
            }
        });
    }
}