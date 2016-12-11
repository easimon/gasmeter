package org.homenet.easimon.util;

import static org.junit.Assert.*;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import org.junit.Test;

public class ZonedDateTimeTruncatorTest {

	@Test
	public void testTruncateToWeekGermanySunToMonBefore() {
		ZonedDateTime dateTime = ZonedDateTime.parse("2016-12-11T18:00:00+01:00[Europe/Berlin]"); // Sun
		ZonedDateTime truncated = ZonedDateTimeTruncator.truncate(dateTime, ChronoUnit.WEEKS, Locale.GERMANY);
		ZonedDateTime expected = ZonedDateTime.parse("2016-12-05T00:00:00+01:00[Europe/Berlin]"); // Mon
		assertEquals(expected, truncated);
	}

	@Test
	public void testTruncateToWeekGermanyMonToSame() {
		ZonedDateTime dateTime = ZonedDateTime.parse("2016-12-12T18:00:00+01:00[Europe/Berlin]"); // Mon
		ZonedDateTime truncated = ZonedDateTimeTruncator.truncate(dateTime, ChronoUnit.WEEKS, Locale.GERMANY);
		ZonedDateTime expected = ZonedDateTime.parse("2016-12-12T00:00:00+01:00[Europe/Berlin]"); // Same
		assertEquals(expected, truncated);
	}

	@Test
	public void testTruncateToWeekUSASunToSame() {
		ZonedDateTime dateTime = ZonedDateTime.parse("2016-12-11T18:00:00-05:00[America/New_York]"); // Sun
		ZonedDateTime truncated = ZonedDateTimeTruncator.truncate(dateTime, ChronoUnit.WEEKS, Locale.US);
		ZonedDateTime expected = ZonedDateTime.parse("2016-12-11T00:00:00-05:00[America/New_York]"); // Same
		assertEquals(expected, truncated);
	}

	@Test
	public void testTruncateToWeekUSAMonToSunBefore() {
		ZonedDateTime dateTime = ZonedDateTime.parse("2016-12-12T18:00:00-05:00[America/New_York]"); // Mon
		ZonedDateTime truncated = ZonedDateTimeTruncator.truncate(dateTime, ChronoUnit.WEEKS, Locale.US);
		ZonedDateTime expected = ZonedDateTime.parse("2016-12-11T00:00:00-05:00[America/New_York]"); // Sun
		assertEquals(expected, truncated);
	}

}
