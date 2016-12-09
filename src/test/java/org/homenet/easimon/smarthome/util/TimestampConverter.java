package org.homenet.easimon.smarthome.util;

import java.time.Instant;

public class TimestampConverter {

	public static String timestampToUnixTime(long timestamp) {
		return Instant.ofEpochSecond(timestamp).toString();
	}

}
