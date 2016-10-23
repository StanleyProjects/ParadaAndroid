package ru.parada.app.modules.actions.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import ru.parada.app.R;
import ru.parada.app.units.AdapterHolder;
import ru.parada.app.utils.AndroidUtil;
import ru.parada.app.utils.ImagesUtils;

public class ActionHolder
        extends AdapterHolder
{
    private ImageView image;
    private TextView date;
    private TextView title;

    public ActionHolder(Context context, ViewGroup parent)
    {
        super(context, parent, R.layout.action_list_item);
        image = (ImageView) itemView.findViewById(R.id.image);
        date = (TextView) itemView.findViewById(R.id.date);
        title = (TextView) itemView.findViewById(R.id.title);
    }
    public void setPhoto(String photoPath)
    {
        if(photoPath != null)
        {
            ImagesUtils.setThumpImage(photoPath, image, AndroidUtil.dp(92), AndroidUtil.dp(92));
        }
    }
    public void setDate(long from_date, long to_date)
    {
        Date from = new Date(from_date);
        Date to = new Date(to_date);
        int m;
        int d;
        String dt = "";
        d = from.getDate();
        if(d<10)
        {
            dt +="0";
        }
        dt += d + ".";
        m = from.getMonth()+1;
        if(m<10)
        {
            dt +="0";
        }
        dt += "" + m;
        dt += " - ";
        d = to.getDate();
        if(d<10)
        {
            dt +="0";
        }
        dt += d + ".";
        m = to.getMonth()+1;
        if(m<10)
        {
            dt +="0";
        }
        dt += "" + m;
        date.setText(dt);
    }
    public void setTitle(String t)
    {
        title.setText(t);
    }
}