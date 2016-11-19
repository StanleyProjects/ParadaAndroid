package ru.parada.app.modules.subscribe.check;

import android.util.Log;

import java.util.HashMap;

import ru.parada.app.connection.ParadaService;
import ru.parada.app.connection.Request;
import ru.parada.app.connection.SimpleRequestListener;
import ru.parada.app.contracts.subscribe.SubscribeCheckContract;
import ru.parada.app.core.SubscribeCore;

public class SubscribeCheckPresenter
        implements SubscribeCheckContract.Presenter
{
    private SubscribeCheckContract.View view;

    private final Request subscribeRequest = new Request(ParadaService.BASE_URL, ParadaService.Post.SUBSCRIBE_DATA);

    public SubscribeCheckPresenter(SubscribeCheckContract.View v)
    {
        view = v;
    }

    @Override
    public void send(SubscribeCore.Model data)
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("date_text", data.getDate());
        map.put("time_text", data.getTime());
        map.put("last_name", data.getLastname());
        map.put("first_name", data.getFirstname());
        map.put("middle_name", data.getMiddlename());
        map.put("email", data.getEmail());
        map.put("phone", data.getPhone());
        map.put("comment", data.getComment());
        subscribeRequest.execute(map, new SimpleRequestListener()
        {
            @Override
            public void response(String answer)
            {
                Log.e(getClass()
                        .getName(), "request " + ParadaService.BASE_URL + "\n" + ParadaService.Post.SUBSCRIBE_DATA + "\n" + answer);
                view.sendSucess();
            }
            @Override
            public void error(String url, Exception error)
            {
                Log.e(getClass()
                        .getName(), url + "\n" + error.getMessage());
                view.sendError();
            }
        });
    }
}