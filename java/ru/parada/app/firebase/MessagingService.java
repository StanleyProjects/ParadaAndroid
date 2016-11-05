package ru.parada.app.firebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService
        extends FirebaseMessagingService
{
    @Override
    public void onMessageReceived(RemoteMessage message)
    {
        if(message.getNotification() != null)
        {
            sendNotification(message.getNotification());
        }
    }

    private void sendNotification(RemoteMessage.Notification notification)
    {
        Log.e(getClass().getName(), "title " + notification.getTitle());
        Log.e(getClass().getName(), "body " + notification.getBody());
    }
}