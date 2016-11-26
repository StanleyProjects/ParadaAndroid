package ru.parada.app.modules.requestcall.check;

import android.util.Log;

import java.util.HashMap;

import ru.parada.app.connection.JsonObjectRequestListener;
import ru.parada.app.connection.ParadaService;
import ru.parada.app.connection.Request;
import ru.parada.app.contracts.requestcall.RequestCallCheckContract;
import ru.parada.app.core.RequestCallCore;

public class RequestCallCheckPresenter
        implements RequestCallCheckContract.Presenter
{
    private RequestCallCheckContract.View view;

    private final Request callRequest = new Request(ParadaService.BASE_URL, ParadaService.Post.REQUESTCALL_DATA);
    private final JsonObjectRequestListener response = new JsonObjectRequestListener()
    {
        @Override
        public void response(HashMap answer)
        {
            try
            {
                String result = (String)answer.get("result");
                if(result.equals("success"))
                {
                    view.sendSucess();
                    return;
                }
                Log.e(getClass()
                        .getName(), "result = " + result);
            }
            catch(Exception error)
            {
                Log.e(getClass()
                        .getName(), answer + "\n" + error.getMessage());
            }
            view.sendError();
        }
        @Override
        public void error(String url, Exception error)
        {
            Log.e(getClass()
                    .getName(), url + "\n" + error.getMessage());
            view.sendError();
        }
    };

    public RequestCallCheckPresenter(RequestCallCheckContract.View v)
    {
        view = v;
    }

    @Override
    public void send(RequestCallCore.Model data)
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", data.getPhone());
        map.put("fio", data.getName());
        callRequest.execute(map, response);
    }
}