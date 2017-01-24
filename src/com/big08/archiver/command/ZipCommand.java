package com.big08.archiver.command;

import com.big08.archiver.ConsoleHelper;
import com.big08.archiver.ZipFileManager;

import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class ZipCommand implements Command
{

    public ZipFileManager getZipFileManager() throws Exception
    {
        ConsoleHelper.writeMessage("Введите полный путь файла архива:");
        Path zipPath = Paths.get(ConsoleHelper.readString());
        return new ZipFileManager(zipPath);
    }
}