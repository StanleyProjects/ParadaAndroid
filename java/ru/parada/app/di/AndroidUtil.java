package ru.parada.app.di;

import android.os.IBinder;

public interface AndroidUtil
{
    int dp(float px);
    void runOnUiThread(Runnable r);
    void runOnUiThread(Runnable r, long ms);
    void hideKeyBoard(IBinder token);
    void openPhone(String phone);
    void openMessages(String phone);
    void openBrowser(String link);
    void openMail(String address);
    boolean blockClick();
    boolean isTablet();
}