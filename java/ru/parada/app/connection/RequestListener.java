package ru.parada.app.connection;

public interface RequestListener<RESPONSE>
{
    RESPONSE answer(String answer);
    void response(RESPONSE answer);
    void error(String url, Exception error);
}