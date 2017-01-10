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
    private final BlockingQueue<RoadAccident> roadAccidentQueue  = new ArrayBlockingQueue<RoadAccident>(1);
    private final BlockingQueue<RoadAccidentDetails> roadAccidentDtlsQueue = new ArrayBlockingQueue<RoadAccidentDetails>(1);

    private List<String> fileList = new ArrayList<String>();

    private static Logger log = LoggerFactory.getLogger(AccidentDataProcessor.class);
    public AccidentDataProcessor(){
        if(fileList.size() == 0) {
            init();
        }
    }


    public void init(){
        fileList.add(FILE_PATH_1);
        fileList.add(FILE_PATH_2);
        fileList.add(FILE_PATH_3);
        fileList.add(FILE_PATH_4);


    }



    public void executeReadAndWrite() throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        AccidentDataProducerTask producer = new AccidentDataProducerTask(roadAccidentQueue, fileList);
        AccidentDataEnricherTask enricher = new AccidentDataEnricherTask(roadAccidentQueue, roadAccidentDtlsQueue);
        AccidentDataConsumerTask consumer = new AccidentDataConsumerTask(roadAccidentDtlsQueue);
        FutureTask<Boolean> future = new FutureTask<Boolean>(producer);

        executorService.submit(future);
        executorService.execute(enricher);
        executorService.execute(consumer);
        boolean bool = future.get();

        executorService.shutdown();


    }

}

