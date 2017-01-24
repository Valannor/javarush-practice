package com.big10.shortener;

import com.big10.shortener.strategies.*;

import java.util.*;

public class Solution
{
    public static Set<Long> getIds(Shortener shortener, Set<String> strings)
    {
        Set<Long> result = new HashSet<>();
        for (String s : strings)
            result.add(shortener.getId(s));

        return result;
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys)
    {
        Set<String> result = new HashSet<>();
        for (Long l : keys)
            result.add(shortener.getString(l));

        return result;
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber)
    {
        Helper.printMessage(strategy.getClass().getSimpleName());

        Set<String> testStrings = new HashSet<>();
        for (long i = 0; i < elementsNumber; i++)
            testStrings.add(Helper.generateRandomString());

        Shortener shortener = new Shortener(strategy);

        Long testTime1 = new Date().getTime();
        Set<Long> testKeys = getIds(shortener, testStrings);
        Helper.printMessage(String.valueOf(new Date().getTime() - testTime1));

        Long testTime2 = new Date().getTime();
        Set<String> tempStrings = getStrings(shortener, testKeys);
        Helper.printMessage(String.valueOf(new Date().getTime() - testTime2));

        if (tempStrings.equals(testStrings))
            Helper.printMessage("Тест пройден.");
        else
            Helper.printMessage("Тест не пройден.");
    }

    public static void main(String[] args)
    {
        testStrategy(new HashMapStorageStrategy(), 10000);
        testStrategy(new OurHashMapStorageStrategy(), 10000);
        testStrategy(new FileStorageStrategy(), 100);
        testStrategy(new OurHashBiMapStorageStrategy(), 10000);
        testStrategy(new HashBiMapStorageStrategy(), 10000);
        testStrategy(new DualHashBidiMapStorageStrategy(), 10000);
    }
}
