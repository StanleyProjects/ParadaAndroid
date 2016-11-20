package ru.parada.app.modules.call;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.parada.app.R;

public class CallDialog
        extends DialogFragment
{
    static public CallDialog newInstance(CallDialogListener l)
    {
        CallDialog fragment = new CallDialog();
        Bundle bundle = new Bundle();
//        bundle.putInt(TX_KEY, tx);
        fragment.setArguments(bundle);
        fragment.listener = l;
        return fragment;
    }

    private CallDialogListener listener;
    private final View.OnClickListener clickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch(v.getId())
            {
                case R.id.phone:
                    dismiss();
                    listener.phone();
                    break;
                case R.id.sms:
                    dismiss();
                    listener.sms();
                    break;
                case R.id.whatsapp:
                    dismiss();
                    listener.whatsapp();
                    break;
                case R.id.viber:
                    dismiss();
                    listener.viber();
                    break;
            }
        }
    };
    ;

    //____________________VIEWS

    @Override
    public void onDismiss(final DialogInterface dialog)
    {
        super.onDismiss(dialog);
        listener.close();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        return new Dialog(getActivity(), R.style.Dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.call_dialog, null);
        initViews(v);
        init();
        return v;
    }

    private void initViews(View v)
    {
        setClickListener(v.findViewById(R.id.phone), v.findViewById(R.id.sms), v.findViewById(R.id.whatsapp), v.findViewById(R.id.viber));
    }

    private void init()
    {

    }

    protected void setClickListener(View... views)
    {
        for(View v : views)
        {
            if(v != null)
            {
                v.setOnClickListener(clickListener);
            }
        }
    }
}