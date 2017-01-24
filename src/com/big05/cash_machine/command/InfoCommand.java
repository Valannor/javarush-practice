package com.big05.cash_machine.command;

import com.big05.cash_machine.CashMachine;
import com.big05.cash_machine.ConsoleHelper;
import com.big05.cash_machine.CurrencyManipulator;
import com.big05.cash_machine.CurrencyManipulatorFactory;

import java.util.ResourceBundle;

class InfoCommand implements Command
{
    //Текст операций в ресурсах
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "info_en");
    String before = res.getString("before");
    String noMoney = res.getString("no.money");

    @Override
    public void execute()
    {
        ConsoleHelper.writeMessage(before);

        if (CurrencyManipulatorFactory.getAllCurrencyManipulators().isEmpty())
            ConsoleHelper.writeMessage(noMoney);

        for (CurrencyManipulator manipulator : CurrencyManipulatorFactory.getAllCurrencyManipulators())
        {
            if (manipulator.hasMoney())
                ConsoleHelper.writeMessage(noMoney);
            else
            {
                String currencyCode = manipulator.getCurrencyCode();
                ConsoleHelper.writeMessage(currencyCode + " - " + manipulator.getTotalAmount());
            }
        }
    }
}
