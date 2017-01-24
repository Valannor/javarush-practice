package com.big10.shortener.tests;

import com.big10.shortener.Helper;
import com.big10.shortener.Shortener;
import com.big10.shortener.strategies.HashBiMapStorageStrategy;
import com.big10.shortener.strategies.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SpeedTest
{
    public long getTimeForGettingIds(Shortener shortener, Set<String> strings, Set<Long> ids)
    {
        Long startTest = new Date().getTime();

        for (String s : strings)
            ids.add(shortener.getId(s));

        return new Date().getTime() - startTest;
    }

    public long getTimeForGettingStrings(Shortener shortener, Set<Long> ids, Set<String> strings)
    {
        Long startTest = new Date().getTime();

        for (Long id : ids)
            strings.add(shortener.getString(id));

        return new Date().getTime() - startTest;
    }

    @Test
    public void testHashMapStorage()
    {
        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());

        Set<String> origStrings = new HashSet<>();
        for (int i = 0; i < 10000; i++)
            origStrings.add(Helper.generateRandomString());

        Set<Long> shortener1_Ids = new HashSet<>();
        Set<Long> shortener2_Ids = new HashSet<>();
        Long timeForGettingIds1 = getTimeForGettingIds(shortener1, origStrings, shortener1_Ids);
        Long timeForGettingIds2 = getTimeForGettingIds(shortener2, origStrings, shortener2_Ids);

        Assert.assertTrue(timeForGettingIds1 > timeForGettingIds2);

        Set<String> shortener1_Str = new HashSet<>();
        Set<String> shortener2_Str = new HashSet<>();
        Long timeForGettingStrings1 = getTimeForGettingStrings(shortener1, shortener1_Ids, shortener1_Str);
        Long timeForGettingStrings2 = getTimeForGettingStrings(shortener2, shortener2_Ids, shortener2_Str);

        Assert.assertEquals(timeForGettingStrings1, timeForGettingStrings2, 5);
    }
}
