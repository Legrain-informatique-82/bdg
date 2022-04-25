/**
 * 
 */
package fr.legrain.libLgrBirt.chart;

import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.ChartWithoutAxes;
import org.eclipse.birt.chart.model.attribute.ChartDimension;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.NumberDataSet;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.TextDataSet;
import org.eclipse.birt.chart.model.data.impl.NumberDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.data.impl.TextDataSetImpl;
import org.eclipse.birt.chart.model.impl.ChartWithoutAxesImpl;
import org.eclipse.birt.chart.model.layout.Legend;
import org.eclipse.birt.chart.model.layout.Plot;
import org.eclipse.birt.chart.model.type.PieSeries;
import org.eclipse.birt.chart.model.type.impl.PieSeriesImpl;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;

/**
 * @author nicolas²
 *
 */
public class PieChart extends AbstractChartBuilder {
	
	private ChartDimension chartDimension;

	public PieChart(Composite parent, int style, DataSetChart dataSetChart) {
		super(parent, style, dataSetChart);
		// TODO Auto-generated constructor stub
	}
	
	public PieChart(Composite parent, int style,
			DataSetChart dataSetChart,String titleChart,ChartDimension chartDimension){
		this(parent,style,dataSetChart);
		this.chartDimension = chartDimension;
		this.dataSetChart = dataSetChart;
		this.title = titleChart;
		this.composite = parent;
	}

	/* (non-Javadoc)
	 * @see fr.legrain.libLgrBirt.chart.AbstractChartBuilder#createChart()
	 */
	@Override
	protected void createChart() {
		// TODO Auto-generated method stub
		chart = ChartWithoutAxesImpl.create( );
		nomalacon();
		

	}

	protected void nomalacon(){
		TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
				"New York", "Boston", "Chicago", "San Francisco", "Dallas"} );//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		NumberDataSet seriesOneValues = NumberDataSetImpl.create( new double[]{
				54.65, 21, 75.95, 91.28, 37.43
		} );

		//Base Series
		Series seCategory = (Series) SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sd = SeriesDefinitionImpl.create( );
		((ChartWithoutAxes)chart).getSeriesDefinitions( ).add( sd );
		sd.getSeriesPalette( ).update( 0 );
		sd.getSeries( ).add( seCategory );

		// Orthogonal Series
		PieSeries sePie = (PieSeries) PieSeriesImpl.create( );
		sePie.setDataSet( seriesOneValues );
		sePie.setSeriesIdentifier( "Cities" );//$NON-NLS-1$ 

		SeriesDefinition sdCity = SeriesDefinitionImpl.create( );
		sdCity.getQuery( ).setDefinition( "Census.City" );//$NON-NLS-1$
		sd.getSeriesDefinitions( ).add( sdCity );
		sdCity.getSeries( ).add( sePie );
	}

	@Override
	protected void buildLegend() {
		// TODO Auto-generated method stub
		Legend lg = chart.getLegend( );
		lg.getText( ).getFont( ).setSize( 16 );
		lg.setBackground( null );
		lg.getOutline( ).setVisible( true );
	}

	@Override
	protected void buildPlot() {
		// TODO Auto-generated method stub
		chart.setSeriesThickness( 25 );
		chart.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		Plot p = chart.getPlot( );
		p.getClientArea( ).setBackground( null );
		p.getClientArea( ).getOutline( ).setVisible( true );
		p.getOutline( ).setVisible( true );
	}

	@Override
	protected void buildTitle() {
		// TODO Auto-generated method stub
		chart.getTitle( ).getLabel( ).getCaption( ).setValue( "Pie Chart" );//$NON-NLS-1$
		chart.getTitle( ).getOutline( ).setVisible( true );
	}


	@Override
	public Chart getChart() {
		// TODO Auto-generated method stub
		return super.getChart();
	}

	@Override
	public Chart getDesignTimeModel() {
		// TODO Auto-generated method stub
		return super.getDesignTimeModel();
	}

	

	@Override
	public void regenerateChart() {
		// TODO Auto-generated method stub
		super.regenerateChart();
	}

	@Override
	public void repaintChart() {
		// TODO Auto-generated method stub
		super.repaintChart();
	}


	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		super.widgetDefaultSelected(e);
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		super.widgetSelected(e);
	}

}
