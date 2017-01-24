package com.big05.cash_machine;

import com.big05.cash_machine.command.CommandExecutor;
import com.big05.cash_machine.exception.InterruptOperationException;

import java.util.Locale;

public class CashMachine
{
    //Путь к ресурсам
    public static final String RESOURCE_PATH = "com.big05.cash_machine.resources.";

    //Если операция "Выход" изменит эту переменную на true, при выборе "y", то основной цикл прекратится
    public static boolean isReallyExit = false;

    public static void main(String[] args)
    {
        Locale.setDefault(Locale.ENGLISH);
        Operation operation;
        boolean isLogged = false;

        //Пока isReallyExit == false, цикл предлагает выбрать операции и исполняет их
        while (!isReallyExit)
        {
            try
            {
                //Если залогинились, то верификация больше не требуется (до самого выхода)
                if (!isLogged)
                {
                    CommandExecutor.execute(Operation.LOGIN);
                    isLogged = true;
                }
                operation = ConsoleHelper.askOperation();
                CommandExecutor.execute(operation);
            }
            catch (InterruptOperationException e)
            {
                break;
            }
        }
    }
}
