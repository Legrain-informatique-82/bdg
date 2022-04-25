package fr.legrain.libLgrBirt.chart.test.data.test;
//package fr.legrain.tiers.statistiques.data.test;
//
//
//
///***********************************************************************
// * Copyright (c) 2004 Actuate Corporation.
// * All rights reserved. This program and the accompanying materials
// * are made available under the terms of the Eclipse Public License v1.0
// * which accompanies this distribution, and is available at
// * http://www.eclipse.org/legal/epl-v10.html
// *
// * Contributors:
// * Actuate Corporation - initial API and implementation
// ***********************************************************************/
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.eclipse.birt.chart.computation.DataPointHints;
//import org.eclipse.birt.chart.computation.withaxes.AxisSubUnit;
//import org.eclipse.birt.chart.computation.withaxes.SeriesRenderingHints;
//import org.eclipse.birt.chart.computation.withaxes.SeriesRenderingHints3D;
//import org.eclipse.birt.chart.computation.withaxes.StackGroup;
//import org.eclipse.birt.chart.computation.withaxes.StackedSeriesLookup;
//import org.eclipse.birt.chart.device.IPrimitiveRenderer;
//import org.eclipse.birt.chart.device.IStructureDefinitionListener;
//import org.eclipse.birt.chart.engine.extension.i18n.Messages;
//import org.eclipse.birt.chart.event.EventObjectCache;
//import org.eclipse.birt.chart.event.InteractionEvent;
//import org.eclipse.birt.chart.event.Polygon3DRenderEvent;
//import org.eclipse.birt.chart.event.PolygonRenderEvent;
//import org.eclipse.birt.chart.event.StructureSource;
//import org.eclipse.birt.chart.event.Text3DRenderEvent;
//import org.eclipse.birt.chart.event.TextRenderEvent;
//import org.eclipse.birt.chart.event.WrappedStructureSource;
//import org.eclipse.birt.chart.exception.ChartException;
//import org.eclipse.birt.chart.log.ILogger;
//import org.eclipse.birt.chart.log.Logger;
//import org.eclipse.birt.chart.model.ChartWithAxes;
//import org.eclipse.birt.chart.model.attribute.Bounds;
//import org.eclipse.birt.chart.model.attribute.ChartDimension;
//import org.eclipse.birt.chart.model.attribute.ColorDefinition;
//import org.eclipse.birt.chart.model.attribute.Fill;
//import org.eclipse.birt.chart.model.attribute.LegendItemType;
//import org.eclipse.birt.chart.model.attribute.LineAttributes;
//import org.eclipse.birt.chart.model.attribute.LineStyle;
//import org.eclipse.birt.chart.model.attribute.Location;
//import org.eclipse.birt.chart.model.attribute.Location3D;
//import org.eclipse.birt.chart.model.attribute.Position;
//import org.eclipse.birt.chart.model.attribute.RiserType;
//import org.eclipse.birt.chart.model.attribute.impl.BoundsImpl;
//import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
//import org.eclipse.birt.chart.model.attribute.impl.LineAttributesImpl;
//import org.eclipse.birt.chart.model.attribute.impl.Location3DImpl;
//import org.eclipse.birt.chart.model.attribute.impl.LocationImpl;
//import org.eclipse.birt.chart.model.component.Axis;
//import org.eclipse.birt.chart.model.component.Label;
//import org.eclipse.birt.chart.model.data.SeriesDefinition;
//import org.eclipse.birt.chart.model.data.Trigger;
//import org.eclipse.birt.chart.model.data.impl.TriggerImpl;
//import org.eclipse.birt.chart.model.layout.Legend;
//import org.eclipse.birt.chart.model.layout.Plot;
//import org.eclipse.birt.chart.model.type.BarSeries;
//import org.eclipse.birt.chart.plugin.ChartEngineExtensionPlugin;
//import org.eclipse.birt.chart.render.AxesRenderer;
//import org.eclipse.birt.chart.render.ISeriesRenderingHints;
//import org.eclipse.birt.chart.script.AbstractScriptHandler;
//import org.eclipse.birt.chart.script.IScriptContext;
//import org.eclipse.birt.chart.script.ScriptHandler;
//import org.eclipse.birt.chart.util.ChartUtil;
//import org.eclipse.emf.common.util.EList;
//import org.eclipse.emf.ecore.util.EcoreUtil;
//
///**
// * Responsible for rendering the graphic elements associated with a bar graph.
// * It is also responsible for rendering the graphic element in the legend for
// * the associated series entry.
// */
//public final class Bar extends AxesRenderer
//{
//
//	private static ILogger logger = Logger.getLogger( "org.eclipse.birt.chart.engine.extension/render" ); //$NON-NLS-1$
//
//	/**
//	 * The default zero-arg constructor (required for initialization via the
//	 * extension framework)
//	 */
//	public Bar( )
//	{
//		super( );
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.birt.chart.render.AxesRenderer#renderSeries(org.eclipse.birt.chart.output.IRenderer,
//	 *      Chart.Plot, org.eclipse.birt.chart.render.axes.SeriesRenderingHints)
//	 */
//	public final void renderSeries( IPrimitiveRenderer ipr, Plot p,
//			ISeriesRenderingHints isrh ) throws ChartException
//	{
//		// VALIDATE CONSISTENT DATASET COUNT BETWEEN BASE AND ORTHOGONAL
//		try
//		{
//			validateDataSetCount( isrh );
//		}
//		catch ( ChartException vex )
//		{
//			throw new ChartException( ChartEngineExtensionPlugin.ID,
//					ChartException.RENDERING,
//					vex );
//		}
//
//		boolean bRendering3D = isDimension3D( );
//
//		// SCALE VALIDATION
//		SeriesRenderingHints srh = null;
//		SeriesRenderingHints3D srh3d = null;
//
//		if ( bRendering3D )
//		{
//			srh3d = (SeriesRenderingHints3D) isrh;
//		}
//		else
//		{
//			srh = (SeriesRenderingHints) isrh;
//		}
//
//		// CANNOT PLOT BARS ON A VALUE X-SCALE or Z-SCALE
//		if ( ( !bRendering3D && !srh.isCategoryScale( ) )
//				|| ( bRendering3D && !srh3d.isXCategoryScale( ) ) )
//		{
//			throw new ChartException( ChartEngineExtensionPlugin.ID,
//					ChartException.RENDERING,
//					"exception.xvalue.scale.bars", //$NON-NLS-1$
//					Messages.getResourceBundle( getRunTimeContext( ).getULocale( ) ) );
//		}
//
//		// OBTAIN AN INSTANCE OF THE CHART (TO RETRIEVE GENERAL CHART PROPERTIES
//		// IF ANY)
//		final ChartWithAxes cwa = (ChartWithAxes) getModel( );
//		final Bounds boClientArea = isrh.getClientAreaBounds( true );
//
//		final IScriptContext sh = getRunTimeContext( ).getScriptContext( );
//		logger.log( ILogger.INFORMATION,
//				Messages.getString( "info.render.series", //$NON-NLS-1$
//						new Object[]{
//								getClass( ).getName( ),
//								new Integer( iSeriesIndex + 1 ),
//								new Integer( iSeriesCount )
//						},
//						getRunTimeContext( ).getULocale( ) ) );
//
//		// OBTAIN AN INSTANCE OF THE SERIES MODEL (AND SOME VALIDATION)
//		final BarSeries bs = (BarSeries) getSeries( );
//		if ( !bs.isSetRiser( ) )
//		{
//			throw new ChartException( ChartEngineExtensionPlugin.ID,
//					ChartException.RENDERING,
//					"exception.undefined.riser", //$NON-NLS-1$
//					new Object[]{
//						bs
//					},
//					Messages.getResourceBundle( getRunTimeContext( ).getULocale( ) ) );
//		}
//		if ( !bs.isSetVisible( ) )
//		{
//			throw new ChartException( ChartEngineExtensionPlugin.ID,
//					ChartException.RENDERING,
//					"exception.series.visibility", //$NON-NLS-1$
//					new Object[]{
//						bs
//					},
//					Messages.getResourceBundle( getRunTimeContext( ).getULocale( ) ) );
//		}
//		if ( !bs.isVisible( ) )
//		{
//			return;
//		}
//
//		// SETUP VARS USED IN RENDERING
//		final RiserType rt = bs.getRiser( );
//		final double dSeriesThickness = bRendering3D ? 0
//				: srh.getSeriesThickness( );
//		// always be Zero for 3D rendering.
//		final double dZeroLocation = bRendering3D ? srh3d.getPlotZeroLocation( )
//				: srh.getZeroLocation( );
//		// NOT TO BE
//		// USED
//		// FOR STACKED
//		// CHARTS
//		double dBaseLocation = -1;
//		final DataPointHints[] dpha = isrh.getDataPoints( );
//		validateNullDatapoint( dpha );
//
//		final ColorDefinition cd = bs.getRiserOutline( );
//		final LineAttributes lia = LineAttributesImpl.create( cd == null ? null
//				: ColorDefinitionImpl.CYAN(),
//				LineStyle.SOLID_LITERAL,
//				1 );
//		double dX = 0, dY = 0, dZ = 0;
//		double dWidth = 0, dHeight = 0, dSpacing = 0, dValue = 0;
//		double dWidthZ = 0, dSpacingZ = 0;
//		Location lo;
//		Location3D lo3d;
//		Location[] loaFrontFace = null;
//		List loa3dFace = null;
//		Location3D[] a3dFace = null;
//		boolean bInverted = false;
//		final double dUnitSpacing = ( !cwa.isSetUnitSpacing( ) ) ? 50
//				: cwa.getUnitSpacing( ); // AS A PERCENTAGE OF ONE
//		if ( cwa.getDimension( ) == ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL )
//		{
//			boClientArea.delta( -dSeriesThickness, dSeriesThickness, 0, 0 );
//		}
//
//		// STRUCTURES NEEDED TO RENDER STACKED BARS
//		AxisSubUnit au;
//		Axis ax = getAxis( );
//
//		StackedSeriesLookup ssl = null;
//		StackGroup sg = null;
//
//		if ( !bRendering3D )
//		{
//			ssl = srh.getStackedSeriesLookup( );
//			sg = ssl.getStackGroup( bs );
//		}
//		int iSharedUnitIndex = ( sg == null ) ? 0 : sg.getSharedIndex( );
//		int iSharedUnitCount = ( sg == null ) ? 1 : sg.getSharedCount( );
//
//		double dStart, dEnd;
//
//		// DATA POINT RELATED VARIABLES ARE INITIALIZED HERE
//		Label laDataPoint = null;
//		Position pDataPoint = null;
//		Location loDataPoint = null;
//		Location3D loDataPoint3d = null;
//		Bounds boDataPoint = null;
//		try
//		{
//			laDataPoint = bRendering3D ? srh3d.getLabelAttributes( bs )
//					: srh.getLabelAttributes( bs );
//			if ( laDataPoint.isVisible( ) ) // ONLY COMPUTE IF NECESSARY
//			{
//				pDataPoint = bRendering3D ? srh3d.getLabelPosition( bs )
//						: srh.getLabelPosition( bs );
//				loDataPoint = LocationImpl.create( 0, 0 );
//				loDataPoint3d = Location3DImpl.create( 0, 0, 0 );
//				boDataPoint = BoundsImpl.create( 0, 0, 0, 0 );
//			}
//		}
//		catch ( Exception ex )
//		{
//			throw new ChartException( ChartEngineExtensionPlugin.ID,
//					ChartException.RENDERING,
//					ex );
//		}
//
//		// SETUP THE RISER FILL COLOR FROM THE SERIES DEFINITION PALETTE
//		// (BY CATEGORIES OR BY SERIES)
//		final SeriesDefinition sd = getSeriesDefinition( );
//		final EList elPalette = sd.getSeriesPalette( ).getEntries( );
//		final int iPaletteSize = elPalette.size( );
//		if ( iPaletteSize == 0 )
//		{
//			throw new ChartException( ChartEngineExtensionPlugin.ID,
//					ChartException.RENDERING,
//					"exception.empty.palette", //$NON-NLS-1$
//					new Object[]{
//						bs
//					},
//					Messages.getResourceBundle( getRunTimeContext( ).getULocale( ) ) );
//		}
//		final boolean bPaletteByCategory = ( cwa.getLegend( )
//				.getItemType( )
//				.getValue( ) == LegendItemType.CATEGORIES );
//		int iThisSeriesIndex = -1;
//		Fill f = null;
//		if ( !bPaletteByCategory )
//		{
//			iThisSeriesIndex = sd.getRunTimeSeries( ).indexOf( bs );
//			if ( iThisSeriesIndex < 0 )
//			{
//				throw new ChartException( ChartEngineExtensionPlugin.ID,
//						ChartException.RENDERING,
//						"exception.missing.series.for.palette.index", //$NON-NLS-1$
//						new Object[]{
//								bs, sd
//						},
//						Messages.getResourceBundle( getRunTimeContext( ).getULocale( ) ) );
//			}
//			f = (Fill) EcoreUtil.copy( (Fill) elPalette.get( iThisSeriesIndex
//					% iPaletteSize ) );
//		}
//
//		double[] faX = new double[dpha.length];
//		double[] faY = new double[dpha.length];
//
//		// THE MAIN LOOP THAT WALKS THROUGH THE DATA POINT HINTS ARRAY 'dpha'
//		for ( int i = 0; i < dpha.length; i++ )
//		{
//			faX[i] = Double.NaN;
//			faY[i] = Double.NaN;
//
//			if ( bPaletteByCategory )
//			{
//				f = (Fill) EcoreUtil.copy( (Fill) elPalette.get( i
//						% iPaletteSize ) );
//			}
//
//			updateTranslucency( f, bs );
//
//			// OBTAIN THE CO-ORDINATES OF THE DATA POINT
//			if ( bRendering3D )
//			{
//				lo3d = dpha[i].getLocation3D( );
//				dX = lo3d.getX( );
//				dY = lo3d.getY( );
//				dZ = lo3d.getZ( );
//
//				// ADJUST CO-ORDINATES BASED ON EACH UNIT SHARE (DUE TO MULTIPLE
//				// SERIES)
//				dSpacing = ( ( dpha[i].getSize2D( ).getWidth( ) ) * dUnitSpacing ) / 200;
//				dSpacingZ = ( ( dpha[i].getSize2D( ).getHeight( ) ) * dUnitSpacing ) / 200;
//			}
//			else
//			{
//				lo = dpha[i].getLocation( );
//				dX = lo.getX( );
//				dY = lo.getY( );
//
//				// ADJUST CO-ORDINATES BASED ON EACH UNIT SHARE (DUE TO MULTIPLE
//				// SERIES)
//				dSpacing = ( ( dpha[i].getSize( ) ) * dUnitSpacing ) / 200;
//			}
//
//			// BRANCH OFF HERE IF THE PLOT IS TRANSPOSED (AXES ARE SWAPPED)
//			if ( cwa.isTransposed( ) )
//			{
//				dHeight = dpha[i].getSize( );
//				dHeight -= 2 * dSpacing;
//				dHeight /= iSharedUnitCount;
//				dY += dSpacing + iSharedUnitIndex * dHeight;
//
//				if ( bs.isStacked( ) || ax.isPercent( ) ) // SPECIAL
//				// PROCESSING
//				// FOR STACKED SERIES
//				{
//					au = ssl.getUnit( bs, i ); // UNIT POSITIONS (MAX, MIN) FOR
//					// INDEX = 'i'
//					dValue = isNaN( dpha[i].getOrthogonalValue( ) ) ? 0
//							: ( (Double) dpha[i].getOrthogonalValue( ) ).doubleValue( );
//					if ( ax.isPercent( ) )
//					{
//						dValue = au.valuePercentage( dValue );
//					}
//					dStart = 0;
//					dEnd = 0;
//					if ( dValue > 0 ) // POSITIVE STACK ACCUMULATION
//					{
//						dStart = au.getValueMax( );
//						dEnd = dStart + dValue;
//						
//						au.setValueMax( dEnd );
//					}
//					else if ( dValue < 0 ) // NEGATIVE STACK ACCUMULATION
//					{
//						dStart = au.getValueMin( );
//						dEnd = dStart + dValue;
//						au.setValueMin( dEnd );
//					}
//					else
//					{
//						dStart = au.getLastValue( );
//					}
//					dEnd = au.getLastValue( ) + dValue;
//					au.setLastValue( dEnd );
//
//					try
//					{
//						// NOTE: CEILS DONE TO FIX ROUNDING ERRORS IN GFX
//						// CONTEXT (DOUBLE EDGES)
//						dX = Math.ceil( srh.getLocationOnOrthogonal( new Double( dEnd ) ) );
//						dBaseLocation = Math.ceil( srh.getLocationOnOrthogonal( new Double( dStart ) ) );
//					}
//					catch ( Exception ex )
//					{
//						throw new ChartException( ChartEngineExtensionPlugin.ID,
//								ChartException.RENDERING,
//								ex );
//					}
//				}
//				else
//				{
//					dBaseLocation = dZeroLocation;
//				}
//
//				// RANGE CHECK (WITHOUT CLIPPING)
//				// =================================
//				// NOTE: use a wider precision check here to fix some incorrect
//				// rendering case.
//				// ==================================
//				if ( ChartUtil.mathLT( dX, boClientArea.getLeft( ) ) ) // LEFT
//				// EDGE
//				{
//					if ( ChartUtil.mathLT( dBaseLocation,
//							boClientArea.getLeft( ) ) )
//					{
//						// BOTH ARE OUT OF RANGE
//						continue;
//					}
//					dX = boClientArea.getLeft( );
//				}
//				else if ( ChartUtil.mathLT( dBaseLocation,
//						boClientArea.getLeft( ) ) )
//				{
//					dBaseLocation = boClientArea.getLeft( );
//				}
//
//				if ( ChartUtil.mathGT( dX, boClientArea.getLeft( )
//						+ boClientArea.getWidth( ) ) ) // RIGHT
//				// EDGE
//				{
//					if ( ChartUtil.mathGT( dBaseLocation,
//							boClientArea.getLeft( ) + boClientArea.getWidth( ) ) )
//					{
//						// BOTH ARE OUT OF RANGE
//						continue;
//					}
//					dX = boClientArea.getLeft( ) + boClientArea.getWidth( );
//				}
//				else if ( ChartUtil.mathGT( dBaseLocation,
//						boClientArea.getLeft( ) + boClientArea.getWidth( ) ) )
//				{
//					dBaseLocation = boClientArea.getLeft( )
//							+ boClientArea.getWidth( );
//				}
//
//				// HANDLE INVERTED RISER DIRECTION
//				dWidth = dBaseLocation - dX;
//				// <= is needed for the label to be in the right place when the
//				// width is null
//				// this is due to the difference in rounding (ceil vs floor) for
//				// transposed axes.
//				bInverted = dWidth <= 0;
//				if ( bInverted )
//				{
//					dX = dBaseLocation;
//					dWidth = -dWidth;
//				}
//			}
//			else
//			// STANDARD PROCESSING FOR REGULAR NON-TRANSPOSED AXES (NOTE:
//			// SYMMETRIC CODE)
//			{
//				if ( bRendering3D )
//				{
//					dWidth = dpha[i].getSize2D( ).getWidth( );
//					dWidth -= 2 * dSpacing;
//
//					dWidthZ = dpha[i].getSize2D( ).getHeight( );
//					dWidthZ -= 2 * dSpacingZ;
//
//					dX += dSpacing;
//					dZ += dSpacingZ;
//				}
//				else
//				{
//					dWidth = dpha[i].getSize( );
//					dWidth -= 2 * dSpacing;
//
//					dWidth /= iSharedUnitCount;
//					dX += dSpacing + iSharedUnitIndex * dWidth;
//				}
//
//				if ( bs.isStacked( ) || ax.isPercent( ) ) // SPECIAL
//				// PROCESSING
//				// FOR STACKED OR
//				// PERCENT SERIES
//				{
//					if ( bRendering3D )
//					{
//						// Not support stack/percent for 3D chart.
//						throw new ChartException( ChartEngineExtensionPlugin.ID,
//								ChartException.COMPUTATION,
//								"exception.no.stack.percent.3D.chart", //$NON-NLS-1$
//								Messages.getResourceBundle( getRunTimeContext( ).getULocale( ) ) );
//					}
//
//					au = ssl.getUnit( bs, i ); // UNIT POSITIONS (MAX, MIN) FOR
//					// INDEX = 'i'
//					dValue = isNaN( dpha[i].getOrthogonalValue( ) ) ? 0
//							: ( (Double) dpha[i].getOrthogonalValue( ) ).doubleValue( );
//					if ( ax.isPercent( ) )
//					{
//						dValue = au.valuePercentage( dValue );
//					}
//					dStart = 0;
//					dEnd = 0;
//					if ( dValue > 0 ) // POSITIVE STACK ACCUMULATION
//					{
//						dStart = au.getValueMax( );
//						dEnd = dStart + dValue;
//						au.setValueMax( dEnd );
//					}
//					else if ( dValue < 0 ) // NEGATIVE STACK ACCUMULATION
//					{
//						dStart = au.getValueMin( );
//						dEnd = dStart + dValue;
//						au.setValueMin( dEnd );
//					}
//					else
//					{
//						dStart = au.getLastValue( );
//					}
//					dEnd = au.getLastValue( ) + dValue;
//					au.setLastValue( dEnd );
//
//					try
//					{
//						// NOTE: FLOORS DONE TO FIX ROUNDING ERRORS IN GFX
//						// CONTEXT (DOUBLE EDGES)
//						dY = Math.floor( srh.getLocationOnOrthogonal( new Double( dEnd ) ) );
//						dBaseLocation = Math.floor( srh.getLocationOnOrthogonal( new Double( dStart ) ) );
//					}
//					catch ( Exception ex )
//					{
//						throw new ChartException( ChartEngineExtensionPlugin.ID,
//								ChartException.RENDERING,
//								ex );
//					}
//				}
//				else
//				{
//					dBaseLocation = dZeroLocation;
//				}
//
//				// Range check.
//				if ( bRendering3D )
//				{
//					double plotBaseLocation = srh3d.getPlotBaseLocation( );
//
//					// RANGE CHECK (WITHOUT CLIPPING)
//					if ( dY < plotBaseLocation ) // TOP EDGE
//					{
//						if ( dBaseLocation < plotBaseLocation )
//						{
//							// BOTH ARE OUT OF RANGE
//							continue;
//						}
//						dY = plotBaseLocation; // - This causes
//						// clipping in output
//					}
//					else if ( dBaseLocation < plotBaseLocation )
//					{
//						dBaseLocation = plotBaseLocation;
//					}
//
//					if ( dY > plotBaseLocation + srh3d.getPlotHeight( ) ) // BOTTOM
//					// EDGE
//					{
//						if ( dBaseLocation > plotBaseLocation
//								+ srh3d.getPlotHeight( ) )
//						{
//							// BOTH ARE OUT OF RANGE
//							continue;
//						}
//						dY = plotBaseLocation + srh3d.getPlotHeight( );
//					}
//					else if ( dBaseLocation > plotBaseLocation
//							+ srh3d.getPlotHeight( ) )
//					{
//						dBaseLocation = plotBaseLocation
//								+ srh3d.getPlotHeight( );
//					}
//				}
//				else
//				{
//					// RANGE CHECK (WITHOUT CLIPPING)
//					if ( dY < boClientArea.getTop( ) ) // TOP EDGE
//					{
//						if ( dBaseLocation < boClientArea.getTop( ) )
//						{
//							// BOTH ARE OUT OF RANGE
//							continue;
//						}
//						dY = boClientArea.getTop( ); // - This causes
//						// clipping in output
//					}
//					else if ( dBaseLocation < boClientArea.getTop( ) )
//					{
//						dBaseLocation = boClientArea.getTop( );
//					}
//
//					if ( dY > boClientArea.getTop( ) + boClientArea.getHeight( ) ) // BOTTOM
//					// EDGE
//					{
//						if ( dBaseLocation > boClientArea.getTop( )
//								+ boClientArea.getHeight( ) )
//						{
//							// BOTH ARE OUT OF RANGE
//							continue;
//						}
//						dY = boClientArea.getTop( ) + boClientArea.getHeight( );
//					}
//					else if ( dBaseLocation > boClientArea.getTop( )
//							+ boClientArea.getHeight( ) )
//					{
//						dBaseLocation = boClientArea.getTop( )
//								+ boClientArea.getHeight( );
//					}
//				}
//
//				// HANDLE INVERTED RISER DIRECTION
//				dHeight = dBaseLocation - dY;
//				bInverted = dHeight < 0;
//				if ( bInverted )
//				{
//					dY = dBaseLocation;
//					dHeight = -dHeight;
//				}
//			}
//
//			// COMPUTE EACH RECTANGLE FACE
//			if ( rt.getValue( ) == RiserType.RECTANGLE )
//			{
//				if ( bRendering3D )
//				{
//					loa3dFace = new ArrayList( );
//
//					if ( !bInverted )
//					{
//						// downward
//
//						// back
//						a3dFace = new Location3D[4];
//						a3dFace[0] = Location3DImpl.create( dX,
//								dY + dHeight,
//								dZ );
//						a3dFace[1] = Location3DImpl.create( dX + dWidth, dY
//								+ dHeight, dZ );
//						a3dFace[2] = Location3DImpl.create( dX + dWidth, dY, dZ );
//						a3dFace[3] = Location3DImpl.create( dX, dY, dZ );
//						loa3dFace.add( a3dFace );
//
//						// left
//						a3dFace = new Location3D[4];
//						a3dFace[0] = Location3DImpl.create( dX,
//								dY + dHeight,
//								dZ );
//						a3dFace[1] = Location3DImpl.create( dX, dY, dZ );
//						a3dFace[2] = Location3DImpl.create( dX, dY, dZ
//								+ dWidthZ );
//						a3dFace[3] = Location3DImpl.create( dX,
//								dY + dHeight,
//								dZ + dWidthZ );
//						loa3dFace.add( a3dFace );
//
//						// bottom
//						a3dFace = new Location3D[4];
//						a3dFace[0] = Location3DImpl.create( dX, dY, dZ );
//						a3dFace[1] = Location3DImpl.create( dX + dWidth, dY, dZ );
//						a3dFace[2] = Location3DImpl.create( dX + dWidth, dY, dZ
//								+ dWidthZ );
//						a3dFace[3] = Location3DImpl.create( dX, dY, dZ
//								+ dWidthZ );
//						loa3dFace.add( a3dFace );
//
//						// top
//						a3dFace = new Location3D[4];
//						a3dFace[0] = Location3DImpl.create( dX,
//								dY + dHeight,
//								dZ );
//						a3dFace[1] = Location3DImpl.create( dX,
//								dY + dHeight,
//								dZ + dWidthZ );
//						a3dFace[2] = Location3DImpl.create( dX + dWidth, dY
//								+ dHeight, dZ + dWidthZ );
//						a3dFace[3] = Location3DImpl.create( dX + dWidth, dY
//								+ dHeight, dZ );
//						loa3dFace.add( a3dFace );
//
//						// right
//						a3dFace = new Location3D[4];
//						a3dFace[0] = Location3DImpl.create( dX + dWidth, dY
//								+ dHeight, dZ + dWidthZ );
//						a3dFace[1] = Location3DImpl.create( dX + dWidth, dY, dZ
//								+ dWidthZ );
//						a3dFace[2] = Location3DImpl.create( dX + dWidth, dY, dZ );
//						a3dFace[3] = Location3DImpl.create( dX + dWidth, dY
//								+ dHeight, dZ );
//						loa3dFace.add( a3dFace );
//
//						// front
//						a3dFace = new Location3D[4];
//						a3dFace[0] = Location3DImpl.create( dX,
//								dY + dHeight,
//								dZ + dWidthZ );
//						a3dFace[1] = Location3DImpl.create( dX, dY, dZ
//								+ dWidthZ );
//						a3dFace[2] = Location3DImpl.create( dX + dWidth, dY, dZ
//								+ dWidthZ );
//						a3dFace[3] = Location3DImpl.create( dX + dWidth, dY
//								+ dHeight, dZ + dWidthZ );
//						loa3dFace.add( a3dFace );
//					}
//					else
//					{
//						// back
//						a3dFace = new Location3D[4];
//						a3dFace[0] = Location3DImpl.create( dX, dY, dZ );
//						a3dFace[1] = Location3DImpl.create( dX,
//								dY + dHeight,
//								dZ );
//						a3dFace[2] = Location3DImpl.create( dX + dWidth, dY
//								+ dHeight, dZ );
//						a3dFace[3] = Location3DImpl.create( dX + dWidth, dY, dZ );
//						loa3dFace.add( a3dFace );
//
//						// left
//						a3dFace = new Location3D[4];
//						a3dFace[0] = Location3DImpl.create( dX, dY, dZ );
//						a3dFace[1] = Location3DImpl.create( dX, dY, dZ
//								+ dWidthZ );
//						a3dFace[2] = Location3DImpl.create( dX,
//								dY + dHeight,
//								dZ + dWidthZ );
//						a3dFace[3] = Location3DImpl.create( dX,
//								dY + dHeight,
//								dZ );
//						loa3dFace.add( a3dFace );
//
//						// bottom
//						a3dFace = new Location3D[4];
//						a3dFace[0] = Location3DImpl.create( dX, dY, dZ );
//						a3dFace[1] = Location3DImpl.create( dX + dWidth, dY, dZ );
//						a3dFace[2] = Location3DImpl.create( dX + dWidth, dY, dZ
//								+ dWidthZ );
//						a3dFace[3] = Location3DImpl.create( dX, dY, dZ
//								+ dWidthZ );
//						loa3dFace.add( a3dFace );
//
//						// top
//						a3dFace = new Location3D[4];
//						a3dFace[0] = Location3DImpl.create( dX,
//								dY + dHeight,
//								dZ );
//						a3dFace[1] = Location3DImpl.create( dX,
//								dY + dHeight,
//								dZ + dWidthZ );
//						a3dFace[2] = Location3DImpl.create( dX + dWidth, dY
//								+ dHeight, dZ + dWidthZ );
//						a3dFace[3] = Location3DImpl.create( dX + dWidth, dY
//								+ dHeight, dZ );
//						loa3dFace.add( a3dFace );
//
//						// right
//						a3dFace = new Location3D[4];
//						a3dFace[0] = Location3DImpl.create( dX + dWidth, dY, dZ
//								+ dWidthZ );
//						a3dFace[1] = Location3DImpl.create( dX + dWidth, dY, dZ );
//						a3dFace[2] = Location3DImpl.create( dX + dWidth, dY
//								+ dHeight, dZ );
//						a3dFace[3] = Location3DImpl.create( dX + dWidth, dY
//								+ dHeight, dZ + dWidthZ );
//						loa3dFace.add( a3dFace );
//
//						// front
//						a3dFace = new Location3D[4];
//						a3dFace[0] = Location3DImpl.create( dX, dY, dZ
//								+ dWidthZ );
//						a3dFace[1] = Location3DImpl.create( dX + dWidth, dY, dZ
//								+ dWidthZ );
//						a3dFace[2] = Location3DImpl.create( dX + dWidth, dY
//								+ dHeight, dZ + dWidthZ );
//						a3dFace[3] = Location3DImpl.create( dX,
//								dY + dHeight,
//								dZ + dWidthZ );
//						loa3dFace.add( a3dFace );
//					}
//				}
//				else
//				{
//					loaFrontFace = new Location[4]; // NEW INSTANCE CREATED PER
//					// DATA
//					// POINT
//					loaFrontFace[0] = LocationImpl.create( dX, dY );
//					loaFrontFace[1] = LocationImpl.create( dX, dY + dHeight );
//					loaFrontFace[2] = LocationImpl.create( dX + dWidth, dY
//							+ dHeight );
//					loaFrontFace[3] = LocationImpl.create( dX + dWidth, dY );
//
//					if ( cwa.isTransposed( ) )
//					{
//						faX[i] = ( dX + dWidth );
//						faY[i] = ( dY + dHeight / 2 );
//					}
//					else
//					{
//						faX[i] = ( dX + dWidth / 2 );
//						faY[i] = dY;
//					}
//				}
//			}
//			// COMPUTE EACH TRIANGULAR FACE
//			else if ( rt.getValue( ) == RiserType.TRIANGLE )
//			{
//				if ( bRendering3D )
//				{
//					loa3dFace = new ArrayList( );
//
//					if ( !bInverted )
//					{
//						// front
//						a3dFace = new Location3D[3];
//						a3dFace[0] = Location3DImpl.create( dX + dWidth, dY
//								+ dHeight, dZ + dWidthZ );
//						a3dFace[1] = Location3DImpl.create( dX,
//								dY + dHeight,
//								dZ + dWidthZ );
//						a3dFace[2] = Location3DImpl.create( dX + dWidth / 2,
//								dY,
//								dZ + dWidthZ / 2 );
//						loa3dFace.add( a3dFace );
//
//						// back
//						a3dFace = new Location3D[3];
//						a3dFace[0] = Location3DImpl.create( dX,
//								dY + dHeight,
//								dZ );
//						a3dFace[1] = Location3DImpl.create( dX + dWidth, dY
//								+ dHeight, dZ );
//						a3dFace[2] = Location3DImpl.create( dX + dWidth / 2,
//								dY,
//								dZ + dWidthZ / 2 );
//						loa3dFace.add( a3dFace );
//
//						// left
//						a3dFace = new Location3D[3];
//						a3dFace[0] = Location3DImpl.create( dX,
//								dY + dHeight,
//								dZ + dWidthZ );
//						a3dFace[1] = Location3DImpl.create( dX,
//								dY + dHeight,
//								dZ );
//						a3dFace[2] = Location3DImpl.create( dX + dWidth / 2,
//								dY,
//								dZ + dWidthZ / 2 );
//						loa3dFace.add( a3dFace );
//
//						// right
//						a3dFace = new Location3D[3];
//						a3dFace[0] = Location3DImpl.create( dX + dWidth, dY
//								+ dHeight, dZ );
//						a3dFace[1] = Location3DImpl.create( dX + dWidth, dY
//								+ dHeight, dZ + dWidthZ );
//						a3dFace[2] = Location3DImpl.create( dX + dWidth / 2,
//								dY,
//								dZ + dWidthZ / 2 );
//						loa3dFace.add( a3dFace );
//
//						// bottom
//						a3dFace = new Location3D[4];
//						a3dFace[0] = Location3DImpl.create( dX,
//								dY + dHeight,
//								dZ );
//						a3dFace[1] = Location3DImpl.create( dX,
//								dY + dHeight,
//								dZ + dWidthZ );
//						a3dFace[2] = Location3DImpl.create( dX + dWidth, dY
//								+ dHeight, dZ + dWidthZ );
//						a3dFace[3] = Location3DImpl.create( dX + dWidth, dY
//								+ dHeight, dZ );
//						loa3dFace.add( a3dFace );
//					}
//					else
//					{
//						// front
//						a3dFace = new Location3D[3];
//						a3dFace[0] = Location3DImpl.create( dX, dY, dZ
//								+ dWidthZ );
//						a3dFace[1] = Location3DImpl.create( dX + dWidth, dY, dZ
//								+ dWidthZ );
//						a3dFace[2] = Location3DImpl.create( dX + dWidth / 2, dY
//								+ dHeight, dZ + dWidthZ / 2 );
//						loa3dFace.add( a3dFace );
//
//						// back
//						a3dFace = new Location3D[3];
//						a3dFace[0] = Location3DImpl.create( dX + dWidth, dY, dZ );
//						a3dFace[1] = Location3DImpl.create( dX, dY, dZ );
//						a3dFace[2] = Location3DImpl.create( dX + dWidth / 2, dY
//								+ dHeight, dZ + dWidthZ / 2 );
//						loa3dFace.add( a3dFace );
//
//						// left
//						a3dFace = new Location3D[3];
//						a3dFace[0] = Location3DImpl.create( dX, dY, dZ );
//						a3dFace[1] = Location3DImpl.create( dX, dY, dZ
//								+ dWidthZ );
//						a3dFace[2] = Location3DImpl.create( dX + dWidth / 2, dY
//								+ dHeight, dZ + dWidthZ / 2 );
//						loa3dFace.add( a3dFace );
//
//						// right
//						a3dFace = new Location3D[3];
//						a3dFace[0] = Location3DImpl.create( dX + dWidth, dY, dZ
//								+ dWidthZ );
//						a3dFace[1] = Location3DImpl.create( dX + dWidth, dY, dZ );
//						a3dFace[2] = Location3DImpl.create( dX + dWidth / 2, dY
//								+ dHeight, dZ + dWidthZ / 2 );
//						loa3dFace.add( a3dFace );
//
//						// bottom
//						a3dFace = new Location3D[4];
//						a3dFace[0] = Location3DImpl.create( dX, dY, dZ );
//						a3dFace[1] = Location3DImpl.create( dX + dWidth, dY, dZ );
//						a3dFace[2] = Location3DImpl.create( dX + dWidth, dY, dZ
//								+ dWidthZ );
//						a3dFace[3] = Location3DImpl.create( dX, dY, dZ
//								+ dWidthZ );
//						loa3dFace.add( a3dFace );
//					}
//				}
//				else
//				{
//					loaFrontFace = new Location[3]; // NEW INSTANCE CREATED PER
//					// DATA
//					// POINT
//					if ( cwa.isTransposed( ) ) // TRIANGLE IS ROTATED BY 90
//					// DEGREES
//					{
//						final double dX1 = bInverted ? dX : dX + dWidth;
//						final double dX2 = bInverted ? dX + dWidth : dX;
//						loaFrontFace[0] = LocationImpl.create( dX1, dY );
//						loaFrontFace[1] = LocationImpl.create( dX2, dY
//								+ dHeight
//								/ 2 );
//						loaFrontFace[2] = LocationImpl.create( dX1, dY
//								+ dHeight );
//
//						faX[i] = dX2;
//						faY[i] = ( dY + dHeight / 2 );
//					}
//					else
//					// TRIANGLE IS UPRIGHT OR INVERTED DEPENDING ON VALUE BEING
//					// PLOTTED
//					{
//						final double dY1 = bInverted ? dY : dY + dHeight;
//						final double dY2 = bInverted ? dY + dHeight : dY;
//						loaFrontFace[0] = LocationImpl.create( dX, dY1 );
//						loaFrontFace[1] = LocationImpl.create( dX + dWidth / 2,
//								dY2 );
//						loaFrontFace[2] = LocationImpl.create( dX + dWidth, dY1 );
//
//						faX[i] = ( dX + dWidth / 2 );
//						faY[i] = ( dY2 );
//					}
//				}
//			}
//			else
//			{
//				throw new ChartException( ChartEngineExtensionPlugin.ID,
//						ChartException.RENDERING,
//						"exception.unspecified.riser.type", //$NON-NLS-1$ 
//						new Object[]{
//							rt.getName( )
//						},
//						Messages.getResourceBundle( getRunTimeContext( ).getULocale( ) ) );
//			}
//
//			// Skip rendering.
//			if ( isNaN( dpha[i].getOrthogonalValue( ) ) )
//			{
//				faX[i] = Double.NaN;
//				faY[i] = Double.NaN;
//				continue;
//			}
//
//			if ( isInteractivityEnabled( ) )
//			{
//				// PROCESS 'SERIES LEVEL' TRIGGERS USING SOURCE='bs'
//				final EList elTriggers = bs.getTriggers( );
//				if ( !elTriggers.isEmpty( ) )
//				{
//					final StructureSource iSource = WrappedStructureSource.createSeriesDataPoint( bs,
//							dpha[i] );
//					
//					
//
//					if ( bRendering3D )
//					{
//						for ( int j = 0; j< loa3dFace.size( ); j++ )
//						{
//							final InteractionEvent iev = createEvent( iSource, elTriggers, ipr );	
//							
//							final Polygon3DRenderEvent pre3d = (Polygon3DRenderEvent) ( (EventObjectCache) ipr ).getEventObject( StructureSource.createSeries( bs ),
//									Polygon3DRenderEvent.class );
//							pre3d.setPoints3D( (Location3D[]) loa3dFace.get( j ) ) ;
//							final Location panningOffset = getPanningOffset( );
//
//							if ( get3DEngine( ).processEvent( pre3d,
//									panningOffset.getX( ),
//									panningOffset.getY( ) ) != null )
//							{
//								iev.setHotSpot( pre3d );
//								ipr.enableInteraction( iev );
//							}
//						}
//					}
//					else
//					{
//						final InteractionEvent iev = createEvent( iSource, elTriggers, ipr );
//						final PolygonRenderEvent pre = (PolygonRenderEvent) ( (EventObjectCache) ipr ).getEventObject( StructureSource.createSeries( bs ),
//								PolygonRenderEvent.class );
//						pre.setPoints( loaFrontFace );
//						iev.setHotSpot( pre );
//						ipr.enableInteraction( iev );
//					}
//				}
//			}
//
//			// RENDER THE POLYGON (EXTRUDED IF > 2D)
//			ScriptHandler.callFunction( (AbstractScriptHandler<?>) sh,
//					ScriptHandler.BEFORE_DRAW_ELEMENT,
//					dpha[i],
//					f );
//			ScriptHandler.callFunction( (AbstractScriptHandler<?>) sh,
//					ScriptHandler.BEFORE_DRAW_DATA_POINT,
//					dpha[i],
//					f,
//					getRunTimeContext( ).getScriptContext( ) );
//			getRunTimeContext( ).notifyStructureChange( IStructureDefinitionListener.BEFORE_DRAW_ELEMENT,
//					dpha[i] );
//			getRunTimeContext( ).notifyStructureChange( IStructureDefinitionListener.BEFORE_DRAW_DATA_POINT,
//					dpha[i] );
//			if ( bRendering3D )
//			{
//				render3DPlane( ipr,
//						WrappedStructureSource.createSeriesDataPoint( bs,
//								dpha[i] ),
//						loa3dFace,
//						f,
//						lia );
//			}
//			else
//			{
//				renderPlane( ipr,
//						WrappedStructureSource.createSeriesDataPoint( bs,
//								dpha[i] ),
//						loaFrontFace,
//						f,
//						lia,
//						cwa.getDimension( ),
//						dSeriesThickness,
//						true );
//			}
//			ScriptHandler.callFunction( (AbstractScriptHandler<?>) sh,
//					ScriptHandler.AFTER_DRAW_ELEMENT,
//					dpha[i],
//					f );
//			ScriptHandler.callFunction( sh,
//					ScriptHandler.AFTER_DRAW_DATA_POINT,
//					dpha[i],
//					f,
//					getRunTimeContext( ).getScriptContext( ) );
//			getRunTimeContext( ).notifyStructureChange( IStructureDefinitionListener.AFTER_DRAW_ELEMENT,
//					dpha[i] );
//			getRunTimeContext( ).notifyStructureChange( IStructureDefinitionListener.AFTER_DRAW_DATA_POINT,
//					dpha[i] );
//
//			// RENDER DATA POINTS
//			if ( laDataPoint.isVisible( ) )
//			{
//				laDataPoint.getCaption( ).setValue( dpha[i].getDisplayValue( ) );
//
//				ScriptHandler.callFunction( (AbstractScriptHandler<?>) sh,
//						ScriptHandler.BEFORE_DRAW_DATA_POINT_LABEL,
//						dpha[i],
//						laDataPoint,
//						getRunTimeContext( ).getScriptContext( ) );
//				getRunTimeContext( ).notifyStructureChange( IStructureDefinitionListener.BEFORE_DRAW_DATA_POINT_LABEL,
//						laDataPoint );
//
//				if ( laDataPoint.isVisible( ) )
//				{
//					if ( !cwa.isTransposed( ) )
//					{
//						if ( bRendering3D )
//						{
//							if ( pDataPoint.getValue( ) == Position.OUTSIDE )
//							{
//								if ( !bInverted )
//								{
//									loDataPoint3d.set( dX + dWidth / 2, dY
//											- p.getVerticalSpacing( ), dZ
//											+ dWidthZ
//											/ 2 );
//
//									Text3DRenderEvent tre = (Text3DRenderEvent) ( (EventObjectCache) ipr ).getEventObject( WrappedStructureSource.createSeriesDataPoint( bs,
//											dpha[i] ),
//											Text3DRenderEvent.class );
//									tre.setLabel( laDataPoint );
//									tre.setTextPosition( TextRenderEvent.BELOW );
//									tre.setAction( TextRenderEvent.RENDER_TEXT_AT_LOCATION );
//
//									Location3D[] loa3d = new Location3D[5];
//									loa3d[0] = loDataPoint3d;
//									loa3d[1] = Location3DImpl.create( dX, dY
//											- p.getVerticalSpacing( ), dZ
//											+ dWidthZ
//											/ 2 );
//									loa3d[2] = Location3DImpl.create( dX, dY
//											- p.getVerticalSpacing( )
//											- 16, dZ + dWidthZ / 2 );
//									loa3d[3] = Location3DImpl.create( dX
//											+ dWidth, dY
//											- p.getVerticalSpacing( )
//											- 16, dZ + dWidthZ / 2 );
//									loa3d[4] = Location3DImpl.create( dX
//											+ dWidth, dY
//											- p.getVerticalSpacing( ), dZ
//											+ dWidthZ
//											/ 2 );
//									tre.setBlockBounds3D( loa3d );
//
//									getDeferredCache( ).addLabel( tre );
//								}
//								else
//								{
//									loDataPoint3d.set( dX + dWidth / 2, dY
//											+ dHeight
//											+ p.getVerticalSpacing( ), dZ
//											+ dWidthZ
//											/ 2 );
//
//									Text3DRenderEvent tre = (Text3DRenderEvent) ( (EventObjectCache) ipr ).getEventObject( WrappedStructureSource.createSeriesDataPoint( bs,
//											dpha[i] ),
//											Text3DRenderEvent.class );
//									tre.setAction( TextRenderEvent.RENDER_TEXT_AT_LOCATION );
//									tre.setLabel( laDataPoint );
//									tre.setTextPosition( TextRenderEvent.ABOVE );
//
//									Location3D[] loa3d = new Location3D[5];
//									loa3d[0] = loDataPoint3d;
//									loa3d[1] = Location3DImpl.create( dX
//											+ dWidth, dY
//											+ dHeight
//											+ p.getVerticalSpacing( ), dZ
//											+ dWidthZ
//											/ 2 );
//									loa3d[2] = Location3DImpl.create( dX
//											+ dWidth, dY
//											+ dHeight
//											+ 16
//											+ p.getVerticalSpacing( ), dZ
//											+ dWidthZ
//											/ 2 );
//									loa3d[3] = Location3DImpl.create( dX, dY
//											+ dHeight
//											+ 16
//											+ p.getVerticalSpacing( ), dZ
//											+ dWidthZ
//											/ 2 );
//									loa3d[4] = Location3DImpl.create( dX, dY
//											+ dHeight
//											+ p.getVerticalSpacing( ), dZ
//											+ dWidthZ
//											/ 2 );
//									tre.setBlockBounds3D( loa3d );
//
//									getDeferredCache( ).addLabel( tre );
//								}
//							}
//							else
//							{
//								throw new ChartException( ChartEngineExtensionPlugin.ID,
//										ChartException.RENDERING,
//										"exception.illegal.datapoint.position.bar3d", //$NON-NLS-1$  
//										new Object[]{
//											pDataPoint.getName( )
//										},
//										Messages.getResourceBundle( getRunTimeContext( ).getULocale( ) ) );
//							}
//						}
//						else
//						{
//							switch ( pDataPoint.getValue( ) )
//							{
//								case Position.OUTSIDE :
//									if ( !bInverted )
//									{
//										loDataPoint.set( dX + dWidth / 2, dY
//												- p.getVerticalSpacing( ) );
//										renderLabel( WrappedStructureSource.createSeriesDataPoint( bs,
//												dpha[i] ),
//												TextRenderEvent.RENDER_TEXT_AT_LOCATION,
//												laDataPoint,
//												Position.ABOVE_LITERAL,
//												loDataPoint,
//												null );
//									}
//									else
//									{
//										loDataPoint.set( dX + dWidth / 2, dY
//												+ dHeight
//												+ p.getVerticalSpacing( ) );
//										renderLabel( WrappedStructureSource.createSeriesDataPoint( bs,
//												dpha[i] ),
//												TextRenderEvent.RENDER_TEXT_AT_LOCATION,
//												laDataPoint,
//												Position.BELOW_LITERAL,
//												loDataPoint,
//												null );
//									}
//									break;
//								case Position.INSIDE :
//									boDataPoint.updateFrom( loaFrontFace );
//									renderLabel( WrappedStructureSource.createSeriesDataPoint( bs,
//											dpha[i] ),
//											TextRenderEvent.RENDER_TEXT_IN_BLOCK,
//											laDataPoint,
//											null,
//											null,
//											boDataPoint );
//									break;
//								default :
//									throw new ChartException( ChartEngineExtensionPlugin.ID,
//											ChartException.RENDERING,
//											"exception.illegal.datapoint.position.bar", //$NON-NLS-1$  
//											new Object[]{
//												pDataPoint.getName( )
//											},
//											Messages.getResourceBundle( getRunTimeContext( ).getULocale( ) ) );
//							}
//						}
//					}
//					else
//					{
//						switch ( pDataPoint.getValue( ) )
//						{
//							case Position.OUTSIDE :
//								if ( !bInverted )
//								{
//									loDataPoint.set( dX
//											- p.getHorizontalSpacing( ), dY
//											+ dHeight
//											/ 2 );
//									renderLabel( WrappedStructureSource.createSeriesDataPoint( bs,
//											dpha[i] ),
//											TextRenderEvent.RENDER_TEXT_AT_LOCATION,
//											laDataPoint,
//											Position.LEFT_LITERAL,
//											loDataPoint,
//											null );
//								}
//								else
//								{
//									loDataPoint.set( dX
//											+ dWidth
//											+ p.getHorizontalSpacing( ), dY
//											+ dHeight
//											/ 2 );
//									renderLabel( WrappedStructureSource.createSeriesDataPoint( bs,
//											dpha[i] ),
//											TextRenderEvent.RENDER_TEXT_AT_LOCATION,
//											laDataPoint,
//											Position.RIGHT_LITERAL,
//											loDataPoint,
//											null );
//								}
//								break;
//							case Position.INSIDE :
//								boDataPoint.updateFrom( loaFrontFace );
//								renderLabel( WrappedStructureSource.createSeriesDataPoint( bs,
//										dpha[i] ),
//										TextRenderEvent.RENDER_TEXT_IN_BLOCK,
//										laDataPoint,
//										null,
//										null,
//										boDataPoint );
//								break;
//							default :
//								throw new ChartException( ChartEngineExtensionPlugin.ID,
//										ChartException.RENDERING,
//										"exception.illegal.datapoint.position.bar", //$NON-NLS-1$
//										new Object[]{
//											pDataPoint.getName( )
//										},
//										Messages.getResourceBundle( getRunTimeContext( ).getULocale( ) ) );
//						}
//					}
//				}
//				ScriptHandler.callFunction( sh,
//						ScriptHandler.AFTER_DRAW_DATA_POINT_LABEL,
//						dpha[i],
//						laDataPoint,
//						getRunTimeContext( ).getScriptContext( ) );
//				getRunTimeContext( ).notifyStructureChange( IStructureDefinitionListener.AFTER_DRAW_DATA_POINT_LABEL,
//						laDataPoint );
//			}
//		}
//
//		if ( !bRendering3D )
//		{
//			List points = new ArrayList( );
//			for ( int i = 0; i < faX.length; i++ )
//			{
//				points.add( new double[]{
//						faX[i], faY[i]
//				} );
//			}
//
//			points = filterNull( points );
//
//			if ( isLastRuntimeSeriesInAxis( ) )
//			{
//				// clean stack state.
//				getRunTimeContext( ).putState( STACKED_SERIES_LOCATION_KEY, null );
//			}
//			else
//			{
//				getRunTimeContext( ).putState( STACKED_SERIES_LOCATION_KEY, points );
//			}
//
//			// Render the fitting curve.
//			if ( getSeries( ).getCurveFitting( ) != null )
//			{
//				Location[] larray = createLocationArray( points );
//				renderFittingCurve( ipr,
//						larray,
//						getSeries( ).getCurveFitting( ),
//						cwa.getDimension( ) == ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL,
//						true );
//			}
//		}
//
//	}
//
//	protected  InteractionEvent createEvent( StructureSource iSource, List elTriggers, IPrimitiveRenderer ipr )
//	{
//		final InteractionEvent iev = new InteractionEvent( iSource );
//		Trigger tg;
//		for ( int t = 0; t < elTriggers.size( ); t++ )
//		{
//			tg = TriggerImpl.copyInstance( (Trigger) elTriggers.get( t ) );
//			processTrigger( tg, iSource );
//			iev.addTrigger( tg );
//		}
//		return iev;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.birt.chart.render.BaseRenderer#renderLegendGraphic(org.eclipse.birt.chart.device.IPrimitiveRenderer,
//	 *      org.eclipse.birt.chart.model.layout.Legend,
//	 *      org.eclipse.birt.chart.model.attribute.Fill,
//	 *      org.eclipse.birt.chart.model.attribute.Bounds)
//	 */
//	public final void renderLegendGraphic( IPrimitiveRenderer ipr, Legend lg,
//			Fill fPaletteEntry, Bounds bo ) throws ChartException
//	{
//		final BarSeries bs = (BarSeries) getSeries( );
//		final ColorDefinition cd = bs.getRiserOutline( );
//		final LineAttributes lia = LineAttributesImpl.create( cd == null ? null
//				: ColorDefinitionImpl.copyInstance( cd ),
//				LineStyle.SOLID_LITERAL,
//				1 );
//		if ( fPaletteEntry == null ) // TEMPORARY PATCH: WILL BE REMOVED SOON
//		{
//			fPaletteEntry = ColorDefinitionImpl.RED( );
//		}
//
//		// COMPUTE THE FRONT FACE ONLY
//		Location[] loaFrontFace = null;
//		if ( bs.getRiser( ).getValue( ) == RiserType.RECTANGLE )
//		{
//			loaFrontFace = new Location[4];
//			loaFrontFace[0] = LocationImpl.create( bo.getLeft( ), bo.getTop( ) );
//			loaFrontFace[1] = LocationImpl.create( bo.getLeft( ), bo.getTop( )
//					+ bo.getHeight( ) );
//			loaFrontFace[2] = LocationImpl.create( bo.getLeft( )
//					+ bo.getWidth( ), bo.getTop( ) + bo.getHeight( ) );
//			loaFrontFace[3] = LocationImpl.create( bo.getLeft( )
//					+ bo.getWidth( ), bo.getTop( ) );
//		}
//		else if ( bs.getRiser( ).getValue( ) == RiserType.TRIANGLE )
//		{
//			loaFrontFace = new Location[3]; // NEW INSTANCE CREATED PER DATA
//			// POINT
//			loaFrontFace[0] = LocationImpl.create( bo.getLeft( ), bo.getTop( )
//					+ bo.getHeight( ) );
//			loaFrontFace[1] = LocationImpl.create( bo.getLeft( )
//					+ bo.getWidth( )
//					/ 2, bo.getTop( ) );
//			loaFrontFace[2] = LocationImpl.create( bo.getLeft( )
//					+ bo.getWidth( ), bo.getTop( ) + bo.getHeight( ) );
//		}
//		else
//		{
//			throw new ChartException( ChartEngineExtensionPlugin.ID,
//					ChartException.RENDERING,
//					"exception.legend.graphic.unknown.riser", //$NON-NLS-1$
//					new Object[]{
//						bs.getRiser( ).getName( )
//					},
//					Messages.getResourceBundle( getRunTimeContext( ).getULocale( ) ) );
//		}
//
//		// RENDER THE PLANE (INTERNALLY EXTRUDED IF NECESSARY)
//		renderPlane( ipr,
//				StructureSource.createLegend( lg ),
//				loaFrontFace,
//				fPaletteEntry,
//				lia,
//				getModel( ).getDimension( ),
//				3 * getDeviceScale( ),
//				false );
//	}
//
//	/**
//	 * Returns a Location array from given list, each entry in the list should
//	 * be a double[2] array object.
//	 * 
//	 * @param ll
//	 * @return
//	 */
//	private Location[] createLocationArray( List ll )
//	{
//		Location[] loa = new Location[ll.size( )];
//		for ( int i = 0; i < loa.length; i++ )
//		{
//			double[] obj = (double[]) ll.get( i );
//			loa[i] = LocationImpl.create( obj[0], obj[1] );
//		}
//		return loa;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.birt.chart.render.BaseRenderer#compute(org.eclipse.birt.chart.model.attribute.Bounds,
//	 *      org.eclipse.birt.chart.model.layout.Plot,
//	 *      org.eclipse.birt.chart.render.ISeriesRenderingHints)
//	 */
//	public void compute( Bounds bo, Plot p, ISeriesRenderingHints isrh )
//			throws ChartException
//	{
//		// NOTE: This method is not used by the BAR renderer
//	}
//}