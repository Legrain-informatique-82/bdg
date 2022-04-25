package fr.legrain.pointsbonus.handler;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PlatformUI;

import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.pointsbonus.divers.ParamAfficheComptePoint;
import fr.legrain.pointsbonus.editor.comptePointMultiPageEditor;
import fr.legrain.tiers.views.ListeTiersView;

public class HandlerListeTiersViewComptePointBonus extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerListeTiersViewComptePointBonus.class.getName());

	public Object execute(ExecutionEvent event) throws ExecutionException{
		try {
			
			IViewReference[] vr = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getViewReferences();
			ISelection selection = null;
			for (int i = 0; i < vr.length; i++) {
				if(vr[i].getId().endsWith(ListeTiersView.ID)) {					
					selection = ((ListeTiersView)vr[i].getView(false)).getController().getView().getViewSite().getSelectionProvider().getSelection();
					ParamAfficheComptePoint param = new ParamAfficheComptePoint();
					param.setSelection(selection);
					AbstractLgrMultiPageEditor.ouvreFiche(null,null, comptePointMultiPageEditor.ID_EDITOR,param,false);
				}
				break;
			}
			
		} catch (Exception e) {
			logger.error("",e);
		}
		return event;
	}
} 
