package com.epam.concurrency.task;

import com.epam.data.RoadAccident;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

/**
 * Created by yangyi on 10/1/17.
 */
public class AccidentDataProducerTask implements Callable<Boolean>{
    private BlockingQueue<RoadAccident> queue;
    private List<String> fileList;
    private final AccidentDataReader accidentDataReader = new AccidentDataReader();
    private final int DATA_PROCESSING_BATCH_SIZE = 10000;

    public AccidentDataProducerTask(BlockingQueue<RoadAccident> queue, List<String> fileList) {
        this.queue = queue;
        this.fileList = fileList;

    }
    @Override
    public Boolean call() throws Exception {
        for(String file : this.fileList) {
            accidentDataReader.init(DATA_PROCESSING_BATCH_SIZE, file);
            readFileAndPutDataToQueue();
        }
        return Boolean.TRUE;
    }

    private void readFileAndPutDataToQueue() throws InterruptedException {
        while (!accidentDataReader.hasFinished()) {
            List<RoadAccident> roadAccidents = accidentDataReader.getNextBatch();

            for (RoadAccident roadAccidentDetails : roadAccidents) {
                queue.put(roadAccidentDetails);
            }
        }
    }


}
