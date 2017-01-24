package com.big08.archiver.command;

import com.big08.archiver.ConsoleHelper;

public class ExitCommand implements Command
{
    @Override
    public void execute() throws Exception
    {
        ConsoleHelper.writeMessage("До встречи!");
    }
}
