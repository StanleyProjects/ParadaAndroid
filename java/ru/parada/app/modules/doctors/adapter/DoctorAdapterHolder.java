package ru.parada.app.modules.doctors.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.parada.app.R;
import ru.parada.app.modules.doctors.DoctorHolder;
import ru.parada.app.units.AdapterHolder;

public class DoctorAdapterHolder
        extends AdapterHolder
{
    private DoctorHolder holder;

    public DoctorAdapterHolder(Context context, ViewGroup parent)
    {
        super(context, parent, R.layout.doctor_list_item);
        holder = new DoctorHolder(context, (ImageView)itemView.findViewById(R.id.photo),
                (TextView)itemView.findViewById(R.id.last_name),
                (TextView)itemView.findViewById(R.id.first_middle_name),
                (TextView)itemView.findViewById(R.id.first_position),
                (TextView)itemView.findViewById(R.id.second_position),
                (TextView)itemView.findViewById(R.id.third_position),
                context.getResources().getDrawable(R.drawable.photo_placeholder));
    }

    public DoctorHolder getHolder()
    {
        return holder;
    }
}