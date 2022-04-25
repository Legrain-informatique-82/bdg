package fr.legrain.gestCom.librairiesEcran.swt;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

public abstract class LgrAbstractHandler extends AbstractHandler {

	private boolean enabled = true;
	
	public LgrAbstractHandler() {
		super();
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	static public IEditorPart verifEditeurOuvert() {
		IEditorPart editor = null;
		try {
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			if(page.getEditorReferences().length>0){	//au moins 1 éditeur ouvert			
				int i = 0;
				while(i<page.getEditorReferences().length && editor==null){
					editor = page.getEditorReferences()[i].getEditor(false);
					}
					i++;
				}
			return editor;
		} catch (Exception e) {
		}
		return editor;
	}
}
