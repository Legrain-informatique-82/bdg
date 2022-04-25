package fr.legrain.articles.champsupp.editors;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import fr.legrain.articles.champsupp.controller.SWTPaChampSuppArticleController;
import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.editor.IEditorArticlesExtension;
import fr.legrain.articles.statistiques.editors.IFormPageArticlesContoller;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrRetourEditeur;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;

public class EditorChampSuppArticle extends JPALgrEditorPart implements ILgrEditor, ILgrRetourEditeur, IEditorArticlesExtension {
	
	static Logger logger = Logger.getLogger(EditorChampSuppArticle.class.getName());
	
	public static final String ID = "fr.legrain.articles.champsupp.editors.EditorChampSuppArticle";
	
	private boolean enabled = true;
	private PaChampSuppArticle composite = null;
	private EntityManager em = null;
	
	private TaArticle masterEntity = null;
	private TaArticleDAO masterDAO = null;
	
	private Map<EditorPart,IFormPageArticlesContoller> listeController = new HashMap<EditorPart,IFormPageArticlesContoller>();

	public EditorChampSuppArticle() {
		super();
	}
	
	public EditorChampSuppArticle(EditorPart parent) {
		super(parent);
	}
	
	public EditorChampSuppArticle(EditorPart parent,EntityManager em) {
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

	@Override
	public void createPartControl(Composite parent) {
		composite = new PaChampSuppArticle(parent,SWT.NONE);
		if(getController()==null) {
			setController(new SWTPaChampSuppArticleController(composite,em));
		} else {
			getController().setEm(em);
		}
		messageEditeur(composite);
		listeController.put(this, (IFormPageArticlesContoller) getController());
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

	public PaChampSuppArticle getComposite() {
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
	
	
	
	public TaArticle getMasterEntity() {
		return masterEntity;
	}

	/* (non-Javadoc)
	 * @see fr.legrain.tiers.statistiques.editors.IEditorStat#setMasterEntity(fr.legrain.tiers.dao.TaTiers)
	 */
	public void setMasterEntity(TaArticle masterEntity) {
		this.masterEntity = masterEntity;
		
		//l'ordre est important : le DAO en premier
		for (EditorPart page : listeController.keySet()) {
			((IFormPageArticlesContoller)listeController.get(page)).setMasterDAO(getMasterDAO());
		}
		
		for (EditorPart page : listeController.keySet()) {
			((IFormPageArticlesContoller)listeController.get(page)).setMasterEntity(getMasterEntity());
		}
	}

	/* (non-Javadoc)
	 * @see fr.legrain.tiers.statistiques.editors.IEditorStat#getMasterDAO()
	 */
	public TaArticleDAO getMasterDAO() {
		return masterDAO;
	}

	/* (non-Javadoc)
	 * @see fr.legrain.tiers.statistiques.editors.IEditorStat#setMasterDAO(fr.legrain.tiers.dao.TaTiersDAO)
	 */
	public void setMasterDAO(TaArticleDAO masterDAO) {
		this.masterDAO = masterDAO;
	}

	@Override
	public void activate() {
		//l'ordre est important : le DAO en premier
		for (EditorPart page : listeController.keySet()) {
			((IFormPageArticlesContoller)listeController.get(page)).setMasterDAO(getMasterDAO());
		}
		
		for (EditorPart page : listeController.keySet()) {
			((IFormPageArticlesContoller)listeController.get(page)).setMasterEntity(getMasterEntity());
		}
	}
	

}
