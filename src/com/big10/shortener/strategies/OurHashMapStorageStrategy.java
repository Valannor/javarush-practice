package com.big10.shortener.strategies;

public class OurHashMapStorageStrategy implements StorageStrategy
{
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    Entry[] table = new Entry[DEFAULT_INITIAL_CAPACITY];
    int size;
    int threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
    float loadFactor = DEFAULT_LOAD_FACTOR;


    @Override
    public boolean containsKey(Long key)
    {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value)
    {
        if (value == null)
            return false;

        Entry[] tab = table;
        for (Entry e : tab)
            for (; e != null; e = e.next)
                if (value.equals(e.value))
                    return true;

        return false;
    }

    @Override
    public void put(Long key, String value)
    {
        if (key == null)
            return;

        int hash = hash(key);
        int i = indexFor(hash, table.length);
        for (Entry e = table[i]; e != null; e = e.next)
        {
            Long k;
            if (e.getHash() == hash && ((k = e.key) == key || key.equals(k)))
            {
                e.value = value;
                return;
            }
        }

        addEntry(hash, key, value, i);
    }

    @Override
    public Long getKey(String value)
    {
        if (value == null)
            return null;

        Entry[] tab = table;
        for (Entry e : tab)
            for (; e != null; e = e.next)
                if (value.equals(e.getValue()))
                    return e.getKey();

        return null;
    }

    @Override
    public String getValue(Long key)
    {
        Entry entry = getEntry(key);

        return null == entry ? null : entry.getValue();
    }

    final int hash(Long k)
    {
        return k.hashCode();
    }

    static int indexFor(int hash, int length)
    {
        return hash & (length - 1);
    }

    final Entry getEntry(Long key)
    {
        if (size == 0)
        {
            return null;
        }

        int hash = (key == null) ? 0 : hash(key);

        for (Entry e = table[indexFor(hash, table.length)];
             e != null;
             e = e.next)
        {
            Long k;
            if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                return e;
        }

        return null;
    }

    void resize(int newCapacity)
    {
        Entry[] oldTable = table;
        int oldCapacity = oldTable.length;
        if (oldCapacity == (1 << 30))
        {
            threshold = Integer.MAX_VALUE;
            return;
        }

        Entry[] newTable = new Entry[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int) Math.min(newCapacity * loadFactor, (1 << 30) + 1);
    }

    void transfer(Entry[] newTable)
    {
        int newCapacity = newTable.length;
        for (Entry e : table)
        {
            while (null != e)
            {
                Entry next = e.next;
                int i = indexFor(e.hash, newCapacity);
                e.next = newTable[i];
                newTable[i] = e;
                e = next;
            }
        }
    }

    void addEntry(int hash, Long key, String value, int bucketIndex)
    {
        if ((size >= threshold) && (null != table[bucketIndex]))
        {
            resize(2 * table.length);
            hash = (null != key) ? hash(key) : 0;
            bucketIndex = indexFor(hash, table.length);
        }

        createEntry(hash, key, value, bucketIndex);
    }

    void createEntry(int hash, Long key, String value, int bucketIndex)
    {
        Entry e = table[bucketIndex];
        table[bucketIndex] = new Entry(hash, key, value, e);
        size++;
    }
}
