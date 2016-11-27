package ru.parada.app.modules.info.detail;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.parada.app.App;
import ru.parada.app.R;
import ru.parada.app.contracts.info.InfoDetailContract;
import ru.parada.app.core.InfoCore;
import ru.parada.app.units.MVPFragment;

public class InfoDetailFragment
        extends MVPFragment<InfoDetailContract.Presenter, InfoDetailContract.Behaviour>
        implements InfoDetailContract.View, InfoCore.Mark
{
    static public MVPFragment newInstanse(InfoDetailContract.Behaviour behaviour, int info_id)
    {
        InfoDetailFragment fragment = new InfoDetailFragment();
        fragment.setBehaviour(behaviour);
        Bundle bundle = new Bundle();
        bundle.putInt(INFO_ID, info_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private TextView title;
    private TextView descr;
    private ImageView image;

    @Override
    public void update(final InfoCore.Model data)
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
                    image.setImageDrawable(App.getComponent().getImagesUtil().getThumbFromCache(
                            App.getComponent().getFoldersManager().getImagesDirectory() + "/" + data.getImagePath(),
                            App.getComponent().getAndroidUtil().dp(222), App.getComponent().getAndroidUtil().dp(222)));
                }
            }
        });
    }

    @Override
    protected InfoDetailContract.Presenter setPresenter()
    {
        return new InfoDetailPresenter(this);
    }

    @Override
    protected int setContentView()
    {
        return R.layout.info_detail_fragment;
    }

    @Override
    protected void initViews(View v)
    {
        title = (TextView)v.findViewById(R.id.title);
        descr = (TextView)v.findViewById(R.id.descr);
        image = (ImageView)v.findViewById(R.id.image);
        setClickListener(v.findViewById(R.id.back));
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
                }
            }
        };
    }

    @Override
    protected void init()
    {
        getPresenter().update(getArguments().getInt(INFO_ID));
    }
}