package fr.legrain.gestionDossier.handlers;
import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import fr.legrain.gestCom.Appli.IB_APPLICATION;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestionCommerciale.OuvreDossier;
import fr.legrain.gestionCommerciale.UtilWorkspace;
import fr.legrain.lib.data.LibChaine;


public class HandlerRenommerDossier extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerRenommerDossier.class.getName());
	
	private IWorkbenchWindow window = null;

	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		renommerDossier();
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
	
	public void renommerDossier(){
		try {			
			/////////////////////////////////////////////////////////////////////////////////////////////////
			
			UtilWorkspace uw = new UtilWorkspace();
			IProject projectOrigine=uw.findOpenProject();			
			String dossierOrigine = projectOrigine.getName();
			if(MessageDialog.openConfirm(PlatformUI.getWorkbench().
					getActiveWorkbenchWindow().getShell(),"Renommer le dossier","Etes-vous sûr de vouloir renommer le dossier '"+dossierOrigine+"' ?")){
				InputDialog input = new InputDialog(window.getShell(),"Renommer le dossier : '"+dossierOrigine+"'","Remplacer par : (Entrez le nouveau nom de dossier)",null,new IInputValidator(){

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

//				InputDialog input = new InputDialog(window.getShell(),"Renommer le dossier : '"+dossierOrigine+"'","Remplacer par : (Entrez le nouveau nom de dossier)",null,null);
				input.open();
				if(input.getValue()!=null){
					String newNomDossier = input.getValue().toLowerCase();
					String path =uw.openProjectLocationPath();
					String projectPath = 
						path.substring(0, path.lastIndexOf(uw.openProjectName())) ;
					OuvreDossier i = new OuvreDossier();

					try {			
						//fermeture du projet courant et de la base

						projectOrigine.close(null);
						i.fermeture();

						//renommer le dossier courant avec le nouveau nom de dossier
						File fileOrigine =new File(projectPath+"/"+dossierOrigine);
						if(fileOrigine.renameTo(new File(projectPath+"/"+newNomDossier))){
							//projectOrigine.copy(new Path(projectPath+"/"+newNomDossier), true, null);

							File projectFile = new File(projectPath+"/"+newNomDossier+"/.project");
							if(projectFile.exists())projectFile.delete();
							projectFile = new File(projectPath+"/"+newNomDossier+"/.bdg");
							if(projectFile.exists())projectFile.delete();
							projectFile = new File(projectPath+"/"+newNomDossier+"/"+dossierOrigine+".bdg");
							if(projectFile.exists())projectFile.delete();
							uw.initDossier(projectPath, newNomDossier);					
							projectFile = new File(projectPath+"/"+newNomDossier+"/.project");					
							i.ouverture(projectFile);

							//suppression du projet d'origine
							projectOrigine.delete(true, null);
						}else{
							MessageDialog.openConfirm(PlatformUI.getWorkbench().
									getActiveWorkbenchWindow().getShell(),"Renommer le dossier","Le dossier '"+dossierOrigine+"' n'a pas pu être renommé !!! ");
							projectOrigine.open(null);
							i.ouverture(new File(projectPath+"/"+projectOrigine.getName()+"/.project"));
						}
					} catch (Exception e) {
						logger.error("",e);
						projectOrigine.open(null);
						i.ouverture(new File(projectPath+"/"+projectOrigine.getName()+"/.project"));
					}
				}
			}
		} catch (Exception e) {
			logger.error("Erreur : run", e);
		}
	}




}

