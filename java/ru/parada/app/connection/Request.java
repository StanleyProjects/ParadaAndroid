package ru.parada.app.connection;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class Request
{
    private String baseUrl;
    private String method;
    private ArrayList<Field> fields;

    public Request(String u, String m)
    {
        this.baseUrl = u;
        this.method = m;
        this.fields = new ArrayList<>();
    }

    public Request addField(String key, Object value)
    {
        fields.add(new Field(key, value));
        return this;
    }

    private String generateUrl()
    {
        String url = baseUrl;
        url += method;
        if(fields.size() > 0)
        {
            url += "?" + fields.get(0).key + "=" + fields.get(0).value;
            for(int i=1; i<fields.size(); i++)
            {
                url += fields.get(0).key + "=" + fields.get(0).value;
            }
        }
        return url;
    }
    public <RESPONSE> void execute(final RequestListener<RESPONSE> listener)
    {
        final String url = generateUrl();
        new Thread(new Runnable()
        {
            public void run()
            {
                RESPONSE response;
                try
                {
                    response = listener.answer(Connection.getDataFromUrlConnection(new URL(url).openConnection()).toString());
                }
                catch(Exception e)
                {
                    listener.error(url, e);
                    return;
                }
                if(response == null)
                {
                    listener.error(url, new Exception("response null"));
                    return;
                }
                listener.response(response);
            }
        }).start();
    }
    public <RESPONSE> void execute(final Map<String, String> data, final RequestListener<RESPONSE> listener)
    {
        final String url = generateUrl();
        new Thread(new Runnable()
        {
            public void run()
            {
                RESPONSE response;
                try
                {
                    response = listener.answer(Connection.getDataFromUrlConnection(new PostURLConnection(url).buildURLConnection(data)).toString());
                }
                catch(Exception e)
                {
                    listener.error(url, e);
                    return;
                }
                if(response == null)
                {
                    listener.error(url, new Exception("response null"));
                    return;
                }
                listener.response(response);
            }
        }).start();
    }

    private class Field
    {
        private String key;
        private Object value;

        private Field(String k, Object v)
        {
            this.key = k;
            this.value = v;
        }
    }
}