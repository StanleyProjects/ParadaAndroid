package ru.parada.app.units;

public interface ListModel<ITEMMODEL>
{
    ITEMMODEL getItem(int i);
    int getItemsCount();
    void clear();
}