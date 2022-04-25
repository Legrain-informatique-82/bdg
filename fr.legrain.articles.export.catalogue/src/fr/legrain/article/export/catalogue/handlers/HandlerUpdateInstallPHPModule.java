package fr.legrain.article.export.catalogue.handlers;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.ui.PlatformUI;

import fr.legrain.article.export.catalogue.GestionModulePHP;
import fr.legrain.article.export.catalogue.ImportVersion;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;

public class HandlerUpdateInstallPHPModule extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerUpdateInstallPHPModule.class.getName());

	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}
	
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			//GestionModulePHP.initVersionServeur();
			
			GestionModulePHPProgress e = new GestionModulePHPProgress();
			PlatformUI.getWorkbench().getProgressService().run(true, false, e);
		} catch (Exception e) {
			logger.error("",e);
		}
		return null;
	}


	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false; //plus disponible car version avec webservice
	}

	public boolean isHandled() {
		// TODO Auto-generated method stub
		return false; //plus disponible car version avec webservice
	}

	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

}
