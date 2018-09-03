package com.udr013;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicVariables {
}

class Album {  //nonatomic
    String title;
    int copiesSold = 0;


    Album(String title){
        this.title = title;
    }

    public void newSale(){
        copiesSold++;
    }

    public void returnedSale(){
        copiesSold--;
    }

}

class AtomicAlbum {
    String title;
    AtomicInteger copiesSold = new AtomicInteger(0); // Atomic!


    AtomicAlbum(String title){
        this.title = title;
    }

    public void newSale(){
        copiesSold.incrementAndGet();
    }

    public void returnedSale(){
        copiesSold.decrementAndGet();
    }

}
