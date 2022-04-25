package fr.legrain.gestCom.librairiesEcran.workbench;

import java.io.File;
import java.net.URI;
import java.net.URL;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Bundle;

public class LgrFileBundleLocator {

	static Logger logger = Logger.getLogger(LgrFileBundleLocator.class.getName());

	public static File bundleToFile(Bundle bundle, String relativePath){
		if(bundle==null) return null;
		URL urlReportFile = null;
		try {

			urlReportFile = FileLocator.find(bundle,new Path(relativePath),null);

			urlReportFile = FileLocator.toFileURL(urlReportFile);

			URI uriReportFile = new URI("file", urlReportFile.getAuthority(),
					urlReportFile.getPath(), urlReportFile.getQuery(), null);
			File reportFileEdition = new File(uriReportFile);
			return reportFileEdition;

		} catch (Exception e) {
			logger.error("",e);
			return null;
		}		
	}
}
