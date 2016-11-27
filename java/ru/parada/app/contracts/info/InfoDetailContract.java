package ru.parada.app.contracts.info;

import ru.parada.app.core.InfoCore;

public interface InfoDetailContract
{
    interface View
    {
        void update(InfoCore.Model data);
    }

    interface Presenter
    {
        void update(int id);
    }

    interface Behaviour
    {
        void back();
    }
}