package fr.legrain.gestionCommerciale;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.eclipse.ant.core.AntRunner;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.firebirdsql.gds.impl.GDSType;
import org.firebirdsql.management.FBUser;
import org.firebirdsql.management.FBUserManager;
import org.osgi.framework.Bundle;

import fr.legrain.changelog.ws.client.ChangelogWebServiceClientCXF;
import fr.legrain.dossier.dao.TaVersion;
import fr.legrain.dossier.dao.TaVersionDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.IB_APPLICATION;
import fr.legrain.gestCom.Appli.ServicePreferenceDossier;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.gestCom.global.VERSION;
import fr.legrain.gestCom.librairiesEcran.swt.PasswordDialog;
import fr.legrain.gestionCommerciale.extensionPoints.ExecLancementExtension;
import fr.legrain.gestionCommerciale.preferences.PreferenceConstants;
import fr.legrain.lib.data.FireBirdManager;
import fr.legrain.lib.data.JPABdLgr;
import fr.legrain.lib.data.LgrConstantes;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.services.IServicePreferenceDossier;

public class UtilWorkspace {
	
	static Logger logger = Logger.getLogger(Application.class.getName());
	
	private String workplaceURL  = Platform.getInstanceLocation().getURL().getFile();

	private List<String[]> fichiersConfig = new ArrayList<String[]>();
	private List<String[]> fichiersSQL = new ArrayList<String[]>();
	private List<String[]> fichiersInsertSQL = new ArrayList<String[]>();
	private List<String[]> fichiersMAJSQL = new ArrayList<String[]>();
	private List<String[]> fichiersMAJBASE = new ArrayList<String[]>();
	
	private static IProject projectTmp = null;
	private static final String C_NOM_PROJET_TMP = "tmp"; //répertoire de travail commun, fichiers temporaires (creation base,...)
	/*
	 * répertoire liasse, liasse et analyse eco, constante redefinie ici pour ne pas creer de dependance avec le plugin liasse.
	 * Le meme nom de dossier est definie dans UtilWorkspaceLiasse 'C_NOM_DOSSIER_DEFAUT' dans le plugin liasse.
	 * Il faudrait trouve une meilleure facon de gerer le dossier liasse.
	 */
	private static final String C_NOM_PROJET_LIASSE = "liasse"; 
	
	//private static final String C_FICHIER_CREATION_DB_BAT = "Creation_Base.bat";
	//private static final String C_FICHIER_CREATION_DB_SH = "Creation_Base.sh";
	private static final String C_FICHIER_SHELL_CREATION_DB = "init_bdd.sh";
	public static final String C_FICHIER_CONFIG_FIREBIRD="c:/program files/firebird/firebird_2_0/aliases.conf";
	public static final String C_FICHIER_CONFIG_FIREBIRD_LINUX="/opt/firebird/aliases.conf";


	
	private static final String C_FICHIER_BAT_ADD_USER = "addDbUser.bat";
	private static final String C_FICHIER_ANT_CREATION_DB = "db.build.xml";
	private static final String C_FICHIER_ANT_MAJ_DB = "db.build_MAJ.xml";
	private static final String C_FICHIER_ANT_MAJ_VERSION_DB = "db.build_MAJ_Version.xml";
	private static final String C_FICHIER_ANT_MAJ_VERSION_DB_LINUX = "db.build_MAJ_Version.linux.xml";
	private static final String C_FICHIER_ANT_MAJ_DB_LINUX = "db.build_MAJ.linux.xml";
	//private static final String C_FICHIER_ANT_CREATION_DB_LINUX = "db.build.linux.xml";
	
	private static final String C_FICHIER_DB_ANCIEN = "Gest_Com.fdb";
	public static final String C_FICHIER_DB = "GEST_COM.FDB";
	private static final String C_FICHIER_DB_BACKUP = "GEST_COM";

	private static final String C_FICHIER_DB_UPPER = "GEST_COM.FDB";
	
	public static final String C_DOSSIER_DB = "Bd";
	private static final String C_DOSSIER_SQL_CREATION = "Creation_Base";
	private static final String C_DOSSIER_SQL_INSERTION = "Requete_Essai";
	private static final String C_DOSSIER_SQL_MAJ = "MAJ";
	
	private ProjectScope ps = null; 
	private String pass = null;
	
	public UtilWorkspace() {
		if(projectTmp==null) {
			initTmp();
		}
		
		//fichiers spécifiques à l'OS
		//Faire des fragments pour chaque OS
		//Windows
		if(Platform.getOS().equals(Platform.OS_WIN32)) {
		//	fichiersConfig.add(new String[] {C_FICHIER_CREATION_DB_BAT, "/Bd"});			
		//	fichiersConfig.add(new String[] {"isql.exe", "/Bd"});
		//	fichiersConfig.add(new String[] {"fbclient.dll", "/Bd"});
			fichiersConfig.add(new String[] {C_FICHIER_ANT_CREATION_DB, "/Bd"});
			fichiersMAJBASE.add(new String[] {C_FICHIER_ANT_MAJ_DB, "/build"});
			fichiersMAJBASE.add(new String[] {C_FICHIER_ANT_MAJ_VERSION_DB, "/build"});
			fichiersMAJBASE.add(new String []{C_FICHIER_BAT_ADD_USER, "/build"});
			
		}
		
		//Linux
		else if(Platform.getOS().equals(Platform.OS_LINUX) 
				|| Platform.getOS().equals(Platform.OS_MACOSX)) {
			fichiersConfig.add(new String[] {C_FICHIER_SHELL_CREATION_DB, "/Bd"});
			//fichiersConfig.add(new String[] {C_FICHIER_CREATION_DB_SH, "/Bd"});
			//fichiersConfig.add(new String[] {"isql", "/Bd"});
			//fichiersConfig.add(new String[] {"libfbclient.so", "/Bd"});
			//fichiersConfig.add(new String[] {"libfbclient.so.1", "/Bd"});
			//fichiersConfig.add(new String[] {"libfbclient.so.1.5.2", "/Bd"});
			//fichiersConfig.add(new String[] {"libib_util.so", "/Bd"});
			//fichiersConfig.add(new String[] {C_FICHIER_ANT_CREATION_DB_LINUX, "/Bd"});
			//fichiersConfig.add(new String[] {C_FICHIER_ANT_MAJ_DB_LINUX, "/Bd"});
			fichiersConfig.add(new String[] {C_FICHIER_ANT_CREATION_DB, "/Bd"});
			fichiersMAJBASE.add(new String[] {C_FICHIER_ANT_MAJ_DB, "/build"});
			fichiersMAJBASE.add(new String[] {C_FICHIER_ANT_MAJ_VERSION_DB, "/build"});
			fichiersMAJBASE.add(new String []{C_FICHIER_BAT_ADD_USER, "/build"});
		}
		
		//fichiers ini / properties généraux
		fichiersConfig.add(new String[] {"CtrlBD.ini", "/Bd"});
		fichiersConfig.add(new String[] {"IDBD.ini", "/Bd"});
		fichiersConfig.add(new String[] {"TitreBD2.lst", "/Bd"});
		fichiersConfig.add(new String[] {"log4j.properties", "/Bd"});
		fichiersConfig.add(new String[] {"GestCode.properties", "/Bd"});
		fichiersConfig.add(new String[] {"Modif.properties", "/Bd"});
		fichiersConfig.add(new String[] {"ChampMaj.ini", "/Bd"});
		fichiersConfig.add(new String[] {"ToutVenant.ini", "/Bd"});
		fichiersConfig.add(new String[] {"ListeChampGrille.properties", "/Bd"});
		fichiersConfig.add(new String[] {"AttributeTableEdition.properties", "/Bd"});
		fichiersConfig.add(new String[] {"AttributeModelLettre.properties", "/Bd"});
		fichiersConfig.add(new String[] {"AttributeEtiquette.properties", "/Bd"});
		fichiersConfig.add(new String[] {"PreferencePage.properties", "/Bd"});
		fichiersConfig.add(new String[] {"sauvegardeFTP.properties", "/Bd"});
		
		//fichiersConfig.add(new String[] {"GEST_COM.FDB", "/Bd"}); //récupération de la "base de developpement"
		
		// fichiers création bdd

		fichiersSQL.add(new String[] {"DescriptionDomaines.SQL", "/Bd/Creation_Base"});
		fichiersSQL.add(new String[] {"DescriptionTables.SQL", "/Bd/Creation_Base"});
		fichiersSQL.add(new String[] {"DescriptionTablesOSC.SQL", "/Bd/Creation_Base"});
		fichiersSQL.add(new String[] {"DescriptionVues.SQL", "/Bd/Creation_Base"});
		fichiersSQL.add(new String[] {"DescriptionGenerator.SQL", "/Bd/Creation_Base"});
		
		fichiersSQL.add(new String[] {"DescriptionException.SQL", "/Bd/Creation_Base"});
		fichiersSQL.add(new String[] {"DescriptionUDF.SQL", "/Bd/Creation_Base"});
		fichiersSQL.add(new String[] {"DescriptionTriggers.SQL", "/Bd/Creation_Base"});
		fichiersSQL.add(new String[] {"DescriptionTablesOSC.SQL", "/Bd/Creation_Base"});

		
		fichiersSQL.add(new String[] {"DescriptionProcedures.SQL", "/Bd/Creation_Base"});
		
		
		// fichiers insert bdd
		fichiersInsertSQL.add(new String[] {"Insertion.SQL", "/Bd/Requete_Essai"});
		
		fichiersMAJSQL.add(new String[] {"MAJ_1.8.9.3-1.SQL", "/MAJ"});
		fichiersMAJSQL.add(new String[] {"MAJ_2.0.2-1.SQL", "/MAJ"});
		fichiersMAJSQL.add(new String[] {"MAJ_2.0.2-2.SQL", "/MAJ"});
		fichiersMAJSQL.add(new String[] {"MAJ_2.0.3-1.SQL", "/MAJ"});
		fichiersMAJSQL.add(new String[] {"MAJ_2.0.3-2.SQL", "/MAJ"});
		fichiersMAJSQL.add(new String[] {"MAJ_2.0.4-1.SQL", "/MAJ"});
		fichiersMAJSQL.add(new String[] {"MAJ_2.0.4-2.SQL", "/MAJ"});
		fichiersMAJSQL.add(new String[] {"MAJ_2.0.5-1.SQL", "/MAJ"});
		fichiersMAJSQL.add(new String[] {"MAJ_2.0.5-2.SQL", "/MAJ"});
		fichiersMAJSQL.add(new String[] {"MAJ_2.0.6-1.SQL", "/MAJ"});
		fichiersMAJSQL.add(new String[] {"MAJ_2.0.6-2.SQL", "/MAJ"});
		fichiersMAJSQL.add(new String[] {"MAJ_2.0.7-1.SQL", "/MAJ"});
		fichiersMAJSQL.add(new String[] {"MAJ_2.0.8-1.SQL", "/MAJ"});
		fichiersMAJSQL.add(new String[] {"MAJ_2.0.8-2.SQL", "/MAJ"});
		fichiersMAJSQL.add(new String[] {"MAJ_2.0.8-3.SQL", "/MAJ"});
		fichiersMAJSQL.add(new String[] {"MAJ_2.0.9-1.SQL", "/MAJ"});		
		fichiersMAJSQL.add(new String[] {"MAJ_2.0.10-1.SQL", "/MAJ"});
		fichiersMAJSQL.add(new String[] {"MAJ_2.0.10-2.SQL", "/MAJ"});
		fichiersMAJSQL.add(new String[] {"MAJ_2.0.11-1.SQL", "/MAJ"});
		fichiersMAJSQL.add(new String[] {"MAJ_2.0.12-1.SQL", "/MAJ"});
		fichiersMAJSQL.add(new String[] {"MAJ_2.0.12-2.SQL", "/MAJ"});
		fichiersMAJSQL.add(new String[] {"MAJ_2.0.13-1.SQL", "/MAJ"});
		fichiersMAJSQL.add(new String[] {"MAJ_2.0.13-2.SQL", "/MAJ"});
		
		fichiersMAJSQL.add(new String[] {"MAJ_Version.SQL", "/MAJ"});
		fichiersMAJSQL.add(new String[] {"MAJ_Date_Version.SQL", "/MAJ"});

		
		fichiersMAJSQL.add(new String[] {"MAJ_DesactiveProc.SQL", "/MAJ"});
		fichiersMAJSQL.add(new String[] {"MAJ_Temporaire.SQL", "/MAJ"});
		fichiersMAJSQL.add(new String[] {"MAJ_RecreateProc.SQL", "/MAJ"});
	}
	
	/**
	 * <li>Initialisation des chemins
	 * <li>Initialisation du repertoire/projet temporaire
	 * <li>Ouverture et initialisation du dossier courant
	 */
	public void verifyWorkplace() {
		System.out.println("UtilWorkspace.verifyWorkplace()");
		changementChemin();
		
		//TODO creation d'un dossier par defaut
		initTmp();
		
		String[] args = Platform.getCommandLineArgs();
		//Passage de parametre : http://www.vogella.de/blog/2008/06/21/passing-parameters-to-eclipse-rcp-via-the-command-line/
		logger.info("== Command Line Args ==");
		for (int i = 0; i < args.length; i++) {
			
			if(args[i].endsWith(".bdg")) {
				logger.info(args[i]);
				//System.err.println(args[i]);
				OuvreDossier od = new OuvreDossier();
				////i.importDossier(new java.io.File(choix));
				try {
					od.ouverture(new java.io.File(args[i]));
				} catch (WorkbenchException e) {
					logger.error("",e);
				}
			}
		}
		logger.info("=========================");
		
		IProject prj = findOpenProject();

		if(prj==null){
			//new NouveauDossierAction().creerDossier();
			initDossier("demo");
		}
		else
			initDossier(prj.getName());
			//initDossier("c:/test/creation/de/projet","dossier");
			//initDossier("\\\\Isa\\c\\Documents and Settings\\All Users\\Documents","dossier");
			//initDossier("//Isa/c/Documents and Settings/All Users/Documents","dossier");
		    //initDossier("//Isa/c:/Documents and Settings/All Users/Documents","dossier");
			//initDossier("//192.168.0.8/shareddocs","dossier");
			//initDossier("//Isa/shareddocs","dossier");
			//initDossier("//Linux/nicolas","dossier");
		prj = findOpenProject();
//		IB_APPLICATION.changementConnectionBdd();
		ApplicationWorkbenchWindowAdvisor.setTitre("Dossier : "+prj.getName()+" - "
				+new File(Const.C_REPERTOIRE_PROJET).getPath()+ " ..... Base : "+new File(Const.C_FICHIER_BDD).getPath());
		
		ExecLancementExtension execLancementExtension = new ExecLancementExtension();
		execLancementExtension.createContributors();

	}
	
	/**
	 * Création/initialisation du répertoire temporaire
	 */
	public void initTmp() {
		IWorkspaceRoot root  = ResourcesPlugin.getWorkspace().getRoot();
		projectTmp  = root.getProject(C_NOM_PROJET_TMP);

		try {
			if (projectTmp.exists())
				projectTmp.delete(true, true, null);
			projectTmp.create(null);
			if (!projectTmp.isOpen()) 
				projectTmp.open(null);
		} catch (CoreException e) {
			logger.error(e);
		}
	}
	
	/**
	 * Creation d'un nouveau dossier dans le workspace
	 * @param name - nom du dossier
	 * @return
	 */
	public IProject initDossier(String name) {
		return initDossier(null,name);
//		try {
//			wakeUpProgressService();
//			return initDossierAvecProgress(null,name);
//		} catch (WorkbenchException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
	}
	
//	class Result {
//		IProject a = null;
//		IProject getA() {
//			return a;
//		}
//	}
//	
//	public static void wakeUpProgressService() {
//		IWorkbench wb = PlatformUI.getWorkbench();
//		wb.getProgressService();
//		//PlatformUI.getWorkbench().getDisplay().readAndDispatch();
//	}
//
//	
//	public IProject initDossierAvecProgress(final String projectPath,final String name) throws WorkbenchException {
//		try {
//			final Result r = new Result();
//
//			//Job job = new Job("ddddddddddddddd") {
//			IRunnableWithProgress job = new IRunnableWithProgress() {
//				//public IStatus run(IProgressMonitor monitor) {
//				public void run(IProgressMonitor monitor) throws InvocationTargetException,InterruptedException {
//					//final int ticks = 1000000;
//					monitor.beginTask("Ouverture du dossier", IProgressMonitor.UNKNOWN);
//					//monitor.beginTask("Ouverture du dossier", ticks);
//					try {
////						Thread t = new Thread() {
////							@Override
////							public void run() {
//
//								r.a = initDossier(null,name);		
//								//										monitor.worked(1);
//								//										if (monitor.isCanceled()) {
//								//											finalSpooler.clear();
//								//											return Status.CANCEL_STATUS;
//								//		
//								//PlatformUI.getWorkbench().getDisplay().readAndDispatch();
//								for (int i = 0; i < 1000000; i++) {
//									System.out.println("aa "+i);
////									monitor.worked(1);
////									PlatformUI.getWorkbench().getDisplay().readAndDispatch();
//								}
//								//}
//
////							}};
////							PlatformUI.getWorkbench().getDisplay().syncExec(t);
//					} finally {
//						monitor.done();
//					}
//					//return Status.OK_STATUS;
//				}
//
//				//@Override
//				//protected IStatus run(IProgressMonitor monitor) {
//				//// TODO Auto-generated method stub
//				//		return null;
//				//}
//			};
////			job.setPriority(Job.SHORT);
////			//job.setUser(true);
//////			PlatformUI.getWorkbench().getActiveWorkbenchWindow().run(false, false, job);
////			job.schedule(); 
////			job.join();
//			
//			ProgressMonitorDialog dialog = new ProgressMonitorDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
//			dialog.run(true, true, job);
//
//			return r.a;
//
//			//} catch(InterruptedException e) {
//		} catch(Exception e) {
//			logger.error("",e);
//		}
//		return null;
//	
//	}
	
	
	public IProject initDossier(String projectPath, String projectName,boolean dejaOuvert) {
		final String projectPathLoc =projectPath;
		final String projectNameLoc =projectName;
		final IProject retour=null;
		
//		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().getDisplay().asyncExec(new Runnable() {
//			@Override
//			public void run() {
				// TODO Auto-generated method stub
//				Job job = new Job("Initialisation de votre dossier") {
//					protected IStatus run(IProgressMonitor monitor) {
//						final int ticks = 1000;
//						monitor.beginTask("Initialisation de votre dossier", ticks);
						new JPABdLgr().fermetureConnection();
						initTmp();
						IFile handle = null;

						IWorkspaceRoot root  = ResourcesPlugin.getWorkspace().getRoot();
						//IWorkspace workspace = ResourcesPlugin.getWorkspace();

						//Projet/dossier
						IProject project  = root.getProject(projectNameLoc);

						//Repertoires
						/**
						 * pour créer les folder EX :  ModelLettre/OO et ModelLettre/WO
						 */
						IFolder folderModelLettre = project.getFolder(Const.FOLDER_MODEL_LETTRE);
						IFolder folderOO = project.getFolder(Const.FOLDER_MODEL_LETTRE+"/"+Const.MODEL_LETTRE_OO);
						IFolder folderWO = project.getFolder(Const.FOLDER_MODEL_LETTRE+"/"+Const.MODEL_LETTRE_WO);
						IFolder folderParamModelLettreOO = project.getFolder(Const.FOLDER_MODEL_LETTRE+"/"+Const.FOLDER_PARAM_LETTRE_OO);
						IFolder folderParamModelLettreWO = project.getFolder(Const.FOLDER_MODEL_LETTRE+"/"+Const.FOLDER_PARAM_LETTRE_WO);


						/**
						 * pour créer le folder de ParametreEtiquette EX :  parametresEtiquette
						 */
						IFolder folderParametresEtiquette = project.getFolder(Const.FOLDER_PARAMETRES_ETIQUETTE);
						IFolder folderParametresEtiquetteTiers = folderParametresEtiquette.getFolder(Const.FOLDER_PARAMETRES_ETIQUETTE_TIERS);
						IFolder folderParametresEtiquetteArticle = folderParametresEtiquette.getFolder(Const.FOLDER_PARAMETRES_ETIQUETTE_ARTICLE);
						IFolder folderEditions = project.getFolder(Const.FOLDER_EDITION_SUPP);
						IFolder folderImagesEditions = folderEditions.getFolder(Const.FOLDER_IMAGES_EDITIONS);
						
						IFolder folderImages = project.getFolder(Const.FOLDER_IMAGES);
						IFolder folderImagesArticles = folderImages.getFolder(Const.FOLDER_IMAGES_ARTICLES);
						IFolder folderImagesCategories = folderImages.getFolder(Const.FOLDER_IMAGES_CATEGORIES);
						IFolder folderImagesLabels = folderImages.getFolder(Const.FOLDER_IMAGES_LABELS);
						
						IFolder folderDatabase = project.getFolder(C_DOSSIER_DB);
						IFolder folderDatabaseTmp = projectTmp.getFolder(C_DOSSIER_DB);
						IFolder folderCreationDatabase = folderDatabaseTmp.getFolder(C_DOSSIER_SQL_CREATION);
						IFolder folderInsertDatabase   = folderDatabaseTmp.getFolder(C_DOSSIER_SQL_INSERTION);
						IFolder folderMajDatabase   = folderDatabaseTmp.getFolder(C_DOSSIER_SQL_MAJ);
						//	IFolder folderMajBase   = folderDatabaseTmp.getFolder(C_DOSSIER_SQL_MAJ);
						IFile   fileScriptShellCreationBD  = folderDatabaseTmp.getFile(C_FICHIER_SHELL_CREATION_DB);

						//Fichier à créer
						IFile fileDatabase    = folderDatabase.getFile(C_FICHIER_DB);
						IFile fileDatabase_ancien   = folderDatabase.getFile(C_FICHIER_DB_ANCIEN);

						IFile fileDatabaseUpper    = folderDatabase.getFile(C_FICHIER_DB_UPPER);
						IFile fileDatabaseTmp = folderDatabaseTmp.getFile(C_FICHIER_DB);

						try {
							project.refreshLocal(IResource.DEPTH_INFINITE, null);
							if (!project.exists()) 
							{
								Path pathWorkspace = new Path(Const.C_RCP_INSTANCE_LOCATION);
								Path pathProject=null;
								if(projectPathLoc!=null)
									pathProject = new Path(projectPathLoc);
								if(projectPathLoc==null || pathWorkspace.isPrefixOf(pathProject)  )
									//if(projectPath==null) //creation d'un nouveau projet dans le workspace
									project.create(null);
								else //creation d'un nouveau projet dans le dossier indiqué
									createProject(project,new Path(projectPathLoc+"/"+projectNameLoc),projectNameLoc);
							}
							//monitor.worked(1);
							if (!project.isOpen()) lgrOpenProject(project); //project.open(null);

							if (!folderDatabase.exists()) {
								logger.info("Création du répertoire de base de données dans "+projectNameLoc);
								folderDatabase.create(IResource.NONE, true, null);
							}

							if (!folderDatabaseTmp.exists()) {
								logger.info("Création du répertoire de base de données dans "+C_NOM_PROJET_TMP);
								folderDatabaseTmp.create(IResource.NONE, true, null);
							}

							if (!folderCreationDatabase.exists()) {
								logger.info("Création du répertoire de creation de la base de données dans "+projectNameLoc);
								folderCreationDatabase.create(IResource.NONE, true, null);
							}

							if (!folderInsertDatabase.exists()) {
								logger.info("Création du répertoire 'requete_essai' la base de données dans "+projectNameLoc);
								folderInsertDatabase.create(IResource.NONE, true, null);
							}	
							if (!folderEditions.exists()) {
								logger.info("Création du répertoire 'Edition' dans "+projectNameLoc);
								folderEditions.create(IResource.NONE, true, null);
								folderImagesEditions.create(IResource.NONE, true, null);
							}
							if (!folderImages.exists()) {
								logger.info("Création du répertoire 'images' dans "+projectNameLoc);
								folderImages.create(IResource.NONE, true, null);
								folderImagesArticles.create(IResource.NONE, true, null);
								folderImagesCategories.create(IResource.NONE, true, null);
								folderImagesLabels.create(IResource.NONE, true, null);
							}

							if (!folderModelLettre.exists()) {
								logger.info("Création du répertoire 'ModelLettre' dans "+projectNameLoc);
								folderModelLettre.create(IResource.NONE, true, null);
							}	

							if (!folderParamModelLettreOO.exists()) {
								logger.info("Création du répertoire 'ModelLettre/paramModelLettreOO' dans "+projectNameLoc);
								folderParamModelLettreOO.create(IResource.NONE, true, null);
							}		

							if (!folderParamModelLettreWO.exists()) {
								logger.info("Création du répertoire 'ModelLettre/paramModelLettreWO' dans "+projectNameLoc);
								folderParamModelLettreWO.create(IResource.NONE, true, null);
							}	

							if (!folderOO.exists()) {
								logger.info("Création du répertoire 'ModelLettre/OO' dans "+projectNameLoc);
								folderOO.create(IResource.NONE, true, null);
							}		

							if (!folderWO.exists()) {
								logger.info("Création du répertoire 'ModelLettre/WO' dans "+projectNameLoc);
								folderWO.create(IResource.NONE, true, null);
							}	

							if (!folderParametresEtiquette.exists()) {
								logger.info("Création du répertoire 'parametresEtiquette' dans "+projectNameLoc);
								folderParametresEtiquette.create(IResource.NONE, true, null);
							}
							
							if (!folderParametresEtiquetteArticle.exists()) {
								logger.info("Création du répertoire 'article' dans "+projectNameLoc);
								folderParametresEtiquetteArticle.create(IResource.NONE, true, null);
							}	
							
							if (!folderParametresEtiquetteTiers.exists()) {
								logger.info("Création du répertoire 'tiers' dans "+projectNameLoc);
								folderParametresEtiquetteTiers.create(IResource.NONE, true, null);
								
								//déplace les fichiers à la racine (créés par d'ancienne version) dans tiers
								File folderEtiquette = folderParametresEtiquette.getLocation().toFile();
								File folderEtiquettetiers = folderParametresEtiquetteTiers.getLocation().toFile();
								File[] f = folderEtiquette.listFiles();
								for (int i = 0; i < f.length; i++) {
									if(f[i].isFile()) {
										FileUtils.moveFileToDirectory(f[i], folderEtiquettetiers, false);
									}
								}
							}	


							if (!folderMajDatabase.exists()) {
								logger.info("Création du répertoire 'MAJ' la base de données dans "+projectNameLoc);
								folderMajDatabase.create(IResource.NONE, true, null);
							}			
							//test présence des fichiers de config
							for (String[] fichier : fichiersConfig) {
								handle = folderDatabase.getFile(fichier[0]);
								//if(handle.getName().equals("CtrlBD.ini")) {
								//	logger.info("Mise à jour forcée de CtrlBD.ini (pour version 2.0.9.2) pour ctrl CRD : "+fichier[0]+" dans "+projectNameLoc);
								//	copyRessource(handle,"GestionCommerciale",fichier[1]+"/"+fichier[0]);
								//}
								if(!handle.exists()) {
									logger.info("Ajout du fichier : "+fichier[0]+" dans "+projectNameLoc);
									copyRessource(handle,"GestionCommerciale",fichier[1]+"/"+fichier[0]);
								}
							}
							//monitor.worked(1);

							for (String[] fichier : fichiersConfig) {
								handle = folderDatabaseTmp.getFile(fichier[0]);
								if(!handle.exists()) {
									logger.info("Ajout du fichier : "+fichier[0]+" dans "+C_NOM_PROJET_TMP);
									copyRessource(handle,"GestionCommerciale",fichier[1]+"/"+fichier[0]);
								}
							}

							//test présence des fichiers sql
							for (String[] fichier : fichiersSQL) {
								handle = folderCreationDatabase.getFile(fichier[0]);
								if(!handle.exists()) {
									logger.info("Ajout du fichier : "+fichier[0]+" dans "+projectNameLoc);
									copyRessource(handle,"GestionCommerciale",fichier[1]+"/"+fichier[0]);
								}
							}

							//test présence des fichiers de Maj  
							for (String[] fichier : fichiersMAJBASE) {
								handle = folderDatabaseTmp.getFile(fichier[0]);
								if(!handle.exists()) {
									logger.info("Ajout du fichier : "+fichier[0]+" dans "+projectNameLoc);
									copyRessource(handle,"MiseAJourBase",fichier[1]+"/"+fichier[0]);
								}
							}

							//test présence des fichiers 'insert' sql 
							for (String[] fichier : fichiersInsertSQL) {
								handle = folderInsertDatabase.getFile(fichier[0]);
								if(!handle.exists()) {
									logger.info("Ajout du fichier : "+fichier[0]+" dans "+projectNameLoc);
									copyRessource(handle,"GestionCommerciale",fichier[1]+"/"+fichier[0]);
								}
							}

							//test présence des fichiers 'MAJ' sql 
							for (String[] fichier : fichiersMAJSQL) {
								handle = folderMajDatabase.getFile(fichier[0]);
								if(!handle.exists()) {
									logger.info("Ajout du fichier : "+fichier[0]+" dans "+projectNameLoc);
									copyRessource(handle,"MiseAJourBase",fichier[1]+"/"+fichier[0]);
								}
							}
							//monitor.worked(1);

							//test présence du fichier bdd
							if(Platform.getOS().equals(Platform.OS_MACOSX)) {
								if (!fileDatabase.exists()) {
									String dbPath = folderDatabaseTmp.getLocation().toString();
									if (!fileDatabaseTmp.exists()) {
										createDatabase(dbPath);
									}
								} else {
								logger.info("bdd presente");
								}
							}
							else if(Platform.getOS().equals(Platform.OS_LINUX)) {
								pass = GestionCommercialePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.SYSTEM_UNIX_PASSWORD_SUDO);
								//pass = "lgr00XXXX";
								boolean passOK = false;
								
								if(!pass.equals("")) {
									//test du mot de passe stocké dans les préférences
									String[] cmd3 = {"/bin/bash","-c","echo "+pass+"| sudo -S ls"}; 
									Process p3 = Runtime.getRuntime().exec(
											cmd3
											, null
											, null
											);
									p3.waitFor();
									if(p3.exitValue()!=0) {
										//le mot de passe stocké est incorrect
										MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "", "Mot de passe incorrect");

										do {
											if(pass.equals("") || !passOK) {
												PasswordDialog p = new PasswordDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Mot de passe utilisateur (sudo)");
												if(p.open() == Window.OK) {
													pass = p.getPassword();
													if(!pass.equals("")) {
														String[] cmd = {"/bin/bash","-c","echo "+pass+"| sudo -S ls"}; 
														Process p1 = Runtime.getRuntime().exec(
																cmd
																, null
																, null
																);
														p1.waitFor();
														logger.info(p1.exitValue());
														if(p1.exitValue()!=0) {
															MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "", "Mot de passe incorrect");
														} else {
															passOK = true;
															if(p.isEnregistrer()) {
																//GestionCommercialePlugin.getDefault().getPreferenceStore().setValue(PreferenceConstants.SYSTEM_UNIX_PASSWORD_SUDO,pass);
																IPreferenceStore store = GestionCommercialePlugin.getDefault().getPreferenceStore();
																store.setValue(PreferenceConstants.SYSTEM_UNIX_PASSWORD_SUDO,pass);
															}
														}
													}
													//	pass = null;
												} else {
													pass = null;
												}

												//$echo <password> | sudo -S <command>
												//Type sudo visudo at the terminal to open the sudo permissions (sudoers) file
												//Around line 25, you'll see this line: %sudo ALL=(ALL:ALL) ALL
												//Below that line, insert the following line, where username is your username:
												//username  ALL=(ALL) NOPASSWD: /home/username/pydatertc.sh
											}
										} while(!passOK && pass!=null);
									}
								}
									
								if (!fileDatabase.exists()) {
									String dbPath = folderDatabaseTmp.getLocation().toString();
									if (!fileDatabaseTmp.exists()) {
										ResourceAttributes resourceAttributes  = fileScriptShellCreationBD.getResourceAttributes();
										if(resourceAttributes!=null) {
											resourceAttributes.setExecutable(true);
											fileScriptShellCreationBD.setResourceAttributes(resourceAttributes);
										}
										if(pass==null) {
											Process p1 = Runtime.getRuntime().exec(
													//												new String[] {
													//														"gksu",
													//														"chmod","u+x",fileScriptShellCreationBD.getLocation().toFile().getAbsolutePath(),
													//														dbPath
													//												}
													new String[] {
															"gksu",
															"--message","Création de la base de données : "+dbPath,
															"chmod","u+x",fileScriptShellCreationBD.getLocation().toFile().getAbsolutePath(),
															dbPath
													}
													, null
													, new File(dbPath)
													);
											p1.waitFor();
											Process p = Runtime.getRuntime().exec(
													new String[] {
															"gksu",
															fileScriptShellCreationBD.getLocation().toFile().getAbsolutePath(),
															"dossier",
															dbPath
													}
													, null
													, new File(dbPath)
											);
											p.waitFor();
										} else {
											String[] cmd = {"/bin/bash","-c","echo "+pass+"| sudo -S chmod u+x "+fileScriptShellCreationBD.getLocation().toFile().getAbsolutePath()+" "+dbPath}; 
											Process p1 = Runtime.getRuntime().exec(
//													new String[] {
//															"echo",pass+" | sudo -S chmod u+x "+fileScriptShellCreationBD.getLocation().toFile().getAbsolutePath()+" "+dbPath
//													}
													cmd
													, null
													, new File(dbPath)
													);
											p1.waitFor();
											String[] cmd2 = {"/bin/bash","-c","echo "+pass+"| sudo -S "+fileScriptShellCreationBD.getLocation().toFile().getAbsolutePath()+" dossier "+dbPath};
											Process p = Runtime.getRuntime().exec(
//													new String[] {
//															"echo", pass+" | sudo -S "+fileScriptShellCreationBD.getLocation().toFile().getAbsolutePath()
//															+" dossier "+dbPath
//													}
													cmd2
													, null
													, new File(dbPath)
											);
											p.waitFor();
										}
										

										createDatabase(dbPath);

									}
									fileDatabaseTmp.refreshLocal(IResource.DEPTH_ZERO, null);
									if(pass==null) {
										Process p = Runtime.getRuntime().exec(
												new String[] {
														"gksu",
														fileScriptShellCreationBD.getLocation().toFile().getAbsolutePath(),
														"copie",
														fileDatabaseTmp.getLocation().toFile().getAbsolutePath(),
														fileDatabase.getLocation().toFile().getAbsolutePath()
												}
												, null
												, new File(dbPath)
												);
										p.waitFor();
									} else {
										String[] cmd2 = {"/bin/bash","-c","echo "+pass+"| sudo -S "+fileScriptShellCreationBD.getLocation().toFile().getAbsolutePath()+" copie "+fileDatabaseTmp.getLocation().toFile().getAbsolutePath()+" "+fileDatabase.getLocation().toFile().getAbsolutePath()};
										Process p = Runtime.getRuntime().exec(
//												new String[] {
//														"gksu",
//														fileScriptShellCreationBD.getLocation().toFile().getAbsolutePath(),
//														"copie",
//														fileDatabaseTmp.getLocation().toFile().getAbsolutePath(),
//														fileDatabase.getLocation().toFile().getAbsolutePath()
//												}
												cmd2
												, null
												, new File(dbPath)
										);
										p.waitFor();
									}

								} else {
									logger.info("bdd presente");
									//					//mise à jour de la version
									//					maj_NumVersion(folderDatabaseTmp.getLocation().toString(),folderDatabase.getLocation().toString());
									////					mise à jour de la base
									//					MAJDatabase(folderDatabaseTmp.getLocation().toString(),folderDatabase.getLocation().toString());
								}
							} else if(Platform.getOS().equals(Platform.OS_WIN32)) {

								if (!fileDatabase.exists()&& !fileDatabase_ancien.exists()) {
									String dbPath = folderDatabaseTmp.getLocation().toString();
									if (!fileDatabaseTmp.exists()) {
										createDatabase(dbPath);
									}
									fileDatabaseTmp.refreshLocal(IResource.DEPTH_ZERO, null);
									fileDatabaseTmp.copy(fileDatabase.getFullPath(), true, null);

									//File[] localFileRoots = File.listRoots();
									//for (int i = 0; i < localFileRoots.length; i++) {
									//	System.out.println("root : "+localFileRoots[i].getAbsolutePath());
									//}
								} else {
									logger.info("bdd presente");
								}
							}
							//monitor.worked(1);
							if(!dejaOuvert){
								lgrOpenProject(project);
								maj_NumVersion(folderDatabaseTmp.getLocation().toString(),folderDatabase.getLocation().toString());
								MAJDatabase(folderDatabaseTmp.getLocation().toString(),folderDatabase.getLocation().toString());			
							}
						} catch (CoreException e) {
							logger.error("",e);
							//monitor.done();
						} catch (InterruptedException e) {
							logger.error("",e);
//							monitor.done();
						} catch (IOException e) {
							logger.error("",e);
//							monitor.done();
						}
						//monitor.done();

//						return Status.OK_STATUS;
//					}
//				};
//				job.setPriority(Job.SHORT);
//				job.setSystem(true);
//				job.schedule();
//				try {
//					job.join();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			//}
//		});
		
		return findOpenProject();


	}

	/**
	 * Vérification de la présence des différents fichiers de configuration du dossier.
	 * Si un fichier est manquant il est re-créé. Si la base de données n'existe pas elle est crée sinon elle est mise a jour.
	 * @param projectPath - emplacement du nouveau dossier - si null, le nouveau dossier est créé dans le workspace
	 * @param projectName - nom du nouveau dossier
	 * @return
	 */
	public IProject initDossier(String projectPath, String projectName) {
		return initDossier(projectPath,projectName,false);
}
	
	/**
	 * re/creation du fichier .bdg a partir du fichier .project
	 * TODO Pour l'instant, si le fichier .bdg existe deja il n'est pas mis à jour
	 * @throws CoreException 
	 */
	private void createBdgFile(IProject project) throws CoreException {
		IFile bdgFile = project.getFile(project.getName()+".bdg");
		IFile oldBdgFile = project.getFile(".bdg"); //fichier .bdg sans le nom du dossier devant
		if(oldBdgFile.exists()) {
			oldBdgFile.delete(true, null);
		}
		if(!bdgFile.exists()) {
			IFile projectFile = project.getFile(".project");
			bdgFile.create(projectFile.getContents(true), true, null);
		}
	}
	
	
	
	
	public void createProject(IProject projectHandle,IPath path,String nomDuProjet) {
		IPath newPath = path;

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
        /*final*/ IProjectDescription description = workspace
                .newProjectDescription(projectHandle.getName());
        description.setLocation(newPath);

        // update the referenced project if provided
//        if (referencePage != null) {
//            IProject[] refProjects = referencePage.getReferencedProjects();
//            if (refProjects.length > 0)
//                description.setReferencedProjects(refProjects);
//        }

        // create the new project operation
    //    WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
    //        protected void execute(IProgressMonitor monitor)
    //               throws CoreException {
            	try {
					projectHandle.create(description,null);
				} catch (CoreException e) {
					logger.error(e);
				}
     //       }
     //   };

	}
	
	/**
	 * Copie le fichier <code>fichier</code> se trouvant dans le plugin <code>pluginId</code>
	 * dans la ressource <code>file</code>
	 * @param file - ressource où copier le fichier
	 * @param pluginId - plugin contenant le fichier à copier
	 * @param fichier - fichier à copier
	 */
	public void copyRessource(IFile file, String pluginId, String fichier) {
		Bundle bundle = Platform.getBundle (pluginId);
		URL urlFichier = bundle.getEntry (fichier);
		URL urlFichierFileSystem = null;
		try {
			urlFichierFileSystem = Platform.resolve (urlFichier);
			if (file.exists()) {
				file.delete(true,false,null);
			}
			if (!file.exists()) {
				// byte[] bytes = "File contents".getBytes();
			    // InputStream source = new ByteArrayInputStream(bytes);
				urlFichierFileSystem.openStream();
				file.create(urlFichierFileSystem.openStream(), IResource.NONE, null);
				// file.appendContents(urlFichierFileSystem.openStream(), true, false, null);
			}
		} catch (CoreException e) {
			logger.error("",e);
		} catch (IOException e) {
			logger.error("",e);
		}  catch (Exception e)  {
			//   IStatus status = ErrorFactory.createStatusObject (getID (), e, DataAccessErrorCodes.ERROR_UNABLE_TO_FIND_DATABASE);
			// throw new CoreException (status);
			logger.error("",e);
		}		
	}

	
	/**
	 * MAJ de la base de données
	 * @param dbPath - emplacement ou sera créé la bdd - contient les scripts de création (.bat ou .sh) et le fichier ant
	 * @throws InterruptedException 
	 */
	public Boolean MAJDatabase(final String fichierPath,final String dbPath) {
		Boolean retour=false;
		if(PlatformUI.getWorkbench().getActiveWorkbenchWindow()!=null) {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
		}
		
		MAJDatabaseForce(fichierPath,dbPath,false);
			TaVersionDAO daoVersion =new TaVersionDAO();
			TaVersion v =null;
			try {
				IB_APPLICATION.NettoyageBase();
			} catch (Exception e) {			
			}
			if(PlatformUI.getWorkbench().getActiveWorkbenchWindow()!=null) {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
			}
		
		return retour;
	}	
	

	
	/**
	 * MAJ de la base de données
	 * @param dbPath - emplacement ou sera créé la bdd - contient les scripts de création (.bat ou .sh) et le fichier ant
	 * @throws InterruptedException 
	 */
	public Boolean MAJDatabaseForce(final String directoryPath,final String dbPath,boolean force) {
		Boolean retour=false;
		if(PlatformUI.getWorkbench().getActiveWorkbenchWindow()!=null) {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
		}
			try {
				new JPABdLgr().fermetureConnection();
				IB_APPLICATION.fermetureConnectionBdd();
				VERSION v =null;
				//des modifications    /// !LgrMess.isDOSSIER_EN_RESEAU()&& 
				if (! IB_APPLICATION.verifMultiConnection())
				{
//					if(!(LgrMess.isDEVELOPPEMENT()&& !LgrMess.isTOUJOURS_MAJ())){					
						v=VERSION.getVERSION("1",null);//cette ligne permet de remonter le fichier de properties
						FireBirdManager fireBirdManager = new FireBirdManager();
						AntRunner antRunner = new AntRunner();
						String numVersion;
						String oldVersion;
						boolean SauvOk=true;
						//VERSION v = VERSION.getVERSION("1",null);
						oldVersion = v.getOLD_VERSION();
						numVersion = v.getNUM_VERSION();
						logger.info("OldVersion = "+oldVersion+" - NumVersion = "+numVersion);
						if(LgrMess.isTOUJOURS_MAJ()==true){
							InputDialog input = new InputDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()
									,"Numéro de version","Indiquer le numéro de version à partir duquel la mise à jour doit être faite.",oldVersion,new IInputValidator(){
								String[] listeVersionsOk =new String[]{"2.0.6","2.0.8","2.0.9","2.0.10","2.0.11","2.0.12"};
								String[] listeVersionsMaintenance =new String[]{};
								public String isValid(String newText) {
									for (int i = 0; i < listeVersionsOk.length; i++) {
										if(listeVersionsOk[i].equalsIgnoreCase(newText))
											return null;
									}
									if(LgrMess.isDEVELOPPEMENT()){
										for (int i = 0; i < listeVersionsMaintenance.length; i++) {
											if(listeVersionsMaintenance[i].equalsIgnoreCase(newText))
												return null;
										}}
									if(!newText.equals(""))
										return "Ce numéro n'est pas valide.";
									else return null;
								}

							});
							input.open();
							oldVersion=input.getValue();
							if(oldVersion==null)
								throw new Exception("abandon de la mise à jour");
							else initDossier(null,findOpenProject().getName(),true);
						}		
						String fileBackup;
		
						if(oldVersion == null)oldVersion="0";
						if(numVersion == null)numVersion="0";
						if (oldVersion.equals(numVersion))
							fileBackup=C_FICHIER_DB_BACKUP+".FBK";
						else fileBackup=C_FICHIER_DB_BACKUP+"_"+oldVersion+".FBK";
						try {
							logger.info("Mise à jour de la base de données");
							if (!oldVersion.equals(numVersion)) MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
									"Mise à jour de la base vers version n° "+numVersion,"Une mise à jour de la base doit être effectuée, elle peut-être longue.");
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));		
							if(logger.isDebugEnabled())
								logger.info(directoryPath+"/"+C_FICHIER_ANT_MAJ_DB);		

							if(Platform.getOS().equals(Platform.OS_WIN32)) {
								//scriptCreationBase = C_FICHIER_CREATION_DB_BAT;
								antRunner.setBuildFileLocation(directoryPath+"/"+C_FICHIER_ANT_MAJ_DB);
							} else if(Platform.getOS().equals(Platform.OS_LINUX)
									|| Platform.getOS().equals(Platform.OS_MACOSX)) {
								antRunner.setBuildFileLocation(directoryPath+"/"+C_FICHIER_ANT_MAJ_DB);
							}
							String serveur=Const.C_SERVEUR_BDD+"/3050:";
							String cheminBase=Const.C_FICHIER_BDD.replace(serveur, "");
							if(!serveur.toLowerCase().contains("localhost"))
								antRunner.setArguments(new String[]{"-Ddb="+cheminBase,"-DuserLoc="+Const.C_USER,
										"-DpassLoc="+Const.C_PASS,"-DoldVersion="+oldVersion,"-Dpath="+dbPath,"-DdebutURLLoc="+serveur});
							else
							antRunner.setArguments(new String[]{"-Ddb="+dbPath+"/"+C_FICHIER_DB,"-DuserLoc="+Const.C_USER,
									"-DpassLoc="+Const.C_PASS,"-DoldVersion="+oldVersion,"-Dpath="+dbPath,"-DdebutURLLoc="+serveur});
							try {
								if(Platform.getOS().equals(Platform.OS_LINUX)) {
									if(pass==null) {
										Process p1 = Runtime.getRuntime().exec(
												new String[] {
														"gksu",
														"--message","Mise à jour de la base de données : "+dbPath,
														"chmod","u+w,g+w,o+w",dbPath,
														dbPath
												}
												, null
												, new File(dbPath)
												);
										p1.waitFor();
									} else {
										String[] cmd = {"/bin/bash","-c","echo "+pass+"| sudo -S chmod u+w,g+w,o+w "+dbPath+" "+dbPath};
										Process p1 = Runtime.getRuntime().exec(
												cmd
												, null
												, new File(dbPath)
												);
										p1.waitFor();
									}
								}
//								FonctionSauvegarde fonctionSauvegarde = new FonctionSauvegarde();
//								fonctionSauvegarde.sauvegarde();
								fireBirdManager.sauvegardeDB(dbPath,C_FICHIER_DB_UPPER,fileBackup,Const.C_USER,Const.C_PASS);
								if(Platform.getOS().equals(Platform.OS_LINUX)) {
									if(pass==null) {
										Process p1 = Runtime.getRuntime().exec(
												new String[] {
														"gksu",
														"chmod","u+w,g+w,o+w",dbPath+"/"+fileBackup,
														dbPath
												}
												, null
												, new File(dbPath)
												);
										p1.waitFor();
									} else {
										String[] cmd = {"/bin/bash","-c","echo "+pass+"| sudo -S chmod u+w,g+w,o+w "+dbPath+"/"+fileBackup+" "+dbPath};
										Process p1 = Runtime.getRuntime().exec(
												cmd
												, null
												, new File(dbPath)
												);
										p1.waitFor();
									}
								}
								if(Platform.getOS().equals(Platform.OS_LINUX)) {
									if(pass==null) {
										Process p1 = Runtime.getRuntime().exec(
												new String[] {
														"gksu",
														"chmod","u+r,g+r,o+r",dbPath+"/"+fileBackup,
														dbPath
												}
												, null
												, new File(dbPath)
												);
										p1.waitFor();
									} else {
										String[] cmd = {"/bin/bash","-c","echo "+pass+"| sudo -S chmod u+r,g+r,o+r "+dbPath+"/"+fileBackup+" "+dbPath};
										Process p1 = Runtime.getRuntime().exec(
												//												new String[] {
												//														"gksu",
												//														"chmod","u+r,g+r,o+r",dbPath+"/"+fileBackup,
												//														dbPath
												//												}
												cmd
												, null
												, new File(dbPath)
												);
										p1.waitFor();
									}
								}
							}catch (Exception e1) {
								logger.info("Problème lors de la sauvegarde");
								SauvOk=false;
							}
							if (!oldVersion.equals(numVersion)){
								antRunner.setExecutionTargets(new String[]{"MAJ_"+oldVersion});//+oldVersion
								antRunner.run();

//								try {
//									fireBirdManager.CompactDB(dbPath,C_FICHIER_DB_UPPER,"admin","###_PASSWORD_FB_BDG_###");
//									fireBirdManager.sauvegardeDB(dbPath,C_FICHIER_DB_UPPER,fileBackup,"admin","###_PASSWORD_FB_BDG_###");
//									IB_APPLICATION.SimpleDeConnectionBdd();
//									fireBirdManager.restaurationDB(dbPath,C_FICHIER_DB_UPPER,fileBackup,"admin","###_PASSWORD_FB_BDG_###");
//								}catch (Exception e1) {
//									if(Platform.getOS().equals(Platform.OS_LINUX)) {
//										logger.debug("Plateforme Linux => La restauration ne fonctionne pas correctement sur ce système. Pas de restauration.");
//									}
//									logger.debug("",e1);
//									logger.info("Problème lors de l'obtimisation de la base");
//									SauvOk=false;
//								}				
							}
							logger.info("Mise à jour de la base de données terminée");
							if (!oldVersion.equals(numVersion)) MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
									"Mise à jour de la base","Mise à jour de la base de données terminée");
							Platform.getBundle("Sauvegarde").start();
							retour=true;
						} catch (Exception e) {
							try{
								MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()
										,"Mise à jour base de données","Erreur durant la Mise à jour de la base de données."
										+LgrMess.C_SAUT_DE_LIGNE
										+"De la version : "+oldVersion+"  à la version : "+numVersion
										+LgrMess.C_CONTACTER_SERVICE_MAINTENANCE);
								logger.error("",e);
								if(SauvOk) {
//									IB_APPLICATION.SimpleDeConnectionBdd();
									IB_APPLICATION.fermetureConnectionBdd();
									fireBirdManager.restaurationDB(dbPath,C_FICHIER_DB_UPPER,fileBackup,Const.C_USER,Const.C_PASS);
									logger.info("Suite à un problème, restauration de la base de données terminée");
									if(Platform.getOS().equals(Platform.OS_LINUX)) {
										logger.debug("Plateforme Linux => La restauration ne fonctionne pas correctement sur ce système. Pas de restauration.");
									}
								}
							}catch (Exception e2){
								logger.error("",e2);
							}
						}
//					}else logger.info("Pas de MAJ de la base");
				}

			} catch (Exception e) {
				String mess;
				if(e.getMessage()!=null)mess=e.getMessage();
				else mess="Problème lors de la recherche de multi-connection";
				logger.debug(mess,e);
				retour= false;
			}
			TaVersionDAO daoVersion =new TaVersionDAO(); //Création d'un DAO pour initialiser l'EntityManagerFactory au lancement du programme
			TaVersion v =null;
////			IB_APPLICATION.fermetureConnectionBdd();
//			//v=daoVersion.findInstance();
			VERSION.getVERSION("1",null);//cette ligne permet de remonter les fichiers de properties
			try {
				IB_APPLICATION.NettoyageBase();
			} catch (Exception e) {			
			}
			if(PlatformUI.getWorkbench().getActiveWorkbenchWindow()!=null) {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
			}
		
		return retour;
	}	

	/**
	 * Creation de la base de données
	 * @param dbPath - emplacement ou sera créé la bdd - contient les scripts de création (.bat ou .sh) et le fichier ant
	 */
	public void createDatabase(String dbPath) {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()
		  .setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
		try {
			logger.info("Création de la base de données");
			FireBirdManager fireBirdManager = new FireBirdManager();
			fireBirdManager.createDB(dbPath);
			AntRunner antRunner = new AntRunner(); 
			if(logger.isDebugEnabled())
				logger.info(dbPath+"/"+C_FICHIER_ANT_CREATION_DB);
			
			//String scriptCreationBase = null;
			if(Platform.getOS().equals(Platform.OS_WIN32)) {
				//scriptCreationBase = C_FICHIER_CREATION_DB_BAT;
				antRunner.setBuildFileLocation(dbPath+"/"+C_FICHIER_ANT_CREATION_DB);
			} else if(Platform.getOS().equals(Platform.OS_LINUX)
					|| Platform.getOS().equals(Platform.OS_MACOSX)) {
				//scriptCreationBase = C_FICHIER_CREATION_DB_SH;
				//antRunner.setBuildFileLocation(dbPath+"/"+C_FICHIER_ANT_CREATION_DB_LINUX);
				antRunner.setBuildFileLocation(dbPath+"/"+C_FICHIER_ANT_CREATION_DB);
			}
		
			//antRunner.setArguments("\"-Dscript="+dbPath+"/"+scriptCreationBase+"\"");
			antRunner.setArguments(new String[]{"-Ddb="+dbPath+"/"+C_FICHIER_DB,"-DuserLoc="+Const.C_USER,"-DpassLoc="+Const.C_PASS});
//			antRunner.setArguments("\"-DUser="+Const.C_USER);
//			antRunner.setArguments("\"-DPass="+Const.C_PASS);
			antRunner.run();
			
//////////////////////////////////////////////////////////////////////////////////////////		
//			ProcessBuilder pb = new ProcessBuilder("cmd","/c",dbPath+"/"+scriptCreationBase);
//			Map<String, String> env = pb.environment();
//			// env.put("VAR1", "myValue");
//			// env.remove("OTHERVAR");
//			// env.put("VAR2", env.get("VAR1") + "suffix");
//			pb.directory(new File(dbPath));
//			Process p = null;
//			try {
//				p = pb.start();
//				p.waitFor();
//			} catch (IOException e) {
//				logger.error("",e);
//			} catch (InterruptedException e) {
//				logger.error("",e);
//			}
//////////////////////////////////////////////////////////////////////////////////////////
			
			logger.info("Création de la base de données terminée");
			
		} catch (Exception e) {
			logger.error("",e);
		}
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()
		  .setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
	}
	
	/**
	 * @return - Le projet ouvert dans le workspace
	 */
	public IProject findOpenProject() {
		IProject projetCourant = null;
		IProject[] projets = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		boolean trouve = false;
		int i = 0 ;
		while(!trouve && i<projets.length) {
			if(projets[i].isOpen() 
					&& !projets[i].getName().equals(C_NOM_PROJET_TMP)
					&& !projets[i].getName().equals(C_NOM_PROJET_LIASSE) //evite l'ouverture automatique du dossier liasse
					) {
				trouve = true;
			}
			i++;
		}
		if(trouve) {
			projetCourant = projets[i-1];
			ps = new ProjectScope(projetCourant);
			Const.setProjectScopeContext(ps);
			Platform.getBundle(gestComBdPlugin.PLUGIN_ID).getBundleContext().registerService(IServicePreferenceDossier.class.getName(), new ServicePreferenceDossier(), null);
			Const.setProjectName(projetCourant.getName());
//			ps.getNode(projetCourant.getName()).put("aa", "test");
//			try {
//				ps.getNode(projetCourant.getName()).flush();
//			} catch (BackingStoreException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		if(logger.isInfoEnabled())
			logger.info("projetCourant ====> "+projetCourant);				
		return projetCourant;
	}

	
	/**
	 * @return - Le chemin du projet ouvert dans le workspace (chemin relatif par rapport au workspace) 
	 */
	public String openProjectRelativePath() {
		if(findOpenProject()==null)
			return "";
		else 
			return findOpenProject().getFullPath().toString();
	}
	
	/**
	 * @return - Le chemin du projet ouvert dans le workspace (chemin relatif par rapport au workspace) 
	 */
	public String openProjectName() {
		if(findOpenProject()==null)
			return "";
		else 
			return findOpenProject().getName().toString();
	}
	/**
	 * @return - Le chemin absolu (par rapport au système de fichiers) du projet ouvert
	 */
	public String openProjectLocationPath() {
		if(findOpenProject()==null)
			return "";
		else 
			return findOpenProject().getLocation().toString();
	}
	
	/**
	 * @return - vrai ssi le projet ouvert se trouve dans le workspace
	 */
	public boolean openProjectInWorkspace() {
		if(findOpenProject()!=null) {
			Path workspacePath = new Path(new File(new UtilWorkspace().getWorkplaceURL()).getAbsolutePath());
			return workspacePath.isPrefixOf(findOpenProject().getLocation());
		} else {
			return false;
		}
	}
	
	/**
	 * Ferme tous les projets ouvert du workspace puis ouvre le project passé en parametre
	 * Pour creer la connexion a la bdd penser à appeler <code>IB_APPLICATION.changementConnectionBdd();</code>
	 * @param project
	 * @throws CoreException
	 */
	public void lgrOpenProject(IProject project) throws CoreException {
		IProject[] projets = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (int i = 0; i < projets.length; i++) {
			if(!projets[i].getName().equals(C_NOM_PROJET_TMP))
				projets[i].close(null);
		}
		project.open(null);
		changementChemin();
		createBdgFile(project);
//		IB_APPLICATION.changementConnectionBdd();
		
	}
	
	public String ressourcePath(String pluginId, String fichier) {
		Bundle bundle = Platform.getBundle (pluginId);
		URL urlFichier = bundle.getEntry (fichier);
		//URL urlFichierFileSystem = null;
		try {
			//System.out.println(urlFichier);
			//System.out.println(Platform.resolve (urlFichier));
			//System.out.println(new java.io.File(Platform.resolve (urlFichier).toURI()).getAbsolutePath());
			return new java.io.File(Platform.resolve (urlFichier).toURI()).getAbsolutePath();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}	
	}
	
	/**
	 * Initialisation des constantes contenant des chemins
	 */
	private void changementChemin() {
		Const.C_REPERTOIRE_PROJET_IN_WORKSPACE = openProjectInWorkspace();
		if(openProjectInWorkspace())
			Const.C_REPERTOIRE_PROJET = openProjectRelativePath();
		else
			Const.C_REPERTOIRE_PROJET = openProjectLocationPath();
		Const.updatePath();
		
		//tester si chemin reseau ou local file system
		//local => localhost sinon nom/ip serveur
		//si reseau et windows chercher lettre de lecteur et ajouter 2 points
		if(findOpenProject()!=null) {
			IPath dbLocation = findOpenProject().getFolder(C_DOSSIER_DB).getFile(C_FICHIER_DB).getLocation();
			//lecture du fichier des alias pour rajouter eventuellement l'alias 
			//du dossier à ouvrir s'il n'existe pas déjà

				String preferences=GestionCommercialePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.ALIAS);
				if(!preferences.contains(","+findOpenProject().getName()+","))
					GestionCommercialePlugin.getDefault().getPreferenceStore().setValue(PreferenceConstants.ALIAS,
						GestionCommercialePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.ALIAS)+
						"localhost,"+findOpenProject().getName()+","+dbLocation.toOSString()+";");
			
			preferences=GestionCommercialePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.ALIAS);
			if(preferences.contains(","+findOpenProject().getName()+",")){
				String[] listeAlias=preferences.split(";");
				for (int i = 0; i < listeAlias.length; i++) {
					if(listeAlias[i].contains(","+findOpenProject().getName()+","))
						if(listeAlias[i].split(",").length>=3){
							Const.C_SERVEUR_BDD =listeAlias[i].split(",")[0];							
							if(Const.C_SERVEUR_BDD.startsWith("localhost")){
								Const.C_FICHIER_BDD =listeAlias[i].split(",")[2];
								Const.C_FICHIER_BDD=Const.C_SERVEUR_BDD+":"+Const.C_FICHIER_BDD;
							}
							else {
								Const.C_FICHIER_BDD =listeAlias[i].split(",")[2];
							}
							if(listeAlias[i].split(",").length>3)
								LgrMess.setDOSSIER_EN_RESEAU(LibConversion.StringToBoolean(listeAlias[i].split(",")[3]));
							else LgrMess.setDOSSIER_EN_RESEAU(false);
						}
				}
			}
//			LgrMess.setAfficheAideRemplie(GestionCommercialePlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.TYPE_AFFICHAGE_AIDE));
			LgrMess.setAfficheAideRemplie(false);
			if(dbLocation==null)findOpenProject().getFolder(C_DOSSIER_DB).getFile(C_FICHIER_DB_ANCIEN).getLocation();
			if(logger.isInfoEnabled())
				logger.info("dbLocation ====> "+LibChaine.lgrStringNonNull(dbLocation.toString()));
			if(dbLocation==null) MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Erreur d'ouverture",
			"localisation du fichier de base de données est incorrecte !!!");
			else{
//				if(dbLocation.isUNC()) { //TODO problème de portabilité
//					String serveur = dbLocation.segment(0);
//					String lecteur = dbLocation.segment(1);
//					logger.debug(dbLocation.toString().replaceFirst("/"+lecteur+"/", "/"+lecteur+":/"));
//					Const.C_SERVEUR_BDD = serveur;
//					if(findOpenProject().getName().equals("legrain_reseau")){
//						Const.C_SERVEUR_BDD ="lgrser.lgr/3050";
//						Const.C_FICHIER_BDD ="lgrser.lgr/3050:/home/legrain/bdg/legrain_reseau/Bd/GEST_COM.FDB";
//					}
//					else		
//						Const.C_FICHIER_BDD =dbLocation.toString().replaceFirst("/"+lecteur+"/", "/"+lecteur+":/");
//				} else {
//					Const.C_SERVEUR_BDD ="localhost";
//					if(findOpenProject().getName().equals("legrain_reseau")){
//						Const.C_SERVEUR_BDD ="lgrser.lgr/3050";
//						Const.C_FICHIER_BDD ="lgrser.lgr/3050:/home/legrain/bdg/legrain_reseau/Bd/GEST_COM.FDB";
//					}
//					else					
//						Const.C_FICHIER_BDD = Const.C_SERVEUR_BDD+":"+findOpenProject().getName();
//				}
//				
			}
		}


		//Const.setFichierCheminIni("C:/CheminFichier.properties");
		if(logger.isInfoEnabled())
			logger.info("url ====> "+Const.C_URL_BDD+Const.C_FICHIER_BDD);
	}

	public String getWorkplaceURL() {
		return workplaceURL;
	}
	
	static public void maj_NumVersion(String fichierPathTmp,String dbPath) {
		try {
			//if(LgrMess.isDEVELOPPEMENT()==false){
			String serveur=Const.C_SERVEUR_BDD+"/3050:";
			String cheminBase=Const.C_FICHIER_BDD.replace(serveur, "");
			//IB_APPLICATION.SimpleDeConnectionBdd();
			logger.info("Mise à jour du numéro de version");
			AntRunner antRunner = new AntRunner(); 
			if (!addDbUser());

			if(Platform.getOS().equals(Platform.OS_WIN32)) {
				antRunner.setBuildFileLocation(fichierPathTmp+"/"+C_FICHIER_ANT_MAJ_VERSION_DB);
			} else if(Platform.getOS().equals(Platform.OS_LINUX)
					|| Platform.getOS().equals(Platform.OS_MACOSX)) {
				antRunner.setBuildFileLocation(fichierPathTmp+"/"+C_FICHIER_ANT_MAJ_VERSION_DB);
			}
			if(!serveur.toLowerCase().contains("localhost"))
				antRunner.setArguments(new String[]{"-Ddb="+cheminBase,"-DuserLoc="+Const.C_USER
						,"-DpassLoc="+Const.C_PASS,"-DdebutURLLoc="+serveur});
			else
				antRunner.setArguments(new String[]{"-Ddb="+dbPath+"/"+C_FICHIER_DB,"-Dpath="+dbPath,"-DuserLoc="+Const.C_USER
						,"-DpassLoc="+Const.C_PASS,"-DdebutURLLoc="+serveur});
			antRunner.run();
			logger.info("Mise à jour du numéro de version terminée");
			//			}else
			//				logger.info("En version développement, pas de mise à jour du numéro de version !!!");				
		} catch (Exception e1) {
			logger.error("Erreur : maj_NumVersion", e1);	
		}
	}
	

	
	static public boolean addDbUser(){
		boolean trouve = false;
		FBUserManager fbUserManager = new FBUserManager(GDSType.getType("PURE_JAVA"));
		Iterator<Object> ele;
		try {
			
			fbUserManager.setHost(Const.C_SERVEUR_BDD);
			fbUserManager.setUser(LgrConstantes.C_USER_BDD_DEFAUT);
			fbUserManager.setPassword(LgrConstantes.C_PASS_BDD_DEFAUT);

			ele = fbUserManager.getUsers().keySet().iterator();
			while (ele.hasNext() && !trouve) {
				trouve = ele.next().toString().equals(Const.C_USER);
			}
			if (!trouve){
				FBUser  user = new FBUser ();
				user.setUserName(Const.C_USER);
				user.setPassword(Const.C_PASS);
				fbUserManager.add(user);
			}
			return trouve;
		} catch (SQLException e) {
			logger.error("",e);
			return trouve;
		} catch (IOException e) {
			logger.error("",e);
			return trouve;
		} 
	}
	
	public Boolean appelMAJDatabase(){
		IProject project  = ResourcesPlugin.getWorkspace().getRoot().getProject(findOpenProject().getName());
		if(project !=null){
			//Repertoires
			IFolder folderDatabase = project.getFolder(C_DOSSIER_DB);
			IFolder folderDatabaseTmp = projectTmp.getFolder(C_DOSSIER_DB);
			return MAJDatabaseForce(folderDatabaseTmp.getLocation().toString(),folderDatabase.getLocation().toString(),true);
			
		}
		return false;
	}
	
	  
	  


//	static public Boolean dossierEnReseau(){
//		Boolean result=false;
//		IProject projetCourant=ResourcesPlugin.getWorkspace().getRoot().getProject();
//		String preferences=GestionCommercialePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.ALIAS);
//		if(preferences.contains(","+projetCourant.getName()+",")){
//			String[] listeAlias=preferences.split(";");
//			for (int i = 0; i < listeAlias.length; i++) {
//				if(listeAlias[i].contains(","+projetCourant.getName()+","))
//					if(listeAlias[i].split(",").length>=3){
//						String reseau =listeAlias[i].split(",")[3];
//						result=LibConversion.StringToBoolean(reseau);
//					}
//			}
//		}
//		return result;
//	}

}
