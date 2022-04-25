package fr.legrain.licence.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;

import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.lib.gui.ParamAffiche;

public abstract class AbstractLgrMultiPageEditorSupport extends
AbstractLgrMultiPageEditor {
	
	abstract public void actCreer(IStructuredSelection selection,final ParamAffiche param);
		
	
	@Override
	protected void onClose() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void createPages() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

}
