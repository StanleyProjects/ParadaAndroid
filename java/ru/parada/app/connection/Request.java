package ru.parada.app.connection;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Map;

public class Request
{
    private String baseUrl;
    private String method;
    private int timeout;
    private ArrayList<Field> fields;


    public Request(String u, String m)
    {
        baseUrl = u;
        method = m;
        timeout = 5000;
        fields = new ArrayList<>();
    }

    public Request addField(String key, String value)
    {
        fields.add(new Field(key, value));
        return this;
    }

    public Request setTimeout(int t)
    {
        timeout = t;
        return this;
    }

    private String generateUrl()
    {
        String url = baseUrl;
        url += method;
        if(fields.size() > 0)
        {
            url += "?" + fields.get(0).key + "=" + fields.get(0).value;
            for(int i = 1; i < fields.size(); i++)
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
                    response = listener.answer(Connection.getDataFromUrlConnection(buildURLConnection(url)).toString());
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
                    response = listener.answer(Connection.getDataFromUrlConnection(new PostURLConnection(buildURLConnection(url)).buildURLConnection(data)).toString());
                }
                catch(IOException e)
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

    private URLConnection buildURLConnection(String url)
            throws IOException
    {
        URL u = new URL(url);
        URLConnection urlConnection = u.openConnection();
        urlConnection.setConnectTimeout(timeout);
        urlConnection.setReadTimeout(timeout);
        return urlConnection;
    }

    private class Field
    {
        private String key;
        private String value;

        private Field(String k, String v)
        {
            this.key = k;
            this.value = v;
        }
    }
}