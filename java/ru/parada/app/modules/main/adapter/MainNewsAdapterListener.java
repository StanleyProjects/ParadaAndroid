package ru.parada.app.modules.main.adapter;

import ru.parada.app.contracts.MainContract;

public interface MainNewsAdapterListener
    extends MainContract.HeaderBehaviour, MainContract.FooterBehaviour
{
    void oneOfNews(int id);
}