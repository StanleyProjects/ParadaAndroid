package ru.parada.app.modules.main;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;

import ru.parada.app.R;
import ru.parada.app.units.AdapterHolder;

public class OneOfNewsHolder
    extends AdapterHolder
{
    private TextView title;
    private TextView descr;
    private TextView date;

    private String[] months;

    public OneOfNewsHolder(Context context, ViewGroup parent)
    {
        super(context, parent, R.layout.news_list_item);
        title = (TextView) itemView.findViewById(R.id.title);
        descr = (TextView) itemView.findViewById(R.id.descr);
        date = (TextView) itemView.findViewById(R.id.date);
        months = context.getResources().getStringArray(R.array.months);
    }

    public void setTitle(String t)
    {
        title.setText(t);
    }
    public void setDescr(String d)
    {
        descr.setText(d);
    }
    public void setDate(long d)
    {
        Date currentDate = new Date(d);
        date.setText(currentDate.getDate() + " " + months[currentDate.getMonth()] + " " + (currentDate.getYear()+1900));
    }
}