package fr.legrain.libLgrBirt.chart;

import org.apache.log4j.Logger;
import org.eclipse.birt.chart.api.ChartEngine;
import org.eclipse.birt.chart.device.ICallBackNotifier;
import org.eclipse.birt.chart.device.IDeviceRenderer;
import org.eclipse.birt.chart.exception.ChartException;
import org.eclipse.birt.chart.factory.GeneratedChartState;
import org.eclipse.birt.chart.factory.Generator;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.ChartWithAxes;
import org.eclipse.birt.chart.model.attribute.Bounds;
import org.eclipse.birt.chart.model.attribute.CallBackValue;
import org.eclipse.birt.chart.model.attribute.impl.BoundsImpl;
import org.eclipse.birt.chart.model.component.Axis;
import org.eclipse.birt.chart.model.layout.TitleBlock;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;



public abstract class AbstractChartBuilder extends Composite implements PaintListener,ControlListener
					  ,ICallBackNotifier,SelectionListener{

	static Logger logger = Logger.getLogger(AbstractChartBuilder.class.getName());
	
	private boolean bFirstPaint = true;
	private Image imgChart;
	private GC gcImage;
	private Bounds bo;
	private IDeviceRenderer idr = null;
	protected Chart chart = null;
	private GeneratedChartState gcs = null;
	protected Composite composite;
	/**
	 * Chart title.
	 */
    protected String title = null;
	/**
     * Font name for all titles, labels, and values.
     */
    protected final static String FONT_NAME = "MS Sans Serif";
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
    protected Axis xAxisPrimary = null;

    /**
     * Y axis.
     */
    protected Axis yAxisPrimary = null;
    /**
     * Provides data for chart.
     */
    
    
    protected DataSetChart dataSetChart = null;
    
   
	public AbstractChartBuilder(Composite parent,int style,DataSetChart dataSetChart) {
		super(parent, style);
		try {
			this.idr = ChartEngine.instance().getRenderer("dv.SWT");
			idr.setProperty(IDeviceRenderer.UPDATE_NOTIFIER,this);
		} catch (ChartException e) {
			// TODO Auto-generated catch block
			logger.error("AbstractChartBuilder ==> ", e);
		}
		this.dataSetChart = dataSetChart;
	}

	@Override
	public void paintControl(PaintEvent e) {
		// TODO Auto-generated method stub
		Rectangle d = this.getClientArea( );
		if ( bFirstPaint )
		{
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

			gcs = gr.build( idr.getDisplayServer(), chart, bo, null, null, null );
			/** action  **/
//			gcs.getRunTimeContext().setActionRenderer( new MyActionRenderer(chart));
//			idr.setProperty(IDeviceRenderer.UPDATE_NOTIFIER,
//					new EmptyUpdateNotifier(chart, gcs.getChartModel()));
			/*************/
			gr.render( idr, gcs );
			GC gc = e.gc;
			gc.drawImage( imgChart, d.x, d.y );
		}
		catch ( ChartException ce ){
			
			logger.error("class AbstractChartBuilder", ce);
		}
		//		testAffichageImage();//pas run
		bFirstPaint = false;
	}

	@Override
	public void controlMoved(ControlEvent e) {
		// TODO Auto-generated method stub
		bFirstPaint = true;
	}

	@Override
	public void controlResized(ControlEvent e) {
		// TODO Auto-generated method stub
		bFirstPaint = true;
	}

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
     * Indicates the major scale X axis
     */
    protected void buildXAxisMajorGrid() {

    }
    /**
     * Builds Y axis.
     */
    protected void buildYAxis() {

    }
    /**
     * Indicates the major scale Y axis
     */
    protected void buildYAxisMajorGrid() {

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
	
    public Chart getChart() {
		return chart;
	}
    
    protected void showChart() {
    	this.setLayoutData(new GridData(GridData.FILL_BOTH));
    	this.addPaintListener(this);
    	this.addControlListener(this);
	}
    
//    protected void disposeChart() {
//    	for (int i = 0; i < composite.getChildren().length; i++) {
//    		composite.dispose();
//		}
//    }
    
    /**
     * Builds one chart.
     */
    public void build() {
//    	disposeChart();
        createChart();
        buildTitle();
        buildPlot();
        buildLegend();
        buildXAxis();
        buildXAxisMajorGrid();
        buildYAxis();
        buildYAxisMajorGrid();
        buildXSeries();
        buildYSeries();
        showChart();
    }
    
    @Override
	public void callback(Object event, Object source, CallBackValue value) {
		// TODO Auto-generated method stub
    	System.out.println("here");
		
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
		//return null;
		return gcs.getChartModel();
	}

	@Override
	public Object peerInstance() {
		// TODO Auto-generated method stub
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
		redraw();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
}

