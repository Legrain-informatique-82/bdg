package fr.legrain.facture.handlers;
import java.io.File;

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

import fr.legrain.document.handlers.LgrAbstractDocumentHandler;
import fr.legrain.facture.editor.EditorInputFacture;
import fr.legrain.facture.editor.FactureMultiPageEditor;
import fr.legrain.facture.perspective.PerspectiveFacture;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.ParamAffiche;


public class HandlerOuvreEditorFactureNew extends LgrAbstractDocumentHandler {

	static Logger logger = Logger.getLogger(HandlerOuvreEditorFactureNew.class.getName());

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
		if(page.getPerspective().getId().equals(PerspectiveFacture.ID)) {
			try {
				EditorInputFacture input = new EditorInputFacture("");
				if(page.getEditorReferences().length-1<0){	//pas d'éditeur ouvert			
					page.openEditor(input, FactureMultiPageEditor.ID_EDITOR);
				} else if (page.closeEditor(page.getEditorReferences()[0].getEditor(false),true)) { //si fermeture de l'éditeur
					
					page.openEditor(input, FactureMultiPageEditor.ID_EDITOR);
				}
//				for(int i=0; i<page.getViewReferences().length;i++) {
//					if(page.getViewReferences()[i].getId().equals(SWTViewAdresseFacturation.ID_VIEW))
//						((SWTViewAdresseFacturation)(page.getViewReferences()[i].getView(false))).getViewController().videVue();
//					else if(page.getViewReferences()[i].getId().equals(SWTViewAdresseLivraison.ID_VIEW))
//						((SWTViewAdresseLivraison)(page.getViewReferences()[i].getView(false))).getViewController().videVue();
//					else if(page.getViewReferences()[i].getId().equals(SWTViewConditionPaiement.ID_VIEW))
//						((SWTViewConditionPaiement)(page.getViewReferences()[i].getView(false))).getViewController().videVue();
//					
//				}
				PlatformUI.getWorkbench().showPerspective(PerspectiveFacture.ID, window);
			}catch (PartInitException e) {
				logger.error("Erreur pendant l'ouverture d'un nouvel editeur facture "+FactureMultiPageEditor.ID_EDITOR,e);
			} catch (WorkbenchException e) {
				logger.error("Erreur pendant l'ouverture de la perspective "+PerspectiveFacture.ID,e);
			}
		} else { //on ouvre la perspective avec l'editeur par défaut
			try {
				//PlatformUI.getWorkbench().showPerspective(PerspectiveFacture.ID, window);
				IEditorPart editor = AbstractLgrMultiPageEditor.chercheEditeurDocumentOuvert(FactureMultiPageEditor.ID_EDITOR);
				if(editor==null ) {
					//((AbstractLgrMultiPageEditor)editor).findMasterController().setActiveAide(true);
					//page.closeEditor(editor,true);

					EditorInputFacture input = new EditorInputFacture("");
					IEditorPart e = page.openEditor(input, FactureMultiPageEditor.ID_EDITOR);
					ParamAffiche param = new ParamAffiche();
					param.setModeEcran(EnumModeObjet.C_MO_INSERTION);
					((AbstractLgrMultiPageEditor)e).findMasterController().configPanel(param);
				} else {
					page.activate(editor);
				}
			} catch (WorkbenchException e) {
				logger.error("Erreur pendant l'ouverture de la perspective "+PerspectiveFacture.ID,e);
			}
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


	//Deletes all files and subdirectories under dir.
	// Returns true if all deletions were successful.
	// If a deletion fails, the method stops attempting to delete and returns false.
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i=0; i<children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

}
