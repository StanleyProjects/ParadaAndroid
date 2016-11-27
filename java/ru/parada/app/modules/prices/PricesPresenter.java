package ru.parada.app.modules.prices;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import ru.parada.app.connection.JsonArrayRequestListener;
import ru.parada.app.connection.ParadaService;
import ru.parada.app.connection.Request;
import ru.parada.app.contracts.PricesContract;
import ru.parada.app.contracts.ServicesWithPricesContract;
import ru.parada.app.core.ServicesWithPricesCore;
import ru.parada.app.db.SQliteApi;
import ru.parada.app.json.JSONParser;
import ru.parada.app.modules.prices.models.Price;
import ru.parada.app.units.ListModel;

public class PricesPresenter
    implements PricesContract.Presenter
{
    private PricesContract.View view;

    private final Request pricesRequest = new Request(ParadaService.BASE_URL, ParadaService.Get.PRICES);

    public PricesPresenter(PricesContract.View v)
    {
        view = v;
    }

    @Override
    public void load(final int id)
    {
        pricesRequest.execute(new JsonArrayRequestListener()
        {
            @Override
            public void response(ArrayList answer)
            {
                SQliteApi.getInstanse().getPrices().clear();
                SQliteApi.getInstanse().startTransaction();
                for(Object price : answer)
                {
                    SQliteApi.getInstanse().getPrices().insertOne(new Price(
                            Integer.parseInt((String)((HashMap)price).get("id")),
                            getString((HashMap)price, "title"),
                            getString((HashMap)price, "value"),
                            Integer.parseInt((String)((HashMap)price).get("service_id"))
                    ));
                }
                SQliteApi.getInstanse().endTransaction();
                update(id);
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
    public void update(final int id)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                update(SQliteApi.getInstanse().getPrices().getAllFromServiceId(id), SQliteApi.getInstanse().getServicesWithPrices().getOneFromId(id));
            }
        }).start();
    }
    private void update(ListModel<PricesContract.Model> data, ServicesWithPricesCore.Model service)
    {
//        Log.e(getClass().getName(), "update " + data.getItemsCount() + " service " + service.getTitle());
        view.update(data, service);
    }
}