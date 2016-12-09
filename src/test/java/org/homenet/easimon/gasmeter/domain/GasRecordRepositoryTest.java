package org.homenet.easimon.gasmeter.domain;

import static org.junit.Assert.*;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.homenet.easimon.gasmeter.domain.GasRecord;
import org.homenet.easimon.gasmeter.domain.GasRecordRepository;
import org.homenet.easimon.gasmeter.spring.SpringBasedIntegrationTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class GasRecordRepositoryTest extends SpringBasedIntegrationTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(GasRecordRepositoryTest.class);

	@Autowired
	private GasRecordRepository repository;

	@Test
	public void testFindAll() {
		List<? extends GasRecord> records = repository.findAllGasRecords();
		assertFalse(records.isEmpty());
		LOGGER.info("{} Records found.", records.size());
	}

	@Test
	public void testFindByPeriod() {
		Instant now = OffsetDateTime.parse("2015-02-01T00:00:00+02:00").toInstant();
		Instant dayAgo = now.minus(1, ChronoUnit.DAYS);
		List<? extends GasRecord> records = repository.findGasRecordsByPeriod(dayAgo, now);
		assertFalse(records.isEmpty());
		for (GasRecord record : records) {
			assertFalse(record.getTimestamp().isBefore(dayAgo)); // >= dayAgo
			assertTrue(record.getTimestamp().isBefore(now)); // < now
		}
		LOGGER.info("{} Records found.", records.size());
	}

}
