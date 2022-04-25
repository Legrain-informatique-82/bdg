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

import fr.legrain.articles.ecran.ParamAfficheArticles;
import fr.legrain.articles.ecran.ParamAfficheTva;
import fr.legrain.articles.ecran.SWTPaArticlesController;
import fr.legrain.articles.ecran.SWTPaTvaController;
import fr.legrain.articles.editor.EditorArticle;
import fr.legrain.articles.editor.EditorInputArticle;
import fr.legrain.articles.editor.EditorInputTva;
import fr.legrain.articles.editor.EditorTva;
import fr.legrain.articles.editor.EditorUnite;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.tiers.editor.EditorTypeBanque;


public class HandlerTva extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerTva.class.getName());
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
			IEditorPart editor = JPALgrEditorPart.verifEditeurOuvert(EditorTva.ID);
			if(editor==null){
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInputTva(), EditorTva.ID);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
		
			ParamAfficheTva paramAfficheTva = new ParamAfficheTva();
			((JPALgrEditorPart)e).setPanel(((JPALgrEditorPart)e).getController().getVue());
			((JPALgrEditorPart)e).getController().configPanel(paramAfficheTva);
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
