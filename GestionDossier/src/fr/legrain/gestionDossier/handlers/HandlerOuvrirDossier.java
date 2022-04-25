package fr.legrain.gestionDossier.handlers;
import java.io.File;
import java.io.FilenameFilter;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.gestionCommerciale.Application;
import fr.legrain.gestionCommerciale.OuvreDossier;
import fr.legrain.gestionDossier.GestionDossierPlugin;
import fr.legrain.gestionDossier.ecran.ParamInfoEntreprise;
import fr.legrain.gestionDossier.ecran.SWTPaInfoEntrepriseSimpleController;
import fr.legrain.gestionDossier.editor.EditorInfoEntreprise;
import fr.legrain.gestionDossier.editor.EditorInputInfoEntreprise;
import fr.legrain.gestionDossier.preferences.PreferenceConstants;
import fr.legrain.lib.data.LibChaine;


public class HandlerOuvrirDossier extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerOuvrirDossier.class.getName());

	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
//			PaNouveauDossier paNouveauDossier = new PaNouveauDossier();
//			PaNouveauDossierController paNouveauDossierController = new PaNouveauDossierController(paNouveauDossier);
//			ParamNouveauDossier paramParamNouveauDossier= new ParamNouveauDossier();
//			paramParamNouveauDossier.setFocusDefaut(paNouveauDossier.getBtnParcourir());
//			paramParamNouveauDossier.setFocus(paNouveauDossier.getBtnParcourir());
//			LgrShellUtil.affiche(paramParamNouveauDossier,null,paNouveauDossier,paNouveauDossierController,window.getShell());
//			paramParamNouveauDossier.getFocus().requestFocus();
			
		//	if(MessageDialog.openQuestion(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "", "Effacer test/doss ?"))
		//		ResourcesPlugin.getWorkspace().getRoot().getProject("doss").delete(true, null);
		//		//ResourcesPlugin.getWorkspace().getRoot().getProject("doss").delete(false,true, null);

		//	IProject[] projets = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		//	System.out.println("Listes des projets du worksace : ");
		//	for (int i = 0; i < projets.length; i++) {
		//		if(!projets[i].getName().equals("tmp"))
		//			System.out.println(projets[i].getName());
		//	}
			
			//org.eclipse.swt.widgets.DirectoryDialog dd = new org.eclipse.swt.widgets.DirectoryDialog(Workbench.getInstance().getActiveWorkbenchWindow().getShell());
			FileDialog dd = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
//			DirectoryDialog dd = new DirectoryDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
			//dd.setMessage("Selection d'un dossier de facturation");
			dd.setFilterExtensions(new String[] {"*.bdg",".project",".txt"});
			dd.setFilterNames(new String[] {"Dossier bdg","Projet bdg","texte"});
			String repDestination = GestionDossierPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_REPERTOIRE_W) ;
			if(repDestination.equals(""))repDestination=Platform.getInstanceLocation().getURL().getFile();
			dd.setFilterPath(LibChaine.pathCorrect(repDestination));
			String choix = dd.open();
			logger.debug("Ouverture du dossier : "+choix);
//			String[] fichierBDG = null;
			if(choix!=null){
//				File cheminDossier = new File(choix);
//				if(cheminDossier.exists()) {
//					fichierBDG = cheminDossier.list(new FilenameFilter() {
//						
//						@Override
//						public boolean accept(File dir, String name) {
//							if(name.endsWith(".bdg"))
//								return true;
//							else
//								return false;
//						}
//					});
//				}
			////////////////////////////////////////////////////////////////////
//			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllPerspectives(true, false);
//			PlatformUI.getWorkbench().showPerspective(Perspective.ID, window);

//				if(fichierBDG!=null && fichierBDG.length>=1) {
					OuvreDossier i = new OuvreDossier();
					////i.importDossier(new java.io.File(choix));
					//i.ouverture(new java.io.File(choix));
					i.ouverture(new java.io.File(choix),false);
//					MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Ouverture de dossier", "Le Bureau de gestion va être fermé, puis réouvert." +
//							Const.finDeLigne+ "Veuillez patientez s'il vous plait");
					PlatformUI.getWorkbench().restart();
//					i.ouverture(new java.io.File(choix+"/"+fichierBDG[0]));
//				} else {
//					MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
//							"Erreur", 
//							"Dossier Bureau de gestion invalide, le fichier .bdg est introuvable");
//				}
			}
		} catch (Exception e) {
			logger.error("Erreur : run", e);
		}
		return null;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isHandled() {
		// TODO Auto-generated method stub
		return true;
	}

	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

}
