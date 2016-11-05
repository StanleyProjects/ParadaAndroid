package ru.parada.app.contracts;

import ru.parada.app.core.NewsCore;
import ru.parada.app.units.ListModel;

public interface NewsContract
{
    interface View
    {
        void update(ListModel<NewsCore.Model> data);
    }

    interface Presenter
    {
        void update();
    }

    interface Behaviour
    {
        void getOneOfNews(int id);
    }
}