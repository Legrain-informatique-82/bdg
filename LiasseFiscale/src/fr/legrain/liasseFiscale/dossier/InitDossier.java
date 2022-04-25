//package fr.legrain.liasseFiscale.dossier;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.log4j.Logger;
//import org.eclipse.ant.core.AntRunner;
//import org.eclipse.core.resources.IFile;
//import org.eclipse.core.resources.IFolder;
//import org.eclipse.core.resources.IProject;
//import org.eclipse.core.resources.IProjectDescription;
//import org.eclipse.core.resources.IResource;
//import org.eclipse.core.resources.IWorkspace;
//import org.eclipse.core.resources.IWorkspaceRoot;
//import org.eclipse.core.resources.ResourcesPlugin;
//import org.eclipse.core.runtime.CoreException;
//import org.eclipse.core.runtime.IPath;
//import org.eclipse.core.runtime.Path;
//import org.eclipse.core.runtime.Platform;
//import org.osgi.framework.Bundle;
//
//import fr.legrain.gestCom.Appli.Const;
//import fr.legrain.lib.data.FireBirdManager;
//
//public abstract class InitDossier {
//	
//	static Logger logger = Logger.getLogger(InitDossier.class.getName());
//	
//	private String workplaceURL  = Platform.getInstanceLocation().getURL().getFile();
//	
//	private List<String[]> fichiersConfig = new ArrayList<String[]>();
//	private List<String[]> fichiersSQL = new ArrayList<String[]>();
//	private List<String[]> fichiersInsertSQL = new ArrayList<String[]>();
//	
//	private static IProject projectTmp = null;
//	private static final String C_NOM_PROJET_TMP = "tmp"; //répertoire de travail commun, fichiers temporaires (creation base,...)
//	
//	private static final String C_FICHIER_CREATION_DB_BAT = "Creation_Base.bat";
//	private static final String C_FICHIER_CREATION_DB_SH = "Creation_Base.sh";
//	
//	private static final String C_FICHIER_ANT_CREATION_DB = "db.build.xml";
//	private static final String C_FICHIER_ANT_CREATION_DB_LINUX = "db.build.linux.xml";
//	
//	private static final String C_FICHIER_DB = "";
//	
//	private static final String C_DOSSIER_DB = "Bd";
//	private static final String C_DOSSIER_SQL_CREATION = "Creation_Base";
//	private static final String C_DOSSIER_SQL_INSERTION = "Requete_Essai";
//	
//	public InitDossier() {
//		if(projectTmp==null) {
//			initTmp();
//		}
//		
//		//fichiers spécifiques à l'OS
//		//Faire des fragments pour chaque OS
//		//Windows
//		if(Platform.getOS().equals(Platform.OS_WIN32)) {
//			fichiersConfig.add(new String[] {C_FICHIER_CREATION_DB_BAT, "/Bd"});
//			fichiersConfig.add(new String[] {"isql.exe", "/Bd"});
//			fichiersConfig.add(new String[] {"fbclient.dll", "/Bd"});
//			fichiersConfig.add(new String[] {C_FICHIER_ANT_CREATION_DB, "/Bd"});
//		}
//		
//		//Linux
//		else if(Platform.getOS().equals(Platform.OS_LINUX)) {
//			fichiersConfig.add(new String[] {C_FICHIER_CREATION_DB_SH, "/Bd"});
//			fichiersConfig.add(new String[] {"isql", "/Bd"});
//			fichiersConfig.add(new String[] {"libfbclient.so", "/Bd"});
//			fichiersConfig.add(new String[] {"libfbclient.so.1", "/Bd"});
//			fichiersConfig.add(new String[] {"libfbclient.so.1.5.2", "/Bd"});
//			fichiersConfig.add(new String[] {"libib_util.so", "/Bd"});
//			fichiersConfig.add(new String[] {C_FICHIER_ANT_CREATION_DB_LINUX, "/Bd"});
//		}
//		
//		//fichiers ini / properties générauxUtilWorkspace
//		fichiersConfig.add(new String[] {"CtrlBD.ini", "/Bd"});
//		fichiersConfig.add(new String[] {"IDBD.ini", "/Bd"});
//		fichiersConfig.add(new String[] {"TitreBD2.lst", "/Bd"});
//		fichiersConfig.add(new String[] {"log4j.properties", "/Bd"});
//		fichiersConfig.add(new String[] {"GestCode.properties", "/Bd"});
//		fichiersConfig.add(new String[] {"Modif.properties", "/Bd"});
//		fichiersConfig.add(new String[] {"ChampMaj.ini", "/Bd"});
//		fichiersConfig.add(new String[] {"ToutVenant.ini", "/Bd"});
//		
//		//fichiersConfig.add(new String[] {"GEST_COM.FDB", "/Bd"}); //récupération de la "base de developpement"
//		
//		// fichiers création bdd
//		//fichiersSQL.add(new String[] {"Creation_Domain.SQL", "/Bd/Creation_Base"});
//		
//		fichiersSQL.add(new String[] {"Description_Base_Tiers.SQL", "/Bd/Creation_Base"});
//		fichiersSQL.add(new String[] {"Description_Base_articles.SQL", "/Bd/Creation_Base"});
//		fichiersSQL.add(new String[] {"Description_base_factures.SQL", "/Bd/Creation_Base"});
//		
//		fichiersSQL.add(new String[] {"Description_Vues_Tiers.SQL", "/Bd/Creation_Base"});
//		fichiersSQL.add(new String[] {"Description_Vues_Articles.SQL", "/Bd/Creation_Base"});
//		fichiersSQL.add(new String[] {"Description_vues_Factures.SQL", "/Bd/Creation_Base"});
//		
//		fichiersSQL.add(new String[] {"Description_Triggers_Tiers.SQL", "/Bd/Creation_Base"});
//		fichiersSQL.add(new String[] {"Description_Triggers_articles.SQL", "/Bd/Creation_Base"});
//		fichiersSQL.add(new String[] {"Description_Triggers_factures.SQL", "/Bd/Creation_Base"});
//		
//		fichiersSQL.add(new String[] {"ProcedureStockees.SQL", "/Bd/Creation_Base"});
//		
//		//fichiersSQL.add(new String[] {"trigger.sql", "/Bd/Creation_Base"});
//		
//		// fichiers insert bdd
//		fichiersInsertSQL.add(new String[] {"Insertion.SQL", "/Bd/Requete_Essai"});
//		fichiersInsertSQL.add(new String[] {"insertion_test.SQL", "/Bd/Requete_Essai"});
//
//	}
//	
//	public void verifyWorkplace() {
//		changementChemin();
//		
//		//TODO creation d'un dossier par defaut
//		initTmp();
//		//if(findOpenProject()==null)
//			initDossier("dossier");
//			
//		changementChemin();
//	}
//	
//	/**
//	 * Création/initialisation du répertoire temporaire
//	 */
//	public void initTmp() {
//		IWorkspaceRoot root  = ResourcesPlugin.getWorkspace().getRoot();
//		projectTmp  = root.getProject(C_NOM_PROJET_TMP);
//
//		try {
//			if (projectTmp.exists())
//				projectTmp.delete(true, true, null);
//			projectTmp.create(null);
//			if (!projectTmp.isOpen()) 
//				projectTmp.open(null);
//		} catch (CoreException e) {
//			logger.error(e);
//		}
//	}
//	
//	abstract public void initDossier(String name);
//	
//	abstract public void initDossier(String projectPath,String projectName);
//	
//	public void createProject(IProject projectHandle,IPath path,String nomDuProjet) {
//		IPath newPath = path;
//
//		IWorkspace workspace = ResourcesPlugin.getWorkspace();
//        /*final*/ IProjectDescription description = workspace
//                .newProjectDescription(projectHandle.getName());
//        description.setLocation(newPath);
//
//        // update the referenced project if provided
////        if (referencePage != null) {
////            IProject[] refProjects = referencePage.getReferencedProjects();
////            if (refProjects.length > 0)
////                description.setReferencedProjects(refProjects);
////        }
//
//        // create the new project operation
//    //    WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
//    //        protected void execute(IProgressMonitor monitor)
//    //               throws CoreException {
//            	try {
//					projectHandle.create(description,null);
//				} catch (CoreException e) {
//					logger.error(e);
//				}
//     //       }
//     //   };
//
//	}
//	
//	/**
//	 * Copie le fichier <code>fichier</code> se trouvant dans le plugin <code>pluginId</code>
//	 * dans la ressource <code>file</code>
//	 * @param file - ressource où copier le fichier
//	 * @param pluginId - plugin contenant le fichier à copier
//	 * @param fichier - fichier à copier
//	 */
//	public void copyRessource(IFile file, String pluginId, String fichier) {
//		Bundle bundle = Platform.getBundle (pluginId);
//		URL urlFichier = bundle.getEntry (fichier);
//		URL urlFichierFileSystem = null;
//		try {
//			urlFichierFileSystem = Platform.resolve (urlFichier);
//			if (file.exists()) {
//				file.delete(true,false,null);
//			}
//			if (!file.exists()) {
//				// byte[] bytes = "File contents".getBytes();
//			    // InputStream source = new ByteArrayInputStream(bytes);
//				urlFichierFileSystem.openStream();
//				file.create(urlFichierFileSystem.openStream(), IResource.NONE, null);
//				// file.appendContents(urlFichierFileSystem.openStream(), true, false, null);
//			}
//		} catch (CoreException e) {
//			logger.error("",e);
//		} catch (IOException e) {
//			logger.error("",e);
//		}  catch (Exception e)  {
//			//   IStatus status = ErrorFactory.createStatusObject (getID (), e, DataAccessErrorCodes.ERROR_UNABLE_TO_FIND_DATABASE);
//			// throw new CoreException (status);
//			logger.error("",e);
//		}		
//	}
//	
//	/**
//	 * Creation de la base de données
//	 * @param dbPath - emplacement ou sera créé la bdd - contient les scripts de création (.bat ou .sh) et le fichier ant
//	 */
//	public void createDatabase(String dbPath) {
//		try {
//			logger.info("Création de la base de données");
//			FireBirdManager fireBirdManager = new FireBirdManager();
//			fireBirdManager.createDB(dbPath);
//			AntRunner antRunner = new AntRunner(); 
//			//antRunner.setBuildFileLocation(dbPath+"/"+C_FICHIER_ANT_CREATION_DB);
//			
//			if(logger.isDebugEnabled())
//				logger.info(dbPath+"/"+C_FICHIER_ANT_CREATION_DB);
//			
//			//String scriptCreationBase = null;
//			if(Platform.getOS().equals(Platform.OS_WIN32)) {
//				//scriptCreationBase = C_FICHIER_CREATION_DB_BAT;
//				antRunner.setBuildFileLocation(dbPath+"/"+C_FICHIER_ANT_CREATION_DB);
//			} else if(Platform.getOS().equals(Platform.OS_LINUX)) {
//				//scriptCreationBase = C_FICHIER_CREATION_DB_SH;
//				antRunner.setBuildFileLocation(dbPath+"/"+C_FICHIER_ANT_CREATION_DB_LINUX);
//			}
//		
//			//antRunner.setArguments("\"-Dscript="+dbPath+"/"+scriptCreationBase+"\"");
//			antRunner.setArguments("\"-Ddb="+dbPath+"/"+C_FICHIER_DB+"\"");
//			antRunner.run();
//						
//			logger.info("Création de la base de données terminée");
//		} catch (Exception e) {
//			logger.error("",e);
//		}
//	}
//	
//	/**
//	 * @return - Le projet ouvert dans le workspace
//	 */
//	public IProject findOpenProject() {
//		IProject projetCourant = null;
//		IProject[] projets = ResourcesPlugin.getWorkspace().getRoot().getProjects();
//		boolean trouve = false;
//		int i = 0 ;
//		while(!trouve && i<projets.length) {
//			if(projets[i].isOpen() && !projets[i].getName().equals(C_NOM_PROJET_TMP)) {
//				trouve = true;
//			}
//			i++;
//		}
//		if(trouve) {
//			projetCourant = projets[i-1];
//		}
//		return projetCourant;
//	}
//	
//	/**
//	 * @return - Le chemin du projet ouvert dans le workspace (chemin relatif par rapport au workspace) 
//	 */
//	public String openProjectRelativePath() {
//		if(findOpenProject()==null)
//			return "";
//		else 
//			return findOpenProject().getFullPath().toString();
//	}
//	
//	/**
//	 * @return - Le chemin absolu (par rapport au système de fichiers) du projet ouvert
//	 */
//	public String openProjectLocationPath() {
//		if(findOpenProject()==null)
//			return "";
//		else 
//			return findOpenProject().getLocation().toString();
//	}
//	
//	/**
//	 * @return - vrai ssi le projet ouvert se trouve dans le workspace
//	 */
//	public boolean openProjectInWorkspace() {
//		if(findOpenProject()!=null) {
//			Path workspacePath = new Path(new File(getWorkplaceURL()).getAbsolutePath());
//			return workspacePath.isPrefixOf(findOpenProject().getLocation());
//		} else {
//			return false;
//		}
//	}
//	
//	/**
//	 * Ferme tous les projets ouvert du workspace puis ouvre le project passé en parametre
//	 * @param project
//	 * @throws CoreException
//	 */
//	public void lgrOpenProject(IProject project) throws CoreException {
//		IProject[] projets = ResourcesPlugin.getWorkspace().getRoot().getProjects();
//		for (int i = 0; i < projets.length; i++) {
//			if(!projets[i].getName().equals(C_NOM_PROJET_TMP))
//				projets[i].close(null);
//		}
//		project.open(null);
//		changementChemin();
//	}
//	
//	public String ressourcePath(String pluginId, String fichier) {
//		Bundle bundle = Platform.getBundle (pluginId);
//		URL urlFichier = bundle.getEntry (fichier);
//		//URL urlFichierFileSystem = null;
//		try {
//			//System.out.println(urlFichier);
//			//System.out.println(Platform.resolve (urlFichier));
//			//System.out.println(new java.io.File(Platform.resolve (urlFichier).toURI()).getAbsolutePath());
//			return new java.io.File(Platform.resolve (urlFichier).toURI()).getAbsolutePath();
//		} catch (Exception e) {
//			logger.error(e);
//			return null;
//		}	
//	}
//	
//	/**
//	 * Initialisation des constantes contenant des chemins
//	 */
//	private void changementChemin() {
//		ConstLiasse.C_REPERTOIRE_PROJET_IN_WORKSPACE = openProjectInWorkspace();
//		if(openProjectInWorkspace())
//			ConstLiasse.C_REPERTOIRE_PROJET = openProjectRelativePath();
//		else
//			ConstLiasse.C_REPERTOIRE_PROJET = openProjectLocationPath();
//		ConstLiasse.updatePath();
//		
//		//tester si chemin reseau ou local file system
//		//local => localhost sinon nom/ip serveur
//		//si reseau et windows chercher lettre de lecteur et ajouter 2 points
//		if(findOpenProject()!=null) {
//			IPath dbLocation = findOpenProject().getFolder(C_DOSSIER_DB).getFile(C_FICHIER_DB).getLocation();
//			if(dbLocation.isUNC()) { //TODO problème de portabilité
//				String serveur = dbLocation.segment(0);
//				String lecteur = dbLocation.segment(1);
//				logger.debug(dbLocation.toString().replaceFirst("/"+lecteur+"/", "/"+lecteur+":/"));
//				ConstLiasse.C_FICHIER_BDD = dbLocation.toString().replaceFirst("/"+lecteur+"/", "/"+lecteur+":/");
//			} else {
//				ConstLiasse.C_FICHIER_BDD = "localhost:"+dbLocation.toString();
//			}
//		}
//		//ConstLiasse.setFichierCheminIni("C:/CheminFichier.properties");
//		if(logger.isInfoEnabled())
//			logger.info("url ====> "+ConstLiasse.C_URL_BDD+ConstLiasse.C_FICHIER_BDD);
//	}
//
//	public String getWorkplaceURL() {
//		return workplaceURL;
//	}
//}
