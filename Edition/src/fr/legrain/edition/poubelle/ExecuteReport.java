package fr.legrain.edition.poubelle;

import java.util.HashMap;

import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderContext;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.ReportEngine;
import org.eclipse.birt.report.engine.api.script.IReportContext;

public class ExecuteReport {

	static void executeReport() throws EngineException
	{
		//Engine Configuration - set and get temp dir, BIRT home, Servlet context
		EngineConfig config = new EngineConfig();
		config.setEngineHome("C:/TestReport/birt-runtime-2_2_2/ReportEngine" );

		//Create the report engine
		ReportEngine engine = new ReportEngine( config );
		IReportContext d;
		

		//Open a report design - use design to modify design, retrieve embedded images etc.
		IReportRunnable design = engine.openReportDesign("C:/workspace/ArchivageEpiceaTestReport/reportDynamique.rptdesign");
		
		String  author = ( String ) design.getProperty( IReportRunnable.AUTHOR );
		System.out.println(author);
		//Create task to run the report - use the task to execute and run the report,
		IRunAndRenderTask task = engine.createRunAndRenderTask(design);

		//Set Render context to handle url and image locataions
		//PDFRenderContext renderContext = new PDFRenderContext();
		HTMLRenderContext renderContext = new HTMLRenderContext();
		
		renderContext.setImageDirectory("image");
		HashMap contextMap = new HashMap();
		contextMap.put( EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT, renderContext );
		task.setAppContext( contextMap );

		//Set rendering options - such as file or stream output,
		//output format, whether it is embeddable, etc
		
		//RenderOptionBase options = new RenderOptionBase();
		
		HTMLRenderOption options = new HTMLRenderOption();
		options.setOutputFileName("c:/TestReport/customers.pdf");
		options.setOutputFormat("pdf");
		task.setRenderOption(options);
		

		//run the report and destroy the engine
		task.run();

		engine.destroy();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try
		{
			executeReport( );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

}