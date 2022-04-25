package fr.legrain.tiers.handlers;
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

import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.tiers.ecran.ParamAfficheBanque;
import fr.legrain.tiers.ecran.ParamAfficheCommercial;
import fr.legrain.tiers.ecran.ParamAfficheTiers;
import fr.legrain.tiers.ecran.SWTPaBanqueController;
import fr.legrain.tiers.ecran.SWTPaCommercialController;
import fr.legrain.tiers.ecran.SWTPaTiersController;
import fr.legrain.tiers.editor.EditorBanque;
import fr.legrain.tiers.editor.EditorCommercial;
import fr.legrain.tiers.editor.EditorInputBanque;
import fr.legrain.tiers.editor.EditorInputCommercial;
import fr.legrain.tiers.editor.EditorInputTiers;
import fr.legrain.tiers.editor.EditorTiers;
import fr.legrain.tiers.editor.EditorTypeBanque;


public class HandlerCommercial extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerCommercial.class.getName());
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
			IEditorPart editor = JPALgrEditorPart.verifEditeurOuvert(EditorCommercial.ID);
			if(editor==null){
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInputCommercial(), EditorCommercial.ID);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
			ParamAfficheCommercial paramAfficheCommercial = new ParamAfficheCommercial();
			((LgrEditorPart)e).setPanel(((LgrEditorPart)e).getController().getVue());
			((LgrEditorPart)e).getController().configPanel(paramAfficheCommercial);
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
