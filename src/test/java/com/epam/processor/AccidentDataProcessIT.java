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
import java.util.concurrent.ExecutionException;


import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
/**
 * Created by yangyi on 20/12/16.
 */
public class AccidentDataProcessIT {

    private final AccidentDataProcessor processor = new AccidentDataProcessor();

    @Test
    public void readAndWriteTest() throws ExecutionException, InterruptedException {
        processor.executeReadAndWrite();
    }

}
