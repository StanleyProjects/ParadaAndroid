package ru.parada.app.modules.subscribe.subscribescreen;

import ru.parada.app.contracts.subscribe.SubscribeContract;

public class SubscribePresenter
        implements SubscribeContract.Presenter
{
    private SubscribeContract.View view;

    public SubscribePresenter(SubscribeContract.View v)
    {
        view = v;
    }
}