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

import fr.legrain.gestCom.librairiesEcran.workbench.ILgrEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrRetourEditeur;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.pointsbonus.controller.SWTPaComptePointController;
import fr.legrain.pointsbonus.ecran.PaComptePointSWT;

public class EditorComptePoint extends JPALgrEditorPart implements ILgrEditor, ILgrRetourEditeur {
	
	static Logger logger = Logger.getLogger(EditorComptePoint.class.getName());
	
	public static final String ID = "fr.legrain.pointsBonus.editor.EditorComptePoint";
	
	private boolean enabled = true;
	private PaComptePointSWT composite = null;
	private EntityManager em = null;

	public EditorComptePoint() {
		super();
	}
	
	public EditorComptePoint(EditorPart parent) {
		super(parent);
	}
	
	public EditorComptePoint(EditorPart parent,EntityManager em) {
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
		composite = new PaComptePointSWT(parent,SWT.NONE);
		if(getController()==null) {
			setController(new SWTPaComptePointController(composite,em));
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

	public PaComptePointSWT getComposite() {
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
	

}
