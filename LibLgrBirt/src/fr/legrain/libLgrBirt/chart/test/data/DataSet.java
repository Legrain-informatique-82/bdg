/****************************************************************
 * Licensed Material - Property of IBM
 *
 * ****-*** 
 *
 * (c) Copyright IBM Corp. 2006.  All rights reserved.
 *
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with
 * IBM Corp.
 *
 ****************************************************************
 */
package fr.legrain.libLgrBirt.chart.test.data;

/**
 * Provides the information of technician and saler in different cities.
 * 
 * @author Qi Liang
 */
public class DataSet {

    /**
     * The singleton instance
     */
    private static DataSet instance = new DataSet();

    /**
     * The records of technician and saler
     */
    private Record[] records = null;

    /**
     * Returns the singleton instance.
     * 
     * @return the singleton instance
     */
    public static DataSet getInstance() {
    	
        return instance;
    }

    /**
     * Returns all records of cities.
     * 
     * @return all records of cities
     */
    private Record[] getRecords() {
        if (records == null) {
            records = new Record[6];

            records[0] = new Record("Shanghai", 8, 20);
            records[1] = new Record("Beijing", 5, 15);
            records[2] = new Record("Hangzhou", 4, 10);
            records[3] = new Record("Guangzhou", 12, 8);
            records[4] = new Record("Xian", 2, 3);
            records[5] = new Record("Nanning", 3, 12);

        }
        return records;
    }

    /**
     * Returns the city names.
     * 
     * @return the city names.
     */
    public String[] getCities() {
        int size = getRecords().length;
        String cities[] = new String[size];
        for (int i = 0; i < size; i++) {
            Record record = records[i];
            cities[i] = record.getCity();
        }
        return cities;
    }

    /**
     * Returns technician count in all cities.
     * 
     * @return technician count
     */
    public double[] getTechnitians() {
        int size = getRecords().length;
        double technitians[] = new double[size];
        for (int i = 0; i < size; i++) {
            Record record = records[i];
            technitians[i] = record.getTechnicians();
        }
        return technitians;
    }

    /**
     * Returns saler count in all cities.
     * 
     * @return saler count
     */
    public double[] getSalers() {
        int size = getRecords().length;
        double salers[] = new double[size];
        for (int i = 0; i < size; i++) {
            Record record = records[i];
            salers[i] = record.getSalers();
        }
        return salers;
    }
}
