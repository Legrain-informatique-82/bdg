package fr.legrain.gestionCommerciale;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.IB_APPLICATION;
import fr.legrain.gestionCommerciale.extensionPoints.ExecLancementExtension;
import fr.legrain.lib.data.JPABdLgr;


public class OuvreDossier {

	static Logger logger = Logger.getLogger(OuvreDossier.class.getName());

	public void importDossier(File projectFile) {
		importDossier(projectFile,true);
	}
	
	/**
	 * Creation d'un dossier deja existant dans le workspace
	 * et ouverture de ce dossier
	 * @param projectFile - fichier .project
	 * @param initDossier - ouverture réelle (pas uniquement par eclipse) du dossier et initialisation
	 */
	public void importDossier(File projectFile, boolean initDossier) {
		IProject proj = null;
		UtilWorkspace uw = new UtilWorkspace();
		ProjectRecord p = new ProjectRecord(projectFile);
		if(!initDossier) { 
			//s'assurer de bien fermer le dossier courant avant d'ouvrir le nouveau, 
			//sinon l'ancien dossier risque d'être reouvert à l'ouverture du workspace
			try {
				if(uw.findOpenProject()!=null)
					uw.findOpenProject().close(null);
			} catch (CoreException e) {
				logger.error(e);
			}
		}
		proj = createExistingProject(p);
		logger.debug("open : "+uw.findOpenProject().getName());
		if(proj!=null && initDossier){
			logger.debug("open : "+uw.findOpenProject().getName());
			
			uw.initDossier(p.getProjectName());
//			try {
//				uw.lgrOpenProject(proj);
//			} catch (CoreException e) {
//				logger.error(e);
//			}
			logger.debug("open : "+uw.findOpenProject().getName());
			ApplicationWorkbenchWindowAdvisor.setTitre("Dossier : "+uw.findOpenProject().getName()+" - "
					+projectFile.getPath()+ " ..... Base : "+new File(Const.C_FICHIER_BDD).getPath());
		}
	}
	
	public void ouverture(File projectFile) throws WorkbenchException {
		 ouverture(projectFile,true);
	}
	
	public void ouverture(File projectFile, boolean initDossier) throws WorkbenchException {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllPerspectives(true, false);
		PlatformUI.getWorkbench().showPerspective(Perspective.ID, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		importDossier(projectFile,initDossier);
	}

	public class ProjectRecord {
		File projectSystemFile;
		Object projectArchiveFile;
		String projectName;
		Object parent;
		int level;
		IProjectDescription description;

		/**
		 * Create a record for a project based on the info in the file.
		 * @param file
		 */
		ProjectRecord(File file) {
			projectSystemFile = file;
			setProjectName();
		}

		/**
		 * @param file - The Object representing the .project file
		 * @param parent - The parent folder of the .project file
		 * @param level - The number of levels deep in the provider the file is
		 */
		ProjectRecord(Object file, Object parent, int level) {
			this.projectArchiveFile = file;
			this.parent = parent;
			this.level = level;
			setProjectName();
		}

		/**
		 * Set the name of the project based on the projectFile.
		 */
		private void setProjectName() {
			IProjectDescription newDescription = null;
			try {
				IPath path = new Path(projectSystemFile.getPath());
				newDescription = ResourcesPlugin.getWorkspace().loadProjectDescription(path);
			} catch (CoreException e) {
				logger.error(e);
			} 

			if (newDescription == null) {
				this.description = null;
				projectName = ""; //$NON-NLS-1$
			} else {
				this.description = newDescription;
				projectName = this.description.getName();
			}
		}

		/**
		 * Get the name of the project
		 * @return String
		 */
		public String getProjectName() {
			return projectName;
		}
	}

	/**
	 * Create the project described in record. If it is successful return true.
	 * @param record
	 * @return boolean <code>true</code> of successult
	 */
	public IProject createExistingProject(final ProjectRecord record) {
		IProject project = null;
		String projectName = record.getProjectName();
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		logger.debug("projectName : "+projectName);
		/*final IProject*/ project = workspace.getRoot().getProject(projectName);
		if (record.description == null) {
			record.description = workspace.newProjectDescription(projectName);
			IPath locationPath = new Path(record.projectSystemFile
					.getAbsolutePath());

			// If it is under the root use the default location
			if (Platform.getLocation().isPrefixOf(locationPath))
				record.description.setLocation(null);
			else
				record.description.setLocation(locationPath);
		} else
			record.description.setName(projectName);
		try {
			if(project.isOpen())project.close(null);
			if(project.exists()){
				project.delete(false,null);
			}
			project.create(record.description, null);
			project.open(IResource.BACKGROUND_REFRESH,null);
		} catch (CoreException e) {	
//			MessageDialog.openWarning(PlatformUI.getWorkbench().
//					getActiveWorkbenchWindow().getShell(),"Ouverture dossier","Le dossier ''"+projectName+"'' existe déjà !!!");
			logger.error(e);
			//return null;
		}
		return project;
	}
	
	public void fermeture() throws WorkbenchException {
		new JPABdLgr().fermetureConnection();
		IB_APPLICATION.fermetureConnectionBdd();
		IB_APPLICATION.SimpleDeConnectionBdd();
	}
	
	public ProjectRecord recupProjectRecord(File projectFile){
	  return  new ProjectRecord(projectFile);
	}
}