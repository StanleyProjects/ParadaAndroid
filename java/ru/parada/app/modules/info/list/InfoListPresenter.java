package ru.parada.app.modules.info.list;

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
import ru.parada.app.contracts.info.InfoContract;
import ru.parada.app.core.InfoCore;
import ru.parada.app.db.SQliteApi;
import ru.parada.app.modules.images.ImageModel;
import ru.parada.app.modules.info.model.Info;
import ru.parada.app.units.ListModel;

public class InfoListPresenter
    implements InfoContract.Presenter
{
    private InfoContract.View view;

    private final Request request = new Request(ParadaService.BASE_URL, ParadaService.Get.INFO);

    public InfoListPresenter(InfoContract.View v)
    {
        view = v;
    }

    @Override
    public void update()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                updateInfo(SQliteApi.getInstanse().getInfo().getAll());
            }
        }).start();
    }
    private void updateInfo(ListModel<InfoCore.Model> data)
    {
        view.update(data);
    }

    @Override
    public void load()
    {
        request.execute(new JsonArrayRequestListener()
        {
            @Override
            public void response(ArrayList answer)
            {
                SQliteApi.getInstanse().getInfo().clear();
                SQliteApi.getInstanse().startTransaction();
                for(Object info : answer)
                {
                    if(!correcting((HashMap)info))
                    {
                        continue;
                    }
                    int id = Integer.parseInt((String)((HashMap)info).get("id"));
                    checkImage(id, getString((HashMap)info, "image_url"));
                    SQliteApi.getInstanse().getInfo().insertOne(new Info(id,
                            getString((HashMap)info, "title"),
                            getString((HashMap)info, "descr"),
                            null));
                }
                SQliteApi.getInstanse().endTransaction();
                update();
                view.load();
            }
            @Override
            public void error(String url, Exception error)
            {
                Log.e(getClass()
                        .getName(), url + "\n" + error.getMessage());
                view.load();
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
    private boolean correcting(HashMap hashMap)
    {
        if(hashMap.get("id") == null)
        {
            return false;
        }
        try
        {
            Integer.parseInt((String)hashMap.get("id"));
        }
        catch(Exception e)
        {
            return false;
        }
        return true;
    }
    private void checkImage(final int id, final String photo_url)
    {
        if(photo_url == null || photo_url.length() == 0)
        {
            return;
        }
        final ImagesContract.Model oldModel = SQliteApi.getInstanse().getImages().getOneFromTypeAndEntityId(ImagesContract.Types.INFO_TYPE, id);
        if(oldModel == null || oldModel.getImageUrl() == null || !oldModel.getImageUrl().equals(photo_url))
        {
            final String relativePath = "info-" + UUID.randomUUID().toString() + ".jpg";
            String fullPath = App.getComponent().getFoldersManager().getImagesDirectory() + "/" + relativePath;
            new DownloadFile(fullPath, photo_url).download(new DownloadFile.DownloadFileListener()
            {
                @Override
                public void answer(File file)
                {
                    SQliteApi.getInstanse().getImages().insertOne(new ImageModel(ImagesContract.Types.INFO_TYPE, id, relativePath, photo_url));
                    if(oldModel != null && oldModel.getImagePath() != null)
                    {
                        new File(oldModel.getImagePath()).delete();
                    }
                    update();
                }
                @Override
                public void error(Exception error)
                {
                    Log.e(getClass().getName(), "download photo " + error.getMessage());
                }
            });
        }
    }
}