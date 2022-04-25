package fr.legrain.libLgrBirt.chart.test.data.test;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.eclipse.birt.chart.device.IDeviceRenderer;
import org.eclipse.birt.chart.exception.ChartException;
import org.eclipse.birt.chart.factory.GeneratedChartState;
import org.eclipse.birt.chart.factory.Generator;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.ChartWithAxes;
import org.eclipse.birt.chart.model.attribute.Anchor;
import org.eclipse.birt.chart.model.attribute.AxisType;
import org.eclipse.birt.chart.model.attribute.Bounds;
import org.eclipse.birt.chart.model.attribute.IntersectionType;
import org.eclipse.birt.chart.model.attribute.LineAttributes;
import org.eclipse.birt.chart.model.attribute.LineStyle;
import org.eclipse.birt.chart.model.attribute.TickStyle;
import org.eclipse.birt.chart.model.attribute.impl.BoundsImpl;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
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
import org.eclipse.birt.chart.model.type.AreaSeries;
import org.eclipse.birt.chart.model.type.impl.AreaSeriesImpl;
import org.eclipse.birt.chart.util.PluginSettings;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class CreateChart {

	private IDeviceRenderer idr = null;
	private Chart chart = null;
	private GeneratedChartState gcs = null;
	private Image imgChart;
	private GC gcImage;
	private boolean bFirstPaint = true;
	private Bounds bo;
	public final void paintControl( PaintEvent e,Composite composite )
	{
		Rectangle d = composite.getClientArea( );
		if ( bFirstPaint )
		{
			//if resized
			if( (imgChart !=null) && (!imgChart.isDisposed()))imgChart.dispose();
			if( (gcImage !=null) && (!gcImage.isDisposed()))gcImage.dispose();

			imgChart = new Image( composite.getDisplay( ), d );
			gcImage = new GC( imgChart );
			idr.setProperty( IDeviceRenderer.GRAPHICS_CONTEXT, gcImage );
			bo = BoundsImpl.create( 0, 0, d.width, d.height );
			bo.scale( 72d / idr.getDisplayServer( ).getDpiResolution( ) );
		}
		Generator gr = Generator.instance( );
		try
		{
			gcs = gr.build( idr.getDisplayServer( ), chart, bo, null, null, null );
			gr.render( idr, gcs );
			GC gc = e.gc;
			gc.drawImage( imgChart, d.x, d.y );
		}
		catch ( ChartException ce )
		{
			ce.printStackTrace( );
		}
		bFirstPaint = false;
//		Display.getDefault( ).timerExec( 250, new Runnable( ) {
//			public void run( )
//			{
//				chartRefresh( );
//			}
//		} );
	}
	public void CreateAreaChartMain(Composite composite)
	{
		final PluginSettings ps = PluginSettings.instance( );
		try
		{
			idr = ps.getDevice( "dv.SWT");//$NON-NLS-1$
			

		}
		catch ( ChartException ex )
		{
			ex.printStackTrace( );
		}
		chart = createAreaChart_1( );
		paintControl(null,composite);
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
}
