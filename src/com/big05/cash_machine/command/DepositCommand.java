package com.big05.cash_machine.command;

import com.big05.cash_machine.CashMachine;
import com.big05.cash_machine.ConsoleHelper;
import com.big05.cash_machine.CurrencyManipulator;
import com.big05.cash_machine.CurrencyManipulatorFactory;
import com.big05.cash_machine.exception.InterruptOperationException;

import java.util.ResourceBundle;

class DepositCommand implements Command
{
    //Текст операций в ресурсах
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "deposit_en");
    String before = res.getString("before");

    @Override
    public void execute() throws InterruptOperationException
    {
        ConsoleHelper.writeMessage(before);

        String currencyCode = ConsoleHelper.askCurrencyCode();
        String[] denomination = ConsoleHelper.getValidTwoDigits(currencyCode);
        int nominal = Integer.parseInt(denomination[0]);
        int count = Integer.parseInt(denomination[1]);

        CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
        manipulator.addAmount(nominal, count);
    }
}
