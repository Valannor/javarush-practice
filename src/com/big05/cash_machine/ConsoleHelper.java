package com.big05.cash_machine;

import com.big05.cash_machine.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

public class ConsoleHelper
{
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    //Текст операций в ресурсах
    private static ResourceBundle res2 = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "common_en");
    static String end = res2.getString("the.end");
    static String chooseOp = res2.getString("choose.operation");
    static String info = res2.getString("operation.INFO");
    static String deposit = res2.getString("operation.DEPOSIT");
    static String withDraw = res2.getString("operation.WITHDRAW");
    static String exit = res2.getString("operation.EXIT");
    static String inValid = res2.getString("invalid.data");
    static String chooseCode = res2.getString("choose.currency.code");
    static String chooseDenCount = res2.getString("choose.denomination.and.count.format");



    /**
     * Метод, выводящий сообщение пользователю
     */
    public static void writeMessage(String message)
    {
        System.out.println(message);
    }



    /**
     * Метод, выводящий сообщение о выходе
     */
    public static void printExitMessage()
    {
        System.out.println(end);
    }



    /**
     * Метод, принимающий текст с консоли
     */
    public static String readString() throws InterruptOperationException
    {
        String console = null;
        try
        {
            console = reader.readLine();
        }
        catch (IOException ignored)
        {
        }

        if (console != null && console.toUpperCase().equals("EXIT"))
        {
            printExitMessage();
            throw new InterruptOperationException();
        }

        return console;
    }



    /**
     * Ввод кода валюты
     */
    public static String askCurrencyCode() throws InterruptOperationException
    {
        writeMessage(chooseCode);
        String currencyName = readString();

        while (currencyName.length() != 3)
        {
            writeMessage(inValid);
            currencyName = readString();
        }

        return currencyName.toUpperCase();
    }



    /**
     * Ввод номинала и количество купюр
     */
    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException
    {
        ResourceBundle res1 = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "deposit_en");
        String success = res1.getString("success.format");

        String[] array;
        int nominal;
        int count;

        while (true)
        {
            writeMessage(String.format(chooseDenCount, currencyCode));
            String line = readString();
            array = line.split(" ");

            try
            {
                nominal = Integer.parseInt(array[0]);
                count = Integer.parseInt(array[1]);

                if (nominal <= 0 || count <= 0 || array.length > 2 || array.length < 2)
                    writeMessage(inValid);
                else
                {
                    writeMessage(String.format(success, nominal*count, currencyCode));
                    break;
                }
            }
            catch (Exception e)
            {
                writeMessage(inValid);
            }
        }

        return array;
    }



    /**
     * Ввод номера операции
     */
    public static Operation askOperation() throws InterruptOperationException
    {
        writeMessage("\n" + "\n" + chooseOp + "\n" + info + " - 1, " +  deposit + " - 2, " + withDraw + " - 3, " + exit+ " - 4.");
        Operation operation;
        int numb;

        while (true)
        {
            try
            {
                numb = Integer.parseInt(readString());
                operation = Operation.getAllowableOperationByOrdinal(numb);
                break;
            }
            catch (Exception e)
            {
                writeMessage(chooseOp);
            }
        }

        return operation;
    }
}
