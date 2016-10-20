package ru.parada.app.modules.events;

import ru.parada.app.contracts.EventsContract;

public class EventsPresenter
        implements EventsContract.Presenter
{
    private EventsContract.View view;
    private boolean newsState;

    public EventsPresenter(EventsContract.View v)
    {
        view = v;
        newsState = true;
    }

    @Override
    public void setNews()
    {
        if(!newsState)
        {
            newsState = true;
            view.showNews();
        }
    }

    @Override
    public void setActions()
    {
        if(newsState)
        {
            newsState = false;
            view.showActions();
        }
    }
}