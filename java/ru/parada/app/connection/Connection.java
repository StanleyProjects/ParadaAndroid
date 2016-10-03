package ru.parada.app.connection;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class Connection
{
    static public void downloadFile(String fullPath, String link)
            throws IOException
    {
        URL url = new URL(link);
        InputStream in = new BufferedInputStream(url.openStream());
        FileOutputStream fos = new FileOutputStream(fullPath);
        byte[] buf = new byte[1024];
        int n;
        while(-1 != (n = in.read(buf)))
        {
            fos.write(buf, 0, n);
        }
        fos.close();
        in.close();
    }

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