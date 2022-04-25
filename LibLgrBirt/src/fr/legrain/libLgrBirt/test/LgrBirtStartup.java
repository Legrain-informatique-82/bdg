package fr.legrain.libLgrBirt.test;

import org.apache.log4j.Logger;
import org.eclipse.birt.report.viewer.utilities.WebappAccessor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.PlatformUI;

import fr.legrain.libLgrBirt.WebViewerUtil;

public class LgrBirtStartup implements IStartup {
	
	static Logger logger = Logger.getLogger(LgrBirtStartup.class.getName());

	public void earlyStartup() {
		WebViewerUtil.startWebViewer();
		//preChargementBirt();
		
		if(logger.isDebugEnabled())
			logger.debug("LgrBirtStartup - earlyStartup");
	}
	
	private void preChargementBirt() {
		//BirtUtil.startReportEngine();
		//WebViewerUtil.startWebViewer();
		//Shell shellBrowser;
		//org.eclipse.swt.browser.Browser swtBrowser;
		String url = "http://"+WebappAccessor.getHost()+":"+WebappAccessor.getPort(WebViewerUtil.C_NOM_WEBAPP)+"/birt/";
		//url = "file:///C:/Documents and Settings/nicolas/Bureau/uu.pdf";
		url = "http://127.0.0.1:"+WebappAccessor.getPort(WebViewerUtil.C_NOM_WEBAPP)+"/birt/run?__report=c:/Projet/Java/Eclipse/GestionCommerciale/Facture/report/facture.rptdesign";
		url = "http://127.0.0.1:"+WebappAccessor.getPort(WebViewerUtil.C_NOM_WEBAPP)+"/birt/run?__report=c:/Projet/Java/Eclipse/GestionCommerciale/Facture/report/facture.rptdesign&paramID_FACTURE=1&paramUrlJDBC=jdbc:firebirdsql:localhost:C:/Projet/Java/Eclipse/GestionCommerciale/@Run/lgrWorkspace/dossier/Bd/Gest_Com.fdb&capital=null&ape=null&siret=null&rcs=null&nomEntreprise=null&__format=pdf";
		url = "http://127.0.0.1:"+WebappAccessor.getPort(WebViewerUtil.C_NOM_WEBAPP)+"/birt/frameset?__report=test.rptdesign&sample=my+parameter";
		url = "http://127.0.0.1:"+WebappAccessor.getPort(WebViewerUtil.C_NOM_WEBAPP)+"/birt/run?__report=test.rptdesign&sample=my+parameter&__format=pdf";
		final String finalURL = url;
		
//		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
//			public void run() {
//				try {
//					PlatformUI.getWorkbench().getBrowserSupport().createBrowser
//					(IWorkbenchBrowserSupport.AS_EXTERNAL, "test_prechargement", 
//							"Prévisualisation de la facture " ,
//							"Prévisualisation de la facture ").openURL(new URL(finalURL));
//				} catch (PartInitException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (MalformedURLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		});
		
		 PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			public void run() {
				Shell shellBrowser = new Shell();
				org.eclipse.swt.browser.Browser swtBrowser = new org.eclipse.swt.browser.Browser(shellBrowser,SWT.NULL);
				try {			
					swtBrowser.setUrl(finalURL);
				} catch (Exception e) {
					logger.error("",e);
				} finally {
					swtBrowser.dispose();
					shellBrowser.dispose();
				}
			}
		});
	}

}
