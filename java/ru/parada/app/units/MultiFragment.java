package ru.parada.app.units;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

public abstract class MultiFragment<PRESENTER, BEHAVIOUR>
        extends MVPFragment<PRESENTER, BEHAVIOUR>
{
    private int screen = beginScreenIndex();
    private final FragmentManager.OnBackStackChangedListener onBackStackChangedListener = new FragmentManager.OnBackStackChangedListener()
    {
        @Override
        public void onBackStackChanged()
        {
            Log.e(getClass().getName(), getChildFragmentManager().getFragments().toString());
            screen = getScreenSize();
        }
    };
    private int getScreenSize()
    {
        int size = 0;
        for(Fragment fragment : getChildFragmentManager().getFragments())
        {
            if(fragment != null)
            {
                size++;
            }
        }
        return size;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        getChildFragmentManager().addOnBackStackChangedListener(onBackStackChangedListener);
        if(getChildFragmentManager().getBackStackEntryCount() == 0 && screen > 0)
        {
            setScreens(screen);
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        getChildFragmentManager().removeOnBackStackChangedListener(onBackStackChangedListener);
    }

    protected int beginScreenIndex()
    {
        return 0;
    }
    protected void resetScreenIndex()
    {
        getChildFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        screen = beginScreenIndex();
    }

    protected abstract void setScreens(int screen);
}