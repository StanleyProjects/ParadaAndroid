package ru.parada.app.modules.services.detail;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.parada.app.App;
import ru.parada.app.R;
import ru.parada.app.contracts.ServiceDetailContract;
import ru.parada.app.core.ServicesCore;
import ru.parada.app.units.MVPFragment;
import ru.parada.app.utils.ImagesUtils;

public class ServiceDetailFragment
        extends MVPFragment<ServiceDetailContract.Presenter, ServiceDetailContract.Behaviour>
        implements ServiceDetailContract.View, ServiceDetailContract.Mark
{
    static public MVPFragment newInstanse(ServiceDetailContract.Behaviour behaviour, int service_id)
    {
        ServiceDetailFragment fragment = new ServiceDetailFragment();
        fragment.setBehaviour(behaviour);
        Bundle bundle = new Bundle();
        bundle.putInt(SERVICE_ID, service_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private TextView title;
    private TextView descr;
    private ImageView image;

    @Override
    protected ServiceDetailContract.Presenter setPresenter()
    {
        return new ServiceDetailPresenter(this);
    }

    @Override
    protected int setContentView()
    {
        return R.layout.service_detail_fragment;
    }

    @Override
    protected void initViews(View v)
    {
        title = (TextView)v.findViewById(R.id.title);
        descr = (TextView)v.findViewById(R.id.descr);
        image = (ImageView)v.findViewById(R.id.image);
        setClickListener(v.findViewById(R.id.back), v.findViewById(R.id.subscribe_reception), v.findViewById(R.id.order_call));
    }

    @Override
    protected View.OnClickListener setClickListener()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switch(v.getId())
                {
                    case R.id.back:
                        getBehaviour().back();
                        break;
                    case R.id.subscribe_reception:
                        getBehaviour().subscribe();
                        break;
                    case R.id.order_call:
                        getBehaviour().call();
                        break;
                }
            }
        };
    }

    @Override
    protected void init()
    {
        getPresenter().update(getArguments().getInt(SERVICE_ID));
    }

    @Override
    public void update(final ServicesCore.Model data)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                title.setText(data.getTitle());
                descr.setText(Html.fromHtml(data.getDescription()));
                if(data.getImagePath() != null && data.getImagePath().length() > 0)
                {
                    ImagesUtils.setThumbImage(App.getComponent().getFoldersManager().getImagesDirectory() + "/" + data.getImagePath(), image, App.getComponent().getAndroidUtil().dp(222), App.getComponent().getAndroidUtil().dp(222));
                }
            }
        });
    }
}