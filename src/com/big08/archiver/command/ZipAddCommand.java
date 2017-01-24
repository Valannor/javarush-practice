package com.big08.archiver.command;

import com.big08.archiver.ConsoleHelper;
import com.big08.archiver.ZipFileManager;
import com.big08.archiver.exception.PathIsNotFoundException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipAddCommand extends ZipCommand
{
    @Override
    public void execute() throws Exception
    {
        try
        {
            ConsoleHelper.writeMessage("Добавление файла в архив.");
            ZipFileManager zipFileManager = getZipFileManager();

            ConsoleHelper.writeMessage("Введите полное имя файла для добавления:");
            Path sourcePath = Paths.get(ConsoleHelper.readString());
            zipFileManager.addFile(sourcePath);

            ConsoleHelper.writeMessage("Добавление в архив завершено.");
        }
        catch (PathIsNotFoundException e)
        {
            ConsoleHelper.writeMessage("Файл не был найден.");
        }
    }
}
