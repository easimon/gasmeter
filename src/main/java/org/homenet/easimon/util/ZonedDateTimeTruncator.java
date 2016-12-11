package org.homenet.easimon.util;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;
import java.time.temporal.WeekFields;
import java.util.Locale;

public final class ZonedDateTimeTruncator {

	private ZonedDateTimeTruncator() {

	}

	/**
	 * Truncates a DateTime to the given Temporal Unit. Like
	 * {@link ZonedDateTime#truncatedTo(TemporalUnit)}, just with support for
	 * truncating to the beginning of a week, month and year.
	 */
	public static ZonedDateTime truncate(ZonedDateTime dateTime, TemporalUnit unit, Locale locale) {
		if (unit instanceof ChronoUnit) {
			switch ((ChronoUnit) unit) {
			case WEEKS:
				return dateTime.with(WeekFields.of(locale).dayOfWeek(), 1).truncatedTo(ChronoUnit.DAYS);
			case MONTHS:
				return dateTime.with(TemporalAdjusters.firstDayOfMonth()).truncatedTo(ChronoUnit.DAYS);
			case YEARS:
				return dateTime.with(TemporalAdjusters.firstDayOfYear()).truncatedTo(ChronoUnit.DAYS);
			default:
				break;
			}
		}
		return dateTime.truncatedTo(unit);
	}

}
