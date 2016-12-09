package org.homenet.easimon.smarthome.json;

public class Data {

	private final String[] labels;
	private final Dataset[] datasets;

	public Data(String[] labels, Dataset[] datasets) {
		this.labels = labels;
		// for (Dataset dataset: datasets) {
		// if (dataset.getData().length != labels.length)
		// throw new IllegalArgumentException("Data length mismatch");
		// }
		this.datasets = datasets;
	}

	public String[] getLabels() {
		return labels;
	}

	public Dataset[] getDatasets() {
		return datasets;
	}

}
