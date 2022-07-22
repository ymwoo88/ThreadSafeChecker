package com.example;

public class NumberCaller implements Runnable {

    @Override
    public void run() {
        try {
            RandomNumber.getRandomInt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
