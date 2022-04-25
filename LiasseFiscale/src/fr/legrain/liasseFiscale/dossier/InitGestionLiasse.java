package fr.legrain.liasseFiscale.dossier;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;

import fr.legrain.gestionCommerciale.Application;
import fr.legrain.gestionCommerciale.UtilWorkspace;

public class InitGestionLiasse extends UtilWorkspace{
	
	static Logger logger = Logger.getLogger(InitGestionLiasse.class.getName());
	
	//creation du répertoire général pour les liasses
	//affectation de la nature LiasseFiscale
	
	//création de la base de données

}
