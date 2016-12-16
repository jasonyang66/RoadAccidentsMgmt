package com.epam.concurrency.ppt.threadsafety;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by yangyi on 7/12/16.
 */
public class ProviderAndConsumer1 {

    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(20);

    public static void main(String[] args) {
        ProviderAndConsumer1 waitNotify = new ProviderAndConsumer1();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    provide();// waitNotify.waitThread();
                } catch (InterruptedException e) {

                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                  consume(); //waitNotify.notifyThread();
                } catch (InterruptedException e) {

                }
            }
        });

        t1.start();
        t2.start();


        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void provide() throws InterruptedException {
        Random random = new Random();

        while(queue.size() < 21) {
            Thread.sleep(100);
            queue.put(random.nextInt(30));
            System.out.println("queue in, the size is : " + queue.size());

        }
    }

    public static void consume() throws InterruptedException {
        Random random = new Random();
        while (true) {


            Integer value = queue.take();
            if(queue.size()==0) {
                break;
            }
           // Integer value2 = queue.take();
            System.out.println("Integer value: "+value+"the size of queue is "+queue.size());
//            if(random.nextInt(10) == 3 || random.nextInt() == 6) {
//                Integer value1 = queue.take();
//                Integer value2 = queue.take();
//                System.out.println("Integer value1: "+value1+" value2: "+value2+"the size of queue is "+queue.size());
//            }
//            if(queue.size()>0) {
//                Integer value = queue.take();
//                System.out.println("Integer value: "+value+"the size of queue is "+queue.size());
//            } else {
//                break;
//            }
        }
    }

    public  void waitThread() throws InterruptedException {
       synchronized (this) {
           System.out.println("I am first one to come in");
           wait();
           System.out.println("I am resumed");
       }
    }

    public  void notifyThread() throws InterruptedException {
        synchronized (this) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("I am send one to come in");
            scanner.nextLine();
            System.out.println("return to first one to resume");
            notify();
        }
    }
}