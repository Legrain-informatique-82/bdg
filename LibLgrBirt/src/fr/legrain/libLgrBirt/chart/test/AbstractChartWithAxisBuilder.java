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

import org.eclipse.birt.chart.model.component.Axis;
import org.eclipse.birt.chart.model.impl.ChartWithAxesImpl;

import fr.legrain.libLgrBirt.chart.test.data.DataSet;


/**
 * Builds the chart with axis.
 * 
 * @author Qi Liang
 */
public abstract class AbstractChartWithAxisBuilder extends AbstractChartBuilder {

    /**
     * Title of X axis.
     */
    protected String xTitle = null;

    /**
     * Title of Y axis.
     */
    protected String yTitle = null;

    /**
     * X axis.
     */
    protected Axis xAxis = null;

    /**
     * Y axis.
     */
    protected Axis yAxis = null;

    /**
     * Constructor.
     * 
     * @param dataSet
     *            data for chart
     */
    public AbstractChartWithAxisBuilder(DataSet dataSet) {
        super(dataSet);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.examples.chart.widget.chart.AbstractChartBuilder#createChart()
     */
    protected void createChart() {
        chart = ChartWithAxesImpl.create();
    }
}
