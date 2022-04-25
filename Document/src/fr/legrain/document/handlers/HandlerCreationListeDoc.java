package fr.legrain.document.handlers;
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

import fr.legrain.articles.ecran.ParamAfficheArticles;
import fr.legrain.articles.ecran.ParamAfficheFamille;
import fr.legrain.articles.ecran.SWTPaArticlesController;
import fr.legrain.articles.editor.EditorArticle;
import fr.legrain.articles.editor.EditorInputArticle;
import fr.legrain.document.ecran.ParamAfficheCreationDoc;
import fr.legrain.document.editor.EditorCreationDoc;
import fr.legrain.document.editor.EditorInputCreationDoc;
import fr.legrain.document.editor.EditorListeCreationDoc;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.tiers.editor.EditorTypeNoteTiers;


public class HandlerCreationListeDoc extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerCreationListeDoc.class.getName());
	private IWorkbenchWindow window;
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		try {
			IEditorPart editor = JPALgrEditorPart.verifEditeurOuvert(EditorListeCreationDoc.ID);
			if(editor==null){
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInputCreationDoc(), EditorListeCreationDoc.ID);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
			
			ParamAfficheCreationDoc paramAfficheCreationDoc = new ParamAfficheCreationDoc();
			((LgrEditorPart)e).setPanel(((LgrEditorPart)e).getController().getVue());
			((LgrEditorPart)e).getController().configPanel(paramAfficheCreationDoc);
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
