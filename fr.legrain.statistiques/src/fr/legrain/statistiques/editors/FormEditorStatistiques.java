package fr.legrain.statistiques.editors;

import java.util.HashMap;
import java.util.List;
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
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.editor.IEditorArticlesExtension;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrRetourEditeur;
import fr.legrain.statistiques.Activator;
import fr.legrain.statistiques.controllers.FormPageControllerPrincipal;
import fr.legrain.statistiques.controllers.IFormPageArticlesController;
import fr.legrain.statistiques.ecrans.PaFormPage;



/**
 * Form editor du tableau de bord des statistiques
 * @author nicolas²
 *
 */

public class FormEditorStatistiques extends FormEditor implements ILgrEditor, ILgrRetourEditeur, IEditorArticlesExtension,IPartListener{

	static Logger logger = Logger.getLogger(FormEditorStatistiques.class.getName());
	
	// Déclaration publiques
	public static final String ID = "fr.legrain.statistiques.editors.FormEditorStatistiques";
	
	String iconPageTB = "/icons/application_view_tile.png";
//	String iconPageDevis = "/icons/devis.png";
//	String iconPageCommande = "/icons/devis.png";
//	String iconPageAvoir = "/icons/table.png";
//	String iconPageApporteur = "/icons/creditcards.png";
//	String iconPageAcompte = "/icons/money.png";
//	String iconPageProforma = "/icons/devis.png";
//	String iconPageLivraison = "/icons/lorry.png";

	
	// Déclarations privées
	private TaArticle masterEntity = null;
	private TaArticleDAO masterDAO = null;
	// FormPage principal
	private PaFormPage defaultFormPage = null;
//	private PaFormPageDevis ongletDevis = null;
//	private PaFormPageCommandes ongletCommandes = null;
//	private PaFormPageApporteur ongletApporteur = null;
//	private PaFormPageAvoir ongletAvoir = null;
//	private PaFormPageAcompte ongletAcompte = null;
//	private PaFormPageProforma ongletProforma = null;
//	private PaFormPageLivraisons ongletLivraisons = null;
	private Map<PaFormPage,FormPageControllerPrincipal> listeController = new HashMap<PaFormPage,FormPageControllerPrincipal>();
	
	private List<IEditorArticlesExtension> listeEditeurExtension = null;
		
	
	// Constructeur par défaut
	public FormEditorStatistiques() {
		super();
	}
	
	@Override
	protected void addPages() {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().addPartListener(this);
			// Création du formPage
			defaultFormPage = new PaFormPage(this, PaFormPage.id, PaFormPage.title);
//			ongletDevis = new PaFormPageDevis(this, PaFormPageDevis.id, PaFormPageDevis.title);
//			ongletCommandes = new PaFormPageCommandes(this, PaFormPageCommandes.id, PaFormPageCommandes.title);
//			ongletApporteur = new PaFormPageApporteur(this, PaFormPageApporteur.id, PaFormPageApporteur.title);
//			ongletProforma = new PaFormPageProforma(this, PaFormPageProforma.id, PaFormPageProforma.title);
//			ongletLivraisons = new PaFormPageLivraisons(this, PaFormPageLivraisons.id, PaFormPageLivraisons.title);
//			ongletAvoir = new PaFormPageAvoir(this, PaFormPageAvoir.id, PaFormPageAvoir.title);
//			ongletAcompte = new PaFormPageAcompte(this, PaFormPageAcompte.id, PaFormPageAcompte.title);
			
			
			// Création du Controller
			FormPageControllerPrincipal defautController = new FormPageControllerPrincipal(defaultFormPage);
//			FormPageControllerPrincipal devisController = new FormPageControllerDevis(ongletDevis);
//			FormPageControllerPrincipal commandesController = new FormPageControllerCommandes(ongletCommandes);
//			FormPageControllerPrincipal apporteurController = new FormPageControllerApporteur(ongletApporteur);
//			FormPageControllerPrincipal proformaController = new FormPageControllerProforma(ongletProforma);
//			FormPageControllerPrincipal livraisonsController = new FormPageControllerLivraisons(ongletLivraisons);
//			FormPageControllerPrincipal avoirController = new FormPageControllerAvoir(ongletAvoir);
//			FormPageControllerPrincipal acompteController = new FormPageControllerAcompte(ongletAcompte);
			
			
			// Liaison entre le controller et le formPage et ajout à la liste
			LinknAdd(defautController,defaultFormPage);
//			LinknAdd(devisController,ongletDevis);
//			LinknAdd(commandesController,ongletCommandes);
//			LinknAdd(apporteurController,ongletApporteur);
//			LinknAdd(proformaController,ongletProforma);
//			LinknAdd(livraisonsController,ongletLivraisons);
//			LinknAdd(avoirController,ongletAvoir);
//			LinknAdd(acompteController,ongletAcompte);			

			this.addPage(defaultFormPage);
//			this.addPage(ongletDevis);
//			this.addPage(ongletCommandes);
//			this.addPage(ongletApporteur);
//			this.addPage(ongletProforma);
//			this.addPage(ongletLivraisons);
//			this.addPage(ongletAvoir);
//			this.addPage(ongletAcompte);
						
			setPageImage(0, Activator.getImageDescriptor(iconPageTB).createImage());
//			setPageImage(1, Activator.getImageDescriptor(iconPageDevis).createImage());
//			setPageImage(2, Activator.getImageDescriptor(iconPageCommande).createImage());
//			setPageImage(3, Activator.getImageDescriptor(iconPageApporteur).createImage());
//			setPageImage(4, Activator.getImageDescriptor(iconPageProforma).createImage());
//			setPageImage(5, Activator.getImageDescriptor(iconPageLivraison).createImage());
//			setPageImage(6, Activator.getImageDescriptor(iconPageAvoir).createImage());
//			setPageImage(7, Activator.getImageDescriptor(iconPageAcompte).createImage());
			
			createContributors();
			
		} catch (PartInitException e) {
			logger.error("",e);
		}
	}
	
	/**
	 * Méthode permettant la liaison entre le controller et le formPage et ajout à la liste
	 * @param leController le controller à lier
	 * @param leFormPage le formpage à lier
	 */
	private void LinknAdd(FormPageControllerPrincipal leController, PaFormPage leFormPage) {
		leFormPage.setControllerPage((FormPageControllerPrincipal)leController);
		
		listeController.put(leFormPage, leController);
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
//		//l'ordre est important : le DAO en premier
//		for (FormPage page : listeController.keySet()) {
//			((IFormPageArticlesController)listeController.get(page)).setMasterDAO(getMasterDAO());
//		}
//		
//		for (FormPage page : listeController.keySet()) {
//			((IFormPageArticlesController)listeController.get(page)).setMasterEntity(getMasterEntity());
//		}
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
	
	private void createContributors() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint("fr.legrain.statistiques.editorPageTableauDeBord"); //$NON-NLS-1$
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
						addEditor((PaFormPage)o,(FormPageControllerPrincipal)c,editorName,icon);
						
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
	
	private void addEditor(PaFormPage e, FormPageControllerPrincipal c, String label) {
		 addEditor(e,c,label,null);
	}
	
	private void addEditor(PaFormPage e, FormPageControllerPrincipal c, String label, ImageDescriptor icon) {
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
		
		c.initVue(e);
		
		LinknAdd(c,e);
		
		c.appel();
		
		
		
//		if (e instanceof IEditorArticlesExtension) {
//			if(listeEditeurExtension==null) {
//				listeEditeurExtension = new ArrayList<IEditorArticlesExtension>();
//			}
//			listeEditeurExtension.add((IEditorArticlesExtension)e);
//			((IEditorArticlesExtension)e).setMasterDAO(masterDAO);
//			((IEditorArticlesExtension)e).setMasterEntity(masterEntity);
//		}
	}

}
