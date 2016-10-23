package ru.parada.app.modules.contacts;

import ru.parada.app.contracts.ContactsContract;

public class ContactsPresenter
    implements ContactsContract.Presenter
{
    private ContactsContract.View view;

    public ContactsPresenter(ContactsContract.View v)
    {
        view = v;
    }
}