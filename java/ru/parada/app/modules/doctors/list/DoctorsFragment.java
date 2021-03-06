package ru.parada.app.modules.doctors.list;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ru.parada.app.App;
import ru.parada.app.R;
import ru.parada.app.contracts.doctors.DoctorDetailContract;
import ru.parada.app.contracts.doctors.DoctorVideoDetailContract;
import ru.parada.app.contracts.doctors.DoctorVideosContract;
import ru.parada.app.contracts.doctors.DoctorsContract;
import ru.parada.app.core.DoctorsCore;
import ru.parada.app.modules.doctors.detail.DoctorDetailFragment;
import ru.parada.app.modules.doctors.list.adapter.DoctorsAdapter;
import ru.parada.app.modules.doctors.list.adapter.DoctorsAdapterListener;
import ru.parada.app.modules.doctors.videos.detail.DoctorVideoDetailFragment;
import ru.parada.app.modules.doctors.videos.list.DoctorVideosFragment;
import ru.parada.app.units.ListModel;
import ru.parada.app.units.MultiFragment;

public class DoctorsFragment
        extends MultiFragment<DoctorsContract.Presenter, DoctorsContract.Behaviour>
        implements DoctorsContract.View
{
    static public Fragment newInstanse(DoctorsContract.Behaviour behaviour)
    {
        DoctorsFragment fragment = new DoctorsFragment();
        fragment.setBehaviour(behaviour);
        return fragment;
    }

    private Fragment detailFragment;
    private Fragment videosFragment;
    private Fragment videoDetailFragment;

    private RecyclerView list;
    private EditText search;
    private TextView list_empty;

    private boolean load;
    private DoctorsAdapter adapter;
    private final DoctorDetailContract.Behaviour doctorDetailBehaviour = new DoctorDetailContract.Behaviour()
    {
        @Override
        public void back()
        {
            getChildFragmentManager().popBackStack();
            detailFragment = null;
        }
        @Override
        public void showVideos(int id)
        {
            videosFragment = DoctorVideosFragment.newInstanse(doctorVideosBehaviour, id);
            addSubscreen(videosFragment);
        }
    };
    private final DoctorVideosContract.Behaviour doctorVideosBehaviour = new DoctorVideosContract.Behaviour()
    {
        @Override
        public void back()
        {
            getChildFragmentManager().popBackStack();
            videosFragment = null;
        }
        @Override
        public void getVideo(int id)
        {
            videoDetailFragment = DoctorVideoDetailFragment.newInstanse(doctorVideoDetailBehaviour, id);
            addSubscreen(videoDetailFragment);
        }
    };
    private final DoctorVideoDetailContract.Behaviour doctorVideoDetailBehaviour = new DoctorVideoDetailContract.Behaviour()
    {
        @Override
        public void back()
        {
            getChildFragmentManager().popBackStack();
            videoDetailFragment = null;
        }
    };

    @Override
    protected void setScreens(int screen)
    {
        if(screen >= DoctorsCore.Screens.DETAIL)
        {
            addSubscreen(detailFragment);
        }
        if(screen >= DoctorsCore.Screens.VIDEOS)
        {
            addSubscreen(videosFragment);
        }
        if(screen >= DoctorsCore.Screens.VIDEO_DETAIL)
        {
            addSubscreen(videoDetailFragment);
        }
    }

    @Override
    protected DoctorsContract.Presenter setPresenter()
    {
        return new DoctorsPresenter(this);
    }

    @Override
    protected int setContentView()
    {
        return R.layout.doctors_screen;
    }

    @Override
    protected void initViews(View v)
    {
        setClickListener(v.findViewById(R.id.menu), v.findViewById(R.id.search_clear));
        list = (RecyclerView)v.findViewById(R.id.list);
        search = (EditText)v.findViewById(R.id.search);
        list_empty = (TextView)v.findViewById(R.id.list_empty);
    }

    @Override
    protected void onClickView(int id)
    {
        switch(id)
        {
            case R.id.menu:
                getBehaviour().openMenu();
                break;
            case R.id.search_clear:
                searchClear();
                break;
        }
    }

    @Override
    protected void init()
    {
        list.setVisibility(View.GONE);
        list_empty.setVisibility(View.VISIBLE);
        adapter = new DoctorsAdapter(getActivity(), new DoctorsAdapterListener()
        {
            @Override
            public void getDoctor(final int id)
            {
                if(load || App.getComponent().getAndroidUtil().blockClick())
                {
                    return;
                }
                runAfterResume(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        resetScreenIndex();
                        detailFragment = DoctorDetailFragment.newInstanse(doctorDetailBehaviour, id);
                        addSubscreen(detailFragment);
                    }
                });
            }
        });
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        search.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                String keys = s.toString();
                if(keys.length() > 0)
                {
                    getPresenter().search(keys);
                }
                else
                {
                    getPresenter().update();
                }
            }
            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        getPresenter().update();
        load = true;
        getPresenter().load();
    }

    private void searchClear()
    {
        if(search.getText()
                 .toString()
                 .length() > 0)
        {
            search.setText("");
            getPresenter().update();
        }
    }

    @Override
    public void load()
    {
        load = false;
    }

    @Override
    public void update(final ListModel<DoctorsCore.Model> data)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                if(data.getItemsCount() > 0)
                {
                    list.setVisibility(View.VISIBLE);
                    list_empty.setVisibility(View.GONE);
                    adapter.swapData(data);
                }
                else
                {
                    list.setVisibility(View.GONE);
                    list_empty.setVisibility(View.VISIBLE);
                    adapter.swapData(null);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void addSubscreen(Fragment fragment)
    {
        getChildFragmentManager().beginTransaction()
                                 .add(R.id.subscreen, fragment)
                                 .addToBackStack(fragment.getClass().getName())
                                 .commit();
    }
}