package com.epam.concurrency.task;

import com.epam.data.RoadAccident;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

/**
 * Created by yangyi on 10/1/17.
 */
public class AccidentDataProducer implements Callable<Boolean>{
    private BlockingQueue<RoadAccidentDetails> queue;
    private final AccidentDataReader accidentDataReader = new AccidentDataReader();
    private final AccidentDataEnricher accidentDataEnricher = new AccidentDataEnricher();
    private final String FILE_PATH_1 = "src/main/resources/DfTRoadSafety_Accidents_2010.csv";
    private final int DATA_PROCESSING_BATCH_SIZE = 10000;
    public AccidentDataProducer(BlockingQueue<RoadAccidentDetails> queue) {
        this.queue = queue;
        accidentDataReader.init(DATA_PROCESSING_BATCH_SIZE, FILE_PATH_1);
    }
    @Override
    public Boolean call() throws Exception {

        int batchCount = 1;
        while (!accidentDataReader.hasFinished()) {
            List<RoadAccident> roadAccidents = accidentDataReader.getNextBatch();

            List<RoadAccidentDetails> roadAccidentDetailsList = accidentDataEnricher.enrichRoadAccidentData(roadAccidents);
            for (RoadAccidentDetails roadAccidentDetails : roadAccidentDetailsList) {
                queue.put(roadAccidentDetails);
            }
        }

        return Boolean.TRUE;
    }
}
