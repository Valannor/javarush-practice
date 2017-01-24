package com.big07.messenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper
{
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message)
    {
        System.out.println(message);
    }

    public static String readString()
    {
        String text;

        try
        {
            text = reader.readLine();
        }
        catch (IOException e1)
        {
            System.out.println("Произошла ошибка при попытке ввода текста. Попробуйте еще раз.");
            text = readString();
        }

        return text;
    }

    public static int readInt()
    {
        int number;

        try
        {
            number = Integer.parseInt(readString());
        }
        catch (NumberFormatException e1)
        {
            System.out.println("Произошла ошибка при попытке ввода числа. Попробуйте еще раз.");
            number = readInt();
        }

        return number;
    }
}
