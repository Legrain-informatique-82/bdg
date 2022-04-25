package fr.legrain.javascript.editors;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.mortbay.jetty.webapp.WebAppContext;

//import org.eclipse.jetty.webapp.WebAppContext;

public class AppContextBuilder {
	
	private WebAppContext webAppContext;
	
	public WebAppContext buildWebAppContext(){
		
		URL urlEclipse = FileLocator.find(Platform.getBundle(Activator.PLUGIN_ID),new Path("/"),null);
		
		URL urlReportFile;
		String url = null;
		
			try {
				urlReportFile = FileLocator.toFileURL(urlEclipse);
			
		
		URI uriReportFile;

			uriReportFile = new URI("file", urlReportFile.getAuthority(),
					urlReportFile.getPath(), urlReportFile.getQuery(), null);
		
		File reportFileEdition = new File(uriReportFile);
		 url = reportFileEdition.getAbsolutePath();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		webAppContext = new WebAppContext();
		webAppContext.setDescriptor(webAppContext + "/WEB-INF/web.xml");
		//webAppContext.setDescriptor(null);
		webAppContext.setResourceBase(url);
		//webAppContext.setResourceBase(".");
		webAppContext.setContextPath("/runJetty");
		return webAppContext;
	}
}  
