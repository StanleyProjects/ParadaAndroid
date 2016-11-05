package ru.parada.app.modules.actions;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import ru.parada.app.connection.DownloadFile;
import ru.parada.app.connection.ParadaService;
import ru.parada.app.connection.Request;
import ru.parada.app.contracts.ActionsContract;
import ru.parada.app.contracts.ImagesContract;
import ru.parada.app.core.ActionsCore;
import ru.parada.app.db.SQliteApi;
import ru.parada.app.json.JSONParser;
import ru.parada.app.managers.FoldersManager;
import ru.parada.app.modules.actions.model.Action;
import ru.parada.app.modules.images.ImageModel;
import ru.parada.app.units.ListModel;

public class ActionsPresenter
    implements ActionsContract.Presenter
{
    private ActionsContract.View view;

    public ActionsPresenter(ActionsContract.View v)
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
                Log.e(this.getClass().getName(), "update " + Thread.currentThread());
                updateActions(SQliteApi.getInstanse().getActions().getAll());
            }
        }).start();
    }

    private void updateActions(ListModel<ActionsCore.Model> data)
    {
        Log.e(this.getClass().getName(), "update " + data.getItemsCount() + " view " + view);
        view.update(data);
    }

    @Override
    public void load()
    {
        new Request(ParadaService.BASE_URL, ParadaService.Get.ACTIONS).execute(new Request.RequestListener()
        {
            @Override
            public void answer(String answer)
            {
                ArrayList actions;
                try
                {
                    actions = (ArrayList)JSONParser.newParser().parse(answer);
                }
                catch(Exception e)
                {
                    Log.e(this.getClass().getName(), "parse " + e.getMessage());
                    return;
                }
                SQliteApi.getInstanse().getActions().clear();
                SQliteApi.getInstanse().startTransaction();
                for(Object action : actions)
                {
                    if(!correcting((HashMap)action))
                    {
                        continue;
                    }
                    int id = Integer.parseInt((String)((HashMap)action).get("id"));
                    checkImage(id, (String)((HashMap)action).get("preview_url"));
                    checkDetailImage(id, (String)((HashMap)action).get("image_url"));
                    SQliteApi.getInstanse().getActions().insertOne(new Action(id,
                            getString((HashMap)action, "descr"),
                            getString((HashMap)action, "title"),
                            getString((HashMap)action, "subtitle"),
                            null,
                            Long.parseLong((String)((HashMap)action).get("from_date")),
                            Long.parseLong((String)((HashMap)action).get("to_date"))));
                }
                SQliteApi.getInstanse().endTransaction();
                update();
            }
            @Override
            public void error(Exception error)
            {
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
    private boolean correcting(HashMap action)
    {
        if(action.get("id") == null)
        {
            return false;
        }
        try
        {
            Integer.parseInt((String)action.get("id"));
        }
        catch(Exception e)
        {
            return false;
        }
        if(action.get("from_date") == null)
        {
            return false;
        }
        try
        {
            Long.parseLong((String)action.get("from_date"));
        }
        catch(Exception e)
        {
            return false;
        }
        if(action.get("to_date") == null)
        {
            return false;
        }
        try
        {
            Long.parseLong((String)action.get("to_date"));
        }
        catch(Exception e)
        {
            return false;
        }
        return true;
    }

    private void checkImage(final int id, final String photo_url)
    {
        final ImagesContract.Model oldModel = SQliteApi.getInstanse().getImages().getOneFromTypeAndEntityId(ImagesContract.Types.ACTIONS_TYPE, id);
        if(oldModel == null || oldModel.getImageUrl() == null || !oldModel.getImageUrl().equals(photo_url))
        {
            final String relativePath = "action-" + UUID.randomUUID().toString() + ".jpg";
            String fullPath = FoldersManager.getImagesDirectory() + "/" + relativePath;
            new DownloadFile(fullPath, photo_url).download(new DownloadFile.DownloadFileListener()
            {
                @Override
                public void answer(File file)
                {
                    SQliteApi.getInstanse().getImages().insertOne(new ImageModel(ImagesContract.Types.ACTIONS_TYPE, id, relativePath, photo_url));
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
    private void checkDetailImage(final int id, final String photo_url)
    {
        final ImagesContract.Model oldModel = SQliteApi.getInstanse().getImages().getOneFromTypeAndEntityId(ImagesContract.Types.ACTION_DETAIL_TYPE, id);
        if(oldModel == null || oldModel.getImageUrl() == null || !oldModel.getImageUrl().equals(photo_url))
        {
            final String relativePath = "action-detail-" + UUID.randomUUID().toString() + ".jpg";
            String fullPath = FoldersManager.getImagesDirectory() + "/" + relativePath;
            new DownloadFile(fullPath, photo_url).download(new DownloadFile.DownloadFileListener()
            {
                @Override
                public void answer(File file)
                {
                    SQliteApi.getInstanse().getImages().insertOne(new ImageModel(ImagesContract.Types.ACTION_DETAIL_TYPE, id, relativePath, photo_url));
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
}