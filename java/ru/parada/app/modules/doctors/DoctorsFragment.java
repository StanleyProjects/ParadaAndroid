package ru.parada.app.modules.doctors;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import ru.parada.app.R;
import ru.parada.app.contracts.DoctorDetailContract;
import ru.parada.app.contracts.DoctorVideoDetailContract;
import ru.parada.app.contracts.DoctorVideosContract;
import ru.parada.app.contracts.DoctorsContract;
import ru.parada.app.core.DoctorsCore;
import ru.parada.app.modules.doctordetail.DoctorDetailFragment;
import ru.parada.app.modules.doctors.adapter.DoctorsAdapter;
import ru.parada.app.modules.doctors.adapter.DoctorsAdapterListener;
import ru.parada.app.modules.doctorvideodetail.DoctorVideoDetailFragment;
import ru.parada.app.modules.doctorvideos.DoctorVideosFragment;
import ru.parada.app.units.ListModel;
import ru.parada.app.units.MVPFragment;

public class DoctorsFragment
        extends MVPFragment<DoctorsContract.Presenter, DoctorsContract.Behaviour>
        implements DoctorsContract.View
{
    static public MVPFragment newInstanse(DoctorsContract.Behaviour behaviour)
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
            showVideosScreen();
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
            showVideoDetailScreen();
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
    public void onResume()
    {
        super.onResume();
        if(getChildFragmentManager().getBackStackEntryCount() == 0)
        {
            if(detailFragment != null)
            {
                showDetailScreen();
            }
            if(videosFragment != null)
            {
                showVideosScreen();
            }
            if(videoDetailFragment != null)
            {
                showVideoDetailScreen();
            }
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
                    case R.id.menu:
                        getBehaviour().openMenu();
                        break;
                    case R.id.search_clear:
                        searchClear();
                        break;
                }
            }
        };
    }

    @Override
    protected void init()
    {
        adapter = new DoctorsAdapter(getActivity(), new DoctorsAdapterListener()
        {
            @Override
            public void getDoctor(int id)
            {
                detailFragment = DoctorDetailFragment.newInstanse(doctorDetailBehaviour, id);
                showDetailScreen();
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
    public void update(final ListModel<DoctorsCore.Model> data)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                adapter.swapData(data);
                adapter.notifyDataSetChanged();
                Log.e(this.getClass()
                          .getName(), "updateDoctors " + data.getItemsCount());
            }
        }, 0);
    }

    private void showDetailScreen()
    {
        showSubscreen(detailFragment);
    }
    private void showVideosScreen()
    {
        showSubscreen(videosFragment);
    }
    private void showVideoDetailScreen()
    {
        showSubscreen(videoDetailFragment);
    }
    private void showSubscreen(Fragment fragment)
    {
        getChildFragmentManager().beginTransaction()
                                 .add(R.id.subscreen, fragment)
                                 .addToBackStack(fragment.getClass().getName())
                                 .commit();
    }
}