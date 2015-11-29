package org.homenet.easimon.smarthome.flot;

import java.util.Date;

public class DataFactory {
	
	public static double[] createDataEntry(Date date, double value) {
		double[] result = new double[2];
		result[0] = date.getTime();
		result[1] = value;
		return result;
	}
	
	public static double[][] createData() {
		return null;
	}

}
