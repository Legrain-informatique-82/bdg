package fr.legrain.exportation.editor;



import java.util.List;

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

import fr.legrain.document.controller.PaCritereListeDocumentCochableController;
import fr.legrain.document.controller.PaSelectionLigneDocumentCochableControlleur;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.exportation.ExportationPlugin;
import fr.legrain.exportation.controllers.PaExportationFactureControllerDates;
import fr.legrain.exportation.controllers.PaExportationFactureControllerReference;
import fr.legrain.exportation.controllers.PaExportationFactureControllerTous;
import fr.legrain.exportation.divers.DeclencheInitBorneControllerEvent;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.librairiesEcran.swt.DeclencheCommandeControllerEvent;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDePageEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDeSelectionEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.IChangementDePageListener;
import fr.legrain.gestCom.librairiesEcran.workbench.IChangementDeSelectionListener;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrRetourEditeur;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;


/**
 * An example showing how to create a multi-page editor.
 * This example has 3 pages:
 * <ul>
 * <li>page 0 contains a nested text editor.
 * <li>page 1 allows you to change the font used in page 2
 * <li>page 2 shows the words in page 0 in sorted order
 * </ul>
 */
public class ExportationMultiPageEditor extends AbstractLgrMultiPageEditor implements ILgrRetourEditeur, 
IChangementDePageListener, IChangementDeSelectionListener{
//public class MultiPageEditor extends FormEditor {
//public class MultiPageEditor extends SharedHeaderFormEditor {
	
	public static final String ID_EDITOR = "fr.legrain.exportation.editor.Exportation.multi"; //$NON-NLS-1$
	static Logger logger = Logger.getLogger(ExportationMultiPageEditor.class.getName());
	
	private ILgrEditor editeurCourant;
	private int currentPage;
	private TaFactureDAO masterEntity = null;
	private EntityManager em = null;
	
	private EditorExportation editorExportationTous = null;	
	String labelPageExportationTous = "Exportation de tous les documents non transférés";
	
	private EditorExportationReference editorExportationReference = null;	
	String labelPageExportationReference = "Exportation par référence";
	
	private EditorExportationDates editorExportationDates = null;	
	String labelPageExportationDates = "Exportation par dates";
	
	private fr.legrain.document.editor.EditorListeDocument editorListeDocument = null;	
	String labelPageListeDocument = "Liste des documents exportés";
	
	private EditorListeDocument editorListeDocumentManquant = null;	
	String labelPageListeDocumentManquant = "Liste des documents manquants";
	
	/**
	 * Creates a multi-page editor example.
	 */
	public ExportationMultiPageEditor() {
		super();
		setID_EDITOR(ID_EDITOR);
	}
	


	
	void createPageQueEditeur() {
		try {
			//Initialisation des pages/editeurs composant le multipage editor

			
			String iconPageExportation = "/icons/export_wiz.gif";

			
			em = new TaFactureDAO().getEntityManager();
			editorExportationTous = new EditorExportation(this,em);
			editorExportationReference = new EditorExportationReference(this, em);
			editorExportationDates = new EditorExportationDates(this, em);
			editorListeDocument = new fr.legrain.document.editor.EditorListeDocument(this, em);
			editorListeDocumentManquant = new EditorListeDocument(this, em);
			
			listePageEditeur.add(editorExportationTous);
			listePageEditeur.add(editorExportationReference);
			listePageEditeur.add(editorExportationDates);
			listePageEditeur.add(editorListeDocument);
			listePageEditeur.add(editorListeDocumentManquant);
			
			int index = addPage(editorExportationTous, getEditorInput());
			setPageText(index, labelPageExportationTous);
			setPageImage(index, ExportationPlugin.getImageDescriptor(iconPageExportation).createImage());
			
			index = addPage(editorExportationReference, getEditorInput());
			setPageText(index, labelPageExportationReference);
			setPageImage(index, ExportationPlugin.getImageDescriptor(iconPageExportation).createImage());
			
			index = addPage(editorExportationDates, getEditorInput());
			setPageText(index, labelPageExportationDates);
			setPageImage(index, ExportationPlugin.getImageDescriptor(iconPageExportation).createImage());
			
			index = addPage(editorListeDocument, getEditorInput());
			setPageText(index, labelPageListeDocument);
			setPageImage(index, ExportationPlugin.getImageDescriptor(iconPageExportation).createImage());
			
			index = addPage(editorListeDocumentManquant, getEditorInput());
			setPageText(index, labelPageListeDocumentManquant);
			setPageImage(index, ExportationPlugin.getImageDescriptor(iconPageExportation).createImage());
			
			//liaison entre la selection du controller principal et le multipage editor
			editorExportationTous.getController().addChangementDeSelectionListener(this);
			
			mapEditorController.put(editorExportationTous, editorExportationTous.getController());
			mapEditorController.put(editorExportationReference, editorExportationReference.getController());
			mapEditorController.put(editorExportationDates, editorExportationDates.getController());
			mapEditorController.put(editorListeDocument, editorListeDocument.getController());
			mapEditorController.put(editorListeDocumentManquant, editorListeDocumentManquant.getController());
			
			editorExportationTous.getController().addChangementDePageListener(this);
			editorExportationReference.getController().addChangementDePageListener(this);
			editorExportationDates.getController().addChangementDePageListener(this);
			editorListeDocument.getController().addChangementDePageListener(this);
			editorListeDocumentManquant.getController().addChangementDePageListener(this);
			
			((PaExportationFactureControllerReference)editorExportationReference.getController()).
			setMasterDaoAvoir(((PaExportationFactureControllerTous) editorExportationTous.getController()).getDaoAvoir());
			((PaExportationFactureControllerReference)editorExportationReference.getController()).
			setMasterDaoApporteur(((PaExportationFactureControllerTous) editorExportationTous.getController()).getDaoApporteur());				
			((PaExportationFactureControllerReference)editorExportationReference.getController()).
		    setMasterDaoFacture(((PaExportationFactureControllerTous) editorExportationTous.getController()).getDaoFacture());
			((PaExportationFactureControllerReference)editorExportationReference.getController()).
		    setMasterDaoAcompte(((PaExportationFactureControllerTous) editorExportationTous.getController()).getDaoAcompte());
			((PaExportationFactureControllerReference)editorExportationReference.getController()).
		    setMasterDaoReglement(((PaExportationFactureControllerTous) editorExportationTous.getController()).getDaoReglement());
			((PaExportationFactureControllerReference)editorExportationReference.getController()).
		    setMasterDaoRemise(((PaExportationFactureControllerTous) editorExportationTous.getController()).getDaoRemise());
			
			
			((PaExportationFactureControllerDates)editorExportationDates.getController()).
			setMasterDaoAvoir(((PaExportationFactureControllerTous) editorExportationTous.getController()).getDaoAvoir());
			((PaExportationFactureControllerDates)editorExportationDates.getController()).
			setMasterDaoApporteur(((PaExportationFactureControllerTous) editorExportationTous.getController()).getDaoApporteur());	
			((PaExportationFactureControllerDates)editorExportationDates.getController()).
		    setMasterDaoFacture(((PaExportationFactureControllerTous) editorExportationTous.getController()).getDaoFacture());
			((PaExportationFactureControllerDates)editorExportationDates.getController()).
		    setMasterDaoAcompte(((PaExportationFactureControllerTous) editorExportationTous.getController()).getDaoAcompte());
			((PaExportationFactureControllerDates)editorExportationDates.getController()).
		    setMasterDaoReglement(((PaExportationFactureControllerTous) editorExportationTous.getController()).getDaoReglement());
			((PaExportationFactureControllerDates)editorExportationDates.getController()).
		    setMasterDaoRemise(((PaExportationFactureControllerTous) editorExportationTous.getController()).getDaoRemise());
			
			editorExportationReference.getController().addDeclencheCommandeControllerListener(editorExportationTous.getController());
			editorExportationDates.getController().addDeclencheCommandeControllerListener(editorExportationTous.getController());
			editorListeDocument.getController().addDeclencheCommandeControllerListener(editorExportationTous.getController());
			editorListeDocumentManquant.getController().addDeclencheCommandeControllerListener(editorExportationTous.getController());
			
			editeurCourant = editorExportationTous;
//			DeclencheInitBorneControllerEvent event = new DeclencheInitBorneControllerEvent(
//					editorExportationTous.getController(),JPABaseControllerSWTStandard.C_COMMAND_GLOBAL_REFRESH_ID);
//			editorListeDocument.getController().declencheCommandeController(event);
			
			editorExportationReference.getController().configPanel(null);
			editorExportationDates.getController().configPanel(null);
			DeclencheCommandeControllerEvent event = new DeclencheCommandeControllerEvent(
			editorExportationTous.getController(),JPABaseControllerSWTStandard.C_COMMAND_GLOBAL_REFRESH_ID);
			editorExportationTous.getController().declencheCommandeController(event);
//			editorListeDocument.getController().configPanel(null);
			hideTabs();
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
 			if (getContainer() instanceof CTabFolder) {
 				((CTabFolder)getContainer()).setTabHeight(0);
 			}
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
					//|| masterEntity==null
					) {
				setActivePage(oldPageIndex);
				return;
			}
		}
		
		
		super.pageChange(newPageIndex);
		
		editeurCourant = listePageEditeur.get(newPageIndex);
		setCurrentPage(listePageEditeur.indexOf(editeurCourant));
		
//		if (oldPageIndex != -1 && listePageEditeur.size() > oldPageIndex
//				&& listePageEditeur.get(oldPageIndex) instanceof ILgrEditor
//				&& oldPageIndex != newPageIndex) {
//			//evite l'appel a refresh lors de l'ouverture du multipage editor (la commande n'a pas encore de handler actif)
//			mapEditorController.get(getActiveEditor()).executeCommand(JPABaseControllerSWTStandard.C_COMMAND_GLOBAL_REFRESH_ID);
//		}

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

	public ILgrEditor findSuivantReference() {
		return editorExportationReference;
	}
	public ILgrEditor findSuivantDates() {
		return editorExportationDates;
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

	public void changementDePage(ChangementDePageEvent evt) {
		if(evt.getSens() == ChangementDePageEvent.AUTRE) {
			ILgrEditor editorAppelant=listePageEditeur.get(evt.getPageAppelante());
			List<IDocumentTiers> model =null;
			if(listePageEditeur.get(evt.getPosition())!=null){
				setActiveEditor(listePageEditeur.get(evt.getPosition()));
			}
			if(listePageEditeur.get(evt.getPosition())== editorListeDocument){
				((PaCritereListeDocumentCochableController)listePageEditeur.get(evt.getPosition()).getController()).
				setIndiceAppelant(evt.getPageAppelante());
			}
			if(listePageEditeur.get(evt.getPosition())== editorListeDocumentManquant){
				if(editorAppelant==editorExportationReference){
					model=((PaExportationFactureControllerReference)editorExportationReference.getController()).getListeVerif();
				}
				((PaSelectionLigneDocumentCochableControlleur)listePageEditeur.get(evt.getPosition()).getController()).
				setMasterListEntity(model);			
				
			}
			
			DeclencheCommandeControllerEvent event = new DeclencheCommandeControllerEvent(listePageEditeur.get(evt.getPosition()).getController(), 
					JPABaseControllerSWTStandard.C_COMMAND_GLOBAL_REFRESH_ID);
			listePageEditeur.get(evt.getPosition()).getController().declencheCommandeController(event);
		}
	}
	@Override
	protected void onClose() throws Exception {
		try {
			if (!findMasterController().onClose())
				throw new Exception();
		} finally {
			em.close();
		}
	}
	/*
	 * Reaction au changement de selection dans l'editeur principal
	 * (non-Javadoc)
	 * @see fr.legrain.gestCom.librairiesEcran.workbench.IChangementDeSelectionListener#changementDeSelection(fr.legrain.gestCom.librairiesEcran.workbench.ChangementDeSelectionEvent)
	 */
	public void changementDeSelection(ChangementDeSelectionEvent evt) {
//		masterEntity = ((PaStocksController)editorStocks.getController()).getTaStock();
////		if(masterEntity!=null && masterEntity.getCodeArticle()!=null) {
////			setPartName(masterEntity.getCodeArticle());
////		} else {
//			setPartName("");
////		}
//		//maj de l'editeur prix
//		ParamAffiche paramAffichePrix = new ParamAffiche();
//		paramAffichePrix.setIdArticle(LibConversion.integerToString(masterEntity.getIdArticle()));
//		((PaCalculReportController)editorCalculReport.getController()).setMasterEntity(masterEntity);
//		((PaCalculReportController)editorCalculReport.getController()).configPanel(paramAffichePrix);
//		//maj de l'editeur commentaire
////		ParamAfficheCommentairesArticle paramAfficheCommentairesArticle = new ParamAfficheCommentairesArticle();
//		paramAffichePrix.setIdArticle(LibConversion.integerToString(masterEntity.getIdArticle()));
////		((SWTPaCommentairesArticleController)editorCommentairesArticle.getController()).setMasterEntity(masterEntity);
////		((SWTPaCommentairesArticleController)editorCommentairesArticle.getController()).configPanel(paramAfficheCommentairesArticle);
	}







}
