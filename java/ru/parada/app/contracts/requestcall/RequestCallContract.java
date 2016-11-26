package ru.parada.app.contracts.requestcall;

import ru.parada.app.core.RequestCallCore;

public interface RequestCallContract
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
        void send(RequestCallCore.Model data);
    }
}