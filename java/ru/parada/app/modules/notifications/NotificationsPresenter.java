package ru.parada.app.modules.notifications;

import ru.parada.app.contracts.NotificationsContract;

public class NotificationsPresenter
    implements NotificationsContract.Presenter
{
    private NotificationsContract.View view;

    public NotificationsPresenter(NotificationsContract.View v)
    {
        view = v;
    }
}