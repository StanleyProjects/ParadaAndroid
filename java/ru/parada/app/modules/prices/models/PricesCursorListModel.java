package ru.parada.app.modules.prices.models;

import android.database.Cursor;
import android.provider.BaseColumns;

import ru.parada.app.contracts.PricesContract;
import ru.parada.app.db.Tables;
import ru.parada.app.units.CursorListModel;

public class PricesCursorListModel
        extends CursorListModel<PricesContract.Model>
{
    public PricesCursorListModel(Cursor d)
    {
        super(d);
    }

    @Override
    protected PricesContract.Model getModel(int i)
    {
        return new PricesContract.Model()
        {
            @Override
            public int getId()
            {
                return getInt(BaseColumns._ID);
            }

            @Override
            public int getServiceId()
            {
                return getInt(Tables.Prices.Columns.service_id);
            }

            @Override
            public String getTitle()
            {
                return getString(Tables.Prices.Columns.title);
            }

            @Override
            public String getValue()
            {
                return getString(Tables.Prices.Columns.value);
            }
        };
    }
}