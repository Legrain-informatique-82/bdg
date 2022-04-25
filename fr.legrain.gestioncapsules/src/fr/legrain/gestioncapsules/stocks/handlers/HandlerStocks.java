package fr.legrain.gestioncapsules.stocks.handlers;
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

import fr.legrain.articles.editor.EditorInputArticle;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.gestioncapsules.stocks.divers.ParamAfficheStocks;
import fr.legrain.gestioncapsules.stocks.editor.EditorInputStocks;
import fr.legrain.gestioncapsules.stocks.editor.EditorStocks;
import fr.legrain.gestioncapsules.stocks.editor.StocksMultiPageEditor;
import fr.legrain.lib.gui.ParamAffiche;


public class HandlerStocks extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerStocks.class.getName());
	private IWorkbenchWindow window;
	
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}
	private Object ouvreUnEditeur() {
		try {
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
			    openEditor(new EditorInputStocks(), EditorStocks.ID);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);

			ParamAfficheStocks paramAfficheStocks = new ParamAfficheStocks();
			((JPALgrEditorPart)e).setPanel(((JPALgrEditorPart)e).getController().getVue());
			((JPALgrEditorPart)e).getController().configPanel(paramAfficheStocks);
			
		} catch (PartInitException e) {
			logger.error("",e);
		}
		return null;
	}
	
	private Object ouvreMultiPageEditeur() {
		this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		try {
			IEditorPart editor = AbstractLgrMultiPageEditor.chercheEditeurDocumentOuvert(StocksMultiPageEditor.ID_EDITOR);
			if(editor==null){
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
			openEditor(new EditorInputArticle(), StocksMultiPageEditor.ID_EDITOR);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
			ParamAffiche param = new ParamAffiche();
			((AbstractLgrMultiPageEditor)e).findMasterController().configPanel(param);
		} else {
			page.activate(editor);
		}
	} catch (WorkbenchException e) {
		logger.error("Erreur pendant l'ouverture de la perspective Stocks",e);
	}
return null;
}

	public Object execute(ExecutionEvent event) throws ExecutionException {
//		try {
			return ouvreMultiPageEditeur();
//			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInputStocks(), EditorStocks.ID);
//			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
////			SWTPaArticlesController swtPaArticleController = new SWTPaArticlesController(((EditorArticle)e).getComposite());
////			
////			((LgrEditorPart)e).setController(swtPaArticleController);
////			((LgrEditorPart)e).setPanel(((EditorArticle)e).getComposite());
////			
////			ParamAfficheArticles paramAfficheArticles = new ParamAfficheArticles();
////			swtPaArticleController.configPanel(paramAfficheArticles);
//			
//			ParamAfficheStocks paramAfficheArticles = new ParamAfficheStocks();
//			((LgrEditorPart)e).setPanel(((LgrEditorPart)e).getController().getVue());
//			((LgrEditorPart)e).getController().configPanel(paramAfficheArticles);

//		} catch (PartInitException e) {
//			logger.error("",e);
//		}
//		return null;
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
