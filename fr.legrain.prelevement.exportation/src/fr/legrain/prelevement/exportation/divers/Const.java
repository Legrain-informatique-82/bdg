package fr.legrain.prelevement.exportation.divers;

import java.io.File;
import java.net.URI;
import java.net.URL;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import fr.legrain.prelevement.exportation.pluginPrelevement;



public class Const {
	static Logger logger = Logger.getLogger(Const.class.getName());

	static final public String MODEL_WORD_1 = "modelLettre.doc";
	static final public String MODEL_WORD_2 = "modelLettre2.doc";
	static final public String MODEL_WORD_3 = "modelLettre3.doc";
	static final public String MODEL_OPENOFFICE_1 = "modelLettre.odt";
	static final public String MODEL_OPENOFFICE_2 = "modelLettre2.odt";
	static final public String MODEL_OPENOFFICE_3 = "modelLettre3.odt";
	static final public String CORRESPONDANCE = "motcle.properties";
	
	
	public static String pathRepertoireModelesRelances(){
		Bundle bundleEditions =Platform.getBundle(pluginPrelevement.PLUGIN_ID);
		if(bundleEditions==null)return null;
		URL urlReportFile;
		try {

			urlReportFile = FileLocator.find(bundleEditions,new Path("ModelesRelance"),null);
			if(urlReportFile!=null){
				urlReportFile = FileLocator.toFileURL(urlReportFile);

				URI uriReportFile = new URI("file", urlReportFile.getAuthority(),
						urlReportFile.getPath(), urlReportFile.getQuery(), null);
				File reportFileEdition = new File(uriReportFile);
				return reportFileEdition.getAbsolutePath();
			}else{
				/*
				 * il n'a y pas fragement pour client 
				 */
				return null;
			}
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}		
	}
}
