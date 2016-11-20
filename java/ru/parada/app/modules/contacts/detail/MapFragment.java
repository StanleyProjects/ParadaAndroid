package ru.parada.app.modules.contacts.detail;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ru.parada.app.R;
import ru.parada.app.core.ContactsCore;

public class MapFragment
    extends SupportMapFragment
    implements ContactsCore.Mark
{
    static public SupportMapFragment newInstanse(double latitude, double longitude)
    {
        MapFragment fragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble(LATITUDE, latitude);
        bundle.putDouble(LONGITUDE, longitude);
        fragment.setArguments(bundle);
        return fragment;
    }

    private double latitude;
    private double longitude;

    private final OnMapReadyCallback onMapReadyCallback = new OnMapReadyCallback()
    {
        @Override
        public void onMapReady(GoogleMap googleMap)
        {
            setPin(googleMap);
        }
    };

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        latitude = getArguments().getDouble(LATITUDE);
        longitude = getArguments().getDouble(LONGITUDE);
        Log.e(getClass().getName(), "la " + latitude + " lo " + longitude);
        getMapAsync(onMapReadyCallback);
    }

    private void setPin(GoogleMap googleMap)
    {
        LatLng pos = new LatLng(latitude, longitude);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(pos).zoom(16).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 1, null);
        googleMap.clear();
        googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_pin))
                .position(pos));
    }
}