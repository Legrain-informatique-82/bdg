package fr.legrain.gestCom.librairiesEcran.workbench;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import fr.legrain.lib.gui.ParamAffiche;

public class LgrPartUtil {

	static Logger logger = Logger.getLogger(LgrPartUtil.class.getName());

	static public void ouvreDocument(String valeurIdentifiant,String idEditor){
		AbstractLgrMultiPageEditor.ouvreFiche(null,valeurIdentifiant, idEditor,null,null);
//		if(idEditor!=null) {
//			IEditorPart editor = AbstractLgrMultiPageEditor.chercheEditeurDocumentOuvert(idEditor);
//			if(editor==null) {
//				try {
//					IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new IEditorInput(){
//
//						public boolean exists() {
//							return false;
//						}
//						public ImageDescriptor getImageDescriptor() {
//							return null;
//						}
//						public String getName() {
//							return "";
//						}
//						public IPersistableElement getPersistable() {
//							return null;
//						}
//						public String getToolTipText() {
//							return "";
//						}
//						public Object getAdapter(Class adapter) {
//							return null;
//						}
//					}, idEditor);
//
//					LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
//
//
//					ParamAffiche paramAffiche = new ParamAffiche();
//					if(e instanceof AbstractLgrMultiPageEditor) {
//						paramAffiche.setCodeDocument(valeurIdentifiant);
//						((AbstractLgrMultiPageEditor)e).findMasterController().configPanel(paramAffiche);
//					} else {
//						((JPALgrEditorPart)e).setPanel(((JPALgrEditorPart)e).getController().getVue());
//						paramAffiche.setCodeDocument(valeurIdentifiant);
//						((JPALgrEditorPart)e).getController().configPanel(paramAffiche);
//					}
//
//				} catch (PartInitException e1) {
//					logger.error("",e1);
//				}
//			} else {
//				if(MessageDialog.openQuestion(
//						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
//						"Affichage document",
//				"Voulez-vous abandonner l'élément ("+editor.getTitle()+") en cours ?")){// de saisie
//					ParamAffiche paramAffiche = new ParamAffiche();
//					if(editor instanceof AbstractLgrMultiPageEditor) {
//						paramAffiche.setCodeDocument(valeurIdentifiant);
//						((AbstractLgrMultiPageEditor)editor).findMasterController().configPanel(paramAffiche);
//					}
//				}
//				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().activate(editor);
//			}
//		} else {
//			logger.debug("La variable idEditor n'est pas renseignée, impossible d'ouvrir un éditeur.");
//		}
	}
}
