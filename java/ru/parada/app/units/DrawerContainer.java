package ru.parada.app.units;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import ru.parada.app.utils.AndroidUtil;

public class DrawerContainer
        extends FrameLayout
{
    private InputMethodManager inputMethodManager;

    private View drawerLayout;

    private AnimatorSet currentAnimation;
    private int durationAnimation = 150;
    private float drawerPosition;
    private int drawerWidth;

    private boolean drawerOpened;
    private boolean startedTouch;
    private boolean moveProcess;
    private boolean findScrollHorizontally;
    private boolean multitouch;
    private float startedTrackingX;
    private float startedTrackingY;

    private Drawable rightDrawable;

    private Paint scrimPaint = new Paint();

    public DrawerContainer(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
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
        if(!moveProcess)
        {
            requestFocus();
            inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
        }
        if(drawerLayout == null)
        {
            return;
        }
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

    public void closeDrawer(final AnimationEndListener listener)
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
                if(listener != null)
                {
                    listener.onAnimationEnd();
                }
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
        if(!findScrollHorizontally && !moveProcess && !isOpen())
        {
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            for(int i=0; i<getChildCount(); i++)
            {
                if(getChildAt(i) == drawerLayout)
                {
                    continue;
                }
                if(getChildAt(i) instanceof ViewGroup)
                {
                    if(findScrollHorizontally((ViewGroup)getChildAt(i), ev.getX(), ev.getY() + rect.top))
                    {
                        findScrollHorizontally = true;
                        break;
                    }
                }
                else
                {
                    if(findView(getChildAt(i), ev.getX(), ev.getY() + rect.top))
                    {
                        findScrollHorizontally = true;
                        break;
                    }
                }
            }
        }
        if(isOpen() && ev.getX() > drawerPosition + drawerWidth)
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
                    if(!isOpen() && x < 0)
                    {
                        return super.onInterceptTouchEvent(ev);
                    }
                    if(isOpen() && x > 0)
                    {
                        return super.onInterceptTouchEvent(ev);
                    }
                    else if(Math.abs(y) <= Math.abs(x))
                    {
                        onTouchEvent(ev);
                    }
                    else
                    {
                        startedTouch = false;
                        moveProcess = false;
                        if(!isOpen())
                        {
                            closeDrawer(null);
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

    private boolean findScrollHorizontally(ViewGroup viewGroup, float x, float y)
    {
        for(int i=0; i<viewGroup.getChildCount(); i++)
        {
            if(viewGroup.getChildAt(i) instanceof ViewGroup)
            {
                if(findScrollHorizontally((ViewGroup)viewGroup.getChildAt(i), x, y))
                {
                    return true;
                }
            }
            else
            {
                if(findView(viewGroup.getChildAt(i), x, y))
                {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean findView(View view, float x, float y)
    {
//        if(!ViewCompat.canScrollHorizontally(view, ViewCompat.LAYOUT_DIRECTION_LTR))
        if(!view.canScrollHorizontally(LAYOUT_DIRECTION_LTR))
        {
            return false;
        }
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        boolean find = rect.left < x && rect.right > x && rect.top < y && rect.bottom > y;
        if(find)
        {
            Log.e(getClass().getName(), "findScrollHorizontally " + view + " xy " +x+ " " +y+ " rect " + rect.left + " " + rect.right + " " + rect.top + " " + rect.bottom);
        }
        return find;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        if(ev.getPointerCount() > 1)
        {
            if(!multitouch)
            {
                //Log.e(getClass().getName(), "onTouchEvent multitouch " + ev);
                multitouch = true;
            }
        }
        if(ev.getAction() == MotionEvent.ACTION_UP)
        {
            multitouch = false;
            findScrollHorizontally = false;
        }
        float factor = 2;
        if(isOpen() && ev.getX() > drawerPosition + drawerWidth && !moveProcess)
        {
            if(ev.getAction() == MotionEvent.ACTION_UP)
            {
                startedTouch = false;
                moveProcess = false;
                closeDrawer(null);
                return true;
            }
        }
        if(ev.getAction() == MotionEvent.ACTION_UP)
        {
            startedTouch = false;
            if(moveProcess)
            {
                moveProcess = false;
                float x = (ev.getX() - startedTrackingX)*factor;
                if(isOpen())
                {
                    x += drawerWidth;
                }
                if(x > (drawerWidth / 2))
                {
                    openDrawer();
                }
                else
                {
                    closeDrawer(null);
                }
            }
            return false;
        }
        if(ev.getAction() == MotionEvent.ACTION_DOWN)
        {
            if(multitouch || findScrollHorizontally)
            {
                startedTouch = false;
                moveProcess = false;
                closeDrawer(null);
                return false;
            }
            startedTouch = true;
            startedTrackingX = ev.getX();
            startedTrackingY = ev.getY();
            return true;
        }
        if(ev.getAction() == MotionEvent.ACTION_MOVE && startedTouch)
        {
            if(multitouch || findScrollHorizontally)
            {
                startedTouch = false;
                moveProcess = false;
                closeDrawer(null);
                return false;
            }
            if(isOpen())
            {
                setDrawerPosition(drawerWidth + (ev.getX() - startedTrackingX)*factor);
            }
            else
            {
                setDrawerPosition((ev.getX() - startedTrackingX)*factor);
            }
            moveProcess = true;
            return true;
        }
        return false;
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime)
    {
        if(drawerLayout == null)
        {
            return super.drawChild(canvas, child, drawingTime);
        }
        if(child == getChildAt(0))
        {
            drawChilds(canvas);
        }
        return true;
    }

    private void drawChilds(Canvas canvas)
    {
        for(int i=0; i<getChildCount(); i++)
        {
            if(getChildAt(i) == drawerLayout)
            {
                continue;
            }
            super.drawChild(canvas, getChildAt(i), 0);
        }
        float dpos = drawerPosition + drawerWidth;
        float scrimOpacity = dpos / drawerWidth;
        scrimPaint.setColor((int)(((0x99000000 & 0xff000000) >>> 24) * scrimOpacity) << 24);
        canvas.drawRect(dpos, 0, getWidth(), getHeight(), scrimPaint);
        super.drawChild(canvas, drawerLayout, 0);
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

    public boolean isOpen()
    {
        return drawerOpened;
    }

    public interface AnimationEndListener
    {
        void onAnimationEnd();
    }
}