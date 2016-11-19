package ru.parada.app.connection;

import java.util.ArrayList;

import ru.parada.app.json.JSONParser;

public abstract class JsonArrayRequestListener
    implements RequestListener<ArrayList>
{
    @Override
    public ArrayList answer(String answer)
    {
        try
        {
            return (ArrayList)JSONParser.newParser().parse(answer);
        }
        catch(Exception e)
        {
        }
        return null;
    }
}