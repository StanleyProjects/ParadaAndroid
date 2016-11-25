package ru.parada.app.firebase.messaging;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import ru.parada.app.connection.ParadaService;
import ru.parada.app.connection.Request;
import ru.parada.app.connection.SimpleRequestListener;

public class InstanceIDService
        extends FirebaseInstanceIdService
{
    @Override
    public void onTokenRefresh()
    {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(getClass().getName(), "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken)
    {
        new Request(ParadaService.BASE_URL, ParadaService.Get.PUSH_TOKEN)
                .addField("token", refreshedToken)
                .execute(new SimpleRequestListener()
        {
            @Override
            public void response(String answer)
            {
                Log.e(getClass().getName(), "answer " + answer);
            }
            @Override
            public void error(String url, Exception error)
            {
                Log.e(getClass().getName(), "url " + url + "\n" + error.getMessage());
            }
        });
    }
}