package org.homenet.easimon.gasmeter.domain;

import java.time.Instant;
import java.time.temporal.TemporalAccessor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA Type converter for {@link Instant} to a {@link Long} and back. Actually
 * converts any {@link TemporalAccessor} to a {@link Long}, and back to an
 * {@link Instant}. Unsure if that may break things, but seems to work.
 * 
 * @author mdobel
 */
@Converter
public class InstantToEpochSecondConverter implements AttributeConverter<TemporalAccessor, Long> {

	@Override
	public Long convertToDatabaseColumn(TemporalAccessor timestamp) {
		if (timestamp == null)
			return null;
		return Long.valueOf(Instant.from(timestamp).getEpochSecond());
	}

	@Override
	public Instant convertToEntityAttribute(Long epochSecond) {
		if (epochSecond == null)
			return null;
		return Instant.ofEpochSecond(epochSecond.longValue());
	}

}
