package com.epam.concurrency.task;

import com.epam.data.RoadAccident;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by yangyi on 10/1/17.
 */
public class AccidentDataEnricherTask implements Runnable{
    private BlockingQueue<RoadAccident> queue;
    private BlockingQueue<RoadAccidentDetails> detailsBlockingQueue;
    private final AccidentDataEnricher accidentDataEnricher = new AccidentDataEnricher();
    protected AccidentDataEnricherTask(BlockingQueue<RoadAccident> queue, BlockingQueue<RoadAccidentDetails> detailsBlockingQueue) {
        this.queue = queue;
        this.detailsBlockingQueue = detailsBlockingQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                RoadAccident details = queue.take();
                RoadAccidentDetails roadAccidentDetails = accidentDataEnricher.enrichRoadAccidentDataItem(details);
                detailsBlockingQueue.put(roadAccidentDetails);
            }
        }catch (InterruptedException e) {
            System.out.println("InterruptedException: "+e.getMessage());
        }catch (Exception e) {
            System.out.println("Exception : "+e.getMessage());
        }
        finally {
            return;
        }


    }
}
