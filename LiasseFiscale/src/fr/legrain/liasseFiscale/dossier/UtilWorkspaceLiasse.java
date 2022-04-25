package fr.legrain.liasseFiscale.dossier;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.ant.core.AntRunner;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import fr.legrain.liasseFiscale.db.ConstLiasse;
import fr.legrain.liasseFiscale.nature.LiasseFiscaleNature;
import fr.legrain.lib.data.FireBirdManager;

public class UtilWorkspaceLiasse {
	
	static Logger logger = Logger.getLogger(UtilWorkspaceLiasse.class.getName());
	
	private String workplaceURL  = Platform.getInstanceLocation().getURL().getFile();
	
	private List<String[]> fichiersConfig = new ArrayList<String[]>();
	private List<String[]> fichiersSQL = new ArrayList<String[]>();
	
	private static IProject projectTmp = null;
	private static final String C_NOM_PROJET_TMP = "tmp"; //répertoire de travail commun, fichiers temporaires (creation base,...)
	
	private static final String C_FICHIER_ANT_CREATION_DB = "db.build.xml";
	private static final String C_FICHIER_SHELL_CREATION_DB = "init_bdd.sh";
	
	//private static final String C_FICHIER_DB = "liasse.fdb";
	private static final String C_FICHIER_DB = "LIASSE.FDB";
	
	private static final String C_DOSSIER_DB = "Bd";
	private static final String C_DOSSIER_SQL_CREATION = "Creation_Base";
	private static final String C_NOM_DOSSIER_DEFAUT = "liasse";
	
	public UtilWorkspaceLiasse() {
		if(projectTmp==null) {
			initTmp();
		}
		
		fichiersConfig.add(new String[] {C_FICHIER_ANT_CREATION_DB, "/Bd"});
		fichiersConfig.add(new String[] {"CtrlBD.ini", "/Bd"});
		fichiersConfig.add(new String[] {"IDBD.ini", "/Bd"});
		fichiersConfig.add(new String[] {"Modif.properties", "/Bd"});
		//fichiersConfig.add(new String[] {"log4j.properties", "/Bd"});
		fichiersConfig.add(new String[] {C_FICHIER_SHELL_CREATION_DB, "/Bd"});

		// fichiers création bdd
		fichiersSQL.add(new String[] {"ddl_metier.sql", "/Bd/Creation_Base"});
		fichiersSQL.add(new String[] {"ddl_technique.sql", "/Bd/Creation_Base"});
		fichiersSQL.add(new String[] {"domaines.sql", "/Bd/Creation_Base"});
		fichiersSQL.add(new String[] {"procedure_technique.sql", "/Bd/Creation_Base"});
		fichiersSQL.add(new String[] {"trigger_technique.sql", "/Bd/Creation_Base"});
		fichiersSQL.add(new String[] {"trigger_metier.sql", "/Bd/Creation_Base"});
		
		//2002
		fichiersSQL.add(new String[] {"2002_insert_repart.sql", "/Bd/Creation_Base"});
		fichiersSQL.add(new String[] {"2002_insert_totaux.sql", "/Bd/Creation_Base"});
		fichiersSQL.add(new String[] {"2002_insert_traitements.sql", "/Bd/Creation_Base"});
		
		fichiersSQL.add(new String[] {"2002_insert_repart_bic.sql", "/Bd/Creation_Base"});
		fichiersSQL.add(new String[] {"2002_insert_totaux_bic.sql", "/Bd/Creation_Base"});
		
		fichiersSQL.add(new String[] {"2002_insert_repart_suite.sql", "/Bd/Creation_Base"});
		fichiersSQL.add(new String[] {"2002_insert_totaux_suite.sql", "/Bd/Creation_Base"});
		
		//2007
		//fichiersSQL.add(new String[] {"2007_insert_repart.sql", "/Bd/Creation_Base"});
		fichiersSQL.add(new String[] {"2007_insert_totaux.sql", "/Bd/Creation_Base"});
		fichiersSQL.add(new String[] {"2007_insert_traitements.sql", "/Bd/Creation_Base"});
		
		fichiersSQL.add(new String[] {"2007_insert_repart_bic.sql", "/Bd/Creation_Base"});
		fichiersSQL.add(new String[] {"2007_insert_totaux_bic.sql", "/Bd/Creation_Base"});
		
		fichiersSQL.add(new String[] {"2007_insert_repart_suite.sql", "/Bd/Creation_Base"});
		fichiersSQL.add(new String[] {"2007_insert_totaux_suite.sql", "/Bd/Creation_Base"});
		
				
	}
	
	public void verifyWorkplace() {
		changementChemin();
		
		//TODO creation d'un dossier par defaut
		initTmp();
		//if(findOpenProject()==null)
		initDossier(C_NOM_DOSSIER_DEFAUT);
		
		changementChemin();
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
	
	public void initDossier(String name) {
		initDossier(null,name);
	}
	
	public void enableNature(IProject projectHandle, String natureId) {
		try {
			boolean wasOpen = true;
			
			if(!projectHandle.isOpen()) {
				projectHandle.open(null);
				wasOpen = false;
			}
			
			IProjectDescription newDescription = projectHandle.getDescription();
			String[] natures = newDescription.getNatureIds();
			String[] newNatures = new String[natures.length + 1];
			System.arraycopy(natures, 0, newNatures, 0, natures.length);
			newNatures[natures.length] = natureId;
			newDescription.setNatureIds(newNatures);
			projectHandle.setDescription(newDescription, null);
			
			if(!wasOpen) {
				projectHandle.close(null);
			}
		} catch (CoreException e) {
			logger.error("",e);
			for( int i=0; i<e.getStatus().getChildren().length; i++) {
				logger.error(i+" : "+e.getStatus().getChildren()[i].getMessage());
			}
		}
	}
	
	public void initDossier(String projectPath, String projectName) {
		IFile handle = null;
		
		IWorkspaceRoot root  = ResourcesPlugin.getWorkspace().getRoot();
		//IWorkspace workspace = ResourcesPlugin.getWorkspace();
		
		//Projet/dossier
		IProject project  = root.getProject(projectName);
		
		//Repertoires
		IFolder folderDatabase = project.getFolder(C_DOSSIER_DB);
		IFolder folderDatabaseTmp = projectTmp.getFolder(C_DOSSIER_DB);
		IFolder folderCreationDatabase = folderDatabaseTmp.getFolder(C_DOSSIER_SQL_CREATION);
		IFile   fileScriptShellCreationBD  = folderDatabaseTmp.getFile(C_FICHIER_SHELL_CREATION_DB);
		
		//Fichier à créer
		IFile fileDatabase    = folderDatabase.getFile(C_FICHIER_DB);
		IFile fileDatabaseTmp = folderDatabaseTmp.getFile(C_FICHIER_DB);
		
		try {
			
			if (!project.exists()) {
				if(projectPath==null) //creation d'un nouveau projet dans le workspace
					project.create(null);
				else //creation d'un nouveau projet dans le dossier indiqué
					createProject(project,new Path(projectPath+"/"+projectName),projectName);
				
				enableNature(project,LiasseFiscaleNature.ID);
			}
			
			if (!project.isOpen()) lgrOpenProject(project); //project.open(null);
			
			if (!folderDatabase.exists()) {
				logger.info("Création du répertoire de base de données dans "+projectName);
				folderDatabase.create(IResource.NONE, true, null);
			}
			
			if (!folderDatabaseTmp.exists()) {
				logger.info("Création du répertoire de base de données dans "+C_NOM_PROJET_TMP);
				folderDatabaseTmp.create(IResource.NONE, true, null);
			}
			
			if (!folderCreationDatabase.exists()) {
				logger.info("Création du répertoire de creation de la base de données dans "+projectName);
				folderCreationDatabase.create(IResource.NONE, true, null);
			}		
			
			//test présence des fichiers de config
			for (String[] fichier : fichiersConfig) {
				handle = folderDatabase.getFile(fichier[0]);
				//if(!handle.exists()) {
				logger.info("Ajout du fichier : "+fichier[0]+" dans "+projectName);
				copyRessource(handle,"LiasseFiscale",fichier[1]+"/"+fichier[0]);
				//}
			}
			
			for (String[] fichier : fichiersConfig) {
				handle = folderDatabaseTmp.getFile(fichier[0]);
				//if(!handle.exists()) {
				logger.info("Ajout du fichier : "+fichier[0]+" dans "+C_NOM_PROJET_TMP);
				copyRessource(handle,"LiasseFiscale",fichier[1]+"/"+fichier[0]);
				//}
			}
			
			//test présence des fichiers sql
			for (String[] fichier : fichiersSQL) {
				handle = folderCreationDatabase.getFile(fichier[0]);
				if(!handle.exists()) {
					logger.info("Ajout du fichier : "+fichier[0]+" dans "+projectName);
					copyRessource(handle,"LiasseFiscale",fichier[1]+"/"+fichier[0]);
				}
			}
						
			//test présence du fichier bdd
			if(Platform.getOS().equals(Platform.OS_LINUX)) {
				if (!fileDatabase.exists()) {
					String dbPath = folderDatabaseTmp.getLocation().toString();
					if (!fileDatabaseTmp.exists()) {
						ResourceAttributes resourceAttributes  = fileScriptShellCreationBD.getResourceAttributes();
						if(resourceAttributes!=null) {
							resourceAttributes.setExecutable(true);
							fileScriptShellCreationBD.setResourceAttributes(resourceAttributes);
						}
						Process p1 = Runtime.getRuntime().exec(
								new String[] {
										"gksu",
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

						createDatabase(dbPath);
					}
					fileDatabaseTmp.refreshLocal(IResource.DEPTH_ZERO, null);
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
					logger.info("bdd presente");
				}
			} else if(Platform.getOS().equals(Platform.OS_WIN32)) {

				if (!fileDatabase.exists()) {
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

		} catch (CoreException e) {
			logger.error("",e);
		} catch (InterruptedException e) {
			logger.error("",e);
		} catch (IOException e) {
			logger.error("",e);
		}
	}
	
	public void createProject(IProject projectHandle,IPath path,String nomDuProjet) {
		IPath newPath = path;
		
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		/*final*/ IProjectDescription description = workspace
		.newProjectDescription(projectHandle.getName());
		description.setLocation(newPath);
		
		// update the referenced project if provided
//		if (referencePage != null) {
//		IProject[] refProjects = referencePage.getReferencedProjects();
//		if (refProjects.length > 0)
//		description.setReferencedProjects(refProjects);
//		}
		
		// create the new project operation
		//    WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
		//        protected void execute(IProgressMonitor monitor)
		//               throws CoreException {
		try {
			//String[] newNatures = new String[] {LiasseFiscaleNature.ID};
			//description.setNatureIds(newNatures);
			
			projectHandle.create(description,null);
			
		} catch (CoreException e) {
			logger.error("",e);
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
	 * Creation de la base de données
	 * @param dbPath - emplacement ou sera créé la bdd - contient les scripts de création (.bat ou .sh) et le fichier ant
	 */
	public void createDatabase(String dbPath) {
		try {
			logger.info("Création de la base de données");
			FireBirdManager fireBirdManager = new FireBirdManager();
			fireBirdManager.createDB(dbPath, C_FICHIER_DB);
			AntRunner antRunner = new AntRunner(); 
			//antRunner.setBuildFileLocation(dbPath+"/"+C_FICHIER_ANT_CREATION_DB);
			
			if(logger.isDebugEnabled())
				logger.info(dbPath+"/"+C_FICHIER_ANT_CREATION_DB);
			
			//String scriptCreationBase = null;
			//		if(Platform.getOS().equals(Platform.OS_WIN32)) {
			//scriptCreationBase = C_FICHIER_CREATION_DB_BAT;
			antRunner.setBuildFileLocation(dbPath+"/"+C_FICHIER_ANT_CREATION_DB);
			//		} else if(Platform.getOS().equals(Platform.OS_LINUX)) {
			//scriptCreationBase = C_FICHIER_CREATION_DB_SH;
			//			antRunner.setBuildFileLocation(dbPath+"/"+C_FICHIER_ANT_CREATION_DB_LINUX);
			//		}
			
			//antRunner.setArguments("\"-Dscript="+dbPath+"/"+scriptCreationBase+"\"");
			antRunner.setArguments("\"-Ddb="+dbPath+"/"+C_FICHIER_DB+"\"");
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
//			p = pb.start();
//			p.waitFor();
//			} catch (IOException e) {
//			logger.error("",e);
//			} catch (InterruptedException e) {
//			logger.error("",e);
//			}
//////////////////////////////////////////////////////////////////////////////////////////
			
			logger.info("Création de la base de données terminée");
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	/**
	 * @return - Le projet ouvert dans le workspace
	 */
	public IProject findOpenProject() {
		IProject projetCourant = null;
		try {
			IProject[] projets = ResourcesPlugin.getWorkspace().getRoot().getProjects();
			boolean trouve = false;
			int i = 0 ;
			while(!trouve && i<projets.length) {
				if(projets[i].isOpen() 
						&& !projets[i].getName().equals(C_NOM_PROJET_TMP)
						&& projets[i].isNatureEnabled(LiasseFiscaleNature.ID)) {
					trouve = true;
				}
				i++;
			}
			if(trouve) {
				projetCourant = projets[i-1];
			}
			return projetCourant;
		} catch(CoreException e) {
			logger.error("",e);
			return projetCourant;
		}
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
			Path workspacePath = new Path(new File(new UtilWorkspaceLiasse().getWorkplaceURL()).getAbsolutePath());
			return workspacePath.isPrefixOf(findOpenProject().getLocation());
		} else {
			return false;
		}
	}
	
	/**
	 * Ferme tous les projets ouvert du workspace puis ouvre le project passé en parametre
	 * @param project
	 * @throws CoreException
	 */
	public void lgrOpenProject(IProject project) throws CoreException {
		IProject[] projets = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (int i = 0; i < projets.length; i++) {
			if(!projets[i].getName().equals(C_NOM_PROJET_TMP) && projets[i].isOpen() && projets[i].isNatureEnabled(LiasseFiscaleNature.ID))
				projets[i].close(null);
		}
		project.open(null);
		changementChemin();
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
		ConstLiasse.C_REPERTOIRE_PROJET_IN_WORKSPACE = openProjectInWorkspace();
		if(openProjectInWorkspace())
			ConstLiasse.C_REPERTOIRE_PROJET = openProjectRelativePath();
		else
			ConstLiasse.C_REPERTOIRE_PROJET = openProjectLocationPath();
		ConstLiasse.updatePath();
		
		//tester si chemin reseau ou local file system
		//local => localhost sinon nom/ip serveur
		//si reseau et windows chercher lettre de lecteur et ajouter 2 points
		if(findOpenProject()!=null) {
			IPath dbLocation = findOpenProject().getFolder(C_DOSSIER_DB).getFile(C_FICHIER_DB).getLocation();
			if(dbLocation.isUNC()) { //TODO problème de portabilité
				String serveur = dbLocation.segment(0);
				String lecteur = dbLocation.segment(1);
				logger.debug(dbLocation.toString().replaceFirst("/"+lecteur+"/", "/"+lecteur+":/"));
				ConstLiasse.C_FICHIER_BDD = dbLocation.toString().replaceFirst("/"+lecteur+"/", "/"+lecteur+":/");
			} else {
				ConstLiasse.C_FICHIER_BDD = "localhost:"+dbLocation.toString();
			}
		}
		//TODO cf utilworkspace dupliqué, à revoir !!!!!
		//ConstLiasse.setFichierCheminIni("C:/CheminFichier.properties");
		if(logger.isInfoEnabled())
			logger.info("url ====> "+ConstLiasse.C_URL_BDD+ConstLiasse.C_FICHIER_BDD);
	}
	
	public String getWorkplaceURL() {
		return workplaceURL;
	}
}
