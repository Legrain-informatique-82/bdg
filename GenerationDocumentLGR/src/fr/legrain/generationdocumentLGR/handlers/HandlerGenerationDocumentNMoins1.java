package fr.legrain.generationdocumentLGR.handlers;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import fr.legrain.generationdocumentLGR.divers.ParamAfficheEditeurListeTiers;
import fr.legrain.generationdocumentLGR.editor.EditorEditeurListeTiers;
import fr.legrain.generationdocumentLGR.editor.EditorEditeurListeTiersNMoins1;
import fr.legrain.generationdocumentLGR.editor.EditorInputEditeurListeTiers;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;

public class HandlerGenerationDocumentNMoins1 extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerGenerationDocumentNMoins1.class.getName());

	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
			openEditor(new EditorInputEditeurListeTiers(), EditorEditeurListeTiersNMoins1.ID);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
			
			ParamAfficheEditeurListeTiers paramAfficheGenerationDocument = new ParamAfficheEditeurListeTiers();
			((LgrEditorPart)e).setPanel(((LgrEditorPart)e).getController().getVue());
			((LgrEditorPart)e).getController().configPanel(paramAfficheGenerationDocument);

		} catch (PartInitException e) {
			logger.error("",e);
		}
		return null;
	}

//	public Object execute(ExecutionEvent event) throws ExecutionException {
//		try {								
//			Shell s = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),LgrShellUtil.styleLgr);		
////			if(MessageDialogLGR.openQuestion(Workbench.getInstance()
////					.getActiveWorkbenchWindow().getShell(),
////					"Calcul des reports de stocks", "Etes-vous sûr de vouloir recalculer les reports de stocks ?",1)){
//				PaSelectionGenerationDocument paGenerationDocument = new PaSelectionGenerationDocument(s,SWT.NULL);
//				PaSelectionGenerationDocumentController paGenerationDocumentController = new PaSelectionGenerationDocumentController(paGenerationDocument);
//				ParamAffiche paramGenerationDocument= new ParamAffiche();
//				paramGenerationDocument.setFocusSWT(paGenerationDocument.getTfCODE_TIERS());
//				ParamAfficheSWT paramAfficheSWT = new ParamAfficheSWT();
//				paramAfficheSWT.setHauteur(200);
//				paramAfficheSWT.setLargeur(500);
//				paramAfficheSWT.setTitre("Choix de la liste des codes tiers.");
//				LgrShellUtil.afficheSWT(paramGenerationDocument, paramAfficheSWT, paGenerationDocument, paGenerationDocumentController, s);
//				if(paramGenerationDocument.getFocus()!=null)
//					paramGenerationDocument.getFocusDefaut().requestFocus();
////			}
//		} catch (Exception e1) {
//			logger.error("Erreur : actionPerformed", e1);
//		}
//		return event;
//	}

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
