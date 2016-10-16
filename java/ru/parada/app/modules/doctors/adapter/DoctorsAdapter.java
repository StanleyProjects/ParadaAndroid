package ru.parada.app.modules.doctors.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import ru.parada.app.contracts.DoctorDetailContract;
import ru.parada.app.managers.FoldersManager;
import ru.parada.app.units.ListModel;
import ru.parada.app.units.ModelAdapter;

public class DoctorsAdapter
    extends ModelAdapter<DoctorAdapterHolder, DoctorDetailContract.Model, DoctorsAdapterListener>
{
    private ListModel<DoctorDetailContract.Model> data;

    public DoctorsAdapter(Context c, DoctorsAdapterListener l)
    {
        super(c, l);
    }

    @Override
    protected DoctorDetailContract.Model getItem(int position)
    {
        return data.getItem(position);
    }

    @Override
    protected void setData(DoctorAdapterHolder holder, DoctorDetailContract.Model item)
    {
        if(item.getPhotoPath() != null)
        {
            holder.getHolder().setPhoto(FoldersManager.getImagesDirectory() + "/" + item.getPhotoPath());
        }
        else
        {
            holder.getHolder().setPhotoPlaceHolder();
        }
        holder.getHolder().setLastName(item.getLastName());
        holder.getHolder().setFirstMiddleName(item.getFirstName(), item.getMiddleName());
        holder.getHolder().setFirstPosition(item.getFirstPosition());
        holder.getHolder().setSecondPosition(item.getSecondPosition());
        holder.getHolder().setThirdPosition(item.getThirdPosition());
        final int id = item.getId();
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getListener().getDoctor(id);
            }
        });
    }

    @Override
    public DoctorAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new DoctorAdapterHolder(getContext(), parent);
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

    public void swapData(ListModel<DoctorDetailContract.Model> d)
    {
        if(this.data != null)
        {
            this.data.clear();
        }
        this.data = d;
    }
}