package com.udr013.Locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ImplicitLocks {

    public static void main(String[] args) {
        Inventory loc1 = new Inventory("Shop1");
        Inventory loc2 = new Inventory("Shop2");
        loc1.inStock = 100;
        loc2.inStock = 100;

        Shipment s1 = new Shipment(loc1, loc2, 12);
        Shipment s2 = new Shipment(loc1, loc2, 6);
        s1.start(); // remember order execution not guarantied
        s2.start(); // ever....
    }
}

class Shipment extends Thread {

    Inventory loc1, loc2;
    int quantity;

    Shipment(Inventory loc1, Inventory loc2, int quantity) {
        this.loc1 = loc1;
        this.loc2 = loc2;
        this.quantity = quantity;
    }

    /**implicit lock*/
//    @Override
//    public void run() {
//        synchronized (loc1){ //acquire lock on loc1  depending on the underlying schedular , this might deadlock..??
//            synchronized (loc2){ //acquire lock on loc2
//                loc1.stockOut(quantity);
//                loc2.stockIn(quantity);
//                System.out.println("loc1: " + loc1.inStock + "| loc2: " + loc2.inStock);
//            } //release lock on loc2
//        } //release lock on loc1
//    }

    /**
     * explicit lock
     */
    @Override
    public void run() {
        if (loc1.lock.tryLock()) {    // notice the  lockObject is lock of  class Inventory
            if (loc2.lock.tryLock()) {  //tries  acquire lock on  loc2.lock and returns immediately
                loc1.stockOut(quantity);
                loc2.stockIn(quantity);
                System.out.println("loc1: " + loc1.inStock + "| loc2: " + loc2.inStock);
                loc2.lock.unlock();
                loc1.lock.unlock();
            } else {
                System.out.println("Locking false:  " + loc2.name);
            }
        } else {
            System.out.println("Locking false:  " + loc1.name);
        }
    }
}

class Inventory {
    Lock lock = new ReentrantLock();
    int inStock;
    String name;

    Inventory(String name) {
        this.name = name;
    }

    public void stockIn(long quantity) {
        inStock += quantity;
    }

    public void stockOut(long quantity) {
        inStock -= quantity;
    }
}