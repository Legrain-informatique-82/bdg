package fr.legrain.licenceepicea.editors;

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
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.licenceepicea.controllers.SWTPaEpiceaController;
import fr.legrain.licenceepicea.ecrans.PaEpiceaSWT;
import fr.legrain.tiers.ecran.PaTAdrSWT;
import fr.legrain.tiers.ecran.SWTPaTAdrController;

public class EditorLicenceEpicea extends JPALgrEditorPart implements ILgrEditor, ILgrRetourEditeur {
	
	static Logger logger = Logger.getLogger(EditorLicenceEpicea.class.getName());
	
	public static final String ID = "fr.legrain.licenceEpicea";
	
	private boolean enabled = true;
	private PaEpiceaSWT composite = null;

	private EntityManager em = null;

	public EditorLicenceEpicea() {
		super();
	}
	
	public EditorLicenceEpicea(EditorPart parent) {
		super(parent);
	}
	
	public EditorLicenceEpicea(EditorPart parent,EntityManager em) {
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
		composite = new PaEpiceaSWT(parent,SWT.NONE);
		if(getController()==null) {
			setController(new SWTPaEpiceaController(composite,em));
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
		this.enabled = enabled;
	}

	public boolean validate() {
		// TODO Auto-generated method stub
		return true;
	}

	public PaEpiceaSWT getComposite() {
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
	public String getTitle() {
		return "Licence Comptabilité";
	}
}
