package ru.parada.app.contracts.subscribe;

import ru.parada.app.core.SubscribeCore;

public interface SubscribeContract
{
    interface View
    {
    }

    interface Presenter
    {
    }

    interface Behaviour
    {
        void back();
        void send(SubscribeCore.Model data);
    }
}