package ru.parada.app.modules.services;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import ru.parada.app.connection.DownloadFile;
import ru.parada.app.connection.ParadaService;
import ru.parada.app.connection.Request;
import ru.parada.app.contracts.ImagesContract;
import ru.parada.app.contracts.ServicesContract;
import ru.parada.app.db.SQliteApi;
import ru.parada.app.json.JSONParser;
import ru.parada.app.managers.FoldersManager;
import ru.parada.app.modules.images.ImageModel;
import ru.parada.app.units.ListModel;

public class ServicesPresenter
    implements ServicesContract.Presenter
{
    private ServicesContract.View view;

    public ServicesPresenter(ServicesContract.View v)
    {
        this.view = v;
    }

    @Override
    public void loadServices()
    {
        new Request(ParadaService.BASE_URL, ParadaService.GET_SERVICES).execute(new Request.RequestListener()
        {
            @Override
            public void answer(String answer)
            {
                ArrayList services;
                try
                {
                    services = (ArrayList)JSONParser.newParser().parse(answer);
                }
                catch(Exception e)
                {
                    Log.e(this.getClass().getName(), "parse services " + e.getMessage());
                    return;
                }
                SQliteApi.getInstanse().getServices().clearTable();
                SQliteApi.getInstanse().startTransaction();
                for(Object service : services)
                {
                    int id = Integer.parseInt((String)((HashMap)service).get("id"));
                    checkImage(id, (String)((HashMap)service).get("image_url"));
                    SQliteApi.getInstanse().getServices().insertOne(new Service(id,
                            (String)((HashMap)service).get("title")));
                }
                SQliteApi.getInstanse().endTransaction();
                updateServices();
            }
            @Override
            public void error(Exception error)
            {
            }
        });
    }

    private void checkImage(final int id, final String photo_url)
    {
        final ImagesContract.Model oldModel = SQliteApi.getInstanse().getImages().getOneFromTypeAndEntityId(ImagesContract.Types.SERVICES_TYPE, id);
        if(oldModel == null || oldModel.getImageUrl() == null || !oldModel.getImageUrl().equals(photo_url))
        {
            final String relativePath = "service-" + UUID.randomUUID().toString() + ".jpg";
            String fullPath = FoldersManager.getImagesDirectory() + "/" + relativePath;
            new DownloadFile(fullPath, photo_url).download(new DownloadFile.DownloadFileListener()
            {
                @Override
                public void answer(File file)
                {
                    SQliteApi.getInstanse().getImages().insertOne(new ImageModel(ImagesContract.Types.SERVICES_TYPE, id, relativePath, photo_url));
                    if(oldModel != null && oldModel.getImagePath() != null)
                    {
                        new File(oldModel.getImagePath()).delete();
                    }
                    updateServices();
                }
                @Override
                public void error(Exception error)
                {
                    Log.e(this.getClass().getName(), "download photo " + error.getMessage());
                }
            });
        }
    }

    @Override
    public void updateServices()
    {
        updateServices(SQliteApi.getInstanse().getServices().getAll());
    }

    private void updateServices(ListModel<ServicesContract.ListItemModel> data)
    {
        view.updateServices(data);
    }
}