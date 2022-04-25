package fr.legrain.tiers.views;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PlatformUI;

import fr.legrain.gestCom.librairiesEcran.swt.ILgrListView;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.editor.TiersMultiPageEditor;

public class HandlerListeTiersViewInserer extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerListeTiersViewInserer.class.getName());

	public Object execute(ExecutionEvent event) throws ExecutionException{
		try {
			
			IViewReference[] vr = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getViewReferences();
			ISelection selection = null;
			for (int i = 0; i < vr.length; i++) {
				if(vr[i].getId().endsWith(ListeTiersView.ID)) {
//					selection = ((ListeTiersView)vr[i].getView(false)).getController().getView().getViewSite().getSelectionProvider().getSelection();
//					ParamAffiche param = new ParamAffiche();
//					param.setSelection(selection);
//					AbstractLgrMultiPageEditor.ouvreFiche(null, TiersMultiPageEditor.ID_EDITOR,param,false);
					
					ParamAffiche param = new ParamAffiche();
					param.setModeEcran(EnumModeObjet.C_MO_INSERTION);
					
					IEditorPart editor = AbstractLgrMultiPageEditor.chercheEditeurDocumentOuvert(TiersMultiPageEditor.ID_EDITOR);
					if(editor==null){
//						IEditorPart e = null;
//
//						e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
//								openEditor(new EditorInputTiers(), TiersMultiPageEditor.ID_EDITOR);

//						LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
						
//						((AbstractLgrMultiPageEditor)e).findMasterController().configPanel(param);
						AbstractLgrMultiPageEditor.ouvreFiche(null,null, TiersMultiPageEditor.ID_EDITOR,param,false);
					} else {
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().activate(editor);
						((AbstractLgrMultiPageEditor)editor).findMasterController().configPanel(param);
					}
					break;
				}
			}
			
		} catch (Exception e) {
			logger.error("",e);
		}
		return event;
	}
} 
