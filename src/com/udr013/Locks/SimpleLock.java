package com.udr013.Locks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleLock {

    Lock myLock = new ReentrantLock();  //lock object

    static List<String> colors = new ArrayList<>();

    public void addColor(String color){
        myLock.lock(); // acquire lock, wait if not available
        try{
            colors.add(color);
        }
        finally {
            myLock.unlock(); //call unlock to release lock
        }
    }
}
