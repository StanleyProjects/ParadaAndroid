package ru.parada.app.modules.contacts.model;

import ru.parada.app.core.ContactsCore;

public class Contact
    implements ContactsCore.Model
{
    private String name;
    private int image;
    private double latitude, longitude;

    public Contact(String n, int img, double lat, double lng)
    {
        name = n;
        image = img;
        latitude = lat;
        longitude = lng;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public int getImage()
    {
        return image;
    }

    @Override
    public double getLatitude()
    {
        return latitude;
    }

    @Override
    public double getLongitude()
    {
        return longitude;
    }
}