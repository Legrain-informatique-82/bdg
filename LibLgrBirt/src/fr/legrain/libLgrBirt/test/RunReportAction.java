package fr.legrain.libLgrBirt.test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderContext;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.ReportEngine;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import fr.legrain.silentPdfPrint.LgrSpooler;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class RunReportAction implements IWorkbenchWindowActionDelegate {
	
	
	//static Logger logger = Logger.getLogger(RunReportAction.class.getName());
	public static final String BIRT_HOME   = "C:/Eclipse 3.1/eclipse/plugins/org.eclipse.birt.report.viewer_2.0.0/birt";
	//public static final String REPORT_FILE = "C:/Documents and Settings/nicolas/Bureau/test.rptdesign";
	public static final String REPORT_FILE = "C:/Projet/Java/Eclipse/GestionCommerciale/testBirt/testSampleDB.rptdesign";
	//public static final String REPORT_FILE = "C:/Projet/Java/Eclipse/GestionCommerciale/testBirt/simple.rptdesign";
	//public static final String REPORT_FILE = "/tiers.rptdesign";
	
	
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public RunReportAction() {
	}
	
	static void executeReport() throws EngineException {
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
		
//		try {
//			String osName = System.getProperty("os.name" );
//			//FOR WINDOWS 95 AND 98 USE COMMAND.COM
//			if(osName.equals("Windows 95") || osName.equals("Windows 98")){		
//				//Runtime.getRuntime().exec(
//				//		"command.com /C start \"C:/Program Files/Adobe/Acrobat 7.0/Reader/acrord32\" /p /h" + "c:/aaaaaaaaaa.pdf");
//			}
//			//FOR WINDOWS NT/XP/2000 USE CMD.EXE
//			else {
//				Runtime.getRuntime().exec("cmd.exe start /C \"C:/Program Files/Adobe/Acrobat 7.0/Reader/acrord32\" /p /h" + "c:/aaaaaaaaaa.pdf");
//			//	Runtime.getRuntime().exec("java org.pdfbox.PrintPDF " + "c:/aaaaaaaaaa.pdf");
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
		
	}

	public void run(IAction action) {
		try {
			executeReport();
		} catch (EngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		
	}	

}