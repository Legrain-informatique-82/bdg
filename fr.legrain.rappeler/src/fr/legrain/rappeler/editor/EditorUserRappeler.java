package fr.legrain.rappeler.editor;

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
import fr.legrain.rappeler.ecran.PaUserRappeler;

import fr.legrain.rappeler.ecran.SWTPaUserRappelerController;


public class EditorUserRappeler extends JPALgrEditorPart implements ILgrEditor, ILgrRetourEditeur {
	
	static Logger logger = Logger.getLogger(EditorUserRappeler.class.getName());
	
	public static final String ID = "fr.legrain.rappeler.newMail";
	
	private boolean enabled = true;
	private PaUserRappeler composite = null;
	//private SWTPaArticlesController controller = null;

	public EditorUserRappeler() {
		super();
	}
	
	public EditorUserRappeler(EditorPart parent) {
		super(parent);
	}
	
	@Override
	protected void initEditor() {
		setPanel(composite);
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

	@Override
	public void createPartControl(Composite parent) {
		composite = new PaUserRappeler(parent,SWT.NONE);
		if(getController()==null) {
			setController(new SWTPaUserRappelerController(composite));
		}
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
		try {
			getController().validateUI();
		} catch (Exception e) {
			return false;
		}
		if(!getController().changementPageValide())return false;
		return true;
	}

	public PaUserRappeler getComposite() {
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
