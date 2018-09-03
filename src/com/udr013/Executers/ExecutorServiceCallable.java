package com.udr013.Executers;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceCallable {

    public static void main(String[] args) {
        Motel motel = new Motel();
        FoodOrder callable1 = new FoodOrder("fish");
        FoodOrder callable2 = new FoodOrder("steak");
        FoodOrder callable3 = new FoodOrder("burger");

        motel.orderFood(callable1);
        motel.orderFood(callable2);
        motel.hotelClosedForever();
        motel.orderFood(callable3);
        motel.hotelClosedForADay();
    }
}

class FoodOrder implements Callable<Void> { //so override call()

    String name;

    FoodOrder(String name) {
        this.name = name;
    }

    @Override
    public Void call() throws Exception { //do something throws exception is not explicit
        Thread.sleep(1100);
        System.out.println(name);
        if(name.equals("berry")){
            throw new Exception("berry unavailable");
        }
        return null;
    }
}

class Motel {
   ExecutorService service = Executors.newFixedThreadPool(5);

   public void orderFood(FoodOrder order){
       service.submit(order);
   }

   public void hotelClosedForADay(){
       service.shutdown();
   }
   public void hotelClosedForever(){
       service.shutdown();
       try {
           if(!service.awaitTermination(60, TimeUnit.MICROSECONDS)){ // InterruptedException
               service.shutdownNow();
               if(!service.awaitTermination(60, TimeUnit.SECONDS)){
                   System.err.println("Pool didn't terminate");
               }
           }
       } catch (InterruptedException i){
           service.shutdownNow();
           Thread.currentThread().interrupt();
       }
   }



}