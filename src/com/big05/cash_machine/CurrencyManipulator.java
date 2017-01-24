package com.big05.cash_machine;

import com.big05.cash_machine.exception.NotEnoughMoneyException;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CurrencyManipulator
{
    private String currencyCode;
    private Map<Integer, Integer> denominations = new HashMap<>();

    public CurrencyManipulator(String currencyCode)
    {
        this.currencyCode = currencyCode;
    }



    /**
     * Получение кода валюты
     */
    public String getCurrencyCode()
    {
        return currencyCode;
    }

    

    /**
     * Добавление номинала купюры и их количества в карту
     */
    public void addAmount(int denomination, int count)
    {
        if (denominations.containsKey(denomination))
            denominations.put(denomination, denominations.get(denomination) + count);
        else
            denominations.put(denomination, count);
    }



    /**
     * Получение информации о доступном количестве всех денег
     */
    public int getTotalAmount()
    {
        int result = 0;

        for (Map.Entry<Integer, Integer> pair : this.denominations.entrySet())
        {
            int key = pair.getKey();
            int value = pair.getValue();

            result += key * value;
        }

        return result;
    }



    /**
     * Проверка, есть ли средства в банкомате
     */
    public boolean hasMoney()
    {
        return  denominations.isEmpty();
    }



    /**
     * Проверка, достаточно ли денег
     */
    public boolean isAmountAvailable(int expectedAmount)
    {
        return expectedAmount <= getTotalAmount();
    }



    /**
     * Операция списания денег со счета
     */
    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException
    {
        //Сортируем все значения по убываюшей
        Map<Integer, Integer> sortedDenominations = new TreeMap<>(new Comparator<Integer>()
        {
            @Override
            public int compare(Integer o1, Integer o2)
            {
                return o2.compareTo(o1);
            }
        });
        sortedDenominations.putAll(denominations);

        //Находим нужную комбинацию
        Map<Integer, Integer> combo = new HashMap<>();
        int copyExpectedAmount = expectedAmount;
        int timer = 0;
        A:
        for (Map.Entry<Integer, Integer> pair : sortedDenominations.entrySet())
        {
            timer++;
            int nominal = pair.getKey();
            int count = pair.getValue();

            if (copyExpectedAmount >= nominal)
            {
                for (int i = 1; i <= count; i++)
                {
                    if (nominal < copyExpectedAmount)
                    {
                        copyExpectedAmount -= nominal;
                        combo.put(nominal, i);
                    }
                    else if (nominal == copyExpectedAmount)
                    {
                        combo.put(nominal, i);
                        break A;
                    }
                    else if (nominal > copyExpectedAmount)
                        break;
                }
            }

            //Если остаются лишние деньги - нет нужной комбинации
            if (copyExpectedAmount > 0 && timer == sortedDenominations.size())
                throw new NotEnoughMoneyException();
        }

        //Удаляем выданные деньги из карты
        for (Map.Entry<Integer, Integer> pair : combo.entrySet())
        {
            int nominal = pair.getKey();
            int comboCount = pair.getValue();
            int denCount = denominations.get(nominal);
            int resultCount = denCount - comboCount;

            if (resultCount == 0)
                denominations.remove(nominal);
            else
                denominations.put(nominal, resultCount);
        }

        return combo;
    }
}
