package ru.parada.app.modules.news;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import ru.parada.app.connection.ParadaService;
import ru.parada.app.connection.Request;
import ru.parada.app.contracts.NewsContract;
import ru.parada.app.core.NewsCore;
import ru.parada.app.db.SQliteApi;
import ru.parada.app.json.JSONParser;
import ru.parada.app.modules.news.model.OneOfNews;
import ru.parada.app.units.ListModel;

public class NewsPresenter
    implements NewsContract.Presenter
{
    private NewsContract.View view;

    public NewsPresenter(NewsContract.View v)
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
                updateNews(SQliteApi.getInstanse().getNews().getAll());
            }
        }).start();
    }

    @Override
    public void load()
    {
        new Request(ParadaService.BASE_URL, ParadaService.Get.NEWS).execute(new Request.RequestListener()
        {
            @Override
            public void answer(String answer)
            {
                ArrayList news;
                try
                {
                    news = (ArrayList)JSONParser.newParser().parse(answer);
                }
                catch(Exception e)
                {
                    Log.e(this.getClass().getName(), "parse " + e.getMessage());
                    return;
                }
                SQliteApi.getInstanse().getNews().clear();
                SQliteApi.getInstanse().startTransaction();
                for(Object oneOfNews : news)
                {
                    SQliteApi.getInstanse().getNews().insertOne(new OneOfNews(
                            Integer.parseInt((String)((HashMap)oneOfNews).get("id")),
                            (String)((HashMap)oneOfNews).get("title"),
                            (String)((HashMap)oneOfNews).get("descr"),
                            Long.parseLong((String)((HashMap)oneOfNews).get("date"))));
                }
                SQliteApi.getInstanse().endTransaction();
                update();
            }
            @Override
            public void error(Exception error)
            {
                Log.e(this.getClass().getName(), "load " + error.getMessage());
            }
        });
    }

    private void updateNews(ListModel<NewsCore.OneOfNewsModel> data)
    {
        Log.e(this.getClass().getName(), "update " + data.getItemsCount() + " view " + view);
        view.update(data);
    }
}