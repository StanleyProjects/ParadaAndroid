package ru.parada.app.contracts.requestcall;

import ru.parada.app.core.RequestCallCore;

public interface RequestCallCheckContract
{
    interface View
    {
        void sendSucess();
        void sendError();
    }
    interface Presenter
    {
        void send(RequestCallCore.Model data);
    }
    interface Behaviour
    {
        void back();
        void sendSucess();
    }
}