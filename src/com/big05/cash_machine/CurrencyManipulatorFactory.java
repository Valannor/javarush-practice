package com.big05.cash_machine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CurrencyManipulatorFactory
{
    private static CurrencyManipulatorFactory ourInstance = new CurrencyManipulatorFactory();
    private static Map<String, CurrencyManipulator> manipulatorsMap = new HashMap<>();

    public static CurrencyManipulatorFactory getInstance()
    {
        return ourInstance;
    }

    private CurrencyManipulatorFactory()
    {
    }



    /**
     * Получаем манипулятор по коду валюты
     */
    public static CurrencyManipulator getManipulatorByCurrencyCode(String currencyCode)
    {
        int n = 0;
        CurrencyManipulator result = null;
        for (Map.Entry<String, CurrencyManipulator> pair : manipulatorsMap.entrySet())
        {
            String key = pair.getKey();
            CurrencyManipulator value = pair.getValue();
            if (key.equals(currencyCode))
            {
                n = 1;
                result = value;
            }
        }

        if (n == 0)
        {
            result = new CurrencyManipulator(currencyCode);
            manipulatorsMap.put(currencyCode, result);
        }

        return result;
    }



    /**
     * Получаем коллекцию из всех манипуляторов
     */
    public static Collection<CurrencyManipulator> getAllCurrencyManipulators()
    {
        return manipulatorsMap.values();
    }
}
