package com.big10.shortener.strategies;

import com.big10.shortener.ExceptionHandler;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBucket
{
    private Path path;

    public FileBucket()
    {
        try
        {
            path = Files.createTempFile("temp", null);
            Files.deleteIfExists(path);
            Files.createFile(path);
            path.toFile().deleteOnExit();
        }
        catch (IOException e)
        {
            ExceptionHandler.log(e);
        }
    }

    public long getFileSize()
    {
        try
        {
            return Files.size(path);
        }
        catch (IOException e)
        {
            ExceptionHandler.log(e);
        }

        return 0;
    }

    public void putEntry(Entry entry)
    {
        try
        {
            ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(path));
            outputStream.writeObject(entry);
        }
        catch (IOException e)
        {
            ExceptionHandler.log(e);
        }
    }

    public Entry getEntry()
    {
        if (getFileSize() > 0)
        {
            try
            {
                ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(path));
                return (Entry) inputStream.readObject();
            }
            catch (Exception e)
            {
                ExceptionHandler.log(e);
            }
        }

        return null;
    }

    public void remove()
    {
        try
        {
            Files.delete(path);
        }
        catch (IOException e)
        {
            ExceptionHandler.log(e);
        }
    }
}
