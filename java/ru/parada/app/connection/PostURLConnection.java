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
    private final URLConnection httpConn;
    private final DataOutputStream output;

    public PostURLConnection(String url)
            throws IOException
    {
        boundary = "---" + System.currentTimeMillis();
        httpConn = new URL(url).openConnection();
        httpConn.setDoOutput(true);
        httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        output = new DataOutputStream(httpConn.getOutputStream());
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

    public URLConnection buildPostURLConnection(Map<String, String> data)
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
        return httpConn;
    }
}