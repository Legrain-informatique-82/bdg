package fr.legrain.saisiecaisse.handlers;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.saisiecaisse.divers.ParamAfficheEtablissement;
import fr.legrain.saisiecaisse.editor.EditorEtablissement;
import fr.legrain.saisiecaisse.editor.EditorInputEtablissement;
import fr.legrain.saisiecaisse.editor.EditorInputOperation;
import fr.legrain.saisiecaisse.editor.SaisieCaisseMultiPageEditor;


public class HandlerEtablissement extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerEtablissement.class.getName());

	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}
	private Object ouvreUnEditeur() {
		try {
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
			    openEditor(new EditorInputEtablissement(), EditorEtablissement.ID);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);

			ParamAfficheEtablissement paramAfficheStocks = new ParamAfficheEtablissement();
			((JPALgrEditorPart)e).setPanel(((JPALgrEditorPart)e).getController().getVue());
			((JPALgrEditorPart)e).getController().configPanel(paramAfficheStocks);
			
		} catch (PartInitException e) {
			logger.error("",e);
		}
		return null;
	}
	
	private Object ouvreMultiPageEditeur() {
		try {
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
			openEditor(new EditorInputOperation(), SaisieCaisseMultiPageEditor.ID_EDITOR);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);


			ParamAffiche param = new ParamAffiche();
			((AbstractLgrMultiPageEditor)e).findMasterController().configPanel(param);

		} catch (PartInitException e) {
			logger.error("",e);
		} catch (Exception e) {
			logger.error("",e);
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
