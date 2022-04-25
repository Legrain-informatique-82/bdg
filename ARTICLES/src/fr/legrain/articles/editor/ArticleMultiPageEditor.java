package fr.legrain.articles.editor;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISaveablePart2;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import fr.legrain.articles.ArticlesPlugin;
import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.ecran.ParamAfficheActionArticle;
import fr.legrain.articles.ecran.ParamAfficheCatalogueWeb;
import fr.legrain.articles.ecran.ParamAfficheCategorieArticle;
import fr.legrain.articles.ecran.ParamAfficheCommentairesArticle;
import fr.legrain.articles.ecran.ParamAfficheConditionnementArticle;
import fr.legrain.articles.ecran.ParamAfficheFournisseurArticle;
import fr.legrain.articles.ecran.ParamAfficheImageArticle;
import fr.legrain.articles.ecran.ParamAfficheLabelArticle;
import fr.legrain.articles.ecran.ParamAfficheNoteArticle;
import fr.legrain.articles.ecran.ParamAffichePrix;
import fr.legrain.articles.ecran.ParamAfficheRapportUnite;
import fr.legrain.articles.ecran.ParamAfficheTArticle;
import fr.legrain.articles.ecran.SWTPaActionArticleController;
import fr.legrain.articles.ecran.SWTPaArticlesController;
import fr.legrain.articles.ecran.SWTPaCatalogueWebController;
import fr.legrain.articles.ecran.SWTPaCategorieController;
import fr.legrain.articles.ecran.SWTPaCommentairesArticleController;
import fr.legrain.articles.ecran.SWTPaConditionnementController;
import fr.legrain.articles.ecran.SWTPaFournisseurController;
import fr.legrain.articles.ecran.SWTPaImageArticleController;
import fr.legrain.articles.ecran.SWTPaLabelController;
import fr.legrain.articles.ecran.SWTPaNoteArticleController;
import fr.legrain.articles.ecran.SWTPaPrixController;
import fr.legrain.articles.ecran.SWTPaRapportUniteController;
//import fr.legrain.articles.ecran.SWTPaTypeArticleController;
import fr.legrain.gestCom.librairiesEcran.swt.DeclencheCommandeControllerEvent;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.AnnuleToutEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDePageEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDeSelectionEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementMasterEntityEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.IAnnuleToutListener;
import fr.legrain.gestCom.librairiesEcran.workbench.IChangementDePageListener;
import fr.legrain.gestCom.librairiesEcran.workbench.IChangementDeSelectionListener;
import fr.legrain.gestCom.librairiesEcran.workbench.IChangementMasterEntityListener;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrRetourEditeur;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.JPABdLgr;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.ModeObjet;


/**
 * An example showing how to create a multi-page editor.
 * This example has 3 pages:
 * <ul>
 * <li>page 0 contains a nested text editor.
 * <li>page 1 allows you to change the font used in page 2
 * <li>page 2 shows the words in page 0 in sorted order
 * </ul>
 */
public class ArticleMultiPageEditor extends AbstractLgrMultiPageEditor implements ILgrRetourEditeur, IChangementDePageListener,IChangementMasterEntityListener ,
IChangementDeSelectionListener, IAnnuleToutListener{
//public class MultiPageEditor extends FormEditor {
//public class MultiPageEditor extends SharedHeaderFormEditor {
	
	public static final String ID_EDITOR = "fr.legrain.editor.article.multi"; //$NON-NLS-1$
	static Logger logger = Logger.getLogger(ArticleMultiPageEditor.class.getName());
	
	private ILgrEditor editeurCourant;
	private int currentPage;
	private TaArticle masterEntity = null;
	private TaArticleDAO masterDAO = null;
	private EntityManager em = null;//new JPABdLgr().getEntityManager();
	
	private EditorArticle editorArticle = null;
	private EditorPrix editorPrix = null;
	private EditorCommentairesArticle editorCommentairesArticle = null;
	private EditorRapportUnite editorRapportUnite = null;
	private EditorNoteArticle editorNoteArticle = null;
	private EditorImageArticle editorImageArticle = null;
	private EditorCatalogueWeb editorCatalogueWeb = null;
	private EditorCategorieArticle editorCategorieArticle = null;
	private EditorLabelArticle editorLabelArticle = null;
	private EditorFournisseurArticle editorFournisseurArticle = null;
	private EditorConditionnementArticle editorConditionnementArticle = null;
	private EditorActionArticle editorActionArticle = null;
//	private EditorTArticle editorTArticle = null;
	
	private List<IEditorArticlesExtension> listeEditeurExtension = null;
	
	public static final String PREFIXE_NOM_EDITEUR = "Article";

	/**
	 * Creates a multi-page editor example.
	 */
	public ArticleMultiPageEditor() {
		super();
		setID_EDITOR(ID_EDITOR);
	}
	

//	@Override
//	protected void createHeaderContents(IManagedForm headerForm) {
//		// TODO Auto-generated method stub
//		new Composite1(headerForm.getForm().getParent(), SWT.NONE);
//	}


//	/**
//	 * Creates page 0 of the multi-page editor,
//	 * which contains a text editor.
//	 */
//	void createPage0() {
//		try {
//			editor = new SWTEditorFacture();
//			int index = addPage(editor, getEditorInput());
//			//setPageText(index, editor.getTitle());
//			setPageText(index, "TestEditor");
//		} catch (PartInitException e) {
//			ErrorDialog.openError(
//				getSite().getShell(),"Error creating nested text editor",null,e.getStatus());
//		}
//	}
	
	void createPageQueEditeur() {
		try {
			//Initialisation des pages/editeurs composant le multipage editor
			String labelPageArticle = "Articles";
			String labelPagePrix = "Prix";
			String labelPageCommentaireArticle = "Commentaires";
			String labelPageRapportUnite = "Rapport entre unités";
			String labelPageNoteArticle = "Notes";
			String labelPageImageArticle = "Images";
			String labelPageCatalogueWeb = "Catalogue Web";
			String labelPageCategorie = "Catégories";
			String labelPageLabel = "Labels";
			String labelPageFournisseur = "Fournisseurs";
			String labelPageConditionnement = "Unités personnalisées";
			String labelPageAction = "Etiquette";
			String labelPageTArticle = "Type d'article";
			
			String iconPageArticle = "/icons/package.png";
			String iconPagePrix = "/icons/money.png";
			String iconPageCommentaireArticle = "/icons/note.png";
			String iconPageRapportUnite = "/icons/unites2.png";
			String iconPageNoteArticle = "/icons/note.png";
			String iconPageImageArticle = "/icons/pictures.png";
			String iconPageCatalogueWeb = "/icons/application_view_gallery.png";
			String iconPageCategorie = "/icons/logo_lgr_16.png";
			String iconPageLabel = "/icons/tag_green.png";
			String iconPageFournisseur = "/icons/user_gray.png";
			String iconPageConditionnement = "icons/box.png";
			String iconPageTArticle = "icons/logo_lgr_16.png";
			String iconPageAction = "icons/logo_lgr_16.png";
			
			em = new TaArticleDAO().getEntityManager();
			editorArticle = new EditorArticle(this,em);
			editorPrix = new EditorPrix(this,em);
			editorCommentairesArticle = new EditorCommentairesArticle(this,em);
			editorRapportUnite = new EditorRapportUnite(this,em);
			editorNoteArticle = new EditorNoteArticle(this,em);
			editorImageArticle = new EditorImageArticle(this,em);
			editorCatalogueWeb = new EditorCatalogueWeb(this,em);
			editorCategorieArticle = new EditorCategorieArticle(this,em);
			editorLabelArticle = new EditorLabelArticle(this,em);
			editorFournisseurArticle = new EditorFournisseurArticle(this,em);
			editorConditionnementArticle = new EditorConditionnementArticle(this,em);
			editorActionArticle = new EditorActionArticle(this,em);
//			editorTArticle = new EditorTArticle(this, em);
			
			listePageEditeur.add(editorArticle);
			listePageEditeur.add(editorPrix);
			listePageEditeur.add(editorCommentairesArticle);
			listePageEditeur.add(editorRapportUnite);
			listePageEditeur.add(editorNoteArticle);
			listePageEditeur.add(editorImageArticle);
			listePageEditeur.add(editorCatalogueWeb);
			listePageEditeur.add(editorCategorieArticle);
			listePageEditeur.add(editorLabelArticle);
			listePageEditeur.add(editorFournisseurArticle);
			listePageEditeur.add(editorConditionnementArticle);
			listePageEditeur.add(editorActionArticle);
//			listePageEditeur.add(editorTArticle);
			
			int index = addPage(editorArticle, getEditorInput());
			setPageText(index, labelPageArticle);
			setPageImage(index, ArticlesPlugin.getImageDescriptor(iconPageArticle).createImage());
			index = addPage(editorPrix, getEditorInput());
			setPageText(index, labelPagePrix);
			setPageImage(index, ArticlesPlugin.getImageDescriptor(iconPagePrix).createImage());
			index = addPage(editorCommentairesArticle, getEditorInput());
			setPageText(index, labelPageCommentaireArticle);
			setPageImage(index, ArticlesPlugin.getImageDescriptor(iconPageCommentaireArticle).createImage());
			index = addPage(editorRapportUnite, getEditorInput());
			setPageText(index, labelPageRapportUnite);
			setPageImage(index, ArticlesPlugin.getImageDescriptor(iconPageRapportUnite).createImage());
			index = addPage(editorNoteArticle, getEditorInput());
			setPageText(index, labelPageNoteArticle);
			setPageImage(index, ArticlesPlugin.getImageDescriptor(iconPageNoteArticle).createImage());
			index = addPage(editorImageArticle, getEditorInput());
			setPageText(index, labelPageImageArticle);
			setPageImage(index, ArticlesPlugin.getImageDescriptor(iconPageImageArticle).createImage());
			index = addPage(editorCatalogueWeb, getEditorInput());
			setPageText(index, labelPageCatalogueWeb);
			setPageImage(index, ArticlesPlugin.getImageDescriptor(iconPageCatalogueWeb).createImage());
			index = addPage(editorCategorieArticle, getEditorInput());
			setPageText(index, labelPageCategorie);
			setPageImage(index, ArticlesPlugin.getImageDescriptor(iconPageCategorie).createImage());
			index = addPage(editorLabelArticle, getEditorInput());
			setPageText(index, labelPageLabel);
			setPageImage(index, ArticlesPlugin.getImageDescriptor(iconPageLabel).createImage());
			index = addPage(editorFournisseurArticle, getEditorInput());
			setPageText(index, labelPageFournisseur);
			setPageImage(index, ArticlesPlugin.getImageDescriptor(iconPageFournisseur).createImage());
			index = addPage(editorConditionnementArticle, getEditorInput());
			setPageText(index, labelPageConditionnement);
			setPageImage(index, ArticlesPlugin.getImageDescriptor(iconPageConditionnement).createImage());
//			index = addPage(editorTArticle,getEditorInput());
//			setPageText(index, labelPageTArticle);
			setPageImage(index, ArticlesPlugin.getImageDescriptor(iconPageTArticle).createImage());
			index = addPage(editorActionArticle,getEditorInput());
			setPageText(index, labelPageAction);
			setPageImage(index, ArticlesPlugin.getImageDescriptor(iconPageAction).createImage());
			

			
			//liaison entre la selection du controller principal et le multipage editor
			editorArticle.getController().addChangementDeSelectionListener(this);
			((SWTPaArticlesController)editorArticle.getController()).addChangementMasterEntityListener(this);
			mapEditorController.put(editorArticle, editorArticle.getController());
			mapEditorController.put(editorPrix, editorPrix.getController());
			mapEditorController.put(editorCommentairesArticle, editorCommentairesArticle.getController());
			mapEditorController.put(editorRapportUnite, editorRapportUnite.getController());
			mapEditorController.put(editorNoteArticle, editorNoteArticle.getController());
			mapEditorController.put(editorCatalogueWeb, editorCatalogueWeb.getController());
			mapEditorController.put(editorCategorieArticle, editorCategorieArticle.getController());
			mapEditorController.put(editorLabelArticle, editorLabelArticle.getController());
			mapEditorController.put(editorFournisseurArticle, editorFournisseurArticle.getController());
			mapEditorController.put(editorConditionnementArticle, editorConditionnementArticle.getController());
//			mapEditorController.put(editorTArticle, editorTArticle.getController());
			mapEditorController.put(editorActionArticle, editorActionArticle.getController());
			
//			em = new TaArticleDAO().getEntityManager();
//			editorArticle.getController().setEm(em);
//			editorPrix.getController().setEm(em);
//			editorCommentairesArticle.getController().setEm(em);
//			editorRapportUnite.getController().setEm(em);
			
			editorArticle.getController().addChangementDePageListener(this);
			editorPrix.getController().addChangementDePageListener(this);
			editorCommentairesArticle.getController().addChangementDePageListener(this);
			editorRapportUnite.getController().addChangementDePageListener(this);
			editorNoteArticle.getController().addChangementDePageListener(this);
			editorImageArticle.getController().addChangementDePageListener(this);
			editorCatalogueWeb.getController().addChangementDePageListener(this);
			editorCategorieArticle.getController().addChangementDePageListener(this);
			editorLabelArticle.getController().addChangementDePageListener(this);
			editorFournisseurArticle.getController().addChangementDePageListener(this);
			editorConditionnementArticle.getController().addChangementDePageListener(this);
//			editorTArticle.getController().addChangementDePageListener(this);
			editorActionArticle.getController().addChangementDePageListener(this);
			
			//liaison entre l'editeur principal et les editeur secondaire par injection de l'entite principale dans les editeurs secondaires
			masterDAO = ((SWTPaArticlesController)editorArticle.getController()).getDao();
			((SWTPaPrixController)editorPrix.getController()).setMasterDAO(masterDAO);
			((SWTPaCommentairesArticleController)editorCommentairesArticle.getController()).setMasterDAO(masterDAO);
			((SWTPaRapportUniteController)editorRapportUnite.getController()).setMasterDAO(masterDAO);
			((SWTPaNoteArticleController)editorNoteArticle.getController()).setMasterDAO(masterDAO);
			((SWTPaImageArticleController)editorImageArticle.getController()).setMasterDAO(masterDAO);
			((SWTPaCatalogueWebController)editorCatalogueWeb.getController()).setMasterDAO(masterDAO);
			((SWTPaCategorieController)editorCategorieArticle.getController()).setMasterDAO(masterDAO);
			((SWTPaLabelController)editorLabelArticle.getController()).setMasterDAO(masterDAO);
			((SWTPaFournisseurController)editorFournisseurArticle.getController()).setMasterDAO(masterDAO);
			((SWTPaConditionnementController)editorConditionnementArticle.getController()).setMasterDAO(masterDAO);
//			((SWTPaTypeArticleController)editorTArticle.getController()).setMasterDAO(masterDAO);
			((SWTPaActionArticleController)editorActionArticle.getController()).setMasterDAO(masterDAO);
			
			editorPrix.getController().addDeclencheCommandeControllerListener(editorArticle.getController());
			editorCommentairesArticle.getController().addDeclencheCommandeControllerListener(editorArticle.getController());
			editorRapportUnite.getController().addDeclencheCommandeControllerListener(editorArticle.getController());
			editorNoteArticle.getController().addDeclencheCommandeControllerListener(editorArticle.getController());
			editorImageArticle.getController().addDeclencheCommandeControllerListener(editorArticle.getController());
			editorCatalogueWeb.getController().addDeclencheCommandeControllerListener(editorArticle.getController());
			editorCategorieArticle.getController().addDeclencheCommandeControllerListener(editorArticle.getController());
			editorLabelArticle.getController().addDeclencheCommandeControllerListener(editorArticle.getController());
			editorFournisseurArticle.getController().addDeclencheCommandeControllerListener(editorArticle.getController());
			editorConditionnementArticle.getController().addDeclencheCommandeControllerListener(editorArticle.getController());
//			editorTArticle.getController().addDeclencheCommandeControllerListener(editorArticle.getController());
			editorActionArticle.getController().addDeclencheCommandeControllerListener(editorActionArticle.getController());

			
			
			editorArticle.getController().addAnnuleToutListener(this);
			editorPrix.getController().addAnnuleToutListener(this);
			editorCommentairesArticle.getController().addAnnuleToutListener(this);
			editorRapportUnite.getController().addAnnuleToutListener(this);
			editorNoteArticle.getController().addAnnuleToutListener(this);
			editorImageArticle.getController().addAnnuleToutListener(this);
			editorCatalogueWeb.getController().addAnnuleToutListener(this);
			editorCategorieArticle.getController().addAnnuleToutListener(this);
			editorLabelArticle.getController().addAnnuleToutListener(this);
			editorFournisseurArticle.getController().addAnnuleToutListener(this);
			editorConditionnementArticle.getController().addAnnuleToutListener(this);
//			editorTArticle.getController().addAnnuleToutListener(this);
			editorActionArticle.getController().addAnnuleToutListener(this);
			
			//Boolean affiche_onglets = DocumentPlugin.getDefault().getPreferenceStore().getBoolean(fr.legrain.document.preferences.PreferenceConstants.P_ONGLETS_DOC);
			//if(!affiche_onglets)
			//	hideTabs();		
			
			editeurCourant = editorArticle;
			//changeEditeurCourant(editorEntete);
			
			createContributors();
			if(listeEditeurExtension!=null){
				for (IEditorArticlesExtension editor : listeEditeurExtension) {
					if(((ILgrEditor)editor).getController()!=null){
						((ILgrEditor)editor).getController().addDeclencheCommandeControllerListener(editorArticle.getController());
						((ILgrEditor)editor).getController().addChangementDePageListener(this);
						((ILgrEditor)editor).getController().addAnnuleToutListener(this);
					}
				}
			}

		} catch (PartInitException e) {
			ErrorDialog.openError(
				getSite().getShell(),"Error creating nested text editor",null,e.getStatus());
		}
	}
	
	/**
 	 * If there is just one page in the multi-page editor part,
 	 * this hides the single tab at the bottom.
 	 * <!-- begin-user-doc -->
 	 * <!-- end-user-doc -->
 	 * @generated
 	 */
 	protected void hideTabs() {
		//if (getPageCount() <= 0) {
 		//	setPageText(0, ""); //$NON-NLS-1$
 			if (getContainer() instanceof CTabFolder) {
 				((CTabFolder)getContainer()).setTabHeight(0);
 			}
		//}
 	}
	
	public void changeEditeurCourant(ILgrEditor ed) {
		
		//if(editeurCourant.validate()) {
			for (ILgrEditor e : listePageEditeur) {
				if(e!=ed) e.setEnabled(false);
			}
			ed.setEnabled(true);
			editeurCourant = ed;
			setCurrentPage(listePageEditeur.indexOf(editeurCourant));
		//}
	}

	/**
	 * Creates the pages of the multi-page editor.
	 */
	protected void createPages() {
		createPageQueEditeur();
	//	createPage0();
	//	super.createPages();
	}
	
	
	/**
	 * The <code>MultiPageEditorPart</code> implementation of this 
	 * <code>IWorkbenchPart</code> method disposes all nested editors.
	 * Subclasses may extend.
	 */
	public void dispose() {
	//	ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}
	/**
	 * Saves the multi-page editor's document.
	 */
	public void doSave(IProgressMonitor monitor) {
		findMasterController().setActiveAide(true);
		getEditor(0).doSave(monitor);

	}
	
	/**
	 * Saves the multi-page editor's document as another file.
	 * Also updates the text for page 0's tab, and updates this multi-page editor's input
	 * to correspond to the nested editor's.
	 */
	public void doSaveAs() {
//		IEditorPart editor = getEditor(0);
//		editor.doSaveAs();
//		setPageText(0, editor.getTitle());
//		setInput(editor.getEditorInput());
	}
	
	/**
	 * The <code>MultiPageEditorExample</code> implementation of this method
	 * checks that the input is an instance of <code>IFileEditorInput</code>.
	 */
	public void init(IEditorSite site, IEditorInput editorInput)
		throws PartInitException {
//		if (!(editorInput instanceof IFileEditorInput))
//			throw new PartInitException("Invalid Input: Must be IFileEditorInput");
		super.init(site, editorInput);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().addPartListener(LgrPartListener.getLgrPartListener());
	}
	/* (non-Javadoc)
	 * Method declared on IEditorPart.
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}
	
	/**
	 * Calculates the contents of page 2 when the it is activated.
	 */
	protected void pageChange(int newPageIndex) {
		

		int oldPageIndex =   getCurrentPage();
		if (oldPageIndex != -1 && listePageEditeur.size() > oldPageIndex
				&& listePageEditeur.get(oldPageIndex) instanceof ILgrEditor
				&& oldPageIndex != newPageIndex) {
			// Check the old page
			ILgrEditor oldFormPage = (ILgrEditor) listePageEditeur.get(oldPageIndex);
			if (oldFormPage.canLeaveThePage() == false 
					|| masterEntity==null) {
				setActivePage(oldPageIndex);
				return;
			}
		}
		
		
		super.pageChange(newPageIndex);
		
		editeurCourant = listePageEditeur.get(newPageIndex);
		setCurrentPage(listePageEditeur.indexOf(editeurCourant));
		
		if (oldPageIndex != -1 && listePageEditeur.size() > oldPageIndex
				&& listePageEditeur.get(oldPageIndex) instanceof ILgrEditor
				&& oldPageIndex != newPageIndex) {
			if(mapEditorController.get(getActiveEditor())!=null) { //on est bien sur un editeur "classique" et non une extension particuliere
				//evite l'appel a refresh lors de l'ouverture du multipage editor (la commande n'a pas encore de handler actif)
				if ((mapEditorController.get(getActiveEditor()).getDaoStandard().getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) != 0)
						&& (mapEditorController.get(getActiveEditor()).getDaoStandard().getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) != 0)) {
					//on n'est pas en edition, ni en insertion
					masterEntity = ((SWTPaArticlesController)editorArticle.getController()).getTaArticle();
					mapEditorController.get(getActiveEditor()).executeCommand(JPABaseControllerSWTStandard.C_COMMAND_GLOBAL_REFRESH_ID);
				}
			}
		}
		
		if(getActiveEditor() instanceof IEditorArticlesExtension) {
			((IEditorArticlesExtension)getActiveEditor()).activate();
		}

	}

	public int getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;	
	}


	public Object retour() {
		// TODO Auto-generated method stub
		return null;
	}


	public void utiliseRetour(Object r) {
		// TODO Auto-generated method stub
		if(editeurCourant instanceof ILgrRetourEditeur)
			((ILgrRetourEditeur)editeurCourant).utiliseRetour(r);
	}


	@Override
	protected Composite createPageContainer(Composite parent) {
		// TODO Auto-generated method stub
		return super.createPageContainer(parent);
	}

	public ILgrEditor findSuivant() {
		if(listePageEditeur.size()>getCurrentPage()+1) {
			if(listePageEditeur.get(getCurrentPage()+1)!=null) {
				return listePageEditeur.get(getCurrentPage()+1);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	public ILgrEditor findPrecedent() {
		if(getCurrentPage()>0) {
			if(listePageEditeur.get(getCurrentPage()-1)!=null) {
				return listePageEditeur.get(getCurrentPage()-1);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	public void annuleTout(AnnuleToutEvent evt) {
//		boolean autreOngletEnModif = false;
//		for (ILgrEditor ed : mapEditorController.keySet()) {
//			//if(mapEditorController.get(ed).getDaoStandard().getModeObjet()
//			switch (mapEditorController.get(ed).getDaoStandard().getModeObjet().getMode()) {
//			case C_MO_INSERTION:
//			case C_MO_EDITION:
//				if(mapEditorController.get(ed).getSourceDeclencheCommandeController()==null)
//					autreOngletEnModif = true;
//				break;
//			}
//		}
		
//		if(autreOngletEnModif) {
			MessageDialog dialog = new MessageDialog(getEditorSite().getShell(), 
					"Annulation",
					//null, "Il existe d'autres modifications/insertions en cours pour ce tiers. Voulez vous les annuler aussi ?",
					null, "Voulez vous annuler les modifications en cours ?",
					MessageDialog.QUESTION, 
					//new String [] {IDialogConstants.YES_LABEL,IDialogConstants.NO_LABEL},
					new String [] {IDialogConstants.YES_LABEL,IDialogConstants.YES_TO_ALL_LABEL,IDialogConstants.NO_LABEL,IDialogConstants.NO_TO_ALL_LABEL,IDialogConstants.CANCEL_LABEL},
					0);

			//final int dialogResult = dialog.open();
			int pourTous = -1;

			/*
			 * yes => boucle avec question
			 * yes_all => boucle sans question (annulation silencieuse)
			 * no => enregistre avec question
			 * no_all => enregistre tout
			 */

			//if(dialogResult ==0) {
			int dialogResult = -1;
			
			if(evt.isSilencieu())
				dialogResult = 1;
			
				for (ILgrEditor ed : mapEditorController.keySet()) {
					//if(mapEditorController.get(ed).getDaoStandard().getModeObjet()
					switch (mapEditorController.get(ed).getDaoStandard().getModeObjet().getMode()) {
					case C_MO_INSERTION:
					case C_MO_EDITION:
						if(!mapEditorController.get(ed).equals(evt.getSource())) {
							
							setActiveEditor(ed);
							
							if(dialogResult==-1)
								dialogResult = dialog.open();
							
							if(dialogResult ==0 || dialogResult==1) {
								
								
								boolean silencieu = mapEditorController.get(ed).isSilencieu();
								mapEditorController.get(ed).setSilencieu(true);
								//mapEditorController.get(ed).setSilencieu(evt.isSilencieu());
								mapEditorController.get(ed).setAnnuleToutEnCours(true);
								mapEditorController.get(ed).declencheCommandeController(new DeclencheCommandeControllerEvent(this,JPABaseControllerSWTStandard.C_COMMAND_GLOBAL_ANNULER_ID));
								mapEditorController.get(ed).setAnnuleToutEnCours(false);
								mapEditorController.get(ed).setSilencieu(silencieu);
								if( dialogResult==0)
									dialogResult = -1;
							} else if(dialogResult==2 || dialogResult==3) {
								//no
								if( dialogResult==2)
									dialogResult = -1;
							} else if(dialogResult==4) {
								//cancel
								break;
							}


						}
						break;
					}
				}
			//}
//		}
	}
	
	public void enregistreTout(AnnuleToutEvent evt) {
//		MessageDialog dialog = new MessageDialog(getEditorSite().getShell(), 
//				"Enregistrement",
//				null, "Voulez vous enregistrer les modifications ?",
//				MessageDialog.QUESTION, 
//				new String [] {IDialogConstants.YES_LABEL,IDialogConstants.NO_LABEL,IDialogConstants.CANCEL_LABEL},
//				0);
//		
//		final int dialogResult = dialog.open();
		
		/*
		 * yes => boucle avec question
		 * yes_all => boucle sans question (annulation silencieuse)
		 * no => enregistre avec question
		 * no_all => enregistre tout
		 */
		
//		if(dialogResult ==0) {
//			
//		}
		boolean pourTous = false;
		for (ILgrEditor ed : mapEditorController.keySet()) {
			//if(mapEditorController.get(ed).getDaoStandard().getModeObjet()
			switch (mapEditorController.get(ed).getDaoStandard().getModeObjet().getMode()) {
			case C_MO_INSERTION:
			case C_MO_EDITION:
				boolean silencieu = mapEditorController.get(ed).isSilencieu();
//				if(!pourTous) {
//					setActiveEditor(ed);
//				}
				
//				if(!pourTous) {
//					if(!mapEditorController.get(ed).equals(evt.getSource())) {
//						MessageDialog dialog = new MessageDialog(mapEditorController.get(ed).getVue().getShell(), 
//								"Attention",
//								null, "Enregistrer ?",
//								MessageDialog.QUESTION, 
//								new String [] {IDialogConstants.YES_LABEL,IDialogConstants.YES_TO_ALL_LABEL,IDialogConstants.NO_LABEL,IDialogConstants.NO_TO_ALL_LABEL,IDialogConstants.CANCEL_LABEL},
//								0);
//						final int dialogResult = dialog.open();
//						if(dialogResult ==0) {
//						} else if(dialogResult ==1) {
//							pourTous = true;
//						} else if(dialogResult ==2) {
//						} else if(dialogResult ==3) {
//						} else if(dialogResult ==4) {
//						}
//						silencieu = true;
//					}
//				}
				
				
				if(!mapEditorController.get(ed).equals(evt.getSource())) {
//					if(!(mapEditorController.get(ed) instanceof SWTPaTiersController)) {
					mapEditorController.get(ed).setSilencieu(evt.isSilencieu());
					mapEditorController.get(ed).setPrepareEnregistrement(true);
					mapEditorController.get(ed).declencheCommandeController(new DeclencheCommandeControllerEvent(this,JPABaseControllerSWTStandard.C_COMMAND_GLOBAL_ENREGISTRER_ID));
					mapEditorController.get(ed).setPrepareEnregistrement(false);
					mapEditorController.get(ed).setSilencieu(silencieu);
//					}
				}
				break;
			}
		}
	}
	
	@Override
	public int promptToSaveOnClose() {
		try {
			onClose();
		} catch (Exception e) {
			return ISaveablePart2.CANCEL; 
		}
		return ISaveablePart2.NO; 
//		MessageDialog dialog = new MessageDialog(getEditorSite().getShell(), 
//				"Enregistrement",
//				null, "Voulez vous sauvegarder les modifications ?",
//				MessageDialog.QUESTION, 
//				new String [] {IDialogConstants.YES_LABEL,IDialogConstants.NO_LABEL,IDialogConstants.CANCEL_LABEL},
//				0);
//		MessageDialogWithToggle dialogt;
//		final int dialogResult = dialog.open();
//		
//		if(dialogResult ==0) {
//			if(true) { //bouchon, appeler une methode de verif des données
//				return ISaveablePart2.YES;
//			} else {
//				//showMessageErreur
//				return ISaveablePart2.CANCEL;
//			}
//		} else if(dialogResult ==1) {
//			annuleTout(new AnnuleToutEvent(this,true));
//			return ISaveablePart2.NO;
//		} else {
//			return ISaveablePart2.CANCEL;
//		}
	}

	public void changementDePage(ChangementDePageEvent evt) {
		if(evt.getSens() == ChangementDePageEvent.PRECEDENT) {
			if(findPrecedent()!=null) {
//				changeEditeurCourant(findPrecedent());
				setActiveEditor(findPrecedent());
			}
		} else if(evt.getSens() == ChangementDePageEvent.SUIVANT) {
			if(findSuivant()!=null) {
				//changeEditeurCourant(findSuivant());
				setActiveEditor(findSuivant());
			}
		} else if(evt.getSens() == ChangementDePageEvent.DEBUT) {
				//changeEditeurCourant(findSuivant());
			if(listePageEditeur.get(0)!=null)	
				setActiveEditor(listePageEditeur.get(0));
		} else if(evt.getSens() == ChangementDePageEvent.FIN) {
			if(listePageEditeur.size()!=0 && listePageEditeur.get(listePageEditeur.size())!=null)
				setActiveEditor(listePageEditeur.get(listePageEditeur.size()-1));
		} else if(evt.getSens() == ChangementDePageEvent.AUTRE) {
			if(listePageEditeur.get(evt.getPosition())!=null)
				setActiveEditor(listePageEditeur.get(evt.getPosition()));
			
		}
	}

	/*
	 * Reaction au changement de selection dans l'editeur principal
	 * (non-Javadoc)
	 * @see fr.legrain.gestCom.librairiesEcran.workbench.IChangementDeSelectionListener#changementDeSelection(fr.legrain.gestCom.librairiesEcran.workbench.ChangementDeSelectionEvent)
	 */
	public void changementDeSelection(ChangementDeSelectionEvent evt) {
		masterEntity = ((SWTPaArticlesController)editorArticle.getController()).getTaArticle();
		if(masterEntity!=null && masterEntity.getCodeArticle()!=null) {
			setPartName(PREFIXE_NOM_EDITEUR+" : "+masterEntity.getCodeArticle());
		} else {
			setPartName("");
		}
		//maj de l'editeur prix
		ParamAffichePrix paramAffichePrix = new ParamAffichePrix();
		paramAffichePrix.setIdArticle(LibConversion.integerToString(masterEntity.getIdArticle()));
		((SWTPaPrixController)editorPrix.getController()).setMasterEntity(masterEntity);
		((SWTPaPrixController)editorPrix.getController()).configPanel(paramAffichePrix);
		//maj de l'editeur commentaire
		ParamAfficheCommentairesArticle paramAfficheCommentairesArticle = new ParamAfficheCommentairesArticle();
		paramAffichePrix.setIdArticle(LibConversion.integerToString(masterEntity.getIdArticle()));
		((SWTPaCommentairesArticleController)editorCommentairesArticle.getController()).setMasterEntity(masterEntity);
		((SWTPaCommentairesArticleController)editorCommentairesArticle.getController()).configPanel(paramAfficheCommentairesArticle);
		//maj de l'editeur RapportUnite
		ParamAfficheRapportUnite paramAfficheRapportUnite = new ParamAfficheRapportUnite();
		paramAfficheRapportUnite.setIdArticle(LibConversion.integerToString(masterEntity.getIdArticle()));
		((SWTPaRapportUniteController)editorRapportUnite.getController()).setMasterEntity(masterEntity);
		((SWTPaRapportUniteController)editorRapportUnite.getController()).configPanel(paramAfficheRapportUnite);
		//maj de l'editeur Note
		ParamAfficheNoteArticle paramAfficheNoteArticle = new ParamAfficheNoteArticle();
		paramAfficheNoteArticle.setIdArticle(masterEntity.getIdArticle());
		((SWTPaNoteArticleController)editorNoteArticle.getController()).setMasterEntity(masterEntity);
		((SWTPaNoteArticleController)editorNoteArticle.getController()).configPanel(paramAfficheNoteArticle);
		//maj de l'editeur image
		ParamAfficheImageArticle paramAfficheImageArticle = new ParamAfficheImageArticle();
		paramAfficheImageArticle.setIdArticle(String.valueOf(masterEntity.getIdArticle()));
		((SWTPaImageArticleController)editorImageArticle.getController()).setMasterEntity(masterEntity);
		((SWTPaImageArticleController)editorImageArticle.getController()).configPanel(paramAfficheImageArticle);
		//maj de l'editeur catalogue web
		ParamAfficheCatalogueWeb paramAfficheCatalogueWeb = new ParamAfficheCatalogueWeb();
		paramAfficheCatalogueWeb.setIdArticle(String.valueOf(masterEntity.getIdArticle()));
		((SWTPaCatalogueWebController)editorCatalogueWeb.getController()).setMasterEntity(masterEntity);
		((SWTPaCatalogueWebController)editorCatalogueWeb.getController()).configPanel(paramAfficheCatalogueWeb);
		//maj de l'editeur categorie
		ParamAfficheCategorieArticle paramAfficheCategorieArticle = new ParamAfficheCategorieArticle();
		paramAfficheCategorieArticle.setIdArticle(String.valueOf(masterEntity.getIdArticle()));
		((SWTPaCategorieController)editorCategorieArticle.getController()).setMasterEntity(masterEntity);
		((SWTPaCategorieController)editorCategorieArticle.getController()).configPanel(paramAfficheCategorieArticle);
		//maj de l'editeur lable
		ParamAfficheLabelArticle paramAfficheLabelArticle = new ParamAfficheLabelArticle();
		paramAfficheLabelArticle.setIdArticle(String.valueOf(masterEntity.getIdArticle()));
		((SWTPaLabelController)editorLabelArticle.getController()).setMasterEntity(masterEntity);
		((SWTPaLabelController)editorLabelArticle.getController()).configPanel(paramAfficheLabelArticle);
		//maj de l'editeur fournisseur
		ParamAfficheFournisseurArticle paramAfficheFournisseurArticle = new ParamAfficheFournisseurArticle();
		paramAfficheFournisseurArticle.setIdArticle(String.valueOf(masterEntity.getIdArticle()));
		((SWTPaFournisseurController)editorFournisseurArticle.getController()).setMasterEntity(masterEntity);
		((SWTPaFournisseurController)editorFournisseurArticle.getController()).configPanel(paramAfficheFournisseurArticle);
		//maj de l'editeur conditionnement
		ParamAfficheConditionnementArticle paramAfficheConditionnementArticle = new ParamAfficheConditionnementArticle();
		paramAfficheConditionnementArticle.setIdArticle(String.valueOf(masterEntity.getIdArticle()));
		((SWTPaConditionnementController)editorConditionnementArticle.getController()).setMasterEntity(masterEntity);
		((SWTPaConditionnementController)editorConditionnementArticle.getController()).configPanel(paramAfficheConditionnementArticle);
		
		//maj de l'editeur editorTArticle
		ParamAfficheTArticle paramAfficheTArticle = new ParamAfficheTArticle();
		paramAfficheTArticle.setIdArticle(String.valueOf(masterEntity.getIdArticle()));
//		((SWTPaTypeArticleController)editorTArticle.getController()).setMasterEntity(masterEntity);
//		((SWTPaTypeArticleController)editorTArticle.getController()).configPanel(paramAfficheTArticle);
		
		//maj de l'editeur editorActionArticle
		ParamAfficheActionArticle paramAfficheActionArticle = new ParamAfficheActionArticle();
		paramAfficheActionArticle.setIdArticle(String.valueOf(masterEntity.getIdArticle()));
		((SWTPaActionArticleController)editorActionArticle.getController()).setMasterEntity(masterEntity);
		((SWTPaActionArticleController)editorActionArticle.getController()).configPanel(paramAfficheActionArticle);
		
		if(listeEditeurExtension!=null){
			for (IEditorArticlesExtension editor : listeEditeurExtension) {
				editor.setMasterEntity(masterEntity);
			}
		}
	}
	@Override
	protected void onClose() throws Exception {
		try {
			if (!findMasterController().onClose())
				throw new Exception();
		} finally {
			if(!JPABdLgr.isENTITY_MANAGER_UNIQUE()) {
				em.close();
			}
		}
	}
//	@Override
//	protected void addPages() {
//		// TODO Auto-generated method stub
//		try {
//			int index = addPage(new FormPageA(this,"aa", "desc"));
//			setPageText(index, "formPage");
//			index = addPage(new FormPageA(this,"bb", "descb"));
//			setPageText(index, "formPageb");
//		} catch (PartInitException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}


	@Override
	public void changementMasterEntity(ChangementMasterEntityEvent evt) {
		masterEntity = ((SWTPaArticlesController)editorArticle.getController()).getTaArticle();
		if(masterEntity!=null && masterEntity.getCodeArticle()!=null) {
			setPartName(PREFIXE_NOM_EDITEUR+" : "+masterEntity.getCodeArticle());
		} else {
			setPartName("");
		}
		((SWTPaPrixController)editorPrix.getController()).setMasterEntity(masterEntity);
		((SWTPaCommentairesArticleController)editorCommentairesArticle.getController()).setMasterEntity(masterEntity);
		((SWTPaRapportUniteController)editorRapportUnite.getController()).setMasterEntity(masterEntity);
		((SWTPaNoteArticleController)editorNoteArticle.getController()).setMasterEntity(masterEntity);
		((SWTPaImageArticleController)editorImageArticle.getController()).setMasterEntity(masterEntity);
		((SWTPaCatalogueWebController)editorCatalogueWeb.getController()).setMasterEntity(masterEntity);
		((SWTPaCategorieController)editorCategorieArticle.getController()).setMasterEntity(masterEntity);
		((SWTPaLabelController)editorLabelArticle.getController()).setMasterEntity(masterEntity);
		((SWTPaFournisseurController)editorFournisseurArticle.getController()).setMasterEntity(masterEntity);
		((SWTPaActionArticleController)editorActionArticle.getController()).setMasterEntity(masterEntity);
		
		for (IEditorArticlesExtension editor : listeEditeurExtension) {
			editor.setMasterEntity(masterEntity);
		}

	}
	
	private void createContributors() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint("Articles.editorPageArticles"); //$NON-NLS-1$
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
						if(editorIcon!=null) {
							icon = AbstractUIPlugin.imageDescriptorFromPlugin(contributorName, editorIcon);
						}
						addEditor((ILgrEditor)o,editorName,icon);
						
//						// test if the contributor applies to this editor
//						boolean isApplicable = false;
//						Class<?> subject = this.getClass();
//						while (subject != EditorPart.class && !isApplicable) {
//							isApplicable = editorClass.equals(subject.getName());
//							subject = subject.getSuperclass();
//						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private void addEditor(ILgrEditor e,String label) {
		 addEditor(e,label,null);
	}
	
	private void addEditor(ILgrEditor e,String label,ImageDescriptor icon) {
		String labelPage = label;		
		listePageEditeur.add(e);
		
		int index = 0;
		try {
			index = addPage(e, getEditorInput());
		} catch (PartInitException e1) {
			logger.error("",e1);
		}
		setPageText(index, labelPage);
		setPageImage(index, icon.createImage());
		
		if (e instanceof IEditorArticlesExtension) {
			if(listeEditeurExtension==null) {
				listeEditeurExtension = new ArrayList<IEditorArticlesExtension>();
			}
			listeEditeurExtension.add((IEditorArticlesExtension)e);
			((IEditorArticlesExtension)e).setMasterDAO(masterDAO);
			((IEditorArticlesExtension)e).setMasterEntity(masterEntity);
//			((IEditorArticlesExtension)e).setController(e.getController());
		}
	}
	
	
}
