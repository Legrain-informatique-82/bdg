package fr.legrain.sauvegarde.handlers;
import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestionCommerciale.OuvreDossier;
import fr.legrain.gestionCommerciale.UtilWorkspace;
import fr.legrain.lib.compress.UtilZip;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.sauvegarde.SauvegardePlugin;
import fr.legrain.sauvegarde.preferences.PreferenceConstants;


public class HandlerRestauration extends LgrAbstractHandler {

	static Logger logger = Logger.getLogger(HandlerRestauration.class.getName());

	private IWorkbenchWindow window;

	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		FonctionSauvegarde fonctionSauvegarde = new FonctionSauvegarde();
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		fonctionSauvegarde.setShell(shell);
		String repDestination = SauvegardePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_REP_DEST) ;
		fonctionSauvegarde.nouveau(repDestination,shell,null);
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

	private void original() {
//		try {
//			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
//			.getShell().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
//			UtilZip s = new UtilZip();
//
////			String repDestination = "C:/LGRDOSS/BureauDeGestion/";
////			String dossierAEffacer = "C:/LGRDOSS/BureauDeGestion/dossier";
//
//			String repDestination = Platform.getInstanceLocation().getURL().getFile();
//			String dossierAEffacer = Platform.getInstanceLocation().getURL().getFile()+"/dossier";
////			String repDestination = RestaurationSauvegardePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_REP_DEST) ;
////			String dossierAEffacer = RestaurationSauvegardePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_DOSSIER_A_EFFACER) ;
//
//			File dest = new File(repDestination);
//
//			FileDialog dd = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),SWT.OPEN);
//			dd.setText("Choix de la sauvegarde à restaurer");
//			dd.setFilterExtensions(new String[] {"*.zip"}); // !! chaine des extension dépend de l'OS
//			dd.setFilterNames(new String[] {"dossier bureau de gestion"});
//			String emplacementSauvegarde = dd.open();
//
//			if(emplacementSauvegarde!=null) {
//				if(MessageDialog.openConfirm(window.getShell(),"Restauration","Êtes-vous certains de vouloir restaurer :\n"+emplacementSauvegarde)) {
//					//Sauvegarde avant restauration
//					String svg = "C:/svgAvantRestauration.zip";
//					File svgAvRest = new File(svg); 
//					if(!svgAvRest.equals(new File(emplacementSauvegarde))) {
//						//on restaure svgAvRest donc pas de sauvegarde avant restauration
//						svgAvRest.delete();
//						s.zip(dossierAEffacer,svg);
//					}
//					//Restauration
//					String value1=emplacementSauvegarde.replaceAll("/", "").replaceAll("\\\\", "");
//					String value2=dossierAEffacer.replaceAll("/", "").replaceAll("\\\\", "");
//					if(value1.toLowerCase().contains(value2.toLowerCase())){
//						MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
//								"ATTENTION", "La sauvegarde est contenu dans le dossier à supprimer. " +
//						"Vous devez sauvegarder dans un autre dossier avant de restaurer");
//						throw new Exception();
//					}
//					deleteDir(new File(dossierAEffacer));
//					s.unzip(repDestination,emplacementSauvegarde);
//					MessageDialog.openInformation(window.getShell(),"Restauration","Restauration terminée.");
//				}
//			}
//		} catch(Exception e) {
//			MessageDialog.openInformation(window.getShell(),"Restauration","Erreur durant la restauration.");
//			//logger.error("",e);
//			e.printStackTrace();
//		}finally{
//			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
//			.getShell().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
//		}
	}

	private void nouveau() {
		try {		
			if(LgrEditorPart.verifEditeurOuvert()!=null){
				MessageDialog.openInformation(window.getShell(),"Sauvegarde","Vous devez fermer tous les écrans avant de lancer la restauration !");	
			}
			else{
				window.getShell().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));

				UtilZip s = new UtilZip();
				UtilWorkspace utilWordspace = new UtilWorkspace();

//				String repDestination = "C:/LGRDOSS/BureauDeGestion/";
//				String dossierAEffacer = "C:/LGRDOSS/BureauDeGestion/dossier";

//				String repDestination = Platform.getInstanceLocation().getURL().getFile();
//				String dossierAEffacer = Platform.getInstanceLocation().getURL().getFile()+"/dossier";
				String repDestination = SauvegardePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_REP_DEST) ;

				//String dossierAEffacer = SauvegardePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_DOSSIER_A_SAUVEGARDER) ;
				String dossierAEffacer = utilWordspace.openProjectLocationPath();
				String RepDossier =utilWordspace.openProjectLocationPath().replace(utilWordspace.openProjectName(), ""); 
				repDestination=RepDossier;
				boolean continuer=false;
				continuer=MessageDialog.openConfirm(window.getShell(),"Répertoire destination","Le répertoire de destination de la sauvegarde est : \n\r "+RepDossier+
						"\n\r Voulez-vous continuer");
				if(continuer){
					File dest = new File(repDestination);

					/*
					 * Choix du fichier a restaurer
					 */
					FileDialog dd = new FileDialog(window.getShell(),SWT.OPEN);
					dd.setText("Choix de la sauvegarde à restaurer");
					dd.setFilterExtensions(new String[] {"*"+PreferenceConstants.fileExtentionZBG,"*.zip"}); // !! chaine des extension dépend de l'OS
					dd.setFilterNames(new String[] {"sauvegarde bureau de gestion"});
					dd.setFilterPath(LibChaine.pathCorrect(repDestination));
					String emplacementSauvegarde = dd.open();

					/*
					 * Choix de l'emplacement
					 */


					/*
					 * Sauvegarde de secours
					 */
					if(emplacementSauvegarde!=null) {
						if(MessageDialog.openConfirm(window.getShell(),"Restauration","Êtes-vous certains de vouloir restaurer :\n"+emplacementSauvegarde)) {
//							//Sauvegarde avant restauration
//							String svg = "C:/svgAvantRestauration.zip";
//							File svgAvRest = new File(svg); 
//							if(!svgAvRest.equals(new File(emplacementSauvegarde))) {
//							//on restaure svgAvRest donc pas de sauvegarde avant restauration
//							svgAvRest.delete();
//							s.zip(dossierAEffacer,svg);
//							}
							/*
							 * Restauration (verif si dossier existe pas deja dans l'emplacement)
							 */
							String value1=emplacementSauvegarde.replaceAll("/", "").replaceAll("\\\\", "").replace(".zip", "");
							String value2=dossierAEffacer.replaceAll("/", "").replaceAll("\\\\", "");
							if(value1.toLowerCase().contains(value2.toLowerCase())&&
									!value1.equalsIgnoreCase(value2)){
								MessageDialog.openWarning(window.getShell(),
										"ATTENTION", "La sauvegarde est contenu dans le dossier à supprimer. " +
								"Vous devez sauvegarder dans un autre dossier avant de restaurer");
								throw new Exception();
							}
							String nomDossierARestaurer ="";
							//on verifie que le dossier à restaurer n'existe pas déjà dans le répertoire de destination
							int depart = s.firstEntryName(emplacementSauvegarde).indexOf("\\");
							if (depart>0)
								nomDossierARestaurer = s.firstEntryName(emplacementSauvegarde).substring(0,depart);
							else nomDossierARestaurer = s.firstEntryName(emplacementSauvegarde);
							if(!LgrMess.isDEVELOPPEMENT()&& ! LgrMess.getUTILISATEUR().equals("isabelle") &&!utilWordspace.openProjectName().equalsIgnoreCase(nomDossierARestaurer)){
								MessageDialog.openInformation(window.getShell(),"Restauration","La sauvegarde : " +
										LgrMess.C_SAUT_DE_LIGNE+
										"'"+emplacementSauvegarde+"'" +
										LgrMess.C_SAUT_DE_LIGNE+
								"à restaurer ne correspond pas au dossier en cours !!!");
							}else{
								File destination = new File(repDestination);
								File[] liste = destination.listFiles();
								boolean trouve = false;
								int i = -1;
								while(!trouve && i+1<liste.length) {
									i++;
									if(liste[i].isDirectory() && liste[i].getName().equalsIgnoreCase(nomDossierARestaurer)) {
										trouve = true;
									}
								}
								window.getShell().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));

								OuvreDossier oD = new OuvreDossier();
								oD.fermeture();
								if(trouve) {
									//if(MessageDialog.openQuestion(window.getShell(),"Restauration","Le dossier '"+nomDossierARestaurer+"' existe déjà dans ce repertoire.\nContinuer ?")) {
									//deleteDir(new File(repDestination+"/"+s.firstEntryName(emplacementSauvegarde)));
									s.unzip(repDestination+"/",emplacementSauvegarde);
									MessageDialog.openInformation(window.getShell(),"Restauration","Restauration terminée.");
									//}
									//else MessageDialog.openInformation(window.getShell(),"Restauration","Restauration annulée.");
								} else {
									s.unzip(repDestination+"/",emplacementSauvegarde);
									MessageDialog.openInformation(window.getShell(),"Restauration","Restauration terminée.");
								}

								/*
								 * Ouverture du dossier restaure 
								 */
								try{
									//if(MessageDialog.openQuestion(window.getShell(),"Restauration","Ouvrir le dossier '"+nomDossierARestaurer+"' ?")) {
									File projectFile = new java.io.File(RepDossier+"/"+nomDossierARestaurer+"/.project");
									if(!projectFile.exists()){ 
										projectFile = new java.io.File(RepDossier+"/"+nomDossierARestaurer+"/.bdg");
										if(projectFile.exists())	
											oD.ouverture(new java.io.File(RepDossier+"/"+nomDossierARestaurer+"/.bdg"));								
									}else
										oD.ouverture(new java.io.File(RepDossier+"/"+nomDossierARestaurer+"/.project"));
									//}
								}catch(Exception e) {
									MessageDialog.openInformation(window.getShell(),"Ouverture","Erreur durant l'ouverture.");
								}
							}
						}
					}
				}}
		} catch(Exception e) {
			MessageDialog.openInformation(window.getShell(),"Restauration","Erreur durant la restauration.");
			logger.error("",e);
			e.printStackTrace();
		}finally{
			window.getShell().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		}

	}

	//Deletes all files and subdirectories under dir.
	// Returns true if all deletions were successful.
	// If a deletion fails, the method stops attempting to delete and returns false.
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i=0; i<children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

}
