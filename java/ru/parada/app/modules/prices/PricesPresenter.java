package ru.parada.app.modules.prices;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

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

    public PricesPresenter(PricesContract.View v)
    {
        view = v;
    }

    @Override
    public void load(final int id)
    {
        new Request(ParadaService.BASE_URL, ParadaService.Get.PRICES).execute(new Request.RequestListener()
        {
            @Override
            public void answer(String answer)
            {
                ArrayList prices;
                try
                {
                    prices = (ArrayList)JSONParser.newParser().parse(answer);
                }
                catch(Exception e)
                {
                    Log.e(this.getClass().getName(), "parse " + e.getMessage());
                    return;
                }
                SQliteApi.getInstanse().getPrices().clear();
                SQliteApi.getInstanse().startTransaction();
                for(Object price : prices)
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
            public void error(Exception error)
            {
                Log.e(this.getClass().getName(), "load " + error.getMessage());
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
                Log.e(this.getClass().getName(), "update t " + Thread.currentThread());
                update(SQliteApi.getInstanse().getPrices().getAllFromServiceId(id), SQliteApi.getInstanse().getServicesWithPrices().getOneFromId(id));
            }
        }).start();
    }
    private void update(ListModel<PricesContract.Model> data, ServicesWithPricesCore.Model service)
    {
        Log.e(this.getClass().getName(), "update " + data.getItemsCount() + " service " + service.getTitle());
        view.update(data, service);
    }
}