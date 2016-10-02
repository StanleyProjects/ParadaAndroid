package ru.parada.app.units;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class AdapterHolder
        extends RecyclerView.ViewHolder
{
    public AdapterHolder(Context context, ViewGroup parent, int layout)
    {
        super(LayoutInflater.from(context).inflate(layout, parent, false));
    }
}