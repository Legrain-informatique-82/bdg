package fr.legrain.saisiecaisse.editor;



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

import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDePageEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDeSelectionEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.IChangementDePageListener;
import fr.legrain.gestCom.librairiesEcran.workbench.IChangementDeSelectionListener;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrRetourEditeur;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.JPABdLgr;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.saisieCaisse.dao.TaEtablissement;
import fr.legrain.saisieCaisse.dao.TaOperationDAO;
import fr.legrain.saisiecaisse.saisieCaissePlugin;
import fr.legrain.saisiecaisse.controller.PaCalculReportController;
import fr.legrain.saisiecaisse.controller.PaDepotController;
import fr.legrain.saisiecaisse.controller.PaEtablissementController;
import fr.legrain.saisiecaisse.controller.PaEtatSaisieCaisseController;
import fr.legrain.saisiecaisse.controller.PaExportSaisieCaisseController;
import fr.legrain.saisiecaisse.controller.PaOperationController;
import fr.legrain.saisiecaisse.controller.PaTOperationController;
import fr.legrain.saisiecaisse.divers.ParamAfficheDepot;
import fr.legrain.saisiecaisse.divers.ParamAfficheOperation;
import fr.legrain.saisiecaisse.divers.ParamAfficheTOperation;
import fr.legrain.saisiecaisse.divers.ParamImpressionSaisieCaisse;


/**
 * An example showing how to create a multi-page editor.
 * This example has 3 pages:
 * <ul>
 * <li>page 0 contains a nested text editor.
 * <li>page 1 allows you to change the font used in page 2
 * <li>page 2 shows the words in page 0 in sorted order
 * </ul>
 */
public class SaisieCaisseMultiPageEditor extends AbstractLgrMultiPageEditor 
implements ILgrRetourEditeur, IChangementDePageListener, IChangementDeSelectionListener{
//public class MultiPageEditor extends FormEditor {
//public class MultiPageEditor extends SharedHeaderFormEditor {
	
	public static final String ID_EDITOR = "fr.legrain.editor.saisieCaisse.multi"; //$NON-NLS-1$
	static Logger logger = Logger.getLogger(SaisieCaisseMultiPageEditor.class.getName());
	
	private ILgrEditor editeurCourant;
	private int currentPage;
	private TaEtablissement masterEntity = null;
	//private TaOperationDAO masterDAO = null;
	private EntityManager em = null;
	
	private EditorOperation2 editorOperation = null;
	private EditorDepot editorDepot = null;
	private EditorTOperation editorTOperation = null;
	private EditorEtatSaisieCaisse editorEtatSaisieCaisseJour = null;
	private EditorEtatSaisieCaisse editorEtatSaisieCaisseMois = null;
	private EditorEtablissement editorEtablissement = null;
	private EditorCalculReportCaisse editorCalculReportCaisse = null;
	private EditorExportSaisieCaisse editorExportSaisieCaisse = null;
	
	String labelPageOperation = "Opération";
	String labelPageDepot = "Dépôt";
	String labelPageTOperation = "Type d'opération";
	String labelPageEtatSaisieCaisseJour = "Etat de la caisse (jour)";
	String labelPageEtatSaisieCaisseMois = "Etat de la caisse (synthèse au mois)";
	String labelPageEtablissement = "Etablissement";
	String labelPageReportCaisse = "Calcul report de caisse";
	String labelPageExportCaisse = "Exportation de la caisse";
	/**
	 * Creates a multi-page editor example.
	 */
	public SaisieCaisseMultiPageEditor() {
		super();
		setID_EDITOR(ID_EDITOR);
	}
	


	
	void createPageQueEditeur() {
		try {
			//Initialisation des pages/editeurs composant le multipage editor

			
			String iconPageOperation = "/icons/book_open.png";
			String iconPageDepot = "/icons/money.png";
			String iconPageTOperation = "/icons/legrain.gif";
			String iconPageEtatSaisieCaisseJour = "/icons/printer.png";
			String iconPageEtatSaisieCaisseMois = "/icons/printer.png";
			String iconPageEtablissement = "/icons/house.png";
			String iconPageReportCaisse = "/icons/report_add.png";
			String iconPageExportCaisse = "/icons/export_wiz.gif";
			
			em = new TaOperationDAO().getEntityManager();
			editorEtablissement = new EditorEtablissement(this,em);
			editorOperation = new EditorOperation2(this,em);
			editorDepot = new EditorDepot(this,em);
			editorEtatSaisieCaisseJour = new EditorEtatSaisieCaisse(this,em);
			editorEtatSaisieCaisseMois = new EditorEtatSaisieCaisse(this,em);
			editorCalculReportCaisse = new EditorCalculReportCaisse(this,em);
			editorTOperation = new EditorTOperation(this,em);
			editorExportSaisieCaisse = new EditorExportSaisieCaisse(this,em);
			
			listePageEditeur.add(editorEtablissement);
			listePageEditeur.add(editorOperation);
			listePageEditeur.add(editorDepot);
			listePageEditeur.add(editorEtatSaisieCaisseJour);
			listePageEditeur.add(editorEtatSaisieCaisseMois);
			listePageEditeur.add(editorCalculReportCaisse);
			listePageEditeur.add(editorTOperation);
			listePageEditeur.add(editorExportSaisieCaisse);
			
			int index = addPage(editorEtablissement, getEditorInput());
			setPageText(index, labelPageEtablissement);
			setPageImage(index, saisieCaissePlugin.getImageDescriptor(iconPageEtablissement).createImage());
			index = addPage(editorOperation, getEditorInput());
			setPageText(index, labelPageOperation);
			setPageImage(index, saisieCaissePlugin.getImageDescriptor(iconPageOperation).createImage());
			index = addPage(editorDepot, getEditorInput());
			setPageText(index, labelPageDepot);
			setPageImage(index, saisieCaissePlugin.getImageDescriptor(iconPageDepot).createImage());
			index = addPage(editorEtatSaisieCaisseJour, getEditorInput());
			setPageText(index, labelPageEtatSaisieCaisseJour);
			setPageImage(index, saisieCaissePlugin.getImageDescriptor(iconPageEtatSaisieCaisseJour).createImage());
			index = addPage(editorEtatSaisieCaisseMois, getEditorInput());
			setPageText(index, labelPageEtatSaisieCaisseMois);
			setPageImage(index, saisieCaissePlugin.getImageDescriptor(iconPageEtatSaisieCaisseMois).createImage());
			index = addPage(editorCalculReportCaisse, getEditorInput());
			setPageText(index, labelPageReportCaisse);
			setPageImage(index, saisieCaissePlugin.getImageDescriptor(iconPageReportCaisse).createImage());
			index = addPage(editorTOperation, getEditorInput());
			setPageText(index, labelPageTOperation);
			setPageImage(index, saisieCaissePlugin.getImageDescriptor(iconPageTOperation).createImage());
			index = addPage(editorExportSaisieCaisse, getEditorInput());
			setPageText(index, labelPageExportCaisse);
			setPageImage(index, saisieCaissePlugin.getImageDescriptor(iconPageExportCaisse).createImage());
			
			//liaison entre la selection du controller principal et le multipage editor
			editorEtablissement.getController().addChangementDeSelectionListener(this);
			
			mapEditorController.put(editorEtablissement, editorEtablissement.getController());
			mapEditorController.put(editorOperation, editorOperation.getController());
			mapEditorController.put(editorDepot, editorDepot.getController());
			mapEditorController.put(editorEtatSaisieCaisseJour, editorEtatSaisieCaisseJour.getController());
			mapEditorController.put(editorEtatSaisieCaisseMois, editorEtatSaisieCaisseMois.getController());
			mapEditorController.put(editorCalculReportCaisse, editorCalculReportCaisse.getController());
			mapEditorController.put(editorTOperation, editorTOperation.getController());
			mapEditorController.put(editorExportSaisieCaisse, editorExportSaisieCaisse.getController());
			
			editorEtablissement.getController().addChangementDePageListener(this);
			editorOperation.getController().addChangementDePageListener(this);
			editorDepot.getController().addChangementDePageListener(this);
			editorEtatSaisieCaisseJour.getController().addChangementDePageListener(this);
			editorEtatSaisieCaisseMois.getController().addChangementDePageListener(this);
			editorCalculReportCaisse.getController().addChangementDePageListener(this);
			editorTOperation.getController().addChangementDePageListener(this);
			editorExportSaisieCaisse.getController().addChangementDePageListener(this);
			
//			editorTOperation.getController().addDeclencheCommandeControllerListener(editorOperation.getController());
//			editorDepot.getController().addDeclencheCommandeControllerListener(editorOperation.getController());
//			editorEtatSaisieCaisseJour.getController().addDeclencheCommandeControllerListener(editorOperation.getController());
//			editorEtatSaisieCaisseMois.getController().addDeclencheCommandeControllerListener(editorOperation.getController());
//			editorEtablissement.getController().addDeclencheCommandeControllerListener(editorEtablissement.getController());
			

//			ParamAfficheEtablissement paramAfficheEtablissement = new ParamAfficheEtablissement();
//			((PaEtablissementController)editorEtablissement.getController()).configPanel(paramAfficheEtablissement);
			
			

			ParamAfficheTOperation paramAfficheTOperation = new ParamAfficheTOperation();
			((PaTOperationController)editorTOperation.getController()).configPanel(paramAfficheTOperation);

			editeurCourant = editorEtablissement;
			
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
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().
		addPartListener(LgrPartListener.getLgrPartListener());
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
		masterEntity = ((PaEtablissementController)editorEtablissement.getController()).getTaEtablissement();
		if(masterEntity!=null && masterEntity.getNomEtablissement()!=null) {
			setPartName(masterEntity.getNomEtablissement());
		} else
		{
			setPartName("");
		}

		
		ParamAfficheOperation paramAfficheOperation = new ParamAfficheOperation();
		paramAfficheOperation.setIdEtablissement(LibConversion.integerToString(masterEntity.getIdEtablissement()));
		((PaOperationController)editorOperation.getController()).setMasterEntity(masterEntity);
		((PaOperationController)editorOperation.getController()).configPanel(paramAfficheOperation);

		
		ParamAfficheDepot paramAfficheDepot = new ParamAfficheDepot();
		paramAfficheDepot.setIdEtablissement(LibConversion.integerToString(masterEntity.getIdEtablissement()));
		((PaDepotController)editorDepot.getController()).setMasterEntity(masterEntity);
		((PaDepotController)editorDepot.getController()).configPanel(paramAfficheDepot);
		
		ParamImpressionSaisieCaisse paramAfficheEtatSaisieCaisseJour = new ParamImpressionSaisieCaisse();
		paramAfficheEtatSaisieCaisseJour.setIdEtablissement(LibConversion.integerToString(masterEntity.getIdEtablissement()));
		paramAfficheEtatSaisieCaisseJour.setSynthese(false);
		paramAfficheEtatSaisieCaisseJour.setTitreFormulaire(labelPageEtatSaisieCaisseJour);
		((PaEtatSaisieCaisseController)editorEtatSaisieCaisseJour.getController()).setMasterEntity(masterEntity);
		((PaEtatSaisieCaisseController)editorEtatSaisieCaisseJour.getController()).configPanel(paramAfficheEtatSaisieCaisseJour);
		
		ParamImpressionSaisieCaisse paramAfficheEtatSaisieCaisseMois = new ParamImpressionSaisieCaisse();
		paramAfficheEtatSaisieCaisseMois.setIdEtablissement(LibConversion.integerToString(masterEntity.getIdEtablissement()));
		paramAfficheEtatSaisieCaisseMois.setTitreFormulaire(labelPageEtatSaisieCaisseMois);
		paramAfficheEtatSaisieCaisseMois.setSynthese(true);
		((PaEtatSaisieCaisseController)editorEtatSaisieCaisseMois.getController()).setMasterEntity(masterEntity);
		((PaEtatSaisieCaisseController)editorEtatSaisieCaisseMois.getController()).configPanel(paramAfficheEtatSaisieCaisseMois);
		
		ParamImpressionSaisieCaisse paramAfficheCalculReportCaisse = new ParamImpressionSaisieCaisse();
		paramAfficheCalculReportCaisse.setIdEtablissement(LibConversion.integerToString(masterEntity.getIdEtablissement()));
		paramAfficheCalculReportCaisse.setTitreFormulaire(labelPageReportCaisse);
		paramAfficheCalculReportCaisse.setSynthese(false);
		((PaCalculReportController)editorCalculReportCaisse.getController()).setMasterEntity(masterEntity);
		((PaCalculReportController)editorCalculReportCaisse.getController()).configPanel(paramAfficheCalculReportCaisse);

		ParamImpressionSaisieCaisse paramAfficheExportCaisse = new ParamImpressionSaisieCaisse();
		paramAfficheExportCaisse.setTitreFormulaire(labelPageExportCaisse);
		paramAfficheExportCaisse.setIdEtablissement(LibConversion.integerToString(masterEntity.getIdEtablissement()));
		paramAfficheExportCaisse.setSynthese(false);
		((PaExportSaisieCaisseController)editorExportSaisieCaisse.getController()).setMasterEntity(masterEntity);
		((PaExportSaisieCaisseController)editorExportSaisieCaisse.getController()).configPanel(paramAfficheExportCaisse);
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
}
