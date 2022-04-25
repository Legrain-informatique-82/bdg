package fr.legrain.document.etat.prelevement.editors;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.document.etat.prelevement.Activator;
import fr.legrain.document.etat.prelevement.controllers.FormPageControllerPrelevement;
import fr.legrain.document.etat.prelevement.ecrans.PaFormPagePrelevement;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrRetourEditeur;

/**
 * Form editor pour les états d'échéances de documents
 * @author nicolas
 *
 */

public class FormEditorEtat extends FormEditor implements ILgrEditor, ILgrRetourEditeur, /*IEditorArticlesExtension,*/IPartListener{

	static Logger logger = Logger.getLogger(FormEditorEtat.class.getName());
	
	public static final String ID = "fr.legrain.document.etat.devis";
	
	private String iconPageTB = "/icons/application_view_tile.png";

	private TaArticle masterEntity = null;
	private TaArticleDAO masterDAO = null;
	
	// FormPage principal
	private PaFormPagePrelevement defaultFormPage = null;
	private Map<PaFormPagePrelevement,FormPageControllerPrelevement> listeController = new HashMap<PaFormPagePrelevement,FormPageControllerPrelevement>();
			
	// Constructeur par défaut
	public FormEditorEtat() {
		super();
	}
	
	@Override
	protected void addPages() {
	}
		
	@Override
	public boolean canLeaveThePage() {
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
