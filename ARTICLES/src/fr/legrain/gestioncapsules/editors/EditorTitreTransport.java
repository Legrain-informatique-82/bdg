package fr.legrain.gestioncapsules.editors;

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
import fr.legrain.gestioncapsules.controllers.SWTPaTitreTransport;
import fr.legrain.gestioncapsules.ecrans.PaTitreTransport;

public class EditorTitreTransport extends JPALgrEditorPart implements ILgrEditor, ILgrRetourEditeur {
	
	static Logger logger = Logger.getLogger(EditorTitreTransport.class.getName());
	
	public static final String ID = "fr.legrain.gestioncapsules.editors.EditorTitreTransport";
	
	private boolean enabled = true;
	private PaTitreTransport composite = null;
	private EntityManager em = null;

	public EditorTitreTransport() {
		super();
	}
	
	public EditorTitreTransport(EditorPart parent) {
		super(parent);
	}
	
	public EditorTitreTransport(EditorPart parent,EntityManager em) {
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
		composite = new PaTitreTransport(parent,SWT.NONE);
		if(getController()==null) {
			setController(new SWTPaTitreTransport(composite,em));
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
		//c.setVisible(enabled);
		this.enabled = enabled;
	}

	public boolean validate() {
		// TODO Auto-generated method stub
		return true;
	}

	public PaTitreTransport getComposite() {
		return composite;
	}

	public boolean canLeaveThePage() {
		// TODO Auto-generated method stub
		return true;
	}

	public Object retour() {
		// TODO Auto-generated method stub
		return null;
	}

	public void utiliseRetour(Object r) {
		// TODO Auto-generated method stub
	}
	

}
