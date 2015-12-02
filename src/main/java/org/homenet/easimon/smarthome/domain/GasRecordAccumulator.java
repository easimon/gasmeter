package org.homenet.easimon.smarthome.domain;

import java.util.ArrayList;

import org.joda.time.DateTime;

public class GasRecordAccumulator {

    private ArrayList<AccumulatedGasRecord> records;

    private DateTime start;
    private long interval;

    private GasRecordAccumulator(DateTime start, long interval) {
        this.start = start;
        this.interval = interval;
        this.records = new ArrayList<AccumulatedGasRecord>();
    }

    private GasRecord getOrExtendFor(DateTime date) {
        if (date.isBefore(start)) {
            throw new IllegalArgumentException();
        }

        long diff = date.getMillis() - start.getMillis();
        int index = (int) (diff / interval);

        if (records.size() <= index) {
            records.ensureCapacity(index + 1);
            for (int i = records.size(); i < index; i++) {
                DateTime startForIndex = start.withDurationAdded((index * interval), 1);
                // records.add(new AccumulatedGasRecord(startForIndex));
            }
        }
        return records.get(index);
    }

    public static GasRecordAccumulator createAccumulator(DateTime start, long interval) {
        GasRecordAccumulator accumulator = new GasRecordAccumulator(start, interval);
        return accumulator;
    }

}
