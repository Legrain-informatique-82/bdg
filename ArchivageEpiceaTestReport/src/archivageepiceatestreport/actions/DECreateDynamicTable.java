package archivageepiceatestreport.actions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList; 
import java.util.HashMap;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.HTMLRenderContext;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderContext;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.api.ReportEngine;
import org.eclipse.birt.report.model.api.CellHandle;
import org.eclipse.birt.report.model.api.DataItemHandle;
import org.eclipse.birt.report.model.api.DataSourceHandle;
import org.eclipse.birt.report.model.api.DesignConfig; 
import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.DesignEngine;
import org.eclipse.birt.report.model.api.ElementFactory;
import org.eclipse.birt.report.model.api.GridHandle;
import org.eclipse.birt.report.model.api.IDesignEngine;
import org.eclipse.birt.report.model.api.IDesignEngineFactory;
import org.eclipse.birt.report.model.api.ImageHandle;
import org.eclipse.birt.report.model.api.LabelHandle;
import org.eclipse.birt.report.model.api.ModuleOption;
import org.eclipse.birt.report.model.api.OdaDataSetHandle;
import org.eclipse.birt.report.model.api.OdaDataSourceHandle;
import org.eclipse.birt.report.model.api.PropertyHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.RowHandle;
import org.eclipse.birt.report.model.api.ScalarParameterHandle;
import org.eclipse.birt.report.model.api.SessionHandle;
import org.eclipse.birt.report.model.api.StructureFactory;
import org.eclipse.birt.report.model.api.StyleHandle;
import org.eclipse.birt.report.model.api.TableHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.elements.DesignChoiceConstants;
import org.eclipse.birt.report.model.api.elements.structures.ComputedColumn;
import org.eclipse.birt.report.model.api.elements.structures.FilterCondition;

import com.ibm.icu.util.ULocale;

/**
 * Dynamic Table BIRT Design Engine API (DEAPI) demo.
 */

public class DECreateDynamicTable
{
	
	
	ReportDesignHandle designHandle = null;
	ElementFactory designFactory = null;//for understand look P242
	StructureFactory structFactory = null;	
 
	static String querySql = "select E.COMPTE,E.TIERS,E.LIBELLE,E.\"DATE\",E.DEBIT,E.CREDIT,P.REFERENCE from ECRITURES E join PIECES P on (E.ID_PIECE=P.ID) WHERE cast(E.COMPTE  AS integer)>=6700000";
	static String queryProcedureSql = "select * from PROCEDURE_ONGLET_ECRITURE("+querySql+")";
	public static void main( String[] args )
	{
		try
		{
			DECreateDynamicTable de = new DECreateDynamicTable();
			ArrayList al = new ArrayList();
//			al.add("OFFICECODE");
//			al.add("CITY");
//			al.add("COUNTRY");
			/**
			 * for reportEcriture.rptdesign
			 */
			
			al.add("COMPTE");
			al.add("TIERS");
			al.add("LIBELLE");
			al.add("DATE");
			al.add("DEBIT");
			al.add("CREDIT");
			al.add("REFERENCE");
			
			//de.buildReport(al, "From PROCEDURE_ONGLET_ECRITURE(?)" );
			de.buildReport(al, querySql );
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

	void buildDataSource( ) throws SemanticException
	{

		OdaDataSourceHandle dsHandle = designFactory.newOdaDataSource(
				"Data Source", "org.eclipse.birt.report.data.oda.jdbc" );
		
		
//		dsHandle.setProperty( "odaDriverClass",
//				"org.eclipse.birt.report.data.oda.sampledb.Driver" );
//		dsHandle.setProperty( "odaURL", "jdbc:classicmodels:sampledb" );
//		dsHandle.setProperty( "odaUser", "ClassicModels" );
//		dsHandle.setProperty( "odaPassword", "" );
		/**
		 * for reportEcriture.rptdesign
		 * select E.COMPTE,E.TIERS,E.LIBELLE,E."DATE",E.DEBIT,E.CREDIT,P.REFERENCE from ECRITURES E 
		 * join PIECES P on (E.ID_PIECE=P.ID) 
		 * WHERE cast(E.COMPTE  AS integer)>= '6700000'
		 */
		dsHandle.setProperty( "odaDriverClass","org.firebirdsql.jdbc.FBDriver" );
		dsHandle.setProperty( "odaURL", "jdbc:firebirdsql://localhost/C:/Archivage_epicea/archepi/JQJQJQJJJJ.FDB" );
		dsHandle.setProperty( "odaUser", "admin" );
		dsHandle.setProperty( "odaPassword", "###_PASSWORD_FB_BDG_###" );
		//dsHandle.s

		designHandle.getDataSources( ).add( dsHandle );

	}

	void buildDataSet(ArrayList cols, String fromClause ) throws SemanticException
	{

		OdaDataSetHandle dsHandle = designFactory.newOdaDataSet( "ds",
				"org.eclipse.birt.report.data.oda.jdbc.JdbcSelectDataSet" );
		dsHandle.setDataSource( "Data Source" );
		String qry = "Select ";
		for( int i=0; i < cols.size(); i++){
			qry += " " + cols.get(i);
			if( i != (cols.size() -1) ){
				qry += ",";
			}
			
		}
		qry += " " + fromClause;
		
		//dsHandle.setQueryText( qry );
		dsHandle.setQueryText( querySql );
		designHandle.getDataSets( ).add( dsHandle );
	}
	void buildReport(ArrayList cols, String fromClause ) throws IOException, SemanticException
	{


		//Configure the Engine and start the Platform
		DesignConfig config = new DesignConfig( );

		config.setProperty("BIRT_HOME", "C:/TestReport/birt-runtime-2_2_2/ReportEngine");
 		IDesignEngine engine = null;
 		
// 		DesignEngine Dengine = null;
// 		Dengine = new DesignEngine(config);
 		
		try{


			Platform.startup( config );
			IDesignEngineFactory factory = (IDesignEngineFactory) Platform
			.createFactoryObject( IDesignEngineFactory.EXTENSION_DESIGN_ENGINE_FACTORY );
			engine = factory.createDesignEngine( config );

		}catch( Exception ex){
 			ex.printStackTrace();
		}		


		SessionHandle session = engine.newSessionHandle( ULocale.FRENCH ) ;
		
	

		try{
			//open a design or a template
			//designHandle = session.openDesign("C:/workspace/ArchivageEpiceaTestReport/reportDynamique.rptdesign");
			File file = new File("c:/tmp/sample.rptdesign");
			if(file.exists()){
				file.delete();
			}
			//designHandle = session.openDesign("c:/tmp/sample.rptdesign");
			/*
			 * for understand , look page 242
			 */
			designHandle = session.createDesign("c:/tmp/sample.rptdesign");
			designFactory = designHandle.getElementFactory( );
 
			buildDataSource();
 			buildDataSet(cols, fromClause);
 
			TableHandle table = designFactory.newTableItem( "table", cols.size() );
			table.setWidth( "100%" );
			
			table.setProperty(StyleHandle.BORDER_BOTTOM_STYLE_PROP,DesignChoiceConstants.LINE_STYLE_SOLID);
			table.setProperty(StyleHandle.BORDER_LEFT_STYLE_PROP,DesignChoiceConstants.LINE_STYLE_SOLID );
			table.setProperty(StyleHandle.BORDER_TOP_STYLE_PROP,DesignChoiceConstants.LINE_STYLE_SOLID );
			table.setProperty(StyleHandle.BORDER_RIGHT_STYLE_PROP,DesignChoiceConstants.LINE_STYLE_SOLID );
 			table.setDataSet( designHandle.findDataSet( "ds" ) );
 
			 
   			PropertyHandle computedSet = table.getColumnBindings( ); 
			ComputedColumn  cs1 = null;

			for( int i=0; i < cols.size(); i++){
				cs1 = StructureFactory.createComputedColumn();
 				cs1.setName((String)cols.get(i));
				cs1.setExpression("dataSetRow[\"" + (String)cols.get(i) + "\"]");
				System.out.println("---"+cs1.getReferencableProperty());
				computedSet.addItem(cs1);
				
			}
		
			
			// table header
			
			RowHandle tableheader = (RowHandle) table.getHeader( ).get( 0 );
			tableheader.setProperty(StyleHandle.BORDER_BOTTOM_STYLE_PROP,DesignChoiceConstants.LINE_STYLE_SOLID );

		 
			
 			for( int i=0; i < cols.size(); i++){
 				LabelHandle label1 = designFactory.newLabel( (String)cols.get(i) );	
 				label1.setText((String)cols.get(i));
 				CellHandle cell = (CellHandle) tableheader.getCells( ).get( i );
 				cell.setProperty(StyleHandle.BORDER_LEFT_STYLE_PROP,DesignChoiceConstants.LINE_STYLE_SOLID);
 				cell.getContent( ).add( label1 );
			}							
 	
 			// table detail
 			RowHandle tabledetail = (RowHandle) table.getDetail( ).get( 0 );
 			tabledetail.setProperty(StyleHandle.TEXT_ALIGN_PROP,DesignChoiceConstants.TEXT_ALIGN_RIGHT);
 			tabledetail.setProperty(StyleHandle.BORDER_BOTTOM_STYLE_PROP,DesignChoiceConstants.LINE_STYLE_SOLID );
 			for( int i=0; i < cols.size(); i++){
				CellHandle cell = (CellHandle) tabledetail.getCells( ).get( i );
  				DataItemHandle data = designFactory.newDataItem( "data_"+(String)cols.get(i) );
  				data.setResultSetColumn( (String)cols.get(i));
   				cell.getContent( ).add( data );
 			}
 
 			// table footer
 			RowHandle tabbleFooter;
 			for(int i=0 ;i<1;i++){
 				tabbleFooter = (RowHandle)table.getFooter().get(i);
 				for( int j=0; j < cols.size()-1; j++){
 					CellHandle cell = (CellHandle) tabbleFooter.getCells( ).get( j );
 					cell.drop();
 				}
 				LabelHandle labelTextFooter = designFactory.newLabel(null);
 				labelTextFooter.setText("7777");
 				CellHandle cell = (CellHandle) tabbleFooter.getCells( ).get( 6 );
 				cell.setColumnSpan(7);
 				cell.getContent( ).add( labelTextFooter );
 				//}
 				
 			
 			}
 			designHandle.getBody( ).add( table );
 
 			/**
 			 * add Gird
 			 * 
 			 * DesignElementHandle All report items derive from this class
 			 */
 			DesignElementHandle element = designFactory.newSimpleMasterPage( "Page Master" ); 
 			designHandle.getMasterPages( ).add( element );
 			
 			GridHandle grid = designFactory.newGridItem( null, 2 /* cols */, 1 /* row */ );
 			designHandle.getBody( ).add( grid );
 			
 			grid.setWidth( "100%" );
 			RowHandle row = (RowHandle) grid.getRows( ).get( 0 );
 			
 			// Create an image and add it to the first cell.
 			ImageHandle image = designFactory.newImage( null );
 			CellHandle cell = (CellHandle) row.getCells( ).get( 0 );
 			cell.getContent( ).add( image );
 			//image.setURL( "\"urlofimage\"" ); 
 			image.setURL( "\"C:/workspace/JDK5/Images/Blue.gif\"");
 			
 			// Create a label and add it to the second cell.
 			
 			LabelHandle label = designFactory.newLabel( null );
 			cell = (CellHandle) row.getCells( ).get( 1 );
 			cell.getContent( ).add( label );
 			label.setText( "Hello, world!" );
 			
			// Save the design and close it. 

			designHandle.saveAs( "c:/tmp/sample.rptdesign" ); //$NON-NLS-1$
			/**
			 * for make a file of pdf
			 */
			EngineConfig configPDF = new EngineConfig();
			configPDF.setEngineHome("C:/TestReport/birt-runtime-2_2_2/ReportEngine" );
			ReportEngine enginePDF = new ReportEngine( configPDF );
			IReportRunnable design = enginePDF.openReportDesign("c:/tmp/sample.rptdesign");

			//Create task to run the report - use the task to execute and run the report,
			IRunAndRenderTask task = enginePDF.createRunAndRenderTask(design);

			//Set Render context to handle url and image locataions
			//HTMLRenderContext renderContext = new HTMLRenderContext();
			PDFRenderContext renderContext = new PDFRenderContext();
			renderContext.setEmbededFont(true);
			HashMap contextMap = new HashMap();
			//contextMap.put( EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT, renderContext );
			contextMap.put( EngineConstants.APPCONTEXT_PDF_RENDER_CONTEXT, renderContext );
			task.setAppContext( contextMap );

			//Set rendering options - such as file or stream output,
			//output format, whether it is embeddable, etc
//			RenderOptionBase optionsPDF = new RenderOptionBase();
			
			//HTMLRenderOption options = new HTMLRenderOption();
			PDFRenderOption options = new PDFRenderOption();
			File filePDF = new File("c:/TestReport/TestPDFEcriture.pdf");
			if(filePDF.exists()){
				filePDF.delete();
			}
			options.setOutputFileName("c:/TestReport/TestPDFEcriture.pdf");
			options.setOutputFormat("pdf");
			task.setRenderOption(options);
			

			//run the report and destroy the engine
			task.run();

			enginePDF.destroy();
			/**
			 * an orther method for make PDF
			 */
			designHandle.close( );
			//session.closeAll(true);
 			System.out.println("Finished");
		}catch (Exception e){
			e.printStackTrace();
		}		

	}
 }

