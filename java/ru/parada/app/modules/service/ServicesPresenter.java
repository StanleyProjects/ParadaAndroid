package ru.parada.app.modules.service;

import ru.parada.app.connection.ParadaService;
import ru.parada.app.connection.Request;
import ru.parada.app.contracts.ServicesContract;

public class ServicesPresenter
    implements ServicesContract.Presenter
{
    private ServicesContract.View view;

    public ServicesPresenter(ServicesContract.View v)
    {
        this.view = v;
    }

    @Override
    public void loadServices()
    {
        new Request(ParadaService.BASE_URL, ParadaService.GET_SERVICES).execute(new Request.RequestListener()
        {
            @Override
            public void answer(String answer)
            {
                view.updateServices();
            }
            @Override
            public void error(Exception error)
            {
                view.updateServices();
            }
        });
    }
}