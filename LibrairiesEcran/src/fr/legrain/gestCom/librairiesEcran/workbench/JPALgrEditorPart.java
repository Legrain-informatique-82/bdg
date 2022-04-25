package fr.legrain.gestCom.librairiesEcran.workbench;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.ISaveablePart2;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.notifier.NotificationType;
import fr.legrain.gestCom.librairiesEcran.notifier.NotifierComposite;
import fr.legrain.gestCom.librairiesEcran.preferences.PreferenceConstants;
import fr.legrain.gestCom.librairiesEcran.swt.BaseControllerSelection;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.SWTBaseControllerSWTStandard;
import fr.legrain.lib.data.ChangeModeEvent;
import fr.legrain.lib.data.ChangeModeListener;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LibConversion;


abstract public class JPALgrEditorPart extends EditorPart implements 
IPartListener, ISaveablePart2  {
	//	
	static Logger logger = Logger.getLogger(JPALgrEditorPart.class.getName());

	//	public static final String ID = null; //$NON-NLS-1$
	private Composite panel = null;
	private JPABaseControllerSWTStandard controller = null;
	private BaseControllerSelection controllerSelection = null;
	private EditorPart parent = null;


	public JPALgrEditorPart(EditorPart parent) {
		super();
		this.parent = parent;
		initEditor();
	}


	public JPALgrEditorPart() {
		super();
		initEditor();
		//		controller = new SWTPaEditorFactureController(panel);
		//		controllerLigne = new SWTPaLigneController(panel.getPaSaisieLigneSWT(),controller.getSwtFacture());
		//		controller.setControllerLigne(controllerLigne);
		//		controller.getIbTaTable().addChangeModeListener(new ChangeMode());
	}

	abstract protected void initEditor();

	abstract protected String getID();

	private class ChangeMode implements ChangeModeListener {
		public void changementMode(ChangeModeEvent evt) {
			if(!PlatformUI.getWorkbench().getDisplay().isDisposed()) { 
				PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
					public void run() {
						try {
							firePropertyChange(IWorkbenchPartConstants.PROP_DIRTY);
						} catch (Exception e) {
							logger.error(e);
						}
					}
				});
			}
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		//		final Display display = Display.getCurrent();
		//		final boolean[] runLoopAndContinue = new boolean[] {true, true};
		final IProgressMonitor m = monitor;

		m.setCanceled(true);


		try {
			//			display.syncExec(new Runnable() {
			//				public void run() {
			m.setCanceled(false);
			//				}
			//			});
			controller.executeCommand(SWTBaseControllerSWTStandard.C_COMMAND_GLOBAL_ENREGISTRER_ID,true);
			controller.executeCommand(SWTBaseControllerSWTStandard.C_COMMAND_GLOBAL_FERMER_ID,true);
			//			runLoopAndContinue[1] = true;
		} catch (Exception e) {
			logger.error("Erreur à l'enregistrement, fermeture éditeur",e);
			//			runLoopAndContinue[1] = false;	
			m.setCanceled(true);
		}

		//		display.syncExec(new Runnable() {
		//			public void run() {
		//				m.setCanceled(true);
		//				runLoopAndContinue[0] = false;    // Tell method to stop looping.
		//			}
		//		});
		//		display.asyncExec(null);  // Wake SWT UI thread up one more time to make sure it terminates.

	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);

		IContextService ics = (IContextService)getSite().getService(IContextService.class);
		//ics = (IContextService)PlatformUI.getWorkbench().getService(IContextService.class);

	}

	@Override
	public boolean isDirty() {
		changeEditorName();
		if(controller==null || controller.getDaoStandard()==null)
			return false;
		else
			return controller.getDaoStandard().dataSetEnModif();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	public Composite getPanel() {
		return panel;
	}

	public void setPanel(Composite panel) {
		this.panel = panel;
	}

	public JPABaseControllerSWTStandard getController() {
		return controller;
	}

	public void setController(JPABaseControllerSWTStandard controller) {
		this.controller = controller;
		if(parent==null)
			controller.setWorkbenchPart(this);
		else
			controller.setWorkbenchPart(parent);
		if(controller.getDaoStandard()!=null) {
			controller.getDaoStandard().addChangeModeListener(new ChangeMode());
		}
	}

	@Override
	public void setFocus() {
		if(controller!=null && controller.getFocusCourantSWT()!=null)
			controller.getFocusCourantSWT().forceFocus();

		if(LgrPartListener.getLgrPartListener().getLgrActivePart()!=null) {
			if(this!=LgrPartListener.getLgrPartListener().getLgrActivePart()) {
				//activation de la WorkbenchPart verrouillee a la place de la partie demandee
				LgrPartListener.getLgrPartListener().getLgrActivePart().getSite().getPage().activate(LgrPartListener.getLgrPartListener().getLgrActivePart());
			}
		}
	}

	//    class FenetrePart implements IPartListener{
	//		public void partActivated(IWorkbenchPart part) {
	////			if(controller!=null)
	////				controller.activeWorkenchPartCommands(true);
	//		}
	//
	//		public void partBroughtToTop(IWorkbenchPart part) {}
	//
	//		public void partClosed(IWorkbenchPart part) {
	//			try {
	//				//je n'active la perspective bureau que si je ferme l'éditeur facture, et non pas si je ferme
	//				//l'éditeur de l'impression
	//				if(part.getSite().getId().equals(LgrEditorPart.ID_EDITOR)){
	//					controller.getIbTaTable().annuler();
	//					controller.actAnnuler();
	//			    	if (controller!=null){
	//			    		controller.annulerListeContext();
	//			    		controller.setIdContext("");
	//			    	}
	//			    	if (controllerLigne!=null){
	//			    		controllerLigne.annulerListeContext();
	//			    		controllerLigne.setIdContext("");
	//			    	}					
	//					//controller.setControllerLigne(null);
	//					//controller=null;
	////					if(PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null 
	////							&& PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()!=null) {
	////					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closePerspective(
	////					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getPerspective(),
	////					false, false);
	////					}
	//					PlatformUI.getWorkbench().showPerspective(Perspective.ID, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
	//				}
	//			} catch (Exception e) {
	//				logger.error(e);
	//			}
	//		}
	//
	//		public void partDeactivated(IWorkbenchPart part) {
	//			if(controller!=null){
	//				controller.annulerListeContext();
	////				for (IContextActivation c : controller.getListeContext()) {
	////					((IContextService) PlatformUI.getWorkbench().getService(IContextService.class))
	////					.deactivateContext(c);
	////				}
	//				controller.getControllerLigne().annulerListeContext();
	////				for (IContextActivation c : controllerLigne.getListeContext()) {
	////					((IContextService) PlatformUI.getWorkbench().getService(IContextService.class))
	////					.deactivateContext(c);
	////				}
	//			}
	//		}
	//
	//		public void partOpened(IWorkbenchPart part) {
	//			try {
	//				if(part.getSite().getId().equals(LgrEditorPart.ID_EDITOR)){
	//				controller.actInserer();
	//				}
	//			} catch (Exception e) {
	//				logger.error(e);
	//			}
	//		}
	//    }

	private void changeEditorName() {
		//setPartName("Facture "+((PaEditorFactureSWT)controller.getVue()).getTfCODE_FACTURE().getText());
		//setPartName(controller.getTitre());
	}


	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub

	}

	public BaseControllerSelection getControllerSelection() {
		return controllerSelection;
	}


	public void setControllerSelection(BaseControllerSelection controllerSelection) {
		this.controllerSelection = controllerSelection;
		if(parent==null)
			controllerSelection.setWorkbenchPart(this);
		else
			controllerSelection.setWorkbenchPart(parent);
		if(controllerSelection.getDaoStandard()!=null) {
			controllerSelection.getDaoStandard().addChangeModeListener(new ChangeMode());
		}
	}


	/*
	 * Debut IPartListener
	 */

	public void partActivated(IWorkbenchPart part) {
		// TODO Auto-generated method stub
	}


	public void partBroughtToTop(IWorkbenchPart part) {
		// TODO Auto-generated method stub
	}


	public void partClosed(IWorkbenchPart part) {
		if(controller!=null) {
			try {
				controller.onClose();
			} catch (ExceptLgr e) {
				logger.error("",e);
			}
		}
	}


	public void partDeactivated(IWorkbenchPart part) {
		// TODO Auto-generated method stub
	}


	public void partOpened(IWorkbenchPart part) {
		// TODO Auto-generated method stub
	}

	/*
	 * Fin IPartListener
	 */
	static public IEditorPart verifEditeurOuvert() {
		IEditorPart editor = null;
		try {
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			if(page.getEditorReferences().length>0){	//au moins 1 éditeur ouvert			
				int i = 0;
				while(i<page.getEditorReferences().length && editor==null){
					editor = page.getEditorReferences()[i].getEditor(false);
				}
				i++;
			}
			return editor;
		} catch (Exception e) {
			logger.error("",e);
		}
		return editor;
	}

	static public IEditorPart verifEditeurOuvert(String idEditor) {
		IEditorPart editor = null;
		try {
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			if(page.getEditorReferences().length>0){	//au moins 1 éditeur ouvert			
				int i = 0;
				while(i<page.getEditorReferences().length && editor==null){
					//if(page.getEditorReferences()[i].getEditor(false) instanceof AbstractMultiPageDocumentEditor) {
					if(page.getEditorReferences()[i].getEditor(false) instanceof JPALgrEditorPart) {
						if(idEditor!=null && ((JPALgrEditorPart)page.getEditorReferences()[i].getEditor(false)).getID()!=null){
							if (((JPALgrEditorPart)page.getEditorReferences()[i].getEditor(false)).getID().equals(idEditor))
								editor = page.getEditorReferences()[i].getEditor(false);
						} else {
							editor = page.getEditorReferences()[i].getEditor(false);
						}
					}else if(page.getEditorReferences()[i].getEditor(false) instanceof FormEditor) {
						if(idEditor!=null && ((lgrFormEditor)page.getEditorReferences()[i].getEditor(false)).getID()!=null){
							if (((lgrFormEditor)page.getEditorReferences()[i].getEditor(false)).getID().equals(idEditor))
								editor = page.getEditorReferences()[i].getEditor(false);
						} else {
							editor = page.getEditorReferences()[i].getEditor(false);
						}
					}
					i++;
				}
			}
			return editor;
		} catch (Exception e) {
			logger.error("",e);
		}
		return editor;
	}
	public int promptToSaveOnClose() {
		try {
			getController().onClose();
		} catch (Exception e) {
			return ISaveablePart2.CANCEL; 
		}
		return ISaveablePart2.NO;
	}

	public void messageEditeur(Composite composite) {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint("GestionCommerciale.messageEditeur"); //$NON-NLS-1$
		
		boolean messageOptionnel = true;
		messageOptionnel = LibrairiesEcranPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.AFFICHAGE_MESSAGE_EXTENSION_OPTIONNEL);
		
		if (point != null) {
			ImageDescriptor icon = null;
			Object o = null;
			IExtension[] extensions = point.getExtensions();
			for (int i = 0; i < extensions.length; i++) {
				IConfigurationElement confElements[] = extensions[i].getConfigurationElements();
				for (int jj = 0; jj < confElements.length; jj++) {
					try {
						/*
						 * Priorité :
						 * la classe > tout le reste
						 * icone > type_message
						 * url > message
						 */

						String idEditeur = confElements[jj].getAttribute("idEditeur");
						if(idEditeur.equals(getID())) {
							String message = confElements[jj].getAttribute("message");
							String messageEditorClass = confElements[jj].getAttribute("messageEditeurClass");

							String messageTypeAffichage = confElements[jj].getAttribute("typeAffichage");

							String messageIcon = confElements[jj].getAttribute("icone");
							String messageColor = confElements[jj].getAttribute("couleur");

							String messageTitle = confElements[jj].getAttribute("titre");
							String messageURL = confElements[jj].getAttribute("url");
							String messageTypeMessage = confElements[jj].getAttribute("type_message");
							String obligatoire = confElements[jj].getAttribute("obligatoire");
							boolean affiche = true;
							
							if(!messageOptionnel && !LibConversion.StringToBoolean(obligatoire,true)) { 
								affiche = false; 
							} 

							String contributorName = confElements[jj].getContributor().getName();

							o = null;
							if (messageEditorClass != null) {
								o = confElements[jj].createExecutableExtension("messageEditeurClass");
							}
							
							icon = null;
							if(messageIcon!=null) {
								icon = AbstractUIPlugin.imageDescriptorFromPlugin(contributorName, messageIcon);
							}


							if(affiche) {
								/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
								//
								//Pour avoir le background, il faut empecher l'appel à changeCouleur() dans JPABaseController
								//
								if(o!=null) {
									((IMessageEditeur)o).afficheMessage(composite);
								} else {
									Composite c = composite;
									Composite compo = null;
									if(messageTypeAffichage==null || messageTypeAffichage.equals(IMessageEditeur.C_EXT_ATT_TYPE_AFFICHAGE_COMPOSITE)) {

										//Composite compo = new Composite(c, SWT.BORDER);
										//compo.moveAbove(composite.getChildren()[0]);
										//compo.setLayout(new FillLayout());
										//Label l = new Label(compo, SWT.BORDER);
										//GridData gd = new GridData(SWT.FILL,SWT.CENTER,true,false);
										//compo.setLayoutData(gd);
										//l.setText(message);

										compo = new NotifierComposite(
												c, SWT.BORDER,
												messageTitle,
												message,
												NotificationType.valueOf(messageTypeMessage),
												icon,
												messageColor
										);
										compo.moveAbove(composite.getChildren()[0]);
										compo.setLayout(new FillLayout());

										GridData gd = new GridData(SWT.FILL,SWT.CENTER,true,false);
										compo.setLayoutData(gd);


									} else if(messageTypeAffichage.equals(IMessageEditeur.C_EXT_ATT_TYPE_AFFICHAGE_BROWSER)) {
										compo = new Composite(c, SWT.BORDER);
										compo.moveAbove(composite.getChildren()[0]);
										compo.setLayout(new FillLayout());
										Browser l = new Browser(compo, SWT.BORDER);

										GridData gd = new GridData(SWT.FILL,SWT.CENTER,true,false);
										compo.setLayoutData(gd);

										if(messageURL!=null) {
											l.setUrl(messageURL);
										} else {
											l.setText(message);
										}
									}
									
									controller.getListeCompositeExcluCouleur().add(compo);
									
									compo.layout();
									c.layout();
									composite.layout();
								} 
							}

						}

					} catch (Exception e) {
						logger.error("",e);
					}
				}
			}
		}
	}
}
