package archivageepiceatestreport.actions;

import java.io.File;
import java.io.IOException;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.script.internal.element.AutoText;
import org.eclipse.birt.report.model.api.AutoTextHandle;
import org.eclipse.birt.report.model.api.CellHandle;
import org.eclipse.birt.report.model.api.DesignConfig;
import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.ElementFactory;
import org.eclipse.birt.report.model.api.GridHandle;
import org.eclipse.birt.report.model.api.IDesignEngine;
import org.eclipse.birt.report.model.api.IDesignEngineFactory;
import org.eclipse.birt.report.model.api.ImageHandle;
import org.eclipse.birt.report.model.api.LabelHandle;
import org.eclipse.birt.report.model.api.OdaDataSetHandle;
import org.eclipse.birt.report.model.api.OdaDataSourceHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.RowHandle;
import org.eclipse.birt.report.model.api.SessionHandle;
import org.eclipse.birt.report.model.api.SimpleMasterPageHandle;
import org.eclipse.birt.report.model.api.SlotHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException; 
import org.eclipse.birt.report.model.api.command.ContentException;
import org.eclipse.birt.report.model.api.command.NameException;

import com.ibm.icu.util.ULocale;

/**
 * Simple BIRT Design Engine API (DEAPI) demo.
 */

public class SimpleCreate
{

 	public static void main( String[] args )
	{
		try
		{
			buildReport( );
		}
		catch ( IOException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch ( SemanticException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// This function shows how to build a very simple BIRT report with a
	// minimal set of content: a simple grid with an image and a label.
 	
	static void buildReport( ) throws IOException, SemanticException
	{
		// Create a session handle. This is used to manage all open designs.
		// Your app need create the session only once.
 		
 		//Configure the Engine and start the Platform 
		
		DesignConfig config = new DesignConfig( );

		config.setProperty("BIRT_HOME", "C:/TestReport/birt-runtime-2_2_2/ReportEngine");
		IDesignEngine engine = null;
		try{
					
			
		Platform.startup( config );
		IDesignEngineFactory factory = (IDesignEngineFactory) Platform
		.createFactoryObject( IDesignEngineFactory.EXTENSION_DESIGN_ENGINE_FACTORY );
		engine = factory.createDesignEngine( config );

		}catch( Exception ex){
			ex.printStackTrace();
		}		
				
		
		SessionHandle session = engine.newSessionHandle( ULocale.ENGLISH ) ;
		
		// Create a new report design.
		
		ReportDesignHandle design = session.createDesign( );
		
		// The element factory creates instances of the various BIRT elements.
		
		ElementFactory efactory = design.getElementFactory( );
		
		
		// Create a simple master page that describes how the report will
		// appear when printed.
		//
		// Note: The report will fail to load in the BIRT designer
		// unless you create a master page.
		
		//DesignElementHandle element = efactory.newSimpleMasterPage( "Page Master" ); 
		SimpleMasterPageHandle element = efactory.newSimpleMasterPage( "Page Master" );
		design.getMasterPages( ).add( element );
		element.setProperty("type", "a4");
		element.setStringProperty("topMargin", "1cm");
		element.setStringProperty("leftMargin", "1cm");
		element.setStringProperty("bottomMargin", "1cm");
		element.setStringProperty("rightMargin", "1cm");
		//SlotHandle slotFooter =  ((SimpleMasterPageHandle)element).getPageFooter();
		
		//DesignElementHandle footerText = efactory.newTextItem("test");
		//DesignElementHandle footerGrid = efactory.newGridItem("gridFooter", 3, 1);
		//footerGrid.setStringProperty("width","20%");
//		footerText.setProperty("contentType", "html");         
//		footerText.setStringProperty("width", "20%");
//		element.getPageFooter().add(footerText);
		//element.getPageFooter().add(footerGrid);
		
		GridHandle gridFooter = efactory.newGridItem( "gridFooter", 3 /* cols */, 1 /* row */ );
		gridFooter.setStringProperty("width", "20%");
		element.getPageFooter().add(gridFooter);
		
		RowHandle rowFooter = (RowHandle)gridFooter.getRows().get(0);
		CellHandle cellGridFooter1 = (CellHandle) rowFooter.getCells( ).get( 0 );
		LabelHandle label_1 = efactory.newLabel( null );
		AutoTextHandle autoText = efactory.newAutoText(null);
		autoText.setStringProperty("type", "page-number");
		cellGridFooter1.getContent( ).add( autoText );
		//label_1.setText( "Hello, world!" ); 
		
		CellHandle cellGridFooter2 = (CellHandle) rowFooter.getCells( ).get( 2 );
		LabelHandle label_2 = efactory.newLabel( null );
		AutoTextHandle autoText2 = efactory.newAutoText(null);
		autoText2.setStringProperty("type", "total-page");
		cellGridFooter2.getContent( ).add( autoText2 );
		
		
		
		// Create a grid and add it to the "body" slot of the report
		// design.
		
		GridHandle grid = efactory.newGridItem( null, 2 /* cols */, 600 /* row */ );
		design.getBody( ).add( grid );
		
		// Note: Set the table width to 100% to prevent the label
		// from appearing too narrow in the layout view.
		
		grid.setWidth( "100%" ); 
		

		
//		RowHandle row = (RowHandle) grid.getRows( ).get( 0 );
//		
//		// Create an image and add it to the first cell.
//		ImageHandle image = efactory.newImage( null );
//		CellHandle cell = (CellHandle) row.getCells( ).get( 0 );
//		cell.getContent( ).add( image );
//		//image.setURL( "\"urlofimage\"" ); 
//		image.setURL( "\"C:/workspace/JDK5/Images/Blue.gif\"");
//		
//		// Create a label and add it to the second cell.
//		LabelHandle label = efactory.newLabel( null );
//		cell = (CellHandle) row.getCells( ).get( 1 );
//		cell.getContent( ).add( label );
//		label.setText( "Hello, world!" ); 
		
		// Save the design and close it.
		
		design.saveAs( "c:/tmp/sample_footer.rptdesign" ); 
		design.close( );
		System.out.println("Finished");
		
		// We're done!
	}
 }

