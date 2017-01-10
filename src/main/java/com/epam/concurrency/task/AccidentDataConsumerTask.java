package com.epam.concurrency.task;

import java.util.concurrent.BlockingQueue;

/**
 * Created by yangyi on 10/1/17.
 */
public class AccidentDataConsumerTask implements Runnable {
    private BlockingQueue<RoadAccidentDetails> queue;
    private final AccidentDataWriter accidentDataWriter = new AccidentDataWriter();
    private static final String OUTPUT_FILE_PATH = "target/DfTRoadSafety_Accidents_consolidated.csv";
    public AccidentDataConsumerTask(BlockingQueue<RoadAccidentDetails> queue) {
        this.queue = queue;
        accidentDataWriter.init(OUTPUT_FILE_PATH);
    }

    @Override
    public void run() {
            try {
                while (true) {
                    RoadAccidentDetails details = queue.take();
                    accidentDataWriter.writeAccidentData(details);
                }
            }catch (InterruptedException e) {
                System.out.println("InterruptedException: "+e.getMessage());
            }catch (Exception e) {
                System.out.println("Exception : "+e.getMessage());
            }
            finally {

            }
        }
   }
