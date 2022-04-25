package fr.legrain.recherchermulticrit.editors;

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
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrRetourEditeur;
import fr.legrain.recherchermulticrit.Activator;
import fr.legrain.recherchermulticrit.controllers.FormPageController;
import fr.legrain.recherchermulticrit.controllers.FormPageControllerResultats;
import fr.legrain.recherchermulticrit.controllers.FormPageMessenger;
import fr.legrain.recherchermulticrit.controllers.IFormPageArticlesController;
import fr.legrain.recherchermulticrit.ecrans.PaFormPage;
import fr.legrain.recherchermulticrit.ecrans.PaFormPageResultats;



/**
 * Form editor du tableau de bord des statistiques
 * @author nicolas²
 *
 */

public class FormEditorRechercherMultiCrit extends FormEditor implements ILgrEditor, ILgrRetourEditeur,IPartListener{

	static Logger logger = Logger.getLogger(FormEditorRechercherMultiCrit.class.getName());
	
	// Déclaration publiques
	public static final String ID = "fr.legrain.recherchermulticrit.editors.FormEditorRechercherMultiCrit";
	
	// Icones
	String iconPageResultats = "/icons/table.png";
	String iconPageRecherche = "/icons/find.png";
	
	// Déclarations privées
	private TaArticle masterEntity = null;
	private TaArticleDAO masterDAO = null;
	// FormPage principal
	private PaFormPage defaultFormPage = null;
	private PaFormPageResultats resultatsFormPage = null;
		
	
	// Constructeur par défaut
	public FormEditorRechercherMultiCrit() {
		super();
	}
	
	@Override
	protected void addPages() {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().addPartListener(this);
			// Création du formPage
			defaultFormPage = new PaFormPage(this, PaFormPage.id, PaFormPage.title);
			resultatsFormPage = new PaFormPageResultats(this,PaFormPageResultats.id,PaFormPageResultats.title);
			
			
			// Création du Controller
			FormPageController defautController = new FormPageController(defaultFormPage);
			FormPageControllerResultats resultatsController = new FormPageControllerResultats(resultatsFormPage);
			
			FormPageMessenger messenger = new FormPageMessenger(this,defautController, resultatsController);
			
			
			// Liaison entre le controller et le formPage 			
			defaultFormPage.setControllerPage(defautController);
			resultatsFormPage.setControllerPage(resultatsController);
			
			// Liaison entre les controllers
			defautController.setMessengerPage(messenger);
			resultatsController.setMessengerPage(messenger);
			
			
			this.addPage(defaultFormPage);
			this.addPage(resultatsFormPage);
			// Initialisation des pages
			setPageImage(0, Activator.getImageDescriptor(iconPageRecherche).createImage());
			setPageImage(1, Activator.getImageDescriptor(iconPageResultats).createImage());
			super.setActivePage(1);
			super.setActivePage(0);
		} catch (PartInitException e) {
			logger.error("",e);
		}
	}
	
	/**
	 * Méthode permettant de changer de page en passant
	 * l'index en paramètre
	 * @param index l'index de la page
	 */
	public void changePage(int index) {
		super.setActivePage(index);
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
