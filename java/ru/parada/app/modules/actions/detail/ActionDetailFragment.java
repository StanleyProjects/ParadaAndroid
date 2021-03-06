package ru.parada.app.modules.actions.detail;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import ru.parada.app.App;
import ru.parada.app.R;
import ru.parada.app.contracts.actions.ActionDetailContract;
import ru.parada.app.core.ActionsCore;
import ru.parada.app.units.MVPFragment;

public class ActionDetailFragment
        extends MVPFragment<ActionDetailContract.Presenter, ActionDetailContract.Behaviour>
        implements ActionDetailContract.View, ActionsCore.Mark
{
    static public MVPFragment newInstanse(ActionDetailContract.Behaviour behaviour, int action_id)
    {
        ActionDetailFragment fragment = new ActionDetailFragment();
        fragment.setBehaviour(behaviour);
        Bundle bundle = new Bundle();
        bundle.putInt(ACTION_ID, action_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private TextView title;
    private TextView date;
    private TextView subtitle;
    private ImageView image;
    private TextView descr;

    @Override
    protected ActionDetailContract.Presenter setPresenter()
    {
        return new ActionDetailPresenter(this);
    }

    @Override
    protected int setContentView()
    {
        return R.layout.action_detail_fragment;
    }

    @Override
    protected void initViews(View v)
    {
        title = (TextView)v.findViewById(R.id.title);
        date = (TextView)v.findViewById(R.id.date);
        subtitle = (TextView)v.findViewById(R.id.subtitle);
        image = (ImageView)v.findViewById(R.id.image);
        descr = (TextView)v.findViewById(R.id.descr);
        setClickListener(v.findViewById(R.id.back), v.findViewById(R.id.subscribe_reception), v.findViewById(R.id.order_call));
    }

    @Override
    protected void onClickView(int id)
    {
        switch(id)
        {
            case R.id.back:
                getBehaviour().back();
                break;
            case R.id.subscribe_reception:
                getBehaviour().subscribe();
                break;
            case R.id.order_call:
                getBehaviour().requestcall();
                break;
        }
    }

    @Override
    protected void init()
    {
        getPresenter().update(getArguments().getInt(ACTION_ID));
    }

    @Override
    protected Animation getEnterAnimation()
    {
        return AnimationUtils.loadAnimation(getActivity(), R.anim.rtl);
    }

    @Override
    public void update(final ActionsCore.Model data)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                title.setText(data.getTitle());
                date.setText(getStringDate(data.getFromDate()*1000, data.getToDate()*1000));
                if(data.getSubtitle().length() > 0)
                {
                    subtitle.setText(data.getSubtitle());
                    subtitle.setVisibility(View.VISIBLE);
                }
                else
                {
                    subtitle.setVisibility(View.GONE);
                }
                if(data.getImagePath() != null && data.getImagePath().length() > 0)
                {
                    image.setImageDrawable(App.getComponent().getImagesUtil().getThumbFromCache(
                            App.getComponent().getFoldersManager().getImagesDirectory() + "/" + data.getImagePath(),
                            App.getComponent().getAndroidUtil().dp(128), App.getComponent().getAndroidUtil().dp(128)));
                }
                descr.setText(Html.fromHtml(data.getDescription()));
            }
        });
    }
    public String getStringDate(long from_date, long to_date)
    {
        Date from = new Date(from_date);
        Date to = new Date(to_date);
        int m;
        int d;
        String dt = "";
        d = from.getDate();
        if(d<10)
        {
            dt +="0";
        }
        dt += d + ".";
        m = from.getMonth()+1;
        if(m<10)
        {
            dt +="0";
        }
        dt += "" + m;
        dt += " - ";
        d = to.getDate();
        if(d<10)
        {
            dt +="0";
        }
        dt += d + ".";
        m = to.getMonth()+1;
        if(m<10)
        {
            dt +="0";
        }
        dt += "" + m;
        return dt;
    }
}