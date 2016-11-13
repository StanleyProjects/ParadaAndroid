package ru.parada.app.connection;

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
    public void execute(final RequestListener listener)
    {
        new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    listener.answer(Connection.getDataFromUrlConnection(Connection.buildURLConnection(generateUrl())).toString());
                }
                catch(Exception e)
                {
                    listener.error(e);
                }
            }
        }).start();
    }
    public void executePost(final Map<String, String> data, final RequestListener listener)
    {
        new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    listener.answer(Connection.getDataFromUrlConnection(new PostURLConnection(generateUrl()).buildPostURLConnection(data)).toString());
                }
                catch(Exception e)
                {
                    listener.error(e);
                }
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

    public interface RequestListener
    {
        void answer(String answer);
        void error(Exception error);
    }
}