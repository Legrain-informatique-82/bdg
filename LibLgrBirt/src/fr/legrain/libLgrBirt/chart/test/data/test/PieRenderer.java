package fr.legrain.libLgrBirt.chart.test.data.test;
//package fr.legrain.tiers.statistiques.data.test;
//
//
//import java.text.MessageFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//
//import org.eclipse.birt.chart.computation.BoundingBox;
//import org.eclipse.birt.chart.computation.DataPointHints;
//import org.eclipse.birt.chart.computation.IConstants;
//import org.eclipse.birt.chart.computation.Methods;
//import org.eclipse.birt.chart.device.IDeviceRenderer;
//import org.eclipse.birt.chart.device.IDisplayServer;
//import org.eclipse.birt.chart.device.IStructureDefinitionListener;
//import org.eclipse.birt.chart.engine.extension.i18n.Messages;
//import org.eclipse.birt.chart.event.ArcRenderEvent;
//import org.eclipse.birt.chart.event.AreaRenderEvent;
//import org.eclipse.birt.chart.event.EventObjectCache;
//import org.eclipse.birt.chart.event.InteractionEvent;
//import org.eclipse.birt.chart.event.LineRenderEvent;
//import org.eclipse.birt.chart.event.PolygonRenderEvent;
//import org.eclipse.birt.chart.event.StructureSource;
//import org.eclipse.birt.chart.event.TextRenderEvent;
//import org.eclipse.birt.chart.event.WrappedStructureSource;
//import org.eclipse.birt.chart.exception.ChartException;
//import org.eclipse.birt.chart.extension.render.Pie;
//import org.eclipse.birt.chart.log.ILogger;
//import org.eclipse.birt.chart.log.Logger;
//import org.eclipse.birt.chart.model.ChartWithoutAxes;
//import org.eclipse.birt.chart.model.attribute.Bounds;
//import org.eclipse.birt.chart.model.attribute.ChartDimension;
//import org.eclipse.birt.chart.model.attribute.ColorDefinition;
//import org.eclipse.birt.chart.model.attribute.Fill;
//import org.eclipse.birt.chart.model.attribute.Gradient;
//import org.eclipse.birt.chart.model.attribute.Insets;
//import org.eclipse.birt.chart.model.attribute.LeaderLineStyle;
//import org.eclipse.birt.chart.model.attribute.LegendItemType;
//import org.eclipse.birt.chart.model.attribute.LineAttributes;
//import org.eclipse.birt.chart.model.attribute.LineStyle;
//import org.eclipse.birt.chart.model.attribute.Location;
//import org.eclipse.birt.chart.model.attribute.Palette;
//import org.eclipse.birt.chart.model.attribute.Position;
//import org.eclipse.birt.chart.model.attribute.Size;
//import org.eclipse.birt.chart.model.attribute.impl.BoundsImpl;
//import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
//import org.eclipse.birt.chart.model.attribute.impl.GradientImpl;
//import org.eclipse.birt.chart.model.attribute.impl.InsetsImpl;
//import org.eclipse.birt.chart.model.attribute.impl.LineAttributesImpl;
//import org.eclipse.birt.chart.model.attribute.impl.LocationImpl;
//import org.eclipse.birt.chart.model.attribute.impl.SizeImpl;
//import org.eclipse.birt.chart.model.component.Label;
//import org.eclipse.birt.chart.model.component.impl.LabelImpl;
//import org.eclipse.birt.chart.model.data.Trigger;
//import org.eclipse.birt.chart.model.data.impl.TriggerImpl;
//import org.eclipse.birt.chart.model.type.PieSeries;
//import org.eclipse.birt.chart.plugin.ChartEngineExtensionPlugin;
//import org.eclipse.birt.chart.script.ScriptHandler;
//import org.eclipse.birt.chart.util.ChartUtil;
//import org.eclipse.emf.common.util.EList;
//import org.eclipse.emf.ecore.util.EcoreUtil;
//
///**
// * PieRenderer
// */
//public class PieRenderer{
//
//	static final int UNKNOWN = 0;
//
//	private static final int LOWER = 1;
//
//	private static final int UPPER = 2;
//
//	private static final double LEADER_TICK_MIN_SIZE = 10; // POINTS
//
//	private static final int LESS = -1;
//
//	private static final int MORE = 1;
//
//	private static final int EQUAL = 0;
//
//	private transient final double[] daAngleDelta, daPercentDelta;
//
//	private transient LeaderLine2D[] lla = null;
//
//	private final Position lpDataPoint;
//
//	private final Position lpSeriesTitle;
//
//	private transient double dLeaderTick;
//
//	private transient double dLeaderLength;
//
//	private final LeaderLineStyle lls;
//
//	private transient double dStartAngle = 0;
//
//	/**
//	 * Series thickness
//	 */
//	private transient double dThickness;
//
//	private transient double dExplosion = 0;
//
//	private transient String sExplosionExpression = null;
//
//	private transient boolean[] bExploded;
//
//	private final Pie pie;
//
//	private final PieSeries ps;
//
//	/**
//	 * Holds a list of deferred planes (flat and curved) to be sorted before
//	 * rendering
//	 */
//	private final ArrayList alPlanes = new ArrayList( );
//
//	private final Palette pa;
//
//	private Label laDataPoint;
//
//	private final Label laSeriesTitle;
//
//	private DataPointHints[] dpha;
//
//	private double[] da;
//
//	private int[] categoryIndex;
//
//	private final LineAttributes liaLL;
//
//	private final LineAttributes liaEdges;
//
//	private transient IDisplayServer xs = null;
//
//	private transient IDeviceRenderer idr = null;
//
//	private transient Bounds boTitleContainer = null;
//
//	private transient Insets insCA = null;
//
//	private transient Bounds boSetDuringComputation = null;
//
//	private final boolean bPaletteByCategory;
//
//	private transient boolean bBoundsAdjustedForInsets = false;
//
//	private transient boolean bMinSliceDefined = false;
//
//	private transient double dMinSlice = 0;
//
//	private transient double dAbsoluteMinSlice = 0;
//
//	private transient boolean bPercentageMinSlice = false;
//
//	private transient boolean bMinSliceApplied = false;
//
//	private transient int orginalSliceCount = 0;
//
//	private transient double ratio = 0;
//
//
//	/**
//	 * 
//	 * @param cwoa
//	 * @param pie
//	 * @param dpha
//	 * @param da
//	 * @param pa
//	 */
//	PieRenderer( ChartWithoutAxes cwoa, Pie pie, DataPointHints[] dpha,
//			double[] da, Palette pa ) throws ChartException
//	{
//		this.pa = pa;
//		this.pie = pie;
//		this.dpha = dpha;
//		this.da = da;
//
//		ps = (PieSeries) pie.getSeries( );
//		sExplosionExpression = ps.getExplosionExpression( );
//		dExplosion = ( ps.isSetExplosion( ) ? ps.getExplosion( ) : 0 )
//				* pie.getDeviceScale( );
//		dThickness = ( ( cwoa.getDimension( ) == ChartDimension.TWO_DIMENSIONAL_LITERAL ) ? 0
//				: cwoa.getSeriesThickness( ) )
//				* pie.getDeviceScale( );
//		ratio = ps.isSetRatio( ) ? ps.getRatio( ) : 1;
//		liaLL = ps.getLeaderLineAttributes( );
//		if ( ps.getLeaderLineAttributes( ).isVisible( ) )
//		{
//			dLeaderLength = ps.getLeaderLineLength( ) * pie.getDeviceScale( );
//			dLeaderTick = Math.max( dLeaderLength / 4, LEADER_TICK_MIN_SIZE
//					* pie.getDeviceScale( ) );
//		}
//		else
//		{
//			dLeaderLength = 0;
//			dLeaderTick = LEADER_TICK_MIN_SIZE * pie.getDeviceScale( );
//		}
//
//		liaEdges = LineAttributesImpl.create( ColorDefinitionImpl.BLACK( ),
//				LineStyle.SOLID_LITERAL,
//				1 );
//		bPaletteByCategory = ( cwoa.getLegend( ).getItemType( ) == LegendItemType.CATEGORIES_LITERAL );
//
//		lpDataPoint = ps.getLabelPosition( );
//		lpSeriesTitle = ps.getTitlePosition( );
//		laSeriesTitle = LabelImpl.copyCompactInstance( ps.getTitle( ) );
//		laSeriesTitle.getCaption( )
//				.setValue( pie.getRunTimeContext( )
//						.externalizedMessage( String.valueOf( ps.getSeriesIdentifier( ) ) ) ); // TBD:
//		laSeriesTitle.getCaption( )
//				.getFont( )
//				.setAlignment( pie.switchTextAlignment( laSeriesTitle.getCaption( )
//						.getFont( )
//						.getAlignment( ) ) );
//		// APPLY
//		// FORMAT
//		// SPECIFIER
//		laDataPoint = LabelImpl.copyCompactInstance( ps.getLabel( ) );
//		lls = ps.getLeaderLineStyle( );
//
//		bMinSliceDefined = cwoa.isSetMinSlice( );
//		dMinSlice = cwoa.getMinSlice( );
//		bPercentageMinSlice = cwoa.isMinSlicePercent( );
//
//		int n = da.length;
//		double dTotal = 0;
//		orginalSliceCount = n;
//		for ( int i = 0; i < n; i++ )
//		{
//			if ( da[i] < 0 )
//			{
//				throw new ChartException( ChartEngineExtensionPlugin.ID,
//						ChartException.GENERATION,
//						"exception.pie.negative.dataset.values",//$NON-NLS-1$
//						Messages.getResourceBundle( pie.getRunTimeContext( )
//								.getULocale( ) ) );
//			}
//
//			if ( !Double.isNaN( da[i] ) )
//			{
//				dTotal += da[i];
//			}
//		}
//
//		if ( bMinSliceDefined )
//		{
//			if ( bPercentageMinSlice )
//			{
//				dAbsoluteMinSlice = dMinSlice * dTotal / 100d;
//			}
//			else
//			{
//				dAbsoluteMinSlice = dMinSlice;
//			}
//
//			ArrayList daList = new ArrayList( );
//			ArrayList dphaList = new ArrayList( );
//			ArrayList caIndex = new ArrayList( );
//			double residual = 0;
//			DataPointHints dph = null;
//			for ( int i = 0; i < n; i++ )
//			{
//				// filter null values.
//				if ( Double.isNaN( da[i] ) )
//				{
//					continue;
//				}
//
//				if ( da[i] >= dAbsoluteMinSlice )
//				{
//					daList.add( new Double( da[i] ) );
//					dphaList.add( dpha[i] );
//					caIndex.add( new Integer( i ) );
//				}
//				else if ( dph == null )
//				{
//					residual += da[i];
//					dph = dpha[i].getCopy( );
//				}
//				else
//				{
//					residual += da[i];
//					dph.accumulate( dpha[i].getBaseValue( ),
//							dpha[i].getOrthogonalValue( ),
//							dpha[i].getSeriesValue( ),
//							dpha[i].getPercentileOrthogonalValue( ) );
//				}
//			}
//
//			if ( dph != null )
//			{
//				daList.add( new Double( residual ) );
//				dphaList.add( dph );
//				caIndex.add( new Integer( orginalSliceCount ) );
//
//				bMinSliceApplied = true;
//			}
//
//			Double[] dao = (Double[]) daList.toArray( new Double[daList.size( )] );
//			this.da = new double[dao.length];
//			this.categoryIndex = new int[dao.length];
//			for ( int i = 0; i < dao.length; i++ )
//			{
//				this.da[i] = dao[i].doubleValue( );
//				this.categoryIndex[i] = ( (Integer) caIndex.get( i ) ).intValue( );
//			}
//
//			this.dpha = (DataPointHints[]) dphaList.toArray( new DataPointHints[dphaList.size( )] );
//		}
//		else
//		{
//			ArrayList daList = new ArrayList( );
//			ArrayList dphaList = new ArrayList( );
//			ArrayList caIndex = new ArrayList( );
//			this.categoryIndex = new int[n];
//			for ( int i = 0; i < n; i++ )
//			{
//				categoryIndex[i] = i;
//
//				// filter null values.
//				if ( Double.isNaN( da[i] ) )
//				{
//					continue;
//				}
//				daList.add( new Double( da[i] ) );
//				dphaList.add( dpha[i] );
//				caIndex.add( new Integer( i ) );
//			}
//
//			if ( daList.size( ) != da.length )
//			{
//				Double[] dao = (Double[]) daList.toArray( new Double[daList.size( )] );
//				this.da = new double[dao.length];
//				this.categoryIndex = new int[dao.length];
//				for ( int i = 0; i < dao.length; i++ )
//				{
//					this.da[i] = dao[i].doubleValue( );
//					this.categoryIndex[i] = ( (Integer) caIndex.get( i ) ).intValue( );
//				}
//
//				this.dpha = (DataPointHints[]) dphaList.toArray( new DataPointHints[dphaList.size( )] );
//			}
//		}
//
//		double angleTotal = 0;
//		n = this.da.length;
//		daAngleDelta = new double[n];
//		daPercentDelta = new double[n];
//
//		if ( dTotal == 0 )
//		{
//			dTotal = 1;
//		}
//
//		for ( int i = 0; i < n; i++ )
//		{
//			daAngleDelta[i] = ( this.da[i] / dTotal ) * 360d;
//			daPercentDelta[i] = ( this.da[i] / dTotal ) * 100d;
//			angleTotal += daAngleDelta[i];
//		}
//
//		// complement the last slice with residual angle
//		// TODO What is this for?
//		if ( angleTotal > 0 && 360 - angleTotal > 0.001 ) // handle precision
//		// loss during
//		// computations
//		{
//			daAngleDelta[n - 1] += ( 360 - angleTotal );
//		}
//
//		initExploded( );
//	}
//
//	/**
//	 * 
//	 * @param idr
//	 * @throws RenderingException
//	 */
//	private final void renderDataPoints( IDeviceRenderer idr )
//			throws ChartException
//	{
//		// IF NOT VISIBLE, DON'T DO ANYTHING
//		if ( !laDataPoint.isVisible( ) )
//		{
//			return;
//		}
//
//		final ScriptHandler sh = (ScriptHandler) pie.getRunTimeContext( ).getScriptHandler( );
//		if ( lpDataPoint.getValue( ) == Position.OUTSIDE )
//		{
//			// RENDER SHADOWS (IF ANY) FIRST
//			if ( laDataPoint.getShadowColor( ) != null )
//			{
//				for ( int i = 0; i < lla.length; i++ )
//				{
//					lla[i].render( idr,
//							TextRenderEvent.RENDER_SHADOW_AT_LOCATION,
//							dpha[i] );
//				}
//			}
//
//			// RENDER ACTUAL TEXT CAPTIONS ON DATA POINTS
//			for ( int i = 0; i < lla.length; i++ )
//			{
//				laDataPoint.getCaption( ).setValue( lla[i].getValue( ) );
//				ScriptHandler.callFunction( sh,
//						ScriptHandler.BEFORE_DRAW_DATA_POINT_LABEL,
//						dpha[i],
//						laDataPoint,
//						pie.getRunTimeContext( ).getScriptContext( ) );
//				pie.getRunTimeContext( )
//						.notifyStructureChange( IStructureDefinitionListener.BEFORE_DRAW_DATA_POINT_LABEL,
//								laDataPoint );
//
//				lla[i].setValue( laDataPoint.getCaption( ).getValue( ) );
//				if ( laDataPoint.isVisible( ) )
//				{
//					lla[i].render( idr,
//							TextRenderEvent.RENDER_TEXT_AT_LOCATION,
//							dpha[i] );
//				}
//				ScriptHandler.callFunction( sh,
//						ScriptHandler.AFTER_DRAW_DATA_POINT_LABEL,
//						dpha[i],
//						laDataPoint,
//						pie.getRunTimeContext( ).getScriptContext( ) );
//				pie.getRunTimeContext( )
//						.notifyStructureChange( IStructureDefinitionListener.AFTER_DRAW_DATA_POINT_LABEL,
//								laDataPoint );
//			}
//		}
//		else
//		{
//			// RENDER ACTUAL TEXT CAPTIONS ON DATA POINTS
//			for ( int i = 0; i < lla.length; i++ )
//			{
//				laDataPoint.getCaption( ).setValue( lla[i].getValue( ) );
//				ScriptHandler.callFunction( sh,
//						ScriptHandler.BEFORE_DRAW_DATA_POINT_LABEL,
//						dpha[i],
//						laDataPoint,
//						pie.getRunTimeContext( ).getScriptContext( ) );
//				pie.getRunTimeContext( )
//						.notifyStructureChange( IStructureDefinitionListener.BEFORE_DRAW_DATA_POINT_LABEL,
//								laDataPoint );
//
//				lla[i].setValue( laDataPoint.getCaption( ).getValue( ) );
//				lla[i].render( idr,
//						TextRenderEvent.RENDER_TEXT_IN_BLOCK,
//						dpha[i] );
//				ScriptHandler.callFunction( sh,
//						ScriptHandler.AFTER_DRAW_DATA_POINT_LABEL,
//						dpha[i],
//						laDataPoint,
//						pie.getRunTimeContext( ).getScriptContext( ) );
//				pie.getRunTimeContext( )
//						.notifyStructureChange( IStructureDefinitionListener.AFTER_DRAW_DATA_POINT_LABEL,
//								laDataPoint );
//			}
//		}
//	}
//
//	/**
//	 * 
//	 * @param bo
//	 */
//	private final void computeLabelsInsidePie( Bounds bo )
//			throws IllegalArgumentException
//	{
//		double w = bo.getWidth( ) / 2 - dExplosion;
//		double h = bo.getHeight( ) / 2 - dExplosion - dThickness / 2;
//		double xc = bo.getLeft( ) + w + dExplosion;
//		double yc = bo.getTop( ) + h + dExplosion + dThickness / 2;
//
//		if ( ratio > 0 && w > 0 )
//		{
//			if ( h / w > ratio )
//			{
//				h = w * ratio;
//			}
//			else if ( h / w < ratio )
//			{
//				w = h / ratio;
//			}
//		}
//
//		// detect invalid size.
//		if ( w <= 0 || h <= 0 )
//		{
//			w = h = 1;
//		}
//
//		int n = daAngleDelta.length;
//		double dStart = dStartAngle, dMidAngleInDegrees, dMidAngleInRadians, dSineThetaMid, dCosThetaMid, xDelta, yDelta;
//		BoundingBox bb;
//
//		if ( lla == null || lla.length != n ) // REUSE IF POSSIBLE
//		{
//			lla = new LeaderLine2D[n];
//		}
//
//		for ( int i = 0; i < n; i++ )
//		{
//			dMidAngleInDegrees = dStart + daAngleDelta[i] / 2;
//			dMidAngleInRadians = Math.toRadians( -dMidAngleInDegrees );
//			dSineThetaMid = Math.sin( dMidAngleInRadians );
//			dCosThetaMid = Math.cos( dMidAngleInRadians );
//			if ( bExploded[i] )
//			{
//				xDelta = ( ( w / 1.5d + dExplosion ) * dCosThetaMid );
//				yDelta = ( ( h / 1.5d + dExplosion ) * dSineThetaMid );
//			}
//			else
//			{
//				xDelta = ( ( w / 1.5d ) * dCosThetaMid );
//				yDelta = ( ( h / 1.5d ) * dSineThetaMid );
//			}
//			lla[i] = new LeaderLine2D( 0, 0, 0, 0, 0, 0 );
//			lla[i].setValue( dpha[i].getDisplayValue( ) );
//			laDataPoint.getCaption( ).setValue( lla[i].getValue( ) );
//			bb = Methods.computeBox( xs,
//					IConstants.LEFT/* DONT-CARE */,
//					laDataPoint,
//					0,
//					0 );
//			bb.setLeft( xc + xDelta - bb.getWidth( ) / 2 );
//			bb.setTop( yc - dThickness / 2 + yDelta - bb.getHeight( ) / 2 );
//
//			lla[i].setBounds( bb );
//			lla[i].setValue( dpha[i].getDisplayValue( ) );
//			lla[i].setQuadrant( -1 ); // UNDEFINED INDICATES THAT TEXT WILL BE
//			// RENDERED CENTERED IN BLOCK
//			dStart += daAngleDelta[i];
//		}
//	}
//
//	/**
//	 * 
//	 * @param bo
//	 */
//	private final void computeLabelsOutsidePie( Bounds bo )
//			throws IllegalArgumentException
//	{
//		double w = bo.getWidth( ) / 2 - dExplosion;
//		double h = bo.getHeight( ) / 2 - dExplosion - dThickness / 2;
//		double xc = bo.getLeft( ) + w + dExplosion;
//		double yc = bo.getTop( ) + h + dExplosion + dThickness / 2;
//
//		if ( ratio > 0 && w > 0 )
//		{
//			if ( h / w > ratio )
//			{
//				h = w * ratio;
//			}
//			else if ( h / w < ratio )
//			{
//				w = h / ratio;
//			}
//		}
//
//		// detect invalid size.
//		if ( w <= 0 || h <= 0 )
//		{
//			w = h = 1;
//		}
//
//		int n = daAngleDelta.length;
//		int iLL = 0;
//		BoundingBox bb = null;
//		double dStart = dStartAngle;
//		double dLeaderW = 0, dLeaderH = 0, dBottomLeaderW = 0, dBottomLeaderH = 0, dTopLeaderW = 0, dTopLeaderH = 0;
//		Location center = LocationImpl.create( xc, yc - dThickness / 2 );
//		Location depthCenter = LocationImpl.create( xc, yc ); // center in the
//		// middle of the
//		// depth
//		double dX = 0;
//		double dLeftSide = xc - dExplosion - w;
//		double dRightSide = xc + dExplosion + w;
//		double dMidAngleInDegrees, dMidAngleInRadians, dSineThetaMid, dCosThetaMid, xDelta1, yDelta1, xDelta2, yDelta2;
//
//		if ( lla == null || lla.length != n ) // REUSE IF POSSIBLE
//		{
//			lla = new LeaderLine2D[n];
//		}
//
//		if ( w > h )
//		{
//			dTopLeaderW = dLeaderTick;
//			dTopLeaderH = dLeaderTick * ratio;
//		}
//		else
//		{
//			dTopLeaderH = dLeaderTick;
//			dTopLeaderW = dLeaderTick / ratio;
//		}
//
//		// compute additional bottom tick size due to series thickness.
//
//		for ( int i = 0; i < n; i++ )
//		{
//			dMidAngleInDegrees = dStart + daAngleDelta[i] / 2;
//			dMidAngleInRadians = Math.toRadians( -dMidAngleInDegrees );
//			dSineThetaMid = Math.sin( dMidAngleInRadians );
//			dCosThetaMid = Math.cos( dMidAngleInRadians );
//
//			if ( dThickness > 0
//					&& dMidAngleInDegrees > 180
//					&& dMidAngleInDegrees < 360 )
//			{
//				double dTmpLeaderTick = Math.max( dThickness
//						* dSineThetaMid
//						+ 8
//						* pie.getDeviceScale( ), dLeaderTick );
//				if ( w > h )
//				{
//					dBottomLeaderW = dTmpLeaderTick;
//					dBottomLeaderH = dTmpLeaderTick * ratio;
//				}
//				else
//				{
//					dBottomLeaderH = dTmpLeaderTick;
//					dBottomLeaderW = dTmpLeaderTick / ratio;
//				}
//
//				dLeaderW = dBottomLeaderW;
//				dLeaderH = dBottomLeaderH;
//			}
//			else
//			{
//				dLeaderW = dTopLeaderW;
//				dLeaderH = dTopLeaderH;
//			}
//
//			if ( bExploded[i] )
//			{
//				xDelta1 = ( w + dExplosion ) * dCosThetaMid;
//				yDelta1 = ( h + dExplosion ) * dSineThetaMid;
//				xDelta2 = ( w + dLeaderW + dExplosion ) * dCosThetaMid;
//				yDelta2 = ( h + dLeaderH + dExplosion ) * dSineThetaMid;
//			}
//			else
//			{
//				xDelta1 = ( w ) * dCosThetaMid;
//				yDelta1 = ( h ) * dSineThetaMid;
//				xDelta2 = ( w + dLeaderW ) * dCosThetaMid;
//				yDelta2 = ( h + dLeaderH ) * dSineThetaMid;
//			}
//
//			if ( lls == LeaderLineStyle.STRETCH_TO_SIDE_LITERAL )
//			{
//				if ( dMidAngleInDegrees >= 90 && dMidAngleInDegrees < 270 )
//				{
//					dX = dLeftSide - dLeaderW * 1.5;
//					iLL = IConstants.LEFT;
//				}
//				else
//				{
//					dX = dRightSide + dLeaderW * 1.5;
//					iLL = IConstants.RIGHT;
//				}
//			}
//			else if ( lls == LeaderLineStyle.FIXED_LENGTH_LITERAL )
//			{
//				if ( dMidAngleInDegrees > 90 && dMidAngleInDegrees < 270 )
//				{
//					dX = center.getX( ) + xDelta2 - dLeaderLength;
//					if ( dLeaderLength > 0 )
//					{
//						iLL = IConstants.LEFT;
//					}
//					else
//					{
//						if ( dMidAngleInDegrees < 135 )
//						{
//							iLL = IConstants.TOP;
//						}
//						else if ( dMidAngleInDegrees < 225 )
//						{
//							iLL = IConstants.LEFT;
//						}
//						else if ( dMidAngleInDegrees < 270 )
//						{
//							iLL = IConstants.BOTTOM;
//						}
//						else
//							assert false;
//					}
//				}
//				else
//				{
//					dX = center.getX( ) + xDelta2 + dLeaderLength;
//					if ( dLeaderLength > 0 )
//					{
//						iLL = IConstants.RIGHT;
//					}
//					else
//					{
//						if ( dMidAngleInDegrees <= 45 )
//						{
//							iLL = IConstants.RIGHT;
//						}
//						else if ( dMidAngleInDegrees > 45
//								&& dMidAngleInDegrees <= 90 )
//						{
//							iLL = IConstants.TOP;
//						}
//						else if ( dMidAngleInDegrees <= 315
//								&& dMidAngleInDegrees >= 270 )
//						{
//							iLL = IConstants.BOTTOM;
//						}
//						else if ( dMidAngleInDegrees > 315 )
//						{
//							iLL = IConstants.RIGHT;
//						}
//						else
//							assert false;
//					}
//
//				}
//			}
//			else
//			{
//				// SHOULD'VE ALREADY THROWN THIS EXCEPTION PREVIOUSLY
//			}
//
//			Location relativeCenter;
//			if ( dMidAngleInDegrees > 0 && dMidAngleInDegrees < 180 )
//			{
//				relativeCenter = center;
//			}
//			else
//			{
//				relativeCenter = depthCenter;
//			}
//			lla[i] = new LeaderLine2D( relativeCenter.getX( ) + xDelta1,
//					relativeCenter.getY( ) + yDelta1,
//					relativeCenter.getX( ) + xDelta2,
//					relativeCenter.getY( ) + yDelta2,
//					dX,
//					relativeCenter.getY( ) + yDelta2 );
//			lla[i].setValue( dpha[i].getDisplayValue( ) );
//			laDataPoint.getCaption( ).setValue( lla[i].getValue( ) );
//			bb = Methods.computeBox( xs,
//					iLL,
//					laDataPoint,
//					lla[i].loEnd.getX( ),
//					lla[i].loEnd.getY( ) );
//			lla[i].setBounds( bb ); // NEEDED FOR DYNAMIC REPOSITIONING
//			lla[i].setQuadrant( getQuadrant( dMidAngleInDegrees ) ); // NEEDED
//			// FOR
//			// COMPUTING
//			// DYNAMIC
//			// REPOSITIONING
//			// LIMITS
//			dStart += daAngleDelta[i];
//		}
//	}
//
//	/**
//	 * 
//	 * @param bo
//	 * @param boAdjusted
//	 * @param ins
//	 * @return
//	 * @throws UnexpectedInputException
//	 */
//	private final Insets adjust( Bounds bo, Bounds boAdjusted, Insets ins )
//			throws IllegalArgumentException
//	{
//		computeLabelsOutsidePie( boAdjusted );
//		ins.set( 0, 0, 0, 0 );
//		double dDelta = 0;
//		BoundingBox bb = null;
//		for ( int i = 0; i < lla.length; i++ )
//		{
//			bb = lla[i].getBounds( );
//			if ( bb.getLeft( ) < bo.getLeft( ) )
//			{
//				dDelta = bo.getLeft( ) - bb.getLeft( );
//				if ( ins.getLeft( ) < dDelta )
//				{
//					ins.setLeft( dDelta );
//				}
//			}
//			if ( bb.getTop( ) < bo.getTop( ) )
//			{
//				dDelta = bo.getTop( ) - bb.getTop( );
//				if ( ins.getTop( ) < dDelta )
//				{
//					ins.setTop( dDelta );
//				}
//			}
//			if ( bb.getLeft( ) + bb.getWidth( ) > bo.getLeft( ) + bo.getWidth( ) )
//			{
//				dDelta = bb.getLeft( )
//						+ bb.getWidth( )
//						- bo.getLeft( )
//						- bo.getWidth( );
//				if ( ins.getRight( ) < dDelta )
//				{
//					ins.setRight( dDelta );
//				}
//			}
//			if ( bb.getTop( ) + bb.getHeight( ) > bo.getTop( ) + bo.getHeight( ) )
//			{
//				dDelta = bb.getTop( )
//						+ bb.getHeight( )
//						- bo.getTop( )
//						- bo.getHeight( );
//				if ( ins.getBottom( ) < dDelta )
//				{
//					ins.setBottom( dDelta );
//				}
//			}
//		}
//		return ins;
//	}
//
//	/**
//	 * 
//	 * @param bo
//	 * @throws UndefinedValueException
//	 * @throws UnexpectedInputException
//	 */
//	final void computeInsets( Bounds bo ) throws ChartException,
//			IllegalArgumentException
//	{
//		boSetDuringComputation = BoundsImpl.copyInstance( bo );
//		xs = pie.getXServer( );
//
//		// ALLOCATE SPACE FOR THE SERIES TITLE
//		boTitleContainer = null;
//		if ( laSeriesTitle.isVisible( ) )
//		{
//			if ( lpSeriesTitle == null )
//			{
//				throw new ChartException( ChartEngineExtensionPlugin.ID,
//						ChartException.UNDEFINED_VALUE,
//						"exception.unspecified.visible.series.title", //$NON-NLS-1$
//						Messages.getResourceBundle( pie.getRunTimeContext( )
//								.getULocale( ) ) );
//			}
//
//			final BoundingBox bb = Methods.computeBox( xs,
//					IConstants.BELOW,
//					laSeriesTitle,
//					0,
//					0 );
//			boTitleContainer = BoundsImpl.create( 0, 0, 0, 0 );
//
//			switch ( lpSeriesTitle.getValue( ) )
//			{
//				case Position.BELOW :
//					bo.setHeight( bo.getHeight( ) - bb.getHeight( ) );
//					boTitleContainer.set( bo.getLeft( ), bo.getTop( )
//							+ bo.getHeight( ), bo.getWidth( ), bb.getHeight( ) );
//					break;
//				case Position.ABOVE :
//					boTitleContainer.set( bo.getLeft( ),
//							bo.getTop( ),
//							bo.getWidth( ),
//							bb.getHeight( ) );
//					bo.setTop( bo.getTop( ) + bb.getHeight( ) );
//					bo.setHeight( bo.getHeight( ) - bb.getHeight( ) );
//					break;
//				case Position.LEFT :
//					bo.setWidth( bo.getWidth( ) - bb.getWidth( ) );
//					boTitleContainer.set( bo.getLeft( ),
//							bo.getTop( ),
//							bb.getWidth( ),
//							bo.getHeight( ) );
//					bo.setLeft( bo.getLeft( ) + bb.getWidth( ) );
//					break;
//				case Position.RIGHT :
//					bo.setWidth( bo.getWidth( ) - bb.getWidth( ) );
//					boTitleContainer.set( bo.getLeft( ) + bo.getWidth( ),
//							bo.getTop( ),
//							bb.getWidth( ),
//							bo.getHeight( ) );
//					break;
//				default :
//					throw new IllegalArgumentException( MessageFormat.format( Messages.getResourceBundle( pie.getRunTimeContext( )
//							.getULocale( ) )
//							.getString( "exception.illegal.pie.series.title.position" ), //$NON-NLS-1$
//							new Object[]{
//								lpSeriesTitle
//							} )
//
//					);
//			}
//		}
//
//		if ( !laDataPoint.isSetVisible( ) )
//		{
//			throw new ChartException( ChartEngineExtensionPlugin.ID,
//					ChartException.UNDEFINED_VALUE,
//					"exception.unspecified.datapoint.visibility.pie", //$NON-NLS-1$
//					new Object[]{
//						ps
//					},
//					Messages.getResourceBundle( pie.getRunTimeContext( )
//							.getULocale( ) ) );
//		}
//
//		if ( lpDataPoint == Position.OUTSIDE_LITERAL )
//		{
//			if ( laDataPoint.isVisible( ) ) // FILTERED FOR PERFORMANCE GAIN
//			{
//				// ADJUST THE BOUNDS TO ACCOMODATE THE DATA POINT LABELS +
//				// LEADER LINES RENDERED OUTSIDE
//				Bounds boAdjusted = BoundsImpl.copyInstance( bo );
//				Insets insTrim = InsetsImpl.create( 0, 0, 0, 0 );
//				do
//				{
//					adjust( bo, boAdjusted, insTrim );
//					boAdjusted.adjust( insTrim );
//				} while ( !insTrim.areLessThan( 0.5 )
//						&& boAdjusted.getWidth( ) > 0
//						&& boAdjusted.getHeight( ) > 0 );
//				bo = boAdjusted;
//			}
//		}
//		else if ( lpDataPoint == Position.INSIDE_LITERAL )
//		{
//			if ( laDataPoint.isVisible( ) ) // FILTERED FOR PERFORMANCE GAIN
//			{
//				computeLabelsInsidePie( bo );
//			}
//		}
//		else
//		{
//			throw new IllegalArgumentException( MessageFormat.format( Messages.getResourceBundle( pie.getRunTimeContext( )
//					.getULocale( ) )
//					.getString( "exception.invalid.datapoint.position.pie" ), //$NON-NLS-1$
//					new Object[]{
//						lpDataPoint
//					} )
//
//			);
//		}
//
//		insCA = InsetsImpl.create( bo.getTop( )
//				- boSetDuringComputation.getTop( ),
//				bo.getLeft( ) - boSetDuringComputation.getLeft( ),
//				boSetDuringComputation.getTop( )
//						+ boSetDuringComputation.getHeight( )
//						- ( bo.getTop( ) + bo.getHeight( ) ),
//				boSetDuringComputation.getLeft( )
//						+ boSetDuringComputation.getWidth( )
//						- ( bo.getLeft( ) + bo.getWidth( ) ) );
//		bBoundsAdjustedForInsets = false;
//	}
//
//	/**
//	 * 
//	 * @return
//	 */
//	final Insets getFittingInsets( )
//	{
//		return insCA;
//	}
//
//	/**
//	 * 
//	 * @param insCA
//	 */
//	final void setFittingInsets( Insets insCA ) throws IllegalArgumentException
//	{
//		this.insCA = insCA;
//		if ( !bBoundsAdjustedForInsets ) // CHECK IF PREVIOUSLY ADJUSTED
//		{
//			bBoundsAdjustedForInsets = true;
//			boSetDuringComputation.adjust( insCA );
//		}
//
//		if ( lpDataPoint == Position.OUTSIDE_LITERAL )
//		{
//			if ( laDataPoint.isVisible( ) ) // FILTERED FOR PERFORMANCE GAIN
//			{
//				computeLabelsOutsidePie( boSetDuringComputation );
//			}
//		}
//		else if ( lpDataPoint == Position.INSIDE_LITERAL )
//		{
//			if ( laDataPoint.isVisible( ) ) // FILTERED FOR PERFORMANCE GAIN
//			{
//				computeLabelsInsidePie( boSetDuringComputation );
//			}
//		}
//	}
//
//	/**
//	 * 
//	 * @param idr
//	 * @param bo
//	 * @throws RenderingException
//	 */
//	public final void render( IDeviceRenderer idr, Bounds bo )
//			throws ChartException
//	{
//		bo.adjust( insCA );
//		xs = idr.getDisplayServer( );
//		this.idr = idr;
//		if ( !laSeriesTitle.isSetVisible( ) )
//		{
//			throw new ChartException( ChartEngineExtensionPlugin.ID,
//					ChartException.RENDERING,
//					"exception.unspecified.pie.series.title.visibility",//$NON-NLS-1$
//					new Object[]{
//						ps
//					},
//					Messages.getResourceBundle( pie.getRunTimeContext( )
//							.getULocale( ) ) );
//		}
//
//		final ScriptHandler sh = pie.getRunTimeContext( ).getScriptHandler( );
//
//		double w = bo.getWidth( ) / 2d - dExplosion;
//		double h = bo.getHeight( ) / 2d - dExplosion - dThickness / 2d;
//
//		double xc = bo.getLeft( ) + bo.getWidth( ) / 2d;
//		double yc = bo.getTop( ) + bo.getHeight( ) / 2d;
//
//		if ( ratio > 0 && w > 0 )
//		{
//			if ( h / w > ratio )
//			{
//				h = w * ratio;
//			}
//			else if ( h / w < ratio )
//			{
//				w = h / ratio;
//			}
//		}
//
//		// detect invalid rendering size.
//		if ( w > 0 && h > 0 )
//		{
//			// RENDER THE INSIDE OF THE PIE SLICES AS DEFERRED PLANES (FLAT AND
//			// CURVED)
//			double dStart = dStartAngle;
//			int n = daAngleDelta.length;
//			Fill fPaletteEntry = null;
//			if ( !bPaletteByCategory )
//			{
//				fPaletteEntry = getPaletteColor( pie.getSeriesDefinition( )
//						.getRunTimeSeries( )
//						.indexOf( ps ) );
//			}
//
//			if ( dThickness > 0 )
//			{
//				for ( int i = 0; i < n; i++ )
//				{
//					if ( bPaletteByCategory )
//					{
//						if ( bMinSliceApplied && i == n - 1 )
//						{
//							fPaletteEntry = getPaletteColor( orginalSliceCount );
//						}
//						else
//						{
//							fPaletteEntry = getPaletteColor( categoryIndex[i] );
//						}
//					}
//					ScriptHandler.callFunction( sh,
//							ScriptHandler.BEFORE_DRAW_ELEMENT,
//							dpha[i],
//							fPaletteEntry );
//					ScriptHandler.callFunction( sh,
//							ScriptHandler.BEFORE_DRAW_DATA_POINT,
//							dpha[i],
//							fPaletteEntry,
//							pie.getRunTimeContext( ).getScriptContext( ) );
//					pie.getRunTimeContext( )
//							.notifyStructureChange( IStructureDefinitionListener.BEFORE_DRAW_ELEMENT,
//									dpha[i] );
//					pie.getRunTimeContext( )
//							.notifyStructureChange( IStructureDefinitionListener.BEFORE_DRAW_DATA_POINT,
//									dpha[i] );
//					drawSlice( LocationImpl.create( xc, yc ),
//							LocationImpl.create( 0, dThickness ),
//							SizeImpl.create( w, h ),
//							new Range( dStart, daAngleDelta[i] ),
//							fPaletteEntry,
//							dpha[i],
//							LOWER,
//							bExploded[i] );
//					ScriptHandler.callFunction( sh,
//							ScriptHandler.AFTER_DRAW_ELEMENT,
//							dpha[i],
//							fPaletteEntry );
//					ScriptHandler.callFunction( sh,
//							ScriptHandler.AFTER_DRAW_DATA_POINT,
//							dpha[i],
//							fPaletteEntry,
//							pie.getRunTimeContext( ).getScriptContext( ) );
//					pie.getRunTimeContext( )
//							.notifyStructureChange( IStructureDefinitionListener.AFTER_DRAW_ELEMENT,
//									dpha[i] );
//					pie.getRunTimeContext( )
//							.notifyStructureChange( IStructureDefinitionListener.AFTER_DRAW_DATA_POINT,
//									dpha[i] );
//					dStart += daAngleDelta[i];
//				}
//
//				// SORT AND ACTUALLY RENDER THE PLANES PREVIOUSLY WRITTEN AS
//				// DEFERRED
//				sortAndRenderPlanes( );
//			
//			
//				// RENDER THE UPPER SECTORS ON THE PIE SLICES (DON'T CARE ABOUT THE
//				// ORDER)
//				dStart = dStartAngle;
//				for ( int i = 0; i < n; i++ )
//				{
//					if ( bPaletteByCategory )
//					{
//						if ( bMinSliceApplied && i == n - 1 )
//						{
//							fPaletteEntry = getPaletteColor( orginalSliceCount );
//						}
//						else
//						{
//							fPaletteEntry = getPaletteColor( categoryIndex[i] );
//						}
//					}
//					ScriptHandler.callFunction( sh,
//							ScriptHandler.BEFORE_DRAW_DATA_POINT,
//							dpha[i],
//							fPaletteEntry,
//							pie.getRunTimeContext( ).getScriptContext( ) );
//					pie.getRunTimeContext( )
//							.notifyStructureChange( IStructureDefinitionListener.BEFORE_DRAW_DATA_POINT,
//									dpha[i] );
//					drawSlice( LocationImpl.create( xc, yc ),
//							LocationImpl.create( 0, dThickness ),
//							SizeImpl.create( w, h ),
//							new Range( dStart, daAngleDelta[i] ),
//							fPaletteEntry,
//							dpha[i],
//							UPPER,
//							bExploded[i] );
//					ScriptHandler.callFunction( sh,
//							ScriptHandler.AFTER_DRAW_DATA_POINT,
//							dpha[i],
//							fPaletteEntry,
//							pie.getRunTimeContext( ).getScriptContext( ) );
//					pie.getRunTimeContext( )
//							.notifyStructureChange( IStructureDefinitionListener.AFTER_DRAW_DATA_POINT,
//									dpha[i] );
//					dStart += daAngleDelta[i];
//				}
//
//			}
//
//		}
//
//		// RENDER THE SERIES TITLE NOW
//		ScriptHandler.callFunction( sh,
//				ScriptHandler.BEFORE_DRAW_SERIES_TITLE,
//				ps,
//				laSeriesTitle,
//				pie.getRunTimeContext( ).getScriptContext( ) );
//		pie.getRunTimeContext( )
//				.notifyStructureChange( IStructureDefinitionListener.BEFORE_DRAW_SERIES_TITLE,
//						laSeriesTitle );
//		laDataPoint = LabelImpl.copyInstance( ps.getLabel( ) );
//		if ( laSeriesTitle.isVisible( ) )
//		{
//			final TextRenderEvent tre = (TextRenderEvent) ( (EventObjectCache) idr ).getEventObject( WrappedStructureSource.createSeriesTitle( ps,
//					laSeriesTitle ),
//					TextRenderEvent.class );
//			tre.setLabel( laSeriesTitle );
//			tre.setBlockBounds( boTitleContainer );
//			tre.setBlockAlignment( null );
//			tre.setAction( TextRenderEvent.RENDER_TEXT_IN_BLOCK );
//			idr.drawText( tre );
//		}
//		ScriptHandler.callFunction( sh,
//				ScriptHandler.AFTER_DRAW_SERIES_TITLE,
//				ps,
//				laSeriesTitle,
//				pie.getRunTimeContext( ).getScriptContext( ) );
//		pie.getRunTimeContext( )
//				.notifyStructureChange( IStructureDefinitionListener.AFTER_DRAW_SERIES_TITLE,
//						laSeriesTitle );
//
//		// LASTLY, RENDER THE DATA POINTS
//		try
//		{
//			renderDataPoints( idr );
//		}
//		catch ( ChartException rex )
//		{
//			logger.log( rex );
//		}
//	}
//
//	/**
//	 * 
//	 * @param dAngle
//	 * @return
//	 */
//	private static final int getQuadrant( double dAngle )
//	{
//		if ( dAngle >= 0 && dAngle < 90 )
//			return 1;
//		if ( dAngle >= 90 && dAngle < 180 )
//			return 2;
//		if ( dAngle >= 180 && dAngle < 270 )
//			return 3;
//		else
//			return 4;
//	}
//
//	/**
//	 * 
//	 * @param areBentOrTwistedCurve
//	 * @param dX1
//	 * @param dX2
//	 */
//	private final void deferCurvedPlane( AreaRenderEvent areBentOrTwistedCurve,
//			double dX1, double dX2 )
//	{
//		alPlanes.add( new CurvedPlane( areBentOrTwistedCurve, dX1, dX2 ) );
//	}
//
//	/**
//	 * 
//	 * @param daXPoints
//	 * @param daYPoints
//	 * @param cd
//	 * @param dX1
//	 * @param dX2
//	 */
//	private final void deferFlatPlane( double[] daXPoints, double[] daYPoints,
//			Fill cd, double dX1, double dX2, DataPointHints dph )
//	{
//		alPlanes.add( new FlatPlane( daXPoints, daYPoints, cd, dX1, dX2, dph ) );
//	}
//
//	/**
//	 * 
//	 * @throws RenderingException
//	 */
//	private final void sortAndRenderPlanes( ) throws ChartException
//	{
//		int n = alPlanes.size( );
//		IDrawable id;
//		Collections.sort( alPlanes );
//		for ( int i = 0; i < n; i++ )
//		{
//			id = (IDrawable) alPlanes.get( i );
//			id.draw( );
//		}
//
//		alPlanes.clear( );
//	}
//
//	private final ColorDefinition getSliceOutline( Fill f )
//	{
//		if ( ps.getSliceOutline( ) == null )
//		{
//			if ( f instanceof ColorDefinition )
//			{
//				return ( (ColorDefinition) f ).darker( );
//			}
//			else
//			{
//				return ColorDefinitionImpl.TRANSPARENT( );
//			}
//		}
//		return ColorDefinitionImpl.copyInstance( ps.getSliceOutline( ) );
//	}
//
//	private void initExploded( )
//	{
//		bExploded = new boolean[dpha.length];
//
//		Arrays.fill( bExploded, true );
//
//		if ( sExplosionExpression == null
//				|| !pie.getRunTimeContext( ).isScriptingEnabled( ) )
//		{
//			return;
//		}
//
//		for ( int i = 0; i < dpha.length; i++ )
//		{
//			try
//			{
//				pie.getRunTimeContext( )
//						.getScriptHandler( )
//						.registerVariable( ScriptHandler.BASE_VALUE,
//								dpha[i].getBaseValue( ) );
//				pie.getRunTimeContext( )
//						.getScriptHandler( )
//						.registerVariable( ScriptHandler.ORTHOGONAL_VALUE,
//								dpha[i].getOrthogonalValue( ) );
//				pie.getRunTimeContext( )
//						.getScriptHandler( )
//						.registerVariable( ScriptHandler.SERIES_VALUE,
//								dpha[i].getSeriesValue( ) );
//
//				Object obj = pie.getRunTimeContext( )
//						.getScriptHandler( )
//						.evaluate( sExplosionExpression );
//
//				if ( obj instanceof Boolean )
//				{
//					bExploded[i] = ( (Boolean) obj ).booleanValue( );
//				}
//
//				pie.getRunTimeContext( )
//						.getScriptHandler( )
//						.unregisterVariable( ScriptHandler.BASE_VALUE );
//				pie.getRunTimeContext( )
//						.getScriptHandler( )
//						.unregisterVariable( ScriptHandler.ORTHOGONAL_VALUE );
//				pie.getRunTimeContext( )
//						.getScriptHandler( )
//						.unregisterVariable( ScriptHandler.SERIES_VALUE );
//
//			}
//			catch ( ChartException e )
//			{
//				logger.log( e );
//			}
//		}
//	}
//
//	/**
//	 * 
//	 * @param g2d
//	 * @param loC
//	 * @param loOffset
//	 * @param sz
//	 * @param r
//	 * @param fi
//	 * @param iPieceType
//	 * @param dExplosion
//	 */
//	private final void drawSlice( Location loC, Location loOffset, Size sz,
//			Range r, Fill fi, DataPointHints dph, int iPieceType,
//			boolean bExploded ) throws ChartException
//	{
//		loC.translate( loOffset.getX( ) / 2d, loOffset.getY( ) / 2d );
//
//		if ( bExploded && dExplosion != 0 )
//		{
//			// double dRatio = (double) d.width / d.height;
//			double dMidAngleInRadians = Math.toRadians( r.dStartAngle
//					+ r.dAngleExtent
//					/ 2d );
//			double dSineThetaMid = ( Math.sin( dMidAngleInRadians ) );
//			double dCosThetaMid = ( Math.cos( dMidAngleInRadians ) );
//			double xDelta = ( dExplosion * dCosThetaMid );
//			double yDelta = ( dExplosion * dSineThetaMid );
//
//			loC.translate( xDelta, -yDelta );
//		}
//
//		Location loCTop = LocationImpl.create( loC.getX( ) - loOffset.getX( ),
//				loC.getY( ) - loOffset.getY( ) );
//		double dAngleInRadians = Math.toRadians( r.dStartAngle );
//		double dSineThetaStart = Math.sin( dAngleInRadians );
//		double dCosThetaStart = Math.cos( dAngleInRadians );
//		dAngleInRadians = Math.toRadians( r.dAngleExtent + r.dStartAngle );
//		double dSineThetaEnd = Math.sin( dAngleInRadians );
//		double dCosThetaEnd = Math.cos( dAngleInRadians );
//
//		double xE = ( sz.getWidth( ) * dCosThetaEnd );
//		double yE = ( sz.getHeight( ) * dSineThetaEnd );
//		double xS = ( sz.getWidth( ) * dCosThetaStart );
//		double yS = ( sz.getHeight( ) * dSineThetaStart );
//
//		if ( iPieceType == LOWER )
//		{
//			ArcRenderEvent are = new ArcRenderEvent( WrappedStructureSource.createSeriesDataPoint( ps,
//					dph ) );
//			are.setBackground( fi );
//			liaEdges.setColor( getSliceOutline( fi ) );
//			are.setOutline( liaEdges );
//			are.setTopLeft( LocationImpl.create( loCTop.getX( ) - sz.getWidth( ),
//					loCTop.getY( ) - sz.getHeight( ) + dThickness ) );
//			are.setWidth( sz.getWidth( ) * 2 );
//			are.setHeight( sz.getHeight( ) * 2 );
//			are.setStartAngle( r.dStartAngle );
//			are.setAngleExtent( r.dAngleExtent );
//			are.setStyle( ArcRenderEvent.SECTOR );
//			idr.fillArc( are );
//
//			// DRAWN INTO A BUFFER FOR DEFERRED RENDERING
//			double[] daXPoints = {
//					loC.getX( ),
//					loCTop.getX( ),
//					loCTop.getX( ) + xE,
//					loC.getX( ) + xE
//			};
//			double[] daYPoints = {
//					loC.getY( ),
//					loCTop.getY( ),
//					loCTop.getY( ) - yE,
//					loC.getY( ) - yE
//			};
//			deferFlatPlane( daXPoints, daYPoints, fi, loC.getX( ), loC.getX( )
//					+ xE, dph );
//
//			daXPoints = new double[]{
//					loC.getX( ),
//					loC.getX( ) + xS,
//					loCTop.getX( ) + xS,
//					loCTop.getX( )
//			};
//			daYPoints = new double[]{
//					loC.getY( ),
//					loC.getY( ) - yS,
//					loCTop.getY( ) - yS,
//					loCTop.getY( )
//			};
//			deferFlatPlane( daXPoints, daYPoints, fi, loC.getX( ), loC.getX( )
//					+ xS, dph );
//
//			daXPoints = new double[]{
//					loC.getX( ) + xS,
//					loCTop.getX( ) + xS,
//					loCTop.getX( ) + xE,
//					loC.getX( ) + xE
//			};
//			daYPoints = new double[]{
//					loC.getY( ) - yS,
//					loCTop.getY( ) - yS,
//					loCTop.getY( ) - yE,
//					loC.getY( ) - yE
//			};
//
//			final LineRenderEvent lre1 = new LineRenderEvent( WrappedStructureSource.createSeriesDataPoint( ps,
//					dph ) );
//			lre1.setStart( LocationImpl.create( loC.getX( ) + xS, loC.getY( )
//					- yS ) );
//			lre1.setEnd( LocationImpl.create( loCTop.getX( ) + xS,
//					loCTop.getY( ) - yS ) );
//			final LineRenderEvent lre2 = new LineRenderEvent( WrappedStructureSource.createSeriesDataPoint( ps,
//					dph ) );
//			lre2.setStart( LocationImpl.create( loCTop.getX( ) + xE,
//					loCTop.getY( ) - yE ) );
//			lre2.setEnd( LocationImpl.create( loC.getX( ) + xE, loC.getY( )
//					- yE ) );
//			Bounds r2dd1 = BoundsImpl.create( loCTop.getX( ) - sz.getWidth( ),
//					loCTop.getY( ) - sz.getHeight( ),
//					sz.getWidth( ) * 2,
//					sz.getHeight( ) * 2 );
//			Bounds r2dd2 = BoundsImpl.create( loC.getX( ) - sz.getWidth( ),
//					loC.getY( ) - sz.getHeight( ),
//					sz.getWidth( ) * 2,
//					sz.getHeight( ) * 2 );
//
//			registerCurvedSurface( r2dd1,
//					r2dd2,
//					r.dStartAngle,
//					r.dAngleExtent,
//					lre1,
//					lre2,
//					fi,
//					dph );
//		}
//
//		else if ( iPieceType == UPPER ) // DRAWN IMMEDIATELY
//		{
//			final ArcRenderEvent are = (ArcRenderEvent) ( (EventObjectCache) idr ).getEventObject( WrappedStructureSource.createSeriesDataPoint( ps,
//					dph ),
//					ArcRenderEvent.class );
//			are.setBackground( fi );
//			liaEdges.setColor( getSliceOutline( fi ) );
//			are.setOutline( liaEdges );
//			are.setTopLeft( LocationImpl.create( loCTop.getX( ) - sz.getWidth( ),
//					loCTop.getY( ) - sz.getHeight( ) ) );
//			are.setWidth( sz.getWidth( ) * 2 );
//			are.setHeight( sz.getHeight( ) * 2 );
//			are.setStartAngle( r.dStartAngle );
//			are.setAngleExtent( r.dAngleExtent );
//			are.setStyle( ArcRenderEvent.SECTOR );
//			idr.fillArc( are );
//			idr.drawArc( are );
//
//			if ( pie.isInteractivityEnabled( ) )
//			{
//				final EList elTriggers = ps.getTriggers( );
//				if ( !elTriggers.isEmpty( ) )
//				{
//					final StructureSource iSource = WrappedStructureSource.createSeriesDataPoint( ps,
//							dph );
//					final InteractionEvent iev = (InteractionEvent) ( (EventObjectCache) idr ).getEventObject( iSource,
//							InteractionEvent.class );
//					Trigger tg;
//					for ( int t = 0; t < elTriggers.size( ); t++ )
//					{
//						tg = TriggerImpl.copyInstance( (Trigger) elTriggers.get( t ) );
//						pie.processTrigger( tg, iSource );
//						iev.addTrigger( tg );
//					}
//					iev.setHotSpot( are );
//					idr.enableInteraction( iev );
//				}
//			}
//		}
//	}
//
//	// HANDLE ELEVATION COMPUTATION FOR SOLID
//	// COLORS,
//	// GRADIENTS AND IMAGES
//
//	protected Gradient getDepthGradient( Fill cd )
//	{
//		if ( cd instanceof Gradient )
//		{
//			return GradientImpl.create( ( (Gradient) cd ).getStartColor( )
//					.darker( ),
//					( (Gradient) cd ).getEndColor( ).darker( ),
//					( (Gradient) cd ).getDirection( ),
//					( (Gradient) cd ).isCyclic( ) );
//
//		}
//		else
//			return GradientImpl.create( ( cd instanceof ColorDefinition ) ? ( (ColorDefinition) cd ).darker( )
//					: ColorDefinitionImpl.GREY( ),
//					ColorDefinitionImpl.BLACK( ),
//					0,
//					true );
//	}
//
//	/**
//	 * Used for deferred rendering
//	 * 
//	 * @param topBound
//	 * @param bottomBound
//	 * @param dStartAngle
//	 * @param dEndAngle
//	 * @param lre1
//	 * @param lre2
//	 * @param cd
//	 */
//	private final void registerCurvedSurface( Bounds topBound,
//			Bounds bottomBound, double dStartAngle, double dAngleExtent,
//			LineRenderEvent lre1, LineRenderEvent lre2, Fill cd,
//			DataPointHints dph )
//	{
//		if ( dAngleExtent + dStartAngle > 180 && dStartAngle < 180 )
//		{
//			// THE NEW SPLIT LINE AT 180 DEGREES
//			final LineRenderEvent lre3 = new LineRenderEvent( WrappedStructureSource.createSeriesDataPoint( ps,
//					dph ) );
//			lre3.setEnd( LocationImpl.create( bottomBound.getLeft( ),
//					bottomBound.getTop( ) + bottomBound.getHeight( ) / 2 ) );
//			lre3.setStart( LocationImpl.create( topBound.getLeft( ),
//					topBound.getTop( ) + topBound.getHeight( ) / 2 ) );
//
//			// CURVED PLANE 1
//			ArcRenderEvent are1 = new ArcRenderEvent( WrappedStructureSource.createSeriesDataPoint( ps,
//					dph ) );
//			ArcRenderEvent are2 = new ArcRenderEvent( WrappedStructureSource.createSeriesDataPoint( ps,
//					dph ) );
//			are1.setBounds( topBound );
//			are1.setStartAngle( dStartAngle );
//			are1.setAngleExtent( 180 - dStartAngle );
//			are1.setStyle( ArcRenderEvent.OPEN );
//			are2.setBounds( bottomBound );
//			are2.setStartAngle( 180 );
//			are2.setAngleExtent( dStartAngle - 180 );
//			are2.setStyle( ArcRenderEvent.OPEN );
//			AreaRenderEvent are = new AreaRenderEvent( WrappedStructureSource.createSeriesDataPoint( ps,
//					dph ) );
//			are.add( lre1 );
//			are.add( are1 );
//			are.add( lre3 );
//			are.add( are2 );
//			are.setOutline( LineAttributesImpl.create( getSliceOutline( cd ),
//					LineStyle.SOLID_LITERAL,
//					1 ) );
//
//			are.setBackground( getDepthGradient( cd ) );
//			deferCurvedPlane( are, lre1.getStart( ).getX( ), lre3.getStart( )
//					.getX( ) );
//
//			// CURVED PLANE 2
//			are1 = new ArcRenderEvent( WrappedStructureSource.createSeriesDataPoint( ps,
//					dph ) );
//			are2 = new ArcRenderEvent( WrappedStructureSource.createSeriesDataPoint( ps,
//					dph ) );
//			are1.setBounds( topBound );
//			are1.setStartAngle( 180 );
//			are1.setAngleExtent( dStartAngle + dAngleExtent - 180 );
//			are1.setStyle( ArcRenderEvent.OPEN );
//			are2.setBounds( bottomBound );
//			are2.setStartAngle( dStartAngle + dAngleExtent );
//			are2.setAngleExtent( 180 - ( dStartAngle + dAngleExtent ) );
//			are2.setStyle( ArcRenderEvent.OPEN );
//
//			// swap lre3
//			final LineRenderEvent lre4 = new LineRenderEvent( WrappedStructureSource.createSeriesDataPoint( ps,
//					dph ) );
//			lre4.setStart( LocationImpl.copyInstance( lre3.getEnd( ) ) );
//			lre4.setEnd( LocationImpl.copyInstance( lre3.getStart( ) ) );
//
//			are = new AreaRenderEvent( WrappedStructureSource.createSeriesDataPoint( ps,
//					dph ) );
//			are.add( lre4 );
//			are.add( are1 );
//			are.add( lre2 );
//			are.add( are2 );
//			are.setOutline( LineAttributesImpl.create( getSliceOutline( cd ),
//					LineStyle.SOLID_LITERAL,
//					1 ) );
//			are.setBackground( getDepthGradient( cd ) );
//
//			deferCurvedPlane( are, lre4.getStart( ).getX( ), lre2.getStart( )
//					.getX( ) );
//		}
//		else
//		{
//			// CURVED PLANE 1
//			final ArcRenderEvent are1 = new ArcRenderEvent( WrappedStructureSource.createSeriesDataPoint( ps,
//					dph ) );
//			final ArcRenderEvent are2 = new ArcRenderEvent( WrappedStructureSource.createSeriesDataPoint( ps,
//					dph ) );
//			are1.setBounds( topBound );
//			are1.setStartAngle( dStartAngle );
//			are1.setAngleExtent( dAngleExtent );
//			are1.setStyle( ArcRenderEvent.OPEN );
//			are2.setBounds( bottomBound );
//			are2.setStartAngle( dStartAngle + dAngleExtent );
//			are2.setAngleExtent( -dAngleExtent );
//			are2.setStyle( ArcRenderEvent.OPEN );
//			AreaRenderEvent are = new AreaRenderEvent( WrappedStructureSource.createSeriesDataPoint( ps,
//					dph ) );
//			are.add( lre1 );
//			are.add( are1 );
//			are.add( lre2 );
//			are.add( are2 );
//
//			are.setOutline( LineAttributesImpl.create( getSliceOutline( cd ),
//					LineStyle.SOLID_LITERAL,
//					1 ) );
//			are.setBackground( getDepthGradient( cd ) );
//			deferCurvedPlane( are, lre1.getStart( ).getX( ), lre2.getStart( )
//					.getX( ) );
//		}
//	}
//
//	/**
//	 * 
//	 * @param iIndex
//	 * @return
//	 */
//	private final Fill getPaletteColor( int iIndex )
//	{
//		final Fill fiClone = (Fill) EcoreUtil.copy( (Fill) pa.getEntries( )
//				.get( iIndex % pa.getEntries( ).size( ) ) );
//
//		pie.updateTranslucency( fiClone, ps );
//		return fiClone;
//	}
//
//	/**
//	 * Range
//	 */
//	private static final class Range
//	{
//
//		double dStartAngle;
//
//		double dAngleExtent;
//
//		Range( double _dStartAngle, double _dEndAngle )
//		{
//			dStartAngle = _dStartAngle;
//			dAngleExtent = _dEndAngle;
//		}
//	}
//
//	/**
//	 * CurvedPlane
//	 */
//	private final class CurvedPlane implements Comparable, IDrawable
//	{
//
//		private final AreaRenderEvent _are;
//
//		private final Bounds _bo;
//
//		CurvedPlane( AreaRenderEvent are, double dX1, double dX2 )
//		{
//			_are = are;
//			_bo = are.getBounds( );
//		}
//
//		public final Bounds getBounds( )
//		{
//			return _bo;
//		}
//
//		/*
//		 * (non-Javadoc)
//		 * 
//		 * @see java.lang.Comparable#compareTo(java.lang.Object)
//		 */
//		public final int compareTo( Object o ) // Z-ORDER TEST
//		{
//			final CurvedPlane cp1 = this;
//			if ( o instanceof CurvedPlane )
//			{
//				final CurvedPlane cp2 = (CurvedPlane) o;
//				final double dMinY1 = cp1.getMinY( );
//				final double dMinY2 = cp2.getMinY( );
//				double dDiff = dMinY1 - dMinY2;
//				if ( !ChartUtil.mathEqual( dDiff, 0 ) )
//				{
//					return ( dDiff < 0 ) ? LESS : ( dDiff > 0 ) ? MORE : EQUAL;
//				}
//				else
//				{
//					final double dMaxY1 = cp1.getMaxY( );
//					final double dMaxY2 = cp2.getMaxY( );
//					dDiff = dMaxY1 - dMaxY2;
//					if ( !ChartUtil.mathEqual( dDiff, 0 ) )
//					{
//						return ( dDiff < 0 ) ? LESS : MORE;
//					}
//					else
//					{
//						final double dMinX1 = cp1.getMinX( );
//						final double dMinX2 = cp2.getMinX( );
//						dDiff = dMinX1 - dMinX2;
//						if ( !ChartUtil.mathEqual( dDiff, 0 ) )
//						{
//							return ( dDiff < 0 ) ? LESS : MORE;
//						}
//						else
//						{
//							final double dMaxX1 = cp1.getMaxX( );
//							final double dMaxX2 = cp2.getMaxX( );
//							dDiff = dMaxX1 - dMaxX2;
//							if ( !ChartUtil.mathEqual( dDiff, 0 ) )
//							{
//								return ( dDiff < 0 ) ? LESS : MORE;
//							}
//							else
//							{
//								return EQUAL;
//							}
//						}
//					}
//				}
//			}
//			else if ( o instanceof FlatPlane )
//			{
//				final FlatPlane pi2 = (FlatPlane) o;
//				return pi2.compareTo( cp1 ) * -1; // DELEGATE AND INVERT
//				// RESULT
//			}
//			return EQUAL;
//		}
//
//		/*
//		 * (non-Javadoc)
//		 * 
//		 * @see org.eclipse.birt.chart.prototype.Pie.IDrawable#draw(java.awt.Graphics2D)
//		 */
//		public final void draw( ) throws ChartException
//		{
//			idr.fillArea( _are );
//			idr.drawArea( _are );
//			/*
//			 * GradientPaint gp = new GradientPaint( (float)dGradientStart2D, 0,
//			 * Color.black, (float)(dGradientStart2D + (dGradientEnd2D -
//			 * dGradientStart2D)/2), 0, _c, true); g2d.setPaint(gp);
//			 * g2d.fill(_sh); g2d.setColor(_c.darker()); g2d.draw(_sh);
//			 */
//		}
//
//		private final double getMinY( )
//		{
//			return _bo.getTop( );
//		}
//
//		private final double getMinX( )
//		{
//			return _bo.getLeft( );
//		}
//
//		private final double getMaxX( )
//		{
//			return _bo.getLeft( ) + _bo.getWidth( );
//		}
//
//		private final double getMaxY( )
//		{
//			return _bo.getTop( ) + _bo.getHeight( );
//		}
//	}
//
//	/**
//	 * FlatPlane
//	 */
//	private final class FlatPlane implements Comparable, IDrawable
//	{
//
//		private final double[] _daXPoints, _daYPoints;
//
//		private Fill _cd;
//
//		private final Bounds _bo;
//
//		private DataPointHints _dph;
//
//		FlatPlane( double[] daXPoints, double[] daYPoints, Fill cd, double dX1,
//				double dX2, DataPointHints dph )
//		{
//			_daXPoints = daXPoints;
//			_daYPoints = daYPoints;
//			_dph = dph;
//
//			// COMPUTE THE BOUNDS
//			final int n = _daXPoints.length;
//			double dMinX = 0, dMinY = 0, dMaxX = 0, dMaxY = 0;
//
//			for ( int i = 0; i < n; i++ )
//			{
//				if ( i == 0 )
//				{
//					dMinX = _daXPoints[i];
//					dMinY = _daYPoints[i];
//					dMaxX = dMinX;
//					dMaxY = dMinY;
//				}
//				else
//				{
//					if ( dMinX > _daXPoints[i] )
//					{
//						dMinX = _daXPoints[i];
//					}
//					if ( dMinY > _daYPoints[i] )
//					{
//						dMinY = _daYPoints[i];
//					}
//					if ( dMaxX < _daXPoints[i] )
//					{
//						dMaxX = _daXPoints[i];
//					}
//					if ( dMaxY < _daYPoints[i] )
//					{
//						dMaxY = _daYPoints[i];
//					}
//				}
//			}
//			_bo = BoundsImpl.create( dMinX, dMinY, dMaxX - dMinX, dMaxY - dMinY );
//
//			_cd = cd;
//
//			int nPoints = _daXPoints.length;
//			int[] iaX = new int[nPoints];
//			int[] iaY = new int[nPoints];
//			for ( int i = 0; i < nPoints; i++ )
//			{
//				iaX[i] = (int) daXPoints[i];
//				iaY[i] = (int) daYPoints[i];
//			}
//			// _p = new Polygon(iaX, iaY, nPoints);
//		}
//
//		public Bounds getBounds( )
//		{
//			return _bo;
//		}
//
//		/*
//		 * (non-Javadoc)
//		 * 
//		 * @see org.eclipse.birt.chart.prototype.Pie.IDrawable#draw(java.awt.Graphics2D)
//		 */
//		public final void draw( ) throws ChartException
//		{
//			PolygonRenderEvent pre = (PolygonRenderEvent) ( (EventObjectCache) idr ).getEventObject( WrappedStructureSource.createSeriesDataPoint( ps,
//					_dph ),
//					PolygonRenderEvent.class );
//			pre.setPoints( toLocationArray( ) );
//			liaEdges.setColor( getSliceOutline( _cd ) );
//			pre.setOutline( liaEdges );
//
//			pre.setBackground( getDepthGradient( _cd ) );
//			idr.fillPolygon( pre );
//			idr.drawPolygon( pre );
//		}
//
//		/*
//		 * (non-Javadoc)
//		 * 
//		 * @see java.lang.Comparable#compareTo(java.lang.Object)
//		 */
//		public final int compareTo( Object o ) // Z-ORDER TEST
//		{
//			final FlatPlane pi1 = this;
//			if ( o instanceof FlatPlane )
//			{
//				final FlatPlane pi2 = (FlatPlane) o;
//
//				final double dMinY1 = pi1.getMinY( );
//				final double dMinY2 = pi2.getMinY( );
//				double dDiff = dMinY1 - dMinY2;
//				if ( !ChartUtil.mathEqual( dDiff, 0 ) )
//				{
//					return ( dDiff < 0 ) ? LESS : ( dDiff > 0 ) ? MORE : EQUAL;
//				}
//				else
//				{
//					final double dMaxY1 = pi1.getMaxY( );
//					final double dMaxY2 = pi2.getMaxY( );
//					dDiff = dMaxY1 - dMaxY2;
//					if ( !ChartUtil.mathEqual( dDiff, 0 ) )
//					{
//						return ( dDiff < 0 ) ? LESS : MORE;
//					}
//					else
//					{
//						final double dMinX1 = pi1.getMinX( );
//						final double dMinX2 = pi2.getMinX( );
//						dDiff = dMinX1 - dMinX2;
//						if ( !ChartUtil.mathEqual( dDiff, 0 ) )
//						{
//							return ( dDiff < 0 ) ? LESS : MORE;
//						}
//						else
//						{
//							final double dMaxX1 = pi1.getMaxX( );
//							final double dMaxX2 = pi2.getMaxX( );
//							dDiff = dMaxX1 - dMaxX2;
//							if ( !ChartUtil.mathEqual( dDiff, 0 ) )
//							{
//								return ( dDiff < 0 ) ? LESS : MORE;
//							}
//							else
//							{
//								return EQUAL;
//							}
//						}
//					}
//				}
//			}
//			else if ( o instanceof CurvedPlane )
//			{
//				final CurvedPlane pi2 = (CurvedPlane) o;
//
//				final double dMinY1 = pi1.getMinY( );
//				final double dMinY2 = pi2.getMinY( );
//				double dDiff = dMinY1 - dMinY2;
//				if ( !ChartUtil.mathEqual( dDiff, 0 ) )
//				{
//					return ( dDiff < 0 ) ? LESS : MORE;
//				}
//				else
//				{
//					final double dMaxY1 = pi1.getMaxY( );
//					final double dMaxY2 = pi2.getMaxY( );
//					dDiff = dMaxY1 - dMaxY2;
//					if ( !ChartUtil.mathEqual( dDiff, 0 ) )
//					{
//						return ( dDiff < 0 ) ? LESS : MORE;
//					}
//					else
//					{
//						final double dMinX1 = pi1.getMinX( );
//						final double dMinX2 = pi2.getMinX( );
//						dDiff = dMinX1 - dMinX2;
//						if ( !ChartUtil.mathEqual( dDiff, 0 ) )
//						{
//							return ( dDiff < 0 ) ? LESS : MORE;
//						}
//						else
//						{
//							final double dMaxX1 = pi1.getMaxX( );
//							final double dMaxX2 = pi2.getMaxX( );
//							dDiff = dMaxX1 - dMaxX2;
//							if ( !ChartUtil.mathEqual( dDiff, 0 ) )
//							{
//								return ( dDiff < 0 ) ? LESS : MORE;
//							}
//							else
//							{
//								return EQUAL;
//							}
//						}
//					}
//				}
//
//			}
//			return EQUAL;
//		}
//
//		private final double getMinY( )
//		{
//			return _bo.getTop( );
//		}
//
//		private final double getMinX( )
//		{
//			return _bo.getLeft( );
//		}
//
//		private final double getMaxX( )
//		{
//			return _bo.getLeft( ) + _bo.getWidth( );
//		}
//
//		private final double getMaxY( )
//		{
//			return _bo.getTop( ) + _bo.getHeight( );
//		}
//
//		private final Location[] toLocationArray( )
//		{
//			final int n = _daXPoints.length;
//			Location[] loa = new Location[n];
//			for ( int i = 0; i < n; i++ )
//			{
//				loa[i] = LocationImpl.create( _daXPoints[i], _daYPoints[i] );
//			}
//			return loa;
//		}
//	}
//
//	/**
//	 * IDrawable
//	 */
//	private interface IDrawable
//	{
//
//		void draw( ) throws ChartException;
//
//		Bounds getBounds( );
//	}
//
//	/**
//	 * LeaderLine2D
//	 */
//	private final class LeaderLine2D
//	{
//
//		private final Location loPie;
//
//		private final Location loStart;
//
//		private final Location loEnd;
//
//		private BoundingBox bbDataPoint = null;
//
//		private String sValue = null;
//
//		private int iQuadrant = -1;
//
//		/**
//		 * 
//		 * @param dX1
//		 * @param dY1
//		 * @param dX2
//		 * @param dY2
//		 */
//		LeaderLine2D( double dX0, double dY0, double dX1, double dY1,
//				double dX2, double dY2 )
//		{
//			loPie = LocationImpl.create( dX0, dY0 );
//			loStart = LocationImpl.create( dX1, dY1 );
//			loEnd = LocationImpl.create( dX2, dY2 );
//		}
//
//		/**
//		 * 
//		 * @param loStart
//		 * @param loEnd
//		 */
//		LeaderLine2D( Location loPie, Location loStart, Location loEnd )
//		{
//			this.loPie = loPie;
//			this.loStart = loStart;
//			this.loEnd = loEnd;
//		}
//
//		/**
//		 * 
//		 * @param bb
//		 */
//		final void setBounds( BoundingBox bb )
//		{
//			bbDataPoint = bb;
//		}
//
//		/**
//		 * 
//		 * @return
//		 */
//		final BoundingBox getBounds( )
//		{
//			return bbDataPoint;
//		}
//
//		/**
//		 * 
//		 * @param iQuadrant
//		 */
//		final void setQuadrant( int iQuadrant )
//		{
//			this.iQuadrant = iQuadrant;
//		}
//
//		/**
//		 * 
//		 * @param sValue
//		 */
//		final void setValue( String sValue )
//		{
//			this.sValue = sValue;
//		}
//
//		final String getValue( )
//		{
//			return sValue;
//		}
//
//		/**
//		 * 
//		 * @param g2d
//		 */
//		private final void render( IDeviceRenderer idr, int iTextRenderType,
//				DataPointHints dph ) throws ChartException
//		{
//			if ( iQuadrant != -1 )
//			{
//				if ( iTextRenderType == TextRenderEvent.RENDER_TEXT_AT_LOCATION )
//				{
//					LineRenderEvent lre = (LineRenderEvent) ( (EventObjectCache) idr ).getEventObject( WrappedStructureSource.createSeriesDataPoint( ps,
//							dph ),
//							LineRenderEvent.class );
//					lre.setLineAttributes( liaLL );
//					lre.setStart( loPie );
//					lre.setEnd( loStart );
//					idr.drawLine( lre );
//
//					lre = (LineRenderEvent) ( (EventObjectCache) idr ).getEventObject( WrappedStructureSource.createSeriesDataPoint( ps,
//							dph ),
//							LineRenderEvent.class );
//					lre.setLineAttributes( liaLL );
//					lre.setStart( loStart );
//					lre.setEnd( loEnd );
//					idr.drawLine( lre );
//				}
//
//				laDataPoint.getCaption( ).setValue( sValue );
//				pie.renderLabel( WrappedStructureSource.createSeriesDataPoint( ps,
//						dph ),
//						TextRenderEvent.RENDER_TEXT_IN_BLOCK,
//						laDataPoint,
//						( iQuadrant == 1 || iQuadrant == 4 ) ? Position.RIGHT_LITERAL
//								: Position.LEFT_LITERAL,
//						loEnd,
//						BoundsImpl.create( bbDataPoint.getLeft( ),
//								bbDataPoint.getTop( ),
//								bbDataPoint.getWidth( ),
//								bbDataPoint.getHeight( ) ) );
//			}
//			else
//			{
//				laDataPoint.getCaption( ).setValue( sValue );
//				pie.renderLabel( StructureSource.createSeries( ps ),
//						TextRenderEvent.RENDER_TEXT_IN_BLOCK,
//						laDataPoint,
//						null,
//						null,
//						BoundsImpl.create( bbDataPoint.getLeft( ),
//								bbDataPoint.getTop( ),
//								bbDataPoint.getWidth( ),
//								bbDataPoint.getHeight( ) ) );
//			}
//		}
//	}
//}
// 
//
//  

