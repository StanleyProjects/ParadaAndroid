package ru.parada.app.modules.servicesprices;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

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

    public ServicesWithPricesPresenter(ServicesWithPricesContract.View v)
    {
        view = v;
    }

    @Override
    public void load()
    {
        new Request(ParadaService.BASE_URL, ParadaService.Get.SERVICES_WITH_PRICES).execute(new Request.RequestListener()
        {
            @Override
            public void answer(String answer)
            {
                ArrayList services;
                try
                {
                    services = (ArrayList)JSONParser.newParser().parse(answer);
//                    services = (ArrayList)JSONParser.newParser().parse("[{\"id\":\"1\",\"title\":\"rino1\",\"order\":\"1\",\"group_id\":\"2\",\"group\":\"Ринопластика\",\"group_order\":null},{\"id\":\"4\",\"title\":\"plast4\",\"order\":\"2\",\"group_id\":\"2\",\"group\":\"Ринопластика\",\"group_order\":null},{\"id\":\"2\",\"title\":\"plvk\",\"order\":\"2\",\"group_id\":\"3\",\"group\":\"Пластика век, отопластика\",\"group_order\":null},{\"id\":\"3\",\"title\":\"oto\",\"order\":\"3\",\"group_id\":\"3\",\"group\":\"Пластика век, отопластика\",\"group_order\":null},{\"id\":\"5\",\"title\":\"plastoto6\",\"order\":\"4\",\"group_id\":\"3\",\"group\":\"Пластика век, отопластика\",\"group_order\":null}]");
                }
                catch(Exception e)
                {
                    Log.e(this.getClass()
                              .getName(), "parse " + e.getMessage());
                    return;
                }
                SQliteApi.getInstanse()
                         .getServicesWithPrices()
                         .clear();
                SQliteApi.getInstanse()
                         .startTransaction();
                for(Object service : services)
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
//                                     Integer.parseInt((String)((HashMap)service).get("group_order"))
                             ));
                }
                SQliteApi.getInstanse()
                           .endTransaction();
                update();
            }
            @Override
            public void error(Exception e)
            {
                Log.e(this.getClass()
                          .getName(), "request " + ParadaService.BASE_URL + "\n" + ParadaService.Get.SERVICES_WITH_PRICES + "\n" + e.getMessage());
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