package com.big08.archiver.command;

import com.big08.archiver.ConsoleHelper;
import com.big08.archiver.ZipFileManager;
import com.big08.archiver.exception.PathIsNotFoundException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipCreateCommand extends ZipCommand
{
    @Override
    public void execute() throws Exception
    {
        try
        {
            ConsoleHelper.writeMessage("Создание архива.");

            ZipFileManager zipFileManager = getZipFileManager();

            ConsoleHelper.writeMessage("Введите полное имя файла или директории для архивации:");
            Path sourcePath = Paths.get(ConsoleHelper.readString());
            zipFileManager.createZip(sourcePath);

            ConsoleHelper.writeMessage("Архив создан.");

        }
        catch (PathIsNotFoundException e)
        {
            ConsoleHelper.writeMessage("Вы неверно указали имя файла или директории.");
        }
    }
}
