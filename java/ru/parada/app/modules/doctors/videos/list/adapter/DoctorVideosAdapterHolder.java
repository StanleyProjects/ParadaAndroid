package ru.parada.app.modules.doctors.videos.list.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.parada.app.App;
import ru.parada.app.R;
import ru.parada.app.units.AdapterHolder;
import ru.parada.app.utils.ImagesUtils;

public class DoctorVideosAdapterHolder
        extends AdapterHolder
{
    private TextView title;
    private ImageView image;

    public DoctorVideosAdapterHolder(Context context, ViewGroup parent)
    {
        super(context, parent, R.layout.doctor_videos_list_item);
        title = (TextView)itemView.findViewById(R.id.title);
        image = (ImageView)itemView.findViewById(R.id.image);
    }

    public void setTitle(String t)
    {
        title.setText(t);
    }
    public void setImage(String imp)
    {
        if(imp == null || imp.length() == 0)
        {
            image.setImageResource(0);
        }
        else
        {
            ImagesUtils.setThumbImage(imp, image, App.getComponent().getAndroidUtil().dp(128), App.getComponent().getAndroidUtil().dp(128));
        }
    }
    public void setPlay(View.OnClickListener clickListener)
    {
        itemView.setOnClickListener(clickListener);
    }
}