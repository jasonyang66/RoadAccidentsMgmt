package com.epam.concurrency.task;

import com.epam.data.RoadAccident;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private AccidentDataReader accidentDataReader = new AccidentDataReader();
    private AccidentDataEnricher accidentDataEnricher = new AccidentDataEnricher();
    private AccidentDataWriter accidentDataWriter = new AccidentDataWriter();

    private ExecutorService executorService = Executors.newFixedThreadPool(4);

    private List<String> fileQueue = new ArrayList<String>();

    private Logger log = LoggerFactory.getLogger(AccidentDataProcessor.class);


    public void init(){
        fileQueue.add(FILE_PATH_1);
        fileQueue.add(FILE_PATH_2);
        fileQueue.add(FILE_PATH_3);
        fileQueue.add(FILE_PATH_4);

        accidentDataWriter.init(OUTPUT_FILE_PATH);
    }

    public void process(){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                for (String accidentDataFile : fileQueue){
                    log.info("Starting to process {} file ", accidentDataFile);
                    accidentDataReader.init(DATA_PROCESSING_BATCH_SIZE, accidentDataFile);
                    processFile();
                }
            }
        });
        executorService.shutdown();
    }



    private void processFile(){
        int batchCount = 1;
        while (!accidentDataReader.hasFinished()){
            List<RoadAccident> roadAccidents = accidentDataReader.getNextBatch();
            log.info("Read [{}] records in batch [{}]", roadAccidents.size(), batchCount++);
            List<RoadAccidentDetails> roadAccidentDetailsList = accidentDataEnricher.enrichRoadAccidentData(roadAccidents);
            log.info("Enriched records");
            accidentDataWriter.writeAccidentData(roadAccidentDetailsList);
            log.info("Written records");
        }
    }


    public static void main(String[] args) {
        AccidentDataProcessor dataProcessor = new AccidentDataProcessor();
        long start = System.currentTimeMillis();
        dataProcessor.init();
        dataProcessor.process();
        long end = System.currentTimeMillis();
        System.out.println("Process finished in s : " + (end-start)/1000);
    }

}
