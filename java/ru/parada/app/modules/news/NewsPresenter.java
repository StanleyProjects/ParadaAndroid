package ru.parada.app.modules.news;

import ru.parada.app.contracts.news.NewsContract;
import ru.parada.app.db.SQliteApi;

public class NewsPresenter
    implements NewsContract.Presenter
{
    private NewsContract.View view;

    public NewsPresenter(NewsContract.View v)
    {
        view = v;
    }

    @Override
    public void update()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                view.update(SQliteApi.getInstanse().getNews().getAll());
            }
        }).start();
    }
}