package fr.legrain.javascript.editors;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Text;

public class CKEditor extends AbstractLgrJavascriptEditor {

	public CKEditor(Browser browser) {
		super(browser);
	}

	public CKEditor(Browser browser, Text textComponent) {
		super(browser, textComponent);
	}

	public String getBaseURL() {
		String url = null;
		try {
		//URL urlEclipse = FileLocator.find(Platform.getBundle(Activator.PLUGIN_ID),new Path("/ckeditor/_samples/lgr.html"),null);
		URL urlEclipse = FileLocator.find(Platform.getBundle(Activator.PLUGIN_ID),new Path("/ckeditor4.2/samples/lgr.html"),null);
		
		URL urlReportFile = FileLocator.toFileURL(urlEclipse);
		
		URI uriReportFile = new URI("file", urlReportFile.getAuthority(),
				urlReportFile.getPath(), urlReportFile.getQuery(), null);
		File reportFileEdition = new File(uriReportFile);
		url = reportFileEdition.getAbsolutePath();
		
		//si serveur jetty
		//url = "http://localhost:8585/runJetty/ckeditor4.2/samples/lgr.html";
		
//		url = "http://localhost:8585/runJetty/tinymce4.0.5/lgr.html";
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}
}
