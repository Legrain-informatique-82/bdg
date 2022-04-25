package fr.legrain.edition.actions;

import org.apache.log4j.Logger;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.model.api.DesignConfig;
import org.eclipse.birt.report.model.api.DesignFileException;
import org.eclipse.birt.report.model.api.IDesignEngine;
import org.eclipse.birt.report.model.api.IDesignEngineFactory;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.SessionHandle;

import com.ibm.icu.util.ULocale;

/**
 * Lecture des informations dans un fichier d'édition birt (.rptdesign)
 * 
 * ajouter les attributes pour l'edition 
 */
public class AnalyseFileReport {
	
	static Logger logger = Logger.getLogger(AnalyseFileReport.class.getName());

	private ReportDesignHandle designHandle = null;
	//Configure the Engine and start the Platform
	private DesignConfig config = new DesignConfig( );
	private IDesignEngine engine = null;
	private SessionHandle session = null;
	private IDesignEngineFactory factory = null;
	
	/**
	 * Prépare à la lecture du fichier
	 * @param pathFileEdition - chemin du fichier d'édition
	 */
	public void initializeBuildDesignReportConfig(String pathFileEdition){
		
		try{
			Platform.startup( config );
			factory = (IDesignEngineFactory)Platform
			.createFactoryObject( IDesignEngineFactory.EXTENSION_DESIGN_ENGINE_FACTORY );
			engine = factory.createDesignEngine( config );
			
		}catch( Exception ex){
			logger.error("", ex);
		}
		session = engine.newSessionHandle( ULocale.FRENCH ) ;
		
		try {
			designHandle = session.openDesign(pathFileEdition);
		} catch (DesignFileException e) {
			logger.error("", e);
		}
	}
	public void closeDesignReportConfig(){
		designHandle.close();
	}
	
	/**
	 * @return - la propriété commentaire de l'edition
	 */
	public String findCommentsReport(){
		return (String)designHandle.getProperty("comments");
		
	}
	
	public String findDescription(){
//		System.out.println("=====>"+designHandle.getDisplayDescription());
		return (String)designHandle.getDisplayDescription();
		
	}
	
	public String findOrientationReport(){
		return (String)designHandle.getMasterPages().get(0).getProperty("orientation");
	}
	
	public String findSubjectReport(){
		return (String)designHandle.getProperty("subject");
	}
	
	public String findTitleReport(){
		return (String)designHandle.getProperty("title");
	}
	public String findDisplayNameReport(){
		return (String)designHandle.getProperty("displayName");
	}
	
	
}
