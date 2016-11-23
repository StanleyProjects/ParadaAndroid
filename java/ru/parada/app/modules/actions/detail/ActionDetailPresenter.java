package ru.parada.app.modules.actions.detail;

import ru.parada.app.contracts.ActionDetailContract;
import ru.parada.app.db.SQliteApi;

public class ActionDetailPresenter
    implements ActionDetailContract.Presenter
{
    private ActionDetailContract.View view;

    public ActionDetailPresenter(ActionDetailContract.View v)
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
                view.update(SQliteApi.getInstanse().getActions().getOneFromId(id));
            }
        }).start();
    }
}