package fr.legrain.document.etat.devis.handlers;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import fr.legrain.document.etat.devis.editors.FormEditorEtat;
import fr.legrain.document.etat.devis.editors.FormEditorInputEtat;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;

public class HandlerEtatDevis extends LgrAbstractHandler {

	static Logger logger = Logger.getLogger(HandlerEtatDevis.class.getName());

	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(
						new FormEditorInputEtat(), FormEditorEtat.ID
					);
		} catch (PartInitException e) {
			logger.error("",e);
		} catch (Exception e) {
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

}
