package fr.legrain.libLgrBirt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
//import org.eclipse.birt.data.oda.util.manifest.DtpManifestExplorer;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderContext;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.ReportEngine;

import fr.legrain.silentPdfPrint.LgrSpooler;

public class BirtUtil {
	
	static Logger logger = Logger.getLogger(BirtUtil.class.getName());
	
	static private ReportEngine engine = null;
	static public final int PDF_FORMAT = 1;
	static public final int HTML_FORMAT = 2;
	static private HashMap appContextEdition = null;
	
	static public void startReportEngine() {
		if(engine==null) {
			EngineConfig config = new EngineConfig();
			//config.setEngineHome( "C:/birt-runtime-2_0_1/birt-runtime-2_0_1/Report Engine" );
			
//			System.setProperty( "RUN_UNDER_ECLIPSE", "true" );
			//config.setConfigurationVariable("RUN_UNDER_ECLIPSE", "true");
//			config.setProperty("RUN_UNDER_ECLIPSE", "true");
			//DtpManifestExplorer.getInstance( ).getExtensionManifests( );
			
			//Create the report engine
			engine = new ReportEngine(config);
			
			
			
		}
		engine.getConfig().getAppContext().putAll(appContextEdition);
	}
	
	static public void destroyReportEngine() {
		engine.destroy();
		engine = null;
	}
	
	static public OutputStream renderReportToStream(String reportFile, Map reportParameters, int format) {
		
		HTMLRenderOption options;
		OutputStream os = null;
		try {
			//Set rendering options - such as file or stream output, 
			//output format, whether it is embeddable, etc
			options = new HTMLRenderOption();
			
			os = new ByteArrayOutputStream();
			options.setOutputStream(os);
			
			//run the report and destroy the engine
			IRunAndRenderTask task = prepareReport(reportFile,reportParameters,format);
			
			if(format == PDF_FORMAT)
				options.setOutputFormat("pdf");
			else if(format == HTML_FORMAT)
				options.setOutputFormat("html");
			
			task.setRenderOption(options);
			
			task.run();
			
			return os;
			
		} catch (EngineException e) {
			logger.error("",e);
			return null;
		} finally{
			try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	static public String renderReportToFile(String reportFile, String outputFileName, Map reportParameters, int format) throws EngineException {
		try {
			//Set rendering options - such as file or stream output, 
			//output format, whether it is embeddable, etc
			HTMLRenderOption options = new HTMLRenderOption();
			
			OutputStream os = new ByteArrayOutputStream();
			options.setOutputStream(os);
			
			//run the report and destroy the engine
			IRunAndRenderTask task = prepareReport(reportFile,reportParameters,format);
			
			if(format == PDF_FORMAT)
				options.setOutputFormat("pdf");
			else if(format == HTML_FORMAT)
				options.setOutputFormat("html");
			
			options.setOutputFileName(outputFileName);
			
			task.setRenderOption(options);
			
			task.run();
			
			return outputFileName;
			
		} catch (EngineException e) {
			logger.error("",e);
			return null;
		}
	}
	
	private static IRunAndRenderTask prepareReport(String reportFile, Map<String,String> reportParameters, int format) throws EngineException {
		//Open a report design - use design to modify design, retrieve embedded images etc. 
		IReportRunnable design = engine.openReportDesign(reportFile);
		
		//Create task to run the report - use the task to execute and run the report,
		IRunAndRenderTask task = engine.createRunAndRenderTask(design); 
		
		
		for (String reportParam : reportParameters.keySet()) {
			task.setParameterValue(reportParam,reportParameters.get(reportParam));
		}
		
		//Set Render context to handle url and image locataions
		HTMLRenderContext renderContext = new HTMLRenderContext();
		renderContext.setImageDirectory("image");
		HashMap contextMap = new HashMap();
		contextMap.put( EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT, renderContext );
		task.setAppContext( contextMap );
		
		return task;
	}
	
	//EXEMPLE
	private static void executeReport() throws EngineException {
		//Engine Configuration - set and get temp dir, BIRT home, Servlet context
		EngineConfig config = new EngineConfig();
		//config.setEngineHome( "C:/birt-runtime-2_0_1/birt-runtime-2_0_1/Report Engine" );
		
//		System.setProperty( "RUN_UNDER_ECLIPSE", "true" );
		//config.setConfigurationVariable("RUN_UNDER_ECLIPSE", "true");
		config.setProperty("RUN_UNDER_ECLIPSE", "true");
		//DtpManifestExplorer.getInstance( ).getExtensionManifests( );
		
		//Create the report engine
		ReportEngine engine = new ReportEngine( config );
		
		//Open a report design - use design to modify design, retrieve embedded images etc. 
		IReportRunnable design = engine.openReportDesign("C:/Projet/Java/Eclipse/GestionCommerciale/LibLgrBirt/src/libLgrBirt/test/test.rptdesign");
		
		//Create task to run the report - use the task to execute and run the report,
		IRunAndRenderTask task = engine.createRunAndRenderTask(design); 
		
		task.setParameterValue("paramID_FACTURE","1");
		task.setParameterValue("paramUrlJDBC","jdbc:firebirdsql://localhost/C:/Projet/Java/Eclipse/GestionCommerciale/@Run/lgrWorkspace/dossier/Bd/GEST_COM.FDB");
		
		//Set Render context to handle url and image locataions
		HTMLRenderContext renderContext = new HTMLRenderContext();
		renderContext.setImageDirectory("image");
		HashMap contextMap = new HashMap();
		contextMap.put( EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT, renderContext );
		task.setAppContext( contextMap );
		
		//Set rendering options - such as file or stream output, 
		//output format, whether it is embeddable, etc
		HTMLRenderOption options = new HTMLRenderOption();
		
		//options.setOutputFileName("c:/aaaaaaaaaa.html");
		//options.setOutputFormat("html");
		
		//options.setOutputFileName("c:/aaaaaaaaaa.pdf");
		
		OutputStream os = new ByteArrayOutputStream();
		options.setOutputStream(os);
		
		options.setOutputFormat("pdf");
		task.setRenderOption(options);
		
		//run the report and destroy the engine
		task.run();
		
		engine.destroy();
		
		LgrSpooler spooler = LgrSpooler.getInstance();
		//spooler.add("c:/aaaaaaaaaa.pdf");
		spooler.add(os);
		//spooler.add("c:/run.pdf");
		//spooler.add("c:/a.pdf");
		//spooler.print();
		spooler.print(false);
		
	}

	static public HashMap getAppContextEdition() {
		return appContextEdition;
	}

	static public void setAppContextEdition(HashMap appContextEdition) {
		BirtUtil.appContextEdition = appContextEdition;
	}
}
