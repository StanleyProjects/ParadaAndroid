package ru.parada.app.connection;

import java.util.ArrayList;

public class Request
{
    private String baseUrl;
    private String method;
    public ArrayList<Field> fields;

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

    public void execute(final RequestListener listener)
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
        final String finalUrl = url;
        new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    listener.answer(Connection.getDataFromUrlConnection(Connection.buildURLConnection(finalUrl)).toString());
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
        public String key;
        public Object value;

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