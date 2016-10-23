package ru.parada.app.modules.socials;

import ru.parada.app.contracts.SocialsContract;

public class SocialsPresenter
    implements SocialsContract.Presenter
{
    private SocialsContract.View view;

    public SocialsPresenter(SocialsContract.View v)
    {
        view = v;
    }
}