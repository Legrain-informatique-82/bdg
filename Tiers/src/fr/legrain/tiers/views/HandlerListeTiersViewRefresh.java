package fr.legrain.tiers.views;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PlatformUI;

import fr.legrain.gestCom.librairiesEcran.swt.ILgrListView;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.editor.TiersMultiPageEditor;

public class HandlerListeTiersViewRefresh extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerListeTiersViewRefresh.class.getName());

	public Object execute(ExecutionEvent event) throws ExecutionException{
		try {
			System.out.println("ListeTiersViewController.HandlerListeTiersViewRefresh.execute()");
			
			IViewReference[] vr = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getViewReferences();
			ISelection selection = null;
			for (int i = 0; i < vr.length; i++) {
				if(vr[i].getId().endsWith(ListeTiersView.ID)) {			
					((ListeTiersView)vr[i].getView(false)).getVue().getLgrViewer().getViewer().refresh();
				}
				break;
			}
			
		} catch (Exception e) {
			logger.error("",e);
		}
		return event;
	}
} 
