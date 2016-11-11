package ru.parada.app.modules.doctorvideos.adapter;

import android.content.Context;
import android.view.ViewGroup;

import ru.parada.app.core.DoctorVideosCore;
import ru.parada.app.managers.FoldersManager;
import ru.parada.app.units.ModelDataAdapter;

public class DoctorVideosAdapter
        extends ModelDataAdapter<DoctorVideosAdapterHolder, DoctorVideosCore.Model, DoctorVideosAdapterListener>
{
    public DoctorVideosAdapter(Context c, DoctorVideosAdapterListener l)
    {
        super(c, l);
    }

    @Override
    protected void setData(DoctorVideosAdapterHolder holder, DoctorVideosCore.Model item)
    {
        holder.setTitle(item.getTitle());
        holder.setImage(FoldersManager.getImagesDirectory() + "/" + item.getImagePath());
    }

    @Override
    public DoctorVideosAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new DoctorVideosAdapterHolder(getContext(), parent);
    }
}