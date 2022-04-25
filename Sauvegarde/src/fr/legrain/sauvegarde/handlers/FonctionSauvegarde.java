package fr.legrain.sauvegarde.handlers;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestionCommerciale.OuvreDossier;
import fr.legrain.gestionCommerciale.UtilWorkspace;
import fr.legrain.lib.compress.UtilZip;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.sauvegarde.SauvegardePlugin;
import fr.legrain.sauvegarde.preferences.PreferenceConstants;

public class FonctionSauvegarde {

	static Logger logger = Logger.getLogger(FonctionSauvegarde.class.getName());
	
	private Shell shell;
	
	
	public FonctionSauvegarde(){
		
	}
	
	public String sauvegarde(String pathSauvegarde,	boolean flagSauvegardeFTP, Shell shell,
			String fileExtentionZBG,boolean auto){
					
//		shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		String retour=null;
		try{
			if(LgrEditorPart.verifEditeurOuvert()!=null){
				MessageDialog.openInformation(shell,"Sauvegarde","Vous devez fermer tous les écrans avant de lancer la sauvegarde du dossier !");	
			}
			else{
				if(shell!=null) {
					shell.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
				}
				TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO();
				TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();

				UtilZip s = new UtilZip();

				String fichierSauvegarde = null;

				UtilWorkspace uw = new UtilWorkspace();
				String aSauvegarder = uw.openProjectLocationPath();
				fichierSauvegarde = "bdg_";
				
				//si dossier à sauvegarder s'appelle encore dossier et que l'info entreprise
				//est remplie, alors on prend l'info entreprise
				if(uw.openProjectName().equalsIgnoreCase("dossier") && 
						taInfoEntreprise!=null && taInfoEntreprise.getNomInfoEntreprise()!=null) {
					fichierSauvegarde = "bdg_";
					if(taInfoEntreprise.getNomInfoEntreprise().length()>15)
						fichierSauvegarde += taInfoEntreprise.getNomInfoEntreprise().substring(0,15);

					fichierSauvegarde = LibChaine.clearString(fichierSauvegarde,LibChaine.C_UNAUTHORIZED_CHAR_FILE_IN_NAME,'_');

				}else{//sinon, on prend le nom du dossier
					if(uw.openProjectName().length()>15)
					   fichierSauvegarde+=uw.openProjectName().substring(0,15);
					else
						fichierSauvegarde+=uw.openProjectName();
					fichierSauvegarde = LibChaine.clearString(fichierSauvegarde,LibChaine.C_UNAUTHORIZED_CHAR_FILE_IN_NAME,'_');
				}

				DateFormat df = new SimpleDateFormat ("_ddMMyyyy_HHmmss");
				Date d = new Date();
				if(!auto)fichierSauvegarde+=df.format(d)+fileExtentionZBG;
				else fichierSauvegarde+="_SauvegardeFermeture"+fileExtentionZBG;

				String emplacementSauvegarde = null;
				if(pathSauvegarde == null && shell!=null){
					DirectoryDialog dd = new DirectoryDialog(shell,SWT.SAVE);
					dd.setText("Choix d'un emplacement pour la sauvegarde : "+fichierSauvegarde);
					String repDestDefaut = SauvegardePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_EMPLACEMENT);
					dd.setFilterPath(LibChaine.pathCorrect(repDestDefaut));
					emplacementSauvegarde = dd.open();
				}else{
					emplacementSauvegarde = pathSauvegarde;
				}
				/**
				 * flagSauvegardeFTP ==> si true  : n'afficher pas message
				 * 					 ==> si false : afficher message
				 */
				if(flagSauvegardeFTP){
					s.zip(aSauvegarder,emplacementSauvegarde+"/"+fichierSauvegarde);
					retour=emplacementSauvegarde+"/"+fichierSauvegarde;
				}else{
					if(shell!=null){
//						if(emplacementSauvegarde!=null && shell!=null) {
							if(!emplacementSauvegarde.equalsIgnoreCase(SauvegardePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_EMPLACEMENT))){
								SauvegardePlugin.getDefault().getPreferenceStore().setDefault(PreferenceConstants.P_EMPLACEMENT,emplacementSauvegarde);
							}
							MessageDialog.openInformation(shell,"Sauvegarde","Emplacement de la sauvegarde.\n\r"+emplacementSauvegarde);
							s.zip(aSauvegarder,emplacementSauvegarde+"/"+fichierSauvegarde);
							retour=emplacementSauvegarde+"/"+fichierSauvegarde;
							MessageDialog.openInformation(shell,"Sauvegarde","Sauvegarde terminée.");
//						}
					}else{
						if(!auto){
						if(!emplacementSauvegarde.equalsIgnoreCase(SauvegardePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.EMPLACEMENT_SAUVEGARDE_FERMETURE))){
							SauvegardePlugin.getDefault().getPreferenceStore().setDefault(PreferenceConstants.EMPLACEMENT_SAUVEGARDE_FERMETURE,emplacementSauvegarde);
						}
						}
						new File(emplacementSauvegarde).mkdirs();
						s.zip(aSauvegarder,emplacementSauvegarde+"/"+fichierSauvegarde);
					}
				}
				
			}
		} catch(Exception e) {
			if(shell!=null) {
				MessageDialog.openInformation(shell,"Sauvegarde","Erreur durant la sauvegarde.");
			}
			logger.error("",e);
		}
		if(shell!=null) {
			shell.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		}
		
		return retour; 
		
	}

	public String sauvegarde(){
		
		return sauvegarde(null,false,shell,PreferenceConstants.fileExtentionZBG,false);
	}
	
	/*** restauration ***/
	
	public void nouveau(String repDestination,Shell shell,String fileName) {
		try {		
			if(LgrEditorPart.verifEditeurOuvert()!=null){
				MessageDialog.openInformation(shell,"Sauvegarde","Vous devez fermer tous les écrans " +
						"avant de lancer la restauration !");	
			}
			else{
				
				shell.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
				
				UtilZip s = new UtilZip();
				UtilWorkspace utilWordspace = new UtilWorkspace();

//				String repDestination = "C:/LGRDOSS/BureauDeGestion/";
//				String dossierAEffacer = "C:/LGRDOSS/BureauDeGestion/dossier";

//				String repDestination = Platform.getInstanceLocation().getURL().getFile();
//				String dossierAEffacer = Platform.getInstanceLocation().getURL().getFile()+"/dossier";
//				String repDestination = SauvegardePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_REP_DEST) ;

				//String dossierAEffacer = SauvegardePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_DOSSIER_A_SAUVEGARDER) ;
				String dossierAEffacer = utilWordspace.openProjectLocationPath();
				String RepDossier = utilWordspace.openProjectLocationPath().replace(utilWordspace.openProjectName(), "");
				String RepDossierAOuvrir = utilWordspace.openProjectLocationPath().replace(utilWordspace.openProjectName(), "");
				if(fileName != null){
					RepDossier  = repDestination;
				}
				 
//				repDestination=RepDossier;
				boolean continuer=false;
				continuer=MessageDialog.openConfirm(shell,"Répertoire destination",
						"Le répertoire de destination de la sauvegarde est : \n\r "+RepDossier+
						"\n\r Voulez-vous continuer");
				if(continuer){
					File dest = new File(repDestination);

					/**
					 * Choix du fichier a restaurer
					 */
					FileDialog dd = new FileDialog(shell,SWT.OPEN);
					dd.setText("Choix de la sauvegarde à restaurer");
					dd.setFilterExtensions(new String[] {"*"+PreferenceConstants.fileExtentionZBG,"*.zip"}); // !! chaine des extension dépend de l'OS
					dd.setFilterNames(new String[] {"sauvegarde bureau de gestion"});
					dd.setFilterPath(LibChaine.pathCorrect(repDestination));
					if(fileName != null){
						dd.setFileName(fileName);
					}
					
					String emplacementSauvegarde = dd.open();

					/*
					 * Choix de l'emplacement
					 */


					/*
					 * Sauvegarde de secours
					 */
					if(emplacementSauvegarde!=null) {
						repDestination = utilWordspace.openProjectLocationPath().replace(utilWordspace.openProjectName(), "");
						//if(MessageDialog.openConfirm(shell,"Restauration","Êtes-vous certains de vouloir restaurer :\n"+emplacementSauvegarde)) {
						if(MessageDialog.openConfirm(shell,"Restauration","Êtes-vous certains de vouloir restaurer :\n"+
								emplacementSauvegarde)) {
//							//Sauvegarde avant restauration
//							String svg = "C:/svgAvantRestauration.zip";
//							File svgAvRest = new File(svg); 
//							if(!svgAvRest.equals(new File(emplacementSauvegarde))) {
//							//on restaure svgAvRest donc pas de sauvegarde avant restauration
//							svgAvRest.delete();
//							s.zip(dossierAEffacer,svg);
//							}
							
							String nomDossierARestaurer ="";
							/*** Restauration (verif si dossier existe pas deja dans l'emplacement)	(code de isabelle ) ***/
							//String value1=emplacementSauvegarde.replaceAll("/", "").replaceAll("\\\\", "").replace(".zip", "");
							//String value1=emplacementSauvegarde.replaceAll("/", "").replaceAll("\\\\", "").replace(".zbg", "");
							String value1= utilWordspace.openProjectLocationPath().replace(utilWordspace.openProjectName(), "").
										  replaceAll("/", "").replaceAll("\\\\", "").replace(".zbg", "");
							String value2=dossierAEffacer.replaceAll("/", "").replaceAll("\\\\", "");
							if(value1.toLowerCase().contains(value2.toLowerCase())&&
									!value1.equalsIgnoreCase(value2)){
								MessageDialog.openWarning(shell,
										"ATTENTION", "La sauvegarde est contenu dans le dossier à supprimer. " +
								"Vous devez sauvegarder dans un autre dossier avant de restaurer");
								throw new Exception();
							}
							
							//on verifie que le dossier à restaurer n'existe pas déjà dans le répertoire de destination
			
							int depart = s.firstEntryName(emplacementSauvegarde).indexOf("\\");
							if (depart>0)
								nomDossierARestaurer = s.firstEntryName(emplacementSauvegarde).substring(0,depart);
							else nomDossierARestaurer = s.firstEntryName(emplacementSauvegarde);
							
							/*** Restauration (verif si dossier existe pas deja dans l'emplacement)	(code de zhaolin ) ***/
//							String fileNameRestauration = new File(emplacementSauvegarde).getName();
//							nomDossierARestaurer = fileNameRestauration.split("_")[1];
							/**********************************************/
							if(!utilWordspace.openProjectName().equalsIgnoreCase(nomDossierARestaurer)){
								MessageDialog.openInformation(shell,"Restauration","La sauvegarde : " +
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
								shell.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));

								OuvreDossier oD = new OuvreDossier();
								oD.fermeture();
								if(trouve) {
									//if(MessageDialog.openQuestion(window.getShell(),"Restauration","Le dossier '"+nomDossierARestaurer+"' existe déjà dans ce repertoire.\nContinuer ?")) {
									//deleteDir(new File(repDestination+"/"+s.firstEntryName(emplacementSauvegarde)));
									s.unzip(repDestination+"/",emplacementSauvegarde);
									MessageDialog.openInformation(shell,"Restauration","Restauration terminée.");
									//}
									//else MessageDialog.openInformation(window.getShell(),"Restauration","Restauration annulée.");
								} else {
									s.unzip(repDestination+"/",emplacementSauvegarde);
									MessageDialog.openInformation(shell,"Restauration","Restauration terminée.");
								}

								/*
								 * Ouverture du dossier restaure 
								 */
								try{
									//if(MessageDialog.openQuestion(window.getShell(),"Restauration","Ouvrir le dossier '"+nomDossierARestaurer+"' ?")) {
									File projectFile = new java.io.File(RepDossierAOuvrir+"/"+nomDossierARestaurer+"/.project");
									if(!projectFile.exists()){ 
										projectFile = new java.io.File(RepDossierAOuvrir+"/"+nomDossierARestaurer+"/.bdg");
										if(projectFile.exists())	
											oD.ouverture(new java.io.File(RepDossierAOuvrir+"/"+nomDossierARestaurer+"/.bdg"));								
									}else
										oD.ouverture(new java.io.File(RepDossierAOuvrir+"/"+nomDossierARestaurer+"/.project"));
									//}
								}catch(Exception e) {
									MessageDialog.openInformation(shell,"Ouverture","Erreur durant l'ouverture.");
								}
							}
						}
					}
				}}
		} catch(Exception e) {
			MessageDialog.openInformation(shell,"Restauration","Erreur durant la restauration.");
			logger.error("",e);
			e.printStackTrace();
		}finally{
			shell.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		}
	}

	public Shell getShell() {
		return shell;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}

	
	 public void sauvegardeAuto(){
		 if(SauvegardePlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.sauvegardeFermeture)){
			FonctionSauvegarde fonctionSauvegarde = new FonctionSauvegarde();
			fonctionSauvegarde.setShell(getShell());
			fonctionSauvegarde.sauvegarde(SauvegardePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.EMPLACEMENT_SAUVEGARDE_FERMETURE),false,getShell(),".Zbg",true);
		 }
	 }
}
