package ru.parada.app.modules.actions.model;

import android.database.Cursor;
import android.provider.BaseColumns;

import ru.parada.app.core.ActionsCore;
import ru.parada.app.db.Tables;
import ru.parada.app.units.CursorListModel;

public class ActionsCursorListModel
        extends CursorListModel<ActionsCore.ActionModel>
{
    public ActionsCursorListModel(Cursor d)
    {
        super(d);
    }

    @Override
    protected ActionsCore.ActionModel getModel(int i)
    {
        return new ActionsCore.ActionModel()
        {
            @Override
            public int getId()
            {
                return getInt(BaseColumns._ID);
            }

            @Override
            public String getDescription()
            {
                return getString(Tables.Actions.Columns.descr);
            }

            @Override
            public String getTitle()
            {
                return getString(Tables.Actions.Columns.title);
            }

            @Override
            public String getSubtitle()
            {
                return getString(Tables.Actions.Columns.subtitle);
            }

            @Override
            public String getPhotoPath()
            {
                int photoPathColumnIndex = getColumnIndex(Tables.Images.Columns.image_path);
                if(photoPathColumnIndex < 0)
                {
                    return null;
                }
                return getString(Tables.Images.Columns.image_path);
            }

            @Override
            public long getFromDate()
            {
                return getLong(Tables.Actions.Columns.from_date);
            }

            @Override
            public long getToDate()
            {
                return getLong(Tables.Actions.Columns.to_date);
            }
        };
    }
}