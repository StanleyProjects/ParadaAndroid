package ru.parada.app.modules.services.list;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import ru.parada.app.App;
import ru.parada.app.connection.DownloadFile;
import ru.parada.app.connection.JsonArrayRequestListener;
import ru.parada.app.connection.ParadaService;
import ru.parada.app.connection.Request;
import ru.parada.app.contracts.ImagesContract;
import ru.parada.app.contracts.ServicesContract;
import ru.parada.app.core.ServicesCore;
import ru.parada.app.db.SQliteApi;
import ru.parada.app.modules.images.ImageModel;
import ru.parada.app.modules.services.model.Service;
import ru.parada.app.units.ListModel;

public class ServicesPresenter
    implements ServicesContract.Presenter
{
    private ServicesContract.View view;

    private final Request servicesRequest = new Request(ParadaService.BASE_URL, ParadaService.Get.SERVICES);

    public ServicesPresenter(ServicesContract.View v)
    {
        this.view = v;
    }

    @Override
    public void loadServices()
    {
        servicesRequest.execute(new JsonArrayRequestListener()
        {
            @Override
            public void response(ArrayList answer)
            {
                SQliteApi.getInstanse().getServices().clear();
                SQliteApi.getInstanse().startTransaction();
                for(Object service : answer)
                {
                    if(!correcting((HashMap)service))
                    {
                        continue;
                    }
                    int id = Integer.parseInt((String)((HashMap)service).get("id"));
                    checkImage(id, (String)((HashMap)service).get("preview_url"));
                    checkDetailImage(id, (String)((HashMap)service).get("image_url"));
                    SQliteApi.getInstanse().getServices().insertOne(new Service(id,
                            Integer.parseInt((String)((HashMap)service).get("order")),
                            getString((HashMap)service, "title"),
                            getString((HashMap)service, "descr"),
                            null));
                }
                SQliteApi.getInstanse().endTransaction();
                updateServices();
            }
            @Override
            public void error(String url, Exception error)
            {
                Log.e(getClass()
                        .getName(), url + "\n" + error.getMessage());
            }
        });
    }
    private String getString(HashMap map, String key)
    {
        if(map.get(key) == null)
        {
            return "";
        }
        return (String)map.get(key);
    }
    private boolean correcting(HashMap service)
    {
        if(service.get("id") == null)
        {
            return false;
        }
        try
        {
            Integer.parseInt((String)service.get("id"));
        }
        catch(Exception e)
        {
            return false;
        }
        if(service.get("order") == null)
        {
            service.put("order", 0);
        }
        try
        {
            Integer.parseInt((String)service.get("order"));
        }
        catch(Exception e)
        {
            service.put("order", 0);
        }
        return true;
    }

    private void checkImage(final int id, final String photo_url)
    {
        final ImagesContract.Model oldModel = SQliteApi.getInstanse().getImages().getOneFromTypeAndEntityId(ImagesContract.Types.SERVICES_TYPE, id);
        if(oldModel == null || oldModel.getImageUrl() == null || !oldModel.getImageUrl().equals(photo_url))
        {
            final String relativePath = "service-" + UUID.randomUUID().toString() + ".jpg";
            String fullPath = App.getComponent().getFoldersManager().getImagesDirectory() + "/" + relativePath;
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
                    Log.e(getClass().getName(), "download photo " + error.getMessage());
                }
            });
        }
    }
    private void checkDetailImage(final int id, final String photo_url)
    {
        final ImagesContract.Model oldModel = SQliteApi.getInstanse().getImages().getOneFromTypeAndEntityId(ImagesContract.Types.SERVICE_DETAIL_TYPE, id);
        if(oldModel == null || oldModel.getImageUrl() == null || !oldModel.getImageUrl().equals(photo_url))
        {
            final String relativePath = "service-detail-" + UUID.randomUUID().toString() + ".jpg";
            String fullPath = App.getComponent().getFoldersManager().getImagesDirectory() + "/" + relativePath;
            new DownloadFile(fullPath, photo_url).download(new DownloadFile.DownloadFileListener()
            {
                @Override
                public void answer(File file)
                {
                    SQliteApi.getInstanse().getImages().insertOne(new ImageModel(ImagesContract.Types.SERVICE_DETAIL_TYPE, id, relativePath, photo_url));
                    if(oldModel != null && oldModel.getImagePath() != null)
                    {
                        new File(oldModel.getImagePath()).delete();
                    }
                }
                @Override
                public void error(Exception error)
                {
                    Log.e(getClass().getName(), "download photo " + error.getMessage());
                }
            });
        }
    }

    @Override
    public void updateServices()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                updateServices(SQliteApi.getInstanse().getServices().getAll());
            }
        }).start();
    }

    private void updateServices(ListModel<ServicesCore.Model> data)
    {
        //Log.e(this.getClass().getName(), "updateServices " + data.getItemsCount());
        view.updateServices(data);
    }
}