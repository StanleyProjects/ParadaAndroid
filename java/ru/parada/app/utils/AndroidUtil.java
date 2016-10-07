package ru.parada.app.utils;

public class AndroidUtil
{
    static private float density;
    static
    {
        density = 1;
    }

    static public void setDensity(float d)
    {
        density = d;
    }

    static public int dp(float value)
    {
        if(value == 0)
        {
            return 0;
        }
        return (int)Math.ceil(density * value);
    }
}