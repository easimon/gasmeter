package org.homenet.easimon.smarthome.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;

public class GasRecordQuantizerTest {

	private GasRecord mockRecord(final TemporalAccessor time, final long amount) {
		final Instant instant = Instant.from(time);
		return new GasRecord() {

			@Override
			public Instant getTimestamp() {
				return instant;
			}

			@Override
			public long getAmount() {
				return amount;
			}

			@Override
			public GasRecordType getType() {
				return GasRecordType.TEST;
			}

			@Override
			public String toString() {
				return new ToStringBuilder(this).append("timestamp", instant).append("amount", amount).toString();
			}
		};
	}

	private static final ZoneId BERLIN = ZoneId.of("Europe/Berlin");

	@Test
	public void testSimpleByHour() {
		final ZoneId zone = BERLIN;

		List<GasRecord> records = Arrays.asList( //
				mockRecord(ZonedDateTime.parse("2015-01-01T00:00:00+01:00[Europe/Berlin]"), 1), //
				mockRecord(ZonedDateTime.parse("2015-01-01T00:00:10+01:00[Europe/Berlin]"), 2), //
				mockRecord(ZonedDateTime.parse("2015-01-01T00:05:00+01:00[Europe/Berlin]"), 3), //
				mockRecord(ZonedDateTime.parse("2015-01-01T00:10:00+01:00[Europe/Berlin]"), 4), //
				mockRecord(ZonedDateTime.parse("2015-01-01T01:01:00+01:00[Europe/Berlin]"), 5), //
				mockRecord(ZonedDateTime.parse("2015-01-01T02:00:00+01:00[Europe/Berlin]"), 6), //
				mockRecord(ZonedDateTime.parse("2015-01-01T02:01:00+01:00[Europe/Berlin]"), 7) //
		);

		List<GasRecord> quantizedByHour = GasRecordQuantizer.quantize(records, ChronoUnit.HOURS, zone);

		assertThat(quantizedByHour.size(), is(3));
		assertThat(quantizedByHour.get(0).getAmount(), equalTo(1L + 2 + 3 + 4));
		assertThat(quantizedByHour.get(1).getAmount(), equalTo(5L));
		assertThat(quantizedByHour.get(2).getAmount(), equalTo(6L + 7));
	}

	@Test
	public void testSimpleByDay() {
		final ZoneId zone = BERLIN;

		List<GasRecord> records = Arrays.asList( //
				mockRecord(ZonedDateTime.parse("2015-01-01T00:00:00+01:00[Europe/Berlin]"), 1), //
				mockRecord(ZonedDateTime.parse("2015-01-01T23:59:59+01:00[Europe/Berlin]"), 2), //
				mockRecord(ZonedDateTime.parse("2015-01-02T00:05:00+01:00[Europe/Berlin]"), 3), //
				mockRecord(ZonedDateTime.parse("2015-01-02T00:10:00+01:00[Europe/Berlin]"), 4), //
				mockRecord(ZonedDateTime.parse("2015-01-03T01:01:00+01:00[Europe/Berlin]"), 5) //
		);

		List<GasRecord> quantizedByHour = GasRecordQuantizer.quantize(records, ChronoUnit.DAYS, zone);

		assertThat(quantizedByHour.size(), is(3));
		assertThat(quantizedByHour.get(0).getAmount(), equalTo(1L + 2));
		assertThat(quantizedByHour.get(1).getAmount(), equalTo(3L + 4));
		assertThat(quantizedByHour.get(2).getAmount(), equalTo(5L));
	}

	@Test
	public void testHourlyAroundCESTtoCET() {
		final ZoneId zone = BERLIN;

		List<GasRecord> records = Arrays.asList( //
				mockRecord(OffsetDateTime.parse("2016-10-30T01:00:00+02:00"), 1), //
				mockRecord(OffsetDateTime.parse("2016-10-30T02:00:00+02:00"), 2), //
				mockRecord(OffsetDateTime.parse("2016-10-30T02:59:59+02:00"), 3), //
				mockRecord(OffsetDateTime.parse("2016-10-30T02:00:00+01:00"), 4), //
				mockRecord(OffsetDateTime.parse("2016-10-30T02:59:59+01:00"), 5), //
				mockRecord(OffsetDateTime.parse("2016-10-30T03:00:00+01:00"), 6) //
		);

		List<GasRecord> quantizedByHour = GasRecordQuantizer.quantize(records, ChronoUnit.HOURS, zone);

		assertThat(quantizedByHour.size(), is(4));
		assertThat(quantizedByHour.get(0).getAmount(), equalTo(1L));
		assertThat(quantizedByHour.get(1).getAmount(), equalTo(2L + 3));
		assertThat(quantizedByHour.get(2).getAmount(), equalTo(4L + 5));
		assertThat(quantizedByHour.get(3).getAmount(), equalTo(6L));
	}

	@Test
	public void testDailyAroundCESTtoCET() {
		final ZoneId zone = BERLIN;

		List<GasRecord> records = Arrays.asList( //
				mockRecord(OffsetDateTime.parse("2016-10-30T00:00:00+02:00"), 1), //
				mockRecord(OffsetDateTime.parse("2016-10-30T23:59:59+01:00"), 6), //
				mockRecord(OffsetDateTime.parse("2016-10-31T00:00:00+01:00"), 11) //
		);

		List<GasRecord> quantizedByHour = GasRecordQuantizer.quantize(records, ChronoUnit.DAYS, zone);

		assertThat(quantizedByHour.size(), is(2));
		assertThat(quantizedByHour.get(0).getAmount(), equalTo(1L + 6));
		assertThat(quantizedByHour.get(1).getAmount(), equalTo(11L));
	}

	@Test
	public void testHourlyAroundCETtoCEST() {
		final ZoneId zone = BERLIN;

		List<GasRecord> records = Arrays.asList( //
				mockRecord(OffsetDateTime.parse("2016-03-27T01:00:00+01:00"), 1), //
				mockRecord(OffsetDateTime.parse("2016-03-27T01:59:59+01:00"), 2), //
				mockRecord(OffsetDateTime.parse("2016-03-27T03:00:00+02:00"), 4), //
				mockRecord(OffsetDateTime.parse("2016-03-27T03:59:59+02:00"), 5), //
				mockRecord(OffsetDateTime.parse("2016-03-27T04:00:00+02:00"), 6) //
		);

		List<GasRecord> quantizedByHour = GasRecordQuantizer.quantize(records, ChronoUnit.HOURS, zone);

		assertThat(quantizedByHour.size(), is(3));
		assertThat(quantizedByHour.get(0).getAmount(), equalTo(1L + 2));
		assertThat(quantizedByHour.get(1).getAmount(), equalTo(4L + 5));
		assertThat(quantizedByHour.get(2).getAmount(), equalTo(6L));
	}

}
