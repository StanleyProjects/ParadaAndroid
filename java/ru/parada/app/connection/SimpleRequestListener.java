package ru.parada.app.connection;

public abstract class SimpleRequestListener
    implements RequestListener<String>
{
    @Override
    public String answer(String answer)
    {
        return answer;
    }
}