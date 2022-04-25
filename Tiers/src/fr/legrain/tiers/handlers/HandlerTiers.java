package fr.legrain.tiers.handlers;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.tiers.ecran.ParamAfficheTiers;
import fr.legrain.tiers.editor.EditorInputTiers;
import fr.legrain.tiers.editor.EditorTiers;
import fr.legrain.tiers.editor.TiersMultiPageEditor;
import fr.legrain.tiers.views.ListeTiersView;


public class HandlerTiers extends LgrAbstractHandler {

	static Logger logger = Logger.getLogger(HandlerTiers.class.getName());
	private IWorkbenchWindow window;

	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}

	private Object ouvreUnEditeur() {
		try {
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInputTiers(), EditorTiers.ID);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);

			ParamAfficheTiers paramAfficheTiers = new ParamAfficheTiers();
			((JPALgrEditorPart)e).setPanel(((JPALgrEditorPart)e).getController().getVue());
			((JPALgrEditorPart)e).getController().configPanel(paramAfficheTiers);
		} catch (PartInitException e) {
			logger.error("",e);
		}
		return null;
	}

	private Object ouvreMultiPageEditeur() {
		this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		try {
			IEditorPart editor = AbstractLgrMultiPageEditor.chercheEditeurDocumentOuvert(TiersMultiPageEditor.ID_EDITOR);
			if(editor==null){
				IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
						openEditor(new EditorInputTiers(), TiersMultiPageEditor.ID_EDITOR);
				LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
				ParamAffiche param = new ParamAffiche();
				((AbstractLgrMultiPageEditor)e).findMasterController().configPanel(param);
			} else {
				page.activate(editor);
			}
		} catch (WorkbenchException e) {
			logger.error("Erreur pendant l'ouverture de la perspective Tiers",e);
		}
		return null;
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ListeTiersView.ID);
		} catch (PartInitException e) {
			logger.error("",e);
		}
		return null;
		//return ouvreMultiPageEditeur();
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
