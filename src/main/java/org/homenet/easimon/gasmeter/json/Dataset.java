package org.homenet.easimon.gasmeter.json;

public class Dataset {

	private final String label;
	private final long[] data;

	public Dataset(String label, long[] data) {
		this.label = label;
		this.data = data;
	}

	public String getLabel() {
		return label;
	}

	public long[] getData() {
		return data;
	}

}
