package org.homenet.easimon.smarthome.domain;

import org.joda.time.DateTime;

public class AccumulatedGasRecord implements GasRecord {

    private long amount;
    private final DateTime timestamp;

    public AccumulatedGasRecord(DateTime start) {
        this.timestamp = start;
        this.amount = 0;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public DateTime getTimestamp() {
        return timestamp;
    }

}
