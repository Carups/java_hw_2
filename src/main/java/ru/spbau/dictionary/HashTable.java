package ru.spbau.dictionary;


public class HashTable implements Dictionary {
    private final int defaultSizeOfTable = 1024;
    private int size, sizeOfTable;
    final private double rateDown, rateUp;
    private List[] table;

    {
        sizeOfTable = defaultSizeOfTable;
        table = new List[defaultSizeOfTable];
        for (int i = 0; i < defaultSizeOfTable; i++) {
            table[i] = new List();
        }
        size = 0;
    }

    HashTable(){
        rateDown = 0.4;
        // half sizeOfTable and call rehash() if ratio of size / sizeOfTable < rateDown
        rateUp = 2.1;
        // double sizeOfTable and call rehash() if ratio of size / sizeOfTable > rateUp
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

    public String remove(String key) {
        String res = table[getHashKey(key)].remove(key);
        if (res != null) {
            size--;
            if (size < sizeOfTable * rateDown) {
                rehash(sizeOfTable / 2);
            }
        }
        return res;
    }

    public void clear() {
        size = 0;
        table = new List[defaultSizeOfTable];
        for (int i = 0; i < defaultSizeOfTable; i++) {
            table[i] = new List();
        }
    }

    private int getHashKey(String key) {
        if (key == null){
            return 0;
        }
        return ((key.hashCode() % sizeOfTable) + sizeOfTable) % sizeOfTable;
    }


    public int getSizeOfTable() {
        return sizeOfTable;
    }

    private void rehash(int newSize) {
        if (newSize >= defaultSizeOfTable){
            List[] oldTable = table;
            table = new List[newSize];
            for (int i = 0; i < newSize; i++) {
                table[i] = new List();
            }
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

        }
    }
}

