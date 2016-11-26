package ru.parada.app.connection;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class PostURLConnection
{
    static private final String LINE_FEED = "\r\n";

    private final String boundary;
    private final URLConnection urlConnection;
    private final DataOutputStream output;

    public PostURLConnection(URLConnection u)
            throws IOException
    {
        boundary = "---" + System.currentTimeMillis();
        urlConnection = u;
        urlConnection.setDoOutput(true);
        urlConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        output = new DataOutputStream(urlConnection.getOutputStream());
    }
    private void addData(String key, String value)
            throws IOException
    {
        output.writeBytes("--" + boundary);
        output.writeBytes(LINE_FEED);
        output.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"");
        output.writeBytes(LINE_FEED);
        output.writeBytes(LINE_FEED);
        output.writeBytes(value);
        output.writeBytes(LINE_FEED);
    }

    public URLConnection buildURLConnection(Map<String, String> data)
            throws IOException
    {
        if(data != null && data.size() > 0)
        {
            for(String key: data.keySet())
            {
                addData(key, data.get(key));
            }
        }
        output.writeBytes("--" + boundary);
        output.writeBytes(LINE_FEED);
        output.writeBytes("--");
        output.close();
        return urlConnection;
    }
}