package com.big10.shortener.strategies;


public class FileStorageStrategy implements StorageStrategy
{
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    private FileBucket[] table = tempMethod();
    private long bucketSizeLimit = 10000;

    private FileBucket[] tempMethod()
    {
        FileBucket[] buckets = new FileBucket[DEFAULT_INITIAL_CAPACITY];
        for (int i = 0; i < DEFAULT_INITIAL_CAPACITY; i++)
        {
            buckets[i] = new FileBucket();
        }

        return buckets;
    }

    public long getBucketSizeLimit()
    {
        return bucketSizeLimit;
    }

    public void setBucketSizeLimit(long bucketSizeLimit)
    {
        this.bucketSizeLimit = bucketSizeLimit;
    }

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

        FileBucket[] tab = table;
        for (FileBucket eBucket : tab)
        {
            Entry e = eBucket.getEntry();
            for (; e != null; e = e.next)
                if (value.equals(e.value))
                    return true;
        }

        return false;
    }

    @Override
    public void put(Long key, String value)
    {
        if (key == null)
            return;

        int hash = hash(key);
        int i = indexFor(hash, table.length);
        for (Entry e = table[i].getEntry(); e != null; e = e.next)
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

        FileBucket[] tab = table;
        for (FileBucket eBucket : tab)
        {
            Entry e = eBucket.getEntry();
            for (; e != null; e = e.next)
                if (value.equals(e.getValue()))
                    return e.getKey();
        }

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
        int hash = (key == null) ? 0 : hash(key);

        for (Entry e = table[indexFor(hash, table.length)].getEntry();
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
        FileBucket[] newTable = new FileBucket[newCapacity];
        for (int i = 0; i < newTable.length; i++)
            newTable[i] = new FileBucket();

        for (FileBucket bucket : table)
            bucket.remove();

        transfer(newTable);
        table = newTable;
    }

    void transfer(FileBucket[] newTable)
    {
        int newCapacity = newTable.length;
        for (FileBucket eBucket : table)
        {
            Entry e = eBucket.getEntry();
            while (null != e)
            {
                Entry next = e.next;
                int i = indexFor(e.hash, newCapacity);
                e.next = newTable[i].getEntry();
                newTable[i].putEntry(e);
                e = next;
            }
        }
    }

    void addEntry(int hash, Long key, String value, int bucketIndex)
    {
        if (null != table[bucketIndex])
        {
            for (FileBucket bucket : table)
            {
                if (bucket.getFileSize() > bucketSizeLimit)
                {
                    resize(2 * table.length);
                    break;
                }
            }

            hash = (null != key) ? hash(key) : 0;
            bucketIndex = indexFor(hash, table.length);
        }

        createEntry(hash, key, value, bucketIndex);
    }

    void createEntry(int hash, Long key, String value, int bucketIndex)
    {
        Entry e = table[bucketIndex].getEntry();
        table[bucketIndex].putEntry(new Entry(hash, key, value, e));
    }
}
