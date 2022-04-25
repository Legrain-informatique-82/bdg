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

public class TinyMCE extends AbstractLgrJavascriptEditor {

	public TinyMCE(Browser browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	public TinyMCE(Browser browser, Text textComponent) {
		super(browser, textComponent);
		// TODO Auto-generated constructor stub
	}

	public String getBaseURL() {
		String url = null;
		try {
//		url = "/donnees/Projet/Java/Eclipse/GestionCommerciale/fr.legrain.tinymce/tinymce/index.html";
		URL urlEclipse = FileLocator.find(Platform.getBundle(Activator.PLUGIN_ID),new Path("/tinymce/index.html"),null);
		
		URL urlReportFile;
		
			urlReportFile = FileLocator.toFileURL(urlEclipse);
		
		URI uriReportFile = new URI("file", urlReportFile.getAuthority(),
				urlReportFile.getPath(), urlReportFile.getQuery(), null);
		File reportFileEdition = new File(uriReportFile);
		url = reportFileEdition.getAbsolutePath();
		
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
