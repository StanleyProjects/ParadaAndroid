package ru.parada.app.modules.map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import ru.parada.app.R;
import ru.parada.app.core.ContactsCore;

public class MapActivity
        extends FragmentActivity
        implements ContactsCore.Mark
{
    static public Intent createIntent(Context context, double latitude, double longitude)
    {
        return new Intent(context, MapActivity.class).putExtra(LATITUDE, latitude)
                                                     .putExtra(LONGITUDE, longitude);
    }

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.map_activity);
        getSupportFragmentManager().beginTransaction()
                                   .add(R.id.mapframe, MapFragment.newInstanse(getIntent().getExtras()
                                                                                          .getDouble(LATITUDE), getIntent().getExtras()
                                                                                                                           .getDouble(LONGITUDE)))
                                   .commit();
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }
}