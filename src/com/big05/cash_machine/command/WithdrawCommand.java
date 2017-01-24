package com.big05.cash_machine.command;

import com.big05.cash_machine.CashMachine;
import com.big05.cash_machine.ConsoleHelper;
import com.big05.cash_machine.CurrencyManipulator;
import com.big05.cash_machine.CurrencyManipulatorFactory;
import com.big05.cash_machine.exception.InterruptOperationException;
import com.big05.cash_machine.exception.NotEnoughMoneyException;

import java.util.*;

class WithdrawCommand implements Command
{
    //Текст операций в ресурсах
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "withdraw_en");
    String before = res.getString("before");
    String success = res.getString("success.format");
    String amount = res.getString("specify.amount");
    String invalidAmount = res.getString("specify.not.empty.amount");
    String notEnough = res.getString("not.enough.money");
    String notAvailable = res.getString("exact.amount.not.available");
    String notCurrency = res.getString("currency.not.available");
    String emptyAccount = res.getString("account.empty");

    @Override
    public void execute() throws InterruptOperationException
    {
        ConsoleHelper.writeMessage(before);

        String currencyCode;
        CurrencyManipulator manipulator;

        //Если никаких средств на счету нет, возвращаем к выбору операции
        if (CurrencyManipulatorFactory.getAllCurrencyManipulators().isEmpty())
        {
            ConsoleHelper.writeMessage(emptyAccount);
        }
        else
        {
            /**
             * Определяем, есть ли манипуоятор с таким кодом валюты
             */
            A:
            while (true)
            {
                currencyCode = ConsoleHelper.askCurrencyCode();

                for (CurrencyManipulator isPresented : CurrencyManipulatorFactory.getAllCurrencyManipulators())
                {
                    if (isPresented.getCurrencyCode().equals(currencyCode))
                    {
                        manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
                        break A;
                    }
                }
                //Такой валюты нет в наличии
                ConsoleHelper.writeMessage(notCurrency);
            }


            /**
             * Основной цикл с операцией списания
             */
            while (true)
            {
                /*
                Если манипулятор пуст - заверщаем цикл и операцию
                 */
                if (manipulator.getTotalAmount() == 0)
                {
                    ConsoleHelper.writeMessage(emptyAccount);
                    break;
                }


                /*
                Просим пользователя ввести нужную сумму. Если данные некоректны - возвращаем его к вводу суммы.
                */
                ConsoleHelper.writeMessage(amount);
                int expectedAmount;
                try
                {
                    expectedAmount = Integer.parseInt(ConsoleHelper.readString());
                    if (expectedAmount <= 0) throw new NumberFormatException();
                }
                catch (NumberFormatException e)
                {
                    ConsoleHelper.writeMessage(invalidAmount);
                    continue;
                }


                /*
                Если нужная нам сумма есть и есть нужные для суммы купюры - мы выдаем их и завершаем операцию.
                Иначе, мы возвращаем пользователя в пункт "Введите нужную сумму".
                */
                if (manipulator.isAmountAvailable(expectedAmount))
                {
                    Map<Integer, Integer> amountOut;
                    try
                    {
                        amountOut = manipulator.withdrawAmount(expectedAmount);
                    }
                    catch (NotEnoughMoneyException e)
                    {
                        ConsoleHelper.writeMessage(notAvailable);
                        continue;
                    }

                    //Сортируем все значения по убываюшей
                    Map<Integer, Integer> sortedAmounts = new TreeMap<>(new Comparator<Integer>()
                    {
                        @Override
                        public int compare(Integer o1, Integer o2)
                        {
                            return o2.compareTo(o1);
                        }
                    });
                    sortedAmounts.putAll(amountOut);

                    //Выводим суммы на экран
                    for (Map.Entry<Integer, Integer> pair : sortedAmounts.entrySet())
                    {
                        ConsoleHelper.writeMessage("    " + pair.getKey() + " - " + pair.getValue());
                    }

                    ConsoleHelper.writeMessage(String.format(success, expectedAmount, currencyCode));
                    break;
                } else ConsoleHelper.writeMessage(notEnough);
            }
        }
    }
}
