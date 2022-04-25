package fr.legrain.typesupport.editor;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.editor.IEditorArticlesExtension;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrRetourEditeur;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.typesupport.controller.SWTPaTypeArticleController;

public class EditorTArticle extends JPALgrEditorPart implements ILgrEditor, ILgrRetourEditeur ,IEditorArticlesExtension{
	
	static Logger logger = Logger.getLogger(EditorTArticle.class.getName());
	
	public static final String ID = "fr.legrain.articles.editor.EditorTArticle";
	
	private boolean enabled = true;
	private fr.legrain.typesupport.ecran.PaTypeArticleSWT composite = null;
	private EntityManager em = null;
	private TaArticle masterEntity = null;
	private TaArticleDAO masterDAO = null;
	

	
	public EditorTArticle() {
		super();
	}
	
	public EditorTArticle(EditorPart parent) {
		super(parent);
	}
	
	public EditorTArticle(EditorPart parent,EntityManager em) {
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
	  	}



	@Override
	public void createPartControl(Composite parent) {
		composite = new fr.legrain.typesupport.ecran.PaTypeArticleSWT(parent,SWT.NONE);
		if(getController()==null) {
			setController(new SWTPaTypeArticleController(composite,em));
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

	public fr.legrain.typesupport.ecran.PaTypeArticleSWT getComposite() {
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

	public void setMasterEntity(TaArticle masterEntity) {
		this.masterEntity = masterEntity;
		
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

/* ************************************************************************************ */


	@Override
	public void activate() {
		//l'ordre est important : le DAO en premier
			((SWTPaTypeArticleController)getController()).setMasterDAO(getMasterDAO());
			((SWTPaTypeArticleController)getController()).setMasterEntity(getMasterEntity());
			((SWTPaTypeArticleController)getController()).refresh();
	}

	@Override
	public TaArticle getMasterEntity() {
		// TODO Auto-generated method stub
		return masterEntity;
	}
	

}
