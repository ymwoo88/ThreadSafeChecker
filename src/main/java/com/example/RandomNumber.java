package com.example;

import java.util.Random;

public class RandomNumber {

    public static void getRandomInt() throws InterruptedException {

        final int randomInt = new Random().nextInt(1000);

        System.out.println("슬립 전 ThreadID[" + Thread.currentThread().getId() + "] result >> " + randomInt);
        Thread.sleep(3000);
        System.out.println("슬립 후 ThreadID[" + Thread.currentThread().getId() + "] result >> " + randomInt);

    }
}
