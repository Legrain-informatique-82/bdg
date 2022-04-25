package fr.legrain.edition.actions;

import java.io.IOException;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.model.api.DesignConfig;
import org.eclipse.birt.report.model.api.ElementFactory;
import org.eclipse.birt.report.model.api.IDesignEngine;
import org.eclipse.birt.report.model.api.IDesignEngineFactory;
import org.eclipse.birt.report.model.api.MasterPageHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.SessionHandle;
import org.eclipse.birt.report.model.api.StructureFactory;
import org.eclipse.birt.report.model.api.activity.SemanticException;

import com.ibm.icu.util.ULocale;

public class EditionAddProprityToEdition {
	
	private String pathFileEdition = null;
	private ReportDesignHandle designHandle = null;
	private ElementFactory designFactory = null;//for understand look P242
	private StructureFactory structFactory = null;
	//Configure the Engine and start the Platform
	private DesignConfig designConfig = new DesignConfig( );
	private EngineConfig engineConfig = new EngineConfig(); 
	private IDesignEngine engine = null;
	private SessionHandle session = null;
	private IDesignEngineFactory factory = null;
	
	/** test variable**/
	String pathImageEdtion = "/home/lee/Bureau/images/motif_fond.jpg";

	public EditionAddProprityToEdition() {
		super();
	}

	public void initialiserDesignReportConfig(String pathFileEdtion){
		try {
			Platform.startup( designConfig );
			factory = (IDesignEngineFactory)Platform
			               .createFactoryObject(IDesignEngineFactory.EXTENSION_DESIGN_ENGINE_FACTORY );
			engine = factory.createDesignEngine( designConfig );
			session = engine.newSessionHandle( ULocale.FRENCH ) ;
			designHandle = session.openDesign(pathFileEdtion);
			designFactory = designHandle.getElementFactory( );
			
		} catch (BirtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addCommentaire(String commentaire){
		try {
			designHandle.setProperty(ConstEdition.COMMENTS,commentaire);
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addImageBackGround(String pathImage){

		MasterPageHandle masterPageHandle = designHandle
				.findMasterPage(ConstEdition.NAME_MASTER_PAGE);
		try {
			masterPageHandle.setStringProperty("backgroundImage", pathImage);
			masterPageHandle.setStringProperty("backgroundRepeat", "no-repeat");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addSupportImageSvg(String pathFileEdition){
		
		

	}
	public void savsAsDesignHandle(){
		try {
			designHandle.saveAs(pathFileEdition);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		designHandle.close();
	}
	public String getPathFileEdition() {
		return pathFileEdition;
	}

	public void setPathFileEdition(String pathFileEdition) {
		this.pathFileEdition = pathFileEdition;
	}
	

}
