package org.homenet.easimon.gasmeter.json;

import java.util.List;

import org.homenet.easimon.gasmeter.domain.GasRecord;

public class DataConverter {

	public static Data convert(List<GasRecord>... recordLists) {

		for (List<GasRecord> records : recordLists) {
			final String[] labels = records.stream().map(g -> g.getTimestamp().toString()).toArray(String[]::new);
			final long[] data = records.stream().mapToLong(g -> g.getAmount()).toArray();
		}
		return null;

	}

}
