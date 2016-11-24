package ru.parada.app.modules.menu;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import ru.parada.app.App;
import ru.parada.app.connection.JsonArrayRequestListener;
import ru.parada.app.connection.ParadaService;
import ru.parada.app.connection.Request;
import ru.parada.app.contracts.MenuContract;
import ru.parada.app.core.GeneralCore;
import ru.parada.app.db.SQliteApi;
import ru.parada.app.modules.notifications.model.Notification;

public class MenuPresenter
    implements MenuContract.Presenter
{
    private MenuContract.View view;
    private final Request notificationsRequest = new Request(ParadaService.BASE_URL, ParadaService.Get.NOTIFICATIONS);

    public MenuPresenter(MenuContract.View v)
    {
        this.view = v;
    }

    @Override
    public void open(GeneralCore.ScreenType screenType)
    {
        view.set(screenType);
    }

    @Override
    public void checkNotifications()
    {
        notificationsRequest.execute(new JsonArrayRequestListener()
        {
            @Override
            public void response(ArrayList answer)
            {
                for(Object notification : answer)
                {
                    if(!SQliteApi.getInstanse()
                             .getNotifications()
                             .exist(new Notification(
                                     Integer.parseInt((String)((HashMap)notification).get("id")),
                                     getString((HashMap)notification, "message"),
                                     Long.parseLong((String)((HashMap)notification).get("date")), false)))
                    {
                        App.getComponent().getPreferenceManager().setNotificationBadge(true);
                        view.update();
                        break;
                    }
                }
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
}