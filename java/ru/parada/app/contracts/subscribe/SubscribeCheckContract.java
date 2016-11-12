package ru.parada.app.contracts.subscribe;

import ru.parada.app.core.SubscribeCore;

public interface SubscribeCheckContract
{
    interface View
    {
        void sendSucess();
        void sendError();
    }
    interface Presenter
    {
        void send(SubscribeCore.Model data);
    }
    interface Behaviour
    {
        void back();
        void sendSucess();
    }
}