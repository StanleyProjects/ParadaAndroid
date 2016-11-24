package ru.parada.app.modules.menu;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuHolder
{
    private View background;
    private ImageView icon;
    private TextView name;
    private View badge;

    private int highlightBackColor;
    private int highlightColor;
    private int normalBackColor;
    private int normalIconColor;
    private int normalTextColor;

    public MenuHolder(View b, ImageView i, TextView n, View bdg, int hbc, int hc, int nbc, int nic, int ntc)
    {
        background = b;
        icon = i;
        name = n;
        badge = bdg;
        highlightBackColor = hbc;
        highlightColor = hc;
        normalBackColor = nbc;
        normalIconColor = nic;
        normalTextColor = ntc;
    }

    public void setNormal()
    {
        background.setBackgroundColor(normalBackColor);
        icon.setColorFilter(normalIconColor);
        name.setTextColor(normalTextColor);
    }
    public void highlight()
    {
        background.setBackgroundColor(highlightBackColor);
        icon.setColorFilter(highlightColor);
        name.setTextColor(highlightColor);
    }

    public void showBadge(boolean show)
    {
        if(show)
        {
            badge.setVisibility(View.VISIBLE);
        }
        else
        {
            badge.setVisibility(View.GONE);
        }
    }

    public ImageView getIcon()
    {
        return icon;
    }
    public void setName(String n)
    {
        name.setText(n);
    }
}