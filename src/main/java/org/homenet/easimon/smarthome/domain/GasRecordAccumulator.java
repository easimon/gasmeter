package org.homenet.easimon.smarthome.domain;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class GasRecordAccumulator {

    private ArrayList<AccumulatedGasRecord> records;

    private Date start;
    private long interval;

    private GasRecordAccumulator(Date start, long interval) {
        this.start = new Date(start.getTime());
        this.interval = interval;
        this.records = new ArrayList<AccumulatedGasRecord>();
    }

    private GasRecord getOrExtendFor(Date date) {
        if (date.before(start)) {
            throw new IllegalArgumentException();
        }
        
        long diff = date.getTime() - start.getTime();
        int index = (int) (diff / interval);

        if (records.size() <= index) {
            records.ensureCapacity(index + 1);
            for (int i = records.size(); i < index; i++) {
                Date startForIndex = DateUtils.addSeconds(start, (int) (index * interval));
                records.add(new AccumulatedGasRecord(startForIndex));
            }
        }
        return records.get(index);
    }

    public static GasRecordAccumulator createAccumulator(Date start, long interval) {
        GasRecordAccumulator accumulator = new GasRecordAccumulator(start, interval);
        return accumulator;
    }

}
