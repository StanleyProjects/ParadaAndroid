package ru.parada.app.modules.call;

public interface CallDialogListener
{
    void phone();
    void sms();
    void whatsapp();
    void viber();
    void close();
}