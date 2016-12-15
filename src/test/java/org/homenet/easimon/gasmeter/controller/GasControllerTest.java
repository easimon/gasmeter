package org.homenet.easimon.gasmeter.controller;

import org.hamcrest.collection.IsArrayContaining;
import org.homenet.easimon.gasmeter.domain.GasRecord;
import org.homenet.easimon.gasmeter.domain.GasRecordRepository;
import org.homenet.easimon.gasmeter.domain.GasRecordType;
import org.homenet.easimon.gasmeter.json.Data;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Locale;

public class GasControllerTest {
	
	GasController controller;
	GasRecordRepository repository = mock(GasRecordRepository.class);

	@Before
	public void setUp() {
		this.controller = new GasController(repository, ZoneId.of("Europe/Paris"), Locale.GERMANY);
	}

	@Test
	public void testGetGas() {
		GasRecord gasRecord = new GasRecord() {
			
			@Override
			public GasRecordType getType() {
				return GasRecordType.NORMAL;
			}
			
			@Override
			public Instant getTimestamp() {
				return Instant.EPOCH;
			}
			
			@Override
			public long getAmount() {
				return 1;
			}
		}; 
		when(repository.findGasRecordsByPeriod(any(), any())).thenReturn(Collections.singletonList(gasRecord));
		
		Data data = controller.getGas("2016-01-01T00:00:00+02:00[Europe/Berlin]", 
				ChronoUnit.DAYS.name(), ChronoUnit.DAYS.name(), "7", "false");
		assertEquals(1, data.getDatasets().length);
		assertEquals(1, data.getDatasets()[0].getData()[0]);
	}
}
