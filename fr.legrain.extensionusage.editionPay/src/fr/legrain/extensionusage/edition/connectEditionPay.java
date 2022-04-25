package fr.legrain.extensionusage.edition;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import fr.legrain.edition.actions.AnalyseFileReport;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.extensionprovider.IConnect;


public class connectEditionPay implements IConnect {

	static Logger logger = Logger.getLogger(connectEditionPay.class.getName());
	private String PLUGIN_ID = "fr.legrain.extensionusage.editionPay";
	
	
	public connectEditionPay() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void connect() {
		// TODO Auto-generated method stub
	}

	@Override
	public Map getInfosEdititon(String namePlugin,String nameEntity,AnalyseFileReport analyseFileReport) {
		// TODO Auto-generated method stub
		Map<String, String> mapEdition = new HashMap<String, String>();
		
		Bundle bundle = Platform.getBundle(PLUGIN_ID);
		URL pluginURL = null;
		String pathFolderReport = null;
		try {
			//pluginURL = Platform.resolve(bundle.getEntry("/"));
			pluginURL = bundle.getEntry("/");
			URL urlFolderEdition = FileLocator.find(bundle, new Path("/"), null);
			urlFolderEdition = FileLocator.toFileURL(urlFolderEdition);
			
			URI uriReportFile = new URI("file", urlFolderEdition.getAuthority(),
					urlFolderEdition.getPath(), urlFolderEdition.getQuery(), null);
			File folderEdition = new File(uriReportFile);
			
			pathFolderReport = folderEdition.toString()+ConstEdition.FOLDER_EDITION+namePlugin+"/"+nameEntity; 
			
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.error("", e);
		}
		
		return arrayPathFileParamEtiquette(pathFolderReport,analyseFileReport);
	}
	public String  getPathFolderEdititonPay(String namePlugin,String nameEntity,AnalyseFileReport analyseFileReport) {
		// TODO Auto-generated method stub
		
		String pathFolderReport = null ;
		Bundle bundle = Platform.getBundle(PLUGIN_ID);
		URL pluginURL = null;
		try {
			//pluginURL = Platform.resolve(bundle.getEntry("/"));
			pluginURL = bundle.getEntry("/");
			URL urlFolderEdition = FileLocator.find(bundle, new Path("/"), null);
			urlFolderEdition = FileLocator.toFileURL(urlFolderEdition);
			
			URI uriReportFile = new URI("file", urlFolderEdition.getAuthority(),
					urlFolderEdition.getPath(), urlFolderEdition.getQuery(), null);
			File folderEdition = new File(uriReportFile);
			
			pathFolderReport = folderEdition.toString()+ConstEdition.FOLDER_EDITION+namePlugin+"/"+nameEntity; 
			
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error("", e);
		}
		
		return pathFolderReport;
	}
	
	public Map<String, String> arrayPathFileParamEtiquette(String pathFolder,AnalyseFileReport analyseFileReport){
		
		Map<String, String> mapEdition = new HashMap<String, String>();
		
		File folder = new File(pathFolder);
		FilenameFilter filter = new TypeFileReportFilter();
		File[] arrayFileReport = folder.listFiles(filter);
		
		for (int i = 0; i < arrayFileReport.length; i++) {
			String pathFileReport = arrayFileReport[i].toString();
			analyseFileReport.initializeBuildDesignReportConfig(pathFileReport);
			String CommentaireEdition = analyseFileReport.findCommentsReport();	
			mapEdition.put(CommentaireEdition, pathFileReport);
		}
		return mapEdition;
	}
	private class TypeFileReportFilter implements FilenameFilter{

		@Override
		public boolean accept(File dir, String nameFile) {
			// TODO Auto-generated method stub
			//return false;
			
			return nameFile.endsWith(ConstEdition.TYPE_FILE_REPORT);
			//return nameFile.endsWith(".dat");
		}
		
	}

}
