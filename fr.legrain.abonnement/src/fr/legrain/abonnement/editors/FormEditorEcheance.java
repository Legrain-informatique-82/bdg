package fr.legrain.abonnement.editors;

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

import fr.legrain.abonnement.pluginAbonnement;
import fr.legrain.abonnement.controllers.echeance.FormPageControllerPrincipalEcheance;
import fr.legrain.abonnement.ecrans.PaFormPageEcheance;
import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrRetourEditeur;
import fr.legrain.gestCom.librairiesEcran.workbench.lgrFormEditor;

/**
 * Form editor pour les états d'échéances de documents
 * @author nicolas
 *
 */

public class FormEditorEcheance extends lgrFormEditor implements ILgrEditor, ILgrRetourEditeur, /*IEditorArticlesExtension,*/IPartListener{

	static Logger logger = Logger.getLogger(FormEditorEcheance.class.getName());
	
	public static final String ID = "fr.legrain.abonnement.Echeance";
	
	private String iconPageTB = "/icons/logo_lgr_16.png";

	private TaArticle masterEntity = null;
	private TaArticleDAO masterDAO = null;
	
	// FormPage principal
	private PaFormPageEcheance defaultFormPage = null;
	private PaFormPageEcheance remonterFormPage = null;
	private Map<PaFormPageEcheance,FormPageControllerPrincipalEcheance> listeController = new HashMap<PaFormPageEcheance,FormPageControllerPrincipalEcheance>();
			
	// Constructeur par défaut
	public FormEditorEcheance() {
		super();
	}
	
	@Override
	protected void addPages() {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().addPartListener(this);
			// Création du formPage
			defaultFormPage = new PaFormPageEcheance(this, PaFormPageEcheance.id, PaFormPageEcheance.title);
			remonterFormPage = new PaFormPageEcheance(this, PaFormPageEcheance.id, PaFormPageEcheance.titleRemonter);
			// Création du Controller
			FormPageControllerPrincipalEcheance defautController = new FormPageControllerPrincipalEcheance(defaultFormPage);
			FormPageControllerPrincipalEcheance remonterController = new FormPageControllerPrincipalEcheance(remonterFormPage);
			
			// Liaison entre le controller et le formPage et ajout à la liste
			liaisonControllerFormPage(defautController,defaultFormPage);
			liaisonControllerFormPage(remonterController,remonterFormPage);
			this.addPage(defaultFormPage);
			this.addPage(remonterFormPage);
			setPageImage(0, pluginAbonnement.getImageDescriptor(iconPageTB).createImage());
			setPageImage(1, pluginAbonnement.getImageDescriptor(iconPageTB).createImage());
			remonterController.setSuppressionUniquement(true);
			//addEditor((PaFormPageEcheance)o,(FormPageControllerPrincipalEcheance)c,editorName,icon);
			//createContributors();
			
		} catch (PartInitException e) {
			logger.error("",e);
		}
	}
	
	/**
	 * Méthode permettant la liaison entre le controller et le formPage et ajout à la liste
	 * @param leController le controller à lier
	 * @param leFormPage le formpage à lier
	 */
	private void liaisonControllerFormPage(FormPageControllerPrincipalEcheance leController, PaFormPageEcheance leFormPage) {
		leFormPage.setControllerPage((FormPageControllerPrincipalEcheance)leController);
		
		listeController.put(leFormPage, leController);
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


//	@Override
//	public void activate() {
////		//l'ordre est important : le DAO en premier
////		for (FormPage page : listeController.keySet()) {
////			((IFormPageArticlesController)listeController.get(page)).setMasterDAO(getMasterDAO());
////		}
////		
////		for (FormPage page : listeController.keySet()) {
////			((IFormPageArticlesController)listeController.get(page)).setMasterEntity(getMasterEntity());
////		}
//	}

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
	
	private void createContributors() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint("fr.legrain.document.etat.facture.editorPageEtatDocument"); //$NON-NLS-1$
		if (point != null) {
			//Map<Integer, List<IMultiPaneEditorContributor>> seq2contributors = new HashMap<Integer, List<IMultiPaneEditorContributor>>();
			ImageDescriptor icon = null;
			IExtension[] extensions = point.getExtensions();
			for (int i = 0; i < extensions.length; i++) {
				IConfigurationElement confElements[] = extensions[i].getConfigurationElements();
				for (int jj = 0; jj < confElements.length; jj++) {
					try {
						String editorClass = confElements[jj].getAttribute("editorClass");//$NON-NLS-1$
						String editorName = confElements[jj].getAttribute("editorLabel");//$NON-NLS-1$
						String editorIcon = confElements[jj].getAttribute("editorIcon");//$NON-NLS-1$
						String contributorName = confElements[jj].getContributor().getName();
						
						if (editorClass == null || editorName == null)
							continue;

						Object o = confElements[jj].createExecutableExtension("editorClass");
						Object c = confElements[jj].createExecutableExtension("editorController");
						if(editorIcon!=null) {
							icon = AbstractUIPlugin.imageDescriptorFromPlugin(contributorName, editorIcon);
						}
						addEditor((PaFormPageEcheance)o,(FormPageControllerPrincipalEcheance)c,editorName,icon);
						
//						// test if the contributor applies to this editor
//						boolean isApplicable = false;
//						Class<?> subject = this.getClass();
//						while (subject != EditorPart.class && !isApplicable) {
//							isApplicable = editorClass.equals(subject.getName());
//							subject = subject.getSuperclass();
//						}
					} catch (Exception e) {
						logger.error("",e);
					}
				}
			}
		}
	}
	
	private void addEditor(PaFormPageEcheance e, FormPageControllerPrincipalEcheance c, String label) {
		 addEditor(e,c,label,null);
	}
	
	private void addEditor(PaFormPageEcheance e, FormPageControllerPrincipalEcheance controllerPrincipal, String label, ImageDescriptor icon) {
		String labelPage = label;		
//		listePageEditeur.add(e);
		
		e.initialize(this);
		
		int index = 0;
		try {
			index = addPage(e, getEditorInput());
		} catch (PartInitException e1) {
			logger.error("",e1);
		}
		setPageText(index, labelPage);
		setPageImage(index, icon.createImage());
		
		controllerPrincipal.initVue(e);
		
		liaisonControllerFormPage(controllerPrincipal,e);
		
		controllerPrincipal.init();
		
//		if (e instanceof IEditorArticlesExtension) {
//			if(listeEditeurExtension==null) {
//				listeEditeurExtension = new ArrayList<IEditorArticlesExtension>();
//			}
//			listeEditeurExtension.add((IEditorArticlesExtension)e);
//			((IEditorArticlesExtension)e).setMasterDAO(masterDAO);
//			((IEditorArticlesExtension)e).setMasterEntity(masterEntity);
//		}
	}

	@Override
	public String getTitle() {
		return "Gestion des échéances d'abonnement";
	}

	@Override
	protected String getID() {
		// TODO Auto-generated method stub
		return ID;
	}
}
