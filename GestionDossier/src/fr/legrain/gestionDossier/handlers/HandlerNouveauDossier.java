package fr.legrain.gestionDossier.handlers;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestionCommerciale.Application;
import fr.legrain.gestionCommerciale.OuvreDossier;
import fr.legrain.gestionCommerciale.UtilWorkspace;
import fr.legrain.gestionDossier.GestionDossierPlugin;
import fr.legrain.gestionDossier.preferences.PreferenceConstants;
import fr.legrain.lib.data.LibChaine;


public class HandlerNouveauDossier extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerNouveauDossier.class.getName());
	
	private IWorkbenchWindow window = null;

	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		creerDossier();
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
	
	public void creerDossier(){
		try {
			
			/////////////////////////////////////////////////////////////////////////////////////////////////
			InputDialog input = new InputDialog(window.getShell(),"Nouveau dossier","Nom du nouveau dossier",null,new IInputValidator(){

				public String isValid(String newText) {
					String refuse = "";
					for (int i = 0; i < newText.length(); i++) {
						String value = newText.substring(i,i+1);

						if(new String(LibChaine.C_UNAUTHORIZED_CHAR_PROJET_NAME).contains(value)&& !newText.equals(""))
							refuse+=value;												 
					}
					if(refuse.equals(" ")) refuse = "espace";
					if (!refuse.equals(""))return "Le nom de dossier ne doit pas contenir le(s) caractère(s) : "+refuse;
					if(newText!=null && !newText.equals("") ){
						if(!newText.toLowerCase().equals(newText))
							return "Le nom du dossier doit être écrit en minuscule !";
					}
					return null;
				}

			});
			input.open();
			String nomDossier = input.getValue().toLowerCase();
			
			if (!nomDossier.equals("")){			
				DirectoryDialog dd = new DirectoryDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),SWT.DIALOG_TRIM);
				dd.setMessage("Choisir l'emplacement du nouveau dossier");
				dd.setText("Emplacement du nouveau dossier");
				String repDestination = GestionDossierPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_REPERTOIRE_W) ;
				if(repDestination.equals(""))repDestination=Platform.getInstanceLocation().getURL().getFile();
				dd.setFilterPath(LibChaine.pathCorrect(repDestination));
				String choix = dd.open();
				if(choix!=null){
					System.out.println(choix);

					if(nomDossier!=null && choix!=null) {
						UtilWorkspace uw = new UtilWorkspace();
						uw.initDossier(choix,nomDossier);

						/*
						 * Ouverture du dossier cree 
						 */
						//if(MessageDialog.openQuestion(window.getShell(),"Nouveau dossier","Ouvrir le dossier '"+nomDossier+"' ?")) {
						OuvreDossier oD = new OuvreDossier();
						oD.ouverture(new java.io.File(choix+"/"+nomDossier+"/.project"));
						//}
					}
				}
			}
			//paramParamNouveauDossier.getFocusDefaut().requestFocus();
		} catch (Exception e) {
			logger.error("Erreur : run", e);
		}

	}

}
