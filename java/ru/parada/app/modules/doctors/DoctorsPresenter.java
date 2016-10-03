package ru.parada.app.modules.doctors;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import ru.parada.app.connection.ParadaService;
import ru.parada.app.connection.Request;
import ru.parada.app.contracts.DoctorsContract;
import ru.parada.app.db.SQliteApi;
import ru.parada.app.json.JSONParser;

public class DoctorsPresenter
    implements DoctorsContract.Presenter
{
    private DoctorsContract.View view;

    public DoctorsPresenter(DoctorsContract.View v)
    {
        this.view = v;
    }

    @Override
    public void loadDoctors()
    {
//        updateDoctors();
        view.updateDoctors(SQliteApi.getInstanse().getDoctors().getAll());
        new Request(ParadaService.BASE_URL, ParadaService.GET_DOCTORS).execute(new Request.RequestListener()
        {
            @Override
            public void answer(String answer)
            {
                ArrayList doctors;
                try
                {
                    doctors = (ArrayList)JSONParser.newParser().parse(answer);
                }
                catch(Exception e)
                {
                    Log.e(this.getClass().getCanonicalName(), "parse json: " + answer);
                    return;
                }
                Log.e(this.getClass().getCanonicalName(), "load news: " + answer);
                SQliteApi.getInstanse().getDoctors().clearTable();
                SQliteApi.getInstanse().startTransaction();
                for(Object doctor : doctors)
                {
                    final int id = Integer.parseInt((String)((HashMap)doctor).get("id"));
                    final String last_name = (String)((HashMap)doctor).get("last_name");
                    SQliteApi.getInstanse().getDoctors().insertOne(new DoctorsContract.ListItemModel()
                    {
                        @Override
                        public int getId()
                        {
                            return id;
                        }
                        @Override
                        public String getLastName()
                        {
                            return last_name;
                        }
                    });
                }
                SQliteApi.getInstanse().endTransaction();
                updateDoctors();
            }
            @Override
            public void error(Exception error)
            {
            }
        });
    }

    private void updateDoctors()
    {
        new Handler(Looper.getMainLooper()).post(new Runnable()
        {
            @Override
            public void run()
            {
                view.updateDoctors(SQliteApi.getInstanse().getDoctors().getAll());
            }
        });
    }
}