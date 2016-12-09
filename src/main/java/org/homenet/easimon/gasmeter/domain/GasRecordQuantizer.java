package org.homenet.easimon.gasmeter.domain;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class GasRecordQuantizer {

	private static final class QuantizedGasRecord implements GasRecord {

		private final Instant start;
		private long amount;

		public QuantizedGasRecord(final Instant start) {
			this.start = start;
			this.amount = 0;
		}

		@Override
		public long getAmount() {
			return amount;
		}

		@Override
		public Instant getTimestamp() {
			return start;
		}

		@Override
		public GasRecordType getType() {
			return GasRecordType.QUANTIZED;
		}

		public void add(long amount) {
			this.amount += amount;
		}

		@Override
		public String toString() {
			return new ToStringBuilder(this) //
					.append("instant", start) //
					.append("amount", amount) //
					.toString();
		}

	}

	private static final Comparator<GasRecord> BY_TIMESTAMP = (g1, g2) -> g1.getTimestamp()
			.compareTo(g2.getTimestamp());

	public static List<GasRecord> quantize(final List<GasRecord> records, final TemporalUnit unit,
			final ZoneId timezone) {
		if (records.isEmpty())
			return Collections.emptyList();

		final List<GasRecord> result = new ArrayList<>();

		final List<GasRecord> sortedRecords = records.stream().sorted(BY_TIMESTAMP).collect(Collectors.toList());

		Instant start = records.get(0).getTimestamp().atZone(timezone).truncatedTo(unit).toInstant();
		Instant next = start.atZone(timezone).plus(1, unit).toInstant();
		QuantizedGasRecord quantized = new QuantizedGasRecord(start);

		for (GasRecord record : sortedRecords) {
			while (!next.isAfter(record.getTimestamp())) {
				result.add(quantized);
				start = next;
				next = start.atZone(timezone).plus(1, unit).toInstant();
				quantized = new QuantizedGasRecord(start);
			}
			quantized.add(record.getAmount());
		}
		result.add(quantized);

		return result;
	}

}
