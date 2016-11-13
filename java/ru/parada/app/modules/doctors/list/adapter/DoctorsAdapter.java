package ru.parada.app.modules.doctors.list.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import ru.parada.app.core.DoctorsCore;
import ru.parada.app.managers.FoldersManager;
import ru.parada.app.units.ModelDataAdapter;

public class DoctorsAdapter
    extends ModelDataAdapter<DoctorAdapterHolder, DoctorsCore.Model, DoctorsAdapterListener>
{
    public DoctorsAdapter(Context c, DoctorsAdapterListener l)
    {
        super(c, l);
    }

    @Override
    protected void setData(DoctorAdapterHolder holder, DoctorsCore.Model item)
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
}