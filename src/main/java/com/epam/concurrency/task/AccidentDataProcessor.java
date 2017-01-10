package com.epam.concurrency.task;

import com.epam.data.RoadAccident;
import org.hibernate.annotations.Synchronize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.Date;

/**
 * Created by Tanmoy on 6/17/2016.
 */
public class AccidentDataProcessor {

    private static final String FILE_PATH_1 = "src/main/resources/DfTRoadSafety_Accidents_2010.csv";
    private static final String FILE_PATH_2 = "src/main/resources/DfTRoadSafety_Accidents_2011.csv";
    private static final String FILE_PATH_3 = "src/main/resources/DfTRoadSafety_Accidents_2012.csv";
    private static final String FILE_PATH_4 = "src/main/resources/DfTRoadSafety_Accidents_2013.csv";


    private final BlockingQueue<RoadAccidentDetails> blockingQueue = new ArrayBlockingQueue<RoadAccidentDetails>(1);

    private List<String> fileQueue = new ArrayList<String>();

    private static Logger log = LoggerFactory.getLogger(AccidentDataProcessor.class);
    public AccidentDataProcessor(){
        if(fileQueue.size() == 0) {
            init();
        }
    }


    public void init(){
        fileQueue.add(FILE_PATH_1);
        //fileQueue.add(FILE_PATH_2);
        //fileQueue.add(FILE_PATH_3);
        //fileQueue.add(FILE_PATH_4);


    }



    public void executeReadAndWrite() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        AccidentDataProducer producer = new AccidentDataProducer(blockingQueue);
        AccidentDataConsumer consumer = new AccidentDataConsumer(blockingQueue);
        FutureTask<Boolean> future = new FutureTask<Boolean>(producer);

        executorService.submit(future);
        executorService.execute(consumer);
        boolean bool = future.get();

        executorService.shutdown();


    }

}

