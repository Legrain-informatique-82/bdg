package fr.legrain.gestionCommerciale.extensionPoints;

import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

public class ExtensionPoinsClass {
	
	static Logger logger = Logger.getLogger(ExtensionPoinsClass.class.getName());
	
	String TITLE_URL = "Bureau de Gestion"; 
	
	public void createContributors() {
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = extensionRegistry.getExtensionPoint(
										"GestionCommerciale.GestionCommercialeExtentionPoints");
		String SITE_URL = "";
		if(extensionPoint != null){
//			System.err.println("GestionCommerciale.GestionCommercialeExtentionPonits");
			IExtension[] extensions = extensionPoint.getExtensions();
			if(extensions.length>0) {
				for (int i = 0; i < extensions.length; i++) {
					IConfigurationElement configurationElement[] = extensions[i].getConfigurationElements();
					for (int j = 0; j < configurationElement.length; j++) {
						SITE_URL = configurationElement[j].getAttribute("site_url");
					}
				}
				openUrl(SITE_URL);
			}
		}
	}

	public void openUrl(String site_url ) {
		
		
		final String finalURL = site_url;
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				try {
					PlatformUI.getWorkbench().getBrowserSupport()
					.createBrowser(
							IWorkbenchBrowserSupport.AS_EDITOR,
							"myId",
							TITLE_URL,//finalURL,
							finalURL
					).openURL(new URL(finalURL));

				} catch (PartInitException e) {
					logger.error("",e);
				} catch (MalformedURLException e) {
					logger.error("",e);

				}
			}	
		});
	}
	public boolean isInternetReachable(String site_url){
		 boolean flag = false;
		 try {
			 URL url = new URL(site_url); 
			//open a connection to that source 
             HttpURLConnection urlConnect = (HttpURLConnection)url.openConnection(); 
           /*trying to retrieve data from the source. 
            * If there is no connection, this line will fail
            */ 
             Object objData = urlConnect.getContent();
             flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return flag;
	}
	 
}
