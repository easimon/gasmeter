package org.homenet.easimon.smarthome.flot;

import org.joda.time.DateTime;

public class DataFactory {

    public static double[] createDataEntry(DateTime date, double value) {
        double[] result = new double[2];
        result[0] = date.getMillis();
        result[1] = value;
        return result;
    }

    public static double[][] createData() {
        return null;
    }

}
