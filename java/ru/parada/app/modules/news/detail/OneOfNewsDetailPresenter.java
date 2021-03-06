package ru.parada.app.modules.news.detail;

import ru.parada.app.contracts.news.OneOfNewsDetailContract;
import ru.parada.app.db.SQliteApi;

public class OneOfNewsDetailPresenter
    implements OneOfNewsDetailContract.Presenter
{
    private OneOfNewsDetailContract.View view;

    public OneOfNewsDetailPresenter(OneOfNewsDetailContract.View v)
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
                view.update(SQliteApi.getInstanse().getNews().getOneFromId(id));
            }
        }).start();
    }
}