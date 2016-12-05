package com.epam.concurrency.ppt.threadsafety;

import java.util.concurrent.TimeUnit;

/**
 * Created by yangyi on 5/12/16.
 */
public class SimpleThread implements Runnable {
    private static int count =0;
    private int id;
    public static void main(String[] args) {

        System.out.println("main thread start");
        new Thread(new SimpleThread()).start();
        new Thread(new SimpleThread()).start();
        System.out.println("main thread end");
    }

    @Override
    public void run() {

        System.out.println("#### <task-"+id+"> starting #####");
        for(int i=0;i<10;i++) {
            System.out.println("thread id is " + this.id +" print i "+i);

            try {
                TimeUnit.MILLISECONDS.sleep((long)(Math.random()*1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("#### <task-"+id+"> done #####");

    }

    public SimpleThread() {
        id = ++count;
        //this.start();
        //new Thread(this).start();
    }
}




