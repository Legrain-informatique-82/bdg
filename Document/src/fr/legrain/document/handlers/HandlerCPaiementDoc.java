package fr.legrain.document.handlers;
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

import fr.legrain.document.editor.EditorCondPaiementDoc;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.tiers.ecran.ParamAfficheConditionPaiement;
import fr.legrain.tiers.editor.EditorInputTypePaiement;
import fr.legrain.tiers.editor.EditorTypeNoteTiers;


public class HandlerCPaiementDoc extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerCPaiementDoc.class.getName());
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
			IEditorPart editor = JPALgrEditorPart.verifEditeurOuvert(EditorCondPaiementDoc.ID);
			if(editor==null){
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().
			getActivePage().openEditor(new EditorInputTypePaiement(), EditorCondPaiementDoc.ID);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
			
			ParamAfficheConditionPaiement paramAfficheConditionPaiement = new ParamAfficheConditionPaiement();
			paramAfficheConditionPaiement.setTitreFormulaire("Fiche condition de paiement / échéance");
			paramAfficheConditionPaiement.setSousTitre("Gestion des conditions de paiement / échéance");
			paramAfficheConditionPaiement.setTitreGrille("Liste des conditions de paiement / échéance");
			paramAfficheConditionPaiement.setIdTypeCond(1);
			paramAfficheConditionPaiement.setConditionTiers(false);
			((JPALgrEditorPart)e).setPanel(((JPALgrEditorPart)e).getController().getVue());
			((JPALgrEditorPart)e).getController().configPanel(paramAfficheConditionPaiement);
			} else {
				page.activate(editor);
			}
		} catch (WorkbenchException e) {
			logger.error("Erreur pendant l'ouverture de l'éditeur ",e);
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
