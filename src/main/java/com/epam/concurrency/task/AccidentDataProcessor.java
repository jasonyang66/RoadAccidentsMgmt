package com.epam.concurrency.task;

import com.epam.data.RoadAccident;
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

    private static final String OUTPUT_FILE_PATH = "target/DfTRoadSafety_Accidents_consolidated.csv";

    private static final int DATA_PROCESSING_BATCH_SIZE = 10000;
    private static List<RoadAccidentDetails> roadAccidentDetailsList = new ArrayList<RoadAccidentDetails>();
    private static AccidentDataReader accidentDataReader = new AccidentDataReader();
    private static AccidentDataEnricher accidentDataEnricher = new AccidentDataEnricher();
    private static AccidentDataWriter accidentDataWriter = new AccidentDataWriter();
    private static BlockingQueue<RoadAccidentDetails> queue = new ArrayBlockingQueue<RoadAccidentDetails>(400000);

    private List<String> fileQueue = new ArrayList<String>();

    private static Logger log = LoggerFactory.getLogger(AccidentDataProcessor.class);
    public AccidentDataProcessor(){
        if(fileQueue.size() == 0) {
            init();
        }
    }

    public static BlockingQueue<RoadAccidentDetails> getQueue() {
        return queue;
    }

    public void init(){
        fileQueue.add(FILE_PATH_1);
        //fileQueue.add(FILE_PATH_2);
        //fileQueue.add(FILE_PATH_3);
        //fileQueue.add(FILE_PATH_4);

        accidentDataWriter.init(OUTPUT_FILE_PATH);
    }


    public static void produce() throws InterruptedException {
        accidentDataReader.init(DATA_PROCESSING_BATCH_SIZE, FILE_PATH_1);
        int batchCount = 1;
        while (!accidentDataReader.hasFinished()) {
            List<RoadAccident> roadAccidents = accidentDataReader.getNextBatch();
            log.info("Read [{}] records in batch [{}]", roadAccidents.size(), batchCount++);
            List<RoadAccidentDetails> roadAccidentDetailsList = accidentDataEnricher.enrichRoadAccidentData(roadAccidents);
            for(RoadAccidentDetails roadAccidentDetails:roadAccidentDetailsList) {
                queue.put(roadAccidentDetails);
            }
        }
        log.info("Produce end");

    }

    public static void consume() throws InterruptedException {

        do {
            RoadAccidentDetails details = queue.take();
            accidentDataWriter.writeAccidentData(details);

        } while (queue.size()>0);
        log.info("consume end..............");
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        new AccidentDataProcessor();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    produce();
                } catch (InterruptedException e) {

                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    consume();
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

        long end = System.currentTimeMillis();
        System.out.print("take time is "+ (end-start)/1000);

    }

}

