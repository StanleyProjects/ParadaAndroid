package ru.parada.app.modules.servicedetail;

import ru.parada.app.contracts.ServiceDetailContract;
import ru.parada.app.core.ServicesCore;
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
                updateService(SQliteApi.getInstanse().getServices().getOneFromId(id));
            }
        }).start();
    }

    private void updateService(ServicesCore.Model model)
    {
        view.update(model);
    }
}