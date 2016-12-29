package com.epam.processor;

import com.epam.concurrency.task.AccidentDataProcessor;
import com.epam.concurrency.task.AccidentDataWriter;
import com.epam.concurrency.task.RoadAccidentDetails;
import com.epam.data.AccidentsDataLoader;
import com.epam.data.RoadAccident;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
/**
 * Created by yangyi on 20/12/16.
 */
public class DataProcessIntegrationTest {
    private static final String FILE_PATH_1 = "src/main/resources/DfTRoadSafety_Accidents_2010.csv";
    private static AccidentDataProcessor dataProcessor;
    private static List<String> fileQueue = new ArrayList<String>();
    private static long startTimeMillis;
    private static long endTimeMillis;

    @BeforeClass
    public static void initialize() {
        startTimeMillis = System.currentTimeMillis();
        fileQueue.add(FILE_PATH_1);
        dataProcessor = new AccidentDataProcessor();
    }

    @Test
    public void produceTest() throws InterruptedException {
        dataProcessor.produce();
        assertThat(AccidentDataProcessor.getQueue().size(), is(154406));
    }

    @Test
    public void readAndWriteIntegrationTest() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dataProcessor.produce();
                } catch (InterruptedException e) {

                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dataProcessor.consume();
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
        }

        endTimeMillis = System.currentTimeMillis();
        long timeSlot = (endTimeMillis - startTimeMillis)/1000;
        System.out.println("timeSlot = "+ timeSlot);

    }

}
