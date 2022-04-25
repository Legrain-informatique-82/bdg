package fr.legrain.gestCom.librairiesEcran.workbench;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.editor.FormEditor;

import fr.legrain.lib.gui.ParamAffiche;

abstract public class lgrFormEditor extends FormEditor {
	
	static Logger logger = Logger.getLogger(lgrFormEditor.class.getName());
	
	abstract protected String getID();
	@Override
	protected void addPages() {
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
	static public IEditorPart verifEditeurOuvert(String idEditor) {
		IEditorPart editor = null;
		try {
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			if(page.getEditorReferences().length>0){	//au moins 1 éditeur ouvert			
				int i = 0;
				while(i<page.getEditorReferences().length && editor==null){
					//if(page.getEditorReferences()[i].getEditor(false) instanceof AbstractMultiPageDocumentEditor) {
					 if(page.getEditorReferences()[i].getEditor(false) instanceof lgrFormEditor) {
						if(idEditor!=null && ((lgrFormEditor)page.getEditorReferences()[i].getEditor(false)).getID()!=null){
							if (((lgrFormEditor)page.getEditorReferences()[i].getEditor(false)).getID().equals(idEditor))
								editor = page.getEditorReferences()[i].getEditor(false);
						} else {
							editor = page.getEditorReferences()[i].getEditor(false);
						}
					}
					i++;
				}
			}
			return editor;
		} catch (Exception e) {
			logger.error("",e);
		}
		return editor;
	}


}
