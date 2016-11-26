package ru.parada.app.contracts.news;

import ru.parada.app.core.NewsCore;

public interface OneOfNewsDetailContract
{
    interface Mark
    {
        String ONEOFNEWS_ID = Mark.class.getCanonicalName().toLowerCase().replace(".", "_") + "_" + "oneofnews_id";
    }

    interface View
    {
        void update(NewsCore.Model data);
    }

    interface Presenter
    {
        void update(int id);
    }

    interface Behaviour
    {
        void back();
    }
}