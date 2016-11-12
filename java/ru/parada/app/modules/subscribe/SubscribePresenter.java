package ru.parada.app.modules.subscribe;

import ru.parada.app.contracts.SubscribeContract;

public class SubscribePresenter
        implements SubscribeContract.Presenter
{
    private SubscribeContract.View view;

    public SubscribePresenter(SubscribeContract.View v)
    {
        view = v;
    }
}