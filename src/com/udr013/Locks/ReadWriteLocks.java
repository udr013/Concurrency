package com.udr013.Locks;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLocks {

    public static void main(String[] args) {

    }
}

class Rainbow {
    private final static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static int position;
    static Map<Integer, String> colors = new HashMap<>();

    public static void addColors(String color) {
        readWriteLock.writeLock().lock();
        try {
            colors.put(position++, color); //remember Map doesn't have add
        }finally {
            readWriteLock.writeLock().unlock(); //notice we need to tell which version of the lock we want to unlock
        }
    }

    public void display() {
        readWriteLock.readLock().lock();
        try{
            for(String s : colors.values()){
                System.out.println(s);
            }
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}