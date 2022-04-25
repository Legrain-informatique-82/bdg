package fr.legrain.gestCom.librairiesEcran.workbench;

import org.apache.log4j.Logger;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import fr.legrain.gestCom.librairiesEcran.swt.SWTBaseControllerSWTStandard;

abstract public class LgrEditorInput implements IEditorInput {
	
	static Logger logger = Logger.getLogger(LgrEditorInput.class.getName());
	
	//private SWTBaseControllerSWTStandard contoller = null;

	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return "";
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		// TODO Auto-generated method stub
		return "";
	}

	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}

}
