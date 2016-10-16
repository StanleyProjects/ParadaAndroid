package ru.parada.app.units;

public class GroupModel<MODEL>
{
    private MODEL data;
    private int type;

    public GroupModel(MODEL d, int t)
    {
        data = d;
        type = t;
    }

    public MODEL getData()
    {
        return data;
    }

    public int getType()
    {
        return type;
    }
}