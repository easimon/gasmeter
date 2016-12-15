package org.homenet.easimon.gasmeter.json;

public class Data {

	private final String[] labels;
	private final Dataset[] datasets;

	public Data(String[] labels, Dataset[] datasets) {
		this.labels = labels;
		this.datasets = datasets;
	}

	public String[] getLabels() {
		return labels;
	}

	public Dataset[] getDatasets() {
		return datasets;
	}

}
