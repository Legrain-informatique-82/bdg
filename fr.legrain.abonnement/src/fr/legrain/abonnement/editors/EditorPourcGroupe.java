package fr.legrain.abonnement.editors;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import fr.legrain.abonnement.controllers.SWTPaPourcGroupeController;
import fr.legrain.abonnement.ecrans.PaPourcGroupeSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrRetourEditeur;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.tiers.dao.TaFamilleTiers;
import fr.legrain.tiers.dao.TaFamilleTiersDAO;
import fr.legrain.tiers.editor.IEditorFamilleTiersExtension;

public class EditorPourcGroupe extends JPALgrEditorPart implements ILgrEditor, ILgrRetourEditeur,IEditorFamilleTiersExtension {
	
	static Logger logger = Logger.getLogger(EditorPourcGroupe.class.getName());
	
	public static final String ID = "fr.legrain.abonnement.editor.EditorPourcGroupe";
	
	private boolean enabled = true;
	private PaPourcGroupeSWT composite = null;
	private EntityManager em = null;
	private TaFamilleTiers masterEntity = null;
	private TaFamilleTiersDAO masterDAO = null;
	public EditorPourcGroupe() {
		super();
	}
	
	public EditorPourcGroupe(EditorPart parent) {
		super(parent);
	}
	
	public EditorPourcGroupe(EditorPart parent,EntityManager em) {
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
	    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().
	    addPartListener(LgrPartListener.getLgrPartListener());
	    
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
		composite = new PaPourcGroupeSWT(parent,SWT.NONE);
		if(getController()==null) {
			setController(new SWTPaPourcGroupeController(composite,em));
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
		// TODO Auto-generated method stub
		return true;
	}

	public PaPourcGroupeSWT getComposite() {
		return composite;
	}

	public boolean canLeaveThePage() {
		// TODO Auto-generated method stub
		return true;
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

	@Override
	public TaFamilleTiers getMasterEntity() {
		// TODO Auto-generated method stub
		return this.masterEntity;
	}

	@Override
	public void setMasterEntity(TaFamilleTiers masterEntity) {
		// TODO Auto-generated method stub
		this.masterEntity = masterEntity;
	}

	@Override
	public TaFamilleTiersDAO getMasterDAO() {
		// TODO Auto-generated method stub
		return this.masterDAO;
	}

	@Override
	public void setMasterDAO(TaFamilleTiersDAO masterDAO) {
		// TODO Auto-generated method stub
		this.masterDAO = masterDAO;
	}

	@Override
	public void activate() {
		//l'ordre est important : le DAO en premier
			((SWTPaPourcGroupeController)getController()).setMasterDAO(getMasterDAO());
		
			((SWTPaPourcGroupeController)getController()).setMasterEntity(getMasterEntity());
	}

}
