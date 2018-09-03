package com.udr013.Executers;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;

public class ExecutorRunnableExample {

    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        hotel.execute(new Order("tea"));
        hotel.execute(new Order("coffee"));
        hotel.execute(new Order("burger"));
    }
}

class Order implements Runnable { //so override run()

    String name;

    Order(String name) {
        this.name = name;
    }

    @Override
    public void run() { //do something
        System.out.println(name);
    }
}

class Hotel implements Executor { //so override execute
    final Queue<Runnable> customerDeque = new ArrayDeque<>();

    @Override
    public void execute(Runnable command) {
        synchronized (customerDeque) {
            customerDeque.offer(command); //add runnable object (Order) to queue
        }
        processEarliestOrder();
    }

    private void processEarliestOrder() {
        synchronized (customerDeque) {
            Runnable task = customerDeque.poll();
            new Thread(task).start(); //starts it in new thread
//            task.run(); //runs in the same thread, as method run waits for method run the result will be the same

        }
    }

}