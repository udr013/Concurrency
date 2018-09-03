package com.udr013.Executers;

import java.util.concurrent.*;

public class ScheduledExecutorServiceExample {

    public static void main(String[] args) {

        ReminderMessenger reminderMessenger = new ReminderMessenger();
        reminderMessenger.sendReminder();
    }
}

class Reminder implements Runnable {

    @Override
    public void run() {
        //send reminder emails
        System.out.println("All mail send");
    }
}

class ReminderMessenger {

    ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
     Reminder reminder = new Reminder(); //task

     public void sendReminder(){
         ScheduledFuture<?> scheduledFuture = service.scheduleWithFixedDelay(reminder, 0, 2000, TimeUnit.MILLISECONDS);
//         ScheduledFuture<?> scheduledFuture = service.scheduleAtFixedRate(reminder, 0, 2000, TimeUnit.MILLISECONDS);




     }

}

