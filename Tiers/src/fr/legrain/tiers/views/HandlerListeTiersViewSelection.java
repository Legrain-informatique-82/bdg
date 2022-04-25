package fr.legrain.tiers.views;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheVisualisation;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.tiers.ecran.SWTPaTiersController;

public class HandlerListeTiersViewSelection extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerListeTiersViewSelection.class.getName());

	public Object execute(ExecutionEvent event) throws ExecutionException{
		try{
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
			.getActivePage().openEditor(new fr.legrain.visualisation.editor.EditorInputSelectionVisualisation(), 
					fr.legrain.visualisation.editor.EditorSelectionVisualisation.ID);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);

			ParamAfficheVisualisation paramAfficheSelectionVisualisation = new ParamAfficheVisualisation();

			paramAfficheSelectionVisualisation.setNomClassController(SWTPaTiersController.class.getSimpleName());
			paramAfficheSelectionVisualisation.setNomRequete(Const.C_NOM_VU_TIERS);
			paramAfficheSelectionVisualisation.setModule("tiers");

			((LgrEditorPart)e).setPanel(((LgrEditorPart)e).getControllerSelection().getVue());
			((LgrEditorPart)e).getControllerSelection().configPanel(paramAfficheSelectionVisualisation);

		}catch (Exception e) {
			logger.error("",e);
		}	
		return event;
	}
} 
