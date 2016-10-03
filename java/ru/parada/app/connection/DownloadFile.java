package ru.parada.app.connection;

import java.io.File;
import java.io.IOException;

public class DownloadFile
{
    private String fullPath;
    private String link;

    public DownloadFile(String fp, String l)
    {
        this.fullPath = fp;
        this.link = l;
    }

    public void download(final DownloadFileListener listener)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Connection.downloadFile(fullPath, link);
                }
                catch(IOException e)
                {
                    listener.error(e);
                    return;
                }
                File file = new File(fullPath);
                if(file == null)
                {
                    listener.error(new Exception("file null"));
                    return;
                }
                if(!file.exists())
                {
                    listener.error(new Exception("file not exist"));
                    return;
                }
                if(file.isDirectory())
                {
                    listener.error(new Exception("file is directory"));
                    return;
                }
                if(file.length() <= 0)
                {
                    listener.error(new Exception("file zero length"));
                    return;
                }
                listener.answer(file);
            }
        }).start();
    }

    public interface DownloadFileListener
    {
        void answer(File file);
        void error(Exception error);
    }
}