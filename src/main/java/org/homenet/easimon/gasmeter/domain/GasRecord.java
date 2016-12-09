package org.homenet.easimon.gasmeter.domain;

import java.time.Instant;

public interface GasRecord {

	public long getAmount();

	public Instant getTimestamp();

	public GasRecordType getType();

}
