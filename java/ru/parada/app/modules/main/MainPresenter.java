package ru.parada.app.modules.main;

import android.util.Log;

import java.util.ArrayList;

import ru.parada.app.connection.ParadaService;
import ru.parada.app.connection.Request;
import ru.parada.app.contracts.MainContract;
import ru.parada.app.json.JSONParser;

public class MainPresenter
    implements MainContract.Presenter
{
    private MainContract.View view;

    private boolean phoneOpen;

    public MainPresenter(MainContract.View v)
    {
        this.view = v;
        phoneOpen = false;
    }

    @Override
    public void phoneSwitch()
    {
        if(phoneOpen)
        {
            view.phoneOpen();
        }
        else
        {
            view.phoneClose();
        }
        phoneOpen = !phoneOpen;
    }

    @Override
    public void loadNews()
    {
//        new Request(ParadaService.BASE_URL, ParadaService.GET_NEWS).execute(new Request.RequestListener()
//        {
//            @Override
//            public void answer(String answer)
//            {
//                ArrayList news;
//                try
//                {
//                    news = (ArrayList)JSONParser.newParser().parse(answer);
//                }
//                catch(Exception e)
//                {
//                    Log.e(this.getClass().getCanonicalName(), "parse json: " + answer);
//                    return;
//                }
//                Log.e(this.getClass().getCanonicalName(), "load news: " + answer);
//            }
//            @Override
//            public void error(Exception error)
//            {
//                Log.e(this.getClass().getCanonicalName(), "load news error: " + error);
//            }
//        });
    }
}