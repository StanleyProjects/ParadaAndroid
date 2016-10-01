package ru.parada.app.modules.service;

import ru.parada.app.connection.ParadaService;
import ru.parada.app.connection.Request;
import ru.parada.app.contracts.ServiceContract;

public class ServicePresenter
    implements ServiceContract.Presenter
{
    private ServiceContract.View view;

    public ServicePresenter(ServiceContract.View v)
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