package fr.legrain.articles.editor;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import fr.legrain.articles.ecran.PaCatalogueWebSWT;
import fr.legrain.articles.ecran.PaPrixSWT;
import fr.legrain.articles.ecran.SWTPaCatalogueWebController;
import fr.legrain.articles.ecran.SWTPaPrixController;
import fr.legrain.gestCom.librairiesEcran.notifier.NotificationType;
import fr.legrain.gestCom.librairiesEcran.notifier.NotifierComposite;
import fr.legrain.gestCom.librairiesEcran.notifier.NotifierDialog;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrRetourEditeur;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;

public class EditorCatalogueWeb extends JPALgrEditorPart implements ILgrEditor, ILgrRetourEditeur {
	
	static Logger logger = Logger.getLogger(EditorCatalogueWeb.class.getName());
	
	public static final String ID = "fr.legrain.articles.editor.EditorCatalogueWeb";
	
	private boolean enabled = true;
	private PaCatalogueWebSWT composite = null;
	private EntityManager em = null;

	public EditorCatalogueWeb() {
		super();
	}
	
	public EditorCatalogueWeb(EditorPart parent) {
		super(parent);
	}
	
	public EditorCatalogueWeb(EditorPart parent,EntityManager em) {
		super(parent);
		this.em=em;
	}
	
	@Override
	protected void initEditor() {
		setPanel(composite);
	}
	
	@Override
	protected String getID() {
		return ID;
	}

//	@Override
//	public void doSave(IProgressMonitor monitor) {
//		// TODO Auto-generated method stub
//	}
//
//	@Override
//	public void doSaveAs() {
//		// TODO Auto-generated method stub
//	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
	    setInput(input);
	    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().addPartListener(LgrPartListener.getLgrPartListener());
	    
	    //getSite().getWorkbenchWindow().getPartService().addPartListener(TestPartListener.getTestPartListener());
	    //getSite().getPage().addPartListener(TestPartListenerVide.getTestPartListener());
	    //TestPartListener.getTestPartListener().setLgrActivePart(this);
	}

//	@Override
//	public boolean isDirty() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isSaveAsAllowed() {
//		// TODO Auto-generated method stub
//		return false;
//	}
	
//	public void messageEditeur() {
//		IExtensionRegistry registry = Platform.getExtensionRegistry();
//		IExtensionPoint point = registry.getExtensionPoint("Articles.messageEditeur"); //$NON-NLS-1$
//		if (point != null) {
//			//Map<Integer, List<IMultiPaneEditorContributor>> seq2contributors = new HashMap<Integer, List<IMultiPaneEditorContributor>>();
//			ImageDescriptor icon = null;
//			IExtension[] extensions = point.getExtensions();
//			for (int i = 0; i < extensions.length; i++) {
//				IConfigurationElement confElements[] = extensions[i].getConfigurationElements();
//				for (int jj = 0; jj < confElements.length; jj++) {
//					try {
//						String editorClass = confElements[jj].getAttribute("message");//$NON-NLS-1$
////						String editorName = confElements[jj].getAttribute("editorLabel");//$NON-NLS-1$
////						String editorIcon = confElements[jj].getAttribute("editorIcon");//$NON-NLS-1$
//						String contributorName = confElements[jj].getContributor().getName();
//						
////						if (editorClass == null || editorName == null)
////							continue;
////
////						Object o = confElements[jj].createExecutableExtension("editorClass");
////						if(editorIcon!=null) {
////							icon = AbstractUIPlugin.imageDescriptorFromPlugin(contributorName, editorIcon);
////						}
////						addEditor((ILgrEditor)o,editorName,icon);
//						
///*
// * ************************************************************************************************************************************************						
// */
//						/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////						Composite c = composite;
////						
////						Composite compo = new Composite(c, SWT.BORDER);
////						compo.moveAbove(composite.getChildren()[0]);
////						compo.setLayout(new FillLayout());
////						Browser l = new Browser(compo, SWT.BORDER);
////						
////						GridData gd = new GridData(SWT.FILL,SWT.CENTER,true,false);
////						compo.setLayoutData(gd);
////						
////						//l.setText(editorClass);
////						l.setUrl("http://www.legrain.fr");
////						
////						compo.layout();
////						c.layout();
////						composite.layout();
//						/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//						
//						/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//						//
//						//Pour avoir le background, il faut empecher l'appel à changeCouleur() dans JPABaseController
//						//
//						Composite c = composite;
//						Composite compo = new NotifierComposite(c, SWT.BORDER,"PUB VERSION N*",editorClass,NotificationType.INFO);
//						compo.moveAbove(composite.getChildren()[0]);
//						compo.setLayout(new FillLayout());
//						
//						GridData gd = new GridData(SWT.FILL,SWT.CENTER,true,false);
//						compo.setLayoutData(gd);
//						
//						composite.getLaExpediable().setText("extension");
//						
//						compo.layout();
//						c.layout();
//						composite.layout();
//						/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//						
//						/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////						Composite c = composite;
////						
////						Composite compo = new Composite(c, SWT.BORDER);
////						compo.moveAbove(composite.getChildren()[0]);
////						compo.setLayout(new FillLayout());
////						Label l = new Label(compo, SWT.BORDER);
////						
////						GridData gd = new GridData(SWT.FILL,SWT.CENTER,true,false);
////						compo.setLayoutData(gd);
////						
////						l.setText(editorClass);
////						
////						composite.getLaExpediable().setText("extension");
////						
////						compo.layout();
////						c.layout();
////						composite.layout();
//						/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//						
//						/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////						Composite c = composite.getPaGrille();
////						
////						Composite compo = new Composite(c, SWT.BORDER);
////						compo.moveAbove(composite.getGrille());
////						compo.setLayout(new FillLayout());
////						Label l = new Label(compo, SWT.BORDER);
////						
////						GridData gd = new GridData(SWT.FILL,SWT.CENTER,true,false);
////						compo.setLayoutData(gd);
////						
////						l.setText(editorClass);
////						
////						composite.getLaExpediable().setText("extension");
////						
////						compo.layout();
////						c.layout();
////						composite.layout();
//						/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//						
//						/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////						//NotifierDialog.notify("Bureau de gestion", 
////						//		editorClass, 
////						//		NotificationType.INFO
////						//		,vue.getTfCODE_TIERS().toDisplay(vue.getTfCODE_TIERS().getLocation().x, vue.getTfCODE_TIERS().getLocation().y).x + vue.getTfCODE_TIERS().getSize().x + 10, 
////						//		vue.getTfCODE_TIERS().toDisplay(vue.getTfCODE_TIERS().getLocation().x, vue.getTfCODE_TIERS().getLocation().y).y
////						//);
////
////						NotifierDialog.notify("Bureau de gestion", 
////								editorClass, 
////								NotificationType.INFO
////						);
////						composite.layout();
//						/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//						
///*
// * ************************************************************************************************************************************************												
// */
//
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}
//	}

	@Override
	public void createPartControl(Composite parent) {
		composite = new PaCatalogueWebSWT(parent,SWT.NONE);
		if(getController()==null) {
			setController(new SWTPaCatalogueWebController(composite,em));
		} else {
			getController().setEm(em);
		}
		messageEditeur(composite);
	}

//	@Override
//	public void setFocus() {
//		// TODO Auto-generated method stub
//	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		composite.setEnabled(enabled);
		//c.setVisible(enabled);
		this.enabled = enabled;
	}

	public boolean validate() {
		try {
			getController().validateUI();
		} catch (Exception e) {
			return false;
		}
		if(!getController().changementPageValide())return false;
		return true;
	}

	public PaCatalogueWebSWT getComposite() {
		return composite;
	}

	public boolean canLeaveThePage() {
		//return true;
		return validate();
	}

	public Object retour() {
		// TODO Auto-generated method stub
		//return c.getText1().getText();
		return null;
	}

	public void utiliseRetour(Object r) {
		// TODO Auto-generated method stub
		//c.getText2().setText(r.toString());
	}
	

}
