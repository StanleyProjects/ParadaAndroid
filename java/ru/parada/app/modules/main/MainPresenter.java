package ru.parada.app.modules.main;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import ru.parada.app.connection.ParadaService;
import ru.parada.app.connection.Request;
import ru.parada.app.contracts.MainContract;
import ru.parada.app.db.SQliteApi;
import ru.parada.app.json.JSONParser;
import ru.parada.app.units.ListModel;

public class MainPresenter
    implements MainContract.Presenter
{
    private MainContract.View view;

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
    public void loadNews()
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
                    Log.e(this.getClass().getName(), "parse news " + e.getMessage());
                    return;
                }
                SQliteApi.getInstanse().getNews().clearTable();
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
                updateNews();
            }
            @Override
            public void error(Exception error)
            {
                Log.e(this.getClass().getName(), "load news " + error.getMessage());
            }
        });
    }

    @Override
    public void updateNews()
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

    private void updateNews(ListModel<MainContract.ListItemModel> data)
    {
        Log.e(this.getClass().getName(), "updateNews " + data.getItemsCount() + " view " + view);
        view.updateNews(data);
    }
}