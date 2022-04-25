package fr.legrain.articles.editor;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import fr.legrain.articles.ecran.PaArticleSWT;
import fr.legrain.articles.ecran.SWTPaArticlesController;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrRetourEditeur;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;

public class EditorArticle extends JPALgrEditorPart implements ILgrEditor, ILgrRetourEditeur {
	
	static Logger logger = Logger.getLogger(EditorArticle.class.getName());
	
	public static final String ID = "fr.legrain.articles.editor.EditorArticle";
	
	private boolean enabled = true;
	private PaArticleSWT composite = null;
	//private SWTPaArticlesController controller = null;
	private EntityManager em = null;

	public EditorArticle() {
		super();
	}
	
	public EditorArticle(EditorPart parent) {
		super(parent);
	}
	
	public EditorArticle(EditorPart parent,EntityManager em) {
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
	
//	private List<IExtensionEcran> liste = null;

	@Override
	public void createPartControl(Composite parent) {
		composite = new PaArticleSWT(parent,SWT.NONE);
		if(getController()==null) {
//			createContributors();
//			if(liste==null)
				setController(new SWTPaArticlesController(composite,em));
//			else
//				setController(new SWTPaArticlesVitiController(composite,em,liste));
		} else {
			getController().setEm(em);
		}
		messageEditeur(composite);
	}
	
//	private void createContributors() {
//		IExtensionRegistry registry = Platform.getExtensionRegistry();
//		IExtensionPoint point = registry.getExtensionPoint("Articles.editorEcranArticles"); //$NON-NLS-1$
//		if (point != null) {
//			IExtension[] extensions = point.getExtensions();
//			for (int i = 0; i < extensions.length; i++) {
//				IConfigurationElement confElements[] = extensions[i].getConfigurationElements();
//				for (int jj = 0; jj < confElements.length; jj++) {
//					try {
//						String editorClass = confElements[jj].getAttribute("classe");//$NON-NLS-1$
//
//						if (editorClass == null)
//							continue;
//
//						Object o = confElements[jj].createExecutableExtension("classe");
//						if(liste == null)
//							liste = new ArrayList<IExtensionEcran>();
//						liste.add(((IExtensionEcran)o));
//
//					} catch (Exception e) {
//						logger.error("Erreur : PaArticlesController", e);
//					}
//				}
//			}
//		}
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

	public PaArticleSWT getComposite() {
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
