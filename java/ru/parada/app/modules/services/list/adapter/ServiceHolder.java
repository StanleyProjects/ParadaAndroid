package ru.parada.app.modules.services.list.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.parada.app.App;
import ru.parada.app.R;
import ru.parada.app.units.AdapterHolder;

public class ServiceHolder
    extends AdapterHolder
{
    private ImageView image;
    private TextView title;

    public ServiceHolder(Context context, ViewGroup parent)
    {
        super(context, parent, R.layout.service_list_item);
        image = (ImageView) itemView.findViewById(R.id.image);
        title = (TextView) itemView.findViewById(R.id.title);
    }

    public void setPhoto(String photoPath)
    {
        if(photoPath == null || photoPath.length() == 0)
        {
            image.setImageResource(0);
        }
        else
        {
            image.setImageDrawable(App.getComponent().getImagesUtil().getThumbFromCache(photoPath,
                    App.getComponent().getAndroidUtil().dp(112), App.getComponent().getAndroidUtil().dp(112)));
        }
    }
    public void setTitle(String t)
    {
        title.setText(t);
    }
}