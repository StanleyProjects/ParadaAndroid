package ru.parada.app.di;

import android.os.IBinder;

public interface AndroidUtil
{
    int dp(float value);
    void runOnUiThread(Runnable r);
    void hideKeyBoard(IBinder token);
    void openPhone(String phone);
    void openMessages(String phone);
    void openBrowser(String link);
}