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
 * Provides the information of technician and saler of one city.
 * 
 * @author Qi Liang
 */
public class Record {

    /**
     * City name.
     */
    private String city = null;

    /**
     * Technician number.
     */
    private int technicians = 0;

    /**
     * Saler number.
     */
    private int salers = 0;

    /**
     * Constructor.
     * 
     * @param city
     *            city name
     * @param technicians
     *            technician number
     * @param saler
     *            saler numbr
     */
    public Record(String city, int technicians, int saler) {
        this.city = city;
        this.technicians = technicians;
        this.salers = saler;
    }

    /**
     * Returns city name.
     * 
     * @return city name
     */
    public String getCity() {
        return city;
    }

    /**
     * Returns technician number.
     * 
     * @return technician number
     */
    public int getTechnicians() {
        return technicians;
    }

    /**
     * Returns saler number.
     * 
     * @return saler number
     */
    public int getSalers() {
        return salers;
    }
}
