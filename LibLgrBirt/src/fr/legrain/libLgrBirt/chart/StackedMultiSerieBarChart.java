/**
 * StackedMultiSerieBarChart.java
 */
package fr.legrain.libLgrBirt.chart;

import java.util.Iterator;
import java.util.LinkedHashMap;

import org.eclipse.birt.chart.api.ChartEngine;
import org.eclipse.birt.chart.device.IDeviceRenderer;
import org.eclipse.birt.chart.exception.ChartException;
import org.eclipse.birt.chart.factory.GeneratedChartState;
import org.eclipse.birt.chart.factory.Generator;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.ChartWithAxes;
import org.eclipse.birt.chart.model.attribute.Anchor;
import org.eclipse.birt.chart.model.attribute.AxisType;
import org.eclipse.birt.chart.model.attribute.Bounds;
import org.eclipse.birt.chart.model.attribute.CallBackValue;
import org.eclipse.birt.chart.model.attribute.ChartDimension;
import org.eclipse.birt.chart.model.attribute.ColorDefinition;
import org.eclipse.birt.chart.model.attribute.Fill;
import org.eclipse.birt.chart.model.attribute.FontDefinition;
import org.eclipse.birt.chart.model.attribute.Gradient;
import org.eclipse.birt.chart.model.attribute.IntersectionType;
import org.eclipse.birt.chart.model.attribute.LegendItemType;
import org.eclipse.birt.chart.model.attribute.LineStyle;
import org.eclipse.birt.chart.model.attribute.Position;
import org.eclipse.birt.chart.model.attribute.SortOption;
import org.eclipse.birt.chart.model.attribute.Text;
import org.eclipse.birt.chart.model.attribute.TickStyle;
import org.eclipse.birt.chart.model.attribute.impl.BoundsImpl;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
import org.eclipse.birt.chart.model.attribute.impl.GradientImpl;
import org.eclipse.birt.chart.model.attribute.impl.LineAttributesImpl;
import org.eclipse.birt.chart.model.component.Axis;
import org.eclipse.birt.chart.model.component.MarkerLine;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.MarkerLineImpl;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.NumberDataSet;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.TextDataSet;
import org.eclipse.birt.chart.model.data.impl.NumberDataElementImpl;
import org.eclipse.birt.chart.model.data.impl.NumberDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.data.impl.TextDataSetImpl;
import org.eclipse.birt.chart.model.impl.ChartWithAxesImpl;
import org.eclipse.birt.chart.model.layout.Legend;
import org.eclipse.birt.chart.model.layout.Plot;
import org.eclipse.birt.chart.model.layout.TitleBlock;
import org.eclipse.birt.chart.model.type.BarSeries;
import org.eclipse.birt.chart.model.type.impl.BarSeriesImpl;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;


/**
 * StackedMultiSerieBarChart
 * BarChart avec barres superposables
 * @author nicolas²
 *
 */
public class StackedMultiSerieBarChart extends AbstractChartBuilder {

	private ChartDimension chartDimension;

	/* Visibilité du label au dessus de chaque barre du graphique	 */
	private boolean labelAxisVisibility = true;
	/* Taille de la police sur le label */
	private int labelAxisSize = 12;
	/* Rotation du label */
	private int labelAxisRotation = 0;
	
	private DataSetChart overDataSetChart;

	public StackedMultiSerieBarChart(Composite parent, int style,DataSetChart dataSetChart) {
		super(parent, style, dataSetChart);
	}

	public StackedMultiSerieBarChart(Composite parent, int style,
			DataSetChart dataSetChart,DataSetChart overDataSetChart, String titleChart,
			ChartDimension chartDimension,String xTitle,
			String yTitle) {
		this(parent,style,dataSetChart);
		this.dataSetChart = dataSetChart;
		this.overDataSetChart = overDataSetChart;
		this.title = titleChart;
		this.chartDimension = chartDimension;
		this.composite = parent;
		this.xTitle = xTitle;
		this.yTitle = yTitle;

	}

	protected void createChart() {
		// TODO Auto-generated method stub
		chart = ChartWithAxesImpl.create( );
		chart.setDimension(this.chartDimension);
		chart.setType("Bar Chart" ); 
		chart.setSubType("Stacked");
		chart.setSeriesThickness( 10 );
		chart.setGridColumnCount( 2 );
		chart.getBlock().setBackground( ColorDefinitionImpl.WHITE( ) );
		//((ChartWithAxes) chart).setTransposed(true);
	}
	protected void buildTitle() {
		TitleBlock tb = chart.getTitle();
		tb.getLabel().getCaption().setValue(title);
		tb.getLabel().getCaption().getFont().setSize(14);
		tb.getLabel().getCaption().getFont().setName(FONT_NAME);
		tb.setBackground( GradientImpl.create( ColorDefinitionImpl.create(0,128,0 ),
				ColorDefinitionImpl.create( 128, 0, 0 ), 0, false ) );
		tb.getLabel( ).getCaption( ).setColor( ColorDefinitionImpl.WHITE( ) );

	}
	protected void buildPlot() {


		Plot p = chart.getPlot( );
		{
			p.getClientArea( ).getOutline( ).setVisible( false );
			p.getClientArea( ).setBackground( null );
			//			p.getClientArea( ).setBackground(ColorDefinitionImpl.BLACK());
		}
		{
			//			p.getOutline( ).setVisible( false );
			p.getOutline( ).setVisible( true );
			p.getOutline().setColor(ColorDefinitionImpl.create( 214, 100, 12 ) );
		}
		p.setAnchor( Anchor.SOUTH_WEST_LITERAL );


	}

	protected void buildLegend() {
		Legend lg = chart.getLegend( );
		lg.getText().getFont( ).setSize( 16 );
		lg.setBackground( null );
		lg.getOutline( ).setVisible( true );
		//		lg.setItemType( LegendItemType.SERIES_LITERAL );
		lg.getOutline().setColor(ColorDefinitionImpl.create( 214, 100, 12 ) );
		lg.getClientArea().getOutline().setColor(ColorDefinitionImpl.BLUE());
	}

	/**
	 * Builds X axis.
	 */
	protected void buildXAxis() {
		xAxisPrimary = ((ChartWithAxes)chart).getPrimaryBaseAxes()[0];
		xAxisPrimary.getTitle( ).setVisible( true );
		xAxisPrimary.setTitlePosition( Position.BELOW_LITERAL );
		xAxisPrimary.setType( AxisType.TEXT_LITERAL );
		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
		xAxisPrimary.setLabelPosition( Position.BELOW_LITERAL );
		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
		/** change color of text X axis **/ 
		//		xAxisPrimary.getLabel().getCaption().setColor( ColorDefinitionImpl.RED( ) );
		/** change color of line X axis **/
		//		xAxisPrimary.getLineAttributes().setColor(ColorDefinitionImpl.RED());
		//		xAxisPrimary.getTitle().setBackground(ColorDefinitionImpl.RED());

		xAxisPrimary.getTitle().getCaption().setValue(xTitle);

		//		xAxisPrimary.getLabel().setShadowColor( ColorDefinitionImpl.BLACK() );
		xAxisPrimary.getLabel().getCaption().getFont().setRotation( 25 );

	}

	protected void buildXAxisMajorGrid() {
		xAxisPrimary.getMajorGrid( ).setTickStyle(TickStyle.ABOVE_LITERAL );
		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );
		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setStyle(
				LineStyle.DOTTED_LITERAL );
		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setColor(
				ColorDefinitionImpl.GREY( ) );

		//		xAxisPrimary.getMinorGrid().getLineAttributes( ).setColor( ColorDefinitionImpl.BLACK( ) );
		xAxisPrimary.getMinorGrid( ).getLineAttributes( ).setVisible( true );
	}
	protected void buildYAxis() {
		yAxisPrimary = ((ChartWithAxes)chart).getPrimaryOrthogonalAxis( xAxisPrimary );
		yAxisPrimary.getTitle( ).setVisible( true );
		yAxisPrimary.getTitle().getCaption().setValue(yTitle);
		yAxisPrimary.getLabel().getCaption().getFont().setRotation( 25 );
		yAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );

	}
	protected void buildYAxisMajorGrid() {
		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setStyle(
				LineStyle.DOTTED_LITERAL );
		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setColor(
				ColorDefinitionImpl.GREY( ) );
		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );
		

	}

	protected void buildXSeries() {
		TextDataSet categoryValues = TextDataSetImpl.create(dataSetChart.getArrayValuesXSeries());

		Series seCategory = SeriesImpl.create();
		seCategory.setDataSet(categoryValues);

		SeriesDefinition sdX = SeriesDefinitionImpl.create();
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		//		sdX.setSorting(SortOption.ASCENDING_LITERAL);
		sdX.getSeries( ).add( seCategory );


	}

	protected void buildYSeries() {

		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
		sdY.getSeriesPalette().update(1);
		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
		LinkedHashMap<String, Double[]> values = dataSetChart.getMapValuesYSeries();

		SeriesDefinition sdY2 = SeriesDefinitionImpl.create( );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY2 );
		sdY2.getSeriesPalette( ).update(1);
		LinkedHashMap<String, Double[]> values2 = overDataSetChart.getMapValuesYSeries();
		/** methode pour changer color**/
		//		final Fill[] fiaBase = {
		//				ColorDefinitionImpl.ORANGE( ),
		//				GradientImpl.create( ColorDefinitionImpl.create( 225, 225, 255 ),
		//						ColorDefinitionImpl.create( 255, 255, 225 ),-35,false ),
		//						ColorDefinitionImpl.CREAM( ),
		//						//				ColorDefinitionImpl.RED( ),
		//						ColorDefinitionImpl.BLACK( ),
		//						ColorDefinitionImpl.BLUE( ).brighter( ),
		//						ColorDefinitionImpl.CYAN( ).darker( ),
		//		};
		//		sdY.getSeriesPalette( ).getEntries( ).clear( );
		//		for ( int i = 0; i < fiaBase.length; i++ )
		//		{
		//			sdY.getSeriesPalette().getEntries( ).add( fiaBase[i] );
		//		}
		/** methode pour changer color **/
		for (String keyMap : values.keySet()) {	

			NumberDataSet orthoValues = NumberDataSetImpl.create(values.get(keyMap));
			BarSeries bs = (BarSeries) BarSeriesImpl.create( );
			bs.setSeriesIdentifier(keyMap);
			bs.setDataSet( orthoValues );
			bs.setRiserOutline( null );
			bs.getLabel( ).setVisible( labelAxisVisibility );
			bs.getLabel().getCaption().getFont().setSize(labelAxisSize);
			bs.getLabel().getCaption().getFont().setRotation(labelAxisRotation);
			bs.setLabelPosition( Position.INSIDE_LITERAL );
			bs.setStacked(true);
//			sdY.getSeriesPalette( ).update( ColorDefinitionImpl.WHITE( ).darker() );
			sdY.getSeriesPalette( ).update(GradientImpl.create(ColorDefinitionImpl.create( 124, 207, 89 ) ,
					ColorDefinitionImpl.create( 212,
							246,
							160 ),
					-35,
					false ));
			sdY.getSeries().add(bs);

		}

		for (String keyMap : values2.keySet()) {	
			NumberDataSet orthoValues2 = NumberDataSetImpl.create(values2.get(keyMap));
			BarSeries bs2 = (BarSeries) BarSeriesImpl.create( );
			bs2.setSeriesIdentifier(keyMap);
			bs2.setDataSet( orthoValues2 );
			bs2.setRiserOutline( null );
			bs2.getLabel( ).setVisible( labelAxisVisibility );
			bs2.getLabel().getCaption().getFont().setSize(labelAxisSize);
			bs2.getLabel().getCaption().getFont().setRotation(labelAxisRotation);
			bs2.setLabelPosition( Position.INSIDE_LITERAL );
			bs2.setStacked(true);
			bs2.setTranslucent(true);
//			sdY2.getSeriesPalette( ).update( ColorDefinitionImpl.WHITE( ).darker() );
			sdY2.getSeriesPalette( ).update( GradientImpl.create(ColorDefinitionImpl.create( 223, 93, 91 ).translucent() ,
					ColorDefinitionImpl.create( 237,
							168,
							161 ).translucent(),
					-35,
					false ) );
			
			sdY2.getSeries().add(bs2);
		}
	}
	public void setChartDimension(ChartDimension chartDimension) {
		if(chartDimension == null){
			this.chartDimension = ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL;
		}else{
			this.chartDimension = chartDimension;
		}
	}

	public Axis getYAxisPrimary (){
		return yAxisPrimary;
	}

	public Axis getXAxisPrimary() {
		return xAxisPrimary;
	}

	public void setLabelAxisVisibility(boolean labelAxisVisibility) {
		this.labelAxisVisibility = labelAxisVisibility;
	}

	public void setLabelAxisSize(int labelAxisSize) {
		this.labelAxisSize = labelAxisSize;
	}

	public void setLabelAxisRotation(int labelAxisRotation) {
		this.labelAxisRotation = labelAxisRotation;
	}


}
