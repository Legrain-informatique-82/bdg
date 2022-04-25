package fr.legrain.gestionDossier.handlers;
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

import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.gestionDossier.ecran.ParamInfoEntreprise;
import fr.legrain.gestionDossier.editor.EditorInfoEntreprise;
import fr.legrain.gestionDossier.editor.EditorInputInfoEntreprise;
import fr.legrain.gestionDossier.editor.IdentiteEntrepriseTiersMultiPageEditor;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.ecran.ParamAfficheTiers;


public class HandlerIdentiteEntreprise extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerIdentiteEntreprise.class.getName());
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
		try {
			IEditorPart editor = AbstractLgrMultiPageEditor.chercheEditeurDocumentOuvert(IdentiteEntrepriseTiersMultiPageEditor.ID_EDITOR);
			if(editor==null){
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
			openEditor(new EditorInputInfoEntreprise(), IdentiteEntrepriseTiersMultiPageEditor.ID_EDITOR);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
			ParamInfoEntreprise paramInfoEntreprise = new ParamInfoEntreprise();
			ParamAfficheTiers paramAfficheTiers = new ParamAfficheTiers();
			paramAfficheTiers.setTitreGrille("");
			paramAfficheTiers.setTitreFormulaire("Entreprise");
			paramAfficheTiers.setSousTitre("");
			paramAfficheTiers.setIdTiers(TaTiersDAO.C_ID_IDENTITE_ENTREPRISE_STR);
			paramAfficheTiers.setIdDocument(TaTiersDAO.C_ID_IDENTITE_ENTREPRISE_INT);
			paramAfficheTiers.setIdTypeTiers(TaTiersDAO.C_ID_IDENTITE_ENTREPRISE_STR);
			((AbstractLgrMultiPageEditor)e).findMasterController().configPanel(paramAfficheTiers);
			} else {
				page.activate(editor);
			}
		} catch (WorkbenchException e) {
			logger.error("Erreur pendant l'ouverture de la perspective Identite",e);
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

}
