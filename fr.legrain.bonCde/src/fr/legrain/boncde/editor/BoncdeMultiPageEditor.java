package fr.legrain.boncde.editor;


import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import fr.legrain.boncde.boncdePlugin;
import fr.legrain.boncde.controller.PaEditorBoncdeController;
import fr.legrain.boncde.controller.PaEditorTotauxBoncdeController;
import fr.legrain.boncde.controller.PaLigneBoncdeController;
import fr.legrain.boncde.divers.ParamAfficheLBoncde;
import fr.legrain.document.DocumentPlugin;
import fr.legrain.document.editor.AbstractMultiPageDocumentEditor;
import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaBoncdeDAO;
import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.events.SWTModificationDocumentEvent;
import fr.legrain.documents.events.SWTModificationDocumentListener;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDePageEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementMasterEntityEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.IChangementDePageListener;
import fr.legrain.gestCom.librairiesEcran.workbench.IChangementMasterEntityListener;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrRetourEditeur;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.JPABdLgr;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.libMessageLGR.LgrMess;


/**
 * An example showing how to create a multi-page editor.
 * This example has 3 pages:
 * <ul>
 * <li>page 0 contains a nested text editor.
 * <li>page 1 allows you to change the font used in page 2
 * <li>page 2 shows the words in page 0 in sorted order
 * </ul>
 */
public class BoncdeMultiPageEditor extends AbstractMultiPageDocumentEditor implements ILgrRetourEditeur, IChangementDePageListener, IChangementMasterEntityListener {
	
	public static final String ID_EDITOR = "fr.legrain.editor.boncde.swt.multi"; //$NON-NLS-1$
	static Logger logger = Logger.getLogger(BoncdeMultiPageEditor.class.getName());
	
	private ILgrEditor editeurCourant;
	private int currentPage;
	private TaBoncde masterEntity = null;
	private TaBoncdeDAO masterDAO = null;
	private EntityManager em = null;
	
	private EditorBoncde editorBoncde = null;
	private EditorLigne editorLigne = null;
	private EditorTotaux editorTotaux = null;
	
	public static final String PREFIXE_NOM_EDITEUR = "Commande";
	

	/**
	 * Creates a multi-page editor example.
	 */
	public BoncdeMultiPageEditor() {
		super();
		setID_EDITOR(ID_EDITOR);
	}
	



	
	
	void createPageQueEditeur() {
		try {
			//Initialisation des pages/editeurs composant le multipage editor
			String labelPageEntete = "Entete";
			String labelPageCorps = "Coprs";
			String labelPageTotaux = "Totaux";
			
			String iconPageEntete = "/icons/legrain.gif";
			String iconPageCorps = "/icons/legrain.gif";
			String iconPageTotaux = "/icons/legrain.gif";
			
			em = new TaBoncdeDAO().getEntityManager();
			editorBoncde = new EditorBoncde(this,em);
			editorLigne = new EditorLigne(this,em);
			editorTotaux = new EditorTotaux(this,em);		
			
			listePageEditeur.add(editorBoncde);
			listePageEditeur.add(editorLigne);
			listePageEditeur.add(editorTotaux);
			
			int index = addPage(editorBoncde, getEditorInput());
			setPageText(index, labelPageEntete);
			setPageImage(index, boncdePlugin.getImageDescriptor(iconPageEntete).createImage());
			
			index = addPage(editorLigne, getEditorInput());
			setPageText(index, labelPageCorps);
			setPageImage(index, boncdePlugin.getImageDescriptor(iconPageCorps).createImage());
			
			index = addPage(editorTotaux, getEditorInput());
			setPageText(index, labelPageTotaux);
			setPageImage(index, boncdePlugin.getImageDescriptor(iconPageTotaux).createImage());
			
			//liaison entre la selection du controller principal et le multipage editor
			((PaEditorBoncdeController)editorBoncde.getController()).addChangementMasterEntityListener(this);
			
			mapEditorController.put(editorBoncde, editorBoncde.getController());
			mapEditorController.put(editorLigne, editorLigne.getController());
			mapEditorController.put(editorTotaux, editorTotaux.getController());
			
			editorBoncde.getController().addChangementDePageListener(this);
			editorLigne.getController().addChangementDePageListener(this);
			editorTotaux.getController().addChangementDePageListener(this);
			
			//liaison entre l'editeur principal et les editeur secondaire par injection de l'entite principale dans les editeurs secondaires
			masterDAO = ((PaEditorBoncdeController)editorBoncde.getController()).getDao();
			((PaLigneBoncdeController)editorLigne.getController()).setMasterDAO(masterDAO);
			((PaEditorTotauxBoncdeController)editorTotaux.getController()).setMasterDAO(masterDAO);
			
			editorLigne.getController().addDeclencheCommandeControllerListener(editorBoncde.getController());
			editorTotaux.getController().addDeclencheCommandeControllerListener(editorBoncde.getController());
			((PaEditorTotauxBoncdeController)editorTotaux.getController()).initVerifySaisiePublic();
		
			//TODO #JPA
			//Spécifique a la facture
			((PaEditorBoncdeController)editorBoncde.getController()).setControllerLigne((PaLigneBoncdeController)editorLigne.getController());
			((PaEditorBoncdeController)editorBoncde.getController()).getControllerLigne().setMasterEntity(((PaEditorBoncdeController)editorBoncde.getController()).getTaDocument());
//			
			((PaEditorBoncdeController)editorBoncde.getController()).setControllerTotaux((PaEditorTotauxBoncdeController)editorTotaux.getController());
			((PaEditorBoncdeController)editorBoncde.getController()).getControllerTotaux().setMasterEntity(((PaEditorBoncdeController)editorBoncde.getController()).getTaDocument());
//			((PaEditorBoncdeController)editorBoncde.getController()).getControllerTotaux().setMasterDAO(((PaEditorBoncdeController)editorBoncde.getController()).getDao());
		
			
			Boolean affiche_onglets = DocumentPlugin.getDefault().getPreferenceStore().getBoolean(fr.legrain.document.preferences.PreferenceConstants.P_ONGLETS_DOC);
			if(!affiche_onglets)
				hideTabs();		
			
			editeurCourant = editorBoncde;
			//changeEditeurCourant(editorEntete);
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
	
	
//	/**
//	 * Creates page 1 of the multi-page editor,
//	 * which allows you to change the font used in page 2.
//	 */
//	void createPage1() {		
//		Composite1 composite = new Composite1(getContainer(), SWT.NONE);
//
//		int index = addPage(composite);
//		setPageText(index, "Properties");
//	}
//	
//	
//	/**
//	 * Creates page 2 of the multi-page editor,
//	 * which shows the sorted text.
//	 */
//	void createPage2() {
//		Composite2 composite = new Composite2(getContainer(), SWT.NONE);
//
//		int index = addPage(composite);
//		setPageText(index, "Preview");
//	}
	
	
	/**
	 * Creates the pages of the multi-page editor.
	 */
	protected void createPages() {
		createPageQueEditeur();
		
	//	createPage0();
	//	createPage1();
	//	createPage2();
		
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
			if (oldFormPage.canLeaveThePage() == false) {
				setActivePage(oldPageIndex);
				return;
			}
		}
		
		
		super.pageChange(newPageIndex);
		
		editeurCourant = listePageEditeur.get(newPageIndex);
		setCurrentPage(listePageEditeur.indexOf(editeurCourant));

	}


	public int getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
//		if(editeurCourant instanceof EditorLigne){
//			//si corps de pièce, initTTC
//			((PaLigneBoncdeController)((EditorLigne)editeurCourant).getController()).initTTC();
//		}
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

	public void changementDePage(ChangementDePageEvent evt) {
		updatePartName();
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

	private void updatePartName() {
		if(masterEntity!=null && masterEntity.getCodeDocument()!=null) {
			String partName = PREFIXE_NOM_EDITEUR+" : "+masterEntity.getCodeDocument();
			if(masterEntity.getTaTiers()!=null) {
				partName += " ( "+masterEntity.getTaTiers().getCodeTiers()+" - "+masterEntity.getTaTiers().getNomTiers()+" )";
			}
			setPartName(partName);
			setTitleToolTip(partName);
		} else {
			setPartName("");
		}
	}

	public void changeMasterEntity(TaBoncde newMasterEntity) {
		if(masterEntity!=null) {
			masterEntity.removeModificationDocumentListener(((SWTModificationDocumentListener)editorBoncde.getController()));
			masterEntity.removeModificationDocumentListener(((SWTModificationDocumentListener)editorLigne.getController()));
			masterEntity.removeModificationDocumentListener(((SWTModificationDocumentListener)editorTotaux.getController()));
		}
		
		masterEntity = newMasterEntity;
		masterEntity.addModificationDocumentListener(((PaEditorBoncdeController)editorBoncde.getController()));
		masterEntity.addModificationDocumentListener(((PaLigneBoncdeController)editorLigne.getController()));
		masterEntity.addModificationDocumentListener(((PaEditorTotauxBoncdeController)editorTotaux.getController()));
		
		updatePartName();
		
		//maj de l'editeur lignes
		ParamAfficheLBoncde paramAfficheLDevis = new ParamAfficheLBoncde();
		paramAfficheLDevis.setIdDevis(LibConversion.integerToString(masterEntity.getIdDocument()));
		((PaLigneBoncdeController)editorLigne.getController()).setMasterEntity(masterEntity);
		((PaLigneBoncdeController)editorLigne.getController()).configPanel(paramAfficheLDevis);
		//maj de l'editeur totaux
//		ParamAffiche paramAffiche = new ParamAffiche();
//		paramAffiche.setIdArticle(LibConversion.integerToString(masterEntity.getIdFacture()));
		((PaEditorTotauxBoncdeController)editorTotaux.getController()).setMasterEntity(masterEntity);
//		((SWTPaEditorTotauxController)editorTotaux.getController()).configPanel(paramAffiche);
		
		SWTModificationDocumentEvent evt = new SWTModificationDocumentEvent(this);
		((PaEditorBoncdeController)editorBoncde.getController()).modificationDocument(evt);
		((PaLigneBoncdeController)editorLigne.getController()).modificationDocument(evt);
		((PaEditorTotauxBoncdeController)editorTotaux.getController()).modificationDocument(evt);
	}

	public void changeMasterEntitySimple(TaBoncde newMasterEntity) {
		if(masterEntity!=null) {
			masterEntity.removeModificationDocumentListener(((SWTModificationDocumentListener)editorBoncde.getController()));
			masterEntity.removeModificationDocumentListener(((SWTModificationDocumentListener)editorLigne.getController()));
			masterEntity.removeModificationDocumentListener(((SWTModificationDocumentListener)editorTotaux.getController()));
		}
		
		masterEntity = newMasterEntity;
		masterEntity.addModificationDocumentListener(((PaEditorBoncdeController)editorBoncde.getController()));
		masterEntity.addModificationDocumentListener(((PaLigneBoncdeController)editorLigne.getController()));
		masterEntity.addModificationDocumentListener(((PaEditorTotauxBoncdeController)editorTotaux.getController()));
		
		//maj de l'editeur lignes
		((PaLigneBoncdeController)editorLigne.getController()).setMasterEntity(masterEntity);
		//maj de l'editeur totaux
		((PaEditorTotauxBoncdeController)editorTotaux.getController()).setMasterEntity(masterEntity);
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


	@Override
	public void changementMasterEntity(ChangementMasterEntityEvent evt) {
		if(evt.isSimple())
			changeMasterEntitySimple((TaBoncde)evt.getNewMasterEntity());
		else
			changeMasterEntity((TaBoncde)evt.getNewMasterEntity());
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
}
