package com.big10.shortener.tests;

import com.big10.shortener.strategies.*;
import com.big10.shortener.Shortener;
import org.junit.Assert;
import org.junit.Test;

public class FunctionalTest
{
    public void testStorage(Shortener shortener)
    {
        String test1 = "This is a test";
        String test2 = "We are created for testing";
        String test3 = "This is a test";

        Long idTest1 = shortener.getId(test1);
        Long idTest2 = shortener.getId(test2);
        Long idTest3 = shortener.getId(test3);

        Assert.assertNotEquals(idTest2, idTest1);
        Assert.assertNotEquals(idTest2, idTest3);
        Assert.assertEquals(idTest1, idTest3);

        String getTest1 = shortener.getString(idTest1);
        String getTest2 = shortener.getString(idTest2);
        String getTest3 = shortener.getString(idTest3);

        Assert.assertEquals(test1, getTest1);
        Assert.assertEquals(test2, getTest2);
        Assert.assertEquals(test3, getTest3);
    }

    @Test
    public void testHashMapStorageStrategy()
    {
        testStorage(new Shortener(new HashMapStorageStrategy()));
    }

    @Test
    public void testOurHashMapStorageStrategy()
    {
        testStorage(new Shortener(new OurHashMapStorageStrategy()));
    }

    @Test
    public void testFileStorageStrategy()
    {
        testStorage(new Shortener(new FileStorageStrategy()));
    }

    @Test
    public void testHashBiMapStorageStrategy()
    {
        testStorage(new Shortener(new HashBiMapStorageStrategy()));
    }

    @Test
    public void testDualHashBidiMapStorageStrategy()
    {
        testStorage(new Shortener(new DualHashBidiMapStorageStrategy()));
    }

    @Test
    public void testOurHashBiMapStorageStrategy()
    {
        testStorage(new Shortener(new OurHashBiMapStorageStrategy()));
    }
}
