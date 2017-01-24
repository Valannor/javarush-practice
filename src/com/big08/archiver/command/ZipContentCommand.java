package com.big08.archiver.command;

import com.big08.archiver.ConsoleHelper;
import com.big08.archiver.FileProperties;
import com.big08.archiver.ZipFileManager;

import java.util.List;

public class ZipContentCommand extends ZipCommand
{
    @Override
    public void execute() throws Exception
    {
        ConsoleHelper.writeMessage("Просмотр содержимого архива.");

        ZipFileManager zipFileManager = getZipFileManager();

        ConsoleHelper.writeMessage("Содержимое архива:");

        List<FileProperties> files = zipFileManager.getFilesList();
        for (FileProperties file : files)
        {
            ConsoleHelper.writeMessage(file.toString());
        }

        ConsoleHelper.writeMessage("Содержимое архива прочитано.");
    }
}
