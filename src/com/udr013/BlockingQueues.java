package com.udr013;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueues {

    public static void main(String[] args) {
	BlockingQueue<Request> queue = new ArrayBlockingQueue<Request>(3);

	Client client = new Client(queue);
	Server server = new Server(queue);

	new Thread(client).start();
	new Thread(server).start();
    }
}

class Request{}

class Client implements Runnable{

    private BlockingQueue <Request> queue;

    Client(BlockingQueue<Request> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            Request request = null;
            while(true){
                request = new Request();
                System.out.println(queue.remainingCapacity());
                queue.put(request); //InterruptedException
                System.out.println("added new request: " +request);
            }
        } catch (InterruptedException ex){
            System.out.println(ex);
        }
    }
}

class Server implements Runnable{
    private BlockingQueue<Request> requestsQueue;

    public Server(BlockingQueue<Request> requestsQueue) {
        this.requestsQueue = requestsQueue;
    }

    @Override
    public void run() {
        try{
            while (true){
                if(requestsQueue.remainingCapacity() == 0);{
                    System.out.println("waiting..");
                }
                System.out.println("processing :" +requestsQueue.take()); //InterruptedException
            }
        } catch (InterruptedException ie){
            System.out.println(ie);
        }
    }
}