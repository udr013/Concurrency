package com.udr013.Locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class InterruptableLocks {

    public static void main(String[] args) {
        Bus bus = new Bus();
        Passenger passenger1 = new Passenger("jane", bus);
        Passenger passenger2 = new Passenger("Jim", bus);

        passenger1.start(); //passenger extends Thread..
        passenger1.interrupt();
        passenger2.start();
    }

}

class Passenger extends Thread{
    Bus bus;
    String name;

    Passenger(String name, Bus bus){
        this.name  = name;
        this.bus = bus;
    }

    public void run(){
        try{
            bus.lock.lockInterruptibly(); // throws InterruptedException
            try {
                bus.boardBus(name);
            } finally { //though not really needed, bus.boardBus(name) should not throw an exception... good practice
                bus.lock.unlock(); //no exception here, no catch for this needed
            }
        } catch (InterruptedException i){
            System.out.println( name + ": Interrupted!" + Thread.currentThread().isInterrupted());
        }
//        finally {  // can't unlock what's not locked!, IllegalMonitorStateException
//            bus.lock.unlock();
//        }
    }
}

class Bus{
    Lock lock = new ReentrantLock();

    public void boardBus(String name){
        System.out.println(name  + " :boarded bus");
    }
}