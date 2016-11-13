package ru.parada.app.modules.services.detail;

import ru.parada.app.contracts.ServiceDetailContract;
import ru.parada.app.db.SQliteApi;

public class ServiceDetailPresenter
        implements ServiceDetailContract.Presenter
{
    private ServiceDetailContract.View view;

    public ServiceDetailPresenter(ServiceDetailContract.View v)
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
                view.update(SQliteApi.getInstanse().getServices().getOneFromId(id));
            }
        }).start();
    }
}