package fr.legrain.pointsbonus.editor;

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
import fr.legrain.pointsbonus.controller.SWTPaArticlePointController;
import fr.legrain.pointsbonus.divers.ParamAfficheArticePoint;
import fr.legrain.pointsbonus.ecran.PaArticlePointSWT;

public class EditorArticlePoint2 extends JPALgrEditorPart implements ILgrEditor, ILgrRetourEditeur,IEditorArticlesExtension {
	
	static Logger logger = Logger.getLogger(EditorArticlePoint2.class.getName());
	
	public static final String ID = "fr.legrain.pointsBonus.editor.EditorArticlePoint2";
	
	private boolean enabled = true;
	private PaArticlePointSWT composite = null;
	private EntityManager em = null;

	private TaArticle masterEntity = null;
	private TaArticleDAO masterDAO = null;
	
	public EditorArticlePoint2() {
		super();
	}
	
	public EditorArticlePoint2(EditorPart parent) {
		super(parent);
	}
	
	public EditorArticlePoint2(EditorPart parent,EntityManager em) {
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
		composite = new PaArticlePointSWT(parent,SWT.NONE);
		if(getController()==null) {
			setController(new SWTPaArticlePointController(composite,em));
		} else {
			getController().setEm(em);
		}
		messageEditeur(composite);
	}


	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		composite.setEnabled(enabled);
		this.enabled = enabled;
	}

	public boolean validate() {
		try {
			getController().validateUI();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public PaArticlePointSWT getComposite() {
		return composite;
	}

	public boolean canLeaveThePage() {
		return validate();
	}

	public Object retour() {
		// TODO Auto-generated method stub
		return null;
	}

	public void utiliseRetour(Object r) {
		// TODO Auto-generated method stub
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
			((SWTPaArticlePointController)getController()).setMasterDAO(getMasterDAO());
			((SWTPaArticlePointController)getController()).setMasterEntity(getMasterEntity());
			((SWTPaArticlePointController)getController()).configPanel(new ParamAfficheArticePoint());
	}

	@Override
	public TaArticle getMasterEntity() {
		// TODO Auto-generated method stub
		return masterEntity;
	}
	

}
