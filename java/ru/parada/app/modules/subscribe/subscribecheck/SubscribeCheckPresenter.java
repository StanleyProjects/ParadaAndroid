package ru.parada.app.modules.subscribe.subscribecheck;

import ru.parada.app.contracts.subscribe.SubscribeCheckContract;
import ru.parada.app.core.SubscribeCore;

public class SubscribeCheckPresenter
        implements SubscribeCheckContract.Presenter
{
    private SubscribeCheckContract.View view;

    public SubscribeCheckPresenter(SubscribeCheckContract.View v)
    {
        view = v;
    }

    @Override
    public void send(SubscribeCore.Model data)
    {

    }
}