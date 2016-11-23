package ru.parada.app.modules.doctors.videos.list.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import ru.parada.app.App;
import ru.parada.app.core.DoctorVideosCore;
import ru.parada.app.units.adapters.ModelDataAdapter;

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
        holder.setImage(App.getComponent().getFoldersManager().getImagesDirectory() + "/" + item.getImagePath());
        final int id = item.getId();
        holder.setPlay(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getListener().getVideo(id);
            }
        });
    }

    @Override
    public DoctorVideosAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new DoctorVideosAdapterHolder(getContext(), parent);
    }
}