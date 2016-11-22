package ru.parada.app.connection;

import java.util.HashMap;

import ru.parada.app.json.JSONParser;

public abstract class JsonObjectRequestListener
    implements RequestListener<HashMap>
{
    @Override
    public HashMap answer(String answer)
    {
        try
        {
            return (HashMap)JSONParser.newParser().parse(answer);
        }
        catch(Exception e)
        {
        }
        return null;
    }
}