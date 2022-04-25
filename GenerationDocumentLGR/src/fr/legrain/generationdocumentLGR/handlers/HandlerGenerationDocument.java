package fr.legrain.generationdocumentLGR.handlers;
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

import fr.legrain.generationdocumentLGR.divers.ParamAfficheEditeurListeTiers;
import fr.legrain.generationdocumentLGR.editor.EditorEditeurListeTiers;
import fr.legrain.generationdocumentLGR.editor.EditorInputEditeurListeTiers;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.tiers.editor.EditorTypeNoteTiers;

public class HandlerGenerationDocument extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerGenerationDocument.class.getName());
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
			IEditorPart editor = JPALgrEditorPart.verifEditeurOuvert(EditorEditeurListeTiers.ID);
			if(editor==null){
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
			openEditor(new EditorInputEditeurListeTiers(), EditorEditeurListeTiers.ID);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
			
			ParamAfficheEditeurListeTiers paramAfficheGenerationDocument = new ParamAfficheEditeurListeTiers();
			((LgrEditorPart)e).setPanel(((LgrEditorPart)e).getController().getVue());
			((LgrEditorPart)e).getController().configPanel(paramAfficheGenerationDocument);
			} else {
				page.activate(editor);
			}
		} catch (WorkbenchException e) {
			logger.error("Erreur pendant l'ouverture de l'éditeur ",e);
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
