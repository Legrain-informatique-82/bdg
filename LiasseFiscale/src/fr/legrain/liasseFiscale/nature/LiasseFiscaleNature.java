package fr.legrain.liasseFiscale.nature;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

public class LiasseFiscaleNature implements IProjectNature {
	
	static final public String ID = "LiasseFiscale.fr.legrain.liasseFiscale.nature.LiasseFiscaleNature";
	
	private IProject project;

	public void configure() throws CoreException {
		// TODO Auto-generated method stub

	}

	public void deconfigure() throws CoreException {
		// TODO Auto-generated method stub

	}

	public IProject getProject() {
		// TODO Auto-generated method stub
		return project;
	}

	public void setProject(IProject project) {
		// TODO Auto-generated method stub
		this.project = project;
	}

}
