package fr.legrain.stocks.editor;



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

import stocks.StocksPlugin;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDePageEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDeSelectionEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.IChangementDePageListener;
import fr.legrain.gestCom.librairiesEcran.workbench.IChangementDeSelectionListener;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrRetourEditeur;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.stocks.controllers.PaCalculReportController;
import fr.legrain.stocks.controllers.PaEtatStocksController;
import fr.legrain.stocks.controllers.PaEtatStocksReportController;
import fr.legrain.stocks.dao.TaStock;
import fr.legrain.stocks.dao.TaStockDAO;


/**
 * An example showing how to create a multi-page editor.
 * This example has 3 pages:
 * <ul>
 * <li>page 0 contains a nested text editor.
 * <li>page 1 allows you to change the font used in page 2
 * <li>page 2 shows the words in page 0 in sorted order
 * </ul>
 */
public class StocksMultiPageEditor extends AbstractLgrMultiPageEditor implements ILgrRetourEditeur, IChangementDePageListener, IChangementDeSelectionListener{
//public class MultiPageEditor extends FormEditor {
//public class MultiPageEditor extends SharedHeaderFormEditor {
	
	public static final String ID_EDITOR = "fr.legrain.editor.stocks.multi"; //$NON-NLS-1$
	static Logger logger = Logger.getLogger(StocksMultiPageEditor.class.getName());
	
	private ILgrEditor editeurCourant;
	private int currentPage;
	private TaStock masterEntity = null;
	//private TaStockDAO masterDAO = null;
	private EntityManager em = null;
	
	private EditorStocks editorStocks = null;
	private EditorCalculReportStocks editorCalculReport = null;
	private EditorEtatStocks editorEtatStocks= null;
	private EditorEtatStocksReport editorEtatStocksReport= null;
	
	String labelPageStocks = "Stocks";
	String labelPageReportStocks = "Calcul des reports de stocks";
	String labelPageEtatStocks = "Etat des stocks";
	String labelPageEtatStocksReport = "Etat des reports de stocks";
	/**
	 * Creates a multi-page editor example.
	 */
	public StocksMultiPageEditor() {
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

			
			String iconPageStocks = "/icons/brick_add.png";
			String iconPageReportStocks = "/icons/report_add.png";
			String iconPageEtatStocks = "/icons/printer.png";
			String iconPageEtatStocksReport = "/icons/printer.png";
			
			em = new TaStockDAO().getEntityManager();
			editorStocks = new EditorStocks(this,em);
			editorCalculReport = new EditorCalculReportStocks(this,em);
			editorEtatStocks = new EditorEtatStocks(this,em);
			editorEtatStocksReport = new EditorEtatStocksReport(this,em);
			
			listePageEditeur.add(editorStocks);
			listePageEditeur.add(editorCalculReport);
			listePageEditeur.add(editorEtatStocks);
			listePageEditeur.add(editorEtatStocksReport);
			
			int index = addPage(editorStocks, getEditorInput());
			setPageText(index, labelPageStocks);
			setPageImage(index, StocksPlugin.getImageDescriptor(iconPageStocks).createImage());
			
			index = addPage(editorCalculReport, getEditorInput());
			setPageText(index, labelPageReportStocks);
			setPageImage(index, StocksPlugin.getImageDescriptor(iconPageReportStocks).createImage());
			
			index = addPage(editorEtatStocks, getEditorInput());
			setPageText(index, labelPageEtatStocks);
			setPageImage(index, StocksPlugin.getImageDescriptor(iconPageEtatStocks).createImage());
			
			index = addPage(editorEtatStocksReport, getEditorInput());
			setPageText(index, labelPageEtatStocksReport);
			setPageImage(index, StocksPlugin.getImageDescriptor(iconPageEtatStocksReport).createImage());
			
			//liaison entre la selection du controller principal et le multipage editor
			editorStocks.getController().addChangementDeSelectionListener(this);
			
			mapEditorController.put(editorStocks, editorStocks.getController());
			mapEditorController.put(editorCalculReport, editorCalculReport.getController());
			mapEditorController.put(editorEtatStocks, editorEtatStocks.getController());
			mapEditorController.put(editorEtatStocksReport, editorEtatStocksReport.getController());
			
			editorStocks.getController().addChangementDePageListener(this);
			editorCalculReport.getController().addChangementDePageListener(this);
			editorEtatStocks.getController().addChangementDePageListener(this);
			editorEtatStocksReport.getController().addChangementDePageListener(this);
			
//			//liaison entre l'editeur principal et les editeur secondaire par injection de l'entite principale dans les editeurs secondaires
//			masterDAO = ((PaStocksController)editorStocks.getController()).getDao();
//			((PaCalculReportController)editorCalculReport.getController()).setMasterDAO(masterDAO);
////			((SWTPaCommentairesArticleController)editorCommentairesArticle.getController()).setMasterDAO(masterDAO);
			
//			editorCalculReport.getController().addDeclencheCommandeControllerListener(editorStocks.getController());

			ParamAffiche paramAfficheEtatStocks = new ParamAffiche();
			paramAfficheEtatStocks.setTitreFormulaire(labelPageReportStocks);
			((PaCalculReportController)editorCalculReport.getController()).configPanel(paramAfficheEtatStocks);

			paramAfficheEtatStocks.setTitreFormulaire(labelPageEtatStocks);
			((PaEtatStocksController)editorEtatStocks.getController()).configPanel(paramAfficheEtatStocks);
		
			paramAfficheEtatStocks.setTitreFormulaire(labelPageEtatStocksReport);
			((PaEtatStocksReportController)editorEtatStocksReport.getController()).configPanel(paramAfficheEtatStocks);
			
			editeurCourant = editorStocks;
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
