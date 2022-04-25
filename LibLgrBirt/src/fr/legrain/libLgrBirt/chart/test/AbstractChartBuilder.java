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
package fr.legrain.libLgrBirt.chart.test;

import org.eclipse.birt.chart.model.Chart;

import fr.legrain.libLgrBirt.chart.test.data.DataSet;

/**
 * Provides the common members and the framework to build one chart.
 * 
 * @author Qi Liang
 */
public abstract class AbstractChartBuilder {

    /**
     * Font name for all titles, labels, and values.
     */
    protected final static String FONT_NAME = "MS Sans Serif";

    /**
     * Provides data for chart.
     */
    protected DataSet dataSet = null;

    /**
     * Chart instance.
     */
    protected Chart chart = null;
    

    /**
     * Chart title.
     */
    protected String title = null;

    /**
     * Constructs one chart builder and associate it to one data set.
     * 
     * @param dataSet
     *            data set
     */
    public AbstractChartBuilder(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    /**
     * Builds one chart.
     */
    public void build() {
        createChart();
        buildPlot();
        buildLegend();
        buildTitle();
        buildXAxis();
        buildYAxis();
        buildXSeries();
        buildYSeries();
    }

    /**
     * Creates chart instance.
     */
    protected abstract void createChart();

    /**
     * Builds plot.
     */
    protected void buildPlot() {

    }

    /**
     * Builds X axis.
     */
    protected void buildXAxis() {

    }

    /**
     * Builds Y axis.
     */
    protected void buildYAxis() {

    }

    /**
     * Builds X series.
     */
    protected void buildXSeries() {

    }

    /**
     * Builds Y series.
     */
    protected void buildYSeries() {

    }

    /**
     * Builds legend.
     * 
     */
    protected void buildLegend() {

    }

    /**
     * Builds the chart title.
     */
    protected void buildTitle() {
        chart.getTitle().getLabel().getCaption().setValue(title);
        chart.getTitle().getLabel().getCaption().getFont().setSize(14);
        chart.getTitle().getLabel().getCaption().getFont().setName(FONT_NAME);
    }

    /**
     * Returns the chart instance.
     * 
     * @return the chart instance
     */
    public Chart getChart() {
        return chart;
    }

}
