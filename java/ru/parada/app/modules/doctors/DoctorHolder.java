package ru.parada.app.modules.doctors;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.parada.app.App;
import ru.parada.app.R;

public class DoctorHolder
{
    private Context context;

    private ImageView photo;
    private TextView last_name;
    private TextView first_middle_name;
    private TextView first_position;
    private TextView second_position;
    private TextView third_position;

    private Drawable photo_placeholder;

    public DoctorHolder(Context c, ImageView p, TextView ln, TextView fmn, TextView fp, TextView sp, TextView tp, Drawable pph)
    {
        context = c;
        photo = p;
        last_name = ln;
        first_middle_name = fmn;
        first_position = fp;
        second_position = sp;
        third_position = tp;
        photo_placeholder = pph;
    }

    public void setPhotoPlaceHolder()
    {
        photo.setImageDrawable(photo_placeholder);
    }
    public void setPhoto(String photoPath)
    {
        if(photoPath == null || photoPath.length() == 0)
        {
            photo.setImageDrawable(photo_placeholder);
        }
        else
        {
            photo.setImageDrawable(App.getComponent().getImagesUtil().getThumbFromCache(
                    photoPath,
                    App.getComponent().getAndroidUtil().dp(112), App.getComponent().getAndroidUtil().dp(112)));
        }
    }
    public void setLastName(String lastName)
    {
        last_name.setText(lastName);
    }
    public void setFirstMiddleName(String firstName, String middleName)
    {
        first_middle_name.setText(context.getResources().getString(R.string.first_middle_name, firstName, middleName));
    }

    public void setFirstPosition(String position)
    {
        if(position == null || position.length() == 0)
        {
            first_position.setVisibility(View.GONE);
        }
        else
        {
            first_position.setText(context.getResources().getString(R.string.doctor_position, position));
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
            second_position.setText(context.getResources().getString(R.string.doctor_position, position));
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
            third_position.setText(context.getResources()
                                           .getString(R.string.doctor_position, position));
            third_position.setVisibility(View.VISIBLE);
        }
    }
}