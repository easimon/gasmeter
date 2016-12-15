package org.homenet.easimon.gasmeter.domain;

import static org.junit.Assert.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.junit.Before;
import org.junit.Test;

public class InstantToEpochSecondConverterTest {

	private InstantToEpochSecondConverter converter;

	@Before
	public void setup() {
		converter = new InstantToEpochSecondConverter();
	}

	@Test
	public void testNulls() {
		assertSame(null, converter.convertToDatabaseColumn(null));
		assertSame(null, converter.convertToEntityAttribute(null));
	}

	@Test
	public void testForwardAndBack() {
		Instant now = Instant.now().truncatedTo(ChronoUnit.SECONDS);
		assertEquals(now, converter.convertToEntityAttribute(converter.convertToDatabaseColumn(now)));

		Long nowLong = now.getEpochSecond();
		assertEquals(nowLong, converter.convertToDatabaseColumn(converter.convertToEntityAttribute(nowLong)));
	}

}
