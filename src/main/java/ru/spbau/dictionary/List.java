package ru.spbau.dictionary;


public class List {
    static private class Node{
        String key, value;
        Node next;

        Node(String key, String value) {
            this.key = key;
            this.value = value;
            next = null;
        }

    }
    private int size, version;
    private Node head, tail;

    {
        version = 0;
        size = 0;
        head = null;
        tail = null;
    }

    public int getVersion()
    {
        return version;
    }
    public void setVersion(int version)
    {
        this.version = version;
    }

    public void add(String key, String value) {
        Node addNode = new Node(key, value);
        if (tail == null) {
            tail = addNode;
            head = addNode;
        }
        else {
            tail.next = addNode;
            tail = addNode;
        }
        size++;
    }


    public String get(String key) {
        Node cur = head;
        try {
            while (!cur.key.equals(key)) {
                cur = cur.next;
            }
        }
        catch (Exception e){
            return null;
        }
        return cur.value;
    }
    public String set(String key, String value) {
        Node cur = head;
        try {
            while (!cur.key.equals(key)) {
                cur = cur.next;
            }
        }
        catch (Exception e){
            return null;
        }
        String ans = cur.value;
        cur.value = value;
        return ans;
    }

    public String put(String key, String value) {
        String res = set(key, value);
        if (res == null) {
            add(key, value);
        }
        return res;
    }

    //public int size() {
    //    return size;
    //}

    public String remove(String key) {
        String ans;
        try {
            if (head.key.equals(key)) {
                ans = head.value;
                head = head.next;
                size--;
            }
            else{
                Node cur = head;
                while(!cur.next.key.equals(key)) {
                    cur = cur.next;
                }
                ans = cur.next.value;
                cur.next = cur.next.next;
                size--;
            }
        }
        catch (Exception e){
            return null;
        }
        return ans;
    }
    public String[] getKeys()
    {
        Node cur = head;
        String[] ans = new String[size];
        for (int i = 0; i < size; i++) {
            ans[i] = cur.key;
            cur = cur.next;
        }
        return ans;
    }
    public String[] getValues()
    {
        Node cur = head;
        String[] ans = new String[size];
        for (int i = 0; i < size; i++) {
            ans[i] = cur.value;
            cur = cur.next;
        }
        return ans;
    }
    public boolean contains(String key){
        Node cur = head;
        for (int i = 0; i < size; i++) {
            if (cur.key.equals(key)){
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

}
