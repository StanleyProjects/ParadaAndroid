package ru.parada.app.units;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

public class LostFocusEdit
        extends EditText
{
    public LostFocusEdit(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public LostFocusEdit(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public LostFocusEdit(Context context)
    {
        super(context);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            clearFocus();
        }
        return super.onKeyPreIme(keyCode, event);
    }
}