package ru.parada.app.units;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class SquareFrameLayout
        extends FrameLayout
{
    public SquareFrameLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}