package fr.legrain.articles.statistiques.editors;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.editor.IEditorArticlesExtension;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrRetourEditeur;

//public class EditorNoteTiers extends JPALgrEditorPart implements ILgrEditor, ILgrRetourEditeur {
public class FormEditorStatistiquesArticles extends FormEditor implements ILgrEditor, ILgrRetourEditeur, IEditorArticlesExtension,IPartListener{

	static Logger logger = Logger.getLogger(FormEditorStatistiquesArticles.class.getName());
	
	public static final String ID = "fr.legrain.tiers.statistiques.editors.FormEditorStatistiquesArticles";
	
	private TaArticle masterEntity = null;
	private TaArticleDAO masterDAO = null;
	
	
	private DefaultFormPage defaultFormPage = null;
//	private EssaisFormPage essaisFormPage = null;
	
	private Map<FormPage,IFormPageArticlesContoller> listeController = new HashMap<FormPage,IFormPageArticlesContoller>();
	
	
	
	public FormEditorStatistiquesArticles() {
		super();
	}
	
	@Override
	protected void addPages() {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().addPartListener(this);
			defaultFormPage = new DefaultFormPage(this, DefaultFormPage.id, DefaultFormPage.title);
//			essaisFormPage = new EssaisFormPage(this, EssaisFormPage.id, EssaisFormPage.title);
			
			IFormPageArticlesContoller controllerDefaut = new DefaultFormPageController(defaultFormPage);
			
			listeController.put(defaultFormPage, controllerDefaut);

			this.addPage(defaultFormPage);
//			this.addPage(essaisFormPage);
		} catch (PartInitException e) {
			logger.error("",e);
		}
	}

	/** zhaolin 08/04/2010 **/
	@Override
	public boolean canLeaveThePage() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public JPABaseControllerSWTStandard getController() {
		return null;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	public void setEnabled(boolean enabled) {
		
	}

	@Override
	public boolean validate() {
		return false;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		
	}

	@Override
	public void doSaveAs() {
		
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public Object retour() {
		return null;
	}

	@Override
	public void utiliseRetour(Object r) {
		
	}


	public TaArticle getMasterEntity() {
		return masterEntity;
	}

	/* (non-Javadoc)
	 * @see fr.legrain.tiers.statistiques.editors.IEditorStat#setMasterEntity(fr.legrain.tiers.dao.TaTiers)
	 */
	public void setMasterEntity(TaArticle masterEntity) {
		this.masterEntity = masterEntity;
		
//		//l'ordre est important : le DAO en premier
//		for (FormPage page : listeController.keySet()) {
//			((IFormPageTiersContoller)listeController.get(page)).setMasterDAO(getMasterDAO());
//		}
//		
//		for (FormPage page : listeController.keySet()) {
//			((IFormPageTiersContoller)listeController.get(page)).setMasterEntity(getMasterEntity());
//		}
	}

	/* (non-Javadoc)
	 * @see fr.legrain.tiers.statistiques.editors.IEditorStat#getMasterDAO()
	 */
	public TaArticleDAO getMasterDAO() {
		return masterDAO;
	}

	/* (non-Javadoc)
	 * @see fr.legrain.tiers.statistiques.editors.IEditorStat#setMasterDAO(fr.legrain.tiers.dao.TaTiersDAO)
	 */
	public void setMasterDAO(TaArticleDAO masterDAO) {
		this.masterDAO = masterDAO;
	}

/* ************************************************************************************ */


	@Override
	public void activate() {
		//l'ordre est important : le DAO en premier
		for (FormPage page : listeController.keySet()) {
			((IFormPageArticlesContoller)listeController.get(page)).setMasterDAO(getMasterDAO());
		}
		
		for (FormPage page : listeController.keySet()) {
			((IFormPageArticlesContoller)listeController.get(page)).setMasterEntity(getMasterEntity());
		}
	}

	@Override
	public void partActivated(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void partClosed(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void partDeactivated(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void partOpened(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		
	}

}
