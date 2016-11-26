package ru.parada.app.modules.info.model;

import android.database.Cursor;

import ru.parada.app.core.InfoCore;
import ru.parada.app.db.Tables;
import ru.parada.app.units.CursorListModel;

public class InfoCursorListModel
        extends CursorListModel<InfoCore.Model>
{
    private final InfoCore.Model model = new InfoCore.Model()
    {
        @Override
        public int getId()
        {
            return getInt(Tables.Info.Columns.id);
        }

        @Override
        public String getDescription()
        {
            return getString(Tables.Info.Columns.descr);
        }

        @Override
        public String getTitle()
        {
            return getString(Tables.Info.Columns.title);
        }

        @Override
        public String getImagePath()
        {
            int photoPathColumnIndex = getColumnIndex(Tables.Images.Columns.image_path);
            if(photoPathColumnIndex < 0)
            {
                return null;
            }
            return getString(Tables.Images.Columns.image_path);
        }
    };

    public InfoCursorListModel(Cursor d)
    {
        super(d);
    }

    @Override
    protected InfoCore.Model getModel(int i)
    {
        return model;
    }
}