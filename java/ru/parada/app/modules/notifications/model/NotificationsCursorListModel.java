package ru.parada.app.modules.notifications.model;

import android.database.Cursor;
import android.provider.BaseColumns;

import ru.parada.app.core.NotificationsCore;
import ru.parada.app.db.Tables;
import ru.parada.app.units.CursorListModel;

public class NotificationsCursorListModel
        extends CursorListModel<NotificationsCore.Model>
{
    private final NotificationsCore.Model model = new NotificationsCore.Model()
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

        @Override
        public boolean read()
        {
            return getInt(Tables.Notifications.Columns.read) == 1;
        }
    };

    public NotificationsCursorListModel(Cursor d)
    {
        super(d);
    }

    @Override
    protected NotificationsCore.Model getModel(int i)
    {
        return model;
    }
}