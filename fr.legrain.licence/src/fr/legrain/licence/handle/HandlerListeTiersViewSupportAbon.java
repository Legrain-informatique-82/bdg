package fr.legrain.licence.handle;

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
import fr.legrain.licence.divers.ParamAfficheSupport;
import fr.legrain.licence.editors.SupportAbonMultiPageEditor;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.editor.TiersMultiPageEditor;
import fr.legrain.tiers.views.ListeTiersView;

public class HandlerListeTiersViewSupportAbon extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerListeTiersViewSupportAbon.class.getName());

	public Object execute(ExecutionEvent event) throws ExecutionException{
		try {
			
			IViewReference[] vr = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getViewReferences();
			ISelection selection = null;
			for (int i = 0; i < vr.length; i++) {
				if(vr[i].getId().endsWith(ListeTiersView.ID)) {					
					selection = ((ListeTiersView)vr[i].getView(false)).getController().getView().getViewSite().getSelectionProvider().getSelection();
					ParamAfficheSupport param = new ParamAfficheSupport();
					param.setSelection(selection);
					AbstractLgrMultiPageEditor.ouvreFiche(null,null, SupportAbonMultiPageEditor.ID_EDITOR,param,false);
				}
				break;
			}
			
		} catch (Exception e) {
			logger.error("",e);
		}
		return event;
	}
} 
