package fr.legrain.articles.handlers;
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

import fr.legrain.articles.ecran.ParamAfficheTypeNoteArticle;
import fr.legrain.articles.editor.EditorInputTypeNoteArticle;
import fr.legrain.articles.editor.EditorTypeNoteArticle;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.tiers.editor.EditorTypeNoteTiers;


public class HandlerTypeNoteArticle extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerTypeNoteArticle.class.getName());
	private IWorkbenchWindow window;
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {;
	this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	IWorkbenchPage page = window.getActivePage();
	try {
		IEditorPart editor = JPALgrEditorPart.verifEditeurOuvert(EditorTypeNoteArticle.ID);
		if(editor==null){
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInputTypeNoteArticle(),
					EditorTypeNoteArticle.ID);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
			ParamAfficheTypeNoteArticle paramAfficheTypeNote = new ParamAfficheTypeNoteArticle();
			((JPALgrEditorPart)e).setPanel(((JPALgrEditorPart)e).getController().getVue());
			((JPALgrEditorPart)e).getController().configPanel(paramAfficheTypeNote);
	} else {
		page.activate(editor);
	}
} catch (WorkbenchException e) {
	logger.error("Erreur pendant l'ouverture de l'éditeur ",e);
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
