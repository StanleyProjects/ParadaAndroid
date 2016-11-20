package ru.parada.app.modules.services.list.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.parada.app.R;
import ru.parada.app.units.AdapterHolder;
import ru.parada.app.utils.AndroidUtil;
import ru.parada.app.utils.ImagesUtils;

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
            ImagesUtils.setThumbImage(photoPath, image, AndroidUtil.dp(112), AndroidUtil.dp(112));
        }
    }
    public void setTitle(String t)
    {
        title.setText(t);
    }
}