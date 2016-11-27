package ru.parada.app.modules.info.detail;

import ru.parada.app.contracts.info.InfoDetailContract;
import ru.parada.app.db.SQliteApi;

public class InfoDetailPresenter
        implements InfoDetailContract.Presenter
{
    private InfoDetailContract.View view;

    public InfoDetailPresenter(InfoDetailContract.View v)
    {
        view = v;
    }

    @Override
    public void update(final int id)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                view.update(SQliteApi.getInstanse().getInfo().getOneFromId(id));
            }
        }).start();
    }
}