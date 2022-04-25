package fr.legrain.extensionprovider;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;

import fr.legrain.edition.actions.AnalyseFileReport;

public class Application {
	
	static Logger logger = Logger.getLogger(Application.class.getName());
			
	private static final String ICONNECT_ID = "fr.legrain.extensionponit.connects";
	private Map<String, String> mapFileReport = new HashMap<String, String>();
	
	private String pathFolderEditionPay = null;

	public void askFonction(String namePlugin,String nameEntity,AnalyseFileReport analyseFileReport){
		runGreeterExtension(namePlugin,nameEntity,analyseFileReport);
	}

	private void runGreeterExtension(final String namePlugin,final String nameEntity,
								     final AnalyseFileReport analyseFileReport) {
		try {
			IConfigurationElement[] config = Platform.getExtensionRegistry()
					.getConfigurationElementsFor(ICONNECT_ID);
			for (IConfigurationElement e : config) {
				final Object o = e.createExecutableExtension("class");
				if (o instanceof IConnect) {
					ISafeRunnable runnable = new ISafeRunnable() {
						@Override
						public void handleException(Throwable exception) {
							System.out.println("Exception in client");
						}

						@Override
						public void run() throws Exception {
							//((IConnect) o).connect();
							mapFileReport.clear();
							mapFileReport = ((IConnect) o).getInfosEdititon(namePlugin,nameEntity,analyseFileReport);
							pathFolderEditionPay = ((IConnect)o).getPathFolderEdititonPay(namePlugin, nameEntity, analyseFileReport);
						}
					};
					SafeRunner.run(runnable);
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		}
	}
	public Map<String, String> getMapFileReport() {
		return mapFileReport;
	}

	public void setMapFileReport(Map<String, String> mapFileReport) {
		this.mapFileReport = mapFileReport;
	}

	public String getPathFolderEditionPay() {
		return pathFolderEditionPay;
	}

	public void setPathFolderEditionPay(String pathFolderEditionPay) {
		this.pathFolderEditionPay = pathFolderEditionPay;
	}
	
}
