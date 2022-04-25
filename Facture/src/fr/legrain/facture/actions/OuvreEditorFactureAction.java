package fr.legrain.facture.actions;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class OuvreEditorFactureAction extends Action implements IWorkbenchWindowActionDelegate ,
	IWorkbenchAction {
	
		static Logger logger = Logger.getLogger(OuvreEditorFactureAction.class.getName());
		
		private IWorkbenchWindow window;
		
		public void dispose() {}
		
		public void init(IWorkbenchWindow window) {
			this.window = window;
		}
		
		public void run(IAction action) {
			logger.error("utiliser HandlerOuvreEditorFacture.java et les commande plutot que l'action");
//			IWorkbenchPage page = window.getActivePage();
//			if(page.getPerspective().getId().equals(PerspectiveFacture.ID)) {
//				try {
//					EditorInputFacture input = new EditorInputFacture("");
//					if(page.getEditorReferences().length-1<0){	//pas d'éditeur ouvert			
//						page.openEditor(input, SWTEditorFacture.ID_EDITOR);
//					} else if (page.closeEditor(page.getEditorReferences()[0].getEditor(false),true)) { //si fermeture de l'éditeur
//						page.openEditor(input, SWTEditorFacture.ID_EDITOR);
//					}
//					for(int i=0; i<page.getViewReferences().length;i++) {
//						if(page.getViewReferences()[i].getId().equals(SWTViewAdresseFacturation.ID_VIEW))
//							((SWTViewAdresseFacturation)(page.getViewReferences()[i].getView(false))).getViewController().videVue();
//						else if(page.getViewReferences()[i].getId().equals(SWTViewAdresseLivraison.ID_VIEW))
//							((SWTViewAdresseLivraison)(page.getViewReferences()[i].getView(false))).getViewController().videVue();
//						else if(page.getViewReferences()[i].getId().equals(SWTViewConditionPaiement.ID_VIEW))
//							((SWTViewConditionPaiement)(page.getViewReferences()[i].getView(false))).getViewController().videVue();
//						
//					}
//					PlatformUI.getWorkbench().showPerspective(PerspectiveFacture.ID, window);
//				}catch (PartInitException e) {
//					logger.error("Erreur pendant l'ouverture d'un nouvel editeur facture "+EditorFacture.ID_EDITOR,e);
//				} catch (WorkbenchException e) {
//					logger.error("Erreur pendant l'ouverture de la perspective "+PerspectiveFacture.ID,e);
//				}
//			} else { //on ouvre la perspective avec l'editeur par défaut
//				try {
//					PlatformUI.getWorkbench().showPerspective(PerspectiveFacture.ID, window);
//				} catch (WorkbenchException e) {
//					logger.error("Erreur pendant l'ouverture de la perspective "+PerspectiveFacture.ID,e);
//				}
//			}
		}
		
//		public void run(IAction action) {
//			IWorkbenchPage page = window.getActivePage();
//			if(page.getPerspective().getId().equals(PerspectiveFacture.ID)) {
//				try {
//					EditorInputFacture input = new EditorInputFacture("");
//					if(page.getEditorReferences().length-1<0){	//pas d'éditeur ouvert			
//						page.openEditor(input, EditorFacture.ID_EDITOR);
//					} else if (page.closeEditor(page.getEditorReferences()[0].getEditor(false),true)) { //si fermeture de l'éditeur
//						page.openEditor(input, EditorFacture.ID_EDITOR);
//					}
//					for(int i=0; i<page.getViewReferences().length;i++) {
//						if(page.getViewReferences()[i].getId().equals(ViewAdresseFacturation.ID_VIEW))
//							((ViewAdresseFacturation)(page.getViewReferences()[i].getView(false))).getViewController().videVue();
//						else if(page.getViewReferences()[i].getId().equals(ViewAdresseLivraison.ID_VIEW))
//							((ViewAdresseLivraison)(page.getViewReferences()[i].getView(false))).getViewController().videVue();
//						else if(page.getViewReferences()[i].getId().equals(ViewConditionPaiement.ID_VIEW))
//							((ViewConditionPaiement)(page.getViewReferences()[i].getView(false))).getViewController().videVue();
//						
//					}
//				}catch (PartInitException e) {
//					logger.error("Erreur pendant l'ouverture d'un nouvel editeur facture "+EditorFacture.ID_EDITOR,e);
//				}
//			} else { //on ouvre la perspective avec l'editeur par défaut
//				try {
//					PlatformUI.getWorkbench().showPerspective(PerspectiveFacture.ID, window);
//				} catch (WorkbenchException e) {
//					logger.error("Erreur pendant l'ouverture de la perspective "+PerspectiveFacture.ID,e);
//				}
//			}
//		}

		
		public void selectionChanged(IAction action, ISelection selection) {}
		
		public void addPropertyChangeListener(IPropertyChangeListener listener) {}
		
		public int getAccelerator() {
			return 0;
		}
		
		public String getActionDefinitionId() {
			return null;
		}
		
		public String getDescription() {
			return null;
		}
		
		public ImageDescriptor getDisabledImageDescriptor() {
			return null;
		}
		
		public HelpListener getHelpListener() {
			return null;
		}
		
		public ImageDescriptor getHoverImageDescriptor() {
			return null;
		}
		
		public String getId() {
			return null;
		}
		
		public ImageDescriptor getImageDescriptor() {
			return null;
		}
		
		public IMenuCreator getMenuCreator() {
			return null;
		}
		
		public int getStyle() {
			return 0;
		}
		
		public String getText() {
			return null;
		}
		
		public String getToolTipText() {
			return null;
		}
		
		public boolean isChecked() {
			return false;
		}
		
		public boolean isEnabled() {
			return false;
		}
		
		public boolean isHandled() {
			return false;
		}
		
		public void removePropertyChangeListener(IPropertyChangeListener listener) {}
		
		public void run() {
////			Object item = selection.getFirstElement();
//			//ContactsEntry entry = (ContactsEntry) item;
//			IWorkbenchPage page = window.getActivePage();
//			TestEditorInput input = new TestEditorInput("aa");
//			try {
//			page.openEditor(input, NewEditor.ID);
//			} catch (PartInitException e) {
//			// handle error
//			System.out.println("Debug : TestEditorAction - run");
//			}
		}
		
		public void runWithEvent(Event event) {}
		
		public void setActionDefinitionId(String id) {}
		
		public void setChecked(boolean checked) {}
		
		public void setDescription(String text) {}
		
		public void setDisabledImageDescriptor(ImageDescriptor newImage) {}
		
		public void setEnabled(boolean enabled) {}
		
		public void setHelpListener(HelpListener listener) {}
		
		public void setHoverImageDescriptor(ImageDescriptor newImage) {}
		
		public void setId(String id) {}
		
		public void setImageDescriptor(ImageDescriptor newImage) {}
		
		public void setMenuCreator(IMenuCreator creator) {}
		
		public void setText(String text) {}
		
		public void setToolTipText(String text) {}
		
		public void setAccelerator(int keycode) {}
		
	}
