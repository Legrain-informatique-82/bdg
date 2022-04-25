package fr.legrain.libLgrBirt.test;

import org.eclipse.birt.report.viewer.utilities.WebappAccessor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import fr.legrain.libLgrBirt.WebViewerUtil;

public class PreviewBirtAction implements IWorkbenchWindowActionDelegate {
	
	//static Logger logger = Logger.getLogger(PreviewBirtAction.class.getName());	
	
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public PreviewBirtAction() {
	}

	public void run(IAction action) {
		try {
//			WebappManager.start("birt" , "org.eclipse.birt.report.viewer", new Path("/birt"));
//			String url = "http://"+WebappManager.getHost()+":"+WebappManager.getPort()+"/birt/";
//			logger.debug( WebappManager.getHost() + " - " + WebappManager.getPort() );
			
			WebappAccessor.start("/birt" , "org.eclipse.birt.report.viewer", new Path("/"));
			/*logger.debug*/ System.out.println( WebappAccessor.getHost() + " - " + WebappAccessor.getPort(WebViewerUtil.C_NOM_WEBAPP) );
			String url = "http://"+WebappAccessor.getHost()+":"+WebappAccessor.getPort(WebViewerUtil.C_NOM_WEBAPP)+"/birt/";
			
			   System.setProperty( "RUN_UNDER_ECLIPSE", "true" );
			   //DtpManifestExplorer.getInstance( ).getExtensionManifests( );
			  // WebViewer.display("C:/Projet/Java/Eclipse/GestionCommerciale/testBirt/testSampleDB.rptdesign", WebViewer.HTML,false); 
			
			
		//	String url = "http://localhost:8080/birt/";
			
			//BrowserManager.getInstance().createBrowser(false).displayURL("http://www.google.fr");
			//EmbeddedBrowser birtEmbeddedBrowser = new EmbeddedBrowser();
			//IBrowser b = BrowserManager.getInstance().createBrowser(false);
			
			//url+="run?__report=";
			url+="frameset?__report=";
			
			url+="C:/Projet/Java/Eclipse/GestionCommerciale/LibLgrBirt/src/libLgrBirt/test/";
			//logger.debug(TestBirtPlugin.getDefault().find(new Path("/")).getPath());
			//url+="tiers.rptdesign";
			//url+="facture.rptdesign";
			url+="test.rptdesign";
			//url+="tiersv2.rptdesign";
			//url+="new_report.rptdesign";
			//url+="new_report_1.rptdesign";
			//url+="testSampleDB.rptdesign";
			url+="&paramID_FACTURE=1";
			url+="&paramUrlJDBC=jdbc:firebirdsql://localhost/C:/Projet/Java/Eclipse/GestionCommerciale/@Run/lgrWorkspace/dossier/Bd/GEST_COM.FDB";
			
			url+="&__format=html";
			
			//b.displayURL(url);
			//birtEmbeddedBrowser.displayUrl(url);
			
			/*logger.debug*/ System.out.println("URL : "+url);
			//CustomBrowser birtCustomBrowser = new CustomBrowser();
			//WebViewer.display("C:/Projet/Java/Eclipse/GestionCommerciale/testBirt/new_report.rptdesign", WebViewer.HTML,false);
			//WebViewer.display("C:/Projet/Java/Eclipse/GestionCommerciale/testBirt/tiers.rptdesign", WebViewer.HTML,false);	
			org.eclipse.birt.report.viewer.browsers.BrowserManager.getInstance().createBrowser(false).displayURL(url);
			//org.eclipse.birt.report.viewer.
			//org.eclipse.birt.report.viewer.browsers.custom.
			
		//} catch (CoreException e) {
		//	logger.error(e);
		} catch (Exception e) {
			//logger.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

}