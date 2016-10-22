package ru.parada.app.modules.actions.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
        String d = "" + from_date;
        date.setText(d);
    }
    public void setTitle(String t)
    {
        title.setText(t);
    }
}