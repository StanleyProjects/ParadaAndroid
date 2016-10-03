package ru.parada.app.modules.doctors;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.parada.app.R;
import ru.parada.app.units.AdapterHolder;

public class DoctorHolder
    extends AdapterHolder
{
    private TextView last_name;
    private TextView first_middle_name;
    private TextView first_position;
    private TextView second_position;
    private TextView third_position;

    public DoctorHolder(Context context, ViewGroup parent)
    {
        super(context, parent, R.layout.doctor_list_item);
        last_name = (TextView) itemView.findViewById(R.id.last_name);
        first_middle_name = (TextView) itemView.findViewById(R.id.first_middle_name);
        first_position = (TextView) itemView.findViewById(R.id.first_position);
        second_position = (TextView) itemView.findViewById(R.id.second_position);
        third_position = (TextView) itemView.findViewById(R.id.third_position);
        second_position.setVisibility(View.GONE);
        third_position.setVisibility(View.GONE);
    }

    public void setLastName(String lastName)
    {
        last_name.setText(lastName);
    }
    public void setFirstMiddleName(String firstName, String middleName)
    {
        first_middle_name.setText(itemView.getResources().getString(R.string.first_middle_name, firstName, middleName));
    }

    public void setFirstPosition(String position)
    {
        if(position == null || position.length() == 0)
        {
            first_position.setVisibility(View.GONE);
        }
        else
        {
            first_position.setText(itemView.getResources().getString(R.string.doctor_position, position));
            first_position.setVisibility(View.VISIBLE);
        }
    }
    public void setSecondPosition(String position)
    {
        if(position == null || position.length() == 0)
        {
            second_position.setVisibility(View.GONE);
        }
        else
        {
            second_position.setText(itemView.getResources().getString(R.string.doctor_position, position));
            second_position.setVisibility(View.VISIBLE);
        }
    }
    public void setThirdPosition(String position)
    {
        if(position == null || position.length() == 0)
        {
            third_position.setVisibility(View.GONE);
        }
        else
        {
            third_position.setText(itemView.getResources()
                                           .getString(R.string.doctor_position, position));
            third_position.setVisibility(View.VISIBLE);
        }
    }
}