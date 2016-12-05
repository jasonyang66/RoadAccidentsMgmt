package com.epam.concurrency.ppt.threadsafety;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Created by yangyi on 5/12/16.
 */
public class FixedThreadPoolUse {

    /**
     * Created by yangyi on 5/12/16.
     */
    public static void main(String[] args) {
        System.out.println("main thread starts here......");
        ExecutorService exeService = Executors.newFixedThreadPool(3);
        exeService.execute(new SimpleThread());
        exeService.execute(new SimpleThread());
        exeService.execute(new SimpleThread());
        exeService.execute(new SimpleThread());
        exeService.execute(new SimpleThread());
        exeService.shutdown();
        System.out.println("main thread ends here......");

    }


}
