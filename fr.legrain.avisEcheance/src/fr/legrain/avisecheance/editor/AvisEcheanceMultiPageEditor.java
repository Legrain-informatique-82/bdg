package fr.legrain.avisecheance.editor;


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

import fr.legrain.avisecheance.PlugInAvisEcheance;
import fr.legrain.avisecheance.controllers.PaEditorAvisEcheanceController;
import fr.legrain.avisecheance.controllers.PaEditorTotauxAvisEcheanceController;
import fr.legrain.avisecheance.controllers.PaLigneAvisEcheanceController;
import fr.legrain.avisecheance.divers.ParamAfficheLAvisEcheance;
import fr.legrain.document.DocumentPlugin;
import fr.legrain.document.editor.AbstractMultiPageDocumentEditor;
import fr.legrain.documents.dao.TaAvisEcheance;
import fr.legrain.documents.dao.TaAvisEcheanceDAO;
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
public class AvisEcheanceMultiPageEditor extends AbstractMultiPageDocumentEditor 
implements ILgrRetourEditeur, IChangementDePageListener, IChangementMasterEntityListener {
	
	public static final String ID_EDITOR = "fr.legrain.editor.avisEcheance.swt.multi"; //$NON-NLS-1$
	static Logger logger = Logger.getLogger(AvisEcheanceMultiPageEditor.class.getName());
	
	private ILgrEditor editeurCourant;
	private int currentPage;
	private TaAvisEcheance masterEntity = null;
	private TaAvisEcheanceDAO masterDAO = null;
	private EntityManager em = null;
	
	private EditorAvisEcheance editorProforma = null;
	private EditorLigne editorLigne = null;
	private EditorTotaux editorTotaux = null;
	
	public static final String PREFIXE_NOM_EDITEUR = "Avis d'échéance";
	

	/**
	 * Creates a multi-page editor example.
	 */
	public AvisEcheanceMultiPageEditor() {
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
			
			em = new TaAvisEcheanceDAO().getEntityManager();
			editorProforma = new EditorAvisEcheance(this,em);
			editorLigne = new EditorLigne(this,em);
			editorTotaux = new EditorTotaux(this,em);		
			
			listePageEditeur.add(editorProforma);
			listePageEditeur.add(editorLigne);
			listePageEditeur.add(editorTotaux);
			
			int index = addPage(editorProforma, getEditorInput());
			setPageText(index, labelPageEntete);
			setPageImage(index, PlugInAvisEcheance.getImageDescriptor(iconPageEntete).createImage());
			
			index = addPage(editorLigne, getEditorInput());
			setPageText(index, labelPageCorps);
			setPageImage(index, PlugInAvisEcheance.getImageDescriptor(iconPageCorps).createImage());
			
			index = addPage(editorTotaux, getEditorInput());
			setPageText(index, labelPageTotaux);
			setPageImage(index, PlugInAvisEcheance.getImageDescriptor(iconPageTotaux).createImage());
			
			//liaison entre la selection du controller principal et le multipage editor
			((PaEditorAvisEcheanceController)editorProforma.getController()).addChangementMasterEntityListener(this);
			
			mapEditorController.put(editorProforma, editorProforma.getController());
			mapEditorController.put(editorLigne, editorLigne.getController());
			mapEditorController.put(editorTotaux, editorTotaux.getController());
			
			editorProforma.getController().addChangementDePageListener(this);
			editorLigne.getController().addChangementDePageListener(this);
			editorTotaux.getController().addChangementDePageListener(this);
			
			//liaison entre l'editeur principal et les editeur secondaire par injection de l'entite principale dans les editeurs secondaires
			masterDAO = ((PaEditorAvisEcheanceController)editorProforma.getController()).getDao();
			((PaLigneAvisEcheanceController)editorLigne.getController()).setMasterDAO(masterDAO);
			((PaEditorTotauxAvisEcheanceController)editorTotaux.getController()).setMasterDAO(masterDAO);
			
			editorLigne.getController().addDeclencheCommandeControllerListener(editorProforma.getController());
			editorTotaux.getController().addDeclencheCommandeControllerListener(editorProforma.getController());
			((PaEditorTotauxAvisEcheanceController)editorTotaux.getController()).initVerifySaisiePublic();
		
			//TODO #JPA
			//Spécifique a la facture
			((PaEditorAvisEcheanceController)editorProforma.getController()).setControllerLigne((PaLigneAvisEcheanceController)editorLigne.getController());
			((PaEditorAvisEcheanceController)editorProforma.getController()).getControllerLigne().setMasterEntity(((PaEditorAvisEcheanceController)editorProforma.getController()).getTaAvisEcheance());
//			
			((PaEditorAvisEcheanceController)editorProforma.getController()).setControllerTotaux((PaEditorTotauxAvisEcheanceController)editorTotaux.getController());
			((PaEditorAvisEcheanceController)editorProforma.getController()).getControllerTotaux().setMasterEntity(((PaEditorAvisEcheanceController)editorProforma.getController()).getTaAvisEcheance());
//			((PaEditorProformaController)editorProforma.getController()).getControllerTotaux().setMasterDAO(((PaEditorProformaController)editorProforma.getController()).getDao());
		
			
			Boolean affiche_onglets = DocumentPlugin.getDefault().getPreferenceStore().getBoolean(fr.legrain.document.preferences.PreferenceConstants.P_ONGLETS_DOC);
			if(!affiche_onglets)
				hideTabs();		
			
			editeurCourant = editorProforma;
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
//			((PaLigneProformaController)((EditorLigne)editeurCourant).getController()).initTTC();
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

	public void changeMasterEntity(TaAvisEcheance newMasterEntity) {
		if(masterEntity!=null) {
			masterEntity.removeModificationDocumentListener(((SWTModificationDocumentListener)editorProforma.getController()));
			masterEntity.removeModificationDocumentListener(((SWTModificationDocumentListener)editorLigne.getController()));
			masterEntity.removeModificationDocumentListener(((SWTModificationDocumentListener)editorTotaux.getController()));
		}
		
		masterEntity = newMasterEntity;
		masterEntity.addModificationDocumentListener(((PaEditorAvisEcheanceController)editorProforma.getController()));
		masterEntity.addModificationDocumentListener(((PaLigneAvisEcheanceController)editorLigne.getController()));
		masterEntity.addModificationDocumentListener(((PaEditorTotauxAvisEcheanceController)editorTotaux.getController()));
		
		updatePartName();
		
		//maj de l'editeur lignes
		ParamAfficheLAvisEcheance paramAfficheLDevis = new ParamAfficheLAvisEcheance();
		paramAfficheLDevis.setIdDevis(LibConversion.integerToString(masterEntity.getIdDocument()));
		((PaLigneAvisEcheanceController)editorLigne.getController()).setMasterEntity(masterEntity);
		((PaLigneAvisEcheanceController)editorLigne.getController()).configPanel(paramAfficheLDevis);
		//maj de l'editeur totaux
//		ParamAffiche paramAffiche = new ParamAffiche();
//		paramAffiche.setIdArticle(LibConversion.integerToString(masterEntity.getIdFacture()));
		((PaEditorTotauxAvisEcheanceController)editorTotaux.getController()).setMasterEntity(masterEntity);
//		((SWTPaEditorTotauxController)editorTotaux.getController()).configPanel(paramAffiche);
		
		SWTModificationDocumentEvent evt = new SWTModificationDocumentEvent(this);
		((PaEditorAvisEcheanceController)editorProforma.getController()).modificationDocument(evt);
		((PaLigneAvisEcheanceController)editorLigne.getController()).modificationDocument(evt);
		((PaEditorTotauxAvisEcheanceController)editorTotaux.getController()).modificationDocument(evt);
	}

	public void changeMasterEntitySimple(TaAvisEcheance newMasterEntity) {
		if(masterEntity!=null) {
			masterEntity.removeModificationDocumentListener(((SWTModificationDocumentListener)editorProforma.getController()));
			masterEntity.removeModificationDocumentListener(((SWTModificationDocumentListener)editorLigne.getController()));
			masterEntity.removeModificationDocumentListener(((SWTModificationDocumentListener)editorTotaux.getController()));
		}
		
		masterEntity = newMasterEntity;
		masterEntity.addModificationDocumentListener(((PaEditorAvisEcheanceController)editorProforma.getController()));
		masterEntity.addModificationDocumentListener(((PaLigneAvisEcheanceController)editorLigne.getController()));
		masterEntity.addModificationDocumentListener(((PaEditorTotauxAvisEcheanceController)editorTotaux.getController()));
		
		//maj de l'editeur lignes
		((PaLigneAvisEcheanceController)editorLigne.getController()).setMasterEntity(masterEntity);
		//maj de l'editeur totaux
		((PaEditorTotauxAvisEcheanceController)editorTotaux.getController()).setMasterEntity(masterEntity);
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
			changeMasterEntitySimple((TaAvisEcheance)evt.getNewMasterEntity());
		else
			changeMasterEntity((TaAvisEcheance)evt.getNewMasterEntity());
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
