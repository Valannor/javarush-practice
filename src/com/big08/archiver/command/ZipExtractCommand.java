package com.big08.archiver.command;

import com.big08.archiver.ConsoleHelper;
import com.big08.archiver.ZipFileManager;
import com.big08.archiver.exception.PathIsNotFoundException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipExtractCommand extends ZipCommand
{
    @Override
    public void execute() throws Exception
    {
        try
        {
            ConsoleHelper.writeMessage("Распаковка архива.");

            ZipFileManager zipFileManager = getZipFileManager();

            ConsoleHelper.writeMessage("Введите путь для распаковки:");
            Path destinationPath = Paths.get(ConsoleHelper.readString());
            zipFileManager.extractAll(destinationPath);

            ConsoleHelper.writeMessage("Архив был распакован.");

        }
        catch (PathIsNotFoundException e)
        {
            ConsoleHelper.writeMessage("Неверный путь для распаковки.");
        }
    }
}
