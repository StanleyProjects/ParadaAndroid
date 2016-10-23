package ru.parada.app.modules.notifications.model;

import android.database.Cursor;
import android.provider.BaseColumns;

import ru.parada.app.contracts.NotificationsContract;
import ru.parada.app.db.Tables;
import ru.parada.app.units.CursorListModel;

public class NotificationsCursorListModel
        extends CursorListModel<NotificationsContract.Model>
{
    public NotificationsCursorListModel(Cursor d)
    {
        super(d);
    }

    @Override
    protected NotificationsContract.Model getModel(int i)
    {
        return new NotificationsContract.Model()
        {
            @Override
            public int getId()
            {
                return getInt(BaseColumns._ID);
            }

            @Override
            public String getMessage()
            {
                return getString(Tables.Notifications.Columns.message);
            }

            @Override
            public long getDate()
            {
                return getLong(Tables.Notifications.Columns.date);
            }
        };
    }
}