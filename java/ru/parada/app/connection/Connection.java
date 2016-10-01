package ru.parada.app.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class Connection
{
    static public URLConnection buildURLConnection(String url)
            throws IOException
    {
        URLConnection urlConn = new URL(url).openConnection();
        urlConn.setUseCaches(false);
        return urlConn;
    }

    static public StringBuilder getDataFromUrlConnection(URLConnection urlConn)
            throws IOException
    {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(urlConn.getInputStream(), Charset.forName("UTF-8")));
        StringBuilder completeResponse = new StringBuilder();
        String response = br.readLine();
        while(response != null)
        {
            completeResponse.append(response);
            response = br.readLine();
        }
        br.close();
        return completeResponse;
    }
}