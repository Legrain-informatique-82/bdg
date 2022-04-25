package fr.legrain.exportation.handlers;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

import fr.legrain.exportation.editor.EditorExportation;
import fr.legrain.exportation.editor.EditorInputExportation;
import fr.legrain.exportation.editor.ExportationMultiPageEditor;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.gui.ParamAffiche;

public class HandlerExportation extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerExportation.class.getName());
	private IWorkbenchWindow window;
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}
	
	
	private Object ouvreMultiPageEditeur() {
		this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		try {
			IEditorPart editor = AbstractLgrMultiPageEditor.chercheEditeurDocumentOuvert(
					ExportationMultiPageEditor.ID_EDITOR);
			if(editor==null){
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
			openEditor(new EditorInputExportation(), ExportationMultiPageEditor.ID_EDITOR);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);


			ParamAffiche param = new ParamAffiche();
			((AbstractLgrMultiPageEditor)e).findMasterController().configPanel(param);
			} else {
				page.activate(editor);
			}
		} catch (WorkbenchException e) {
			logger.error("Erreur pendant l'ouverture de la perspective Exportation",e);
		}
	return null;
}
	
	private Object ouvreUnEditeur() {
		try {
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
			    openEditor(new EditorInputExportation(), EditorExportation.ID);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);

			ParamAffiche paramAfficheExportation = new ParamAffiche();
			((JPALgrEditorPart)e).setPanel(((JPALgrEditorPart)e).getController().getVue());
			((JPALgrEditorPart)e).getController().configPanel(paramAfficheExportation);
			
		} catch (PartInitException e) {
			logger.error("",e);
		}
		return null;
	}
	
	public Object execute(ExecutionEvent event) throws ExecutionException {
//		this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
//		IWorkbenchPage page = window.getActivePage();
//		try {
//			IEditorPart editor = JPALgrEditorPart.verifEditeurOuvert(EditorExportation.ID);
//			if(editor==null){
//			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
//			openEditor(new EditorInputExportation(), EditorExportation.ID);
//			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
//			
//			ParamAfficheExportation paramAfficheExportation = new ParamAfficheExportation();
//			((LgrEditorPart)e).setPanel(((LgrEditorPart)e).getController().getVue());
//			((LgrEditorPart)e).getController().configPanel(paramAfficheExportation);
//			} else {
//				page.activate(editor);
//			}
//		} catch (WorkbenchException e) {
//			logger.error("Erreur pendant l'ouverture de l'éditeur ",e);
//		}
		return ouvreMultiPageEditeur();
		}


	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isHandled() {
		// TODO Auto-generated method stub
		return true;
	}

	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

}
