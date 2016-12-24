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
public class DataProcessConcurrencyTest {
    private static final String FILE_PATH_1 = "src/main/resources/DfTRoadSafety_Accidents_2009.csv";
    private static AccidentDataProcessor dataProcessor;
    private static List<String> fileQueue = new ArrayList<String>();
    @BeforeClass
    public static void initialize() {
        fileQueue.add(FILE_PATH_1);
        dataProcessor = new AccidentDataProcessor();
    }

    @Test
    public void producerTest() throws InterruptedException {
        dataProcessor.produce();
        assertThat(AccidentDataProcessor.getQueue().size(), is(62224));
    }



}
