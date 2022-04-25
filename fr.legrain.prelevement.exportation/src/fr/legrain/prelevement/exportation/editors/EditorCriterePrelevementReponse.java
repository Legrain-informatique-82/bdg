package fr.legrain.prelevement.exportation.editors;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import fr.legrain.gestCom.librairiesEcran.workbench.ILgrEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrRetourEditeur;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.prelevement.exportation.controllers.presentationBanque.PaCriterePresentationController;
import fr.legrain.prelevement.exportation.controllers.reponse.PaCriterePrelevementReponseController;
import fr.legrain.prelevement.exportation.ecrans.presentationBanque.PaCriterePrelevementPresentation;
import fr.legrain.prelevement.exportation.ecrans.reponses.PaCriterePrelevementReponse;



public class EditorCriterePrelevementReponse extends JPALgrEditorPart implements ILgrEditor, ILgrRetourEditeur {
	
	static Logger logger = Logger.getLogger(EditorCriterePrelevementReponse.class.getName());
	
	public static final String ID = "fr.legrain.prelevement.exportation.editor.EditorCriterePrelevementReponse";
	
	private boolean enabled = true;
	private PaCriterePrelevementReponse composite = null;
	private EntityManager em = null;

	public EditorCriterePrelevementReponse() {
		super();
	}
	
	public EditorCriterePrelevementReponse(EditorPart parent) {
		super(parent);
	}
	
	public EditorCriterePrelevementReponse(EditorPart parent,EntityManager em) {
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
		composite = new PaCriterePrelevementReponse(parent,SWT.NONE);
		if(getController()==null) {
			setController(new PaCriterePrelevementReponseController(composite,em));
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
			return true;
			//return(!getController().getDaoStandard().dataSetEnModif());
		} catch (Exception e) {
			return false;
		}
	}

	public PaCriterePrelevementReponse getComposite() {
		return composite;
	}

	public boolean canLeaveThePage() {
		//return true;
		return getController().changementPageValide();
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
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Prélèvements en attente de réponse";
	}


	

}
