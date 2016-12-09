package org.homenet.easimon.smarthome.domain;

import java.time.Instant;

public interface GasRecord {

	public long getAmount();

	public Instant getTimestamp();

	public GasRecordType getType();

}
