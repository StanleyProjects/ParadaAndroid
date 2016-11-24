package ru.parada.app.managers;

import android.content.Context;
import android.content.SharedPreferences;

import ru.parada.app.di.PreferenceManager;

public class PManager
    implements PreferenceManager
{
    static private final String PREFERENCE = PManager.class.getName().replace(".", "_") + "_" + "preference";
    static private final String NOTIFICATION_BADGE = PREFERENCE + "_" + "notification_badge";

    private SharedPreferences preferences;

    public PManager(Context context)
    {
        preferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
    }

    @Override
    public void setNotificationBadge(boolean need)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(NOTIFICATION_BADGE, need);
        editor.apply();
    }

    @Override
    public boolean needNotificationBadge()
    {
        return preferences.getBoolean(NOTIFICATION_BADGE, false);
    }
}
