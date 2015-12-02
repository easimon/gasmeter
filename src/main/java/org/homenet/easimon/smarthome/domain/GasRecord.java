package org.homenet.easimon.smarthome.domain;

import org.joda.time.DateTime;

public interface GasRecord {

    public long getAmount();

    public DateTime getTimestamp();

}
