package com.big05.cash_machine.command;

import com.big05.cash_machine.CashMachine;
import com.big05.cash_machine.ConsoleHelper;
import com.big05.cash_machine.exception.InterruptOperationException;

import java.util.ResourceBundle;

class LoginCommand implements Command
{
    //Текст операций в ресурсах
    private ResourceBundle validCreditCards = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "verifiedCards");
    private ResourceBundle resLog = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "login_en");
    String before = resLog.getString("before");
    String enterData = resLog.getString("specify.data");
    String success = resLog.getString("success.format");
    String notVer = resLog.getString("not.verified.format");
    String tryAgain = resLog.getString("try.again.or.exit");
    String invalidInput = resLog.getString("try.again.with.details");

    @Override
    public void execute() throws InterruptOperationException
    {
        int pinRes = 0;
        int pinAsk;

        ConsoleHelper.writeMessage(before + "\n" + enterData);

        while (true)
        {
            try
            {
                //Запрашиваем номер карты.
                String cardS = ConsoleHelper.readString();
                if (cardS.length() < 12 || cardS.length() > 12)
                {
                    throw new IllegalArgumentException();
                }

                //Запрашиваем пароль.
                String pinS = ConsoleHelper.readString();
                if (pinS.length() < 4 || pinS.length() > 4)
                {
                    throw new IllegalArgumentException();
                }
                pinAsk = Integer.parseInt(pinS);

                //Проверяем, есть ли такая карта в наших ресурсах.
                if (validCreditCards.containsKey(cardS))
                    pinRes = Integer.parseInt(validCreditCards.getString(cardS));
                else
                    ConsoleHelper.writeMessage(String.format(notVer, cardS));

                //Проверяем пароль. Если совпадает - логинимся, иначе повторяем верификацию.
                if (pinAsk == pinRes)
                {
                    ConsoleHelper.writeMessage(String.format(success, cardS));
                    break;
                }
                else ConsoleHelper.writeMessage(tryAgain);
            }
            catch (Exception e1)
            {
                ConsoleHelper.writeMessage(invalidInput);
            }
        }
    }
}
