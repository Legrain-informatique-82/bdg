package fr.legrain.rappeler.handlers;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.rappeler.ecran.ParamAfficheRappeler;
import fr.legrain.rappeler.editor.EditorInputRappeler;
import fr.legrain.rappeler.editor.EditorMultiPageRappeler;


public class HandlerRappeler extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerRappeler.class.getName());
	private IWorkbenchWindow window;
	
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		return ouvreMultiPageEditeur();
		//return ouvreUnEditeur();
	}
	
	private Object ouvreUnEditeur() {
		try {
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor((IEditorInput) new EditorMultiPageRappeler(), EditorMultiPageRappeler.ID_EDITOR);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);

			
			ParamAfficheRappeler paramAfficheArticles = new ParamAfficheRappeler();
			((JPALgrEditorPart)e).setPanel(((JPALgrEditorPart)e).getController().getVue());
			((JPALgrEditorPart)e).getController().configPanel(paramAfficheArticles);
		
		} catch (Exception e) {
			logger.error("",e);
		}
		return null;
	}
	
	private Object ouvreMultiPageEditeur() {
		this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		try {
			IEditorPart editor = AbstractLgrMultiPageEditor.chercheEditeurDocumentOuvert(EditorMultiPageRappeler.ID_EDITOR);
			if(editor==null){
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInputRappeler(),
					EditorMultiPageRappeler.ID_EDITOR);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
			ParamAffiche param = new ParamAffiche();
			((AbstractLgrMultiPageEditor)e).findMasterController().configPanel(param);
			} else {
				page.activate(editor);
			}
		} catch (WorkbenchException e) {
			logger.error("Erreur pendant l'ouverture de la perspective Rappeler",e);
		}
	return null;
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
