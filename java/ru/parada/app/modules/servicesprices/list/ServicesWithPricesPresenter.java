package ru.parada.app.modules.servicesprices.list;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import ru.parada.app.connection.JsonArrayRequestListener;
import ru.parada.app.connection.ParadaService;
import ru.parada.app.connection.Request;
import ru.parada.app.contracts.ServicesWithPricesContract;
import ru.parada.app.core.ServicesWithPricesCore;
import ru.parada.app.db.SQliteApi;
import ru.parada.app.json.JSONParser;
import ru.parada.app.modules.servicesprices.models.ServiceWithPrice;
import ru.parada.app.units.ListModel;

public class ServicesWithPricesPresenter
        implements ServicesWithPricesContract.Presenter
{
    private ServicesWithPricesContract.View view;

    private final Request request = new Request(ParadaService.BASE_URL, ParadaService.Get.SERVICES_WITH_PRICES);

    public ServicesWithPricesPresenter(ServicesWithPricesContract.View v)
    {
        view = v;
    }

    @Override
    public void load()
    {
        request.execute(new JsonArrayRequestListener()
        {
            @Override
            public void response(ArrayList answer)
            {
                SQliteApi.getInstanse()
                         .getServicesWithPrices()
                         .clear();
                SQliteApi.getInstanse()
                         .startTransaction();
                for(Object service : answer)
                {
                    SQliteApi.getInstanse()
                             .getServicesWithPrices()
                             .insertOne(new ServiceWithPrice(
                                     Integer.parseInt((String)((HashMap)service).get("id")),
                                     getString((HashMap)service, "title"),
                                     getString((HashMap)service, "subtitle"),
                                     Integer.parseInt((String)((HashMap)service).get("order")),
                                     Integer.parseInt((String)((HashMap)service).get("group_id")),
                                     getString((HashMap)service, "group"),
                                     getInt((HashMap)service, "group_order")
                             ));
                }
                SQliteApi.getInstanse()
                         .endTransaction();
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
    private int getInt(HashMap map, String key)
    {
        if(map.get(key) == null)
        {
            return 0;
        }
        try
        {
            return Integer.parseInt((String)map.get(key));
        }
        catch(Exception e)
        {
        }
        return 0;
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
                updateServicesWithPrices(SQliteApi.getInstanse().getServicesWithPrices().getAll(), SQliteApi.getInstanse().getServicesWithPrices().getAllGroups());
            }
        }).start();
    }

    private void updateServicesWithPrices(ListModel<ServicesWithPricesCore.Model> allData, ListModel<ServicesWithPricesContract.GroupModel> groups)
    {
        view.update(allData, groups);
    }

    @Override
    public void search(final String keys)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                updateServicesWithPrices(SQliteApi.getInstanse().getServicesWithPrices().getAllFromKeys(keys), SQliteApi.getInstanse().getServicesWithPrices().getGroupsFromKeys(keys));
            }
        }).start();
    }
}