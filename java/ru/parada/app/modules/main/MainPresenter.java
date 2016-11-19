package ru.parada.app.modules.main;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import ru.parada.app.connection.DownloadFile;
import ru.parada.app.connection.JsonArrayRequestListener;
import ru.parada.app.connection.ParadaService;
import ru.parada.app.connection.Request;
import ru.parada.app.contracts.ImagesContract;
import ru.parada.app.contracts.MainContract;
import ru.parada.app.core.NewsCore;
import ru.parada.app.db.SQliteApi;
import ru.parada.app.json.JSONParser;
import ru.parada.app.managers.FoldersManager;
import ru.parada.app.modules.images.ImageModel;
import ru.parada.app.modules.news.model.OneOfNews;
import ru.parada.app.units.ListModel;

public class MainPresenter
    implements MainContract.Presenter
{
    private MainContract.View view;

    private final Request newsRequest = new Request(ParadaService.BASE_URL, ParadaService.Get.NEWS);

    public MainPresenter(MainContract.View v)
    {
        this.view = v;
    }

    @Override
    public void callDialogOpen()
    {
        view.callDialogOpen();
    }
    @Override
    public void callDialogClose()
    {
        view.callDialogClose();
    }

    @Override
    public void load()
    {
        newsRequest.execute(new JsonArrayRequestListener()
        {
            @Override
            public void response(ArrayList answer)
            {
                SQliteApi.getInstanse().getNews().clear();
                SQliteApi.getInstanse().startTransaction();
                for(Object oneOfNews : answer)
                {
                    if(!correcting((HashMap)oneOfNews))
                    {
                        continue;
                    }
                    int id = Integer.parseInt((String)((HashMap)oneOfNews).get("id"));
                    checkImage(id, (String)((HashMap)oneOfNews).get("image_url"));
                    SQliteApi.getInstanse().getNews().insertOne(new OneOfNews(id,
                            getString((HashMap)oneOfNews, "title"),
                            getString((HashMap)oneOfNews, "descr"),
                            getString((HashMap)oneOfNews, "full_descr"),
                            null,
                            Long.parseLong((String)((HashMap)oneOfNews).get("date"))));
                }
                SQliteApi.getInstanse().endTransaction();
                update();
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
    private boolean correcting(HashMap oneOfNews)
    {
        if(oneOfNews.get("id") == null)
        {
            return false;
        }
        try
        {
            Integer.parseInt((String)oneOfNews.get("id"));
        }
        catch(Exception e)
        {
            return false;
        }
        return true;
    }

    private void checkImage(final int id, final String photo_url)
    {
        final ImagesContract.Model oldModel = SQliteApi.getInstanse().getImages().getOneFromTypeAndEntityId(ImagesContract.Types.ONEOFNEWS_TYPE, id);
        if(oldModel == null || oldModel.getImageUrl() == null || !oldModel.getImageUrl().equals(photo_url))
        {
            final String relativePath = "oneofnews-" + UUID.randomUUID().toString() + ".jpg";
            String fullPath = FoldersManager.getImagesDirectory() + "/" + relativePath;
            new DownloadFile(fullPath, photo_url).download(new DownloadFile.DownloadFileListener()
            {
                @Override
                public void answer(File file)
                {
                    SQliteApi.getInstanse().getImages().insertOne(new ImageModel(ImagesContract.Types.ONEOFNEWS_TYPE, id, relativePath, photo_url));
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
    public void update()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Log.e(this.getClass().getName(), "updateNews " + Thread.currentThread());
                updateNews(SQliteApi.getInstanse().getNews().getAllWithLimit(2));
            }
        }).start();
    }

    private void updateNews(ListModel<NewsCore.Model> data)
    {
        Log.e(getClass().getName(), "updateNews " + data.getItemsCount() + " view " + view);
        view.update(data);
    }
}