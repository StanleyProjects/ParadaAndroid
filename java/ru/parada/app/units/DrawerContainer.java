package ru.parada.app.units;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import ru.parada.app.utils.AndroidUtil;

public class DrawerContainer
        extends FrameLayout
{
    private View drawerLayout;

    private AnimatorSet currentAnimation;
    private int durationAnimation = 150;
    private float drawerPosition;
    private int drawerWidth;

    private boolean drawerOpened;
    private boolean startedTouch;
    private boolean moveProcess;
    private float startedTrackingX;
    private float startedTrackingY;

    private Drawable rightDrawable;

    private Paint scrimPaint = new Paint();

    public DrawerContainer(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        post(new Runnable()
        {
            @Override
            public void run()
            {
                findDrawer();
            }
        });
    }

    private void findDrawer()
    {
        for(int i = 0; i < getChildCount(); i++)
        {
            View v = getChildAt(i);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams)v.getLayoutParams();
            if(lp.gravity == Gravity.START)
            {
                setDrawerLayout(v);
                return;
            }
        }
    }

    public void setDrawerPosition(float value)
    {
        if(drawerLayout == null)
        {
            return;
        }
//        Log.e(this.getClass().getCanonicalName(), "value " + value);
        drawerPosition = value - drawerWidth;
        if(drawerPosition > 0)
        {
            drawerPosition = 0;
        }
        else if(drawerPosition < -drawerWidth)
        {
            drawerPosition = -drawerWidth;
        }
        drawerLayout.setTranslationX(drawerPosition);
        invalidate();
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
        if(drawerLayout == null)
        {
            return;
        }
        cancelCurrentAnimation();
        currentAnimation = new AnimatorSet();
        currentAnimation.play(ObjectAnimator.ofFloat(this, "drawerPosition", drawerPosition + drawerWidth, drawerWidth));
        currentAnimation.setDuration(durationAnimation);
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
        if(drawerLayout == null)
        {
            return;
        }
        cancelCurrentAnimation();
        currentAnimation = new AnimatorSet();
        currentAnimation.play(ObjectAnimator.ofFloat(this, "drawerPosition", drawerPosition + drawerWidth, 0));
        currentAnimation.setDuration(durationAnimation);
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
        if(drawerOpened && ev.getX() > drawerPosition + drawerWidth)
        {
            if(ev.getAction() == MotionEvent.ACTION_UP && !moveProcess)
            {
                onTouchEvent(ev);
            }
            return true;
        }
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
                    double a = Math.sqrt(x*x + y*y);
//                    Log.e(this.getClass()
//                              .getCanonicalName(), "x " + x + " y " + y + " a " + a);
                    if(!drawerOpened && x < 0)
                    {
                        return super.onInterceptTouchEvent(ev);
                    }
                    if(drawerOpened && x > 0)
                    {
                        return super.onInterceptTouchEvent(ev);
                    }
                    if(Math.abs(x) < 2)
                    {
                    }
                    else if(Math.abs(y) < Math.abs(x)-1)
                    {
                        Log.e(this.getClass()
                                  .getCanonicalName(), "move");
                        onTouchEvent(ev);
                    }
                    else
                    {
                        startedTouch = false;
                        moveProcess = false;
                        if(!drawerOpened)
                        {
                            closeDrawer();
                        }
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
        if(drawerOpened && ev.getX() > drawerPosition + drawerWidth && !moveProcess)
        {
            if(ev.getAction() == MotionEvent.ACTION_UP)
            {
                startedTouch = false;
                moveProcess = false;
                closeDrawer();
                return true;
            }
        }
        if(ev.getAction() == MotionEvent.ACTION_UP)
        {
            startedTouch = false;
            if(moveProcess)
            {
                moveProcess = false;
                float x = ev.getX() - startedTrackingX;
                if(drawerOpened)
                {
                    x += drawerWidth;
                }
                if(x > (drawerWidth / 2))
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
//            Log.e(this.getClass()
//                      .getCanonicalName(), "startedTrackingY " + startedTrackingY);
//            cancelCurrentAnimation();
            return true;
        }
        if(ev.getAction() == MotionEvent.ACTION_MOVE && startedTouch)
        {
            moveProcess = true;
            if(drawerOpened)
            {
                setDrawerPosition(drawerWidth + ev.getX() - startedTrackingX);
            }
            else
            {
                setDrawerPosition(ev.getX() - startedTrackingX);
            }
            return true;
        }
        return false;
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime)
    {
        boolean result = super.drawChild(canvas, child, drawingTime);
        float dpos = drawerPosition + drawerWidth;
        if(child != drawerLayout)
        {
            float scrimOpacity = dpos / drawerWidth;
            scrimPaint.setColor((int)(((0x99000000 & 0xff000000) >>> 24) * scrimOpacity) << 24);
            canvas.drawRect(dpos, 0, getWidth(), getHeight(), scrimPaint);
            if(rightDrawable != null)
            {
                float alpha = Math.max(0, Math.min(dpos / AndroidUtil.dp(20), 1.0f));
                if(alpha != 0)
                {
                    rightDrawable.setBounds((int)dpos, child.getTop(), (int)dpos + rightDrawable.getIntrinsicWidth(), child.getBottom());
                    rightDrawable.setAlpha((int)(0xff * alpha));
                    rightDrawable.draw(canvas);
                }
            }
        }
        return result;
    }

    private void setDrawerLayout(View d)
    {
        this.drawerLayout = d;
        this.drawerLayout.post(new Runnable()
        {
            @Override
            public void run()
            {
                ViewGroup.LayoutParams lp = drawerLayout.getLayoutParams();
                drawerWidth = getMeasuredWidth() - AndroidUtil.dp(56);
                lp.width = drawerWidth;
                drawerLayout.setLayoutParams(lp);
                setDrawerPosition(0);
            }
        });
    }

    public void setRightDrawable(Drawable s)
    {
        this.rightDrawable = s;
    }
}