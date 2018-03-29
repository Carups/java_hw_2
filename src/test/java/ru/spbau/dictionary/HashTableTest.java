package ru.spbau.dictionary;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;
class RandomGenString {
    private final char[] alphabet = {'a', 'A', 'b', 'B', 'c', 'C',
            'd', 'D', 'e', 'E', 'f', 'F', 'g', 'G', 'h', 'H', 'i', 'I',
            'j', 'J', 'k', 'K', 'l', 'L', 'm', 'M', 'n', 'N', 'o', 'O',
            'p', 'P', 'q', 'Q', 'r', 'R', 's', 'S', 't', 'T', 'u', 'U',
            'v', 'V', 'w', 'W', 'x', 'X', 'y', 'Y', 'z', 'Z'};
    private final Random random;

    public String getRandomString(int length) {
        char[] buf = new char[length];
        for (int idx = 0; idx < length; idx++) {
            buf[idx] = alphabet[random.nextInt(alphabet.length)];
        }
        return new String(buf);
    }

    RandomGenString()
    {
        random = new Random();
    }
}

public class HashTableTest {
    String[] keys, values;
    int sizeOfSample;
    HashTable hashTable;
    RandomGenString rgs;

    void initiate(int size)
    {
        sizeOfSample = size;
        keys = new String[sizeOfSample];
        values = new String[sizeOfSample];
        hashTable = new HashTable(0.5, 2);
        rgs = new RandomGenString();
        for (int i = 0; i < sizeOfSample; i++) {
            keys[i] = rgs.getRandomString(10);
            values[i] = rgs.getRandomString(10);
        }
    }

    @Test
    public void testBaseActivityWithoutRehash() {
        initiate(1024);
        for (int i = 0; i < sizeOfSample; i++) {
            assertTrue(hashTable.put(keys[i], values[i]) == null);
        }
        assertTrue(hashTable.size() == sizeOfSample);
        for (int i = 0; i < sizeOfSample; i++) {
            assertTrue(hashTable.contains(keys[i]));
            assertTrue(hashTable.get(keys[i]).equals(values[i]));
        }
        for (int i = 0; i < sizeOfSample; i++) {
            assertTrue(hashTable.remove(keys[i]).equals(values[i]));
        }
        assertTrue(hashTable.size() == 0);
        for (int i = 0; i < sizeOfSample; i++) {
            assertFalse(hashTable.contains(keys[i]));
        }
        for (int i = 0; i < sizeOfSample; i++) {
            assertTrue(hashTable.remove(keys[i]) == null);
        }
    }

    @Test
    public void testBaseActivityWithRehash() {
        initiate(10240);
        for (int i = 0; i < sizeOfSample; i++) {
            assertTrue(hashTable.put(keys[i], values[i]) == null);
        }
        assertTrue(hashTable.size() == sizeOfSample);
        assertTrue(hashTable.getSizeOfTable() != 1024);
        for (int i = 0; i < sizeOfSample; i++) {
            assertTrue(hashTable.contains(keys[i]));
            assertTrue(hashTable.get(keys[i]).equals(values[i]));
        }
        for (int i = 0; i < sizeOfSample; i++) {
            assertTrue(hashTable.remove(keys[i]).equals(values[i]));
        }
        assertTrue(hashTable.size() == 0);
        assertTrue(hashTable.getSizeOfTable() == 1024);
        for (int i = 0; i < sizeOfSample; i++) {
            assertFalse(hashTable.contains(keys[i]));
        }
        for (int i = 0; i < sizeOfSample; i++) {
            assertTrue(hashTable.remove(keys[i]) == null);
        }
    }

    @Test
    public void testClear(){
        initiate(2048);
        for (int i = 0; i < sizeOfSample; i++) {
            assertTrue(hashTable.put(keys[i], values[i]) == null);
        }
        assertFalse(hashTable.size() == 0);
        hashTable.clear();
        assertTrue(hashTable.size() == 0);
        for (int i = 0; i < sizeOfSample; i++) {
            assertFalse(hashTable.contains(values[i]));
        }
        for (int i = 0; i < sizeOfSample; i++) {
            assertTrue(hashTable.put(keys[i], values[i]) == null);
        }
        assertFalse(hashTable.size() == 0);
        hashTable.clear();
        assertTrue(hashTable.size() == 0);
        for (int i = 0; i < sizeOfSample; i++) {
            assertFalse(hashTable.contains(values[i]));
        }

    }

    @Test
    public void testPutWithSameKey(){
        initiate(8096);
        String[] newValues = new String[sizeOfSample];
        for (int i = 0; i < sizeOfSample; ++i){
            newValues[i] = rgs.getRandomString(11);
        }
        for (int i = 0; i < sizeOfSample; i++) {
            assertTrue(hashTable.put(keys[i], values[i]) == null);
        }
        for (int i = 0; i < sizeOfSample; i++) {
            assertTrue(hashTable.put(keys[i], newValues[i]).equals(values[i]));
        }
        for (int i = 0; i < sizeOfSample; i++) {
            assertFalse(hashTable.get(keys[i]).equals(values[i]));
            assertTrue(hashTable.get(keys[i]).equals(newValues[i]));
        }
    }
}