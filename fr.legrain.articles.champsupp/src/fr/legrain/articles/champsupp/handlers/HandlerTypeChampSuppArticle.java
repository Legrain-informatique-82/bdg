package fr.legrain.articles.champsupp.handlers;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import fr.legrain.articles.champsupp.editors.EditorInputTypeChampSuppArticle;
import fr.legrain.articles.champsupp.editors.EditorTypeChampSuppArticle;
import fr.legrain.articles.ecran.ParamAfficheArticles;
import fr.legrain.articles.ecran.ParamAfficheTTva;
import fr.legrain.articles.ecran.ParamAfficheTva;
import fr.legrain.articles.ecran.SWTPaArticlesController;
import fr.legrain.articles.ecran.SWTPaTTvaController;
import fr.legrain.articles.ecran.SWTPaTvaController;
import fr.legrain.articles.editor.EditorArticle;
import fr.legrain.articles.editor.EditorInputArticle;
import fr.legrain.articles.editor.EditorInputTva;
import fr.legrain.articles.editor.EditorInputTypeTVA;
import fr.legrain.articles.editor.EditorTva;
import fr.legrain.articles.editor.EditorTypeTVA;
import fr.legrain.articles.editor.EditorUnite;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;


public class HandlerTypeChampSuppArticle extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerTypeChampSuppArticle.class.getName());

	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInputTypeChampSuppArticle(), EditorTypeChampSuppArticle.ID);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
//			SWTPaTTvaController swtPaTTvaController = new SWTPaTTvaController(((EditorTypeTVA)e).getComposite());
//			
//			((LgrEditorPart)e).setController(swtPaTTvaController);
//			((LgrEditorPart)e).setPanel(((EditorTypeTVA)e).getComposite());
//			
//			
//			ParamAfficheTTva paramAfficheTTva = new ParamAfficheTTva();
//			swtPaTTvaController.configPanel(paramAfficheTTva);
			
			ParamAfficheTTva paramAfficheArticles = new ParamAfficheTTva();
			((JPALgrEditorPart)e).setPanel(((JPALgrEditorPart)e).getController().getVue());
			((JPALgrEditorPart)e).getController().configPanel(paramAfficheArticles);
			
		} catch (PartInitException e) {
			logger.error("",e);
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
