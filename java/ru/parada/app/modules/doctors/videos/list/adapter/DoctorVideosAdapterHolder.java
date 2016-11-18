package ru.parada.app.modules.doctors.videos.list.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.parada.app.R;
import ru.parada.app.units.AdapterHolder;
import ru.parada.app.utils.AndroidUtil;
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
        ImagesUtils.setThumbImage(imp, image, AndroidUtil.dp(128), AndroidUtil.dp(128));
    }
    public void setPlay(View.OnClickListener clickListener)
    {
        itemView.setOnClickListener(clickListener);
    }
}