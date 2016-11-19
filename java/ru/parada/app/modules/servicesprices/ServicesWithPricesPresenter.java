package ru.parada.app.modules.servicesprices;

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

    private final Request servicesWithPricesRequest = new Request(ParadaService.BASE_URL, ParadaService.Get.SERVICES_WITH_PRICES);

    public ServicesWithPricesPresenter(ServicesWithPricesContract.View v)
    {
        view = v;
    }

    @Override
    public void load()
    {
        servicesWithPricesRequest.execute(new JsonArrayRequestListener()
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
                                     0
                             ));
                }
                SQliteApi.getInstanse()
                         .endTransaction();
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