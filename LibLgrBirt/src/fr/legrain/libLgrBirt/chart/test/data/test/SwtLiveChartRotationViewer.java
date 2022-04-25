package fr.legrain.libLgrBirt.chart.test.data.test;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Vector;

import org.eclipse.birt.chart.api.ChartEngine;
import org.eclipse.birt.chart.computation.DataPointHints;
import org.eclipse.birt.chart.device.EmptyUpdateNotifier;
import org.eclipse.birt.chart.device.ICallBackNotifier;
import org.eclipse.birt.chart.device.IDeviceRenderer;
import org.eclipse.birt.chart.device.IImageMapEmitter;
import org.eclipse.birt.chart.event.WrappedStructureSource;
import org.eclipse.birt.chart.exception.ChartException;
import org.eclipse.birt.chart.extension.datafeed.StockEntry;
import org.eclipse.birt.chart.factory.GeneratedChartState;
import org.eclipse.birt.chart.factory.Generator;
import org.eclipse.birt.chart.factory.IGenerator;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.ChartWithAxes;
import org.eclipse.birt.chart.model.ChartWithoutAxes;
import org.eclipse.birt.chart.model.DialChart;
import org.eclipse.birt.chart.model.attribute.ActionType;
import org.eclipse.birt.chart.model.attribute.ActionValue;
import org.eclipse.birt.chart.model.attribute.Anchor;
import org.eclipse.birt.chart.model.attribute.Angle3D;
import org.eclipse.birt.chart.model.attribute.AngleType;
import org.eclipse.birt.chart.model.attribute.AxisType;
import org.eclipse.birt.chart.model.attribute.Bounds;
import org.eclipse.birt.chart.model.attribute.CallBackValue;
import org.eclipse.birt.chart.model.attribute.ChartDimension;
import org.eclipse.birt.chart.model.attribute.DataPoint;
import org.eclipse.birt.chart.model.attribute.DataPointComponentType;
import org.eclipse.birt.chart.model.attribute.Fill;
import org.eclipse.birt.chart.model.attribute.IntersectionType;
import org.eclipse.birt.chart.model.attribute.LeaderLineStyle;
import org.eclipse.birt.chart.model.attribute.LegendBehaviorType;
import org.eclipse.birt.chart.model.attribute.LegendItemType;
import org.eclipse.birt.chart.model.attribute.LineAttributes;
import org.eclipse.birt.chart.model.attribute.LineDecorator;
import org.eclipse.birt.chart.model.attribute.LineStyle;
import org.eclipse.birt.chart.model.attribute.Marker;
import org.eclipse.birt.chart.model.attribute.MarkerType;
import org.eclipse.birt.chart.model.attribute.NumberFormatSpecifier;
import org.eclipse.birt.chart.model.attribute.Orientation;
import org.eclipse.birt.chart.model.attribute.Palette;
import org.eclipse.birt.chart.model.attribute.Position;
import org.eclipse.birt.chart.model.attribute.RiserType;
import org.eclipse.birt.chart.model.attribute.TickStyle;
import org.eclipse.birt.chart.model.attribute.TriggerCondition;
import org.eclipse.birt.chart.model.attribute.impl.Angle3DImpl;
import org.eclipse.birt.chart.model.attribute.impl.BoundsImpl;
import org.eclipse.birt.chart.model.attribute.impl.CallBackValueImpl;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
import org.eclipse.birt.chart.model.attribute.impl.DataPointComponentImpl;
import org.eclipse.birt.chart.model.attribute.impl.DataPointImpl;
import org.eclipse.birt.chart.model.attribute.impl.GradientImpl;
import org.eclipse.birt.chart.model.attribute.impl.InsetsImpl;
import org.eclipse.birt.chart.model.attribute.impl.JavaNumberFormatSpecifierImpl;
import org.eclipse.birt.chart.model.attribute.impl.LineAttributesImpl;
import org.eclipse.birt.chart.model.attribute.impl.NumberFormatSpecifierImpl;
import org.eclipse.birt.chart.model.attribute.impl.Rotation3DImpl;
import org.eclipse.birt.chart.model.attribute.impl.SeriesValueImpl;
import org.eclipse.birt.chart.model.attribute.impl.TooltipValueImpl;
import org.eclipse.birt.chart.model.attribute.impl.URLValueImpl;
import org.eclipse.birt.chart.model.component.Axis;
import org.eclipse.birt.chart.model.component.DialRegion;
import org.eclipse.birt.chart.model.component.MarkerLine;
import org.eclipse.birt.chart.model.component.MarkerRange;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.AxisImpl;
import org.eclipse.birt.chart.model.component.impl.CurveFittingImpl;
import org.eclipse.birt.chart.model.component.impl.DialRegionImpl;
import org.eclipse.birt.chart.model.component.impl.MarkerLineImpl;
import org.eclipse.birt.chart.model.component.impl.MarkerRangeImpl;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.Action;
import org.eclipse.birt.chart.model.data.BaseSampleData;
import org.eclipse.birt.chart.model.data.DataFactory;
import org.eclipse.birt.chart.model.data.DateTimeDataSet;
import org.eclipse.birt.chart.model.data.NumberDataSet;
import org.eclipse.birt.chart.model.data.OrthogonalSampleData;
import org.eclipse.birt.chart.model.data.SampleData;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.StockDataSet;
import org.eclipse.birt.chart.model.data.TextDataSet;
import org.eclipse.birt.chart.model.data.Trigger;
import org.eclipse.birt.chart.model.data.impl.ActionImpl;
import org.eclipse.birt.chart.model.data.impl.DateTimeDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.NumberDataElementImpl;
import org.eclipse.birt.chart.model.data.impl.NumberDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.data.impl.StockDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.TextDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.TriggerImpl;
import org.eclipse.birt.chart.model.impl.ChartWithAxesImpl;
import org.eclipse.birt.chart.model.impl.ChartWithoutAxesImpl;
import org.eclipse.birt.chart.model.impl.DialChartImpl;
import org.eclipse.birt.chart.model.layout.Legend;
import org.eclipse.birt.chart.model.layout.Plot;
import org.eclipse.birt.chart.model.layout.TitleBlock;
import org.eclipse.birt.chart.model.type.AreaSeries;
import org.eclipse.birt.chart.model.type.BarSeries;
import org.eclipse.birt.chart.model.type.DialSeries;
import org.eclipse.birt.chart.model.type.LineSeries;
import org.eclipse.birt.chart.model.type.PieSeries;
import org.eclipse.birt.chart.model.type.ScatterSeries;
import org.eclipse.birt.chart.model.type.StockSeries;
import org.eclipse.birt.chart.model.type.impl.AreaSeriesImpl;
import org.eclipse.birt.chart.model.type.impl.BarSeriesImpl;
import org.eclipse.birt.chart.model.type.impl.DialSeriesImpl;
import org.eclipse.birt.chart.model.type.impl.LineSeriesImpl;
import org.eclipse.birt.chart.model.type.impl.PieSeriesImpl;
import org.eclipse.birt.chart.model.type.impl.ScatterSeriesImpl;
import org.eclipse.birt.chart.model.type.impl.StockSeriesImpl;
import org.eclipse.birt.chart.util.CDateTime;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.ibm.icu.util.Calendar;


public class SwtLiveChartRotationViewer extends Composite implements PaintListener/*,ControlListener*/,ICallBackNotifier,SelectionListener 
{
	private static String OUTPUT = "/home/lee/Bureau/Downloads/Standalone.png"; //$NON-NLS-1$
	private static String OUTPUT_HTML = "/home/lee/Bureau/Downloads/Standalone.html"; //$NON-NLS-1$

	public static String backgroundPath = "/icons/lgr.png";
	private IDeviceRenderer idr = null;
	private Chart chart = null;

	private GeneratedChartState gcs = null;
	public static SwtLiveChartRotationViewer c3dViewer;
	/**
	 * Used in building the chart for the first time
	 */
	private boolean bFirstPaint = true;
	private static int colornum=0;
	private MyActionRenderer myActionRenderer;
	
	/**
	 * execute application
	 * 
	 * @param args
	 */
	public static void main( String[] args )
	{
		Display display = Display.getDefault( );
		Shell shell = new Shell( display, SWT.CLOSE );
		shell.setSize( 600, 400 );
		shell.setLayout( new GridLayout( ) );
		c3dViewer = new SwtLiveChartRotationViewer( shell, SWT.NO_BACKGROUND,null,null);
		c3dViewer.setLayoutData( new GridData( GridData.FILL_BOTH ) );
		c3dViewer.addPaintListener( c3dViewer );
//		c3dViewer.addControlListener( c3dViewer );

		shell.open( );
		while ( !shell.isDisposed( ) )
		{
			if ( !display.readAndDispatch( ) )
				display.sleep( );
		}
		display.dispose( );
	}
	@Override
	public void dispose() {
		if( (imgChart !=null) && (!imgChart.isDisposed()))imgChart.dispose();
		if( (gcImage !=null) && (!gcImage.isDisposed()))gcImage.dispose();
		super.dispose();
	}
	/**
	 * Constructor
	 */
	

	public SwtLiveChartRotationViewer(Composite parent,int style,Composite compositeComment,Text textComment){
		super( parent, style );
		this.myActionRenderer = new MyActionRenderer(compositeComment,textComment);
		try
		{
//			PlatformConfig config = new PlatformConfig( );
			//config.setBIRTHome("/home/lee/Bureau/Downloads/birt-runtime-2_3_2/ReportEngine");
//			config.setProperty("STANDALONE", true);

			idr = ChartEngine.instance().getRenderer("dv.SWT");
//			idr = ChartEngine.instance(config).getRenderer("dv.PNG");
//			idr = ChartEngine.instance(config).getRenderer("dv.SWT");
			idr.setProperty(IDeviceRenderer.UPDATE_NOTIFIER, this );
			
		}
		catch ( ChartException pex )
		{
			pex.printStackTrace();
		}
		//cm = createLiveChart( );
//		chart = createBar();
//		chart = createMultiYAxisChart();
//		chart = createSimplePieChart();
//		chart = createSimplelineChart();
//		chart = createScatterChart_1();
//		chart = createScatterChart_2();
//		chart = createMultiPieChart();
		
		/**** new ****/
//		chart =  create3DAreaChart( );
//		chart =  create3DBarChart( );
//		chart =  create3DLineChart( );
//		chart =  createBarChart( );
//		chart = createHSChart();
//		chart = createAreaChart();
//		chart = createAreaChart_1();
//		chart = createCFAreaChart();
//		chart = createCFBarChart();
//		chart = createCFStockChart();
//		chart = createLineChart();
//		chart = createMDialSRegionChart();
//		chart = createMDialMRegionChart();
//		chart = createMultiBarChart();
//		chart = createPieChart();
//		chart = createSDialMRegionChart();
//		chart = createSDialSRegionChart(); 
//		chart = createStockChart();
//		chart = createStackedChart();
//		chart = createStackedChartAction();
		chart = createBarChart_2();
//		chart = createStackedBarAction();
//		chart = showTooltip_PieChart( );
//		chart = showTooltip_PieChart();
//		chart = createMyChart();
//		chart = createMinSliceChart();
//		chart = createMultiYAxisChart_2();
		
		/*** Action ***/
//		chart = actionCreateMultiBarChart();
//		chart = actioncreate2DBarChart();
	}


	protected static final Chart createMinSliceChart( )
	{
		ChartWithoutAxes cwoaPie = ChartWithoutAxesImpl.create( );
		cwoaPie.getBlock( ).setBackground( ColorDefinitionImpl.PINK( ) );
		// Plot
		Plot p = cwoaPie.getPlot( );
		p.getClientArea( ).setBackground( ColorDefinitionImpl.PINK( ) );
		p.getClientArea( ).getOutline( ).setVisible( false );
		p.getOutline( ).setVisible( false );

		// Legend
		Legend lg = cwoaPie.getLegend( );
		lg.setItemType( LegendItemType.CATEGORIES_LITERAL );
		lg.getClientArea( ).getOutline( ).setVisible( true );
		lg.getTitle( ).setVisible( false );

		// Title
		cwoaPie.getTitle( )
		.getLabel( )
		.getCaption( )
		.setValue( "Explosion & Min Slice" ); //$NON-NLS-1$
		cwoaPie.getTitle( ).getOutline( ).setVisible( false );

		// Data Set
		TextDataSet categoryValues = TextDataSetImpl.create( new String []{
				"New York", "Boston", "Chicago", "San Francisco", "Dallas", "Miami"//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$//$NON-NLS-6$
		} );
		NumberDataSet seriesOneValues = NumberDataSetImpl.create( new double[]{
				24, 9, 30, 36, 8, 51
		} );

		// Base Series
		SeriesDefinition sd = SeriesDefinitionImpl.create( );
		cwoaPie.getSeriesDefinitions( ).add( sd );

		Series seCategory = (Series) SeriesImpl.create( );

		final Fill[] fiaBase = {
		ColorDefinitionImpl.ORANGE( ),
		GradientImpl.create( ColorDefinitionImpl.create( 225, 225, 255 ),
						ColorDefinitionImpl.create( 255, 255, 225 ),-35,false ),
		ColorDefinitionImpl.CREAM( ),
//		ColorDefinitionImpl.RED( ),
		ColorDefinitionImpl.BLACK( ),
		ColorDefinitionImpl.BLUE( ).brighter( ),
		ColorDefinitionImpl.CYAN( ).darker( ),
		};
		sd.getSeriesPalette( ).getEntries( ).clear( );
		for ( int i = 0; i < fiaBase.length; i++ )
		{
			sd.getSeriesPalette().getEntries( ).add( fiaBase[i] );
		}

		seCategory.setDataSet( categoryValues );
		sd.getSeries( ).add( seCategory );

		// Orthogonal Series
		SeriesDefinition sdCity = SeriesDefinitionImpl.create( );
		sd.getSeriesDefinitions( ).add( sdCity );

		PieSeries sePie = (PieSeries) PieSeriesImpl.create( );
		sePie.setDataSet( seriesOneValues );
		sePie.setLabelPosition( Position.INSIDE_LITERAL );
		sePie.setSeriesIdentifier( "Cities" ); //$NON-NLS-1$

		// Explosion
		sePie.setExplosion( 30 );
		sePie.setExplosionExpression( "valueData<30 ||valueData>70" );//$NON-NLS-1$

		sdCity.getSeries( ).add( sePie );

		// Min Slice
		cwoaPie.setMinSlice( 10 );
		cwoaPie.setMinSlicePercent( false );
		cwoaPie.setMinSliceLabel( "Others" );//$NON-NLS-1$

		return cwoaPie;
	}

	protected static final Chart createMultiYAxisChart_2( )
	{
		ChartWithAxes cwaBar = ChartWithAxesImpl.create( );

		// Plot
		cwaBar.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		Plot p = cwaBar.getPlot( );
		p.getClientArea( ).setBackground( ColorDefinitionImpl.create( 255,
				245,
				255 ) );

		// Title
		cwaBar.getTitle( )
		.getLabel( )
		.getCaption( )
		.setValue( "Line Chart with Multiple Y Axis" );//$NON-NLS-1$

		// Legend
		Legend lg = cwaBar.getLegend( );
		LineAttributes lia = lg.getOutline( );
		lg.getText( ).getFont( ).setSize( 16 );
		lia.setStyle( LineStyle.SOLID_LITERAL );
		lg.getInsets( ).set( 10, 5, 0, 0 );
		lg.getOutline( ).setVisible( false );
		lg.setAnchor( Anchor.NORTH_LITERAL );

		// X-Axis
		Axis xAxisPrimary = cwaBar.getPrimaryBaseAxes( )[0];
		xAxisPrimary.setType( AxisType.TEXT_LITERAL );
		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
		xAxisPrimary.getTitle( ).setVisible( false );

		// Y-Axis
		Axis yAxisPrimary = cwaBar.getPrimaryOrthogonalAxis( xAxisPrimary );
		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
		yAxisPrimary.getTitle( )
		.getCaption( )
		.setValue( "Sales Growth ($Million)" );//$NON-NLS-1$

		// Y-Axis (2)
		Axis yAxis = AxisImpl.create( Axis.ORTHOGONAL );
		yAxis.setType( AxisType.LINEAR_LITERAL );
		yAxis.getMajorGrid( ).setTickStyle( TickStyle.RIGHT_LITERAL );
		yAxis.setLabelPosition( Position.RIGHT_LITERAL );
		xAxisPrimary.getAssociatedAxes( ).add( yAxis );

		// Data Set
		TextDataSet categoryValues = TextDataSetImpl.create( new String []{
				"March", "April", "May", "June", "July"} );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$//$NON-NLS-5$
		NumberDataSet orthoValues1 = NumberDataSetImpl.create( new double[]{
				12.5, 19.6, 18.3, 13.2, 26.5
		} );
		NumberDataSet orthoValues2 = NumberDataSetImpl.create( new double[]{
				22.7, 23.6, 38.3, 43.2, 40.5
		} );

		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Series (1)
		LineSeries ls1 = (LineSeries) LineSeriesImpl.create( );
		ls1.setSeriesIdentifier( "A Corp." );//$NON-NLS-1$
		ls1.setDataSet( orthoValues1 );
		ls1.getLineAttributes( ).setColor( ColorDefinitionImpl.CREAM( ) );
		for ( int i = 0; i < ls1.getMarkers( ).size( ); i++ )
		{
			( (Marker) ls1.getMarkers( ).get( i ) ).setType( MarkerType.TRIANGLE_LITERAL );
			( (Marker) ls1.getMarkers( ).get( i ) ).setSize( 10 );
		}
		ls1.getLabel( ).setVisible( true );

		SeriesDefinition sdY1 = SeriesDefinitionImpl.create( );
		sdY1.getSeriesPalette( ).update( -2 );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY1 );
		sdY1.getSeries( ).add( ls1 );

		// Y-Serires (2)
		LineSeries ls2 = (LineSeries) LineSeriesImpl.create( );
		ls2.setSeriesIdentifier( "B Corp." );//$NON-NLS-1$
		ls2.setDataSet( orthoValues2 );
		ls2.getLineAttributes( ).setColor( ColorDefinitionImpl.CREAM( ) );
		for ( int i = 0; i < ls2.getMarkers( ).size( ); i++ )
		{
			( (Marker) ls2.getMarkers( ).get( i ) ).setType( MarkerType.CIRCLE_LITERAL );
			( (Marker) ls2.getMarkers( ).get( i ) ).setSize( 10 );
		}
		ls2.getLabel( ).setVisible( true );

		SeriesDefinition sdY2 = SeriesDefinitionImpl.create( );
		sdY2.getSeriesPalette( ).update( -3 );
		yAxis.getSeriesDefinitions( ).add( sdY2 );
		sdY2.getSeries( ).add( ls2 );

		return cwaBar;
	}

	protected static final Chart createMulitYSeriesChart( )
	{
		ChartWithAxes cwaBar = ChartWithAxesImpl.create( );

		// Plot
		cwaBar.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		Plot p = cwaBar.getPlot( );
		p.getClientArea( )
		.setBackground( GradientImpl.create( ColorDefinitionImpl.create( 225,
				225,
				255 ),
				ColorDefinitionImpl.create( 255, 255, 225 ),
				-35,
				false ) );
		p.getOutline( ).setVisible( true );

		// Title
		cwaBar.getTitle( )
		.getLabel( )
		.getCaption( )
		.setValue( "Bar Chart with Multiple Y Series" );//$NON-NLS-1$

		// Legend
		Legend lg = cwaBar.getLegend( );
		lg.getText( ).getFont( ).setSize( 16 );
		lg.getInsets( ).set( 10, 5, 0, 0 );
		lg.setAnchor( Anchor.NORTH_LITERAL );

		// X-Axis
		Axis xAxisPrimary = cwaBar.getPrimaryBaseAxes( )[0];
		xAxisPrimary.setType( AxisType.TEXT_LITERAL );
		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
		xAxisPrimary.getTitle( ).getCaption( ).setValue( "Regional Markets" ); //$NON-NLS-1$ 
		xAxisPrimary.setLabelPosition( Position.BELOW_LITERAL );
		xAxisPrimary.setTitlePosition( Position.BELOW_LITERAL );

		// Y-Axis
		Axis yAxisPrimary = cwaBar.getPrimaryOrthogonalAxis( xAxisPrimary );
		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
		yAxisPrimary.getTitle( )
		.getCaption( )
		.setValue( "Sales vs. Net Profit ($Million)" );//$NON-NLS-1$

		// Data Set
		TextDataSet categoryValues = TextDataSetImpl.create( new String []{
				"Europe", "Asia", "North America"} );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		NumberDataSet orthoValues1 = NumberDataSetImpl.create( new double[]{
				26.17, 34.21, 21.5
		} );
		NumberDataSet orthoValues2 = NumberDataSetImpl.create( new double[]{
				4.81, 3.55, -5.26
		} );

		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Series (1)
		BarSeries bs = (BarSeries) BarSeriesImpl.create( );
		bs.setSeriesIdentifier( "Sales" );//$NON-NLS-1$
		bs.setDataSet( orthoValues1 );
		bs.setRiserOutline( null );
		bs.getLabel( ).setVisible( true );
		bs.setLabelPosition( Position.INSIDE_LITERAL );
		SeriesDefinition sdY1 = SeriesDefinitionImpl.create( );
		sdY1.getSeriesPalette( ).update( -2 );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY1 );
		sdY1.getSeries( ).add( bs );

		// Y-Series (2)
		BarSeries bs2 = (BarSeries) BarSeriesImpl.create( );
		bs2.setSeriesIdentifier( "Net Profit" );//$NON-NLS-1$
		bs2.setDataSet( orthoValues2 );
		bs2.setRiserOutline( null );
		bs2.getLabel( ).setVisible( true );
		bs2.setLabelPosition( Position.INSIDE_LITERAL );

		SeriesDefinition sdY2 = SeriesDefinitionImpl.create( );
		sdY2.getSeriesPalette( ).update( -3 );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY2 );
		sdY2.getSeries( ).add( bs2 );

		return cwaBar;
	}


	

	public static Chart createMyChart() {   
		ChartWithAxes chartWithAxes = ChartWithAxesImpl.create();   
		chartWithAxes.getBlock().setBackground(ColorDefinitionImpl.WHITE());   
		chartWithAxes.getBlock().getOutline().setVisible(true);   
		chartWithAxes.setDimension(ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL);   

		//customize the plot   
		Plot plot = chartWithAxes.getPlot();   
		plot.getClientArea().setBackground(ColorDefinitionImpl.create(255, 255,255));   
		plot.getOutline().setVisible(false);   

		chartWithAxes.getTitle().getLabel().getCaption().setValue("Simple Bar Chart");   

		//customize the legend   
		Legend legend = chartWithAxes.getLegend();   
		legend.getText().getFont().setSize(16);   
		legend.getInsets().set(10, 5, 0, 0);   
		legend.setAnchor(Anchor.NORTH_LITERAL);   

		//customize the x-axis   
		Axis xAxisPrimary = chartWithAxes.getPrimaryBaseAxes()[0];   
		xAxisPrimary.setType(AxisType.TEXT_LITERAL);   
		xAxisPrimary.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);   
		xAxisPrimary.getOrigin().setType(IntersectionType.VALUE_LITERAL);   
		xAxisPrimary.getTitle().setVisible(false);   

		Axis yAxisPrimary = chartWithAxes.getPrimaryOrthogonalAxis(xAxisPrimary);   
		yAxisPrimary.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);   
		yAxisPrimary.setType(AxisType.LINEAR_LITERAL);   
		yAxisPrimary.getLabel().getCaption().getFont().setRotation(90);   

		//initialize a collection with the x-series data 

		Vector vs = new Vector();   
		vs.add("zero");   
		vs.add("one");   
		vs.add("two");   
		vs.add("three");   

		TextDataSet categoryValues = TextDataSetImpl.create(vs);   

		//initialize a collectioin with the y-serise data   
		ArrayList vn1 = new ArrayList();   
		vn1.add(new Double(25));   
		vn1.add(new Double(35));   
		vn1.add(new Double(-45));   
		vn1.add(new Double(60));   

		NumberDataSet orthoValue1 = NumberDataSetImpl.create(vn1);   

		//create the category base series;   
		Series seCategory = SeriesImpl.create();   
		seCategory.setDataSet(categoryValues);   

		//create the value orthogonal series   
		BarSeries bs1 = (BarSeries)BarSeriesImpl.create();   
		bs1.setSeriesIdentifier("My Bar Series");   
		bs1.setDataSet(orthoValue1);   
		bs1.setRiserOutline(null);   
		bs1.getLabel().setVisible(true);   
		bs1.setLabelPosition(Position.INSIDE_LITERAL);   

		//create the value orthogonal series   
		//      BarSeries bs2 = (BarSeries)BarSeriesImpl.create();   
		//      bs2.setSeriesIdentifier("My Bar Series 2");   
		//      bs2.setDataSet(orthoValue1);   
		//      bs2.setRiserOutline(null);   
		//      bs2.getLabel().setVisible(true);   
		//      bs2.setLabelPosition(Position.BELOW_LITERAL);   

		//wrap the base series in the x-axis series definiation   
		SeriesDefinition sdx = SeriesDefinitionImpl.create();   
		sdx.getSeriesPalette().shift(0);   
		xAxisPrimary.getSeriesDefinitions().add(sdx);   
		sdx.getSeries().add(seCategory);   

		//wrap the orthogonal series in the x-axis series definal   
		SeriesDefinition sdy = SeriesDefinitionImpl.create();   
		sdy.getSeriesPalette().shift(1);   
		yAxisPrimary.getSeriesDefinitions().add(sdy);   
		sdy.getSeries().add(bs1);   

		return chartWithAxes;   
	}   
	
	public static final Chart showTooltip_PieChart(){

		ChartWithoutAxesImpl cwoaPie = (ChartWithoutAxesImpl)ChartWithoutAxesImpl.create( );

		// Chart Type
		cwoaPie.setType( "Pie Chart" );
		cwoaPie.setDimension( ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL );

		// Title
		cwoaPie.getTitle( ).getLabel( ).getCaption( ).setValue("Sample Pie Chart" );
		cwoaPie.getBlock( ).setBounds( BoundsImpl.create( 0, 0, 252, 288 ) );
		cwoaPie.getBlock( ).getOutline( ).setVisible( true );

		// Plot
		cwoaPie.getPlot( ).getClientArea( ).getOutline( ).setVisible( false );
		cwoaPie.getPlot( ).getClientArea( ).setBackground(
				ColorDefinitionImpl.create( 255, 255, 225 ) );

		// Legend
		Legend lg = cwoaPie.getLegend( );
		lg.getText( ).getFont( ).setSize( 16 );
		lg.getInsets( ).set( 10, 5, 0, 0 );

		lg.getOutline( ).setStyle( LineStyle.DASH_DOTTED_LITERAL );
		lg.getOutline( ).setColor( ColorDefinitionImpl.create( 214, 100, 12 ) );
		lg.getOutline( ).setVisible( true );

		lg.setBackground( GradientImpl.create( ColorDefinitionImpl.create( 225, 225, 255 ),
						ColorDefinitionImpl.create(255,255,225 ), -35, false ) );
		lg.setAnchor( Anchor.EAST_LITERAL );
		lg.setItemType( LegendItemType.CATEGORIES_LITERAL );

		lg.getClientArea( ).setBackground( ColorDefinitionImpl.ORANGE( ) );
		lg.setPosition( Position.LEFT_LITERAL );
		//lg.setOrientation( Orientation.VERTICAL_LITERAL );

		// Data Set
		TextDataSet dsStringValue = TextDataSetImpl.create( new String[]{
				"Keyboards", "Moritors", "Printers", "Mortherboards"} );
		NumberDataSet dsNumericValues1 = NumberDataSetImpl.create( 
				new double[]{143.26, 156.55, 95.25, 47.56} );

		// Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( dsStringValue );

		SeriesDefinition series = SeriesDefinitionImpl.create( );

		cwoaPie.getSeriesDefinitions( ).add( series );
		series.getSeries( ).add( seCategory );

		PieSeries ps = (PieSeries) PieSeriesImpl.create( );
		/** action **/
		ps.getTriggers( ).add(
				TriggerImpl.create( TriggerCondition.ONCLICK_LITERAL,
				ActionImpl.create( ActionType.HIGHLIGHT_LITERAL, 
				SeriesValueImpl.create(String.valueOf(ps.getSeriesIdentifier( ))))));
//		ActionValue actionValue = TooltipValueImpl.create(0, null);
//		Action action = ActionImpl.create(ActionType.SHOW_TOOLTIP_LITERAL, actionValue);
//		Trigger mouseOver = TriggerImpl.create(TriggerCondition.ONMOUSEOVER_LITERAL,action);
//		ps.getTriggers().add(mouseOver);
		/** action **/
		ps.getLabel( ).getCaption( ).setColor( ColorDefinitionImpl.RED( ) );
		ps.getLabel( ).setBackground( ColorDefinitionImpl.CYAN( ) );
		ps.getLabel( ).setVisible( true );
		ps.setSeriesIdentifier( "Actuate" );
		ps.setDataSet( dsNumericValues1 );
		ps.setLeaderLineAttributes( LineAttributesImpl.create(
				ColorDefinitionImpl.create( 239, 33, 3 ),
				LineStyle.DASH_DOTTED_LITERAL,
				3 ) );
		ps.setLeaderLineStyle( LeaderLineStyle.FIXED_LENGTH_LITERAL );
//		ps.setExplosion( 0 );
		ps.setSliceOutline( ColorDefinitionImpl.BLACK( ) );

		SeriesDefinition seGroup1 = SeriesDefinitionImpl.create( );
		series.getSeriesPalette( ).update( -2 );
		series.getSeriesDefinitions( ).add( seGroup1 );
		seGroup1.getSeries( ).add( ps );

		return cwoaPie;
	}
	public static final Chart createBarChart_2( )
	{
		/***************************************************/
		ChartWithAxes cwaBar = ChartWithAxesImpl.create( );

		// Chart Type
		cwaBar.setType( "Bar Chart" );
		cwaBar.setSubType( "Stacked" );
		cwaBar.setDimension( ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL );
		cwaBar.setFloorFill( ColorDefinitionImpl.ORANGE( ) );
		cwaBar.setWallFill( ColorDefinitionImpl.BLUE( ) );

		// Title
		cwaBar.getTitle( ).getLabel( ).getCaption( ).setValue(
				"Computer Hardware Sales" ); //$NON-NLS-1$
		cwaBar.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );

		// Plot
		cwaBar.getPlot( ).getClientArea( ).getOutline( ).setVisible( false );
		cwaBar.getPlot( ).getClientArea( ).setBackground(
				ColorDefinitionImpl.create( 255, 255, 225 ) );

		// Legend
		Legend lg = cwaBar.getLegend( );
		lg.getText( ).getFont( ).setSize( 16 );
		lg.getInsets( ).set( 10, 5, 0, 0 );

		lg.getOutline( ).setStyle( LineStyle.DOTTED_LITERAL );
		lg.getOutline( ).setColor( ColorDefinitionImpl.create( 214, 100, 12 ) );
		lg.getOutline( ).setVisible( true );

		lg.setBackground( GradientImpl.create( ColorDefinitionImpl.create( 225,
				225, 255 ), ColorDefinitionImpl.create( 255, 255, 225 ), -35,
				false ) );
		lg.setAnchor( Anchor.EAST_LITERAL );
		lg.setItemType( LegendItemType.SERIES_LITERAL );

		lg.getClientArea( ).setBackground( ColorDefinitionImpl.ORANGE( ) );
		lg.setPosition( Position.RIGHT_LITERAL );
		lg.setOrientation( Orientation.VERTICAL_LITERAL );

		// X-Axis
		Axis xAxisPrimary = ( (ChartWithAxesImpl) cwaBar ).getPrimaryBaseAxes( )[0];
		xAxisPrimary.getTitle( ).setVisible( false );

		xAxisPrimary.setType( AxisType.TEXT_LITERAL );
		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
		xAxisPrimary.getLabel( ).getCaption( ).setColor(
				ColorDefinitionImpl.GREEN( ).darker( ) );

		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setStyle(
				LineStyle.DOTTED_LITERAL );
		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setColor(
				ColorDefinitionImpl.GREY( ) );
		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );

		MarkerRange mr = MarkerRangeImpl.create( xAxisPrimary,
				NumberDataElementImpl.create( 2.0 ), NumberDataElementImpl
						.create( 3.0 ), null );
		mr.setOutline( LineAttributesImpl.create( ColorDefinitionImpl.create(
				239, 33, 3 ), LineStyle.DOTTED_LITERAL, 2 ) );
		

		// Y-Axis
		Axis yAxisPrimary = ( (ChartWithAxesImpl) cwaBar )
				.getPrimaryOrthogonalAxis( xAxisPrimary );
		yAxisPrimary.getLabel( ).getCaption( ).setValue( "Sales Growth" ); //$NON-NLS-1$
		yAxisPrimary.getLabel( ).getCaption( ).setColor(
				ColorDefinitionImpl.BLUE( ) );

		yAxisPrimary.getTitle( ).setVisible( false );
		yAxisPrimary.setType( AxisType.LINEAR_LITERAL );
		yAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );

		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setStyle(
				LineStyle.DOTTED_LITERAL );
		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setColor(
				ColorDefinitionImpl.GREY( ) );
		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );
//		yAxisPrimary.setPercent( true );
		
		MarkerLine ml = MarkerLineImpl.create( yAxisPrimary,
				NumberDataElementImpl.create( 60.0 ) );
		ml.setLineAttributes( LineAttributesImpl.create( ColorDefinitionImpl
				.create( 17, 37, 223 ), LineStyle.SOLID_LITERAL, 1 ) );

		// Data Set
		TextDataSet dsStringValue = TextDataSetImpl.create( new String[]{
				"Keyboards", "Moritors", "Printers", "Mortherboards"} );
		NumberDataSet dsNumericValues1 = NumberDataSetImpl
				.create( new double[]{143.26, 156.55, 95.25, 47.56} );
		NumberDataSet dsNumericValues2 = NumberDataSetImpl
				.create( new double[]{15.29, -14.53, -47.05, 32.55} );

		// X-Series
		Series seBase = SeriesImpl.create( );
		seBase.setDataSet( dsStringValue );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		sdX.getQuery( ).setDefinition( "" ); //$NON-NLS-1$
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seBase );

		// Y-Series
		BarSeries bs = (BarSeries) BarSeriesImpl.create( );
		bs.setSeriesIdentifier( "Actuate" ); //$NON-NLS-1$
		bs.getLabel( ).getCaption( ).setColor( ColorDefinitionImpl.RED( ) );
		bs.getLabel( ).setBackground( ColorDefinitionImpl.CYAN( ) );
		bs.getLabel( ).setVisible( true );
		bs.setDataSet( dsNumericValues1 );
		bs.setStacked( true );

		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
		sdY.getSeriesPalette( ).update( ColorDefinitionImpl.GREEN( ) );
		sdY.getSeries( ).add( bs );

		BarSeries bs2 = (BarSeries) BarSeriesImpl.create( );
		bs2.setSeriesIdentifier( "Micorsoft" ); //$NON-NLS-1$
		bs2.getLabel( ).getCaption( ).setColor( ColorDefinitionImpl.RED( ) );
		bs2.getLabel( ).setBackground( ColorDefinitionImpl.CYAN( ) );
		bs2.getLabel( ).setVisible( true );
		bs2.setDataSet( dsNumericValues2 );
		bs2.setStacked( true );

		SeriesDefinition sdY2 = SeriesDefinitionImpl.create( );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY2 );
		sdY2.getSeriesPalette( ).update( ColorDefinitionImpl.YELLOW( ) );
		sdY2.getSeries( ).add( bs2 );

		return cwaBar;
		/************************************************************/
//		ChartWithAxes cwaBar = ChartWithAxesImpl.create( );
//
//		// Chart Type
//		cwaBar.setType( "Bar Chart" );
//		cwaBar.setSubType( "Side-by-side" );
//
//		// Title
//		cwaBar.getTitle( ).getLabel( ).getCaption( ).setValue(
//				"Computer Hardware Sales" ); //$NON-NLS-1$
//		cwaBar.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
//
//		// Plot
//		cwaBar.getPlot( ).getClientArea( ).getOutline( ).setVisible( false );
//		cwaBar.getPlot( ).getClientArea( ).setBackground(
//				ColorDefinitionImpl.create( 255, 255, 225 ) );
//
//		// X-Axis
//		Axis xAxisPrimary = ( (ChartWithAxesImpl) cwaBar ).getPrimaryBaseAxes( )[0];
//		xAxisPrimary.getTitle( ).setVisible( false );
//
//		xAxisPrimary.setType( AxisType.TEXT_LITERAL );
//		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
//		xAxisPrimary.getLabel( ).getCaption( ).setColor(
//				ColorDefinitionImpl.GREEN( ).darker( ) );
//
//		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
//		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setStyle(
//				LineStyle.DOTTED_LITERAL );
//		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setColor(
//				ColorDefinitionImpl.GREY( ) );
//		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );
//
//		// Y-Axis
//		Axis yAxisPrimary = ( (ChartWithAxesImpl) cwaBar )
//				.getPrimaryOrthogonalAxis( xAxisPrimary );
//		yAxisPrimary.getLabel( ).getCaption( ).setValue( "Sales Growth" ); //$NON-NLS-1$
//		FontDefinition fd = FontDefinitionImpl.create( "Arial", (float) 30.0,
//				true, true, false, true, false, 30.0, TextAlignmentImpl
//						.create( ) );
//		yAxisPrimary.getLabel( ).getCaption( ).setFont( fd );
//		yAxisPrimary.getLabel( ).getCaption( ).setColor(
//				ColorDefinitionImpl.BLUE( ) );
//
//		yAxisPrimary.getTitle( ).setVisible( false );
//		yAxisPrimary.setType( AxisType.LINEAR_LITERAL );
//		yAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
//
//		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
//		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setStyle(
//				LineStyle.DOTTED_LITERAL );
//		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setColor(
//				ColorDefinitionImpl.GREY( ) );
//		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );
//
//		// Data Set
//		TextDataSet dsStringValue = TextDataSetImpl.create( new String[]{
//				"Keyboards", "Moritors", "Printers", "Mortherboards"} );
//		NumberDataSet dsNumericValues1 = NumberDataSetImpl
//				.create( new double[]{143.26, 156.55, 95.25, 47.56} );
//		NumberDataSet dsNumericValues2 = NumberDataSetImpl
//				.create( new double[]{15.29, -14.53, -47.05, 32.55} );
//
//		// X-Series
//		Series seBase = SeriesImpl.create( );
//		seBase.setDataSet( dsStringValue );
//
//		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
//		sdX.getQuery( ).setDefinition( "" ); //$NON-NLS-1$
//		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
//		sdX.getSeries( ).add( seBase );
//
//		// Y-Series
//		BarSeries bs = (BarSeries) BarSeriesImpl.create( );
//		bs.setSeriesIdentifier( "Actuate" ); //$NON-NLS-1$
//		bs.getLabel( ).getCaption( ).setColor( ColorDefinitionImpl.RED( ) );
//		bs.getLabel( ).setBackground( ColorDefinitionImpl.CYAN( ) );
//		bs.getLabel( ).setVisible( true );
//		bs.setDataSet( dsNumericValues1 );
//
//		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
//		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
//		sdY.getSeriesPalette( ).update( ColorDefinitionImpl.BLUE( ) );
//		sdY.getSeries( ).add( bs );
//
//		BarSeries bs2 = (BarSeries) BarSeriesImpl.create( );
//		bs2.setSeriesIdentifier( "Micorsoft" ); //$NON-NLS-1$
//		bs2.getLabel( ).getCaption( ).setColor( ColorDefinitionImpl.RED( ) );
//		bs2.getLabel( ).setBackground( ColorDefinitionImpl.CYAN( ) );
//		bs2.getLabel( ).setVisible( true );
//		bs2.setDataSet( dsNumericValues2 );
//
//		SeriesDefinition sdY2 = SeriesDefinitionImpl.create( );
//		yAxisPrimary.getSeriesDefinitions( ).add( sdY2 );
//		sdY2.getSeriesPalette( ).update( ColorDefinitionImpl.PINK( ) );
//		sdY2.getSeries( ).add( bs2 );
//
//		return cwaBar;
		/**************************************************/
//		ChartWithAxes cwaBar = ChartWithAxesImpl.create( );
//
//		// Chart Type
//		cwaBar.setType( "Bar Chart" );
//
//		// Title
//		cwaBar.getTitle( ).getLabel( ).getCaption( ).setValue(
//				"Computer Hardware Sales" ); //$NON-NLS-1$
//		cwaBar.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
//
//		// Plot
//		Plot p = cwaBar.getPlot( );
//
//		p.getOutline( ).setStyle( LineStyle.DASH_DOTTED_LITERAL );
//		p.getOutline( ).setColor( ColorDefinitionImpl.create( 214, 100, 12 ) );
//		p.getOutline( ).setVisible( true );
//
//		p.setBackground( ColorDefinitionImpl.CREAM( ) );
//		p.setAnchor( Anchor.NORTH_LITERAL );
//
//		p.getClientArea( ).setBackground(
//				GradientImpl
//						.create( ColorDefinitionImpl.create( 225, 0, 255 ),
//								ColorDefinitionImpl.create( 255, 253, 200 ),
//								-35, false ) );
//		p.getClientArea( ).getOutline( ).setVisible( true );
//
//		// Legend
//		Legend lg = cwaBar.getLegend( );
//
//		lg.getText( ).getFont( ).setSize( 16 );
//		lg.getInsets( ).set( 10, 5, 0, 0 );
//
//		lg.getOutline( ).setStyle( LineStyle.DOTTED_LITERAL );
//		lg.getOutline( ).setColor( ColorDefinitionImpl.create( 214, 100, 12 ) );
//		lg.getOutline( ).setVisible( true );
//
//		lg.setBackground( GradientImpl.create( ColorDefinitionImpl.create( 225,
//				225, 255 ), ColorDefinitionImpl.create( 255, 255, 225 ), -35,
//				false ) );
//		lg.setAnchor( Anchor.SOUTH_LITERAL );
//		lg.setItemType( LegendItemType.SERIES_LITERAL );
//
//		lg.getClientArea( ).setBackground( ColorDefinitionImpl.ORANGE( ) );
//		lg.setPosition( Position.BELOW_LITERAL );
//		lg.setOrientation( Orientation.HORIZONTAL_LITERAL );
//
//		// X-Axis
//		Axis xAxisPrimary = ( (ChartWithAxesImpl) cwaBar ).getPrimaryBaseAxes( )[0];
//		xAxisPrimary.getTitle( ).setVisible( false );
//
//		xAxisPrimary.setType( AxisType.TEXT_LITERAL );
//		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
//		xAxisPrimary.getLabel( ).getCaption( ).setColor(
//				ColorDefinitionImpl.GREEN( ).darker( ) );
//
//		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
//		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setStyle(
//				LineStyle.DOTTED_LITERAL );
//		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setColor(
//				ColorDefinitionImpl.GREY( ) );
//		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );
//
//		MarkerRange mr = MarkerRangeImpl.create( xAxisPrimary,
//				NumberDataElementImpl.create( 2.0 ), NumberDataElementImpl
//						.create( 3.0 ), null );
//		mr.setOutline( LineAttributesImpl.create( ColorDefinitionImpl.create(
//				239, 33, 3 ), LineStyle.DOTTED_LITERAL, 2 ) );
//
//		// Y-Axis
//		Axis yAxisPrimary = ( (ChartWithAxesImpl) cwaBar )
//				.getPrimaryOrthogonalAxis( xAxisPrimary );
//		yAxisPrimary.getLabel( ).getCaption( ).setValue( "Sales Growth" ); //$NON-NLS-1$
//		yAxisPrimary.getLabel( ).getCaption( ).setColor(
//				ColorDefinitionImpl.BLUE( ) );
//
//		yAxisPrimary.getTitle( ).setVisible( false );
//		yAxisPrimary.setType( AxisType.LINEAR_LITERAL );
//		yAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
//
//		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
//		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setStyle(
//				LineStyle.DOTTED_LITERAL );
//		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setColor(
//				ColorDefinitionImpl.GREY( ) );
//		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );
//		yAxisPrimary.setPercent( true );
//
//		MarkerLine ml = MarkerLineImpl.create( yAxisPrimary,
//				NumberDataElementImpl.create( 60.0 ) );
//		ml.setLineAttributes( LineAttributesImpl.create( ColorDefinitionImpl
//				.create( 17, 37, 223 ), LineStyle.SOLID_LITERAL, 1 ) );
//
//		// Data Set
//		TextDataSet dsStringValue = TextDataSetImpl.create( new String[]{
//				"Keyboards", "Moritors", "Printers", "Mortherboards"} );
//		NumberDataSet dsNumericValues1 = NumberDataSetImpl
//				.create( new double[]{143.26, 156.55, 95.25, 47.56} );
//		NumberDataSet dsNumericValues2 = NumberDataSetImpl
//				.create( new double[]{15.29, -14.53, -47.05, 32.55} );
//
//		// X-Series
//		Series seBase = SeriesImpl.create( );
//		seBase.setDataSet( dsStringValue );
//
//		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
//		sdX.getQuery( ).setDefinition( "" ); //$NON-NLS-1$
//		sdX.setSorting( SortOption.DESCENDING_LITERAL );
//		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
//		sdX.getSeries( ).add( seBase );
//
//		// Y-Series
//		BarSeries bs = (BarSeries) BarSeriesImpl.create( );
//		bs.setSeriesIdentifier( "Actuate" ); //$NON-NLS-1$
//		bs.getLabel( ).getCaption( ).setColor( ColorDefinitionImpl.RED( ) );
//		bs.getLabel( ).setBackground( ColorDefinitionImpl.CYAN( ) );
//		bs.getLabel( ).setVisible( true );
//		bs.setDataSet( dsNumericValues1 );
//		bs.setStacked( true );
//
//		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
//		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
//		sdY.getSeriesPalette( ).update( ColorDefinitionImpl.GREEN( ) );
//		sdY.getSeries( ).add( bs );
//
//		BarSeries bs2 = (BarSeries) BarSeriesImpl.create( );
//		bs2.setSeriesIdentifier( "Micorsoft" ); //$NON-NLS-1$
//		bs2.getLabel( ).getCaption( ).setColor( ColorDefinitionImpl.RED( ) );
//		bs2.getLabel( ).setBackground( ColorDefinitionImpl.CYAN( ) );
//		bs2.getLabel( ).setVisible( true );
//		bs2.setDataSet( dsNumericValues2 );
//		bs2.setStacked( true );
//
//		SeriesDefinition sdY2 = SeriesDefinitionImpl.create( );
//		yAxisPrimary.getSeriesDefinitions( ).add( sdY2 );
//		sdY2.getSeriesPalette( ).update( ColorDefinitionImpl.YELLOW( ) );
//		sdY2.getSeries( ).add( bs2 );
//
//		return cwaBar;
		/***********************************************************/
//		ChartWithAxes cwaBar = ChartWithAxesImpl.create( );
//
//		// Chart Type
//		cwaBar.setType( "Bar Chart" );
//
//		// Title
//		cwaBar.getTitle( ).getLabel( ).getCaption( ).setValue(
//				"Computer Hardware Sales" ); //$NON-NLS-1$
//		cwaBar.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
//
//		// Plot
//		Plot p = cwaBar.getPlot( );
//
//		p.getOutline( ).setStyle( LineStyle.DASH_DOTTED_LITERAL );
//		p.getOutline( ).setColor( ColorDefinitionImpl.create( 214, 100, 12 ) );
//		p.getOutline( ).setVisible( true );
//
//		p.setBackground( ColorDefinitionImpl.CREAM( ) );
//		p.setAnchor( Anchor.NORTH_LITERAL );
//
//		p.getClientArea( ).setBackground(
//				GradientImpl
//						.create( ColorDefinitionImpl.create( 225, 0, 255 ),
//								ColorDefinitionImpl.create( 255, 253, 200 ),
//								-35, false ) );
//		p.getClientArea( ).getOutline( ).setVisible( true );
//
//		// Legend
//		Legend lg = cwaBar.getLegend( );
//		lg.getText( ).getFont( ).setSize( 16 );
//		lg.getInsets( ).set( 10, 5, 0, 0 );
//
//		lg.getOutline( ).setStyle( LineStyle.DOTTED_LITERAL );
//		lg.getOutline( ).setColor( ColorDefinitionImpl.create( 214, 100, 12 ) );
//		lg.getOutline( ).setVisible( true );
//
//		lg.setBackground( GradientImpl.create( ColorDefinitionImpl.create( 225,
//				225, 255 ), ColorDefinitionImpl.create( 255, 255, 225 ), -35,
//				false ) );
//		lg.setAnchor( Anchor.SOUTH_LITERAL );
//		lg.setItemType( LegendItemType.SERIES_LITERAL );
//
//		lg.getClientArea( ).setBackground( ColorDefinitionImpl.ORANGE( ) );
//		lg.setPosition( Position.BELOW_LITERAL );
//		lg.setOrientation( Orientation.HORIZONTAL_LITERAL );
//
//		// X-Axis
//		Axis xAxisPrimary = ( (ChartWithAxesImpl) cwaBar ).getPrimaryBaseAxes( )[0];
//		xAxisPrimary.getTitle( ).setVisible( false );
//
//		xAxisPrimary.setType( AxisType.TEXT_LITERAL );
//		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
//		xAxisPrimary.getLabel( ).getCaption( ).setColor(
//				ColorDefinitionImpl.GREEN( ).darker( ) );
//
//		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
//		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setStyle(
//				LineStyle.DOTTED_LITERAL );
//		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setColor(
//				ColorDefinitionImpl.GREY( ) );
//		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );
//
//		MarkerRange mr = MarkerRangeImpl.create( xAxisPrimary,
//				NumberDataElementImpl.create( 2.0 ), NumberDataElementImpl
//						.create( 3.0 ), null );
//		mr.setOutline( LineAttributesImpl.create( ColorDefinitionImpl.create(
//				239, 33, 3 ), LineStyle.DOTTED_LITERAL, 2 ) );
//
//		// Y-Axis
//		Axis yAxisPrimary = ( (ChartWithAxesImpl) cwaBar )
//				.getPrimaryOrthogonalAxis( xAxisPrimary );
//		yAxisPrimary.getLabel( ).getCaption( ).setValue( "Sales Growth" ); //$NON-NLS-1$
//		yAxisPrimary.getLabel( ).getCaption( ).setColor(
//				ColorDefinitionImpl.BLUE( ) );
//
//		yAxisPrimary.getTitle( ).setVisible( false );
//		yAxisPrimary.setType( AxisType.LINEAR_LITERAL );
//		yAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
//
//		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
//		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setStyle(
//				LineStyle.DOTTED_LITERAL );
//		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setColor(
//				ColorDefinitionImpl.GREY( ) );
//		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );
//		yAxisPrimary.setPercent( true );
//
//		MarkerLine ml = MarkerLineImpl.create( yAxisPrimary,
//				NumberDataElementImpl.create( 60.0 ) );
//		ml.setLineAttributes( LineAttributesImpl.create( ColorDefinitionImpl
//				.create( 17, 37, 223 ), LineStyle.SOLID_LITERAL, 1 ) );
//
//		// Data Set
//		TextDataSet dsStringValue = TextDataSetImpl.create( new String[]{
//				"Keyboards", "Moritors", "Printers", "Mortherboards"} );
//		NumberDataSet dsNumericValues1 = NumberDataSetImpl
//				.create( new double[]{143.26, 156.55, 95.25, 47.56} );
//
//		// X-Series
//		Series seBase = SeriesImpl.create( );
//		seBase.setDataSet( dsStringValue );
//
//		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
//		sdX.getQuery( ).setDefinition( "" ); //$NON-NLS-1$
//		sdX.setSorting( SortOption.DESCENDING_LITERAL );
//		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
//		sdX.getSeries( ).add( seBase );
//
//		// Y-Series
//		BarSeries bs = (BarSeries) BarSeriesImpl.create( );
//		bs.setSeriesIdentifier( "Actuate" ); //$NON-NLS-1$
//		bs.getLabel( ).getCaption( ).setColor( ColorDefinitionImpl.RED( ) );
//		bs.getLabel( ).setBackground( ColorDefinitionImpl.CYAN( ) );
//		bs.getLabel( ).setVisible( true );
//		bs.setDataSet( dsNumericValues1 );
//		bs.setStacked( true );
//
//		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
//		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
//		sdY.getSeriesPalette( ).update( ColorDefinitionImpl.GREEN( ) );
//		sdY.getSeries( ).add( bs );
//
//		return cwaBar;


	}

	public static final Chart createAreaChart( )
	{
		ChartWithAxes cwaArea = ChartWithAxesImpl.create( );

		// Chart Type
		cwaArea.setType( "Area Chart" );
		cwaArea.setDimension( ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL );

		// Title
		cwaArea.getTitle( ).getLabel( ).getCaption( ).setValue(
				"Computer Hardware Sales" ); //$NON-NLS-1$
		cwaArea.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );

		// Plot
		cwaArea.getPlot( ).getClientArea( ).getOutline( ).setVisible( false );
		cwaArea.getPlot( ).getClientArea( ).setBackground(
				ColorDefinitionImpl.create( 255, 255, 225 ) );

		// Legend
		Legend lg = cwaArea.getLegend( );
		lg.getText( ).getFont( ).setSize( 16 );
		lg.getInsets( ).set( 10, 5, 0, 0 );

		lg.getOutline( ).setStyle( LineStyle.DOTTED_LITERAL );
		lg.getOutline( ).setColor( ColorDefinitionImpl.create( 214, 100, 12 ) );
		lg.getOutline( ).setVisible( true );

		lg.setBackground( GradientImpl.create( ColorDefinitionImpl.create( 225,
				225, 255 ), ColorDefinitionImpl.create( 255, 255, 225 ), -35,
				false ) );
		lg.setAnchor( Anchor.EAST_LITERAL );
		lg.setItemType( LegendItemType.SERIES_LITERAL );

		lg.getClientArea( ).setBackground( ColorDefinitionImpl.ORANGE( ) );
		lg.setPosition( Position.RIGHT_LITERAL );
		lg.setOrientation( Orientation.VERTICAL_LITERAL );

		// X-Axis
		Axis xAxisPrimary = ( (ChartWithAxesImpl) cwaArea )
				.getPrimaryBaseAxes( )[0];
		xAxisPrimary.getTitle( ).setVisible( false );

		xAxisPrimary.setType( AxisType.TEXT_LITERAL );
		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
		xAxisPrimary.getLabel( ).getCaption( ).setColor(
				ColorDefinitionImpl.GREEN( ).darker( ) );

		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setStyle(
				LineStyle.DOTTED_LITERAL );
		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setColor(
				ColorDefinitionImpl.GREY( ) );
		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );

		MarkerRange mr = MarkerRangeImpl.create( xAxisPrimary,
				NumberDataElementImpl.create( 2.0 ), NumberDataElementImpl
						.create( 3.0 ), null );
		mr.setOutline( LineAttributesImpl.create( ColorDefinitionImpl.create(
				239, 33, 3 ), LineStyle.DOTTED_LITERAL, 2 ) );

		// Y-Axis
		Axis yAxisPrimary = ( (ChartWithAxesImpl) cwaArea )
				.getPrimaryOrthogonalAxis( xAxisPrimary );
		yAxisPrimary.getLabel( ).getCaption( ).setValue( "Sales Growth" ); //$NON-NLS-1$
		yAxisPrimary.getLabel( ).getCaption( ).setColor(
				ColorDefinitionImpl.BLUE( ) );

		yAxisPrimary.getTitle( ).setVisible( false );
		yAxisPrimary.setType( AxisType.LINEAR_LITERAL );
		yAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );

		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setStyle(
				LineStyle.DOTTED_LITERAL );
		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setColor(
				ColorDefinitionImpl.GREY( ) );
		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );

		MarkerLine ml = MarkerLineImpl.create( yAxisPrimary,
				NumberDataElementImpl.create( 60.0 ) );
		ml.setLineAttributes( LineAttributesImpl.create( ColorDefinitionImpl
				.create( 17, 37, 223 ), LineStyle.SOLID_LITERAL, 1 ) );

		// Data Set
		TextDataSet dsStringValue = TextDataSetImpl.create( new String[]{
				"Keyboards", "Moritors", "Printers", "Mortherboards"} );
		NumberDataSet dsNumericValues1 = NumberDataSetImpl
				.create( new double[]{143.26, 156.55, 95.25, 47.56} );

		// X-Series
		Series seBase = SeriesImpl.create( );
		seBase.setDataSet( dsStringValue );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		sdX.getQuery( ).setDefinition( "" ); //$NON-NLS-1$
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seBase );

		// Y-Series
		AreaSeries as = (AreaSeries) AreaSeriesImpl.create( );
		as.setSeriesIdentifier( "Actuate" ); //$NON-NLS-1$
		as.getLabel( ).getCaption( ).setColor( ColorDefinitionImpl.RED( ) );
		as.getLabel( ).setBackground( ColorDefinitionImpl.CYAN( ) );
		as.getLabel( ).setVisible( true );
		as.setLineAttributes( LineAttributesImpl.create( ColorDefinitionImpl
				.create( 207, 41, 207 ), LineStyle.SOLID_LITERAL, 1 ) );
		as.setDataSet( dsNumericValues1 );
		as.setStacked( true );

		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
		sdY.getSeriesPalette( ).update( ColorDefinitionImpl.GREEN( ) );
		sdY.getSeries( ).add( as );

		return cwaArea;
	}

	public final static Chart createStockChart( )
	{
		ChartWithAxes cwaStock = ChartWithAxesImpl.create( );

		// Title
		cwaStock.getTitle( ).getLabel( ).getCaption( ).setValue( "Stock Chart" );//$NON-NLS-1$
		TitleBlock tb = cwaStock.getTitle( );
		tb.setBackground( GradientImpl.create( ColorDefinitionImpl.create( 0,128,0 ), 
				ColorDefinitionImpl.create( 128, 0, 0 ), 0, false ) );
		tb.getLabel( ).getCaption( ).setColor( ColorDefinitionImpl.WHITE( ) );

		// Plot
		cwaStock.getBlock().setBackground( GradientImpl.create(ColorDefinitionImpl.create(
						196,196,196),ColorDefinitionImpl.WHITE(),90,false ) );
		cwaStock.getPlot().getClientArea().getInsets().set( 10, 10, 10, 10 );

		// Legend
		cwaStock.getLegend().setBackground( ColorDefinitionImpl.ORANGE( ) );

		// X-Axis
		Axis xAxisPrimary = ( (ChartWithAxesImpl) cwaStock ).getPrimaryBaseAxes( )[0];

		xAxisPrimary.getTitle( ).getCaption( ).setValue( "X Axis" );//$NON-NLS-1$
		xAxisPrimary.getTitle().getCaption( ).setColor( ColorDefinitionImpl.RED( ) );
		xAxisPrimary.getTitle( ).getCaption( ).setValue( "Date" );//$NON-NLS-1$
		xAxisPrimary.setTitlePosition( Position.ABOVE_LITERAL );

		xAxisPrimary.getLabel( ).getCaption( ).setColor( ColorDefinitionImpl.RED( ) );
		xAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 65 );
		xAxisPrimary.setLabelPosition( Position.ABOVE_LITERAL );

		xAxisPrimary.setType( AxisType.DATE_TIME_LITERAL );
		xAxisPrimary.getOrigin( ).setType( IntersectionType.MAX_LITERAL );

		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.ABOVE_LITERAL );
		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setColor( ColorDefinitionImpl.create( 255, 196, 196 ) );
		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setStyle( LineStyle.DOTTED_LITERAL );
		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );

		xAxisPrimary.setCategoryAxis( true );

		// Y-Axis (1)
		Axis yAxisPrimary = ( (ChartWithAxesImpl) cwaStock ).getPrimaryOrthogonalAxis( xAxisPrimary );

		yAxisPrimary.getLabel( ).getCaption( ).setValue( "Price Axis" );//$NON-NLS-1$
		yAxisPrimary.getLabel( ).getCaption( ).setColor( ColorDefinitionImpl.BLUE( ) );
		yAxisPrimary.setLabelPosition( Position.LEFT_LITERAL );

		yAxisPrimary.getTitle( ).getCaption( ).setValue( "Microsoft ($ Stock Price)" );//$NON-NLS-1$
		yAxisPrimary.getTitle( ).getCaption( ).setColor( ColorDefinitionImpl.BLUE( ) );
		yAxisPrimary.setTitlePosition( Position.LEFT_LITERAL );

		yAxisPrimary.getScale( ).setMin( NumberDataElementImpl.create( 24.5 ) );
		yAxisPrimary.getScale( ).setMax( NumberDataElementImpl.create( 27.5 ) );
		yAxisPrimary.getScale( ).setStep( 0.5 );

		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setColor( ColorDefinitionImpl.create( 196, 196, 255 ) );
		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setStyle( LineStyle.DOTTED_LITERAL );
		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );
		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );

		yAxisPrimary.setType( AxisType.LINEAR_LITERAL );
		yAxisPrimary.getOrigin( ).setType( IntersectionType.MIN_LITERAL );

		// Y-Axis (2)
		Axis yAxisOverlay = AxisImpl.create( Axis.ORTHOGONAL );

		yAxisOverlay.getLabel( ).getCaption( ).setColor( ColorDefinitionImpl.create( 0, 128, 0 ) );
		yAxisOverlay.getLabel( ).getCaption( ).getFont( ).setRotation( -25 );
		yAxisOverlay.setLabelPosition( Position.RIGHT_LITERAL );

		yAxisOverlay.getTitle( ).getCaption( ).setValue( "Volume" );//$NON-NLS-1$
		yAxisOverlay.getTitle( ).getCaption( ).setColor( ColorDefinitionImpl.GREEN( ).darker( ) );
		yAxisOverlay.getTitle( ).getCaption( ).getFont( ).setRotation( 90 );
		yAxisOverlay.getTitle( ).getCaption( ).getFont( ).setSize( 16 );
		yAxisOverlay.getTitle( ).getCaption( ).getFont( ).setBold( true );
		yAxisOverlay.getTitle( ).setVisible( true );
		yAxisOverlay.setTitlePosition( Position.RIGHT_LITERAL );

		yAxisOverlay.getLineAttributes( ).setColor( ColorDefinitionImpl.create( 0, 128, 0 ) );

		yAxisOverlay.setType( AxisType.LINEAR_LITERAL );
		yAxisOverlay.setOrientation( Orientation.VERTICAL_LITERAL );

		yAxisOverlay.getMajorGrid( ).getLineAttributes( ).setColor( ColorDefinitionImpl.create( 64, 196, 64 ) );
		yAxisOverlay.getMajorGrid( ).getLineAttributes( ).setStyle( LineStyle.DOTTED_LITERAL );
		yAxisOverlay.getMajorGrid( ).getLineAttributes( ).setVisible( true );
		yAxisOverlay.getMajorGrid( ).setTickStyle( TickStyle.RIGHT_LITERAL );

		yAxisOverlay.getOrigin( ).setType( IntersectionType.MAX_LITERAL );
		yAxisOverlay.getScale( ).setMax( NumberDataElementImpl.create( 180000000 ) );
		yAxisOverlay.getScale( ).setMin( NumberDataElementImpl.create( 20000000 ) );

		xAxisPrimary.getAssociatedAxes( ).add( yAxisOverlay );

		// Data Set
		DateTimeDataSet dsDateValues = DateTimeDataSetImpl.create( new Calendar[]{
				new CDateTime( 2004, 12, 27 ),
				new CDateTime( 2004, 12, 23 ),
				new CDateTime( 2004, 12, 22 ),
				new CDateTime( 2004, 12, 21 ),
				new CDateTime( 2004, 12, 20 ),
				new CDateTime( 2004, 12, 17 ),
				new CDateTime( 2004, 12, 16 ),
				new CDateTime( 2004, 12, 15 )
		} );

		StockDataSet dsStockValues = StockDataSetImpl.create( new StockEntry[]{
				new StockEntry( 27.01, 27.10, 26.82, 26.85 ),
				new StockEntry( 26.87, 27.15, 26.83, 27.01 ),
				new StockEntry( 26.84, 27.15, 26.78, 26.97 ),
				new StockEntry( 27.00, 27.17, 26.94, 27.07 ),
				new StockEntry( 27.01, 27.15, 26.89, 26.95 ),
				new StockEntry( 27.00, 27.32, 26.80, 26.96 ),
				new StockEntry( 27.15, 27.28, 27.01, 27.16 ),
				new StockEntry( 27.22, 27.40, 27.07, 27.11 ),
		} );

		NumberDataSet dsStockVolume = NumberDataSetImpl.create( new double[]{
				55958500,
				65801900,
				63651900,
				94646096,
				85552800,
				126184400,
				88997504,
				106303904
		} );

		// X-Series
		Series seBase = SeriesImpl.create( );
		seBase.setDataSet( dsDateValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		sdX.getSeriesPalette( ).update( 1 );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seBase );

		// Y-Series
		BarSeries bs = (BarSeries) BarSeriesImpl.create( );
		bs.setRiserOutline( null );
		bs.setDataSet( dsStockVolume );

		StockSeries ss = (StockSeries) StockSeriesImpl.create( );
		ss.setSeriesIdentifier( "Stock Price" );//$NON-NLS-1$
		ss.getLineAttributes( ).setColor( ColorDefinitionImpl.BLUE( ) );
		ss.setDataSet( dsStockValues );

		SeriesDefinition sdY1 = SeriesDefinitionImpl.create( );
		sdY1.getSeriesPalette( ).update( ColorDefinitionImpl.CYAN( ) );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY1 );
		sdY1.getSeries( ).add( ss );

		SeriesDefinition sdY2 = SeriesDefinitionImpl.create( );
		sdY2.getSeriesPalette( ).update( ColorDefinitionImpl.GREEN( ) );
		yAxisOverlay.getSeriesDefinitions( ).add( sdY2 );
		sdY2.getSeries( ).add( bs );

		return cwaStock;
	}
	public static final Chart createStackedChartAction( ){
		ChartWithAxes cwaCombination = ChartWithAxesImpl.create( );

		// Plot
		cwaCombination.setUnitSpacing( 25 );
		cwaCombination.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		Plot p = cwaCombination.getPlot( );
		p.getClientArea( )
				.setBackground( GradientImpl.create( ColorDefinitionImpl.create( 255,
						235,
						255 ),
						ColorDefinitionImpl.create( 255, 255, 225 ),
						-35,
						false ) );

		p.getClientArea( ).getInsets( ).set( 8, 8, 8, 8 );
		p.getOutline( ).setVisible( true );

		// Legend
		Legend lg = cwaCombination.getLegend( );
		lg.setBackground( ColorDefinitionImpl.YELLOW( ) );
		lg.getOutline( ).setVisible( true );

		// Title
		cwaCombination.getTitle( )
				.getLabel( )
				.getCaption( )
				.setValue( "Project Sales" );//$NON-NLS-1$ 

		// X-Axis
		Axis xAxisPrimary = cwaCombination.getPrimaryBaseAxes( )[0];
		xAxisPrimary.setType( AxisType.TEXT_LITERAL );

		xAxisPrimary.getLabel( )
				.setBackground( ColorDefinitionImpl.create( 255, 255, 235 ) );
		xAxisPrimary.getLabel( )
				.setShadowColor( ColorDefinitionImpl.create( 225, 225, 225 ) );
		xAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 25 );

		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
		xAxisPrimary.setTitlePosition( Position.BELOW_LITERAL );
		xAxisPrimary.setLabelPosition( Position.BELOW_LITERAL );

		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		xAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.create( 64, 64, 64 ) );
		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );

		xAxisPrimary.getTitle( ).getCaption( ).setValue( "Computer Components" );//$NON-NLS-1$ 
		xAxisPrimary.getOrigin( ).setType( IntersectionType.MIN_LITERAL );

		xAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.CYAN( ) );
		xAxisPrimary.getMinorGrid( ).getLineAttributes( ).setVisible( true );

		// Y-Series
		Axis yAxisPrimary = cwaCombination.getPrimaryOrthogonalAxis( xAxisPrimary );

		yAxisPrimary.setLabelPosition( Position.LEFT_LITERAL );
		yAxisPrimary.setTitlePosition( Position.LEFT_LITERAL );
		yAxisPrimary.getTitle( )
				.getCaption( )
				.setValue( "Actual Sales ($Millions)" );//$NON-NLS-1$ 

		yAxisPrimary.setType( AxisType.LINEAR_LITERAL );
		yAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 37 );

		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
		yAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		yAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.RED( ) );
		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );
		yAxisPrimary.getMinorGrid( ).setTickStyle( TickStyle.ACROSS_LITERAL );
		yAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		yAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.GREEN( ) );

		// Data Set
		String[] saTextValues = {
				"CPUs", "Keyboards", "Video Cards",//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 
				"Monitors", "Motherboards", "Memory", "Storage Devices",//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				"Media", "Printers", "Scanners"};//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 

		TextDataSet categoryValues = TextDataSetImpl.create( saTextValues );
		NumberDataSet seriesOneValues = NumberDataSetImpl.create( new double[]{
				56.99,352.95,201.95,299.95,95.95,25.45,129.33,26.5,43.5,122
		} );
		NumberDataSet seriesTwoValues = NumberDataSetImpl.create( new double[]{
				20, 35, 59, 105, 150, 37, 65, 99, 145, 185
		} );
		NumberDataSet seriesThreeValues = NumberDataSetImpl.create( new double[]{
				54.99, 21, 75.95, 39.95, 7.95, 91.22, 33.45, 25.63, 40, 13
		} );
		NumberDataSet seriesFourValues = NumberDataSetImpl.create( new double[]{
				15, 45, 43, 5, 19, 25, 35, 94, 15, 55
		} );
		NumberDataSet seriesFiveValues = NumberDataSetImpl.create( new double[]{
				43, 65, 35, 41, 45, 55, 29, 15, 85, 65
		} );
		NumberDataSet seriesSixValues = NumberDataSetImpl.create( new double[]{
				15, 45, 43, 5, 19, 25, 35, 94, 15, 55
		} );
		NumberDataSet seriesSevenValues = NumberDataSetImpl.create( new double[]{
				43, 65, 35, 41, 45, 55, 29, 15, 85, 65
		} );

		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Series
		BarSeries bs1 = (BarSeries) BarSeriesImpl.create( );
		bs1.setSeriesIdentifier( "North America" );//$NON-NLS-1$
		bs1.setDataSet( seriesOneValues );
		bs1.setRiserOutline( null );
		bs1.setRiser( RiserType.RECTANGLE_LITERAL );
		bs1.setStacked( true );

		bs1.getTriggers( )
		.add( TriggerImpl.create( TriggerCondition.ONCLICK_LITERAL,
		ActionImpl.create( ActionType.SHOW_TOOLTIP_LITERAL,
		CallBackValueImpl.create( String.valueOf( bs1.getSeriesIdentifier( 
		) ) ) ) ) );

		
		DataPoint dp = DataPointImpl.create( "(", ")", ", " );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		dp.getComponents( ).clear( );
		dp.getComponents( )
				.add( DataPointComponentImpl.create( DataPointComponentType.BASE_VALUE_LITERAL,
						null ) );
		dp.getComponents( )
				.add( DataPointComponentImpl.create( DataPointComponentType.ORTHOGONAL_VALUE_LITERAL,
						JavaNumberFormatSpecifierImpl.create( "0.00" ) ) );//$NON-NLS-1$
		bs1.setDataPoint( dp );

		BarSeries bs2 = (BarSeries) BarSeriesImpl.create( );
		bs2.setSeriesIdentifier( "South America" );//$NON-NLS-1$
		bs2.setDataSet( seriesThreeValues );
		bs2.setRiserOutline( null );
		bs2.setRiser( RiserType.RECTANGLE_LITERAL );
		bs2.setStacked( true );
		
		bs2.getTriggers( )
		.add( TriggerImpl.create( TriggerCondition.ONCLICK_LITERAL,
		ActionImpl.create( ActionType.SHOW_TOOLTIP_LITERAL,
		CallBackValueImpl.create( String.valueOf( bs1.getSeriesIdentifier( 
		) ) ) ) ) );

		dp = DataPointImpl.create( "[", "]", ", " );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		bs2.setDataPoint( dp );

		BarSeries bs3 = (BarSeries) BarSeriesImpl.create( );
		bs3.setSeriesIdentifier( "Eastern Europe" );//$NON-NLS-1$
		bs3.setDataSet( seriesFourValues );
		bs3.setRiserOutline( null );
		bs3.setRiser( RiserType.RECTANGLE_LITERAL );
		bs3.setStacked( true );

		bs3.getTriggers( )
		.add( TriggerImpl.create( TriggerCondition.ONCLICK_LITERAL,
		ActionImpl.create( ActionType.SHOW_TOOLTIP_LITERAL,
		CallBackValueImpl.create( String.valueOf( bs1.getSeriesIdentifier( 
		) ) ) ) ) );

		
		BarSeries bs4 = (BarSeries) BarSeriesImpl.create( );
		bs4.setSeriesIdentifier( "Western Europe" );//$NON-NLS-1$
		bs4.setDataSet( seriesFiveValues );
		bs4.setRiserOutline( null );
		bs4.setRiser( RiserType.RECTANGLE_LITERAL );
		bs4.setStacked( true );
		
		bs4.getTriggers( )
		.add( TriggerImpl.create( TriggerCondition.ONCLICK_LITERAL,
		ActionImpl.create( ActionType.SHOW_TOOLTIP_LITERAL,
		CallBackValueImpl.create( String.valueOf( bs1.getSeriesIdentifier( 
		) ) ) ) ) );

		
		BarSeries bs5 = (BarSeries) BarSeriesImpl.create( );
		bs5.setSeriesIdentifier( "Asia" );//$NON-NLS-1$
		bs5.setDataSet( seriesSixValues );
		bs5.setRiserOutline( null );
		bs5.setRiser( RiserType.RECTANGLE_LITERAL );

		bs5.getTriggers( )
		.add( TriggerImpl.create( TriggerCondition.ONCLICK_LITERAL,
		ActionImpl.create( ActionType.SHOW_TOOLTIP_LITERAL,
		CallBackValueImpl.create( String.valueOf( bs1.getSeriesIdentifier( 
		) ) ) ) ) );

		
		BarSeries bs6 = (BarSeries) BarSeriesImpl.create( );
		bs6.setSeriesIdentifier( "Australia" );//$NON-NLS-1$
		bs6.setDataSet( seriesSevenValues );
		bs6.setRiserOutline( null );
		bs6.setRiser( RiserType.RECTANGLE_LITERAL );
		
		bs6.getTriggers( )
		.add( TriggerImpl.create( TriggerCondition.ONCLICK_LITERAL,
		ActionImpl.create( ActionType.CALL_BACK_LITERAL,
		CallBackValueImpl.create( String.valueOf( bs1.getSeriesIdentifier( 
		) ) ) ) ) );


		LineSeries ls1 = (LineSeries) LineSeriesImpl.create( );
		ls1.setSeriesIdentifier( "Expected Growth" );//$NON-NLS-1$
		ls1.setDataSet( seriesTwoValues );
		for ( int i = 0; i < ls1.getMarkers( ).size( ); i++ )
		{
			( (Marker) ls1.getMarkers( ).get( i ) ).setType( MarkerType.BOX_LITERAL);
		}
		ls1.getLabel( ).setVisible( true );

		SeriesDefinition sdY1 = SeriesDefinitionImpl.create( );
		sdY1.getSeriesPalette( ).update( 0 );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY1 );

		SeriesDefinition sdY2 = SeriesDefinitionImpl.create( );
		sdY2.getSeriesPalette( ).update( 1 );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY2 );

		SeriesDefinition sdY3 = SeriesDefinitionImpl.create( );
		sdY3.getSeriesPalette( ).update( ColorDefinitionImpl.RED( ) );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY3 );

		SeriesDefinition sdY4 = SeriesDefinitionImpl.create( );
		sdY4.getSeriesPalette( ).update( ColorDefinitionImpl.GREEN( ) );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY4 );

		SeriesDefinition sdY5 = SeriesDefinitionImpl.create( );
		sdY5.getSeriesPalette( ).update( ColorDefinitionImpl.YELLOW( ) );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY5 );

		sdY1.getSeries( ).add( bs1 );
		sdY1.getSeries( ).add( bs2 );
		sdY2.getSeries( ).add( bs3 );
		sdY2.getSeries( ).add( bs4 );
		sdY3.getSeries( ).add( bs5 );
		sdY4.getSeries( ).add( bs6 );
		sdY5.getSeries( ).add( ls1 );

		return cwaCombination;
	}

	public static final Chart createStackedChart( )
	{
		ChartWithAxes cwaCombination = ChartWithAxesImpl.create( );

		// Plot
		cwaCombination.setUnitSpacing( 25 );
		cwaCombination.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		Plot p = cwaCombination.getPlot( );
		p.getClientArea( )
				.setBackground( GradientImpl.create( ColorDefinitionImpl.create( 255,
						235,
						255 ),
						ColorDefinitionImpl.create( 255, 255, 225 ),
						-35,
						false ) );

		p.getClientArea( ).getInsets( ).set( 8, 8, 8, 8 );
		p.getOutline( ).setVisible( true );

		// Legend
		Legend lg = cwaCombination.getLegend( );
		lg.setBackground( ColorDefinitionImpl.YELLOW( ) );
		lg.getOutline( ).setVisible( true );

		// Title
		cwaCombination.getTitle( )
				.getLabel( )
				.getCaption( )
				.setValue( "Project Sales" );//$NON-NLS-1$ 

		// X-Axis
		Axis xAxisPrimary = cwaCombination.getPrimaryBaseAxes( )[0];
		xAxisPrimary.setType( AxisType.TEXT_LITERAL );

		xAxisPrimary.getLabel().setBackground( ColorDefinitionImpl.create( 255, 255, 235 ) );
		xAxisPrimary.getLabel().setShadowColor( ColorDefinitionImpl.create( 225, 225, 225 ) );
		xAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 25 );

		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
		xAxisPrimary.setTitlePosition( Position.BELOW_LITERAL );
		xAxisPrimary.setLabelPosition( Position.BELOW_LITERAL );

		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setStyle( LineStyle.DOTTED_LITERAL );
		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setColor( ColorDefinitionImpl.create( 64, 64, 64 ) );
		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );

		xAxisPrimary.getTitle( ).getCaption( ).setValue( "Computer Components" );//$NON-NLS-1$ 
		xAxisPrimary.getOrigin( ).setType( IntersectionType.MIN_LITERAL );

		xAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.CYAN( ) );
		xAxisPrimary.getMinorGrid( ).getLineAttributes( ).setVisible( true );

		// Y-Series
		Axis yAxisPrimary = cwaCombination.getPrimaryOrthogonalAxis( xAxisPrimary );

		yAxisPrimary.setLabelPosition( Position.LEFT_LITERAL );
		yAxisPrimary.setTitlePosition( Position.LEFT_LITERAL );
		yAxisPrimary.getTitle( )
				.getCaption( )
				.setValue( "Actual Sales ($Millions)" );//$NON-NLS-1$ 

		yAxisPrimary.setType( AxisType.LINEAR_LITERAL );
		yAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 37 );

		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
		yAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		yAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.RED( ) );
		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );
		yAxisPrimary.getMinorGrid( ).setTickStyle( TickStyle.ACROSS_LITERAL );
		yAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		yAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.GREEN( ) );

		// Data Set
		String[] saTextValues = {
				"CPUs", "Keyboards", "Video Cards",//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 
				"Monitors", "Motherboards", "Memory", "Storage Devices",//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				"Media", "Printers", "Scanners"};//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 

		TextDataSet categoryValues = TextDataSetImpl.create( saTextValues );
		NumberDataSet seriesOneValues = NumberDataSetImpl.create( new double[]{
				56.99,352.95,201.95,299.95,95.95,25.45,129.33,26.5,43.5,122
		} );
		NumberDataSet seriesTwoValues = NumberDataSetImpl.create( new double[]{
				20, 35, 59, 105, 150, 37, 65, 99, 145, 185
		} );
		NumberDataSet seriesThreeValues = NumberDataSetImpl.create( new double[]{
				54.99, 21, 75.95, 39.95, 7.95, 91.22, 33.45, 25.63, 40, 13
		} );
		NumberDataSet seriesFourValues = NumberDataSetImpl.create( new double[]{
				15, 45, 43, 5, 19, 25, 35, 94, 15, 55
		} );
		NumberDataSet seriesFiveValues = NumberDataSetImpl.create( new double[]{
				43, 65, 35, 41, 45, 55, 29, 15, 85, 65
		} );
		NumberDataSet seriesSixValues = NumberDataSetImpl.create( new double[]{
				15, 45, 43, 5, 19, 25, 35, 94, 15, 55
		} );
		NumberDataSet seriesSevenValues = NumberDataSetImpl.create( new double[]{
				43, 65, 35, 41, 45, 55, 29, 15, 85, 65
		} );

		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Series
		BarSeries bs1 = (BarSeries) BarSeriesImpl.create( );
		bs1.setSeriesIdentifier( "North America" );//$NON-NLS-1$
		bs1.setDataSet( seriesOneValues );
		bs1.setRiserOutline( null );
		bs1.setRiser( RiserType.RECTANGLE_LITERAL );
		bs1.setStacked( true );
		DataPoint dp = DataPointImpl.create( "(", ")", ", " );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		dp.getComponents( ).clear( );
		dp.getComponents( )
				.add( DataPointComponentImpl.create( DataPointComponentType.BASE_VALUE_LITERAL,
						null ) );
		dp.getComponents( )
				.add( DataPointComponentImpl.create( DataPointComponentType.ORTHOGONAL_VALUE_LITERAL,
						JavaNumberFormatSpecifierImpl.create( "0.00" ) ) );//$NON-NLS-1$
		bs1.setDataPoint( dp );

		BarSeries bs2 = (BarSeries) BarSeriesImpl.create( );
		bs2.setSeriesIdentifier( "South America" );//$NON-NLS-1$
		bs2.setDataSet( seriesThreeValues );
		bs2.setRiserOutline( null );
		bs2.setRiser( RiserType.RECTANGLE_LITERAL );
		bs2.setStacked( true );
		dp = DataPointImpl.create( "[", "]", ", " );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		bs2.setDataPoint( dp );

		BarSeries bs3 = (BarSeries) BarSeriesImpl.create( );
		bs3.setSeriesIdentifier( "Eastern Europe" );//$NON-NLS-1$
		bs3.setDataSet( seriesFourValues );
		bs3.setRiserOutline( null );
		bs3.setRiser( RiserType.RECTANGLE_LITERAL );
		bs3.setStacked( true );

		BarSeries bs4 = (BarSeries) BarSeriesImpl.create( );
		bs4.setSeriesIdentifier( "Western Europe" );//$NON-NLS-1$
		bs4.setDataSet( seriesFiveValues );
		bs4.setRiserOutline( null );
		bs4.setRiser( RiserType.RECTANGLE_LITERAL );
		bs4.setStacked( true );

		BarSeries bs5 = (BarSeries) BarSeriesImpl.create( );
		bs5.setSeriesIdentifier( "Asia" );//$NON-NLS-1$
		bs5.setDataSet( seriesSixValues );
		bs5.setRiserOutline( null );
		bs5.setRiser( RiserType.RECTANGLE_LITERAL );

		BarSeries bs6 = (BarSeries) BarSeriesImpl.create( );
		bs6.setSeriesIdentifier( "Australia" );//$NON-NLS-1$
		bs6.setDataSet( seriesSevenValues );
		bs6.setRiserOutline( null );
		bs6.setRiser( RiserType.RECTANGLE_LITERAL );

		LineSeries ls1 = (LineSeries) LineSeriesImpl.create( );
		ls1.setSeriesIdentifier( "Expected Growth" );//$NON-NLS-1$
		ls1.setDataSet( seriesTwoValues );
		for ( int i = 0; i < ls1.getMarkers( ).size( ); i++ )
		{
			( (Marker) ls1.getMarkers( ).get( i ) ).setType( MarkerType.BOX_LITERAL);
		}
		ls1.getLabel( ).setVisible( true );

		SeriesDefinition sdY1 = SeriesDefinitionImpl.create( );
		sdY1.getSeriesPalette( ).update( 0 );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY1 );

		SeriesDefinition sdY2 = SeriesDefinitionImpl.create( );
		sdY2.getSeriesPalette( ).update( 1 );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY2 );

		SeriesDefinition sdY3 = SeriesDefinitionImpl.create( );
		sdY3.getSeriesPalette( ).update( ColorDefinitionImpl.RED( ) );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY3 );

		SeriesDefinition sdY4 = SeriesDefinitionImpl.create( );
		sdY4.getSeriesPalette( ).update( ColorDefinitionImpl.GREEN( ) );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY4 );

		SeriesDefinition sdY5 = SeriesDefinitionImpl.create( );
		sdY5.getSeriesPalette( ).update( ColorDefinitionImpl.YELLOW( ) );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY5 );

		sdY1.getSeries( ).add( bs1 );
		sdY1.getSeries( ).add( bs2 );
		sdY2.getSeries( ).add( bs3 );
		sdY2.getSeries( ).add( bs4 );
		sdY3.getSeries( ).add( bs5 );
		sdY4.getSeries( ).add( bs6 );
		sdY5.getSeries( ).add( ls1 );

		return cwaCombination;
	}

	public static final Chart createSDialMRegionChart( )
	{
		DialChart dChart = (DialChart) DialChartImpl.create( );
		dChart.setDialSuperimposition( false );
		dChart.setGridColumnCount( 2 );
		dChart.setSeriesThickness( 25 );

		// Title/Plot
		dChart.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		Plot p = dChart.getPlot( );
		p.getClientArea( ).setBackground( ColorDefinitionImpl.CREAM( ) );
		p.getClientArea( ).getOutline( ).setVisible( false );
		p.getOutline( ).setVisible( false );

		dChart.getTitle( )
				.getLabel( )
				.getCaption( )
				.setValue( "City Temperature" );//$NON-NLS-1$
		dChart.getTitle( ).getOutline( ).setVisible( false );

		// Legend
		Legend lg = dChart.getLegend( );
		LineAttributes lia = lg.getOutline( );
		lg.getText( ).getFont( ).setSize( 16 );
		lia.setStyle( LineStyle.SOLID_LITERAL );
		lg.getInsets( ).setLeft( 10 );
		lg.getInsets( ).setRight( 10 );
		lg.setBackground( null );
		lg.getOutline( ).setVisible( false );
		lg.setShowValue( true );
		lg.getClientArea( ).setBackground( ColorDefinitionImpl.PINK( ) );

		lg.getClientArea( ).getOutline( ).setVisible( true );
		lg.getTitle( ).getCaption( ).getFont( ).setSize( 20 );
		lg.getTitle( ).setInsets( InsetsImpl.create( 10, 10, 10, 10 ) );
		lg.getTitle( ).getCaption( ).setValue( "Weather" );//$NON-NLS-1$
		lg.getTitle( ).setVisible( true );
		lg.setTitlePosition( Position.ABOVE_LITERAL );

		TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
				"London", "Madrid", "Rome", "Moscow"} );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		NumberDataSet seriesValues = NumberDataSetImpl.create( new double[]{
				21.0, 39.0, 30.0, 10.0
		} );

		SeriesDefinition sd = SeriesDefinitionImpl.create( );
		dChart.getSeriesDefinitions( ).add( sd );
		Series seCategory = (Series) SeriesImpl.create( );

		final Fill[] fiaBase = {
				ColorDefinitionImpl.ORANGE( ),
				GradientImpl.create( ColorDefinitionImpl.create( 225, 225, 255 ),
						ColorDefinitionImpl.create( 255, 255, 225 ),
						-35,
						false ),
				ColorDefinitionImpl.CREAM( ),
				ColorDefinitionImpl.RED( ),
				ColorDefinitionImpl.GREEN( ),
				ColorDefinitionImpl.BLUE( ).brighter( ),
				ColorDefinitionImpl.CYAN( ).darker( ),
		};
		sd.getSeriesPalette( ).getEntries( ).clear( );
		for ( int i = 0; i < fiaBase.length; i++ )
		{
			sd.getSeriesPalette( ).getEntries( ).add( fiaBase[i] );
		}

		seCategory.setDataSet( categoryValues );
		sd.getSeries( ).add( seCategory );

		SeriesDefinition sdCity = SeriesDefinitionImpl.create( );

		// Dial
		DialSeries seDial = (DialSeries) DialSeriesImpl.create( );
		seDial.setDataSet( seriesValues );
		seDial.getDial( )
				.setFill( GradientImpl.create( ColorDefinitionImpl.create( 225,
						255,
						225 ),
						ColorDefinitionImpl.create( 225, 225, 255 ),
						45,
						false ) );
		NumberFormatSpecifier nfs = NumberFormatSpecifierImpl.create( );
		nfs.setSuffix( "`C" );//$NON-NLS-1$
		nfs.setFractionDigits( 0 );
		seDial.getDial( ).setFormatSpecifier( nfs );
		seDial.setSeriesIdentifier( "Temperature" );//$NON-NLS-1$
		seDial.getNeedle( ).setDecorator( LineDecorator.CIRCLE_LITERAL );
		seDial.getDial( ).setStartAngle( -45 );
		seDial.getDial( ).setStopAngle( 225 );
		seDial.getDial( )
				.getMinorGrid( )
				.getTickAttributes( )
				.setVisible( true );
		seDial.getDial( )
				.getMinorGrid( )
				.getTickAttributes( )
				.setColor( ColorDefinitionImpl.BLACK( ) );
		seDial.getDial( )
				.getMinorGrid( )
				.setTickStyle( TickStyle.BELOW_LITERAL );
		seDial.getDial( )
				.getScale( )
				.setMin( NumberDataElementImpl.create( 0 ) );
		seDial.getDial( )
				.getScale( )
				.setMax( NumberDataElementImpl.create( 90 ) );
		seDial.getDial( ).getScale( ).setStep( 10 );
		seDial.getLabel( )
				.setOutline( LineAttributesImpl.create( ColorDefinitionImpl.GREY( )
						.darker( ),
						LineStyle.SOLID_LITERAL,
						1 ) );
		seDial.getLabel( ).setBackground( ColorDefinitionImpl.GREY( )
				.brighter( ) );

		DialRegion dregion1 = DialRegionImpl.create( );
		dregion1.setFill( ColorDefinitionImpl.GREEN( ) );
		dregion1.setOutline( LineAttributesImpl.create( ColorDefinitionImpl.BLACK( )
				.darker( ),
				LineStyle.SOLID_LITERAL,
				1 ) );
		dregion1.setStartValue( NumberDataElementImpl.create( 70 ) );
		dregion1.setEndValue( NumberDataElementImpl.create( 90 ) );
		dregion1.setInnerRadius( 40 );
		dregion1.setOuterRadius( -1 );
		seDial.getDial( ).getDialRegions( ).add( dregion1 );

		DialRegion dregion2 = DialRegionImpl.create( );
		dregion2.setFill( ColorDefinitionImpl.YELLOW( ) );
		dregion2.setOutline( LineAttributesImpl.create( ColorDefinitionImpl.BLACK( )
				.darker( ),
				LineStyle.SOLID_LITERAL,
				1 ) );
		dregion2.setStartValue( NumberDataElementImpl.create( 40 ) );
		dregion2.setEndValue( NumberDataElementImpl.create( 70 ) );
		dregion2.setOuterRadius( 70 );
		seDial.getDial( ).getDialRegions( ).add( dregion2 );

		DialRegion dregion3 = DialRegionImpl.create( );
		dregion3.setFill( ColorDefinitionImpl.RED( ) );
		dregion3.setOutline( LineAttributesImpl.create( ColorDefinitionImpl.BLACK( )
				.darker( ),
				LineStyle.SOLID_LITERAL,
				1 ) );
		dregion3.setStartValue( NumberDataElementImpl.create( 0 ) );
		dregion3.setEndValue( NumberDataElementImpl.create( 40 ) );
		dregion3.setInnerRadius( 40 );
		dregion3.setOuterRadius( 90 );
		seDial.getDial( ).getDialRegions( ).add( dregion3 );

		sd.getSeriesDefinitions( ).add( sdCity );
		sdCity.getSeries( ).add( seDial );

		return dChart;
	}
	public static final Chart createSDialSRegionChart( )
	{
		DialChart dChart = (DialChart) DialChartImpl.create( );
		dChart.setDialSuperimposition( false );
		dChart.setGridColumnCount( 2 );
		dChart.setSeriesThickness( 25 );

		// Title/Plot
		dChart.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		Plot p = dChart.getPlot( );
		p.getClientArea( ).setBackground( ColorDefinitionImpl.CREAM( ) );
		p.getClientArea( ).getOutline( ).setVisible( false );
		p.getOutline( ).setVisible( false );

		dChart.getTitle( ).getLabel( ).getCaption( ).setValue( "Meter Chart" );//$NON-NLS-1$
		dChart.getTitle( ).getOutline( ).setVisible( false );

		// Legend
		Legend lg = dChart.getLegend( );
		LineAttributes lia = lg.getOutline( );
		lg.getText( ).getFont( ).setSize( 16 );
		lia.setStyle( LineStyle.SOLID_LITERAL );
		lg.getInsets( ).setLeft( 10 );
		lg.getInsets( ).setRight( 10 );
		lg.setBackground( null );
		lg.getOutline( ).setVisible( false );
		lg.setShowValue( true );
		lg.getClientArea( ).setBackground( ColorDefinitionImpl.PINK( ) );

		lg.getClientArea( ).getOutline( ).setVisible( true );
		lg.getTitle( ).getCaption( ).getFont( ).setSize( 20 );
		lg.getTitle( ).setInsets( InsetsImpl.create( 10, 10, 10, 10 ) );
		lg.setTitlePosition( Position.ABOVE_LITERAL );

		// Data Set
		TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
			"Speed"} );//$NON-NLS-1$

		SeriesDefinition sd = SeriesDefinitionImpl.create( );
		dChart.getSeriesDefinitions( ).add( sd );
		Series seCategory = (Series) SeriesImpl.create( );

		final Fill[] fiaBase = {
				ColorDefinitionImpl.ORANGE( ),
				GradientImpl.create( ColorDefinitionImpl.create( 225, 225, 255 ),
						ColorDefinitionImpl.create( 255, 255, 225 ),
						-35,
						false ),
				ColorDefinitionImpl.CREAM( ),
				ColorDefinitionImpl.RED( ),
				ColorDefinitionImpl.GREEN( ),
				ColorDefinitionImpl.BLUE( ).brighter( ),
				ColorDefinitionImpl.CYAN( ).darker( ),
		};
		sd.getSeriesPalette( ).getEntries( ).clear( );
		for ( int i = 0; i < fiaBase.length; i++ )
		{
			sd.getSeriesPalette( ).getEntries( ).add( fiaBase[i] );
		}

		seCategory.setDataSet( categoryValues );
		sd.getSeries( ).add( seCategory );

		SeriesDefinition sdCity = SeriesDefinitionImpl.create( );

		// Dial
		DialSeries seDial = (DialSeries) DialSeriesImpl.create( );
		seDial.setDataSet( NumberDataSetImpl.create( new double[]{
			60
		} ) );
		seDial.getDial( )
				.setFill( GradientImpl.create( ColorDefinitionImpl.create( 225,
						225,
						255 ),
						ColorDefinitionImpl.create( 255, 255, 225 ),
						-35,
						false ) );
		seDial.getNeedle( ).setDecorator( LineDecorator.ARROW_LITERAL );
		seDial.getDial( )
				.getMinorGrid( )
				.getTickAttributes( )
				.setVisible( true );
		seDial.getDial( )
				.getMinorGrid( )
				.getTickAttributes( )
				.setColor( ColorDefinitionImpl.RED( ) );
		seDial.getDial( )
				.getMinorGrid( )
				.setTickStyle( TickStyle.BELOW_LITERAL );
		seDial.getDial( )
				.getScale( )
				.setMin( NumberDataElementImpl.create( 0 ) );
		seDial.getDial( )
				.getScale( )
				.setMax( NumberDataElementImpl.create( 180 ) );
		seDial.getDial( ).getScale( ).setStep( 30 );
		seDial.getLabel( )
				.setOutline( LineAttributesImpl.create( ColorDefinitionImpl.GREY( )
						.darker( ),
						LineStyle.SOLID_LITERAL,
						1 ) );
		seDial.getLabel( ).setBackground( ColorDefinitionImpl.GREY( )
				.brighter( ) );

		DialRegion dregion21 = DialRegionImpl.create( );
		dregion21.setFill( ColorDefinitionImpl.GREEN( ) );
		dregion21.setStartValue( NumberDataElementImpl.create( 0 ) );
		dregion21.setEndValue( NumberDataElementImpl.create( 80 ) );
		seDial.getDial( ).getDialRegions( ).add( dregion21 );

		sd.getSeriesDefinitions( ).add( sdCity );
		sdCity.getSeries( ).add( seDial );

		return dChart;
	}

	public static final Chart actionCreateMultiBarChart( ){
		ChartWithAxes cwaBar = ChartWithAxesImpl.create( );
		cwaBar.setDimension(ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL );
//		cwaBar.setTransposed(true); 
		// Plot
		cwaBar.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		

		Plot p = cwaBar.getPlot( );
		p.getClientArea().setBackground( GradientImpl.create( ColorDefinitionImpl.create( 
							225,225,255),ColorDefinitionImpl.create( 255, 255, 225 ),-35,
							false ) );
		p.getOutline( ).setVisible( true );

		// Title
		cwaBar.getTitle().getLabel().getCaption().setValue( "2-Series Bar Chart" );//$NON-NLS-1$

		// Legend
		Legend lg = cwaBar.getLegend( );
		lg.getText( ).getFont( ).setSize( 16 );

		// X-Axis
		/************* same thing******************/
//		Axis[] axaBase = cwaBar.getPrimaryBaseAxes();
//		Axis xAxisPrimary = axaBase[0];
		Axis xAxisPrimary = cwaBar.getPrimaryBaseAxes( )[0];
		/*****************************************/
		xAxisPrimary.getTitle( ).setVisible( true );
		xAxisPrimary.setTitlePosition( Position.BELOW_LITERAL );
		xAxisPrimary.setType( AxisType.TEXT_LITERAL );
		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		/**  set position X-Axis **/
		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
		/**************************/
		xAxisPrimary.setLabelPosition( Position.BELOW_LITERAL );
		xAxisPrimary.setCategoryAxis( true );
		
		// Y-Axis

		/************* same thing******************/
//		Axis[] axaOrthogonal = cwaBar.getOrthogonalAxes(xAxisPrimary, true); 
//		Axis yAxisPrimary = axaOrthogonal[0];
		Axis yAxisPrimary = cwaBar.getPrimaryOrthogonalAxis(xAxisPrimary);
		/*****************************************/
		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
//		yAxisPrimary.setType(AxisType.TEXT_LITERAL);
//		yAxisPrimary.setCategoryAxis(true);
		
		// Data Set
		TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
				"Janvier", "Février","Mars",/*"Avril", "Mai", "Juin"*/} ); 

		LinkedHashMap<String, Double[]> mapValuesYSeries= new LinkedHashMap<String, Double[]>(); 
		mapValuesYSeries.put("Facture", new Double[]{
				new Double(25), new Double(35),new Double(15)});
		mapValuesYSeries.put("Devis", new Double[]{
				new Double(45),new Double(25), new Double(35)});
		mapValuesYSeries.put("Avoir", new Double[]{
				new Double(40),new Double(10),new Double(25)});

		
		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );
		
		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );
		
	

		// Y-Series
		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
		sdY.getSeriesPalette( ).update( 1 );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
		int count = 0;

		for (String keyMap : mapValuesYSeries.keySet()) {	
			
			NumberDataSet orthoValues = NumberDataSetImpl.create(mapValuesYSeries.get(keyMap));
			BarSeries bs  = (BarSeries) BarSeriesImpl.create();
			bs.setSeriesIdentifier(keyMap);
//			bs.getTriggers( ).add( TriggerImpl.create( TriggerCondition.ONCLICK_LITERAL,ActionImpl.create( ActionType.HIGHLIGHT_LITERAL, SeriesValueImpl.create( String.valueOf( bs.getSeriesIdentifier( ) ) ) ) ) );

//			SeriesValue seriesValue = SeriesValueImpl.create(String.valueOf(bs.getSeriesIdentifier()));
//			Action action = ActionImpl.create(ActionType.HIGHLIGHT_LITERAL,seriesValue);
//			Trigger mouseClick = TriggerImpl.create(TriggerCondition.ONCLICK_LITERAL,action);
//			bs.getTriggers().add(mouseClick);
			
//			ActionValue actionValue = CallBackValueImpl.create(String.valueOf(bs.getSeriesIdentifier()));
//			Action action = ActionImpl.create(ActionType.HIGHLIGHT_LITERAL,actionValue);
//			Trigger mouseClick = TriggerImpl.create(TriggerCondition.ONCLICK_LITERAL,action);
//			bs.getTriggers().add(mouseClick);
				
//			ActionValue actionValue = TooltipValueImpl.create(0, null);
//			Action action = ActionImpl.create(ActionType.SHOW_TOOLTIP_LITERAL, actionValue);
//			Trigger mouseOver = TriggerImpl.create(TriggerCondition.ONMOUSEOVER_LITERAL,action);
//			bs.getTriggers().add(mouseOver);
			
			bs.getTriggers()
			.add( TriggerImpl.create( TriggerCondition.ONMOUSEMOVE_LITERAL,
			//.add( TriggerImpl.create( TriggerCondition.ONCLICK_LITERAL,
			ActionImpl.create( ActionType.CALL_BACK_LITERAL,
			CallBackValueImpl.create( String.valueOf( bs.getSeriesIdentifier())))));

				
			bs.setDataSet(orthoValues);
			bs.setRiserOutline(null);
			bs.getLabel().setVisible( true );
			bs.setLabelPosition(Position.INSIDE_LITERAL);
			bs.setTranslucent(true);
			sdY.getSeries().add(bs);
			
			count ++;
		}
	

		return cwaBar;
	}
	
	public static final Chart createMultiBarChart( )
	{
		
//		org.eclipse.birt.chart.model.attribute.Image imgTiled = ImageImpl.
//								create("http://www.eclipse.org/feather.gif");


		ChartWithAxes cwaBar = ChartWithAxesImpl.create( );
		cwaBar.setDimension(ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL );
//		cwaBar.setTransposed(true); 
		// Plot
		cwaBar.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		
		Plot p = cwaBar.getPlot( );
		p.getClientArea().setBackground( GradientImpl.create( ColorDefinitionImpl.create( 
							225,225,255),ColorDefinitionImpl.create( 255, 255, 225 ),-35,
							false ) );
		p.getOutline( ).setVisible( true );

		// Title
		cwaBar.getTitle().getLabel().getCaption().setValue( "2-Series Bar Chart" );//$NON-NLS-1$

		// Legend
		Legend lg = cwaBar.getLegend( );
		lg.getText( ).getFont( ).setSize( 16 );

		// X-Axis
		/************* same thing******************/
//		Axis[] axaBase = cwaBar.getPrimaryBaseAxes();
//		Axis xAxisPrimary = axaBase[0];
		Axis xAxisPrimary = cwaBar.getPrimaryBaseAxes( )[0];
		/*****************************************/
		xAxisPrimary.getTitle( ).setVisible( true );
		xAxisPrimary.setTitlePosition( Position.BELOW_LITERAL );
		xAxisPrimary.setType( AxisType.TEXT_LITERAL );
		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		/**  set position X-Axis **/
		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
		/**************************/
		xAxisPrimary.setLabelPosition( Position.BELOW_LITERAL );
		xAxisPrimary.setCategoryAxis( true );
		
		// Y-Axis

		/************* same thing******************/
//		Axis[] axaOrthogonal = cwaBar.getOrthogonalAxes(xAxisPrimary, true); 
//		Axis yAxisPrimary = axaOrthogonal[0];
		Axis yAxisPrimary = cwaBar.getPrimaryOrthogonalAxis(xAxisPrimary);
		/*****************************************/
		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
//		yAxisPrimary.setType(AxisType.TEXT_LITERAL);
//		yAxisPrimary.setCategoryAxis(true);
		// Data Set
		TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
				"Janvier", "Février","Mars","Avril", "Mai", "Juin"} ); 
		
		NumberDataSet orthoValues1 = NumberDataSetImpl.create( new double[]{
				25, 35, 15,10,35,45
		} );
		NumberDataSet orthoValues2 = NumberDataSetImpl.create( new double[]{
				20, 30, 10,5,30,40
		} );
		NumberDataSet orthoValues3 = NumberDataSetImpl.create( new double[]{
				35, 15,10,35,45,5
		} );
		NumberDataSet orthoValues4 = NumberDataSetImpl.create( new double[]{
				20, 30,40,10,5,30
		} );
		NumberDataSet orthoValues5 = NumberDataSetImpl.create( new double[]{
				35,45,25,35,15,10
		} );
		NumberDataSet orthoValues6 = NumberDataSetImpl.create( new double[]{
				20,30,40,30,10,5
		} );
		
//		TextDataSet orthoValues1 = TextDataSetImpl.create( new String[]{
//				"25€","35€", "15€","10€","35€","45€"
//		} );
//		TextDataSet orthoValues2 = TextDataSetImpl.create(  new String[]{
//				"25€","35€", "15€","10€","35€","45€"
//		} );
//		TextDataSet orthoValues3 = TextDataSetImpl.create(  new String[]{
//				"25€","35€", "15€","10€","35€","45€"
//		} );
//		TextDataSet orthoValues4 = TextDataSetImpl.create( new String[]{
//				"25€","35€", "15€","10€","35€","45€"
//		} );
//		TextDataSet orthoValues5 = TextDataSetImpl.create(  new String[]{
//				"25€","35€", "15€","10€","35€","45€"
//		} );
//		TextDataSet orthoValues6 = TextDataSetImpl.create(  new String[]{
//				"25€","35€", "15€","10€","35€","45€"
//		} );

		LinkedHashMap<String, Double[]> mapValuesYSeries= new LinkedHashMap<String, Double[]>(); 
		
		
		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );
		
		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );
		
	

		// Y-Series
		boolean flagTranslucent = true;
		BarSeries bs1 = (BarSeries) BarSeriesImpl.create( );
		bs1.setSeriesIdentifier( "Facture" );//$NON-NLS-1$
		bs1.setDataSet( orthoValues1 );
		bs1.setRiserOutline( null );
		bs1.getLabel( ).setVisible( true );
		bs1.setLabelPosition( Position.INSIDE_LITERAL );
		bs1.setTranslucent(!flagTranslucent);
		
		ActionValue actionValue1 = CallBackValueImpl.create(String.valueOf(bs1.getSeriesIdentifier()));
		Action action1 = ActionImpl.create(ActionType.HIGHLIGHT_LITERAL,actionValue1);
		Trigger mouseClick1 = TriggerImpl.create(TriggerCondition.ONCLICK_LITERAL,action1);
		//bs.getTriggers().add(mouseClick);
		bs1.getTriggers().add(mouseClick1);
		

//		DataPoint dp = bs1.getDataPoint( ); 
//		 
//		 
//		dp.getComponents().add(DataPointComponentImpl
//		                           .create(DataPointComponentType.BASE_VALUE_LITERAL, 
//		                                   null)); // no format specified if text values are used
//		dp.getComponents().add(DataPointComponentImpl
//		                           .create(DataPointComponentType.ORTHOGONAL_VALUE_LITERAL, 
//		                                   JavaNumberFormatSpecifierImpl.create("0.00")));
		
		BarSeries bs2 = (BarSeries) BarSeriesImpl.create( );
		bs2.setSeriesIdentifier( "Boncmd" );//$NON-NLS-1$
		bs2.setDataSet( orthoValues2 );
		bs2.setRiserOutline( null );
		bs2.getLabel().setVisible( true );
		bs2.setLabelPosition( Position.OUTSIDE_LITERAL );
		bs2.setTranslucent(!flagTranslucent);
		ActionValue actionValue2 = CallBackValueImpl.create(String.valueOf(bs2.getSeriesIdentifier()));
		Action action2 = ActionImpl.create(ActionType.HIGHLIGHT_LITERAL,actionValue2);
		Trigger mouseClick2 = TriggerImpl.create(TriggerCondition.ONCLICK_LITERAL,action2);
		bs2.getTriggers().add(mouseClick2);
		
		BarSeries bs3 = (BarSeries) BarSeriesImpl.create( );
		bs3.setSeriesIdentifier( "Bonliv" );//$NON-NLS-1$
		bs3.setDataSet( orthoValues3 );
		bs3.setRiserOutline( null );
		bs3.getLabel( ).setVisible( true );
		bs3.setLabelPosition( Position.INSIDE_LITERAL );
		bs3.setTranslucent(!flagTranslucent);
		ActionValue actionValue3 = CallBackValueImpl.create(String.valueOf(bs3.getSeriesIdentifier()));
		Action action3 = ActionImpl.create(ActionType.HIGHLIGHT_LITERAL,actionValue3);
		Trigger mouseClick3 = TriggerImpl.create(TriggerCondition.ONCLICK_LITERAL,action3);
		bs3.getTriggers().add(mouseClick3);
		
		BarSeries bs4 = (BarSeries) BarSeriesImpl.create( );
		bs4.setSeriesIdentifier( "Avoir" );//$NON-NLS-1$
		bs4.setDataSet( orthoValues4 );
		bs4.setRiserOutline( null );
		bs4.getLabel( ).setVisible( true );
		bs4.setLabelPosition( Position.INSIDE_LITERAL );
		bs4.setTranslucent(!flagTranslucent);
		ActionValue actionValue4 = CallBackValueImpl.create(String.valueOf(bs4.getSeriesIdentifier()));
		Action action4 = ActionImpl.create(ActionType.HIGHLIGHT_LITERAL,actionValue4);
		Trigger mouseClick4 = TriggerImpl.create(TriggerCondition.ONCLICK_LITERAL,action4);
		bs4.getTriggers().add(mouseClick4);
		
		BarSeries bs5 = (BarSeries) BarSeriesImpl.create( );
		bs5.setSeriesIdentifier( "Proforma" );//$NON-NLS-1$
		bs5.setDataSet( orthoValues5 );
		bs5.setRiserOutline( null );
		bs5.getLabel( ).setVisible( true );
		bs5.setLabelPosition( Position.INSIDE_LITERAL );
		bs5.setTranslucent(!flagTranslucent);
		ActionValue actionValue5 = CallBackValueImpl.create(String.valueOf(bs5.getSeriesIdentifier()));
		Action action5 = ActionImpl.create(ActionType.HIGHLIGHT_LITERAL,actionValue5);
		Trigger mouseClick5 = TriggerImpl.create(TriggerCondition.ONCLICK_LITERAL,action5);
		bs5.getTriggers().add(mouseClick5);
		
		BarSeries bs6 = (BarSeries) BarSeriesImpl.create( );
		bs6.setSeriesIdentifier( "Devis" );//$NON-NLS-1$
		bs6.setDataSet( orthoValues6 );
		bs6.setRiserOutline( null );
		bs6.getLabel( ).setVisible( true );
		bs6.setLabelPosition( Position.INSIDE_LITERAL );
		bs6.setTranslucent(!flagTranslucent);
		ActionValue actionValue6 = CallBackValueImpl.create(String.valueOf(bs6.getSeriesIdentifier()));
		Action action6 = ActionImpl.create(ActionType.HIGHLIGHT_LITERAL,actionValue6);
		Trigger mouseClick6 = TriggerImpl.create(TriggerCondition.ONCLICK_LITERAL,action6);
		bs6.getTriggers().add(mouseClick6);
		
		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
		sdY.getSeriesPalette( ).update( 1 );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
		sdY.getSeries( ).add( bs1 );
		sdY.getSeries( ).add( bs2 );
		sdY.getSeries( ).add( bs3 );
		sdY.getSeries( ).add( bs4 );
		sdY.getSeries( ).add( bs5 );
		sdY.getSeries( ).add( bs6 );

		return cwaBar;
	}

	public static final Chart createMDialSRegionChart( )
	{
		DialChart dChart = (DialChart) DialChartImpl.create( );
		dChart.setDialSuperimposition( false );
		dChart.setGridColumnCount( 2 );
		dChart.setSeriesThickness( 25 );

		// Title/Plot
		dChart.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		Plot p = dChart.getPlot( );
		p.getClientArea( ).setBackground( ColorDefinitionImpl.CREAM( ) );
		p.getClientArea( ).getOutline( ).setVisible( false );
		p.getOutline( ).setVisible( false );

		dChart.getTitle( ).getLabel( ).getCaption( ).setValue( "Meter Chart" );//$NON-NLS-1$
		dChart.getTitle( ).getOutline( ).setVisible( false );

		// Legend
		Legend lg = dChart.getLegend( );
		LineAttributes lia = lg.getOutline( );
		lg.getText( ).getFont( ).setSize( 16 );
		lia.setStyle( LineStyle.SOLID_LITERAL );
		lg.getInsets( ).setLeft( 10 );
		lg.getInsets( ).setRight( 10 );
		lg.setBackground( null );
		lg.getOutline( ).setVisible( false );
		lg.setShowValue( true );
		lg.getClientArea( ).setBackground( ColorDefinitionImpl.PINK( ) );

		lg.getClientArea( ).getOutline( ).setVisible( true );
		lg.getTitle( ).getCaption( ).getFont( ).setSize( 20 );
		lg.getTitle( ).setInsets( InsetsImpl.create( 10, 10, 10, 10 ) );
		lg.setTitlePosition( Position.ABOVE_LITERAL );
		lg.setItemType( LegendItemType.SERIES_LITERAL );

		// Data Set
		TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
			"Speed"} );//$NON-NLS-1$

		SeriesDefinition sdBase = SeriesDefinitionImpl.create( );
		dChart.getSeriesDefinitions( ).add( sdBase );

		Series seCategory = (Series) SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );
		sdBase.getSeries( ).add( seCategory );

		SeriesDefinition sdOrth = SeriesDefinitionImpl.create( );

		final Fill[] fiaOrth = {
				ColorDefinitionImpl.ORANGE( ),
				ColorDefinitionImpl.RED( ),
				ColorDefinitionImpl.GREEN( )
		};

		sdOrth.getSeriesPalette( ).getEntries( ).clear( );
		for ( int i = 0; i < fiaOrth.length; i++ )
		{
			sdOrth.getSeriesPalette( ).getEntries( ).add( fiaOrth[i] );
		}

		// Dial 1
		DialSeries seDial1 = (DialSeries) DialSeriesImpl.create( );
		seDial1.setDataSet( NumberDataSetImpl.create( new double[]{
			60
		} ) );
		seDial1.getDial( )
				.setFill( GradientImpl.create( ColorDefinitionImpl.create( 225,
						225,
						255 ),
						ColorDefinitionImpl.create( 255, 255, 225 ),
						-35,
						false ) );
		seDial1.getNeedle( ).setDecorator( LineDecorator.ARROW_LITERAL );
		seDial1.getDial( )
				.getMinorGrid( )
				.getTickAttributes( )
				.setVisible( true );
		seDial1.getDial( )
				.getMinorGrid( )
				.getTickAttributes( )
				.setColor( ColorDefinitionImpl.RED( ) );
		seDial1.getDial( )
				.getMinorGrid( )
				.setTickStyle( TickStyle.BELOW_LITERAL );
		seDial1.getDial( )
				.getScale( )
				.setMin( NumberDataElementImpl.create( 0 ) );
		seDial1.getDial( )
				.getScale( )
				.setMax( NumberDataElementImpl.create( 180 ) );
		seDial1.getDial( ).getScale( ).setStep( 30 );
		seDial1.getLabel( )
				.setOutline( LineAttributesImpl.create( ColorDefinitionImpl.GREY( )
						.darker( ),
						LineStyle.SOLID_LITERAL,
						1 ) );
		seDial1.getLabel( ).setBackground( ColorDefinitionImpl.GREY( )
				.brighter( ) );
		seDial1.setSeriesIdentifier( "Speed 1" );//$NON-NLS-1$

		// Dail 2
		DialSeries seDial2 = (DialSeries) DialSeriesImpl.create( );
		seDial2.setDataSet( NumberDataSetImpl.create( new double[]{
			90
		} ) );
		seDial2.getDial( )
				.setFill( GradientImpl.create( ColorDefinitionImpl.create( 225,
						225,
						255 ),
						ColorDefinitionImpl.create( 255, 255, 225 ),
						-35,
						false ) );
		seDial2.getNeedle( ).setDecorator( LineDecorator.ARROW_LITERAL );
		seDial2.getDial( )
				.getMinorGrid( )
				.getTickAttributes( )
				.setVisible( true );
		seDial2.getDial( )
				.getMinorGrid( )
				.getTickAttributes( )
				.setColor( ColorDefinitionImpl.RED( ) );
		seDial2.getDial( )
				.getMinorGrid( )
				.setTickStyle( TickStyle.BELOW_LITERAL );
		seDial2.getDial( )
				.getScale( )
				.setMin( NumberDataElementImpl.create( 0 ) );
		seDial2.getDial( )
				.getScale( )
				.setMax( NumberDataElementImpl.create( 180 ) );
		seDial2.getDial( ).getScale( ).setStep( 30 );
		seDial2.getLabel( )
				.setOutline( LineAttributesImpl.create( ColorDefinitionImpl.GREY( )
						.darker( ),
						LineStyle.SOLID_LITERAL,
						1 ) );
		seDial2.getLabel( ).setBackground( ColorDefinitionImpl.GREY( )
				.brighter( ) );
		seDial2.setSeriesIdentifier( "Speed 2" );//$NON-NLS-1$

		// Dial 3
		DialSeries seDial3 = (DialSeries) DialSeriesImpl.create( );
		seDial3.setDataSet( NumberDataSetImpl.create( new double[]{
			160
		} ) );
		seDial3.getDial( )
				.setFill( GradientImpl.create( ColorDefinitionImpl.create( 225,
						225,
						255 ),
						ColorDefinitionImpl.create( 255, 255, 225 ),
						-35,
						false ) );
		seDial3.getNeedle( ).setDecorator( LineDecorator.ARROW_LITERAL );
		seDial3.getDial( )
				.getMinorGrid( )
				.getTickAttributes( )
				.setVisible( true );
		seDial3.getDial( )
				.getMinorGrid( )
				.getTickAttributes( )
				.setColor( ColorDefinitionImpl.RED( ) );
		seDial3.getDial( )
				.getMinorGrid( )
				.setTickStyle( TickStyle.BELOW_LITERAL );
		seDial3.getDial( )
				.getScale( )
				.setMin( NumberDataElementImpl.create( 0 ) );
		seDial3.getDial( )
				.getScale( )
				.setMax( NumberDataElementImpl.create( 180 ) );
		seDial3.getDial( ).getScale( ).setStep( 30 );
		seDial3.getLabel( )
				.setOutline( LineAttributesImpl.create( ColorDefinitionImpl.GREY( )
						.darker( ),
						LineStyle.SOLID_LITERAL,
						1 ) );
		seDial3.getLabel( ).setBackground( ColorDefinitionImpl.GREY( )
				.brighter( ) );
		seDial3.setSeriesIdentifier( "Speed 3" );//$NON-NLS-1$

		dChart.setDialSuperimposition( true );
		sdBase.getSeriesDefinitions( ).add( sdOrth );
		sdOrth.getSeries( ).add( seDial1 );
		sdOrth.getSeries( ).add( seDial2 );
		sdOrth.getSeries( ).add( seDial3 );

		return dChart;
	}

	public static final Chart createMDialMRegionChart( )
	{
		DialChart dChart = (DialChart) DialChartImpl.create( );
		dChart.setDialSuperimposition( false );
		dChart.setGridColumnCount( 2 );
		dChart.setSeriesThickness( 25 );

		// Title/Plot
		dChart.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		Plot p = dChart.getPlot( );
		p.getClientArea( ).setBackground( ColorDefinitionImpl.CREAM( ) );
		p.getClientArea( ).getOutline( ).setVisible( false );
		p.getOutline( ).setVisible( false );

		dChart.getTitle( ).getLabel( ).getCaption( ).setValue( "Meter Chart" );//$NON-NLS-1$
		dChart.getTitle( ).getOutline( ).setVisible( false );

		// Legend
		Legend lg = dChart.getLegend( );
		LineAttributes lia = lg.getOutline( );
		lg.getText( ).getFont( ).setSize( 16 );
		lia.setStyle( LineStyle.SOLID_LITERAL );
		lg.getInsets( ).setLeft( 10 );
		lg.getInsets( ).setRight( 10 );
		lg.setBackground( null );
		lg.getOutline( ).setVisible( false );
		lg.setShowValue( true );
		lg.getClientArea( ).setBackground( ColorDefinitionImpl.PINK( ) );

		lg.getClientArea( ).getOutline( ).setVisible( true );
		lg.getTitle( ).getCaption( ).getFont( ).setSize( 20 );
		lg.getTitle( ).setInsets( InsetsImpl.create( 10, 10, 10, 10 ) );
		lg.setTitlePosition( Position.ABOVE_LITERAL );
		lg.setPosition( Position.BELOW_LITERAL );
		lg.setItemType( LegendItemType.SERIES_LITERAL );

		TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
			"Moto"} );//$NON-NLS-1$

		SeriesDefinition sd = SeriesDefinitionImpl.create( );
		dChart.getSeriesDefinitions( ).add( sd );
		Series seCategory = (Series) SeriesImpl.create( );

		seCategory.setDataSet( categoryValues );
		sd.getSeries( ).add( seCategory );

		SeriesDefinition sdCity = SeriesDefinitionImpl.create( );

		final Fill[] fiaOrth = {
				ColorDefinitionImpl.PINK( ),
				ColorDefinitionImpl.ORANGE( ),
				ColorDefinitionImpl.WHITE( )
		};
		sdCity.getSeriesPalette( ).getEntries( ).clear( );
		for ( int i = 0; i < fiaOrth.length; i++ )
		{
			sdCity.getSeriesPalette( ).getEntries( ).add( fiaOrth[i] );
		}

		// Dial 1
		DialSeries seDial1 = (DialSeries) DialSeriesImpl.create( );
		seDial1.setDataSet( NumberDataSetImpl.create( new double[]{
			20
		} ) );
		seDial1.getDial( )
				.setFill( GradientImpl.create( ColorDefinitionImpl.create( 225,
						255,
						225 ),
						ColorDefinitionImpl.create( 225, 225, 255 ),
						45,
						false ) );
		seDial1.setSeriesIdentifier( "Temperature" );//$NON-NLS-1$
		seDial1.getNeedle( ).setDecorator( LineDecorator.CIRCLE_LITERAL );
		seDial1.getDial( ).setStartAngle( -45 );
		seDial1.getDial( ).setStopAngle( 225 );
		seDial1.getDial( )
				.getMinorGrid( )
				.getTickAttributes( )
				.setVisible( true );
		seDial1.getDial( )
				.getMinorGrid( )
				.getTickAttributes( )
				.setColor( ColorDefinitionImpl.BLACK( ) );
		seDial1.getDial( )
				.getMinorGrid( )
				.setTickStyle( TickStyle.BELOW_LITERAL );
		seDial1.getDial( )
				.getScale( )
				.setMin( NumberDataElementImpl.create( 0 ) );
		seDial1.getDial( )
				.getScale( )
				.setMax( NumberDataElementImpl.create( 90 ) );
		seDial1.getDial( ).getScale( ).setStep( 10 );
		seDial1.getLabel( )
				.setOutline( LineAttributesImpl.create( ColorDefinitionImpl.GREY( )
						.darker( ),
						LineStyle.SOLID_LITERAL,
						1 ) );
		seDial1.getLabel( ).setBackground( ColorDefinitionImpl.GREY( )
				.brighter( ) );

		DialRegion dregion1 = DialRegionImpl.create( );
		dregion1.setFill( ColorDefinitionImpl.GREEN( ) );
		dregion1.setOutline( LineAttributesImpl.create( ColorDefinitionImpl.BLACK( )
				.darker( ),
				LineStyle.SOLID_LITERAL,
				1 ) );
		dregion1.setStartValue( NumberDataElementImpl.create( 70 ) );
		dregion1.setEndValue( NumberDataElementImpl.create( 90 ) );
		dregion1.setInnerRadius( 40 );
		dregion1.setOuterRadius( -1 );
		seDial1.getDial( ).getDialRegions( ).add( dregion1 );

		DialRegion dregion2 = DialRegionImpl.create( );
		dregion2.setFill( ColorDefinitionImpl.YELLOW( ) );
		dregion2.setOutline( LineAttributesImpl.create( ColorDefinitionImpl.BLACK( )
				.darker( ),
				LineStyle.SOLID_LITERAL,
				1 ) );
		dregion2.setStartValue( NumberDataElementImpl.create( 40 ) );
		dregion2.setEndValue( NumberDataElementImpl.create( 70 ) );
		dregion2.setOuterRadius( 70 );
		seDial1.getDial( ).getDialRegions( ).add( dregion2 );

		DialRegion dregion3 = DialRegionImpl.create( );
		dregion3.setFill( ColorDefinitionImpl.RED( ) );
		dregion3.setOutline( LineAttributesImpl.create( ColorDefinitionImpl.BLACK( )
				.darker( ),
				LineStyle.SOLID_LITERAL,
				1 ) );
		dregion3.setStartValue( NumberDataElementImpl.create( 0 ) );
		dregion3.setEndValue( NumberDataElementImpl.create( 40 ) );
		dregion3.setInnerRadius( 40 );
		dregion3.setOuterRadius( 90 );
		seDial1.getDial( ).getDialRegions( ).add( dregion3 );

		// Dial 2
		DialSeries seDial2 = (DialSeries) DialSeriesImpl.create( );
		seDial2.setDataSet( NumberDataSetImpl.create( new double[]{
			58
		} ) );
		seDial2.getDial( )
				.setFill( GradientImpl.create( ColorDefinitionImpl.create( 225,
						255,
						225 ),
						ColorDefinitionImpl.create( 225, 225, 255 ),
						45,
						false ) );
		seDial2.setSeriesIdentifier( "Wind Speed" );//$NON-NLS-1$
		seDial2.getNeedle( ).setDecorator( LineDecorator.CIRCLE_LITERAL );
		seDial2.getDial( ).setStartAngle( -45 );
		seDial2.getDial( ).setStopAngle( 225 );
		seDial2.getDial( )
				.getMinorGrid( )
				.getTickAttributes( )
				.setVisible( true );
		seDial2.getDial( )
				.getMinorGrid( )
				.getTickAttributes( )
				.setColor( ColorDefinitionImpl.BLACK( ) );
		seDial2.getDial( )
				.getMinorGrid( )
				.setTickStyle( TickStyle.BELOW_LITERAL );
		seDial2.getDial( )
				.getScale( )
				.setMin( NumberDataElementImpl.create( 0 ) );
		seDial2.getDial( )
				.getScale( )
				.setMax( NumberDataElementImpl.create( 90 ) );
		seDial2.getDial( ).getScale( ).setStep( 10 );
		seDial2.getLabel( )
				.setOutline( LineAttributesImpl.create( ColorDefinitionImpl.GREY( )
						.darker( ),
						LineStyle.SOLID_LITERAL,
						1 ) );
		seDial2.getLabel( ).setBackground( ColorDefinitionImpl.GREY( )
				.brighter( ) );

		seDial2.getDial( ).getDialRegions( ).add( dregion1 );
		seDial2.getDial( ).getDialRegions( ).add( dregion2 );
		seDial2.getDial( ).getDialRegions( ).add( dregion3 );

		// Dial 3
		DialSeries seDial3 = (DialSeries) DialSeriesImpl.create( );
		seDial3.setDataSet( NumberDataSetImpl.create( new double[]{
			80
		} ) );
		seDial3.getDial( )
				.setFill( GradientImpl.create( ColorDefinitionImpl.create( 225,
						255,
						225 ),
						ColorDefinitionImpl.create( 225, 225, 255 ),
						45,
						false ) );
		seDial3.setSeriesIdentifier( "Viscosity" );//$NON-NLS-1$
		seDial3.getNeedle( ).setDecorator( LineDecorator.CIRCLE_LITERAL );
		seDial3.getDial( ).setStartAngle( -45 );
		seDial3.getDial( ).setStopAngle( 225 );
		seDial3.getDial( )
				.getMinorGrid( )
				.getTickAttributes( )
				.setVisible( true );
		seDial3.getDial( )
				.getMinorGrid( )
				.getTickAttributes( )
				.setColor( ColorDefinitionImpl.BLACK( ) );
		seDial3.getDial( )
				.getMinorGrid( )
				.setTickStyle( TickStyle.BELOW_LITERAL );
		seDial3.getDial( )
				.getScale( )
				.setMin( NumberDataElementImpl.create( 0 ) );
		seDial3.getDial( )
				.getScale( )
				.setMax( NumberDataElementImpl.create( 90 ) );
		seDial3.getDial( ).getScale( ).setStep( 10 );
		seDial3.getLabel( )
				.setOutline( LineAttributesImpl.create( ColorDefinitionImpl.GREY( )
						.darker( ),
						LineStyle.SOLID_LITERAL,
						1 ) );
		seDial3.getLabel( ).setBackground( ColorDefinitionImpl.GREY( )
				.brighter( ) );

		seDial3.getDial( ).getDialRegions( ).add( dregion1 );
		seDial3.getDial( ).getDialRegions( ).add( dregion2 );
		seDial3.getDial( ).getDialRegions( ).add( dregion3 );

		dChart.setDialSuperimposition( true );
		sd.getSeriesDefinitions( ).add( sdCity );
		sdCity.getSeries( ).add( seDial1 );
		sdCity.getSeries( ).add( seDial2 );
		sdCity.getSeries( ).add( seDial3 );

		return dChart;
	}

	public static final Chart createLineChart( )
	{
		ChartWithAxes cwaLine = ChartWithAxesImpl.create( );

		// Plot
		cwaLine.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		Plot p = cwaLine.getPlot( );
		p.getClientArea( ).setBackground( ColorDefinitionImpl.create( 255,
				255,
				225 ) );

		// Title
		cwaLine.getTitle( ).getLabel( ).getCaption( ).setValue( "Line Chart" );//$NON-NLS-1$

		// Legend
		cwaLine.getLegend( ).setVisible( false );

		// X-Axis
		Axis xAxisPrimary = cwaLine.getPrimaryBaseAxes( )[0];
		xAxisPrimary.setType( AxisType.TEXT_LITERAL );
		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
		xAxisPrimary.getTitle( ).setVisible( true );

		// Y-Axis
		Axis yAxisPrimary = cwaLine.getPrimaryOrthogonalAxis( xAxisPrimary );
		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );

		// Data Set
		TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
				"Item 1", "Item 2", "Item 3"} );//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		NumberDataSet orthoValues = NumberDataSetImpl.create( new double[]{
				25, 35, 15
		} );

		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );
		SeriesDefinition sdX = SeriesDefinitionImpl.create( );

		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Sereis
		LineSeries ls = (LineSeries) LineSeriesImpl.create( );
		ls.setDataSet( orthoValues );
		ls.getLineAttributes( ).setColor( ColorDefinitionImpl.CREAM( ) );
		for ( int i = 0; i < ls.getMarkers( ).size( ); i++ )
		{
			( (Marker) ls.getMarkers( ).get( i ) ).setType( MarkerType.TRIANGLE_LITERAL);
		}
		ls.getLabel( ).setVisible( true );

		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
		sdY.getSeriesPalette( ).update( -2 );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
		sdY.getSeries( ).add( ls );

		return cwaLine;
	}

	public final static Chart createCFStockChart( )
	{
		ChartWithAxes cwaStock = ChartWithAxesImpl.create( );

		// Title
		cwaStock.getTitle( ).getLabel( ).getCaption( ).setValue( "Stock Chart" );//$NON-NLS-1$
		TitleBlock tb = cwaStock.getTitle( );
		tb.setBackground( GradientImpl.create( ColorDefinitionImpl.create( 0,
				128,
				0 ), ColorDefinitionImpl.create( 128, 0, 0 ), 0, false ) );
		tb.getLabel( ).getCaption( ).setColor( ColorDefinitionImpl.WHITE( ) );

		// Plot
		cwaStock.getBlock( )
				.setBackground( GradientImpl.create( ColorDefinitionImpl.create( 196,
						196,
						196 ),
						ColorDefinitionImpl.WHITE( ),
						90,
						false ) );
		cwaStock.getPlot( ).getClientArea( ).getInsets( ).set( 10, 10, 10, 10 );

		// Legend
		cwaStock.getLegend( ).setBackground( ColorDefinitionImpl.ORANGE( ) );

		// X-Axis
		Axis xAxisPrimary = ( (ChartWithAxesImpl) cwaStock ).getPrimaryBaseAxes( )[0];

		xAxisPrimary.getTitle( ).getCaption( ).setValue( "X Axis" );//$NON-NLS-1$
		xAxisPrimary.getTitle( )
				.getCaption( )
				.setColor( ColorDefinitionImpl.RED( ) );
		xAxisPrimary.getTitle( ).getCaption( ).setValue( "Date" );//$NON-NLS-1$
		xAxisPrimary.setTitlePosition( Position.ABOVE_LITERAL );

		xAxisPrimary.getLabel( )
				.getCaption( )
				.setColor( ColorDefinitionImpl.RED( ) );
		xAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 65 );
		xAxisPrimary.setLabelPosition( Position.ABOVE_LITERAL );

		xAxisPrimary.setType( AxisType.DATE_TIME_LITERAL );
		xAxisPrimary.getOrigin( ).setType( IntersectionType.MAX_LITERAL );

		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.ABOVE_LITERAL );
		xAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.create( 255, 196, 196 ) );
		xAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );

		xAxisPrimary.setCategoryAxis( true );

		// Y-Axis
		Axis yAxisPrimary = ( (ChartWithAxesImpl) cwaStock ).getPrimaryOrthogonalAxis( xAxisPrimary );

		yAxisPrimary.getLabel( ).getCaption( ).setValue( "Price Axis" );//$NON-NLS-1$
		yAxisPrimary.getLabel( )
				.getCaption( )
				.setColor( ColorDefinitionImpl.BLUE( ) );
		yAxisPrimary.setLabelPosition( Position.LEFT_LITERAL );

		yAxisPrimary.getTitle( )
				.getCaption( )
				.setValue( "Microsoft ($ Stock Price)" );//$NON-NLS-1$
		yAxisPrimary.getTitle( )
				.getCaption( )
				.setColor( ColorDefinitionImpl.BLUE( ) );
		yAxisPrimary.setTitlePosition( Position.LEFT_LITERAL );

		yAxisPrimary.getScale( ).setMin( NumberDataElementImpl.create( 24.5 ) );
		yAxisPrimary.getScale( ).setMax( NumberDataElementImpl.create( 27.5 ) );
		yAxisPrimary.getScale( ).setStep( 0.5 );

		yAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.create( 196, 196, 255 ) );
		yAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );
		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );

		yAxisPrimary.setType( AxisType.LINEAR_LITERAL );
		yAxisPrimary.getOrigin( ).setType( IntersectionType.MIN_LITERAL );

		// Data Set
		DateTimeDataSet dsDateValues = DateTimeDataSetImpl.create( new Calendar[]{
				new CDateTime( 2004, 12, 27 ),
				new CDateTime( 2004, 12, 23 ),
				new CDateTime( 2004, 12, 22 ),
				new CDateTime( 2004, 12, 21 ),
				new CDateTime( 2004, 12, 20 ),
				new CDateTime( 2004, 12, 17 ),
				new CDateTime( 2004, 12, 16 ),
				new CDateTime( 2004, 12, 15 )
		} );

//		StockEntry[] dd = new StockEntry[]{new StockEntry( 27.01, 27.10, 26.82, 26.85 )};
//		StockDataSet dsStockValues = StockDataSetImpl.create(dd);
		StockDataSet dsStockValues = StockDataSetImpl.create( new StockEntry[]{
				new StockEntry( 27.01, 27.10, 26.82, 26.85 ),
				new StockEntry( 26.87, 27.15, 26.83, 27.01 ),
				new StockEntry( 26.84, 27.15, 26.78, 26.97 ),
				new StockEntry( 27.00, 27.17, 26.94, 27.07 ),
				new StockEntry( 27.01, 27.15, 26.89, 26.95 ),
				new StockEntry( 27.00, 27.32, 26.80, 26.96 ),
				new StockEntry( 27.15, 27.28, 27.01, 27.16 ),
				new StockEntry( 27.22, 27.40, 27.07, 27.11 ),
		} );
	
		// X-Series
		Series seBase = SeriesImpl.create( );
		seBase.setDataSet( dsDateValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		sdX.getSeriesPalette( ).update( 1 );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seBase );

		// Y-Series
		StockSeries ss = (StockSeries) StockSeriesImpl.create( );
		ss.setSeriesIdentifier( "Stock Price" );//$NON-NLS-1$
		ss.getLineAttributes( ).setColor( ColorDefinitionImpl.BLUE( ) );
		ss.setDataSet( dsStockValues );
		ss.setCurveFitting( CurveFittingImpl.create( ) );

		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
		sdY.getSeriesPalette( ).update( ColorDefinitionImpl.CYAN( ) );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
		sdY.getSeries( ).add( ss );


		

		return cwaStock;
	}

	public static final Chart createCFBarChart( )
	{
		ChartWithAxes cwaBar = ChartWithAxesImpl.create( );

		// Plot
		cwaBar.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		cwaBar.getBlock( ).getOutline( ).setVisible( true );
		Plot p = cwaBar.getPlot( );
		p.getClientArea( ).setBackground( ColorDefinitionImpl.create( 255,
				255,
				225 ) );
		p.getOutline( ).setVisible( false );

		// Title
		cwaBar.getTitle( ).getLabel( ).getCaption( ).setValue( "Bar Chart" );//$NON-NLS-1$

		// Legend
		Legend lg = cwaBar.getLegend( );
		lg.getText( ).getFont( ).setSize( 16 );
		lg.setItemType( LegendItemType.CATEGORIES_LITERAL );

		// X-Axis
		Axis xAxisPrimary = cwaBar.getPrimaryBaseAxes( )[0];

		xAxisPrimary.setType( AxisType.TEXT_LITERAL );
		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
		xAxisPrimary.getTitle( ).setVisible( true );

		// Y-Axis
		Axis yAxisPrimary = cwaBar.getPrimaryOrthogonalAxis( xAxisPrimary );
		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
		yAxisPrimary.setType( AxisType.LINEAR_LITERAL );
		yAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 90 );

		// Data Set
		TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
				"Item 1", "Item 2", "Item 3"} );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		NumberDataSet orthoValues = NumberDataSetImpl.create( new double[]{
				25, 35, 15
		} );

		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		sdX.getSeriesPalette( ).update( 0 );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Series
		BarSeries bs = (BarSeries) BarSeriesImpl.create( );
		bs.setDataSet( orthoValues );
		bs.setRiserOutline( null );
		bs.getLabel( ).setVisible( true );
		bs.setLabelPosition( Position.INSIDE_LITERAL );
		bs.setCurveFitting( CurveFittingImpl.create( ) );

		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
		sdY.getSeries( ).add( bs );

		return cwaBar;
	}

	public static final Chart createCFAreaChart( )
	{
		ChartWithAxes cwaArea = ChartWithAxesImpl.create( );

		// Plot/Title
		cwaArea.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		Plot p = cwaArea.getPlot( );
		p.getClientArea( ).setBackground( ColorDefinitionImpl.create( 225,
				225,
				225 ) );
		cwaArea.getTitle( ).getLabel( ).getCaption( ).setValue( "Area Chart" );//$NON-NLS-1$
		cwaArea.getTitle( ).setVisible( true );

		// Legend
		Legend lg = cwaArea.getLegend( );
		LineAttributes lia = lg.getOutline( );
		lg.getText( ).getFont( ).setSize( 16 );
		lia.setStyle( LineStyle.SOLID_LITERAL );
		lg.getInsets( ).set( 10, 5, 0, 0 );
		lg.getOutline( ).setVisible( false );
		lg.setAnchor( Anchor.NORTH_LITERAL );

		// X-Axis
		Axis xAxisPrimary = cwaArea.getPrimaryBaseAxes( )[0];
		xAxisPrimary.setType( AxisType.TEXT_LITERAL );
		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getMajorGrid( )
				.setLineAttributes( LineAttributesImpl.create( ColorDefinitionImpl.BLUE( ),
						LineStyle.SOLID_LITERAL,
						1 ) );
		xAxisPrimary.getMinorGrid( ).getLineAttributes( ).setVisible( true );
		xAxisPrimary.getOrigin( ).setType( IntersectionType.MIN_LITERAL );
		xAxisPrimary.getTitle( ).getCaption( ).setValue( "Month" );//$NON-NLS-1$
		xAxisPrimary.getTitle( ).setVisible( true );
		xAxisPrimary.getTitle( ).getCaption( ).getFont( ).setRotation( 0 );
		xAxisPrimary.getLabel( ).setVisible( true );

		// Y-Axis
		Axis yAxisPrimary = cwaArea.getPrimaryOrthogonalAxis( xAxisPrimary );
		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
		yAxisPrimary.getMajorGrid( )
				.setLineAttributes( LineAttributesImpl.create( ColorDefinitionImpl.BLACK( ),
						LineStyle.SOLID_LITERAL,
						1 ) );
		yAxisPrimary.getMinorGrid( ).getLineAttributes( ).setVisible( true );
		yAxisPrimary.setPercent( false );
		yAxisPrimary.getTitle( ).getCaption( ).setValue( "Net Profit" );//$NON-NLS-1$
		yAxisPrimary.getTitle( ).setVisible( true );
		yAxisPrimary.getTitle( ).getCaption( ).getFont( ).setRotation( 90 );
		yAxisPrimary.getLabel( ).setVisible( true );

		MarkerLine ml = MarkerLineImpl.create( yAxisPrimary,
				NumberDataElementImpl.create( 2 ) );
		yAxisPrimary.getMarkerLines( ).add( ml );

		// Data Set
		TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
				"Jan.", "Feb.", "Mar.", "Apr", "May"} ); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$//$NON-NLS-5$	
		NumberDataSet orthoValues = NumberDataSetImpl.create( new double[]{
				14.32, -19.5, 8.38, 0.34, 9.22
		} );

		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		sdX.getSeriesPalette( ).update( 0 );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Series
		AreaSeries as = (AreaSeries) AreaSeriesImpl.create( );
		as.setSeriesIdentifier( "Series" );//$NON-NLS-1$
		as.setDataSet( orthoValues );
		as.setTranslucent( true );
		as.getLineAttributes( ).setColor( ColorDefinitionImpl.BLUE( ) );
		as.getLabel( ).setVisible( true );
		as.setCurve( true );

		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
		sdY.getSeriesPalette( ).update( 1 );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
		sdY.getSeries( ).add( as );

		return cwaArea;
	}

	public static final Chart createHSChart( )
	{
		ChartWithAxes cwaBar = ChartWithAxesImpl.create( );
		cwaBar.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		cwaBar.getInteractivity( )
		.setLegendBehavior( LegendBehaviorType.HIGHLIGHT_SERIE_LITERAL );
		Plot p = cwaBar.getPlot( );
		p.getClientArea( ).setBackground( ColorDefinitionImpl.create( 255,
				255,
				225 ) );
		cwaBar.getTitle( )
		.getLabel( )
		.getCaption( )
		.setValue( "Click \"Items\" to Highlight Seires" ); //$NON-NLS-1$
		cwaBar.setUnitSpacing( 20 );

		Legend lg = cwaBar.getLegend( );
		LineAttributes lia = lg.getOutline( );
		lg.getText( ).getFont( ).setSize( 16 );
		lia.setStyle( LineStyle.SOLID_LITERAL );
		lg.getInsets( ).set( 10, 5, 0, 0 );
		lg.getOutline( ).setVisible( false );
		lg.setAnchor( Anchor.NORTH_LITERAL );
		lg.setItemType( LegendItemType.CATEGORIES_LITERAL );

		// X-Axis
		Axis xAxisPrimary = cwaBar.getPrimaryBaseAxes( )[0];

		xAxisPrimary.setType( AxisType.TEXT_LITERAL );
		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
		xAxisPrimary.getTitle( ).setVisible( true );

		// Y-Axis
		Axis yAxisPrimary = cwaBar.getPrimaryOrthogonalAxis( xAxisPrimary );
		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
		yAxisPrimary.setType( AxisType.LINEAR_LITERAL );
		yAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 90 );

		// Data Set
		TextDataSet categoryValues = TextDataSetImpl.create( new String []{
				"Item 1", "Item 2", "Item 3"} ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		NumberDataSet orthoValues = NumberDataSetImpl.create( new double[]{
				25, 35, 15
		} );

		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		sdX.getSeriesPalette( ).update( 0 );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Series
		BarSeries bs = (BarSeries) BarSeriesImpl.create( );
		bs.setDataSet( orthoValues );
		bs.setRiserOutline( null );
		bs.setSeriesIdentifier( "Highlight" ); //$NON-NLS-1$
		bs.getLabel( ).setVisible( true );
		bs.setLabelPosition( Position.INSIDE_LITERAL );
//		bs.getTriggers( )
//		.add( TriggerImpl.create( TriggerCondition.ONCLICK_LITERAL,
//				ActionImpl.create( ActionType.SHOW_TOOLTIP_LITERAL,
//						SeriesValueImpl.create( String.valueOf( bs.getSeriesIdentifier( ) ) ) ) ) );
		
		bs.getTriggers( )
		.add( TriggerImpl.create( TriggerCondition.ONCLICK_LITERAL,
		ActionImpl.create( ActionType.CALL_BACK_LITERAL,
		CallBackValueImpl.create( String.valueOf( bs.getSeriesIdentifier( ) ) 
		) ) ) );


		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
		sdY.getSeries( ).add( bs );

		return cwaBar;
	}


	public static final Chart createStackedBarAction(){


		ChartWithAxes cwaBar = ChartWithAxesImpl.create();


		cwaBar.setType("Bar Chart"); //$NON-NLS-1$


		cwaBar.setSubType("Stacked"); //$NON-NLS-1$


		// Plot


		cwaBar.getBlock().setBackground(ColorDefinitionImpl.WHITE());


		cwaBar.getBlock().getOutline().setVisible(true);


		Plot p = cwaBar.getPlot();


		p.getClientArea().setBackground(ColorDefinitionImpl.create(255,255,225));
		// Title
		cwaBar.getTitle().getLabel().getCaption().setValue("Stacked Bar Chart"); //$NON-NLS-1$
		// Legend
		Legend lg = cwaBar.getLegend();
		lg.setItemType(LegendItemType.CATEGORIES_LITERAL);
		//Add Script
		cwaBar.setScript("function beforeGeneration( cm, icsc )"
				+ "{importPackage(Packages.org.eclipse.birt.chart.model.attribute); "
				+ " importPackage(Packages.org.eclipse.birt.chart.model.attribute.impl); "
				+ " cm.getLegend().getOutline( ).setStyle( LineStyle.DASH_DOTTED_LITERAL );"
				+ " cm.getLegend().getOutline( ).setColor( ColorDefinitionImpl.GREEN() );"
				+ " cm.getLegend().getOutline( ).setVisible( true );} ");


		// X-Axis
		Axis xAxisPrimary = cwaBar.getPrimaryBaseAxes()[0];
		xAxisPrimary.setType(AxisType.TEXT_LITERAL);
		xAxisPrimary.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);
		xAxisPrimary.getOrigin().setType(IntersectionType.MIN_LITERAL);
		// Y-Axis
		Axis yAxisPrimary = cwaBar.getPrimaryOrthogonalAxis(xAxisPrimary);
		yAxisPrimary.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);
		yAxisPrimary.setType(AxisType.LINEAR_LITERAL);
		yAxisPrimary.setLabelPosition(Position.RIGHT_LITERAL);
		yAxisPrimary.getLabel().getCaption().getFont().setRotation(45);
		// Data Set
		TextDataSet categoryValues = TextDataSetImpl.create(new String[] {
				"Item 1a", "Item 2", "Item 3", "Item 4", "Item 5" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		NumberDataSet orthoValues1 = NumberDataSetImpl.create(new double[] {
				25, 35, 15, 5, 20});
		NumberDataSet orthoValues2 = NumberDataSetImpl.create(new double[] {
				5, 10, 25, 10, 5});
		SampleData sd = DataFactory.eINSTANCE.createSampleData();
		BaseSampleData sdBase = DataFactory.eINSTANCE.createBaseSampleData();
		sdBase.setDataSetRepresentation("");//$NON-NLS-1$
		sd.getBaseSampleData().add(sdBase);
		OrthogonalSampleData sdOrthogonal1 = DataFactory.eINSTANCE
		.createOrthogonalSampleData();
		sdOrthogonal1.setDataSetRepresentation("");//$NON-NLS-1$
		sdOrthogonal1.setSeriesDefinitionIndex(0);
		sd.getOrthogonalSampleData().add(sdOrthogonal1);

		OrthogonalSampleData sdOrthogonal2 = DataFactory.eINSTANCE
		.createOrthogonalSampleData();
		sdOrthogonal2.setDataSetRepresentation("");//$NON-NLS-1$
		sdOrthogonal2.setSeriesDefinitionIndex(1);


		sd.getOrthogonalSampleData().add(sdOrthogonal2);


		cwaBar.setSampleData(sd);


		// X-Series


		Series seCategory = SeriesImpl.create();


		seCategory.setDataSet(categoryValues);


		SeriesDefinition sdX = SeriesDefinitionImpl.create();


		xAxisPrimary.getSeriesDefinitions().add(sdX);


		sdX.getSeries().add(seCategory);
		sdX.getSeriesPalette().shift(0);


		// Y-Series


		BarSeries bs1 = (BarSeries) BarSeriesImpl.create();


		bs1.setDataSet(orthoValues1);


		bs1.setStacked(true);


		bs1.getLabel().setVisible(true);


		bs1.setLabelPosition(Position.INSIDE_LITERAL);
		bs1.setRiserOutline(ColorDefinitionImpl.TRANSPARENT());


		BarSeries bs2 = (BarSeries) BarSeriesImpl.create();


		bs2.setDataSet(orthoValues2);


		bs2.setStacked(true);


		bs2.setRiserOutline(ColorDefinitionImpl.TRANSPARENT());
		bs2.getLabel().setVisible(true);


		bs2.setLabelPosition(Position.INSIDE_LITERAL);

		Trigger tr1 = TriggerImpl.create(TriggerCondition.ONMOUSEOVER_LITERAL,
				ActionImpl.create(ActionType.SHOW_TOOLTIP_LITERAL,
						TooltipValueImpl.create(200, "test + dph.getDisplayValue()")));
		Trigger tr2 = TriggerImpl.create(TriggerCondition.ONCLICK_LITERAL,
				ActionImpl.create(ActionType.URL_REDIRECT_LITERAL, URLValueImpl.create("https://www.google.com;", null, "component",
						"value", "")));
		//Trigger tr3 = TriggerImpl.create(TriggerCondition.ONMOUSEOVER_LITERAL, ActionImpl.create(ActionType.SHOW_TOOLTIP_LITERAL, TooltipValueImpl.create(200, null)));


		Trigger tr3 = (Trigger) EcoreUtil.copy(tr1);
		Trigger tr4 = (Trigger) EcoreUtil.copy(tr2);
		bs1.getTriggers().add(tr1);
		bs1.getTriggers().add(tr2);


		bs2.getTriggers().add(tr3);
		bs2.getTriggers().add(tr4);


		SeriesDefinition sdY = SeriesDefinitionImpl.create();


		sdY.getSeriesPalette().shift(0);


		yAxisPrimary.getSeriesDefinitions().add(sdY);


		sdY.getSeries().add(bs1);


		sdY.getSeries().add(bs2);


		return cwaBar;


	}



	public static final Chart createBarChart( )
	{
		ChartWithAxes cwaBar = ChartWithAxesImpl.create( );
		cwaBar.setDimension( ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL );
		// Plot
		cwaBar.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		cwaBar.getBlock( ).getOutline( ).setVisible( true );
		
		Plot p = cwaBar.getPlot( );
		p.getClientArea().setBackground( ColorDefinitionImpl.create(255,255,225));
		p.getOutline( ).setVisible(false);

		// Title
		cwaBar.getTitle( ).getLabel( ).getCaption( ).setValue( "Bar Chart" ); //$NON-NLS-1$

		// Legend
		Legend lg = cwaBar.getLegend( );
		lg.getText( ).getFont( ).setSize( 16 );
		lg.setItemType( LegendItemType.CATEGORIES_LITERAL );

		// X-Axis
		Axis xAxisPrimary = cwaBar.getPrimaryBaseAxes()[0];

		xAxisPrimary.setType( AxisType.TEXT_LITERAL );
		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
		xAxisPrimary.getTitle( ).setVisible( true );

		// Y-Axis
		Axis yAxisPrimary = cwaBar.getPrimaryOrthogonalAxis( xAxisPrimary );
		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
		yAxisPrimary.setType( AxisType.LINEAR_LITERAL );
		yAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 90 );

		// Data Set
		String[] iden = new String[]{"Item 1", "Item 2", "Item 3"};
		TextDataSet categoryValues = TextDataSetImpl.create(iden); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		NumberDataSet orthoValues1 = NumberDataSetImpl.create( new double[]{
				25, 35, 15
		} );
		NumberDataSet orthoValues2 = NumberDataSetImpl.create( new double[]{
				15, 45, 65
		} );

		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		sdX.getSeriesPalette( ).update( 0 );
//		sdX.getQuery( ).setDefinition( "" ); //$NON-NLS-1$
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Series;
		BarSeries bs1 = (BarSeries) BarSeriesImpl.create( );
		bs1.setDataSet( orthoValues1 );
		bs1.setRiserOutline( null );
		bs1.getLabel( ).setVisible( true );
		bs1.setLabelPosition( Position.INSIDE_LITERAL );
		bs1.setTranslucent(true);
		
		BarSeries bs2 = (BarSeries) BarSeriesImpl.create( );
		bs2.setDataSet( orthoValues2);
		bs2.setRiserOutline( null );
		bs2.getLabel().setVisible( true );
		bs2.setLabelPosition( Position.INSIDE_LITERAL );
		bs2.setTranslucent(true);
		
		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
		sdY.getSeriesPalette().update( ColorDefinitionImpl.GREEN());
		sdY.getSeries( ).add( bs1 );
		sdY.getSeries( ).add( bs2 );

		return cwaBar;
	}

	public static final Chart createAreaChart_1( )
	{
		ChartWithAxes cwaArea = ChartWithAxesImpl.create( );

		// Plot/Title
		cwaArea.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		Plot p = cwaArea.getPlot( );
		p.getClientArea( ).setBackground( ColorDefinitionImpl.create( 225,
				225,
				225 ) );
		cwaArea.getTitle( ).getLabel( ).getCaption( ).setValue( "Area Chart" );//$NON-NLS-1$
		cwaArea.getTitle( ).setVisible( true );

		// Legend
		Legend lg = cwaArea.getLegend( );
		LineAttributes lia = lg.getOutline( );
		lg.getText( ).getFont( ).setSize( 16 );
		lia.setStyle( LineStyle.SOLID_LITERAL );
		lg.getInsets( ).set( 10, 5, 0, 0 );
		lg.getOutline( ).setVisible( false );
		lg.setAnchor( Anchor.NORTH_LITERAL );

		// X-Axis
		Axis xAxisPrimary = cwaArea.getPrimaryBaseAxes( )[0];
		xAxisPrimary.setType( AxisType.TEXT_LITERAL );
		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getMajorGrid( )
				.setLineAttributes( LineAttributesImpl.create( ColorDefinitionImpl.BLUE( ),
						LineStyle.SOLID_LITERAL,
						1 ) );
		xAxisPrimary.getMinorGrid( ).getLineAttributes( ).setVisible( true );
		xAxisPrimary.getOrigin( ).setType( IntersectionType.MIN_LITERAL );
		xAxisPrimary.getTitle( ).getCaption( ).setValue( "Month" );//$NON-NLS-1$
		xAxisPrimary.getTitle( ).setVisible( true );
		xAxisPrimary.getTitle( ).getCaption( ).getFont( ).setRotation( 0 );
		xAxisPrimary.getLabel( ).setVisible( true );

		// Y-Axis
		Axis yAxisPrimary = cwaArea.getPrimaryOrthogonalAxis( xAxisPrimary );
		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
		yAxisPrimary.getMajorGrid( )
				.setLineAttributes( LineAttributesImpl.create( ColorDefinitionImpl.BLACK( ),
						LineStyle.SOLID_LITERAL,
						1 ) );
		yAxisPrimary.getMinorGrid( ).getLineAttributes( ).setVisible( true );
		yAxisPrimary.setPercent( false );
		yAxisPrimary.getTitle( ).getCaption( ).setValue( "Net Profit" );//$NON-NLS-1$
		yAxisPrimary.getTitle( ).setVisible( true );
		yAxisPrimary.getTitle( ).getCaption( ).getFont( ).setRotation( 90 );
		yAxisPrimary.getLabel( ).setVisible( true );

		MarkerLine ml = MarkerLineImpl.create( yAxisPrimary,
				NumberDataElementImpl.create( 2 ) );
		yAxisPrimary.getMarkerLines( ).add( ml );

		// Data Set
		TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
				"Jan.", "Feb.", "Mar.", "Apr", "May"} ); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$//$NON-NLS-5$
		NumberDataSet orthoValues1 = NumberDataSetImpl.create( new double[]{
				14.32, -19.5, 8.38, 0.34, 9.22
		} );
		NumberDataSet orthoValues2 = NumberDataSetImpl.create( new double[]{
				4.2, -19.5, 0.0, 9.2, 7.6
		} );

		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		sdX.getSeriesPalette( ).update( 0 );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Series
		AreaSeries as1 = (AreaSeries) AreaSeriesImpl.create( );
		as1.setSeriesIdentifier( "Series 1" );//$NON-NLS-1$
		as1.setDataSet( orthoValues1 );
		as1.setTranslucent( true );
		as1.getLineAttributes( ).setColor( ColorDefinitionImpl.BLUE( ) );
		as1.getLabel( ).setVisible( true );

		AreaSeries as2 = (AreaSeries) AreaSeriesImpl.create( );
		as2.setSeriesIdentifier( "Series 2" );//$NON-NLS-1$
		as2.setDataSet( orthoValues2 );
		as2.setTranslucent( true );
		as2.getLineAttributes( ).setColor( ColorDefinitionImpl.PINK( ) );
		as2.getLabel( ).setVisible( true );

		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
		sdY.getSeriesPalette( ).update( 1 );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
		sdY.getSeries( ).add( as1 );
		sdY.getSeries( ).add( as2 );

		return cwaArea;

	}


	public static final Chart create3DLineChart( )
	{
		ChartWithAxes cwa3DLine = ChartWithAxesImpl.create( );
		cwa3DLine.setDimension( ChartDimension.THREE_DIMENSIONAL_LITERAL );

		// Plot
		cwa3DLine.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		Plot p = cwa3DLine.getPlot( );
		p.getClientArea( ).setBackground( ColorDefinitionImpl.create( 255,
				255,
				225 ) );

		// Title
		cwa3DLine.getTitle( ).getLabel( ).getCaption( ).setValue( "Line Chart" );//$NON-NLS-1$

		// Legend
		cwa3DLine.getLegend( ).setVisible( false );

		// X-Axis
		Axis xAxisPrimary = cwa3DLine.getPrimaryBaseAxes( )[0];
		xAxisPrimary.setType( AxisType.TEXT_LITERAL );
		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
		xAxisPrimary.getTitle( ).setVisible( true );

		// Y-Axis
		Axis yAxisPrimary = cwa3DLine.getPrimaryOrthogonalAxis( xAxisPrimary );
		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );

		// Z-Axis
		Axis zAxis = AxisImpl.create( Axis.ANCILLARY_BASE );
		zAxis.setType( AxisType.TEXT_LITERAL );
		zAxis.setLabelPosition( Position.BELOW_LITERAL );
		zAxis.setTitlePosition( Position.BELOW_LITERAL );
		zAxis.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		zAxis.setOrientation( Orientation.HORIZONTAL_LITERAL );
		xAxisPrimary.getAncillaryAxes( ).add( zAxis );

		// Data Set
		TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
				"Item 1", "Item 2", "Item 3"} );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		NumberDataSet orthoValues = NumberDataSetImpl.create( new double[]{
				25, 35, 15
		} );

		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );
		SeriesDefinition sdX = SeriesDefinitionImpl.create( );

		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Sereis
		LineSeries ls = (LineSeries) LineSeriesImpl.create( );
		ls.setDataSet( orthoValues );
		ls.getLineAttributes( ).setColor( ColorDefinitionImpl.CREAM( ) );
		for ( int i = 0; i < ls.getMarkers( ).size( ); i++ )
		{
			( (Marker) ls.getMarkers( ).get( i ) ).setType( MarkerType.TRIANGLE_LITERAL);
		}
		ls.getLabel( ).setVisible( true );

		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
		sdY.getSeriesPalette( ).update( -2 );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
		sdY.getSeries( ).add( ls );

		// Z-Series
		SeriesDefinition sdZ = SeriesDefinitionImpl.create( );
		zAxis.getSeriesDefinitions( ).add( sdZ );

		// Rotate the chart
		cwa3DLine.setRotation( Rotation3DImpl.create( new Angle3D[]{
			Angle3DImpl.create( -20, 45, 0 )
		} ) );

		return cwa3DLine;
	}
	public static final Chart actioncreate2DBarChart( )
	{
		ChartWithAxes cwaBar = ChartWithAxesImpl.create( );
		cwaBar.setDimension( ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL );

		// Plot
		cwaBar.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		cwaBar.getBlock( ).getOutline( ).setVisible( true );
		Plot p = cwaBar.getPlot( );
		p.getClientArea( ).setBackground( ColorDefinitionImpl.create(
				255,255,225));
		p.getOutline( ).setVisible( false );

		// Title
		cwaBar.getTitle( ).getLabel( ).getCaption( ).setValue( "2D -- Bar Chart" );//$NON-NLS-1$

		// Legend
		Legend lg = cwaBar.getLegend( );
		lg.getText( ).getFont( ).setSize( 16 );
		lg.setItemType( LegendItemType.CATEGORIES_LITERAL );

		// X-Axis
		Axis xAxisPrimary = cwaBar.getPrimaryBaseAxes( )[0];

		xAxisPrimary.setType( AxisType.TEXT_LITERAL );
		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
		xAxisPrimary.getTitle( ).setVisible( true );

		// Y-Axis
		Axis yAxisPrimary = cwaBar.getPrimaryOrthogonalAxis( xAxisPrimary );
		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
		yAxisPrimary.setType( AxisType.LINEAR_LITERAL );
		yAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 90 );

		// Z-Axis
		Axis zAxis = AxisImpl.create( Axis.ANCILLARY_BASE );
		zAxis.setType( AxisType.TEXT_LITERAL );
		zAxis.setLabelPosition( Position.BELOW_LITERAL );
		zAxis.setTitlePosition( Position.BELOW_LITERAL );
		zAxis.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		zAxis.setOrientation( Orientation.HORIZONTAL_LITERAL );
		xAxisPrimary.getAncillaryAxes( ).add( zAxis );

		// Data Set
		TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
				"Item 1", "Item 2", "Item 3"} );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		NumberDataSet orthoValues = NumberDataSetImpl.create( new double[]{
				25, 35, 15
		} );

		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		sdX.getSeriesPalette( ).update( 0 );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Series
		BarSeries bs = (BarSeries) BarSeriesImpl.create( );
		bs.setSeriesIdentifier("identifi");
		bs.setDataSet( orthoValues );
		bs.setRiserOutline( null );
		bs.getLabel( ).setVisible( true );
		bs.setLabelPosition( Position.OUTSIDE_LITERAL );

		ActionValue actionValue = CallBackValueImpl.create(String.valueOf(bs.getSeriesIdentifier()));
		Action action = ActionImpl.create(ActionType.HIGHLIGHT_LITERAL,actionValue);
		Trigger mouseClick = TriggerImpl.create(TriggerCondition.ONCLICK_LITERAL,action);
		bs.getTriggers().add(mouseClick);
			
		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
		sdY.getSeries( ).add( bs );

		return cwaBar;
	}
	public static final Chart create3DBarChart( )
	{
		ChartWithAxes cwaBar = ChartWithAxesImpl.create( );
		cwaBar.setDimension( ChartDimension.THREE_DIMENSIONAL_LITERAL );

		// Plot
		cwaBar.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		cwaBar.getBlock( ).getOutline( ).setVisible( true );
		Plot p = cwaBar.getPlot( );
		p.getClientArea( ).setBackground( ColorDefinitionImpl.create( 255,
				255,
				225 ) );
		p.getOutline( ).setVisible( false );

		// Title
		cwaBar.getTitle( ).getLabel( ).getCaption( ).setValue( "3D-- Bar Chart" );//$NON-NLS-1$

		// Legend
		Legend lg = cwaBar.getLegend( );
		lg.getText( ).getFont( ).setSize( 16 );
		lg.setItemType( LegendItemType.CATEGORIES_LITERAL );

		// X-Axis
		Axis xAxisPrimary = cwaBar.getPrimaryBaseAxes( )[0];

		xAxisPrimary.setType( AxisType.TEXT_LITERAL );
		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
		xAxisPrimary.getTitle( ).setVisible( true );

		// Y-Axis
		Axis yAxisPrimary = cwaBar.getPrimaryOrthogonalAxis( xAxisPrimary );
		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
		yAxisPrimary.setType( AxisType.LINEAR_LITERAL );
		yAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 90 );

		// Z-Axis
		Axis zAxis = AxisImpl.create( Axis.ANCILLARY_BASE );
		zAxis.setType( AxisType.TEXT_LITERAL );
		zAxis.setLabelPosition( Position.BELOW_LITERAL );
		zAxis.setTitlePosition( Position.BELOW_LITERAL );
		zAxis.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		zAxis.setOrientation( Orientation.HORIZONTAL_LITERAL );
		xAxisPrimary.getAncillaryAxes( ).add( zAxis );

		// Data Set
		TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
				"Item 1", "Item 2", "Item 3"} );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		NumberDataSet orthoValues = NumberDataSetImpl.create( new double[]{
				25, 35, 15
		} );

		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		sdX.getSeriesPalette( ).update( 0 );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Series
		BarSeries bs = (BarSeries) BarSeriesImpl.create( );
		bs.setDataSet( orthoValues );
		bs.setRiserOutline( null );
		bs.getLabel( ).setVisible( true );
		bs.setLabelPosition( Position.OUTSIDE_LITERAL );

		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
		sdY.getSeries( ).add( bs );

		// Z-Series
		SeriesDefinition sdZ = SeriesDefinitionImpl.create( );
		zAxis.getSeriesDefinitions( ).add( sdZ );

		// Rotate the chart
		cwaBar.setRotation( Rotation3DImpl.create( new Angle3D[]{
			Angle3DImpl.create( -20, 45, 0 )
		} ) );

		return cwaBar;
	}

	public static final Chart create3DAreaChart( )
	{
		ChartWithAxes cwa3DArea = ChartWithAxesImpl.create( );
		cwa3DArea.setDimension( ChartDimension.THREE_DIMENSIONAL_LITERAL );

		// Plot/Title
		cwa3DArea.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		Plot p = cwa3DArea.getPlot( );
		p.getClientArea( ).setBackground( ColorDefinitionImpl.create( 225,
				225,
				225 ) );
		cwa3DArea.getTitle( ).getLabel( ).getCaption( ).setValue( "Area Chart" );//$NON-NLS-1$
		cwa3DArea.getTitle( ).setVisible( true );

		// Legend
		Legend lg = cwa3DArea.getLegend( );
		LineAttributes lia = lg.getOutline( );
		lg.getText( ).getFont( ).setSize( 16 );
		lia.setStyle( LineStyle.SOLID_LITERAL );
		lg.getInsets( ).set( 10, 5, 0, 0 );
		lg.getOutline( ).setVisible( false );
		lg.setAnchor( Anchor.NORTH_LITERAL );

		// X-Axis
		Axis xAxisPrimary = cwa3DArea.getPrimaryBaseAxes( )[0];
		xAxisPrimary.setType( AxisType.TEXT_LITERAL );
		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getMajorGrid( )
				.setLineAttributes( LineAttributesImpl.create( ColorDefinitionImpl.BLUE( ),
						LineStyle.SOLID_LITERAL,
						1 ) );
		xAxisPrimary.getMinorGrid( ).getLineAttributes( ).setVisible( true );
		xAxisPrimary.getOrigin( ).setType( IntersectionType.MIN_LITERAL );
		xAxisPrimary.getTitle( ).getCaption( ).setValue( "Month" );//$NON-NLS-1$
		xAxisPrimary.getTitle( ).setVisible( true );
		xAxisPrimary.getTitle( ).getCaption( ).getFont( ).setRotation( 0 );
		xAxisPrimary.getLabel( ).setVisible( true );

		// Y-Axis
		Axis yAxisPrimary = cwa3DArea.getPrimaryOrthogonalAxis( xAxisPrimary );
		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
		yAxisPrimary.getMajorGrid( )
				.setLineAttributes( LineAttributesImpl.create( ColorDefinitionImpl.BLACK( ),
						LineStyle.SOLID_LITERAL,
						1 ) );
		yAxisPrimary.getMinorGrid( ).getLineAttributes( ).setVisible( true );
		yAxisPrimary.setPercent( false );
		yAxisPrimary.getTitle( ).getCaption( ).setValue( "Net Profit" );//$NON-NLS-1$
		yAxisPrimary.getTitle( ).setVisible( true );
		yAxisPrimary.getTitle( ).getCaption( ).getFont( ).setRotation( 90 );
		yAxisPrimary.getLabel( ).setVisible( true );

		// Z-Axis
		Axis zAxis = AxisImpl.create( Axis.ANCILLARY_BASE );
		zAxis.setType( AxisType.TEXT_LITERAL );
		zAxis.setLabelPosition( Position.BELOW_LITERAL );
		zAxis.setTitlePosition( Position.BELOW_LITERAL );
		zAxis.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		zAxis.setOrientation( Orientation.HORIZONTAL_LITERAL );
		xAxisPrimary.getAncillaryAxes( ).add( zAxis );

		// Data Set
		TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
				"Jan.", "Feb.", "Mar.", "Apr", "May"} ); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$//$NON-NLS-5$
		NumberDataSet orthoValues1 = NumberDataSetImpl.create( new double[]{
				14.32, -19.5, 8.38, 0.34, 9.22
		} );

		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		sdX.getSeriesPalette( ).update( 0 );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Series
		AreaSeries as1 = (AreaSeries) AreaSeriesImpl.create( );
		as1.setSeriesIdentifier( "Series 1" );//$NON-NLS-1$
		as1.setDataSet( orthoValues1 );
		as1.getLineAttributes( ).setColor( ColorDefinitionImpl.BLUE( ) );
		as1.getLabel( ).setVisible( true );

		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
		sdY.getSeriesPalette( ).update( 1 );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
		sdY.getSeries( ).add( as1 );

		// Z-Series
		SeriesDefinition sdZ = SeriesDefinitionImpl.create( );
		zAxis.getSeriesDefinitions( ).add( sdZ );

		// Rotate the chart
		cwa3DArea.setRotation( Rotation3DImpl.create( new Angle3D[]{
			Angle3DImpl.create( -20, 45, 0 )
		} ) );

		return cwa3DArea;
	}

	public static final Chart createPieChart( )
	{
		ChartWithoutAxes cwoaPie = ChartWithoutAxesImpl.create( );

		// Plot
		cwoaPie.setSeriesThickness( 25 );
		cwoaPie.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		Plot p = cwoaPie.getPlot( );
		p.getClientArea( ).setBackground( null );
		p.getClientArea( ).getOutline( ).setVisible( true );
		p.getOutline( ).setVisible( true );

		// Legend
		Legend lg = cwoaPie.getLegend( );
		lg.getText( ).getFont( ).setSize( 16 );
		lg.setBackground( null );
		lg.getOutline( ).setVisible( true );

		// Title
		cwoaPie.getTitle( ).getLabel( ).getCaption( ).setValue( "Pie Chart" );//$NON-NLS-1$
		cwoaPie.getTitle( ).getOutline( ).setVisible( true );

		// Data Set
		TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
				"New York", "Boston", "Chicago", "San Francisco", "Dallas"} );//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		NumberDataSet seriesOneValues = NumberDataSetImpl.create( new double[]{
				54.65, 21, 75.95, 91.28, 37.43
		} );

		// Base Series
		Series seCategory = (Series) SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sd = SeriesDefinitionImpl.create( );
		cwoaPie.getSeriesDefinitions( ).add( sd );
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

		return cwoaPie;
	}

	public static final Chart createMultiPieChart( )
	{
		ChartWithoutAxes cwoaPie = ChartWithoutAxesImpl.create( );

		// Plot
		cwoaPie.setSeriesThickness( 25 );
		cwoaPie.setGridColumnCount( 2 );
		cwoaPie.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		Plot p = cwoaPie.getPlot( );
		p.getClientArea( ).setBackground( null );
		p.getClientArea( ).getOutline( ).setVisible( true );
		p.getOutline( ).setVisible( true );

		// Legend
		Legend lg = cwoaPie.getLegend( );
		lg.getText( ).getFont( ).setSize( 16 );
		lg.setBackground( null );
		lg.getOutline( ).setVisible( true );

		// Title
		cwoaPie.getTitle( )
				.getLabel( )
				.getCaption( )
				.setValue( "Multiple Series Pie Chart" );//$NON-NLS-1$
		cwoaPie.getTitle( ).getOutline( ).setVisible( true );

		// Data Set
		TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
				"Boston", "New York", "Chicago", "San Francisco", "Seattle"} );//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		NumberDataSet seriesOneValues = NumberDataSetImpl.create( new double[]{
				54.65, 21, 75.95, 91.28, 37.43
		} );
		NumberDataSet seriesTwoValues = NumberDataSetImpl.create( new double[]{
				15.65, 65, 25.95, 14.28, 37.43
		} );
		NumberDataSet seriesThreeValues = NumberDataSetImpl.create( new double[]{
				25.65, 85, 45.95, 64.28, 6.43
		} );
		NumberDataSet seriesFourValues = NumberDataSetImpl.create( new double[]{
				25.65, 55, 5.95, 14.28, 86.43
		} );

		// Base Sereis
		Series seCategory = (Series) SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sd = SeriesDefinitionImpl.create( );
		cwoaPie.getSeriesDefinitions( ).add( sd );
		sd.getSeriesPalette( ).update( 1 );
		sd.getSeries( ).add( seCategory );

		// Orthogonal Series
		PieSeries sePie1 = (PieSeries) PieSeriesImpl.create( );
		sePie1.setDataSet( seriesOneValues );
		sePie1.setSeriesIdentifier( "2000" );//$NON-NLS-1$ 
		sePie1.getLabel( ).getCaption( ).getFont( ).setRotation( 25 );
		sePie1.getTitle( ).getCaption( ).getFont( ).setRotation( 8 );
		sePie1.setTitlePosition( Position.ABOVE_LITERAL );
		sePie1.getTitle( ).getInsets( ).set( 8, 10, 0, 5 );

		PieSeries sePie2 = (PieSeries) PieSeriesImpl.create( );
		sePie2.setDataSet( seriesTwoValues );
		sePie2.setSeriesIdentifier( "2001" );//$NON-NLS-1$ 
		sePie2.getLabel( ).getCaption( ).getFont( ).setRotation( -65 );
		sePie2.getTitle( ).getCaption( ).getFont( ).setRotation( 28 );
		sePie2.getLabel( ).setBackground( ColorDefinitionImpl.YELLOW( ) );
		sePie2.getLabel( ).setShadowColor( ColorDefinitionImpl.GREY( ) );
		sePie2.setTitlePosition( Position.RIGHT_LITERAL );

		PieSeries sePie3 = (PieSeries) PieSeriesImpl.create( );
		sePie3.setDataSet( seriesThreeValues );
		sePie3.setSeriesIdentifier( "2002" );//$NON-NLS-1$ 
		sePie3.getTitle( ).getCaption( ).getFont( ).setRotation( 75 );
		sePie3.setTitlePosition( Position.LEFT_LITERAL );

		PieSeries sePie4 = (PieSeries) PieSeriesImpl.create( );
		sePie4.setDataSet( seriesFourValues );
		sePie4.setSeriesIdentifier( "2003" );//$NON-NLS-1$ 
		sePie4.setLabelPosition( Position.INSIDE_LITERAL );

		SeriesDefinition sdCity = SeriesDefinitionImpl.create( );
		sd.getSeriesDefinitions( ).add( sdCity );
		sdCity.getSeries( ).add( sePie1 );
		sdCity.getSeries( ).add( sePie2 );
		sdCity.getSeries( ).add( sePie3 );
		sdCity.getSeries( ).add( sePie4 );

		return cwoaPie;
	}

	public static final Chart createScatterChart_2( )
	{
		ChartWithAxes cwaCombination = ChartWithAxesImpl.create( );

		// Plot
		cwaCombination.setUnitSpacing( 25 );
		cwaCombination.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		Plot p = cwaCombination.getPlot( );
		p.getClientArea().setBackground( GradientImpl.create( 
						ColorDefinitionImpl.create( 255,235,255 ),
						ColorDefinitionImpl.create( 255, 255, 225 ),
						-35,
						false ) );

		p.getClientArea( ).getInsets( ).set( 8, 8, 8, 8 );
		p.getOutline( ).setVisible( true );

		// Legend
		Legend lg = cwaCombination.getLegend( );
		lg.setBackground( ColorDefinitionImpl.YELLOW( ) );
		lg.getOutline( ).setVisible( true );

		// Title
		cwaCombination.getTitle( )
				.getLabel( )
				.getCaption( )
				.setValue( "Project Sales" );//$NON-NLS-1$ 

		// X-Axis
		Axis xAxisPrimary = cwaCombination.getPrimaryBaseAxes( )[0];
		xAxisPrimary.setType( AxisType.TEXT_LITERAL );

		xAxisPrimary.getLabel( )
				.setBackground( ColorDefinitionImpl.create( 255, 255, 235 ) );
		xAxisPrimary.getLabel( )
				.setShadowColor( ColorDefinitionImpl.create( 225, 225, 225 ) );
		xAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 25 );

		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
		xAxisPrimary.setTitlePosition( Position.BELOW_LITERAL );
		xAxisPrimary.setLabelPosition( Position.BELOW_LITERAL );

		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		xAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.create( 64, 64, 64 ) );
		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );

		xAxisPrimary.getTitle( ).getCaption( ).setValue( "Computer Components" );//$NON-NLS-1$ 
		xAxisPrimary.getOrigin( ).setType( IntersectionType.MIN_LITERAL );

		xAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.CYAN( ) );
		xAxisPrimary.getMinorGrid( ).getLineAttributes( ).setVisible( true );

		// Y-Series
		Axis yAxisPrimary = cwaCombination.getPrimaryOrthogonalAxis( xAxisPrimary );

		yAxisPrimary.setLabelPosition( Position.LEFT_LITERAL );
		yAxisPrimary.setTitlePosition( Position.LEFT_LITERAL );
		yAxisPrimary.getTitle( )
				.getCaption( )
				.setValue( "Actual Sales ($Millions)" );//$NON-NLS-1$ 

		yAxisPrimary.setType( AxisType.LINEAR_LITERAL );
		yAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 37 );

		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
		yAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		yAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.RED( ) );
		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );
		yAxisPrimary.getMinorGrid( ).setTickStyle( TickStyle.ACROSS_LITERAL );
		yAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		yAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.GREEN( ) );

		// Data Set
		String[] saTextValues = {
				"CPUs", "Keyboards", "Video Cards",//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 
				"Monitors", "Motherboards", "Memory", "Storage Devices",//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				"Media", "Printers", "Scanners"};//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 

		TextDataSet categoryValues = TextDataSetImpl.create( saTextValues );
		NumberDataSet seriesOneValues = NumberDataSetImpl.create( new double[]{
				56.99,
				352.95,
				201.95,
				299.95,
				95.95,
				25.45,
				129.33,
				26.5,
				43.5,
				122
		} );
		NumberDataSet seriesTwoValues = NumberDataSetImpl.create( new double[]{
				20, 35, 59, 105, 150, 37, 65, 99, 145, 185
		} );
		NumberDataSet seriesThreeValues = NumberDataSetImpl.create( new double[]{
				54.99, 21, 75.95, 39.95, 7.95, 91.22, 33.45, 25.63, 40, 13
		} );
		NumberDataSet seriesFourValues = NumberDataSetImpl.create( new double[]{
				15, 45, 43, 5, 19, 25, 35, 94, 15, 55
		} );
		NumberDataSet seriesFiveValues = NumberDataSetImpl.create( new double[]{
				43, 65, 35, 41, 45, 55, 29, 15, 85, 65
		} );
		NumberDataSet seriesSixValues = NumberDataSetImpl.create( new double[]{
				15, 45, 43, 5, 19, 25, 35, 94, 15, 55
		} );
		NumberDataSet seriesSevenValues = NumberDataSetImpl.create( new double[]{
				43, 65, 35, 41, 45, 55, 29, 15, 85, 65
		} );

		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Series
		BarSeries bs1 = (BarSeries) BarSeriesImpl.create( );
		bs1.setSeriesIdentifier( "North America" );//$NON-NLS-1$
		bs1.setDataSet( seriesOneValues );
		bs1.setRiserOutline( null );
		bs1.setRiser( RiserType.RECTANGLE_LITERAL );
		bs1.setStacked( true );
		DataPoint dp = DataPointImpl.create( "(", ")", ", " );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		dp.getComponents( ).clear( );
		dp.getComponents( )
				.add( DataPointComponentImpl.create( DataPointComponentType.BASE_VALUE_LITERAL,
						null ) );
		dp.getComponents( )
				.add( DataPointComponentImpl.create( DataPointComponentType.ORTHOGONAL_VALUE_LITERAL,
						JavaNumberFormatSpecifierImpl.create( "0.00" ) ) );//$NON-NLS-1$
		bs1.setDataPoint( dp );

		BarSeries bs2 = (BarSeries) BarSeriesImpl.create( );
		bs2.setSeriesIdentifier( "South America" );//$NON-NLS-1$
		bs2.setDataSet( seriesThreeValues );
		bs2.setRiserOutline( null );
		bs2.setRiser( RiserType.RECTANGLE_LITERAL );
		bs2.setStacked( true );
		dp = DataPointImpl.create( "[", "]", ", " );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		bs2.setDataPoint( dp );

		BarSeries bs3 = (BarSeries) BarSeriesImpl.create( );
		bs3.setSeriesIdentifier( "Eastern Europe" );//$NON-NLS-1$
		bs3.setDataSet( seriesFourValues );
		bs3.setRiserOutline( null );
		bs3.setRiser( RiserType.RECTANGLE_LITERAL );
		bs3.setStacked( true );

		BarSeries bs4 = (BarSeries) BarSeriesImpl.create( );
		bs4.setSeriesIdentifier( "Western Europe" );//$NON-NLS-1$
		bs4.setDataSet( seriesFiveValues );
		bs4.setRiserOutline( null );
		bs4.setRiser( RiserType.RECTANGLE_LITERAL );
		bs4.setStacked( true );

		BarSeries bs5 = (BarSeries) BarSeriesImpl.create( );
		bs5.setSeriesIdentifier( "Asia" );//$NON-NLS-1$
		bs5.setDataSet( seriesSixValues );
		bs5.setRiserOutline( null );
		bs5.setRiser( RiserType.RECTANGLE_LITERAL );

		BarSeries bs6 = (BarSeries) BarSeriesImpl.create( );
		bs6.setSeriesIdentifier( "Australia" );//$NON-NLS-1$
		bs6.setDataSet( seriesSevenValues );
		bs6.setRiserOutline( null );
		bs6.setRiser( RiserType.RECTANGLE_LITERAL );

		LineSeries ls1 = (LineSeries) LineSeriesImpl.create( );
		ls1.setSeriesIdentifier( "Expected Growth" );//$NON-NLS-1$
		ls1.setDataSet( seriesTwoValues );
		for ( int i = 0; i < ls1.getMarkers( ).size( ); i++ )
		{
			( (Marker) ls1.getMarkers( ).get( i ) ).setType( MarkerType.BOX_LITERAL);
		}
		ls1.getLabel( ).setVisible( true );

		SeriesDefinition sdY1 = SeriesDefinitionImpl.create( );
		sdY1.getSeriesPalette( ).update( 0 );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY1 );

		SeriesDefinition sdY2 = SeriesDefinitionImpl.create( );
		sdY2.getSeriesPalette( ).update( 1 );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY2 );

		SeriesDefinition sdY3 = SeriesDefinitionImpl.create( );
		sdY3.getSeriesPalette( ).update( ColorDefinitionImpl.RED( ) );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY3 );

		SeriesDefinition sdY4 = SeriesDefinitionImpl.create( );
		sdY4.getSeriesPalette( ).update( ColorDefinitionImpl.GREEN( ) );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY4 );

		SeriesDefinition sdY5 = SeriesDefinitionImpl.create( );
		sdY5.getSeriesPalette( ).update( ColorDefinitionImpl.YELLOW( ) );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY5 );

		sdY1.getSeries( ).add( bs1 );
		sdY1.getSeries( ).add( bs2 );
		sdY2.getSeries( ).add( bs3 );
		sdY2.getSeries( ).add( bs4 );
		sdY3.getSeries( ).add( bs5 );
		sdY4.getSeries( ).add( bs6 );
		sdY5.getSeries( ).add( ls1 );

		return cwaCombination;



	}

	public static final Chart createScatterChart_1(){
		ChartWithAxes cwoaPie = ChartWithAxesImpl.create();


		
		cwoaPie.getTitle().getLabel().getCaption().setValue("Scatter chart");
		cwoaPie.getTitle().getLabel().getCaption().getFont().setSize(14);
		cwoaPie.getTitle().getLabel().getCaption().getFont().setName("MS Sans Serif");
		cwoaPie.getBlock().setBackground( ColorDefinitionImpl.WHITE( ) );
		cwoaPie.getBlock().getOutline( ).setVisible( true );
		
		Plot p = cwoaPie.getPlot( );
		p.getClientArea( ).setBackground( ColorDefinitionImpl.WHITE());
		
		Legend lg = cwoaPie.getLegend( );
		LineAttributes lia = lg.getOutline( );
		lg.getText( ).getFont( ).setSize( 16 );
		lia.setStyle( LineStyle.SOLID_LITERAL );
		lg.getInsets( ).set( 10, 5, 0, 0 );
		lg.getOutline( ).setVisible( false );
		lg.setAnchor( Anchor.NORTH_LITERAL );
		
		Axis xAxis = ((ChartWithAxesImpl) cwoaPie).getPrimaryBaseAxes()[0];


		
        xAxis.getTitle().getCaption().setValue("Axis X--Title ");
        xAxis.getLabel().getCaption().setColor(ColorDefinitionImpl.GREEN()
                .darker());
        xAxis.getTitle().setVisible(true);

        xAxis.setType(AxisType.TEXT_LITERAL);

        xAxis.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);
        xAxis.getMajorGrid().getLineAttributes()
                .setStyle(LineStyle.DOTTED_LITERAL);
        xAxis.getMajorGrid().getLineAttributes().setColor(ColorDefinitionImpl
                .GREY());
        xAxis.getMajorGrid().getLineAttributes().setVisible(true);

        xAxis.getOrigin().setType(IntersectionType.VALUE_LITERAL);
        
        
        
        Axis yAxis = ((ChartWithAxesImpl) cwoaPie).getPrimaryOrthogonalAxis(xAxis);

        yAxis.getTitle().setVisible(true);
        yAxis.getTitle().getCaption().setValue("Axis Y--Title ");

        yAxis.setType(AxisType.LINEAR_LITERAL);

        yAxis.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);
        yAxis.getMajorGrid().getLineAttributes()
                .setStyle(LineStyle.DOTTED_LITERAL);
        yAxis.getMajorGrid().getLineAttributes().setColor(ColorDefinitionImpl
                .GREY());
        yAxis.getMajorGrid().getLineAttributes().setVisible(true);

        yAxis.getOrigin().setType(IntersectionType.VALUE_LITERAL);
        
        yAxis.getScale().setStep(1.0);
        
        TextDataSet categoryValues = TextDataSetImpl
        .create(new String[]{
				"March", "April", "May", "June", "July"});

        Series seBase = SeriesImpl.create();
        seBase.setDataSet(categoryValues);

        SeriesDefinition sdX = SeriesDefinitionImpl.create();
        xAxis.getSeriesDefinitions().add(sdX);
        sdX.getSeries().add(seBase);
        
        NumberDataSet orthoValuesDataSet1 = NumberDataSetImpl.create(new double[]{
				12.5, 19.6, 18.3, 13.2, 26.5
		} );

        ScatterSeries ss = (ScatterSeries) ScatterSeriesImpl.create();
        ss.setSeriesIdentifier("chiffre 1");
        for ( int i = 0; i < ss.getMarkers( ).size( ); i++ )
        {
        	( (Marker) ss.getMarkers( ).get( i ) ).setType( MarkerType.DIAMOND_LITERAL );
        }

        DataPoint dp = ss.getDataPoint();
        dp.getComponents().clear();
        dp.setPrefix("(");
        dp.setSuffix(")");
        dp.getComponents().add(DataPointComponentImpl
                .create(DataPointComponentType.BASE_VALUE_LITERAL,
                        JavaNumberFormatSpecifierImpl.create("0")));
        dp.getComponents().add(DataPointComponentImpl
                .create(DataPointComponentType.ORTHOGONAL_VALUE_LITERAL,
                        JavaNumberFormatSpecifierImpl.create("0")));
        ss.getLabel().getCaption().setColor(ColorDefinitionImpl.RED());
        ss.getLabel().setBackground(ColorDefinitionImpl.CYAN());
        ss.getLabel().setVisible(true);
        ss.setDataSet(orthoValuesDataSet1);

/*************************************************/
        NumberDataSet orthoValuesDataSet2 = NumberDataSetImpl.create(new double[]{
        		22.7, 23.6, 38.3, 43.2, 40.5
		} );
        SeriesDefinition sdY = SeriesDefinitionImpl.create();
        yAxis.getSeriesDefinitions().add(sdY);
        sdY.getSeriesPalette().update(ColorDefinitionImpl.BLACK());
        sdY.getSeries().add(ss);
        
        ScatterSeries ss2 = (ScatterSeries) ScatterSeriesImpl.create();
        ss2.setSeriesIdentifier("chiffre 2");
        for ( int i = 0; i < ss2.getMarkers( ).size( ); i++ )
        {
        	( (Marker) ss2.getMarkers( ).get( i ) ).setType( MarkerType.CIRCLE_LITERAL );
        	
        }
        DataPoint dp2 = ss.getDataPoint();
        dp2.getComponents().clear();
        dp2.setPrefix("(");
        dp2.setSuffix(")");
        dp2.getComponents().add(DataPointComponentImpl
                .create(DataPointComponentType.BASE_VALUE_LITERAL,
                        JavaNumberFormatSpecifierImpl.create("0")));
        dp2.getComponents().add(DataPointComponentImpl
                .create(DataPointComponentType.ORTHOGONAL_VALUE_LITERAL,
                        JavaNumberFormatSpecifierImpl.create("0")));
        
     
        ss2.getLabel().getCaption().setColor(ColorDefinitionImpl.RED());
        ss2.getLabel().setBackground(ColorDefinitionImpl.CYAN());
        ss2.getLabel().setVisible(true);
        ss2.setDataSet(orthoValuesDataSet2);
        
        SeriesDefinition sdY2 = SeriesDefinitionImpl.create();
        yAxis.getSeriesDefinitions().add(sdY2);
        sdY2.getSeriesPalette().update(ColorDefinitionImpl.BLACK());
        sdY2.getSeries().add(ss2);
		
		return cwoaPie;
	}


	public static final Chart createSimplelineChart(){
		ChartWithAxes cwoaPie = ChartWithAxesImpl.create();
		
		cwoaPie.getTitle().getLabel().getCaption().setValue("line chart");
		cwoaPie.getTitle().getLabel().getCaption().getFont().setSize(14);
		cwoaPie.getTitle().getLabel().getCaption().getFont().setName("MS Sans Serif");
		
		Axis xAxis = cwoaPie.getPrimaryBaseAxes()[0];

        xAxis.getTitle().setVisible(true);
        xAxis.getTitle().getCaption().setValue("Axis--X Title");
        xAxis.getTitle().getCaption().getFont().setBold(false);
        xAxis.getTitle().getCaption().getFont().setSize(11);
        xAxis.getTitle().getCaption().getFont().setName("MS Sans Serif");

        xAxis.getLabel().setVisible(true);
        xAxis.getLabel().getCaption().getFont().setSize(8);
        xAxis.getLabel().getCaption().getFont().setName("MS Sans Serif");

        xAxis.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);

        xAxis.setType(AxisType.TEXT_LITERAL);
        xAxis.getOrigin().setType(IntersectionType.VALUE_LITERAL);
        
        Axis yAxis = cwoaPie.getPrimaryOrthogonalAxis(xAxis);

        yAxis.getTitle().setVisible(true);
        yAxis.getTitle().getCaption().setValue("Axis--Y Title");
        yAxis.getTitle().getCaption().getFont().setBold(false);
        yAxis.getTitle().getCaption().getFont().setRotation(90);
        yAxis.getTitle().getCaption().getFont().setSize(11);
        yAxis.getTitle().getCaption().getFont().setName("MS Sans Serif");

        yAxis.getLabel().setVisible(true);
        yAxis.getLabel().getCaption().getFont().setSize(8);
        yAxis.getLabel().getCaption().getFont().setName("MS Sans Serif");

        yAxis.getMajorGrid().getLineAttributes().setVisible(true);
        yAxis.getMajorGrid().getLineAttributes().setColor(ColorDefinitionImpl
                .GREY());
        yAxis.getMajorGrid().getLineAttributes()
                .setStyle(LineStyle.DASHED_LITERAL);
        yAxis.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);

        yAxis.setType(AxisType.LINEAR_LITERAL);
        yAxis.getOrigin().setType(IntersectionType.VALUE_LITERAL);
        
        yAxis.getScale().setStep(1.0);
        
        TextDataSet categoryValues = TextDataSetImpl
        .create(new String[]{
				"March", "April", "May", "June", "July"});

        Series seCategory = SeriesImpl.create();
        seCategory.setDataSet(categoryValues);

        // Apply the color palette
        SeriesDefinition sdX = SeriesDefinitionImpl.create();
        sdX.getSeriesPalette().update(1);

        xAxis.getSeriesDefinitions().add(sdX);
        sdX.getSeries().add(seCategory);
        
        NumberDataSet orthoValuesDataSet1 = NumberDataSetImpl.create(new double[]{
				12.5, 19.6, 18.3, 13.2, 26.5
		});

        LineSeries ls = (LineSeries) LineSeriesImpl.create();
        ls.setDataSet(orthoValuesDataSet1);
        ls.getLineAttributes().setColor(ColorDefinitionImpl.BLUE());
//        ls.getMarker().setType(MarkerType.CIRCLE_LITERAL);// this is old 
        ls.getLabel().setVisible(true);

        SeriesDefinition sdY = SeriesDefinitionImpl.create();
        yAxis.getSeriesDefinitions().add(sdY);
        sdY.getSeries().add(ls);
        
		return cwoaPie;
	}
	public static final Chart createSimplePieChart()
	{
		ChartWithoutAxes cwoaPie = ChartWithoutAxesImpl.create();

		cwoaPie.setSeriesThickness(25);
		cwoaPie.getBlock().setBackground(ColorDefinitionImpl.WHITE());
		Plot p = cwoaPie.getPlot();
		p.getClientArea().setBackground(null);
		p.getClientArea().getOutline().setVisible(true);
		p.getOutline().setVisible(true);

		Legend lg = cwoaPie.getLegend();
		lg.getText().getFont().setSize(16);
		lg.getOutline().setVisible(true);

		cwoaPie.getTitle().getLabel().getCaption().setValue("pie chart");
		cwoaPie.getTitle().getLabel().getCaption().getFont().setSize(14);
		cwoaPie.getTitle().getLabel().getCaption().getFont().setName("MS Sans Serif");

		TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
				"March", "April", "May", "June", "July"} );

		Series seCategory = SeriesImpl.create();
		seCategory.setDataSet(categoryValues);

		// Apply the color palette
		SeriesDefinition sdX = SeriesDefinitionImpl.create();
		sdX.getSeriesPalette().update(1);

		((ChartWithoutAxes) cwoaPie).getSeriesDefinitions().add(sdX);
		sdX.getSeries().add(seCategory);
		
		NumberDataSet orthoValuesDataSet = NumberDataSetImpl.create(new double[]{
				22.7, 23.6, 38.3, 43.2, 40.5
		} );

        PieSeries sePie = (PieSeries) PieSeriesImpl.create();
        sePie.setDataSet(orthoValuesDataSet);
        sePie.setSeriesIdentifier("Cities");

        SeriesDefinition sdCity = SeriesDefinitionImpl.create();
        sdX.getSeriesDefinitions().add(sdCity);
        sdCity.getSeries().add(sePie);
		
		return cwoaPie;
	}



	protected  static Chart createMultiYAxisChart( )
	{
		ChartWithAxes cwaBar = ChartWithAxesImpl.create( );
		cwaBar.setDimension( ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL );
		cwaBar.setType("Bar Chart" ); //$NON-NLS-1$
		cwaBar.setSubType("Side-by-side"); //$NON-NLS-1$
		// Plot
		cwaBar.getBlock().setBackground( ColorDefinitionImpl.WHITE( ) );
		cwaBar.getBlock().getOutline( ).setVisible( true );

		// Plot
		Plot p = cwaBar.getPlot( );
		p.getClientArea( ).setBackground( ColorDefinitionImpl.create( 255,
				245,
				255 ) );

		// Title
		cwaBar.getTitle().getLabel().getCaption().setValue( "Bar Chart with Multiple Y Axis" );//$NON-NLS-1$

		// Legend
		Legend lg = cwaBar.getLegend( );
		LineAttributes lia = lg.getOutline( );
		lg.getText( ).getFont( ).setSize( 16 );
		lia.setStyle( LineStyle.SOLID_LITERAL );
		lg.getInsets( ).set( 10, 5, 0, 0 );
		lg.getOutline( ).setVisible( false );
		lg.setAnchor( Anchor.NORTH_LITERAL );

		// X-Axis
		Axis xAxisPrimary = cwaBar.getPrimaryBaseAxes( )[0];
		xAxisPrimary.setType( AxisType.TEXT_LITERAL );
		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
		xAxisPrimary.getTitle( ).setVisible( false );

		// Y-Axis
		Axis yAxisPrimary = cwaBar.getPrimaryOrthogonalAxis( xAxisPrimary );
		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
		yAxisPrimary.getTitle( )
		.getCaption( )
		.setValue( "Sales Growth ($Million)" );//$NON-NLS-1$

		// Y-Axis (2)
		Axis yAxis = AxisImpl.create( Axis.ORTHOGONAL );
		yAxis.setType( AxisType.LINEAR_LITERAL );
		yAxis.getMajorGrid( ).setTickStyle( TickStyle.RIGHT_LITERAL );
		yAxis.setLabelPosition( Position.RIGHT_LITERAL );
		xAxisPrimary.getAssociatedAxes( ).add( yAxis );

		// Data Set
		TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
				"March", "April", "May", "June", "July"} );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$//$NON-NLS-5$
		NumberDataSet orthoValues1 = NumberDataSetImpl.create( new double[]{
				12.5, 19.6, 18.3, 13.2, 26.5
		} );
		NumberDataSet orthoValues2 = NumberDataSetImpl.create( new double[]{
				22.7, 23.6, 38.3, 43.2, 40.5
		} );


		SampleData sd = DataFactory.eINSTANCE.createSampleData( );
		BaseSampleData sdBase = DataFactory.eINSTANCE.createBaseSampleData( );
		sdBase.setDataSetRepresentation( "" );//$NON-NLS-1$
		sd.getBaseSampleData( ).add( sdBase );

		OrthogonalSampleData sdOrthogonal1 = DataFactory.eINSTANCE.createOrthogonalSampleData( );
		sdOrthogonal1.setDataSetRepresentation( "" );//$NON-NLS-1$
		sdOrthogonal1.setSeriesDefinitionIndex( 0 );
		sd.getOrthogonalSampleData( ).add( sdOrthogonal1 );

		OrthogonalSampleData sdOrthogonal2 = DataFactory.eINSTANCE.createOrthogonalSampleData( );
		sdOrthogonal2.setDataSetRepresentation( "" );//$NON-NLS-1$
		sdOrthogonal2.setSeriesDefinitionIndex( 1 );
		sd.getOrthogonalSampleData( ).add( sdOrthogonal2 );

		cwaBar.setSampleData( sd );

		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		sdX.getSeriesPalette( ).shift( 0, 10 );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Series (1)
		BarSeries bs1 = (BarSeries) BarSeriesImpl.create( );
		bs1.setDataSet( orthoValues1 );
		bs1.getLabel( ).setVisible( false );
		bs1.setLabelPosition( Position.OUTSIDE_LITERAL );
		bs1.setTranslucent(true);
		// Y-Series (2)
		BarSeries bs2 = (BarSeries) BarSeriesImpl.create( );
		bs2.setDataSet( orthoValues2 );
		bs2.getLabel( ).setVisible( false );
		bs2.setLabelPosition( Position.OUTSIDE_LITERAL );
		bs2.setTranslucent(true);

		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY );

		sdY.getSeries( ).add( bs1 );
		sdY.getSeries( ).add( bs2 );

		// Z-Series
		//		SeriesDefinition sdZ = SeriesDefinitionImpl.create( );
		//		zAxis.getSeriesDefinitions( ).add( sdZ );
		return cwaBar;
	}

	/**
	 * 3 type de affichage.[ChartDimension.THREE_DIMENSIONAL_LITERAL,
	 * 						ChartDimension.TWO_DIMENSIONAL_LITERAL,
	 * 						ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL -- defaut]
	 * 
	 * @return
	 */
	public static final Chart createBar( )
	{
		ChartWithAxes cwaBar = ChartWithAxesImpl.create();
		cwaBar.setDimension( ChartDimension.THREE_DIMENSIONAL_LITERAL );
//		cwaBar.setDimension( ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL );
		cwaBar.setType("Bar Chart" ); //$NON-NLS-1$
		cwaBar.setSubType("Side-by-side"); //$NON-NLS-1$
		// Plot
		cwaBar.getBlock().setBackground( ColorDefinitionImpl.WHITE( ) );
		cwaBar.getBlock().getOutline( ).setVisible( true );
		// Title
		cwaBar.getTitle( ).getLabel( ).getCaption( ).setValue( "2D Bar Chart" );//$NON-NLS-1$

		// Legend
		cwaBar.getLegend( ).setItemType( LegendItemType.CATEGORIES_LITERAL);

		// X-Axis
		Axis xAxisPrimary = cwaBar.getPrimaryBaseAxes( )[0];
		xAxisPrimary.setType( AxisType.TEXT_LITERAL );
		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getOrigin( ).setType( IntersectionType.MIN_LITERAL );
		xAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 90 );

		// Y-Axis
		Axis yAxisPrimary = cwaBar.getPrimaryOrthogonalAxis( xAxisPrimary );
		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
		yAxisPrimary.setType( AxisType.LINEAR_LITERAL );
//		yAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 90 );

		// Z-Axis
		Axis zAxis = AxisImpl.create( Axis.ANCILLARY_BASE );
		zAxis.setType( AxisType.TEXT_LITERAL );
		zAxis.setLabelPosition( Position.BELOW_LITERAL );
		zAxis.setTitlePosition( Position.BELOW_LITERAL );
		zAxis.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		zAxis.setOrientation( Orientation.HORIZONTAL_LITERAL );
		xAxisPrimary.getAncillaryAxes( ).add( zAxis );

		// Data Set
		TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
				"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"} ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		NumberDataSet orthoValues1 = NumberDataSetImpl.create( new double[]{
				25, 35, 15, 5, 20
		} );
		NumberDataSet orthoValues2 = NumberDataSetImpl.create( new double[]{
				5, 10, 25, 10, 5
		} );

		SampleData sd = DataFactory.eINSTANCE.createSampleData( );
		BaseSampleData sdBase = DataFactory.eINSTANCE.createBaseSampleData( );
		sdBase.setDataSetRepresentation( "" );//$NON-NLS-1$
		sd.getBaseSampleData( ).add( sdBase );

		OrthogonalSampleData sdOrthogonal1 = DataFactory.eINSTANCE.createOrthogonalSampleData( );
		sdOrthogonal1.setDataSetRepresentation( "" );//$NON-NLS-1$
		sdOrthogonal1.setSeriesDefinitionIndex( 0 );
		sd.getOrthogonalSampleData( ).add( sdOrthogonal1 );

		OrthogonalSampleData sdOrthogonal2 = DataFactory.eINSTANCE.createOrthogonalSampleData( );
		sdOrthogonal2.setDataSetRepresentation( "" );//$NON-NLS-1$
		sdOrthogonal2.setSeriesDefinitionIndex( 1 );
		sd.getOrthogonalSampleData( ).add( sdOrthogonal2 );

		cwaBar.setSampleData( sd );

		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );
		
		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		sdX.getSeriesPalette( ).shift( 0, 10 );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Series (1)
		BarSeries bs1 = (BarSeries) BarSeriesImpl.create( );
		bs1.setDataSet( orthoValues1 );
		{
		/** afficher valeur sur Bar **/
		bs1.getLabel( ).setVisible(true);
		bs1.setLabelPosition( Position.OUTSIDE_LITERAL );
		}
		{
		/*** set attribute translucent ***/	
		bs1.setTranslucent(true);
		}
		{
		/*** change format Bar ***/		
		bs1.setRiser( RiserType.TUBE_LITERAL ); 
		}
		 

//		bs1.setRiserOutline( null );
//		bs1.getLabel( ).setBackground( ColorDefinitionImpl.CYAN( ) );

		// Y-Series (2)
		BarSeries bs2 = (BarSeries) BarSeriesImpl.create( );
		bs2.setDataSet( orthoValues2 );
		bs2.getLabel( ).setVisible( true );
		bs2.setLabelPosition( Position.OUTSIDE_LITERAL );
		bs2.setTranslucent(true);
		bs2.setRiser( RiserType.TUBE_LITERAL );
		
		
		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
		sdY.getSeriesPalette().update(GradientImpl.create
				//(ColorDefinitionImpl.create(255,255,255), 
				(ColorDefinitionImpl.RED(),
				 ColorDefinitionImpl.create(200,0,0,150), 
				 90, false));

		sdY.getSeries( ).add( bs1 );
		sdY.getSeries( ).add( bs2 );

		// Z-Series
		SeriesDefinition sdZ = SeriesDefinitionImpl.create( );
		zAxis.getSeriesDefinitions( ).add( sdZ );

//		 Rotate the chart
				cwaBar.setRotation( Rotation3DImpl.create( new Angle3D[]{
					Angle3DImpl.create( -10, 25, 0 )
				} ) );
		updateDataSet( cwaBar );
		return cwaBar;
	}	

	static final void updateDataSet( ChartWithAxes cwaBar )
	{
		//		// Associate with Data Set
				TextDataSet categoryValues = TextDataSetImpl.create( sa );
				NumberDataSet seriesOneValues = NumberDataSetImpl.create( da1 );
				NumberDataSet seriesTwoValues = NumberDataSetImpl.create( da2 );
				// X-Axis
				Axis xAxisPrimary = cwaBar.getPrimaryBaseAxes( )[0];
				SeriesDefinition sdX = (SeriesDefinition) xAxisPrimary.getSeriesDefinitions( )
				.get( 0 );
				( (Series) sdX.getSeries( ).get( 0 ) ).setDataSet( categoryValues );
				
				Palette p = sdX.getSeriesPalette();
				colornum = colornum + 1;
				if( colornum > 9)colornum=0;
				sdX.getSeriesPalette().shift(-colornum , 10);
				// Y-Axis
				Axis yAxisPrimary = cwaBar.getPrimaryOrthogonalAxis( xAxisPrimary );
				SeriesDefinition sdY = (SeriesDefinition) yAxisPrimary.getSeriesDefinitions( )
				.get( 0 );
				( (Series) sdY.getSeries( ).get( 0 ) ).setDataSet( seriesOneValues );
				( (Series) sdY.getSeries( ).get( 1 ) ).setDataSet( seriesTwoValues );


		// Rotate the chart

				double currx, curry, currz;
				for ( Iterator<?> itr = cwaBar.getRotation().getAngles( ).iterator( ); itr.hasNext( ); )
				{
					Angle3D agl = (Angle3D) itr.next( );
					if ( agl.getType( ) == AngleType.NONE_LITERAL )
					{
						curry = agl.getYAngle();
						currx = agl.getXAngle();
						currz = agl.getZAngle();
						double newy = curry+10;
						if( newy > 360 )newy=0;
						cwaBar.setRotation( Rotation3DImpl.create( new Angle3D[]{
								Angle3DImpl.create( -5, newy, 0 )
							} ) );
					}
				}


	}
	// Live Date Set
	private static final String[] sa = {
		"One", "Two", "Three", "Four", "Five",//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$//$NON-NLS-5$
		"Six", "Seven", "Eight", "Nine", "Ten"};//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$//$NON-NLS-5$
	private static final double[] da1 = {
		56.99,
		352.95,
		-201.95,
		299.95,
		-95.95,
		25.45,
		129.33,
		-26.5,
		43.5,
		122
	};
	private static final double[] da2 = {
		20, 35, 59, 105, 150, -37, -65, -99, -145, -185
	};
	private Image imgChart;
	private GC gcImage;
	private Bounds bo;
	static final void scrollData( ChartWithAxes cwa )
	{
		// Scroll the bar (Y) series
		double dTemp = da1[0];
		for ( int i = 0; i < da1.length - 1; i++ )
		{
			da1[i] = da1[i + 1];
		}
		da1[da1.length - 1] = dTemp;
		// Scroll the line (Y) series
		dTemp = da2[0];
		for ( int i = 0; i < da2.length - 1; i++ )
		{
			da2[i] = da2[i + 1];
		}
		da2[da2.length - 1] = dTemp;
		// Scroll X series
		String sTemp = sa[0];
		for ( int i = 0; i < sa.length - 1; i++ )
		{
			sa[i] = sa[i + 1];
		}
		sa[sa.length - 1] = sTemp;
		updateDataSet( cwa );
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swt.events.PaintListener#paintControl(org.eclipse.swt.events
	 * .PaintEvent)
	 */
	public final void paintControl( PaintEvent e )
	{
		Rectangle d = this.getClientArea( );
		if ( bFirstPaint ){
			bFirstPaint = false;
			//if resized
			if( (imgChart !=null) && (!imgChart.isDisposed()))imgChart.dispose();
			if( (gcImage !=null) && (!gcImage.isDisposed()))gcImage.dispose();

			imgChart = new Image( this.getDisplay( ), d );
			gcImage = new GC( imgChart );
			idr.setProperty( IDeviceRenderer.GRAPHICS_CONTEXT, gcImage );
			bo = BoundsImpl.create( 0, 0, d.width, d.height );
			bo.scale( 72d / idr.getDisplayServer( ).getDpiResolution( ) );
		}
		Generator gr = Generator.instance( );
		try
		{
			
			gcs = gr.build( idr.getDisplayServer( ), chart, bo, null, null, null );
			/** action  **/
//			gcs.getRunTimeContext().setActionRenderer( new MyActionRenderer(chart));
//			idr.setProperty(IDeviceRenderer.UPDATE_NOTIFIER,
//					new EmptyUpdateNotifier(chart, gcs.getChartModel()));
			//gcs.getRunTimeContext().setActionRenderer( new MyActionRenderer()); 
			gcs.getRunTimeContext().setActionRenderer(myActionRenderer);
			/*************/
			gr.render( idr, gcs );
			GC gc = e.gc;
			gc.drawImage( imgChart, d.x, d.y );
		}
		catch ( ChartException ce )
		{
			ce.printStackTrace( );
		}
//		testAffichageImage();//pas run
//		bFirstPaint = false;
		/** for move chart **/
//		Display.getDefault( ).timerExec( 250, new Runnable( ) {
//			public void run( )
//			{
//				chartRefresh( );
//			}
//		} );
		
	}
	public void testAffichageImage() {
		BufferedImage img = new BufferedImage(600, 600,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();

		Graphics2D g2d = (Graphics2D) g;
		//Look at IDeviceRenderer.java for all properties
		//like DPI_RESOLUTION
		//FILE_IDENTIFIER
		//FORMAT_IDENTIFIER
		//UPDATE_NOTIFIER
		idr.setProperty(IDeviceRenderer.GRAPHICS_CONTEXT, g2d);
		idr.setProperty(IDeviceRenderer.FILE_IDENTIFIER, OUTPUT); //$NON-NLS-1$

		//Set the bounds for the entire chart
		Bounds bo = BoundsImpl.create(0, 0, 600, 600);
		bo.scale(72d / idr.getDisplayServer().getDpiResolution());
		IGenerator gr = ChartEngine.instance().getGenerator();
		try {

			gcs = gr.build(idr.getDisplayServer(), chart, bo, null, null, null);
			//gcs.getRunTimeContext().setActionRenderer( new MyActionRenderer());
			idr.setProperty(IDeviceRenderer.UPDATE_NOTIFIER,
					new EmptyUpdateNotifier(chart, gcs.getChartModel()));

			gr.render(idr, gcs);
			String im = ((IImageMapEmitter) idr).getImageMap();

			BufferedWriter out = new BufferedWriter(new FileWriter(OUTPUT_HTML));

			out.write("<html>");
			out.newLine();
			out.write("<body>");
			out.newLine();
			out.write("<div>");
			out.newLine();
			out.write("<map name='testmap'>");
			out.write(im);
			out.write("</map>");
			out.newLine();
			out
			.write("<img id=myimage src='standalone.png' usemap='#testmap'></img>");
			out.newLine();
			out.write("</div>");
			out.newLine();
			out.write("</body>");
			out.newLine();
			out.write("</html>");
			out.newLine();
			out.close();

			System.out.println(im);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse
	 * .swt.events.SelectionEvent)
	 */
	private void chartRefresh( )
	{
		if ( !isDisposed( ) )
		{
			final Generator gr = Generator.instance( );
			scrollData( (ChartWithAxes) chart );
			// Refresh
			try
			{
				gr.refresh( gcs );
			}
			catch ( ChartException ex )
			{
				ex.printStackTrace( );
			}
			redraw( );
		}
	}
	public void controlMoved(ControlEvent arg0) {
		bFirstPaint = true;

	}
	public void controlResized(ControlEvent arg0) {
		bFirstPaint = true;
	}
	
	@Override
	public void callback(Object event, Object source, CallBackValue value) {
		// TODO Auto-generated method stub
		Text text = myActionRenderer.getTextComment();
		Object obj1 = ((DataPointHints)((WrappedStructureSource)source).getSource()).getBaseValue();
		Object obj2 = ((DataPointHints)((WrappedStructureSource)source).getSource()).getOrthogonalValue().toString();
		Object obj3 = ((DataPointHints)((WrappedStructureSource)source).getSource()).getSeriesValue();
		String comment = "Chiffre d'affaire ("+obj3+") est "+obj2+"€ en "+obj1+".";
		text.setText(comment);
		myActionRenderer.getCompositeComment().layout();
		
	}
	@Override
	public Chart getDesignTimeModel() {
		// TODO Auto-generated method stub
		//return null;
		return chart;  
	}
	@Override
	public Chart getRunTimeModel() {
		// TODO Auto-generated method stub
//		return null;
		return gcs.getChartModel( ); 
	}
	@Override
	public Object peerInstance() {
		// TODO Auto-generated method stub
//		return null;
		return this;
	}
	@Override
	public void regenerateChart() {
		// TODO Auto-generated method stub
		redraw();
	}
	@Override
	public void repaintChart() {
		// TODO Auto-generated method stub
		redraw( ); 
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("widgetDefaultSelected");
	}
	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("widgetSelected");
	}
}