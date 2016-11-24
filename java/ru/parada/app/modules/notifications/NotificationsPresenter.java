package ru.parada.app.modules.notifications;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import ru.parada.app.connection.JsonArrayRequestListener;
import ru.parada.app.connection.ParadaService;
import ru.parada.app.connection.Request;
import ru.parada.app.contracts.NotificationsContract;
import ru.parada.app.core.NotificationsCore;
import ru.parada.app.db.SQliteApi;
import ru.parada.app.modules.notifications.model.Notification;
import ru.parada.app.units.ListModel;

public class NotificationsPresenter
        implements NotificationsContract.Presenter
{
    private NotificationsContract.View view;

    private final Request notificationsRequest = new Request(ParadaService.BASE_URL, ParadaService.Get.NOTIFICATIONS);

    public NotificationsPresenter(NotificationsContract.View v)
    {
        view = v;
    }

    @Override
    public void load()
    {
        notificationsRequest.execute(new JsonArrayRequestListener()
        {
            @Override
            public void response(ArrayList answer)
            {
                SQliteApi.getInstanse()
                         .startTransaction();
                for(Object notification : answer)
                {
                    SQliteApi.getInstanse()
                             .getNotifications()
                             .insertOne(new Notification(
                                     Integer.parseInt((String)((HashMap)notification).get("id")),
                                     getString((HashMap)notification, "message"),
                                     Long.parseLong((String)((HashMap)notification).get("date")), false));
                }
                SQliteApi.getInstanse()
                         .endTransaction();
                update();
            }
            @Override
            public void error(String url, Exception error)
            {
                view.loadFail();
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