package ru.parada.app.modules.oneofnewsdetail;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import ru.parada.app.App;
import ru.parada.app.R;
import ru.parada.app.contracts.OneOfNewsDetailContract;
import ru.parada.app.core.NewsCore;
import ru.parada.app.units.MVPFragment;

public class OneOfNewsDetailFragment
        extends MVPFragment<OneOfNewsDetailContract.Presenter, OneOfNewsDetailContract.Behaviour>
        implements OneOfNewsDetailContract.View, OneOfNewsDetailContract.Mark
{
    static public MVPFragment newInstanse(OneOfNewsDetailContract.Behaviour behaviour, int action_id)
    {
        OneOfNewsDetailFragment fragment = new OneOfNewsDetailFragment();
        fragment.setBehaviour(behaviour);
        Bundle bundle = new Bundle();
        bundle.putInt(ONEOFNEWS_ID, action_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private TextView toolbar_title;
    private TextView title;
    private TextView date;
    private TextView full_descr;
    private ImageView image;

    private String[] months;

    @Override
    protected OneOfNewsDetailContract.Presenter setPresenter()
    {
        return new OneOfNewsDetailPresenter(this);
    }

    @Override
    protected int setContentView()
    {
        return R.layout.oneofnews_detail_fragment;
    }

    @Override
    protected void initViews(View v)
    {
        toolbar_title = (TextView)v.findViewById(R.id.toolbar_title);
        title = (TextView)v.findViewById(R.id.title);
        date = (TextView)v.findViewById(R.id.date);
        full_descr = (TextView)v.findViewById(R.id.full_descr);
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
        months = getActivity().getResources().getStringArray(R.array.months);
        getPresenter().update(getArguments().getInt(ONEOFNEWS_ID));
    }

    @Override
    public void update(final NewsCore.Model data)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                toolbar_title.setText(data.getTitle());
                title.setText(data.getTitle());
                full_descr.setText(Html.fromHtml(data.getFullDescription()));
                Date currentDate = new Date(data.getDate());
                date.setText(currentDate.getDate() + " " + months[currentDate.getMonth()] + " " + (currentDate.getYear()+1900));
                if(data.getImagePath() != null && data.getImagePath().length() > 0)
                {
                    image.setImageDrawable(App.getComponent().getImagesUtil().getThumbFromCache(
                            App.getComponent().getFoldersManager().getImagesDirectory() + "/" + data.getImagePath(),
                            App.getComponent().getAndroidUtil().dp(222), App.getComponent().getAndroidUtil().dp(222)));
                }
            }
        });
    }
}