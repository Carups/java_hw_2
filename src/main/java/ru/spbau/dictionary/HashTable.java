package ru.spbau.dictionary;

import jdk.internal.util.xml.impl.Pair;

public class HashTable implements Dictionary {
    final int defaultSizeOfTable = 1024;
    private int size, sizeOfTable, version;
    final private double rateDown, rateUp;
    private List[] table;
    private boolean rehashOn;
    {
        rehashOn = false;
        sizeOfTable = defaultSizeOfTable;
        table = new List[defaultSizeOfTable];
        version = 0;
        size = 0;
    }

    HashTable(double rateDown, double rateUp) {
        this.rateDown = rateDown;
        this.rateUp = rateUp;
    }

    public int size() {
        return size;
    }

    public boolean contains(String key) {
        return table[getHashKey(key)].contains(key);
    }

    public String get(String key) {
        return table[getHashKey(key)].get(key);
    }

    public String put(String key, String value) {
        String res = table[getHashKey(key)].put(key, value);
        size++;
        if (size > sizeOfTable * rateUp){
            rehash(sizeOfTable * 2);
        }
        return res;
    }

    public String remove(String key)
    {
        String res = table[getHashKey(key)].remove(key);
        if (res != null)
        {
            size--;
            if (size < sizeOfTable * rateDown) {
                rehash(sizeOfTable / 2);
            }
        }
        return res;
    }

    public void clear()
    {
        size = 0;
        version++;
        rehash(defaultSizeOfTable);
    }

    private int getHashKey(String key)
    {
        int hashKey = ((key.hashCode() % sizeOfTable) + sizeOfTable) % sizeOfTable;
        update(hashKey);
        return hashKey;
    }

    private void update(int hashKey)
    {
        if (table[hashKey] == null) {
            table[hashKey] = new List();
            table[hashKey].setVersion(version);
        }
        else{
            if (table[hashKey].getVersion() != version)
            {
                table[hashKey] = new List();
                table[hashKey].setVersion(version);
            }
        }

    }

    public int getSizeOfTable() {
        return sizeOfTable;
    }

    private void rehash(int newSize)
    {
        if (newSize != sizeOfTable && newSize >= defaultSizeOfTable && rehashOn == false){
            rehashOn = true;
            List[] oldTable = table;
            table = new List[newSize];
            sizeOfTable = newSize;
            size = 0;
            for (List curList: oldTable) {
                if (curList != null) {
                    String[] keys = curList.getKeys();
                    String[] values = curList.getValues();
                    for (int i = 0; i < keys.length; i++) {
                        put(keys[i], values[i]);
                    }
                }
            }

            rehashOn = false;
        }
    }
}

