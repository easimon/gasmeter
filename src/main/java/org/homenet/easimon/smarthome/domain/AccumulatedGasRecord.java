package org.homenet.easimon.smarthome.domain;

import java.util.Date;

public class AccumulatedGasRecord implements GasRecord {

    private long amount;
    private final Date timestamp;

    public AccumulatedGasRecord(Date start) {
        this.timestamp = start;
    }
    
    public void add(GasRecordEntity gasRecord) {
        this.amount += gasRecord.getAmount();
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public Date getTimestamp() {
        return timestamp;
    }

}
