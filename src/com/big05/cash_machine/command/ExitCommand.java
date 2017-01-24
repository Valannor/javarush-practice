package com.big05.cash_machine.command;

import com.big05.cash_machine.CashMachine;
import com.big05.cash_machine.ConsoleHelper;
import com.big05.cash_machine.exception.InterruptOperationException;

import java.util.ResourceBundle;

class ExitCommand implements Command
{
    //Текст операций в ресурсах
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "exit_en");
    String exit = res.getString("exit.question.y.n");
    String yes = res.getString("yes");
    String no = res.getString("no");
    String goodLuck = res.getString("thank.message");
    String opContinue = res.getString("continue.message");
    String invalidKey = res.getString("invalid.message");

    @Override
    public void execute() throws InterruptOperationException
    {
        ConsoleHelper.writeMessage(exit);

        while (true)
        {
            String answer = ConsoleHelper.readString();
            if (answer.equals(yes))
            {
                ConsoleHelper.writeMessage(goodLuck);
                CashMachine.isReallyExit = true;
                break;
            }
            else if (answer.equals(no))
            {
                ConsoleHelper.writeMessage(opContinue);
                break;
            }
            else
            {
                ConsoleHelper.writeMessage(invalidKey);
            }
        }
    }
}
