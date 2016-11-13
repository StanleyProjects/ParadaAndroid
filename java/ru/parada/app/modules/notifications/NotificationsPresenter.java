package ru.parada.app.modules.notifications;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import ru.parada.app.connection.ParadaService;
import ru.parada.app.connection.Request;
import ru.parada.app.contracts.NotificationsContract;
import ru.parada.app.core.NotificationsCore;
import ru.parada.app.db.SQliteApi;
import ru.parada.app.json.JSONParser;
import ru.parada.app.modules.notifications.model.Notification;
import ru.parada.app.units.ListModel;

public class NotificationsPresenter
        implements NotificationsContract.Presenter
{
    private NotificationsContract.View view;

    public NotificationsPresenter(NotificationsContract.View v)
    {
        view = v;
    }

    @Override
    public void load()
    {
        new Request(ParadaService.BASE_URL, ParadaService.Get.NOTIFICATIONS).execute(new Request.RequestListener()
        {
            @Override
            public void answer(String answer)
            {
                ArrayList notifications;
                try
                {
                    notifications = (ArrayList)JSONParser.newParser()
                                                         .parse(answer);
                }
                catch(Exception e)
                {
                    Log.e(getClass().getName(), "parse " + e.getMessage());
                    return;
                }
                SQliteApi.getInstanse()
                         .getNotifications()
                         .clear();
                SQliteApi.getInstanse()
                         .startTransaction();
                for(Object notification : notifications)
                {
                    //Log.e(getClass().getName(), "message " + getString((HashMap)notification, "message"));
                    SQliteApi.getInstanse()
                             .getNotifications()
                             .insertOne(new Notification(
                                     Integer.parseInt((String)((HashMap)notification).get("id")),
                                     getString((HashMap)notification, "message"),
                                     Long.parseLong((String)((HashMap)notification).get("date"))));
                }
                SQliteApi.getInstanse()
                         .endTransaction();
                update();
            }
            @Override
            public void error(Exception e)
            {
                Log.e(getClass().getName(), "request " + ParadaService.BASE_URL + "\n" + ParadaService.Get.NOTIFICATIONS + "\n" + e.getMessage());
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

    @Override
    public void update()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                updateNotifications(SQliteApi.getInstanse()
                                             .getNotifications()
                                             .getAll());
            }
        }).start();
    }

    private void updateNotifications(ListModel<NotificationsCore.Model> data)
    {
        view.update(data);
    }
}