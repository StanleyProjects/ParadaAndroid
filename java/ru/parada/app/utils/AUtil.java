package ru.parada.app.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import ru.parada.app.di.AndroidUtil;

public class AUtil
    implements AndroidUtil
{
    private float density;
    private Context context;
    private Handler uiHandler;
    private InputMethodManager inputMethodManager;

    public AUtil(Context c)
    {
        context = c;
        density = context.getResources().getDisplayMetrics().density;
        uiHandler = new Handler();
        inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public int dp(float px)
    {
        if(px < 0)
        {
            return 0;
        }
        return (int)Math.ceil(density * px);
    }

    @Override
    public void runOnUiThread(Runnable r)
    {
        uiHandler.post(r);
    }

    @Override
    public void runOnUiThread(Runnable r, long ms)
    {
        uiHandler.postDelayed(r, ms);
    }

    @Override
    public void hideKeyBoard(IBinder token)
    {
        inputMethodManager.hideSoftInputFromWindow(token, 0);
    }

    @Override
    public void openPhone(String phone)
    {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void openMessages(String phone)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("sms:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void openBrowser(String link)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(link));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void openMail(String address)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("mailto:" + address));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}