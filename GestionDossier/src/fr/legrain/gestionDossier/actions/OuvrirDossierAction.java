package fr.legrain.gestionDossier.actions;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import fr.legrain.gestionCommerciale.Perspective;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class OuvrirDossierAction implements IWorkbenchWindowActionDelegate {
	static Logger logger = Logger.getLogger(OuvrirDossierAction.class.getName());
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public OuvrirDossierAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		logger.error("utiliser HandlerOuvrirDossier.java et les commande plutot que l'action");
//		try {
////			PaNouveauDossier paNouveauDossier = new PaNouveauDossier();
////			PaNouveauDossierController paNouveauDossierController = new PaNouveauDossierController(paNouveauDossier);
////			ParamNouveauDossier paramParamNouveauDossier= new ParamNouveauDossier();
////			paramParamNouveauDossier.setFocusDefaut(paNouveauDossier.getBtnParcourir());
////			paramParamNouveauDossier.setFocus(paNouveauDossier.getBtnParcourir());
////			LgrShellUtil.affiche(paramParamNouveauDossier,null,paNouveauDossier,paNouveauDossierController,window.getShell());
////			paramParamNouveauDossier.getFocus().requestFocus();
//			
//		//	if(MessageDialog.openQuestion(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "", "Effacer test/doss ?"))
//		//		ResourcesPlugin.getWorkspace().getRoot().getProject("doss").delete(true, null);
//		//		//ResourcesPlugin.getWorkspace().getRoot().getProject("doss").delete(false,true, null);
//
//		//	IProject[] projets = ResourcesPlugin.getWorkspace().getRoot().getProjects();
//		//	System.out.println("Listes des projets du worksace : ");
//		//	for (int i = 0; i < projets.length; i++) {
//		//		if(!projets[i].getName().equals("tmp"))
//		//			System.out.println(projets[i].getName());
//		//	}
//			
//			//org.eclipse.swt.widgets.DirectoryDialog dd = new org.eclipse.swt.widgets.DirectoryDialog(Workbench.getInstance().getActiveWorkbenchWindow().getShell());
//			FileDialog dd = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
//			//dd.setMessage("Selection d'un dossier de facturation");
//			dd.setFilterExtensions(new String[] {".bdg",".project",".txt"});
//			dd.setFilterNames(new String[] {"Dossier bdg","Projet bdg","texte"});
//			String choix = dd.open();
//			System.out.println(choix);
//			
//			////////////////////////////////////////////////////////////////////
////			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllPerspectives(true, false);
////			PlatformUI.getWorkbench().showPerspective(Perspective.ID, window);
//			OuvreDossier i = new OuvreDossier();
////			i.importDossier(new java.io.File(choix));
//			i.ouverture(new java.io.File(choix));
//			
//		} catch (Exception e) {
//			logger.error("Erreur : run", e);
//		}
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}