package com.big10.shortener.strategies;

import java.io.Serializable;

public class Entry implements Serializable
{
    Long key;
    String value;
    Entry next;
    int hash;

    public Entry(int hash, Long key, String value, Entry next)
    {
        this.key = key;
        this.value = value;
        this.next = next;
        this.hash = hash;
    }

    public Long getKey()
    {
        return key;
    }

    public String getValue()
    {
        return value;
    }

    public int getHash()
    {
        return hash;
    }

    @Override
    public String toString()
    {
        return "Entry{" +
                "key=" + key +
                ", value='" + value + '\'' +
                '}';
    }
}
