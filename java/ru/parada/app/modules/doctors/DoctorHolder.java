package ru.parada.app.modules.doctors;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.parada.app.R;
import ru.parada.app.units.AdapterHolder;

public class DoctorHolder
    extends AdapterHolder
{
    private TextView last_name;

    public DoctorHolder(Context context, ViewGroup parent)
    {
        super(context, parent, R.layout.doctor_list_item);
        last_name = (TextView) itemView.findViewById(R.id.last_name);
    }

    public void setLastName(String text)
    {
        last_name.setText(text);
    }
}