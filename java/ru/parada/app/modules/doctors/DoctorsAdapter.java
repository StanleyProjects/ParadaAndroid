package ru.parada.app.modules.doctors;

import android.content.Context;
import android.view.ViewGroup;

import ru.parada.app.contracts.DoctorsContract;
import ru.parada.app.units.ListModel;
import ru.parada.app.units.ModelAdapter;

public class DoctorsAdapter
    extends ModelAdapter<DoctorHolder, DoctorsContract.ListItemModel, DoctorsAdapterListener>
{
    private ListModel<DoctorsContract.ListItemModel> data;

    public DoctorsAdapter(Context c, DoctorsAdapterListener l)
    {
        super(c, l);
    }

    @Override
    protected DoctorsContract.ListItemModel getItem(int position)
    {
        return data.getItem(position);
    }

    @Override
    protected void setData(DoctorHolder holder, DoctorsContract.ListItemModel item)
    {
        holder.setLastName(item.getLastName());
        holder.setFirstMiddleName(item.getFirstName(), item.getMiddleName());
        holder.setFirstPosition(item.getFirstPosition());
        holder.setSecondPosition(item.getSecondPosition());
        holder.setThirdPosition(item.getThirdPosition());
    }

    @Override
    public DoctorHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new DoctorHolder(getContext(), parent);
    }

    @Override
    public int getItemCount()
    {
        if(data == null)
        {
            return 0;
        }
        return data.getItemsCount();
    }

    public void swapData(ListModel<DoctorsContract.ListItemModel> d)
    {
        if(this.data != null)
        {
            this.data.clear();
        }
        this.data = d;
    }
}