/*******************************************************************************
 * Copyright (c) 2005 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - created
 *******************************************************************************/

package fr.legrain.libLgrBirt.test;

import java.io.IOException;

import org.eclipse.birt.report.model.api.CellHandle;
import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.DesignEngine;
import org.eclipse.birt.report.model.api.ElementFactory;
import org.eclipse.birt.report.model.api.GridHandle;
import org.eclipse.birt.report.model.api.ImageHandle;
import org.eclipse.birt.report.model.api.LabelHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.RowHandle;
import org.eclipse.birt.report.model.api.SessionHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;

/**
 * Simple BIRT Design Engine API (DEAPI) demo.
 */

public class DeDemo
{

	public static void main( String[] args )
	{
		try
		{
			buildReport( );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
		catch ( SemanticException e )
		{
			e.printStackTrace();
		}
	}
	
	// This function shows how to build a very simple BIRT report with a
	// minimal set of content: a simple grid with an image and a label.
	
	static void buildReport( ) throws IOException, SemanticException
	{
		// Create a session handle. This is used to manage all open designs.
		// Your app need create the session only once.
		
		SessionHandle session = DesignEngine.newSession( null );
		
		// Create a new report design.
		
		ReportDesignHandle design = session.createDesign( );
		
		// The element factory creates instances of the various BIRT elements.
		
		ElementFactory factory = design.getElementFactory( );
		
		// Create a simple master page that describes how the report will
		// appear when printed.
		//
		// Note: The report will fail to load in the BIRT designer
		// unless you create a master page.
		
		DesignElementHandle element = factory.newSimpleMasterPage( "Page Master" ); //$NON-NLS-1$
		design.getMasterPages( ).add( element );
		
		// Create a grid and add it to the "body" slot of the report
		// design.
		
		GridHandle grid = factory.newGridItem( null, 2 /* cols */, 1 /* row */ );
		design.getBody( ).add( grid );
		
		// Note: Set the table width to 100% to prevent the label
		// from appearing too narrow in the layout view.
		
		grid.setWidth( "100%" ); //$NON-NLS-1$
		
		// Get the first row.
		
		RowHandle row = (RowHandle) grid.getRows( ).get( 0 );
		
		// Create an image and add it to the first cell.
		
		ImageHandle image = factory.newImage( null );
		CellHandle cell = (CellHandle) row.getCells( ).get( 0 );
		cell.getContent( ).add( image );
		image.setURI( "http://www.eclipse.org/birt/tutorial/multichip-4.jpg" ); //$NON-NLS-1$
		
		// Create a label and add it to the second cell.
		
		LabelHandle label = factory.newLabel( null );
		cell = (CellHandle) row.getCells( ).get( 1 );
		cell.getContent( ).add( label );
		label.setText( "Hello, world!" ); //$NON-NLS-1$
		
		// Save the design and close it.
		
		design.saveAs( "sample.rptdesign" ); //$NON-NLS-1$
		design.close( );
		
		// We're done!
	}
	
	static void buildFacture( ) throws IOException, SemanticException
	{
		// Create a session handle. This is used to manage all open designs.
		// Your app need create the session only once.
		SessionHandle session = DesignEngine.newSession( null );
		
		// Create a new report design.
		ReportDesignHandle design = session.createDesign( );
		
		// The element factory creates instances of the various BIRT elements.
		ElementFactory factory = design.getElementFactory( );
		
		// Create a simple master page that describes how the report will
		// appear when printed.
		//
		// Note: The report will fail to load in the BIRT designer
		// unless you create a master page.
		DesignElementHandle element = factory.newSimpleMasterPage( "Page Master" ); //$NON-NLS-1$
		design.getMasterPages( ).add( element );
		
		// Create a grid and add it to the "body" slot of the report
		// design.
		GridHandle grid = factory.newGridItem( null, 2 /* cols */, 1 /* row */ );
		design.getBody().add(grid);
		
		// Note: Set the table width to 100% to prevent the label
		// from appearing too narrow in the layout view.
		grid.setWidth( "100%" ); //$NON-NLS-1$
		
		// Get the first row.
		RowHandle row = (RowHandle) grid.getRows( ).get( 0 );
		// Create an image and add it to the first cell.
		
		ImageHandle image = factory.newImage( null );
		CellHandle cell = (CellHandle) row.getCells( ).get( 0 );
		cell.getContent( ).add( image );
		image.setURI( "http://www.eclipse.org/birt/tutorial/multichip-4.jpg" ); //$NON-NLS-1$
		
		// Create a label and add it to the second cell.
		LabelHandle label = factory.newLabel( null );
		cell = (CellHandle) row.getCells( ).get( 1 );
		cell.getContent( ).add( label );
		label.setText( "Hello, world!" ); //$NON-NLS-1$
		
		// Save the design and close it.
		design.saveAs( "sample.rptdesign" ); //$NON-NLS-1$
		design.close( );
		
		// We're done!
	}
}