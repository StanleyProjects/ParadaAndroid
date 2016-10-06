package ru.parada.app.units;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class DrawerContainer
        extends FrameLayout
{
    private View drawerLayout;

    private AnimatorSet currentAnimation;
    private float drawerPosition;

    private boolean drawerOpened;
    private boolean startedTouch;
    private boolean moveProcess;
    private float startedTrackingX;
    private float startedTrackingY;

    private Drawable shadow;

    public DrawerContainer(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void setDrawerPosition(float value)
    {
        Log.e(this.getClass().getCanonicalName(), "value " + value);
        drawerPosition = value - getMeasuredWidth();
        if(drawerPosition > 0)
        {
            drawerPosition = 0;
        }
        else if(drawerPosition < -getMeasuredWidth())
        {
            drawerPosition = -getMeasuredWidth();
        }
        drawerLayout.setTranslationX(drawerPosition);
    }

    public void cancelCurrentAnimation()
    {
        if(currentAnimation != null)
        {
            currentAnimation.cancel();
            currentAnimation = null;
        }
    }

    public void openDrawer()
    {
        cancelCurrentAnimation();
        getDrawerLayout().setVisibility(View.VISIBLE);
        currentAnimation = new AnimatorSet();
        currentAnimation.play(ObjectAnimator.ofFloat(this, "drawerPosition", drawerPosition + getMeasuredWidth(), getMeasuredWidth()));
        currentAnimation.setDuration(200);
        currentAnimation.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animator)
            {

            }

            @Override
            public void onAnimationEnd(Animator animator)
            {
                drawerOpened = true;
            }

            @Override
            public void onAnimationCancel(Animator animator)
            {

            }

            @Override
            public void onAnimationRepeat(Animator animator)
            {

            }
        });
        currentAnimation.start();
    }

    public void closeDrawer()
    {
        cancelCurrentAnimation();
        getDrawerLayout().setVisibility(View.VISIBLE);
        currentAnimation = new AnimatorSet();
        currentAnimation.play(ObjectAnimator.ofFloat(this, "drawerPosition", drawerPosition + getMeasuredWidth(), 0));
        currentAnimation.setDuration(200);
        currentAnimation.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animator)
            {

            }

            @Override
            public void onAnimationEnd(Animator animator)
            {
                drawerOpened = false;
            }

            @Override
            public void onAnimationCancel(Animator animator)
            {

            }

            @Override
            public void onAnimationRepeat(Animator animator)
            {

            }
        });
        currentAnimation.start();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        switch(ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                onTouchEvent(ev);
                return super.onInterceptTouchEvent(ev);
            }
            case MotionEvent.ACTION_MOVE:
            {
                boolean intercept = super.onInterceptTouchEvent(ev);
                if(intercept)
                {
                    return true;
                }
                else if(startedTouch && !moveProcess)
                {
                    float x = ev.getX() - startedTrackingX;
                    float y = ev.getY() - startedTrackingY;
                    Log.e(this.getClass().getCanonicalName(), "x " + x + " y " + y);
                    if(x < 2 && x > -2)
                    {
                    }
                    else if(y < 5 && y > -5)
                    {
                        Log.e(this.getClass().getCanonicalName(), "move");
                        onTouchEvent(ev);
                    }
                    else
                    {
                        startedTouch = false;
                        moveProcess = false;
                        closeDrawer();
                    }
                }
                else if(moveProcess)
                {
                    onTouchEvent(ev);
                    return true;
                }
                return false;
            }
            case MotionEvent.ACTION_CANCEL:
            {
                return super.onInterceptTouchEvent(ev);
            }
            case MotionEvent.ACTION_UP:
            {
                onTouchEvent(ev);
                return super.onInterceptTouchEvent(ev);
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        if(ev.getAction() == MotionEvent.ACTION_UP)
        {
            startedTouch = false;
            if(moveProcess)
            {
                moveProcess = false;
                if(ev.getX() - startedTrackingX > (getMeasuredWidth() / 2))
                {
                    openDrawer();
                }
                else
                {
                    closeDrawer();
                }
            }
            return false;
        }
        if(ev.getAction() == MotionEvent.ACTION_DOWN)
        {
            startedTouch = true;
            startedTrackingX = ev.getX();
            startedTrackingY = ev.getY();
            Log.e(this.getClass().getCanonicalName(), "startedTrackingY " + startedTrackingY);
            cancelCurrentAnimation();
            return true;
        }
        if(ev.getAction() == MotionEvent.ACTION_MOVE && startedTouch)
        {
            moveProcess = true;
            if(!drawerOpened)
            {
                setDrawerPosition(ev.getX() - startedTrackingX);
            }
            else
            {
                setDrawerPosition(getMeasuredWidth() + ev.getX() - startedTrackingX);
            }
            return true;
        }
        return false;
    }

    public View getDrawerLayout()
    {
        return drawerLayout;
    }

    public void setDrawerLayout(View d)
    {
        this.drawerLayout = d;
    }
}