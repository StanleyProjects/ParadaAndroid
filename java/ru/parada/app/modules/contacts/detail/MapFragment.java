package ru.parada.app.modules.contacts.detail;

import android.os.Bundle;

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
import ru.parada.app.modules.contacts.model.Contact;

public class MapFragment
    extends SupportMapFragment
    implements ContactsCore.Mark
{
    static public SupportMapFragment newInstanse(ContactsCore.Model data)
    {
        MapFragment fragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NAME, data.getName());
        bundle.putInt(IMAGE, data.getImage());
        bundle.putDouble(LATITUDE, data.getLatitude());
        bundle.putDouble(LONGITUDE, data.getLongitude());
        fragment.setArguments(bundle);
        return fragment;
    }

    private ContactsCore.Model data;

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
        data = new Contact(getArguments().getString(NAME),
                getArguments().getInt(IMAGE),
                getArguments().getDouble(LATITUDE),
                getArguments().getDouble(LONGITUDE));
        getMapAsync(onMapReadyCallback);
    }

    private void setPin(GoogleMap googleMap)
    {
        LatLng pos = new LatLng(data.getLatitude(), data.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder().target(pos).zoom(16).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 1, null);
        googleMap.clear();
        googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_pin))
                .position(pos));
    }
}