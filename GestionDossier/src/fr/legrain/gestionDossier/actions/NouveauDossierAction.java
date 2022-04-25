package fr.legrain.gestionDossier.actions;


import org.apache.log4j.Logger;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import fr.legrain.gestionCommerciale.OuvreDossier;
import fr.legrain.gestionCommerciale.UtilWorkspace;



/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class NouveauDossierAction implements IWorkbenchWindowActionDelegate {
	static Logger logger = Logger.getLogger(NouveauDossierAction.class.getName());
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public NouveauDossierAction() {
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

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		logger.error("utiliser HandlerNouveauDossier.java et les commande plutot que l'action");
//		creerDossier();
	}

	
	public void creerDossier(){
		try {			
			DirectoryDialog dd = new DirectoryDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
			dd.setMessage("Emplacement du nouveau dossier");
			//dd.setFilterPath(new String[] {".project",".txt"});
			String choix = dd.open();
			System.out.println(choix);
			
			/////////////////////////////////////////////////////////////////////////////////////////////////
			InputDialog input = new InputDialog(window.getShell(),"Nouveau dossier","Nom du nouveau dossier",null,null);
			input.open();
			String nomDossier = input.getValue();
			if(nomDossier!=null && choix!=null) {
				UtilWorkspace uw = new UtilWorkspace();
				uw.initDossier(choix,nomDossier);
				
				/*
				 * Ouverture du dossier cree 
				 */
				if(MessageDialog.openQuestion(window.getShell(),"Nouveau dossier","Ouvrir le dossier '"+nomDossier+"' ?")) {
					OuvreDossier oD = new OuvreDossier();
					oD.ouverture(new java.io.File(choix+"/"+nomDossier+"/.project"));
				}
			}

			//paramParamNouveauDossier.getFocusDefaut().requestFocus();
		} catch (Exception e) {
			logger.error("Erreur : run", e);
		}
		
	}
}