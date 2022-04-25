package fr.legrain.majcomptelegrainfr.editors;

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
import fr.legrain.majcomptelegrainfr.controller.SWTPaMajCompteLegrainFrController;
import fr.legrain.majcomptelegrainfr.ecrans.PaMajCompteLegrainFr;

public class EditorMajCompteLegrainFr extends LgrEditorPart implements ILgrEditor, ILgrRetourEditeur {
	
	static Logger logger = Logger.getLogger(EditorMajCompteLegrainFr.class.getName());
	
	public static final String ID = "fr.legrain.majCompteLegrainFr.editors.EditorMajCompteLegrainFr";
	
	private boolean enabled = true;
	private PaMajCompteLegrainFr composite = null;
	private EntityManager em = null;

	public EditorMajCompteLegrainFr() {
		super();
	}
	
	public EditorMajCompteLegrainFr(EditorPart parent) {
		super(parent);
	}
	
	public EditorMajCompteLegrainFr(EditorPart parent,EntityManager em) {
		super(parent);
		this.em=em;
	}
	
	@Override
	protected void initEditor() {
		setPanel(composite);
	}
	
//	@Override
//	protected String getID() {
//		return ID;
//	}

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
		composite = new PaMajCompteLegrainFr(parent,SWT.NONE);
		if(getController()==null) {
			setController(new SWTPaMajCompteLegrainFrController(composite,em));
		} else {
			getController().setEm(em);
		}
//		messageEditeur(composite);
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

	public PaMajCompteLegrainFr getComposite() {
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
	
//	@Override
//	public int promptToSaveOnClose() {
//		try {
//			getController().onClose();
//		} catch (Exception e) {
//			return ISaveablePart2.CANCEL; 
//		}
//		return ISaveablePart2.NO;
//	}
}
