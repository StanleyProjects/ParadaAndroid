package ru.parada.app.modules.contacts.list;

import ru.parada.app.contracts.contacts.ContactsContract;

public class ContactsPresenter
    implements ContactsContract.Presenter
{
    private ContactsContract.View view;

    public ContactsPresenter(ContactsContract.View v)
    {
        view = v;
    }
}