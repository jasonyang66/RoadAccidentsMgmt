package com.epam.processor;

import com.epam.concurrency.task.AccidentDataProcessor;
import com.epam.concurrency.task.AccidentDataReader;
import com.epam.data.RoadAccident;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.junit.Assert.assertThat;

/**
 * Created by yangyi on 24/12/16.
 */
public class RoadAccidentReaderTest {
    private static final String FILE_PATH_1 = "src/main/resources/DfTRoadSafety_Accidents_2010.csv";

    private static AccidentDataReader dataReader;

    private static Iterator<CSVRecord> recordIterator;

    @BeforeClass
    public  static void initialize() throws FileNotFoundException, IOException {
        dataReader = new AccidentDataReader();
        dataReader.init(10000,FILE_PATH_1);
    }

    @Test
    public void testBatchNext(){
        List<RoadAccident> list = dataReader.getNextBatch();
        assertThat(list.size(), is(10000));
    }

    @Test
    public void testTotalNumOfFileWithResult() {
        int loopCount = 3;
        List<RoadAccident> totalList = new ArrayList<>();
        int i = 0;
        do {
            List<RoadAccident> batchList = dataReader.getNextBatch();
            totalList.addAll(batchList);
            i++;
        }while(i<loopCount);
        assertThat(totalList.size(), is(30000));

    }

    @Test
    public void testFirstAndLastRowData() {
        int loopCount = 3;
        List<RoadAccident> totalList = new ArrayList<>();
        int i = 0;
        do {
            List<RoadAccident> batchList = dataReader.getNextBatch();
            totalList.addAll(batchList);
            i++;
        }while(i<loopCount);
        assertThat(totalList, Matchers.hasSize(30000));
        RoadAccident accidentFirst = totalList.get(0);
        assertThat(accidentFirst.getAccidentId(), equalTo("201001BS70003"));
        RoadAccident accidentLast = totalList.get(totalList.size() - 1);
        assertThat(accidentLast.getAccidentId(), equalTo("201005AA02484"));

    }

    @Test
    public void testLastRowDataInFile() {
        int loopCount = 7;
        List<RoadAccident> totalList = new ArrayList<>();
        int i = 0;
        do {
            List<RoadAccident> batchList = dataReader.getNextBatch();
            totalList.addAll(batchList);
            i++;
        }while(i<loopCount);
        assertThat(totalList, Matchers.hasSize(70000)); //should identity the number from excel
        RoadAccident accidentFirst = totalList.get(0);
        assertThat(accidentFirst.getAccidentId(), equalTo("201001BS70003"));
        RoadAccident accidentLast = totalList.get(totalList.size() - 1);
        assertThat(accidentLast.getAccidentId(), equalTo("2010160D04551"));
    }

}
