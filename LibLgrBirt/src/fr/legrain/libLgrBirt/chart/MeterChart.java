/**
 * 
 */
package fr.legrain.libLgrBirt.chart;

import java.awt.font.LineMetrics;

import org.eclipse.birt.chart.event.Line3DRenderEvent;
import org.eclipse.birt.chart.model.ChartWithoutAxes;
import org.eclipse.birt.chart.model.DialChart;
import org.eclipse.birt.chart.model.attribute.ChartDimension;
import org.eclipse.birt.chart.model.attribute.Fill;
import org.eclipse.birt.chart.model.attribute.LineAttributes;
import org.eclipse.birt.chart.model.attribute.LineDecorator;
import org.eclipse.birt.chart.model.attribute.LineStyle;
import org.eclipse.birt.chart.model.attribute.Position;
import org.eclipse.birt.chart.model.attribute.TickStyle;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
import org.eclipse.birt.chart.model.attribute.impl.GradientImpl;
import org.eclipse.birt.chart.model.attribute.impl.InsetsImpl;
import org.eclipse.birt.chart.model.attribute.impl.LineAttributesImpl;
import org.eclipse.birt.chart.model.component.DialRegion;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.DialRegionImpl;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.TextDataSet;
import org.eclipse.birt.chart.model.data.impl.NumberDataElementImpl;
import org.eclipse.birt.chart.model.data.impl.NumberDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.data.impl.TextDataSetImpl;
import org.eclipse.birt.chart.model.impl.DialChartImpl;
import org.eclipse.birt.chart.model.layout.Legend;
import org.eclipse.birt.chart.model.layout.Plot;
import org.eclipse.birt.chart.model.layout.TitleBlock;
import org.eclipse.birt.chart.model.type.DialSeries;
import org.eclipse.birt.chart.model.type.impl.DialSeriesImpl;
import org.eclipse.swt.widgets.Composite;

/**
 * Classe permettant la création d'une jauge de type "Dial" ou "Meter"
 * La jauge prend en argument le titre et le pourcentage à afficher
 * @author nicolas²
 *
 */
public class MeterChart extends AbstractChartBuilder {

	// Donnée "indice" en pourcentage
	private double dataSetChart;

	/* Constructeur par défaut */
	public MeterChart(Composite parent, int style, DataSetChart dataSetChart) {
		super(parent, style, dataSetChart);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructeur du Dial Chart à une aiguille et à une donnée
	 * @param parent composite parent du dial chart
	 * @param style style du dial chart
	 * @param dataSetChart donnée à afficher sur le dial
	 * @param titleChart titre du dial
	 */
	public MeterChart(Composite parent, int style,
			double dataSetChart,String titleChart){
		this(parent,style,null);
		this.dataSetChart = dataSetChart;
		this.title = titleChart;
		this.composite = parent;
	}

	/* (non-Javadoc)
	 * @see fr.legrain.libLgrBirt.chart.AbstractChartBuilder#createChart()
	 */
	@Override
	protected void createChart() {
		// Création du dial chart
		chart = DialChartImpl.create( );
		((DialChart) chart).setDialSuperimposition( false );
		chart.setGridColumnCount( 2 );
		chart.setSeriesThickness( 25 );

	}

	@Override
	protected void buildTitle() {
		// Création du titre
		TitleBlock tb = chart.getTitle();
		// Mise en place de la valeur du titre
		tb.getLabel( ).getCaption( ).setValue( title );//$NON-NLS-1$
		tb.getLabel().getCaption().getFont().setSize(8);
		tb.getOutline( ).setVisible( false );
		chart.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		
		// Plot
		Plot p = chart.getPlot( );
		p.getClientArea( ).setBackground( ColorDefinitionImpl.WHITE() );
		p.getClientArea( ).getOutline( ).setVisible( false );
		p.getInsets( ).setTop(0);
		p.getOutline( ).setVisible( false );

	}

	@Override
	protected void buildLegend() {
		// Légende, masquée ici
		Legend lg =chart.getLegend( );
		lg.setPosition(Position.RIGHT_LITERAL);
		LineAttributes lia = lg.getOutline( );
		lg.getText( ).getFont( ).setSize( 8 );
		lg.getInsets( ).setTop( 200 );
		lg.getInsets( ).setRight( -110 );
		lg.getInsets( ).setLeft( -50 );
		lg.setBackground( null );
		lg.getOutline( ).setVisible( false );
		lg.getClientArea( ).setBackground( null );
		lg.getClientArea( ).getOutline( ).setVisible( false);
		buildGraph();
	}


	private void buildGraph() {
		// Pas de nom de catégorie == pas de légende
		TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
		null} );//$NON-NLS-1$

		SeriesDefinition sd = SeriesDefinitionImpl.create( );
		((ChartWithoutAxes) chart).getSeriesDefinitions( ).add( sd );
		Series seCategory = (Series) SeriesImpl.create( );

		// Couleur de la flèche
		final Fill[] fiaBase = {
				ColorDefinitionImpl.BLACK(),
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
		// POSITION DE l'AIGUILLE
		seDial.setDataSet( NumberDataSetImpl.create( dataSetChart ) );
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
		.setColor( ColorDefinitionImpl.GREY( ).translucent() );
		seDial.getDial( )
		.getMinorGrid( )
		.setTickStyle( TickStyle.BELOW_LITERAL );
		seDial.getDial( )
		.getMinorGrid( )
		.setTickCount(10);
		seDial.getDial( )
		.getScale( )
		.setMin( NumberDataElementImpl.create( 0 ) );
		seDial.getDial( )
		.getScale( )
		.setMax( NumberDataElementImpl.create( 100 ) );
		seDial.getDial( ).getScale( ).setStep( 25);
		seDial.getLabel( )
		.setOutline( LineAttributesImpl.create( ColorDefinitionImpl.GREY( )
				.darker( ),
				LineStyle.SOLID_LITERAL,
				1 ) );
		seDial.getLabel( ).setBackground( ColorDefinitionImpl.GREY( )
				.brighter( ) );

		// Première région
		DialRegion dregion1 = DialRegionImpl.create( );
//		dregion1.setFill( ColorDefinitionImpl.GREEN( ).translucent() );
		dregion1.setFill(GradientImpl.create( ColorDefinitionImpl.create( 212,
				246,
				160 ).translucent(),
				ColorDefinitionImpl.create( 124, 207, 89 ),
				-35,
				false ));
		dregion1.setStartValue( NumberDataElementImpl.create( 0 ) );
		dregion1.setEndValue( NumberDataElementImpl.create( 33 ) );
		seDial.getDial( ).getDialRegions( ).add( dregion1 );
		
		// Deuxième région
		DialRegion dregion2 = DialRegionImpl.create( );
//		dregion2.setFill( ColorDefinitionImpl.ORANGE( ).translucent() );
		dregion2.setFill(GradientImpl.create( ColorDefinitionImpl.create( 255,
				188,
				153 ).translucent(),
				ColorDefinitionImpl.create( 254, 140, 77 ),
				-35,
				false ));
		
		dregion2.setStartValue( NumberDataElementImpl.create(33 ) );
		dregion2.setEndValue( NumberDataElementImpl.create( 66 ) );
		seDial.getDial( ).getDialRegions( ).add( dregion2 );
		
		// Troisième région
		DialRegion dregion3 = DialRegionImpl.create( );
//		dregion3.setFill( ColorDefinitionImpl.RED( ).translucent() );
		dregion3.setFill(GradientImpl.create( ColorDefinitionImpl.create( 237,
				168,
				161 ).translucent(),
				ColorDefinitionImpl.create( 223, 93, 91 ),
				-35,
				false ));
		dregion3.setStartValue( NumberDataElementImpl.create(66 ) );
		dregion3.setEndValue( NumberDataElementImpl.create( 100 ) );
		seDial.getDial( ).getDialRegions( ).add( dregion3 );

		sd.getSeriesDefinitions( ).add( sdCity );
		sdCity.getSeries( ).add( seDial );
	}


}
