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
import org.eclipse.birt.chart.model.component.Axis;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.NumberDataSet;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.TextDataSet;
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

public class MultiSerieBarChart extends AbstractChartBuilder {
    
	private ChartDimension chartDimension;
	
	/* Visibilité du label au dessus de chaque barre du graphique	 */
	private boolean labelAxisVisibility = true;
	/* Taille de la police sur le label */
	private int labelAxisSize = 12;
	/* Rotation du label */
	private int labelAxisRotation = 0;
	
	private String police = "arial";
	
	public MultiSerieBarChart(Composite parent, int style,DataSetChart dataSetChart) {
		super(parent, style, dataSetChart);
	}
	
	public MultiSerieBarChart(Composite parent, int style,
			DataSetChart dataSetChart,String titleChart,
			ChartDimension chartDimension,String xTitle,
			String yTitle) {
		this(parent,style,dataSetChart);
		this.dataSetChart = dataSetChart;
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
		chart.setSubType("Side-by-side");
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
		/** change color of text X axis **/ 
//		xAxisPrimary.getLabel().getCaption().setColor( ColorDefinitionImpl.RED( ) );
		/** change color of line X axis **/
//		xAxisPrimary.getLineAttributes().setColor(ColorDefinitionImpl.RED());
//		xAxisPrimary.getTitle().setBackground(ColorDefinitionImpl.RED());
		
		xAxisPrimary.getTitle().getCaption().setValue(xTitle);
		
//		xAxisPrimary.getLabel().setShadowColor( ColorDefinitionImpl.BLACK() );
		xAxisPrimary.getLabel().getCaption().getFont().setRotation( 25 );
		xAxisPrimary.getLabel().getCaption().getFont().setName( police );
		
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
    	yAxisPrimary.getLabel().getCaption().getFont().setName( police );
    	
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
			bs.getLabel().getCaption().getFont().setName( police );
			bs.setTranslucent(true);
			sdY.getSeries().add(bs);
			
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
