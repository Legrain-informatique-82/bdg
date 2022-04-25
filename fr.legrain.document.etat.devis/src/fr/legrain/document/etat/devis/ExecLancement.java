package fr.legrain.document.etat.devis;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;

import fr.legrain.document.etat.devis.preferences.PreferenceConstants;
import fr.legrain.gestionCommerciale.extension.interfaces.IExecLancement;

public class ExecLancement implements IExecLancement {
	static Logger logger = Logger.getLogger(ExecLancement.class.getName());
	
	public static final String commandID = "fr.legrain.document.etat.devis";

	public ExecLancement() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		
		Boolean affichageAuLancement = Activator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_AFFICHAGE_AU_LANCEMENT);

		if(affichageAuLancement) {
			IHandlerService handlerService = null;
			if(handlerService == null)
				handlerService = (IHandlerService)PlatformUI.getWorkbench().getService(IHandlerService.class);

			try {
				handlerService.executeCommand(commandID, null);
			} catch (ExecutionException e) {
				logger.error("",e);
			} catch (NotDefinedException e) {
				logger.error("",e);
			} catch (NotEnabledException e) {
				logger.error("",e);
			} catch (NotHandledException e) {
				logger.error("",e);
			}
		}
	}

}
